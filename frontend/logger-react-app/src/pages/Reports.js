import '../App.css';
import React, { useEffect, useState, useRef } from "react";
import OneToOne from "../components/OneToOne";


function Reports(props) {
    const downloadData = () => {
        // 다운로드 기능 구현
    };

    const [startTime, setStartTime] = useState("");
    const [finishTime, setFinishTime] = useState("");

    const [OBDData, setOBDData] = useState(
        // 초기 테스트 값
        [
            {
                carName: "현대 아반떼",
                VIN: "F876F7623G234",
            },
            {
                type: "SPEED",
                value: "76",
                unit: "km/h"
            },
            {
                type: "RPM",
                value: "143",
                unit: "rpm"
            },
            {
                type: "ENGINE LOAD",
                value: "16.0",
                unit: "percent(%)"
            },
            {
                type: "FUEL LEVEL",
                value: "51.3",
                unit: "percent(%)"
            },
            {
                type: "OIL TEMP",
                value: "none",
                unit: ""
            },
            {
                type: "COOLANT TEMP",
                value: "81",
                unit: "℃"
            },
            {
                type: "THROTTLE POSITION",
                value: "20.7",
                unit: "percent(%)"
            },
            {
                type: "DISTANCE",
                value: "70",
                unit: "km"
                // 차량 기타 정보로 빼야할듯
            },
            {
                type: "RUNTIME",
                value: "1615",
                unit: "second"
                // 차량 기타 정보로 빼야할듯
            },
            {
                type: "RUNTIME(MIL)",
                value: "none",
                unit: ""
                // 논 값 나오는 거 2개 대신 barometric_pressure evap vapor pressure 대기압 그거랑
            }
        ]
    );

    const [GPS, setGPS] = useState([
        [37.4526437, 126.49236],
        [37.4768068, 126.4847975],
        [37.4988237, 126.4960839]
    ]);

    const handleStartTimeChange = (e) => {
        setStartTime(e.target.value);
    };

    const handleFinishTimeChange = (e) => {
        setFinishTime(e.target.value);
    };

    useEffect(() => {
        // 서버에 데이터 요청하는 코드
    }, []);

    const mapRef = useRef();
    const lat = GPS[0][0];  // 배열 길치 체크 후 가운데 값 부여
    const lng = GPS[0][1];  // 배열 길이 체크 후 가운데 값 부여

    // 위도, 경도 최대 최소 구한 후 줌 구현

    useEffect(() => {
        const { naver } = window;
        const polylinePath = GPS.map(gps => new naver.maps.LatLng(gps[0], gps[1]));
        console.log(polylinePath);
        if(mapRef.current && naver) {
            const location = new naver.maps.LatLng(lat, lng);
            const map = new naver.maps.Map(mapRef.current, {
                center: location,
                zoom: 17,
            });
            const polyline = new naver.maps.Polyline({
                path: polylinePath,
                strokeColor: "#FF0000",
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
                    <div className="download-button" onclick={downloadData}>
                        <i class="fa-solid fa-download"/>
                        <div className="download">Download</div>
                    </div>
                </div>
                <hr color="#E5E5E5"/>
                <div className="time-setting">
                    <div className="title">TIME RANGE :</div>
                    <div className="from">FROM</div>
                    <div className="start-time">
                        <input type="datetime-local" id="start" value={startTime} onChange={handleStartTimeChange}/>
                    </div>
                    <div className="to">TO</div>
                    <div className="finish-time">
                        <input type="datetime-local" id="finish" value={finishTime} onChange={handleFinishTimeChange}/>
                    </div>
                </div>
                <div className="body">
                    <div className="left">
                        <div className="car-photo">
                            <img src={props.photo} alt="차량 이미지"/>
                        </div>
                        <div className="car-info">
                            <ul>
                                <li>차량 이름: {OBDData[0].carName}</li>
                                <li>VIN: {OBDData[0].VIN}</li>
                                <li>대충 차량의 기타 정보들</li>
                            </ul>
                        </div>
                        <div className="car-status-left">
                            <OneToOne className="OneToOne" title={OBDData[1].type} value={OBDData[1].value} unit={OBDData[1].unit} fontSize="24px"/>
                            <OneToOne className="OneToOne" title={OBDData[2].type} value={OBDData[2].value} unit={OBDData[2].unit} fontSize="24px"/>
                            <OneToOne className="OneToOne" title={OBDData[3].type} value={OBDData[3].value} unit={OBDData[3].unit} fontSize="20px"/>
                            <OneToOne className="OneToOne" title={OBDData[4].type} value={OBDData[4].value} unit={OBDData[4].unit} fontSize="20px"/>
                        </div>
                    </div>
                    <div className="right">
                        <div className="map">
                            <div className="naver-map" ref={mapRef}></div>
                        </div>
                        <div className="car-status-right">
                            <OneToOne className="OneToOne" title={OBDData[6].type} value={OBDData[6].value} unit={OBDData[6].unit} fontSize="20px"/>
                            <OneToOne className="OneToOne" title={OBDData[7].type} value={OBDData[7].value} unit={OBDData[7].unit} fontSize="18px"/>
                            <OneToOne className="OneToOne" title={OBDData[7].type} value={OBDData[7].value} unit={OBDData[7].unit} fontSize="18px"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Reports;