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
import car1 from "./assets/car1.jpeg";

function App() {
  const [photo, setPhoto] = useState(car1);
  const [carName, setCarName] = useState();
  const [serialNumber, SetSerialNumber] = useState();

  return (
    <BrowserRouter>
      <div className="App">
        <div className="Frame">
          <Banner/>
          <Routes>
            <Route path="/" element={<LogIn/>}></Route>
            <Route path="FindIdPassword" element={<FindIdPassword/>}></Route>
            <Route path="Reports" element={<Reports photo={photo}/>}></Route>
            <Route path="Library" element={<Library/>}></Route>
            <Route path="GraphDashboard" element={<GraphDashboard/>}></Route>
            <Route path="SignUp" element={<SignUp/>}></Route>
            <Route path="Settings" element={<Settings/>}></Route>
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;
