import { useState } from "react";
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

    async function getLink() {
        const url = "/video/download";
        try {
            const response = await call(`${url}?deviceId=${props.deviceId}&videoName=${props.title}`, "GET", null);
            if(response) {
                const data = await response.json();
                console.log(data.url);
                const link = document.createElement('a');
                link.href = data.url;
                link.click();
            }
            else {
                console.error("Response was undefined");
            }
        } catch(error) {
            console.error("Error fetching data:", error);
        }
    };

    return (
        <div className="Video">
            <img src={Thumbnail}/>
            <div className="main">
                <div className="index">#{props.number}</div>
                <div className="download-button" onClick={getLink}>
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