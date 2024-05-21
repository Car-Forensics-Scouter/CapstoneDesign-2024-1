import { useState } from "react";
import Video from "../components/Video";

function Library() {
    const [videos, setVideos] = useState(
        [
            {
                img: "",
                number: 1,
                from: "2024.04.04 11:11:11",
                to: "2024.04.04 11:11:11"
            },
            {
                img: "",
                number: 2,
                from: "2024.04.04 11:11:11",
                to: "2024.04.04 11:11:11"
            },
            {
                img: "",
                number: 3,
                from: "2024.04.04 11:11:11",
                to: "2024.04.04 11:11:11"
            },
            {
                img: "",
                number: 4,
                from: "2024.04.04 11:11:11",
                to: "2024.04.04 11:11:11"
            }
        ]
    )

    return  (
        <div className="Library">
            <div className="wrap">
                <div className="head">
                    <div className="title">
                        Library
                    </div>
                </div>
                <hr color="#E5E5E5"/>
                <div className="body">
                    {videos.map((video) => (<Video img={video.img} number={video.number} from={video.from} to={video.to}/>))}
                </div>
            </div>
        </div>
    );
}

export default Library;