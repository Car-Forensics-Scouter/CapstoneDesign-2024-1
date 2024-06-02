import "./FindIdPassword.css";
import "../App.css";
import React, { useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { Box, Button } from "@mui/material";
import TextFields from "@mui/material/TextField";
import CFS_logo from "../assets/CFS_logo.png";

const FindIdPassword = () => {
    const [email_for_id, setEmailForID] = useState("");
    const [email_for_password, setEmailForPassword] = useState("");
    const [name_for_id, setNameForID] = useState("");
    const [name_for_password, setNameForPassword] = useState("");
    const [id, setID] = useState("");

    const handleFindID = async (e) => {
        e.preventDefault();

        const payload = {
            email : email_for_id,
            name : name_for_id
        };

        // payload 객체를 쿼리 파라미터로 변환
        const params = new URLSearchParams(payload).toString();
        
        const response = await axios.get(`http://localhost:8080/user/find_id?${params}`, {
            headers: {
                "Content-Type": "application/json",
            },
            withCredentials: true
        });

        if (response.status === 200) {
            alert("ID: " + response.data);
        } else {
            alert("입력하신 내용이 올바르지 않습니다.");
        }
    }

    const handleFindPassword = async (e) => {
        e.preventDefault();

        const payload = {
            id : id,
            email : email_for_password,
            name : name_for_password
        };

        const params = new URLSearchParams(payload).toString();        

        const response = await axios.get(`http://localhost:8080/user/find_password?${params}`, {    
            headers: {
                "Content-Type" : "application/json",
            },
            withCredentials: true
        });

        if (response.status === 200) {
            alert("New PW: " + response.data);
        }
        else{
            alert("입력하신 내용이 올바르지 않습니다.");
        }
    };


    return (
        <div className="hide_banner">
          <div className="find_id_password">
            <div className="header_center">
              <img className="logo" src={CFS_logo} alt="로고"/>
              <h1 className="header1">아이디/비밀번호 찾기</h1>
              <p className="header2">아이디나 비밀번호를 잊으셨나요?</p>
            </div>
    
            <div className="input_center">
                <form className="find_form" onSubmit={handleFindID}>
                    <div>
                        <div className="left_side">
                            <div className="data_name">이름 </div>
                            <Box className="input_box">
                                <TextFields
                                    type="name_for_id"
                                    placeholder="Name"
                                    value={name_for_id}
                                    InputProps={{ sx: { borderRadius: 20, width: "300px" } }}
                                    onChange={(e) => setNameForID(e.target.value)}
                                />
                            </Box>
            
                            <div>
                                <div className="data_name">이메일 </div>
                                <Box className="input_box">
                                    <TextFields
                                        type="email_for_id"
                                        placeholder="Email"
                                        value={email_for_id}
                                        InputProps={{ sx: { borderRadius: 20, width: "300px" } }}
                                        onChange={(e) => setEmailForID(e.target.value)}
                                    />
                                </Box>
                            </div>
                            
                            <Button 
                                className="find_button"
                                type="submit"
                                variant="contained"
                                sx={{ "&:hover": { backgroundColor: "#1976d2" } }}
                            >아이디 찾기
                            </Button>
                        </div>
                    </div>
                </form>
                    
                <form className="find_form" onSubmit={handleFindPassword}>
                    <div className="right_side">
                        <div>
                            <div className="data_name">아이디 </div>
                            <Box className="input_box">
                                <TextFields
                                    type="text"
                                    placeholder="ID"
                                    value={id}
                                    InputProps={{ sx: { borderRadius: 20, width: "300px" } }}
                                    onChange={(e) => setID(e.target.value)}
                                />
                            </Box>

                            <div className="data_name">이름 </div>
                            <Box className="input_box">
                                <TextFields
                                    type="name_for_password"
                                    placeholder="Name"
                                    value={name_for_password}
                                    InputProps={{ sx: { borderRadius: 20, width: "300px" } }}
                                    onChange={(e) => setNameForPassword(e.target.value)}
                                />
                            </Box>
            
                            <div>
                                <div className="data_name">이메일 </div>
                                <Box className="input_box">
                                    <TextFields
                                        type="email_for_password"
                                        placeholder="Email"
                                        value={email_for_password}
                                        InputProps={{ sx: { borderRadius: 20, width: "300px" } }}
                                        onChange={(e) => setEmailForPassword(e.target.value)}
                                    />
                                </Box>
                            </div>
            
                            <Button
                                className="find_button"
                                variant="contained"
                                type="submit"
                                InputProps={{ sx: { "&:hover": { backgroundColor: "#1976d2" } } }}
                                >
                                비밀번호 재발급
                            </Button>
                        </div>
                    </div>
                </form>
            </div>
    
            <div className="tail_center">
              <p className="service_text">서비스 이용을 위해 아이디/비밀번호를 확인해주세요. </p>
              <p className="link_text">회원이 아니신가요?{"  "}
                <Link to="/SignUp" style={{ color: "#C224DC" }}>회원가입 하기 </Link>
              </p>
              <div className="line"/>
              <p className="link_text">이미 계정이 있으신가요?{"  "}
                <Link to="/" style={{ color: "#C224DC" }}>로그인</Link>
              </p>
            </div>
          </div>
        </div>
    );
};

export default FindIdPassword;