import CFS_logo from "../assets/CFS_logo.png";
import { Link } from "react-router-dom";
import React, { useState } from "react";

function Banner() {
    const status = true; // 로그인 상태를 저장할 예정, 현재 구현 X

    const [clickBtn, setClickBtn] = useState("1");

    const handleClickForLink = (e) => {
        setClickBtn((preClickBtn) => {
            return e;
        });
    };

    if(status) {
        return (
            <div className="Banner">
                <div className="Logo">
                    <img src={CFS_logo} width="200" alt="service logo"/>
                </div>
                <div className="Menu">
                    <Link to="/Reports" onClick={() => handleClickForLink(1)} className="Bt1">
                        <div className={"LReports" + (1 == clickBtn ? "active" : "")}>
                            <i class="fa-solid fa-chart-simple"></i>
                            <div>Reports</div>
                        </div>
                    </Link>
                    <Link to="/Library" onClick={() => handleClickForLink(2)} className="Bt2">
                        <div className={"LLibrary" + (2 == clickBtn ? "active" : "")}>
                            <i class="fa-solid fa-photo-film"></i>
                            <div>Library</div>
                        </div>
                    </Link>
                    <Link to="/GraphDashboard" onClick={() => handleClickForLink(3)} className="Bt3">
                        <div className={"LGraphDashboard" + (3 == clickBtn ? "active" : "")}>
                            <i class="fa-solid fa-chart-line"></i>
                            <div>GraphDashboard</div>
                        </div>
                    </Link>
                </div>
                <div className="SubTitle">Support</div>
                <div className="Support">
                    <Link to="/Settings" onClick={() => handleClickForLink(4)} className="Bt4">
                        <div className={"LSettings" + (4 == clickBtn ? "active" : "")}>
                            <i class="fa-solid fa-gear"></i>
                            <div>Settings</div>
                        </div>
                    </Link>
                </div>
                <div className="Info">
                    <div className="Line"></div>
                    <div className="Contents">
                        <div className="Intro">Car Forensics Scouter</div>
                        <div className="Contact">whtjdqls01@gmail.com</div>
                    </div>
                </div>
            </div>
        );
    }
    return <div>{null}</div>;
}

export default Banner;