import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Box, TextField, Select, MenuItem, Button } from "@mui/material";
import TextFields from "@mui/material/TextField";

const SignUp = () => {
  const [email, setEmail] = useState("");
  const [id, setID] = useState("");
  const [password, setPassword] = useState("");
  const [passwordMatch, setPasswordMatch] = useState(true); // 비밀번호 일치 여부 상태
  const [passwordConfirm, setPasswordConfirm] = useState("");
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
      const response = await fetch("백엔드 서버 주소/중복 확인", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          value: props,
        }),
      });

      if (response.status == 201) {
        const data = await response.json();
        if (data.isDuplicate) {
          // 여기에 백엔드에서 처리한 로직을 연결
          alert("이미 사용중인 ID입니다.");
        } else {
          alert("사용할 수 있는 ID입니다.");
        }
      } else {
        throw new Error("중복 확인 요청에 실패했습니다.");
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
      // 기본 정보 입력.
      if (password === passwordConfirm) {
        alert("회원가입이 완료되었습니다!");
        // 여기서 추가적인 회원가입 로직을 작성할 수 있습니다 (예: 서버로 회원가입 정보 전송)
      } else {
        alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
      }

      // reponse 변수는 백엔드 서버의 회원가입 로직과 통신.
      const response = await fetch("로그인 서버 주소", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });

      if (response.status === 201) {
        const data = await response.json();
        console.log("회원가입 성공:", data);
        navigate("/LoginForm");
      } else {
        throw new Error("회원가입 요청 실패");
      }
    } catch (error) {
      console.error("회원가입 에러:", error);
      alert("회원가입 중 에러가 발생했습니다.");
    }
  };

  return (
    <div>
      <div style={{ textAlign: "left" }}>
        <img
          src="/logo.png"
          alt="로고"
          style={{
            width: "200px",
            height: "auto",
            paddingRight: "40px",
          }}
        />
        <h1
          style={{
            fontSize: "32px",
            fontWeight: "bolder",
            paddingLeft: "95px",
            margin: "0",
          }}
        >
          회원가입
        </h1>
        <p
          style={{
            fontSize: "20px",
            paddingLeft: "100px",
            margin: "0",
            marginBottom: "2px",
          }}
        >
          회원이 되어 다양한 정보를 접해 보세요!
        </p>
      </div>

      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <form
          className="signup-form"
          onSubmit={handleSignUp}
          style={{ width: "1000px" }}
        >
          <div
            style={{
              float: "left",
              marginLeft: "120px",
              marginTop: "25px",
            }}
          >
            <div>
              <div
                style={{
                  marginBottom: "1px",
                  float: "left",
                  marginLeft: "5px",
                }}
              >
                이름
              </div>
              <Box
                sx={{
                  marginBottom: 1,
                  borderRadius: "10px",
                  borderRadius: "25px",
                }}
              >
                <TextFields
                  type="name"
                  placeholder="Name"
                  value={name}
                  InputProps={{
                    sx: { borderRadius: 20, border: "2px solid black" },
                    style: { width: "270px", paddingLeft: 10 },
                  }}
                  onChange={(e) => setName(e.target.value)}
                />
              </Box>
            </div>

            <div>
              <div
                style={{
                  marginBottom: "1px",
                  float: "left",
                  marginLeft: "5px",
                }}
              >
                이메일
              </div>
              <Box sx={{ marginBottom: 1, borderRadius: "10px" }}>
                <TextFields
                  type="email"
                  placeholder="Email"
                  value={email}
                  InputProps={{
                    sx: { borderRadius: 20, border: "2px solid black" },
                    style: { width: "270px", paddingLeft: 10 },
                  }}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </Box>
            </div>

            <div>
              <div
                style={{
                  marginBottom: "1px",
                  float: "left",
                  marginLeft: "5px",
                  cursor: "pointer", // 클릭 가능하도록 커서 모양 변경
                }}
                onClick={() => setShowDropdown(!showDropdown)}
                // 클릭 시 드롭다운 토글
              >
                차종 선택
              </div>
            </div>
            <Box sx={{ marginBottom: 2, borderRadius: "10px" }}>
              <TextField
                type="text"
                placeholder="차종을 선택하세요"
                value={car}
                onChange={(e) => setCar(e.target.value)}
                InputProps={{
                  sx: { borderRadius: 20, border: "2px solid black" },
                  style: { width: "270px", paddingLeft: 10, textAlign: "left" },
                }}
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

          <div
            style={{
              float: "left",
              marginLeft: "75px",
              marginTop: "25px",
            }}
          >
            <div>
              <div
                style={{
                  marginBottom: "1px",
                  float: "left",
                  marginLeft: "15px",
                }}
              >
                아이디
              </div>
              <Box
                sx={{
                  marginBottom: 1,
                  borderRadius: "10px",
                  marginRight: 2,
                }}
              >
                <TextFields
                  type="text"
                  placeholder="ID"
                  value={id}
                  InputProps={{
                    sx: { borderRadius: 20, border: "2px solid black" },
                    style: { width: "270px", paddingLeft: 10 },
                  }}
                  onChange={(e) => setID(e.target.value)}
                />
                <Button
                  variant="contained"
                  type="button"
                  InputProps={{
                    sx: {
                      borderRadius: 20,
                      border: "2px solid black",
                      backgroundColor: "#3AE6B2",
                      marginTop: "15px",
                      marginLeft: "-110px",
                      "&:hover": {
                        backgroundColor: "#1976d2",
                      },
                    },
                    style: { width: "270px", paddingLeft: 10 },
                  }}
                  onClick={() => checkDuplication(id)}
                >
                  중복 확인
                </Button>
              </Box>
            </div>

            <div>
              <div
                style={{
                  marginBottom: "1px",
                  float: "left",
                  marginLeft: "15px",
                }}
              >
                비밀번호
              </div>
              <Box sx={{ borderRadius: "100px", padding: "10px" }}>
                <TextFields
                  type="password"
                  placeholder="Password"
                  value={password}
                  InputProps={{
                    sx: { borderRadius: 20, border: "2px solid black" },
                    style: { width: "270px", paddingLeft: 10 },
                  }}
                  onChange={handlePassword}
                />
              </Box>
            </div>

            <div>
              <div>
                <div
                  style={{
                    marginBottom: "1px",
                    float: "left",
                    marginLeft: "15px",
                  }}
                >
                  비밀번호 확인
                </div>
                <Box sx={{ marginBottom: 2, borderRadius: "20px" }}>
                  <TextFields
                    type="passwordConfirm"
                    placeholder="PasswordComfirm"
                    value={passwordConfirm}
                    InputProps={{
                      sx: { borderRadius: 20, border: "2px solid black" },
                      style: { width: "270px", paddingLeft: 10 },
                    }}
                    onChange={handlePasswordConfirm}
                  />
                </Box>
              </div>
              {!passwordMatch && password !== "" && (
                <div style={{ color: "red", marginLeft: "15px" }}>
                  비밀번호가 일치하지 않습니다.
                </div>
              )}
              {passwordMatch && password !== "" && (
                <div style={{ color: "green", marginLeft: "15px" }}>
                  비밀번호가 일치합니다.
                </div>
              )}
            </div>
          </div>
        </form>
      </div>

      <Button
        type="submit"
        variant="contained"
        sx={{
          width: "40%",
          backgroundColor: "#3AE6B2",
          padding: "10px",
          borderRadius: "25px",
          fontSize: "20px",
          "&:hover": {
            backgroundColor: "#1976d2",
          },
          marginTop: 3,
          marginLeft: 10,
        }}
      >
        회원가입
      </Button>

      <div>
        <p style={{ fontWeight: "bolder", fontSize: "25px", marginLeft: 60 }}>
          서비스 이용을 위해 회원가입 해주세요.
        </p>
        <p style={{ textAlign: "center", marginLeft: 60 }}>
          아이디/비밀번호를 잊으셨나요?{"  "}
          <Link to="/SignUp" style={{ color: "#C224DC" }}>
            아이디/비밀번호 찾기
          </Link>
        </p>
        <div
          style={{
            width: "60%",
            borderTop: "2.5px solid",
            marginLeft: "200px",
          }}
        ></div>
        <p style={{ marginTop: "10px", textAlign: "center", marginLeft: 60 }}>
          이미 계정이 있으신가요?{"  "}
          <Link to="/" style={{ color: "#C224DC" }}>
            로그인
          </Link>
        </p>
      </div>
    </div>
  );
};

export default SignUp;
