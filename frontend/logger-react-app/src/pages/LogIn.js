import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Box, Button, TextField } from "@mui/material";

const LogIn = () => {
  const [email, setEmail] = useState("");
  const [id, setID] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault(); // form 컴포넌트에서 버튼을 클릭하면 생기는 새로고침을 막아줌.

    try {
      // reponse 변수는 백엔드 서버의 로그인 로직과 통신합니다.
      const response = await fetch("로그인 서버 주소", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: email,
          id: id,
          password: password,
        }),
      });
      if (response.ok) {
        const result = await response.json();
        console.log(result);

        navigate("/MainBoard");
      } else {
        const errorData = await response.json();
        alert("로그인 실패. 아이디와 비밀번호를 확인해주세요.");
      }
    } catch (error) {
      console.error("로그인 요청 중 오류 발생 : ", error);
      alert("로그인 요청 중 오류가 발생했습니다.");
    }
  };

  return (
    <div>
      <div style={{ textAlign: "center" }}>
        <img
          src="/logo.png"
          alt="로고"
          style={{ marginTop: "50px", width: "600px", height: "auto" }}
        />
      </div>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          height: "60vh",
        }}
      >
        <form
          className="login-form"
          onSubmit={handleLogin}
          style={{ width: "400px" }}
        >
          <Box
            sx={{
              marginLeft: 2,
              marginBottom: 1,
              width: "90%",
            }}
          >
            <TextField
              type="text"
              placeholder="ID"
              value={id}
              onChange={(e) => setID(e.target.value)}
              fullWidth
              sx={{ borderRadius: "25px", border: "7px solid black" }}
            />
          </Box>

          <Box sx={{ marginLeft: 2, marginBottom: 3, width: "90%" }}>
            <TextField
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              fullWidth
              sx={{ borderRadius: "25px", border: "7px solid black" }}
            />
          </Box>

          <Button
            type="submit"
            variant="contained"
            fullWidth
            sx={{
              width: "90%",
              backgroundColor: "#3AE6B2",
              padding: "12px 20px",
              borderRadius: "25px",
              "&:hover": {
                backgroundColor: "#1976d2",
              },
              marginBottom: 3,
            }}
          >
            로그인
          </Button>

          <p
            style={{
              marginTop: "10px",
              textAlign: "center",
              fontSize: "20px",
              fontWeight: "bold",
            }}
          >
            서비스 이용을 위해 로그인 해주세요.
          </p>

          <p style={{ marginTop: "10px", textAlign: "center" }}>
            아이디/비밀번호를 잊으셨나요?{" "}
            <Link to="/SignUpForm" style={{ color: "#C224DC" }}>
              아이디/비밀번호 찾기
            </Link>
          </p>
          <div
            style={{
              width: "100%",
              borderTop: "2.5px solid",
              margin: "10px 0",
            }}
          ></div>
          <p style={{ marginTop: "10px", textAlign: "center" }}>
            계정이 없으신가요?{" "}
            <Link to="/SignUpForm" style={{ color: "#C224DC" }}>
              회원가입
            </Link>
          </p>
        </form>
      </div>
    </div>
  );
};

export default LogIn;

export default LoginForm;
