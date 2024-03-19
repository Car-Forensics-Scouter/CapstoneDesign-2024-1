import './App.css';

function App() {

  return (
    <div className="App">
      <header className="App-header">
        <div className="wrap">
          <div className="title">OBD2 Data Logger for Car Forensics</div>
          <div className="member-menu">
            <ul>
              <li>
                <a href="">로그인</a>
              </li>
              <div className="slash">/</div>
              <li>
                <a href="">회원가입</a>
              </li>
            </ul>
          </div>
        </div>
      </header>
      <body className="App-body">
        <div>대시보드 구성요소</div>
      </body>
      <footer className="App-footer">
        <div className="description">
          기타 정보 들어갈 예정
          <ul>
            <li>
              <a>소프트웨어학과 조성빈</a>
            </li>
            <li>
              <a>소프트웨어학과 윤예진</a>
            </li>
            <li>
              <a>소프트웨어학과 정욱재</a>
            </li>
            <li>
              <a>소프트웨어학과 임찬수</a>
            </li>
          </ul>
        </div>
      </footer>
    </div>
  );
}

export default App;
