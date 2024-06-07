import './App.css';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import React, { useState } from "react";
import Banner from './components/Banner';
import LogIn from './pages/LogIn';
import FindIdPassword from './pages/FindIdPassword';
import Reports from './pages/Reports';
import Library from './pages/Library';
import GraphDashboard from './pages/GraphDashboard';
import SignUp from './pages/SignUp';
import Settings from './pages/Settings';

function App() {
  const [carName, setCarName] = useState();
  const [deviceId, setDeviceId] = useState();
  const [startTime, setStartTime] = useState("2024-01-01T01:01");
  const [finishTime, setFinishTime] = useState("2024-12-31T01:01");

  return (
    <BrowserRouter>
      <div className="App">
        <div className="Frame">
          <Banner/>
          <Routes>
            <Route path="/" element={<LogIn setDeviceId={setDeviceId} setCarName={setCarName}/>}></Route>
            <Route path="FindIdPassword" element={<FindIdPassword/>}></Route>
            <Route path="Reports" element={<Reports setStartTime={setStartTime} setFinishTime={setFinishTime} startTime={startTime} finishTime={finishTime} deviceId={deviceId} carName={carName}/>}></Route>
            <Route path="Library" element={<Library deviceId={deviceId}/>}></Route>
            <Route path="GraphDashboard" element={<GraphDashboard setStartTime={setStartTime} setFinishTime={setFinishTime} startTime={startTime} finishTime={finishTime} deviceId={deviceId}/>}></Route>
            <Route path="SignUp" element={<SignUp/>}></Route>
            <Route path="Settings" element={<Settings/>}></Route>
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;
