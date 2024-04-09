import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Button } from '@mui/material';
import Box from '@mui/material/Box'
import TextFields from '@mui/material/TextField'

const SignUpForm = () => {
    const [email, setEmail] = useState('');
    const [id, setID] = useState('');
    const [password, setPassword] = useState('');
    const [passwordConfirm, setpasswordConfirm] = useState('');
    const [name, setName] = useState('');
    const [car, setCar] = useState('');
    const navigate = useNavigate();


    const handleSignUp = async (e) => {
        e.preventDefault();

        const payload = {
            email : email,
            id : id,
            password : password,
            name, name,
            car, car
        }

        try {
            // 기본 정보 입력.
            // reponse 변수는 백엔드 서버의 회원가입 로직과 통신합니다.
            const response = await fetch(
                '로그인 서버 주소',
                {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(payload),
                }
            );
            
            if (response.status === 201) {
                const data = await response.json();
                console.log('회원가입 성공:', data);
                navigate('/LoginForm');
            } else {
                throw new Error('회원가입 요청 실패');
            }
        } catch (error) {
            console.error('회원가입 에러:', error);
            alert('회원가입 중 에러가 발생했습니다.');
        }
    }

    // 중복 확인 요청
    const checkDuplication = async (props) => {
        try{
            const response = await fetch(
                '백엔드 서버 주소/중복 확인', {
                    method: 'POST',
                    headers:{
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        value : props,
                    })
                });

            if (response.status == 201) {
                const data = await response.json();
                if (data.isDuplicate) { // 여기에 백엔드에서 처리한 로직을 연결
                    alert('이미 사용중인 ID입니다.');
                }
                else {
                    alert('사용할 수 있는 ID입니다.');
                }
            }
            else {
                throw new Error('중복 확인 요청에 실패했습니다.');
            }
        } catch (error) {
            console.error('중복 확인 에러:', error);
            alert('중복 확인 중 에러가 발생했습니다.');
        }
    }



    return (
        <form onSubmit={handleSignUp}>
            <div>
                <Box sx={{ marginBottom : 2 }}>
                    <TextFields
                        type ='email'
                        placeholder='Email'
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </Box>
            </div>
            <div>
                <Box sx={{ marginBottom : 2 }}>
                    <TextFields
                        type ='text'
                        placeholder='ID'
                        value={id}
                        onChange={(e) => setID(e.target.value)}
                    />
                    <Button variant='contained' type='button' sx={{ marginLeft : 1 }}
                     onClick={() => checkDuplication(id)}>중복 확인</Button>
                </Box>
            </div>
            <div>
                <Box sx={{ marginBottom : 2 }}>
                    <TextFields
                        type ='password'
                        placeholder='Password'
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <Button variant='contained' type='button' sx={{ marginLeft : 1 }}
                     onClick={() => checkDuplication(password)}>중복 확인</Button>
                </Box>
            </div>
            <div>
                <Box sx={{ marginBottom : 2 }}>
                    <TextFields
                        type ='name'
                        placeholder='name'
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </Box>
            </div>
            <div>
                <Box sx={{ marginBottom : 2 }}>
                    <TextFields
                        type ='car'
                        placeholder='car'
                        value={car}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </Box>
            </div>

            <p className="login-link">
                이미 회원이신가요? <Link to="/">로그인</Link>
            </p>
        </form>
    );
};

export default SignUpForm;
