import "./GraphDashboard.css";
import React, { useEffect, useState, useCallback } from "react";
import Chart from "react-apexcharts";
import axios from "axios";  
import { errorAlert } from "../components/alert";

const defaultData = [
    {
        "deviceId": "-",
        "timeStamp": "2024-01-01T00:00:00",
        "vin": "-",
        "speed": 0.0,
        "rpm": 0.0,
        "engineLoad": 0.0,
        "fuelLevel": 0.0,
        "barometricPressure": 0.0,
        "coolantTemp": 0.0,
        "throttlePos": 0.0,
        "distance": 0.0,
        "runTime": 0.0,
        "lon": 0.0,
        "lat": 0.0
    },
    {
        "deviceId": "-",
        "timeStamp": "2024-01-01T00:01:00",
        "vin": "-",
        "speed": 0.0,
        "rpm": 0.0,
        "engineLoad": 0.0,
        "fuelLevel": 0.0,
        "barometricPressure": 0.0,
        "coolantTemp": 0.0,
        "throttlePos": 0.0,
        "distance": 0.0,
        "runTime": 0.0,
        "lon": 0.0,
        "lat": 0.0
    }
]

function GraphDashboard(props) {
    const [data, setData] = useState(defaultData);   // 가져온 전체 데이터

    const handleStartTimeChange = (e) => {
        props.setStartTime(e.target.value);
    };

    const handleFinishTimeChange = (e) => {
        props.setFinishTime(e.target.value);
    };
    
    // useCallback은 useEffect
    const fetchData = useCallback(async () => {
        try {
            // 데이터 가져오는 함수 구성.
            const url = "http://localhost:8080/api/obdlog/date-range";
            const parameter = `${url}?deviceId=${encodeURIComponent(props.deviceId)}&startDate=${encodeURIComponent(props.startTime)}&endDate=${encodeURIComponent(props.finishTime)}`;
            const response = await axios.get(parameter, {
                headers: {
                    "Content-Type": "application/json",
                },
                withCredentials: true
            });

            if (response.status === 200) {
                console.log("그래프 데이터를 성공적으로 로드했습니다.");
            
                // 가져온 데이터들을 Data에 저장.
                setData(response.data);
                return
            } else {
                errorAlert("그래프 데이터를 성공적으로 얻어오지 못 했습니다.");
                return
            }
        } catch (error) {
            if (error.response) {
                console.error("응답 오류: ", error.response.data);
                console.error("응답 상태: ", error.response.status);
                console.error("응답 헤더: ", error.response.headers);
              } else if (error.request) {
                console.error("요청 오류: ", error.request);
                errorAlert("서버 응답이 없습니다. 나중에 다시 시도해주세요.");
              } else {
                console.error("데이터 요청 중 오류 발생: ", error.message);
            }
        }
    }, [props.startTime, props.finishTime, props.deviceId]);

    // startTime, finishTime 바뀔 때마다 fetchData 실행 
    useEffect(() => {
        fetchData();
    }, [fetchData])

    // Download 버튼 누를 시 전체 데이터 가져옴.
    const downloadData = () => {
        const url = "http://localhost:8080/api/obdlog/download";
        const parameter = `${url}?deviceId=${encodeURIComponent(props.deviceId)}&startDate=${encodeURIComponent(props.startTime)}&endDate=${encodeURIComponent(props.finishTime)}`;
        const response = axios.get(parameter, {
            headers: {
                "Content-Type": "application/json",
            },
            withCredentials: true
        });

        response.blob().then((blob) => {
            const blobUrl = window.URL.createObjectURL(blob);
            const link = document.createElement("a");
            link.href = blobUrl;
            link.download = "CFS_REPORT.xlsx";
            link.click();
            window.URL.revokeObjectURL(blobUrl);
        }).catch((e) => console.error("Download error:", e));
    };

 
    // 그래프 기본 옵션(SPEED에 기반해서 나머지 만듦)
    const options = {
        chart: { type: "area", height: 350, background: "#ffffff" },
        dataLabels: { enabled: false },
        stroke: { curve: "straight" },
        title: { text: "SPEED", align: "left" },    // 데이터 이름
        subtitle: { text: "km/h", align: "left" },  // 데이터 단위
        labels: [props.startTime, props.finishTime],
        xaxis: { type: "datetime", },
        yaxis: { opposite: false, },
        legend: { horizontalAlign: "left" }
    };

    // 그래프별 세부 옵션
    const rpm_options = { ...options, title: { text: "RPM" }, subtitle: { text: "rpm"} }
    const engine_load_options = { ...options, title: { text: "ENGINE LOAD" }, subtitle: { text: "percent(%)"} }
    const throttle_pos_options = { ...options, title: { text: "THROTTLE POSITION" }, subtitle: { text: "" } }
    const fuel_level_options = { ...options, title: { text: "FUEL LEVEL" }, subtitle: { text: "LEVEL"} }
    const barometric_press_options = { ...options, title: { text: "BAROMETRIC PRESSURE" }, subtitle: { text: "℃"} }
    const run_time_options = { ...options, title: { text: "RUN TIME" }, subtitle: { text: "TIME(DAY:HH:MM"} }
    const distance_options = { ...options, title: { text: "DISTANCE" }, subtitle: { text: "℃"} }
    const coolant_temp_options = { ...options, title: { text: "COOLANT TEMPERATURE" }, subtitle: { text: "℃"} }

    // 데이터들 중 원하는 것만 취사 선택해서 그래프에 표시할 데이터로 포맷.
    const getSeries = (key) => {
        const series = [
          {
            name: key,
            data: data.map((item) => ({
              x: new Date(item.timeStamp).getTime(),
              y: item[key], // 값이 없으면 0으로 초기화
            })),
          },
        ];
        return series;
      };

    console.log(getSeries("speed"));

    return (
    <div className="Graphs">
        <div className="wrap">
            <div className="head">
                <div className="title">
                    Data Graph
                </div>
                <div className="download-button" onClick={downloadData}>
                    <i class="fa-solid fa-download"/>
                    <div className="download">Download</div>
                </div>
            </div>
            <hr color="#E5E5E5"/>
            <div className="time-setting">
                <div className="title">TIME RANGE :</div>
                <div className="from">FROM</div>
                <div className="start-time">
                    <input type="datetime-local" id="start" value={props.startTime} onChange={handleStartTimeChange}/>
                </div>
                <div className="to">TO</div>
                <div className="finish-time">
                    <input type="datetime-local" id="finish" value={props.finishTime} onChange={handleFinishTimeChange}/>
                </div>
            </div>

            <div className="body">
                <Chart options={options} series={getSeries("speed")} type="area" height={350}/>
                <Chart options={rpm_options} series={getSeries("rpm")} type="area" height={350}/>
                <Chart options={engine_load_options} series={getSeries("engineLoad")} type="area" height={350}/>
                <Chart options={throttle_pos_options} series={getSeries("throttlePos")} type="area" height={350}/>
                <Chart options={fuel_level_options} series={getSeries("fuelLevel")} type="area" height={350}/>
                <Chart options={barometric_press_options} series={getSeries("barometricPress")} type="area" height={350}/>
                <Chart options={run_time_options} series={getSeries("runTime")} type="area" height={350}/>
                <Chart options={distance_options} series={getSeries("distance")} type="area" height={350}/>
                <Chart options={coolant_temp_options} series={getSeries("coolantTemp")} type="area" height={350}/>
            </div>
        </div>
    </div>
    );
}

export default GraphDashboard;