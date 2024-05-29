import Thumbnail from "../assets/CFS_logo.png";

function Video(props) {

    function call(api, method, request) {
        let options = {
            headers: new Headers({
                "Content-Type": "application/json",
            }),
            url: "백엔드 기본 주소" + api,
            method: method,
        };

        if(request) {
            // GET METHOD
            options.body = JSON.stringify(request);
        }

        return fetch(options.url, options).then((response) => {
            if(response.status = 200) {
                return response;
            }
        }).catch((error) => {
            console.log("http error");
            console.log(error);
        })
    };

    const downloadVideo = () => {
        const url = "API 주소";
        const reponse = call(`${url}?deviceId=${encodeURIComponent(deviceId)}&videoIndex=${encodeURIComponent(props.number)}`, "GET", null);
        reponse.blob().then((blob) => {
            const blobUrl = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = blobUrl;
            link.download = "비디오 파일 이름.확장자";
            link.click();
            window.URL.revokeObjectURL(blobUrl);
        }).catch((e) => console.error("Download error:", e));
    };

    return (
        <div className="Video">
            <img src={Thumbnail}/>
            <div className="main">
                <div className="index">#{props.number}</div>
                <div className="download-button" onClick={downloadVideo}>
                    <i class="fa-solid fa-download"/>
                    <div className="download">Download</div>
                </div>
            </div>
            <div className="from">From: {props.from}</div>
            <div className="to">To: {props.to}</div>
        </div>
    );
}

export default Video;