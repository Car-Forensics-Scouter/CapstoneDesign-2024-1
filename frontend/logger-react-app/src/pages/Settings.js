import React, { useState } from "react";

function Settings() {
    const [password, setPassword] = useState({password: ""});
    const [car, setCar] = useState("");

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

    const [serialNumber, setSerialNumber] = useState("F123124");

    const handleSettingPW = async (e) => {
        e.preventDefault();

        const url = "api 주소";

        try {
            const response = await call(`${url}`, "POST", password);
            if(response) {
                const data = await response.data;
            } else {
                console.error("Response was undefined");
            }
        } catch(error) {
            console.error("Error fetching data:", error);
        }
    }

    const handleSettingCar = async (e) => {
        e.preventDefault();

        const url = "api 주소";

        try {
            const response = await call(`${url}`, "POST", car);
            if(response) {
                const data = await response.data;
            } else {
                console.error("Response was undefined");
            }
        } catch(error) {
            console.error("Error fetching data:", error);
        }
    }

    const handleSettingSerial = async (e) => {
        e.preventDefault();

        const url = "api 주소";

        try {
            const response = await call(`${url}`, "POST", serialNumber);
            if(response) {
                const data = await response.data;
            } else {
                console.error("Response was undefined");
            }
        } catch(error) {
            console.error("Error fetching data:", error);
        }
    }

    return  (
        <div className="Settings">
            <div className="wrap">
                <div className="head">
                    <div className="title">
                        Settings
                    </div>
                </div>
                <hr color="#E5E5E5"/>
                <div className="body">
                    <div className="templete">
                        <div className="kind">
                            <div className="title">새 비밀번호</div>
                            <div className="save" onClick={handleSettingPW}>저장</div>
                        </div>
                        <div className="input">
                            <input type="password" id="password" onChange={(e) => { setPassword(e.target.value) }}/>
                       </div>
                    </div>
                    <div className="templete">
                        <div className="kind">
                            <div className="title">차종</div>
                            <div className="save" onClick={handleSettingCar}>저장</div>
                        </div>
                        <div className="input">
                            <select>
                                <option value="1">그랜저 IG</option>
                                <option value="2">아반떼 CN7</option>
                                <option value="3">쏘렌토 MQ4</option>
                                <option value="4">K5 DL3</option>
                            </select>
                       </div>
                    </div>
                    <div className="templete">
                        <div className="kind">
                            <div className="title">제품 일련번호</div>
                            <div className="save" onClick={handleSettingSerial}>저장</div>
                        </div>
                        <div className="input">
                            <input type="text" placeholder={serialNumber} id="serialNumber"/>
                       </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Settings;