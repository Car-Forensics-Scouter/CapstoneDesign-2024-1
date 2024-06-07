import React, { useState } from "react";

function Settings(props) {
    const [password, setPassword] = useState({password: ""});

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

    const handleSettingPW = async (e) => {
        e.preventDefault();

        const url = "/user/password";

        if(password === "") return alert("패스워드 변경에 실패하셨습니다.");

        const body = {
            id: "1234",
            password: password
        }

        try {
            const response = await call(`${url}`, "PATCH", body);
            if(response) {
                const data = await response.data;

                if(data.success) {
                    alert("패스워드가 성공적으로 변경되었습니다.");
                } else {
                    alert("패스워드 변경에 실패하셨습니다.")
                }
            } else {
                console.error("Response was undefined");
            }
        } catch(error) {
            console.error("Error fetching data:", error);
        }
    }

    const handleSettingCar = async (e) => {
        e.preventDefault();

        const url = "/user/carName";

        try {
            const response = await call(`${url}`, "POST", props.carName);
            if(response) {
                const data = await response.data;

                if(data.success) {
                    alert("차종이 성공적으로 변경되었습니다.");
                } else {
                    alert("차종 변경에 실패하셨습니다.")
                }
            } else {
                console.error("Response was undefined");
            }
        } catch(error) {
            console.error("Error fetching data:", error);
        }
    }

    const handleSettingSerial = async (e) => {
        e.preventDefault();

        const url = "/user/deviceId";

        try {
            const response = await call(`${url}`, "POST", props.serialNumber);
            if(response) {
                const data = await response.data;

                if(data.success) {
                    alert("일련번호가 성공적으로 변경되었습니다.");
                } else {
                    alert("일련번호 변경에 실패하셨습니다.")
                }
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
                            <input type="password" id="password" onChange={(e) => { setPassword(e.target.value); }}/>
                       </div>
                    </div>
                    <div className="templete">
                        <div className="kind">
                            <div className="title">차종</div>
                            <div className="save" onClick={handleSettingCar}>저장</div>
                        </div>
                        <div className="input">
                            <select value={props.carName} onChange={(e) => { props.setCarName(e.target.value); }}>
                                <option value="그랜저 IG">그랜저 IG</option>
                                <option value="아반떼 CN7">아반떼 CN7</option>
                                <option value="쏘렌토 MQ4">쏘렌토 MQ4</option>
                                <option value="K5 DL3">K5 DL3</option>
                            </select>
                       </div>
                    </div>
                    <div className="templete">
                        <div className="kind">
                            <div className="title">제품 일련번호</div>
                            <div className="save" onClick={handleSettingSerial}>저장</div>
                        </div>
                        <div className="input">
                            <input type="text" placeholder={props.deviceId} id="serialNumber" onChange={(e) => { props.setDeviceId(e.target.value); }}/>
                       </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Settings;