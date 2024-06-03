import { useEffect, useState } from "react";
import Video from "../components/Video";
import img from "../assets/sample_image.png";

function Library() {
    const [videos, setVideos] = useState(
        [
            {
                deviceId: "123123",
                thumbnaiil: "234234",
                title: "asdf",
                createdDate: "123123",
                endDate: "2024-01-21T23:23:23"
            },
        ]
    )

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

    const deviceId = "asdf1234";

    async function video_view() {
        const url = "/video/find";
        try {
            const response = await call(`${url}?deviceId=${deviceId}`, "GET", null);
            if(response) {
                const data = await response.json();
                setVideos(data);
                console.log(data);
            }
            else {
                console.error("Response was undefined");
            }
        } catch(error) {
            console.error("Error fetching data:", error);
        }
    };

    useEffect(() => {
        video_view();
    }, []);

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
                    {videos.map((video, index) => (<Video img={img} number={index + 1} from={video.createdDate} to={video.endDate} title={video.title}/>))}
                </div>
            </div>
        </div>
    );
}

export default Library;