import "./FindIdPassword.css";
import "../App.css";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Box, TextField, MenuItem, Button } from "@mui/material";
import TextFields from "@mui/material/TextField";
import CFS_logo from "../assets/CFS_logo.png";

const FindIdPassword = () => {
    const [email, setEmail] = useState("");
    const [id, setID] = useState("");
    const [password, setPassword] = useState("");
    const [passwordMatch, setPasswordMatch] = useState(true); // 비밀번호 일치 여부 상태
    const [passwordConfirm, setPasswordConfirm] = useState("");
    const [name, setName] = useState("");

    const navigate = useNavigate();


    const handlePassword = (e) => {
        const {value} = e.target;
        setPassword(value);
    };

    const handlePasswordConfirm = (e) => {
        const { value } = e.target;
        setPasswordConfirm(value);
        setPasswordMatch(password === value);
    };

    const handleFineID = async (e) => {
        e.preventDefault();

        const payload = {
            email : email,
            name : name
        };

        const response = await fetch("DB 서버 주소", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json",
            },
            body: JSON.stringify(payload),
        });

        if (response.status ===201) {
            const data = await response.json();
            console.log("ID")
        }
    };
}