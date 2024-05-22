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
    e.preventDefault(); // form 컴포넌트에서 버튼을 클릭하면 생기는 새로고침을 막아줌.

    try {
      // 백엔드 서버의 로그인 로직과 통신하는 과정.
      // 로그인 성공 시 서버에서 생성한 토큰을 받아옴.
      // 향후 서버에서 데이터를 가져오는 등 인가 과정이 필요할 때, 로컬 스토리지에서 토큰을 뽑아 와서 함께 전달하면 된다.
      const response = await fetch("http://localhost:8080/user/login", {
        method: "POST",
        mode: "cors",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          id: id,
          password: password,
        }),
      })

      if (response.ok) {
        const data = await response.json();

        // 로그인에 성공했으면 accessToken을 발급받음.(유효 시간은 10분)
        if (data.accessToken) {
          localStorage.setItem('login-token', data.accessToken);
        }

        console.log("로그인 완료: ", result);
        navigate("/Reports");
      } else {
        console.error("로그인 요청에서 오류 발생");
        alert("아이디와 비밀번호를 다시 확인해주세요.");
      }
    } catch (error) {
      console.error("로그인 에러: ", error);
      alert("로그인 중 에러가 발생했습니다.");
    }
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