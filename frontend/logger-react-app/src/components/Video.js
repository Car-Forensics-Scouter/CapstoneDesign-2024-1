import Thumbnail from "../assets/CFS_logo.png";

function Video(props) {

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