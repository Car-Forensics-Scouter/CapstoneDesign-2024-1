import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const LoginForm = () => {
  const [email, setEmail] = useState('');
  const [id, setID] = useState('');
  const [passwd, setPasswd] = useState('');

  const navigate = useNavigate();
  const goMain = () => {
    navigate('/MainBoard');
  };

  const handleLogin = async (e) => {
    e.preventDefault();         // form 컴포넌트에서 버튼을 클릭하면 생기는 새로고침을 막아줌.
    console.log("Username : ${username}, Password: ${password}");

    // reponse 변수는 백엔드 서버의 로그인 로직과 통신합니다.
    const response = await fetch(
        "로그인 서버 주소",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            email: email,
            password: passwd,
          }),
        }
      )
      .then(res=>res.json())
      .then(res=>{
        console.log(res);
      });

      const result = await response.json();
  };


  return (
    <div>
        <form className="login-form" onSubmit={handleLogin}>
            <div>
                <label htmlFor='username'> 아이디 </label>
                <input
                    type="text"
                    placeholder="input ID"
                    value={id}
                    onChange={(e) => setID(e.target.value)}
                />
            </div>
        
            <div>
                <label htmlFor='password'> 비밀번호 </label>
                <input
                    type="passwd"
                    placeholder="input Password"
                    value={passwd}
                    onChange={(e) => setPasswd(e.target.value)}
                />

                <button type={handleLogin} onClick={goMain}>로그인</button>
            </div>

            <p className='signup-link'>
                아직 회원이 아니신가요? <Link to="/SignUpForm">회원가입</Link>
            </p>
        </form>
    </div>
  );
};

export default LoginForm;
