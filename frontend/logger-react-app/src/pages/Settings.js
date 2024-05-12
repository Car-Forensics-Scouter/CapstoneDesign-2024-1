function Settings() {

    return  (
        <div className="Settings">
            <div className="wrap">
                <div className="head">
                    <div className="title">
                        Settings
                    </div>
                </div>
                <hr color="#E5E5E5"/>
                <div className="body">
                    <div className="templete">
                        <div className="kind">
                            <div className="title">비밀번호</div>
                            <div className="save">저장</div>
                        </div>
                        <div className="input">
                            입력 칸
                       </div>
                    </div>
                    <div className="templete">
                        <div className="kind">
                            <div className="title">차종</div>
                            <div className="save">저장</div>
                        </div>
                        <div className="input">
                            입력 칸
                       </div>
                    </div>
                    <div className="templete">
                        <div className="kind">
                            <div className="title">제품 일련번호</div>
                            <div className="save">저장</div>
                        </div>
                        <div className="input">
                            입력 칸
                       </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Settings;