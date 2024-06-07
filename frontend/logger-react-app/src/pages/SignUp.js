import "./SignUp.css";
import "../App.css";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { Box, Menu, MenuItem, Button } from "@mui/material";
import TextFields from "@mui/material/TextField";
import { errorAlert, successAlert } from "../components/alert";
import CFS_logo from "../assets/CFS_logo.png";

const SignUp = () => {
  const [email, setEmail] = useState("");
  const [id, setID] = useState("");
  const [device_id, setDeviceId] = useState("");
  const [password, setPassword] = useState("");
  const [passwordMatch, setPasswordMatch] = useState(true); // 비밀번호 일치 여부 상태
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [isDuplication, setIsDuplication] = useState("");
  const [name, setName] = useState("");
  const [car, setCar] = useState("");
  const [showDropdown, setShowDropdown] = useState(false); // 차종 선택 목록에 쓰이는 변수
  const [anchorEl, setAnchorEl] = useState(null);   // 토글다운 활성화 여부
  const [carList] = useState(["그랜저 IG", "아반떼 CN7", "쏘렌토 MQ4", "K5 DL3"]); // 선택할 차종 목록

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
  const checkDuplication = async (e) => {    
    try {
      const id_data = {
        id: id
      }

      const response = await axios.post('http://localhost:8080/user/check-id',
       id_data, {
        headers: {
          'Content-Type': 'application/json',
        },
        withCredentials: true,
      });
  
      if (response.status === 200) {
        const data = response.data;
        if (data === false) {
          console.log("중복 확인이 완료되었습니다.", data);
          successAlert("Success!", "사용 가능한 아이디입니다.")
          setIsDuplication(false);
        }
        else {
          console.log("중복 확인 과정에서 오류가 발생했습니다.");
          errorAlert("Duplication Error!", "중복된 아이디입니다.")
          setIsDuplication(true);
        }
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
        console.error("로그인 요청 중 오류 발생: ", error.message);
        errorAlert("중복 확인 요청 중 오류가 발생했습니다.");
      }
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
      carName: car,
      deviceId: device_id
    };

    if (password === "") { // 1차 체크 : password
      errorAlert("비밀번호를 입력해주세요.")
    } else {
      console.log("비밀번호 입력 완료");
      if (password === passwordConfirm) { // 2차 체크 : password confirm
        console.log("비밀번호 검증 완료");
        if (isDuplication === false) {     // 3차 체크 : 중복 확인
          // 중복 체크까지 완료하면 request 보냄.
          
          // 필수 입력 데이터 체크
          if (!name || !email || !id || !password || !passwordConfirm || !car || !device_id) {
            errorAlert("모든 필수 항목을 입력해주세요.");
            return;
          }
          
          try {
          const response = await axios.post('http://localhost:8080/user/signup',
            payload, {
            headers: {
              'Content-Type': 'application/json',
            },
            withCredentials: true,
            });
          
          if (response.status === 200) {
            const data = response.data;
  
            console.log("중복 확인 완료");
            console.log("회원가입 성공:", data);

            successAlert("환영합니다! 회원가입이 되셨습니다. 로그인 화면으로 이동해 로그인 해주시기 바랍니다.")
            navigate("/");
          }
        }
      
        catch (error) {
          if (error.response) {
            console.error("응답 오류: ", error.response.data);
            console.error("응답 상태: ", error.response.status);
            console.error("응답 헤더: ", error.response.headers);
            errorAlert(`회원가입 실패: ${error.response.data}`);
          } else if (error.request) {
            console.error("요청 오류: ", error.request);
            errorAlert("서버 응답이 없습니다. 나중에 다시 시도해주세요.");
          } else {
            console.error("로그인 요청 중 오류 발생: ", error.message);
            errorAlert("회원가입 요청 중 오류가 발생했습니다.");
          }
        }
      } else{
          errorAlert("중복 확인이 되지 않았습니다. 다시 확인해주세요.");
        }
      } else {
        errorAlert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
      }
    }
  }

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

                <div className="data_name">디바이스 아이디 </div>
                  <Box className="input_box">
                      <TextFields
                        type="device_id"
                        placeholder="Device ID"
                        value={device_id}
                        InputProps={{ sx: { borderRadius: 20, width: "300px" } }}
                        onChange={(e) => setDeviceId(e.target.value)}
                      />
                    </Box>
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

            <div className="car_selection">차종 선택 </div>
                  <div className="menu"
                    onClick={() => setShowDropdown(!showDropdown)}   // 클릭 시 드롭다운 토글  
                  />
                  <Box className="input_box">
                    <Button
                      variant="outlined"
                      onClick={(e) => setAnchorEl(e.currentTarget)}
                      sx={{ borderRadius: 20, width: "500px", height:"55px",
                        color:"black", marginLeft: "150px" }}
                    >
                      {car || "차종을 선택하세요"}
                    </Button>
                    <Menu
                      anchorEl={anchorEl}
                      open={Boolean(anchorEl)}
                      onClose={() => setAnchorEl(null)}
                    >
                      { carList.map((carType) => (
                          <MenuItem key={carType} 
                            onClick={() => {
                              setCar(carType);
                              setAnchorEl(null);
                            }}>
                            {carType}
                          </MenuItem>
                        ))}
                    </Menu>
                  </Box>

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
          <p className="tail2">이미 계정이 있으신가요?{"  "}
            <Link to="/" style={{ color: "#C224DC" }}>로그인</Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default SignUp;
