import './App.css';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Link } from "react-router-dom";
import Banner from './components/Banner';
import LogIn from './pages/LogIn';
import Reports from './pages/Reports';
import Library from './pages/Library';
import GraphDashboard from './pages/GraphDashboard';
import SignUp from './pages/SignUp';
import Settings from './pages/Settings';

function App() {

  return (
    <BrowserRouter>
      <div className="App">
        <div className="Frame">
          <Banner/>
          <Routes>
            <Route path="/" element={<LogIn/>}></Route>
            <Route path="Reports" element={<Reports/>}></Route>
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
