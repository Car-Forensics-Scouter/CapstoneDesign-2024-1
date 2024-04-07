function Banner() {
    const status = true; // 로그인 상태를 저장할 예정, 현재 구현 X

    if(status) {
        return <h2>기본 배너 (왼쪽에 갈 예정) - 로그인 화면에서는 렌더링 X</h2>;
    }
    return <div>{null}</div>;
}

export default Banner;