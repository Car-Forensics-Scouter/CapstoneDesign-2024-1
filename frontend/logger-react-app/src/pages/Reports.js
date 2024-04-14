import '../App.css';
import React, { useEffect, useState, useRef } from "react";
import OneToOne from "../components/OneToOne";


function Reports(props) {
    const downloadData = () => {
        // 다운로드 기능 구현
    };

    const mapRef = useRef();
    const lat = 37.3595704;
    const lng = 127.105399;

    useEffect(() => {
        const { naver } = window;
        if(mapRef.current && naver) {
            const location = new naver.maps.LatLng(lat, lng);
            const map = new naver.maps.Map(mapRef.current, {
                center: location,
                zoom: 17,
            });
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
                    <div className="start-time">2020.04.13. 13:42:45</div>
                    <div className="to">TO</div>
                    <div className="finish-time">2020.04.13. 13:42:45</div>
                </div>
                <div className="body">
                    <div className="left">
                        <div className="car-photo">
                            <img src={props.photo} alt="차량 이미지"/>
                        </div>
                        <div className="car-info">
                            <ul>
                                <li>차량 이름: 현대 아반떼</li>
                                <li>VIN: F876F7623G234</li>
                                <li>대충 차량의 기타 정보들</li>
                            </ul>
                        </div>
                        <div className="car-status-left">
                            <OneToOne className="OneToOne" title="SPEED" value="76" unit="km.h" fontSize="24px"/>
                            <OneToOne className="OneToOne" title="RPM" value="76" unit="km.h" fontSize="24px"/>
                            <OneToOne className="OneToOne" title="THROTTLE POSITION" value="76" unit="km.h" fontSize="16px"/>
                            <OneToOne className="OneToOne" title="RPM" value="76" unit="km.h" fontSize="24px"/>
                        </div>
                    </div>
                    <div className="right">
                        <div className="map">
                            <div className="naver-map" ref={mapRef}></div>
                        </div>
                        <div className="car-status-right">
                            <OneToOne className="OneToOne" title="RPM" value="76.2" unit="km.h" fontSize="24px"/>
                            <OneToOne className="OneToOne" title="RPM" value="76" unit="km.h" fontSize="24px"/>
                            <OneToOne className="OneToOne" title="RPM" value="76" unit="km.h" fontSize="24px"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Reports;