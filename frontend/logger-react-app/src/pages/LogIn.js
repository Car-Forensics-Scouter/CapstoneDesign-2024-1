import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Button } from '@mui/material'
import Box from '@mui/material/Box'
import TextFields from '@mui/material/TextField'

const LoginForm = () => {
  const [email, setEmail] = useState('');
  const [id, setID] = useState('');
  const [password, setPassword] = useState('');

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();         // form 컴포넌트에서 버튼을 클릭하면 생기는 새로고침을 막아줌.

    try{
        // reponse 변수는 백엔드 서버의 로그인 로직과 통신합니다.
        const response = await fetch(
        '로그인 서버 주소',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            email: email,
            id : id,
            password: password,
          }),
        }
      )
      if (response.ok) {
        const result = await response.json();
        console.log(result);
        
        navigate('/MainBoard');
      }
      else {
        const errorData = await response.json();
        alert('로그인 실패. 아이디와 비밀번호를 확인해주세요.');
      }
    } catch (error) {
        console.error('로그인 요청 중 오류 발생 : ', error);
        alert('로그인 요청 중 오류가 발생했습니다.');
    }
  };

  return (
    <div>
        <form className='login-form' onSubmit={handleLogin}>
            <div>
                <label htmlFor='username'> 아이디 </label>
                <Box sx={{ marginBottom : 2 }}>
                    <TextFields
                        type='text'
                        placeholder='ID'
                        value={id}
                        onChange={(e) => setID(e.target.value)}
                    />
                </Box>
            </div>
        
            <div>
                <label htmlFor='password'> 비밀번호 </label>
                <Box sx={{ marginBottom : 2 }}>
                    <TextFields
                        type='password'
                        placeholder='Password'
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <Button type='submit' variant='contained' sx={{ marginLeft : 1 }}>
                        로그인 </Button>
                </Box>
            </div>

            <p className='SignUpForm-link'>
                아직 회원이 아니신가요? <Link to='/SignUpForm'>회원가입</Link>
            </p>
        </form>
    </div>
  );
};

export default LoginForm;
