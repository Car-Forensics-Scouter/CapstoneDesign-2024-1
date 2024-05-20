import "./SignUp.css";
import "../App.css";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Box, TextField, MenuItem, Button } from "@mui/material";
import TextFields from "@mui/material/TextField";
import CFS_logo from "../assets/CFS_logo.png";

const SignUp = () => {
  const [email, setEmail] = useState("");
  const [id, setID] = useState("");
  const [password, setPassword] = useState("");
  const [passwordMatch, setPasswordMatch] = useState(true); // 비밀번호 일치 여부 상태
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [isDuplication, setIsDuplication] = useState("");
  const [name, setName] = useState("");
  const [car, setCar] = useState("");
  const [showDropdown, setShowDropdown] = useState(false); // 차종 선택 목록에 쓰이는 변수
  const [carList] = useState(["Sedan", "SUV", "Hatchback", "Coupe"]); // 선택할 차종 목록

  const navigate = useNavigate();

  // 비밀번호 입력
  const handlePassword = (e) => {
    const { value } = e.target;
    setPassword(value);
  };

  // 비밀번호 일치 여부 확인
  const handlePasswordConfirm = (e) => {
    const { value } = e.target;
    setPasswordConfirm(value);
    setPasswordMatch(password === value);
  };

  // ID 중복 확인 요청
  const checkDuplication = async (props) => {
    try {
      const response = await fetch("http://localhost:8080/check_id", {
        method: "POST",
        mode: "cors",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          userid: props,
        }),
      });
  
      if (!response.ok) {
        throw new Error("중복 확인 요청에 실패했습니다.");
      }
  
      const data = await response.json();
      
      if (data === false) {
        alert("중복된 아이디입니다. 다시 시도하세요.");
        setIsDuplication(true);
      } else {
        alert("사용 가능한 아이디입니다.");
        setIsDuplication(false);
      }
    } catch (error) {
      console.error("중복 확인 에러:", error);
      alert("중복 확인 중 에러가 발생했습니다.");
    }
  };

  // 회원가입 처리 로직
  const handleSignUp = async (e) => {
    e.preventDefault();

    const payload = {
      email: email,
      id: id,
      password: password,
      name: name,
      car: car,
    };

    try {
      // reponse 변수는 백엔드 서버의 회원가입 로직과 통신.
      const response = await fetch("http://localhost:8080/signup", {
        method: "POST",
        mode: "cors",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });

      if (response.ok) {
        const data = await response.json();

        // 회원가입 시 로그인에 대한 accessToken 발급받으면 로컬 스토리지에 저장.
        if (data.accessToken) {
          localStorage.setItem('login-token', response);
        }
        

        if (password === "") { // 1차 체크 : password
          alert("비밀번호를 입력해주세요.")
        } else {
          console.log("비밀번호 입력 완료");
          if (password === passwordConfirm) { // 2차 체크 : password confirm
            console.log("비밀번호 검증 완료");
            if (isDuplication === true) {     // 3차 체크 : 중복 확인
              console.log("중복 확인 완료");
              console.log("회원가입 성공:", data);

              localStorage.setItem("token", token); // 토큰 저장 (여기서는 로컬 스토리지에 저장하는 예시)              

              alert("환영합니다! 회원가입이 되셨습니다. 로그인 화면으로 이동해 로그인 해주시기 바랍니다.")
              navigate("/LogIn");
            } else{
              alert("중복 확인이 되지 않았습니다. 다시 확인해주세요.");
            }
          } else {
            alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
          }
        }

      } else {
        throw new Error("회원가입 요청 실패");
      }
    } catch (error) {
      console.error("회원가입 에러:", error);
      alert("회원가입 중 에러가 발생했습니다.");
    }
  };

  return (
    <div className="hide_banner">
      <div className="SignUp">
        <div className="header_center">
          <img className="logo" src={CFS_logo} alt="로고"/>
          <h1 className="header1">회원가입</h1>
          <p className="header2">회원이 되어 다양한 정보를 접해 보세요!</p>
        </div>

        <div className="input_center">
          <form className="signup_form" onSubmit={handleSignUp} >
            <div>
              <div className="left_side">
                <div className="data_name">이름 </div>
                  <Box className="input_box">
                    <TextFields
                      type="name"
                      placeholder="Name"
                      value={name}
                      InputProps={{ sx: { borderRadius: 20, width: "300px" } }}
                      onChange={(e) => setName(e.target.value)}
                    />
                  </Box>

                
                <div className="data_name">이메일 </div>
                  <Box className="input_box">
                    <TextFields
                      type="email"
                      placeholder="Email"
                      value={email}
                      InputProps={{ sx: { borderRadius: 20, width: "300px" } }}
                      onChange={(e) => setEmail(e.target.value)}
                    />
                  </Box>
              

                <div className="data_name">차종 선택
                  <div className="menu"
                    onClick={() => setShowDropdown(!showDropdown)}   // 클릭 시 드롭다운 토글  
                  >
                </div>
                  <Box className="input_box">
                    <TextField
                      type="text"
                      placeholder="차종을 선택하세요"
                      value={car}
                      onChange={(e) => setCar(e.target.value)}
                      InputProps={{ sx: { borderRadius: 20, width: "300px" } }}
                      onClick={() => setShowDropdown(true)} // 입력 필드 클릭 시 드롭다운 표시
                      select // TextField를 select 모드로 변경
                    >
                      {showDropdown &&
                        carList.map((carType) => (
                          <MenuItem key={carType} value={carType}>
                            {carType}
                          </MenuItem>
                        ))}
                    </TextField>
                  </Box>
                </div>
              </div>

              <div className="right_side">
                <form onSubmit={checkDuplication}>
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
                      <Button
                        variant="contained"
                        type="button"
                        className="dedicating_button"
                        InputProps={{ sx: { "&:hover": { backgroundColor: "#1976d2" } } }}
                        onClick={() => checkDuplication(id)}
                      >중복 확인
                      </Button>
                    </Box>
                  </div>
                </form>

                <div>
                  <div className="data_name">비밀번호 </div>
                    <Box className="input_box">
                      <TextFields
                        type="password"
                        placeholder="Password"
                        value={password}
                        InputProps={{ sx: { borderRadius: 20, width: "300px" } }}
                        onChange={handlePassword}
                      />
                    </Box>
                </div>

                <div>
                  <div className="data_name">비밀번호 확인{password !== "" && (
                    <span style={{ color: passwordMatch ? "#40bb96" : "red", marginLeft: "20px" }}>
                        {passwordMatch ? "비밀번호 일치" : "비밀번호 일치하지 않음."}
                    </span>
                    )}
                    <Box className="input_box">
                      <TextFields
                        type="password"
                        placeholder="PasswordComfirm"
                        value={passwordConfirm}
                        InputProps={{ sx: { borderRadius: 20, width: "300px" } }}
                        className="text_box"
                        onChange={handlePasswordConfirm}
                      />
                    </Box>
                  </div>
                </div>
              </div>
            </div>
            <Button
              className="signup_button"
              type="submit"
              variant="contained"
              sx={{ "&:hover": { backgroundColor: "#1976d2" } }}
            >회원가입
            </Button>
          </form>
        </div>

        <div className="tail_center">
          <p className="service_text">서비스 이용을 위해 회원가입 해주세요. </p>
          <p className="find_id_password">아이디/비밀번호를 잊으셨나요?{"  "}
            <Link to="/FindIdPassword" style={{ color: "#C224DC" }}>아이디/비밀번호 찾기 </Link>
          </p>
          <div className="line"/>
          <p className="tail2">이미 계정이 있으신가요?{"  "}
            <Link to="/" style={{ color: "#C224DC" }}>로그인</Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default SignUp;
