import React, { useState } from "react";
import axios from "axios";
import { Box, TextField, Menu, MenuItem, Button } from "@mui/material";
import TextFields from "@mui/material/TextField";

function Settings() {

    const handleSettingPW = async (e) => {
        e.preventDefault();
        const payload_pw = {

        };

    }

    const handleSettingCar = async (e) => {
        e.preventDefault();
        const payload_car = {

        };
    }

    const handleSettingSerial = async (e) => {
        e.preventDefault();
        const payload_serial = {

        };
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
                            <div className="title">비밀번호</div>
                            <div className="save">저장</div>
                        </div>
                        <div className="input">
                            <input type="password" value="Seongbin1!" id="password"/>
                       </div>
                    </div>
                    <div className="templete">
                        <div className="kind">
                            <div className="title">차종</div>
                            <div className="save">저장</div>
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
                            <div className="save">저장</div>
                        </div>
                        <div className="input">
                            <input type="text" placeholder="F124235" id="serialNumber"/>
                       </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Settings;