import "./GraphDashboard.css";
import React, { useEffect, useState } from "react";
import Chart from "react-apexcharts";
import axios from "axios";
import { errorAlert, successAlert } from "../components/alert";

function GraphDashboard() {
    const [startTime, setStartTime] = useState();
    const [finishTime, setFinishTime] = useState();
    const [data, setData] = useState({
        spped: 0,
        engine_load: 0,
        throttle_pos: 0,
        oil_temp: 0,
        intake_press: 0,
        intakee_temp: 0,
    });   // 가져온 전체 데이터

    const handleStartTimeChange = (e) => {
        setStartTime(e.target.value);
    };

    const handleFinishTimeChange = (e) => {
        setFinishTime(e.target.value);
    };


    // 디바이스 아이디 임시 데이터(로그인 시 로컬 스토리지에 저장)
    const deviceId = "F1234";

    
    /* 랜더링될 때 마다 (= startTime/finishTime가 변경될 때마다)
    fetchData 함수가 수행되도록 함. */
    useEffect(() => {
        if (startTime && finishTime){
            fetchData();
        }
    }, [startTime, finishTime]);

    const fetchData = async () => {
        if (!startTime || !finishTime) return;

        try {
            // 데이터 가져오는 함수 구성.
            const url = "http://localhost:8080/api/obdlog/date-range";
            const parameter = `${url}?deviceId=${encodeURIComponent(deviceId)}
                            &startDate=${encodeURIComponent(startTime)}
                            &endDate=${encodeURIComponent(finishTime)}`;
            
            const response = await axios.get(parameter, {
                headers: {
                    "Content-Type": "application/json",
                },
                withCredentials: true
            });

            if (response.status === 200) {
                console.log("그래프 데이터를 성공적으로 로드했습니다.");
            } else {
                errorAlert("그래프 데이터를 성공적으로 얻어오지 못 했습니다.");
                return
            }

            const result = await response.json();
            setData(result);

        } catch (error) {
            console.error("Error fetching data: ", error);
        }
    };


    // Download 버튼 누를 시 전체 데이터 가져옴.
    const downloadData = async () => {
        const url = "http://localhost:8080/api/obdlog/~~~";
        const parameter = `${url}?deviceId=${encodeURIComponent(deviceId)}
                            &startDate=${encodeURIComponent(startTime)}
                            &endDate=${encodeURIComponent(finishTime)}`;

        const response = await axios.get(parameter, {
            headers: {
                "Content-Type": "application/json",
            },
            withCredentials: true
        });

        response.blob().then((blob) => {
            const blobUrl = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = blobUrl;
            link.download = "CFS_REPORT.xlsx";
            link.click();
            window.URL.revokeObjectURL(blobUrl);
        }).catch((e) => console.error("Download error:", e));
    };
 
    // 그래프 기본 옵션(SPEED에 기반해서 나머지 만듦)
    const options = {
        chart: {
          type: "area",
          height: 350,
          background: "#ffffff"
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: "straight"
        },
        title: {
          text: "SPEED", // 여기에 데이터 이름
          align: "left"
        },
        subtitle: {
          text: "km/h", // 여기에 데이터 단위
          align: "left"
        },
        labels: [startTime, finishTime],
        xaxis: {
          type: "datetime",
        },
        yaxis: {
          opposite: false,
        },
        legend: {
          horizontalAlign: "left"
        }
    };


    // 그래프별 세부 옵션
    const rpm_options = {
        ...options,
        title: { text: "RPM" },
        subtitle: { text: "rpm"}
    }

    const engine_load_options = {
        ...options,
        title: { text: "ENGINE LOAD" },
        subtitle: { text: "percent(%)"}
    }

    const throttle_pos_options = {
        ...options,
        title: { text: "THROTTLE POSITION" },
        subtitle: { text: "??"}
    }

    const oil_temp_options = {
        ...options,
        title: { text: "OIL TEMPERATURE" },
        subtitle: { text: "℃"}
    }

    const intake_press_options = {
        ...options,
        title: { text: "INTAKE PRESSURE" },
        subtitle: { text: "??"}
    }

    const intake_temp_options = {
        ...options,
        title: { text: "INTAKE TEMPERATURE" },
        subtitle: { text: "℃"}
    }
    

    return (
    <div className="Graphs">
        <div className="wrap">
            <div className="head">
                <div className="title">
                    Data Graph
                </div>
                <div className="download-button" onclick={downloadData}>
                    <i class="fa-solid fa-download"/>
                    <div className="download">Download</div>
                </div>
            </div>
            <hr color="#E5E5E5"/>
            <div className="time-setting">
                <div className="title">TIME RANGE :</div>
                <div className="from">FROM</div>
                <div className="start-time">
                    <input type="datetime-local" id="start" value={startTime || ""} onChange={handleStartTimeChange}/>
                </div>
                <div className="to">TO</div>
                <div className="finish-time">
                    <input type="datetime-local" id="finish" value={finishTime || ""} onChange={handleFinishTimeChange}/>
                </div>
            </div>

            <div className="body">
                <Chart
                    options={options}
                    series={data.speed}
                    type="area"
                    height={350}
                />

                <Chart
                    options={rpm_options}
                    series={data.rpm}
                    type="area"
                    height={350}
                />

                <Chart
                    options={throttle_pos_options}
                    series={data.throttleO}
                    type="area"
                    height={350}
                />

                <Chart
                    options={engine_load_options}
                    series={data.throttleO}
                    type="area"
                    height={350}
                />

                <Chart
                    options={oil_temp_options}
                    series={data.throttleO}
                    type="area"
                    height={350}
                />

                <Chart
                    options={intake_press_options}
                    series={data.throttleO}
                    type="area"
                    height={350}
                />

                <Chart
                    options={intake_temp_options}
                    series={data.throttleO}
                    type="area"
                    height={350}
                />  
            </div>
        </div>
    </div>

    );
}

export default GraphDashboard;