import '../App.css';
import React, { useState } from "react";


function Reports(props) {
    const downloadData = () => {
        // 다운로드 기능 구현
    };

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
                <hr width="" color="#E5E5E5"/>
                <div>
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
                            컴포넌트 제작 예정...
                        </div>
                    </div>
                    <div className="right">
                        <div className="map">
                            맵이 들어갈 예정이랍니다...
                        </div>
                        <div className="car-status-right">
                            컴포넌트 제작 예정...
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Reports;