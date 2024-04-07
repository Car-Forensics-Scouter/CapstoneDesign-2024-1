import { Link } from "react-router-dom";

function LogIn() {

    return  (
        <div>
            <h1>로그인 페이지</h1>
            <Link to={"/Report"}>로그인을 했다면..</Link>
        </div>
    );
}

export default LogIn;