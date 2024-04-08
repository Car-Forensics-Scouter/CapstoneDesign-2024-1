import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

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

            if (response.ok) {
                const data = await response.json();
                if (data.isDuplicate) {
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
                <input
                    type ='email'
                    placeholder='email'
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
            </div>
            <div>
                <input
                    type ='text'
                    placeholder='ID'
                    value={id}
                    onChange={(e) => setID(e.target.value)}
                />
                <button type='submit' onClick={() => checkDuplication(id)}>중복 확인</button>
            </div>
            <div>
                <input
                    type ='password'
                    placeholder='password'
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button type='button' onClick={() => checkDuplication(password)}>중복 확인</button>
            </div>
            <div>
                <input
                    type ='name'
                    placeholder='name'
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
            </div>
            <div>
                <input
                    type ='car'
                    placeholder='car'
                    value={car}
                    onChange={(e) => setEmail(e.target.value)}
                />
            </div>

            <p className="login-link">
                이미 회원이신가요? <Link to="/">로그인</Link>
            </p>
        </form>
    );
};

export default SignUpForm;
