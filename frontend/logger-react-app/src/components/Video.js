import Thumbnail from "../assets/sample_image.png";

function Video(props) {

    function call(api, method, request) {
        let options = {
            headers: new Headers({
                "Content-Type": "application/json",
            }),
            url: "http://localhost:8080" + api,
            method: method,
        };

        if(request) {
            // GET METHOD
            options.body = JSON.stringify(request);
        }

        return fetch(options.url, options).then((response) => {
            if(response.ok) {
                return response;
            }
            else {
                throw new Error("Network response was not ok.");
            }
        }).catch((error) => {
            console.log("http error");
            console.log(error);
        })
    };

    const deviceId = "F1234";

    /*
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
*/

    return (
        <div className="Video">
            <img src={Thumbnail}/>
            <div className="main">
                <div className="index">#{props.number}</div>
                <div className="download-button">
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