import '../App.css';
import React, { useEffect, useState, useRef } from "react";
import OneToOne from "../components/OneToOne";
import car1 from "../assets/그랜저 IG.png";
import car2 from "../assets/아반떼 CN7.png";
import car3 from "../assets/쏘렌토 MQ4.png";
import car4 from "../assets/K5 DL3.png";
import sample_image from "../assets/car1.jpeg";

function Reports(props) {
    const [photo, setPhoto] = useState(sample_image);

    function call(api, method, request) {
        let options = {
            headers: new Headers({
                "Content-Type": "application/json",
            }),
            url: "http://localhost:8080" + api,
            method: method,
        };

        if(request) {
            // GET METHOD
            options.body = JSON.stringify(request);
        }

        return fetch(options.url, options).then((response) => {
            if(response.ok) {
                return response;
            }
            else {
                throw new Error("Network response was not ok.");
            }
        }).catch((error) => {
            console.log("http error");
            console.log(error);
        })
    };

    async function downloadData() {
        console.log("눌림");
        const url = "/api/obdlog/download";
        try {
            const response = await call(`${url}?deviceId=${props.deviceId}&startDate=${props.startTime}&endDate=${props.finishTime}`, "GET", null);
            if(response) {
                const blob = await response.blob();
                const blobUrl = window.URL.createObjectURL(blob);
                const link = document.createElement('a');
                link.href = blobUrl;
                link.download = "CFS_REPORT.xlsx";
                link.click();
                window.URL.revokeObjectURL(blobUrl);
            } else {
                console.error("Response was undefined.");
            }
        } catch (e) {
            console.error("Download error:", e);
        }
    };

    const [OBDData, setOBDData] = useState(
        {
            "speed": 70,
            "rpm": 123,
            "engineLoad": 12,
            "fuelLevel": 12,
            "throttlePos": 12,
            "barometricPressure": 12,
            "coolantTemp": 12,
            "distance": 23,
            "vin": "asdf1234",
            "runtime": 123
        }
    );

    const [GPS, setGPS] = useState(
        [
            {
                "lat": 1.0,
                "lon": 1.0
            },
            {
                "lat": 1.0,
                "lon": 1.0
            }
        ]
    );

    const handleStartTimeChange = (e) => {
        props.setStartTime(e.target.value);
        console.log("확인", props.startTime);
        view();
        gps_view();
    };

    const handleFinishTimeChange = (e) => {
        props.setFinishTime(e.target.value);
        console.log("확인", props.finishTime);
        view();
        gps_view();
    };

    async function view() {
        const url = "/api/obdlog/summary-avg";
        try {
            const response = await call(`${url}?deviceId=${props.deviceId}&startDate=${props.startTime}&endDate=${props.finishTime}`, "GET", null);
            if(response) {
                const data = await response.json();
                setOBDData(data);
                console.log(data);
            }
            else {
                console.error("Response was undefined");
            }
        } catch(error) {
            console.error("Error fetching data:", error);
        }
    };

    async function gps_view() {
        const url = "/api/obdlog/summary-list";
        try {
            const response = await call(`${url}?deviceId=${props.deviceId}&startDate=${props.startTime}&endDate=${props.finishTime}`, "GET", null);
            if(response) {
                const data = await response.json();
                setGPS(data);
                console.log(data);
                setLat(GPS[Math.trunc(GPS.length/2)].lat);
                setLon(GPS[Math.trunc(GPS.length/2)].lon);
            }
            else {
                console.error("Response was undefined");
            }
        } catch(error) {
            console.error("Error fetching data:", error);
        }
    };

    useEffect(() => {
        if(props.carName === "그랜저 IG") { setPhoto(car1); }
        else if(props.carName === "아반떼 CN7") { setPhoto(car2); }
        else if(props.carName === "쏘렌토 MQ4") { setPhoto(car3); }
        else if(props.carName === "K5 DL3") { setPhoto(car4); }
        view();
        gps_view();
    }, []);

    const mapRef = useRef();
    const [lat, setLat] = useState(1.0);  // 배열 길치 체크 후 가운데 값 부여
    const [lon, setLon] = useState(1.0);  // 배열 길이 체크 후 가운데 값 부여

    // 위도, 경도 최대 최소 구한 후 줌 구현

    useEffect(() => {
        const { naver } = window;
        const polylinePath = GPS.map(gps => new naver.maps.LatLng(gps.lat, gps.lon));
        console.log(polylinePath);
        if(mapRef.current && naver) {
            const location = new naver.maps.LatLng(lat, lon);
            const map = new naver.maps.Map(mapRef.current, {
                center: location,
                zoom: 17,
            });
            const polyline = new naver.maps.Polyline({
                path: polylinePath,
                strokeColor: "#3AE055",
                strokeOpacity: 0.8,
                strokeWeight: 6,
                map
            })
            new naver.maps.Marker({
                position: location,
                map,
            });
        }
    }, []);


    return  (
        <div className="Reports">
            <div className="wrap">
                <div className="head">
                    <div className="title">
                        Reports
                    </div>
                    <div className="download-button" onClick={downloadData}>
                        <i class="fa-solid fa-download"/>
                        <div className="download">Download</div>
                    </div>
                </div>
                <hr color="#E5E5E5"/>
                <div className="time-setting">
                    <div className="title">TIME RANGE :</div>
                    <div className="from">FROM</div>
                    <div className="start-time">
                        <input type="datetime-local" id="start" value={props.startTime} onChange={handleStartTimeChange}/>
                    </div>
                    <div className="to">TO</div>
                    <div className="finish-time">
                        <input type="datetime-local" id="finish" value={props.finishTime} onChange={handleFinishTimeChange}/>
                    </div>
                </div>
                <div className="body">
                    <div className="left">
                        <div className="car-photo">
                            <img src={photo} alt="차량 이미지"/>
                        </div>
                        <div className="car-info">
                            <ul>
                                <li>차량 이름: {props.carName}</li>
                                <li>VIN: {OBDData.vin}</li>
                                <hr/>
                                <li>이동 거리: {OBDData.distance} km</li>
                                <li>이동 시간: {OBDData.runtime} 초</li>
                            </ul>
                        </div>
                        <div className="car-status-left">
                            <OneToOne className="OneToOne" title="SPEED" value={OBDData.speed} unit="km/h" fontSize="24px" tooltip="지정 시간대의 평균 속도입니다."/>
                            <OneToOne className="OneToOne" title="RPM" value={OBDData.rpm} unit="rpm" fontSize="24px" tooltip="지정 시간대의 평균 RPM입니다."/>
                            <OneToOne className="OneToOne" title="ENGINE LOAD" value={OBDData.engineLoad} unit="percent(%)" fontSize="20px" tooltip="지정 시간대의 엔진 부하량의 평균값입니다. 엔진 부하란 엔진에 부하가 가해지는 정도를 말하며, 값이 커질수록 엔진회전수가 증가합니다."/>
                            <OneToOne className="OneToOne" title="FUEL LEVEL" value={OBDData.fuelLevel} unit="percent(%)" fontSize="20px" tooltip="지정 시간대의 연료량의 평균값입니다."/>
                        </div>
                    </div>
                    <div className="right">
                        <div className="map">
                            <div className="naver-map" ref={mapRef}></div>
                        </div>
                        <div className="car-status-right">
                            <OneToOne className="OneToOne" title="COOLANT TEMP" value={OBDData.coolantTemp} unit="℃" fontSize="20px" tooltip="지정 시간대의 냉각수 온도의 평균값입니다."/>
                            <OneToOne className="OneToOne" title="THROTLE POSITION" value={OBDData.throttlePos} unit="percent(%)" fontSize="18px" tooltip="지정 시간대의 엑셀 위치의 평균값입니다. 엑셀 페달을 누르는 정도에 비례하여 값이 증가합니다."/>
                            <OneToOne className="OneToOne" title="BAROMETRIC PRESSURE" value={OBDData.barometricPressure} unit="percent(%)" fontSize="18px" tooltip="지정 시간대의 대기압 평균값입니다. 차량은 이 값을 통해 차의 고도를 계산하여 적당한 공연비가 되도록 연료 분사량과 점화시기를 조정합니다."/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Reports;