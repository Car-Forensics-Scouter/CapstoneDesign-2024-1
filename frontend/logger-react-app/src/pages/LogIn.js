import "./LogIn.css";
import "../App.css";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Box, Button, TextField } from "@mui/material";
import CFS_logo from "../assets/CFS_logo.png";

const Login = () => {
  const [id, setID] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate(); // 화면 이동 객체

  const handleLogin = async (e) => {
    
  };

  return (
    <div className="hide_banner">
      <div className="Login">
        <div className="text_center">
          <img className="logo" src={CFS_logo} alt="로고"/>
          <div className="top_empty_space">
            <form className="login_form" onSubmit={handleLogin}>
              <Box className="input_box">
                <TextField
                  type="text"
                  placeholder="ID"
                  value={id}
                  onChange={(e) => setID(e.target.value)}
                  className="text_box"
                  InputProps={{ sx: { borderRadius: "25px" }}}
                />
              </Box>

              <Box className="input_box">
                <TextField
                  type="password"
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="text_box"
                  InputProps={{ sx: { borderRadius: "25px"} }}
                />
              </Box>

              <Button
                marginTop= "10px"
                type="submit"
                variant="contained"
                className="submit_button"
              >
                로그인
              </Button>

              <p className="service_text">서비스 이용을 위해 로그인 해주세요.</p>
              <p className="find_id_password">
                아이디/비밀번호를 잊으셨나요?{" "}
                <Link to="/FindIdPassword" style={{ color: "#C224DC" }}>
                  아이디/비밀번호 찾기
                </Link>
              </p>

              <div className="line"></div>
              <p className="sign_up">계정이 없으신가요?{" "}
                {/* <Link to="/SignUp" style={{ color: "#C224DC" }}>
                  회원가입
                </Link> */}

                <Link to="/GraphDashboard" style={{ color: "#C224DC" }}>
                  회원가입 (임시 코드)
                </Link>
              </p>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;