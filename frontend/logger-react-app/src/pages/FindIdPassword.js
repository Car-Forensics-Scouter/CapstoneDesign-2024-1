import "./FindIdPassword.css";
import "../App.css";
import React, { useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { Box, Button } from "@mui/material";
import TextFields from "@mui/material/TextField";
import { errorAlert, successAlert } from "../components/alert";
import CFS_logo from "../assets/CFS_logo.png";

const FindIdPassword = () => {
    const [email_for_id, setEmailForID] = useState("");
    const [email_for_password, setEmailForPassword] = useState("");
    const [name_for_id, setNameForID] = useState("");
    const [name_for_password, setNameForPassword] = useState("");
    const [id, setID] = useState("");

    const handleFindID = async (e) => {
        e.preventDefault();

        try{
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
            console.log("입력하신 ID 관련 정보가 적절합니다.");
            successAlert("ID: " + response.data);
        } else {
            errorAlert("입력하신 내용이 올바르지 않습니다.");
        }
    } catch (error) {
        if (error.response) {
          console.error("응답 오류: ", error.response.data);
          console.error("응답 상태: ", error.response.status);
          console.error("응답 헤더: ", error.response.headers);
          errorAlert(`중복 확인 실패: ${error.response.data}`);
        } else if (error.request) {
          console.error("요청 오류: ", error.request);
          errorAlert("서버 응답이 없습니다. 나중에 다시 시도해주세요.");
        } else {
          console.error("ID 정보 요청 중 오류 발생: ", error.message);
          errorAlert("ID 정보 확인 요청 중 오류가 발생했습니다.");
        }
      }
    }

    const handleFindPassword = async (e) => {
        e.preventDefault();

        try{
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
                console.log("입력하신 비밀번호 정보는 적절합니다.");
                successAlert("New PW: " + response.data);
            }
            else{
                errorAlert("서버로부터 오류 메시지가 발생했습니다.");
            }
        } catch (error) {
            if (error.response) {
              console.error("응답 오류: ", error.response.data);
              console.error("응답 상태: ", error.response.status);
              console.error("응답 헤더: ", error.response.headers);
              errorAlert(`비밀번호 확인 실패 : ${error.response.data}`);
            } else if (error.request) {
              console.error("요청 오류 : ", error.request);
              errorAlert("서버 응답이 없습니다. 나중에 다시 시도해주세요.");
            } else {
              console.error("비밀번호 요청 중 오류 발생 : ", error.message);
              errorAlert("비밀번호 확인 요청 중 오류가 발생했습니다.");
            }
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
                                        type="email"
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
                                        type="email"
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