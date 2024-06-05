import "./GraphDashboard.css";
import React, { useEffect, useState, useCallback } from "react";
import Chart from "react-apexcharts";
import axios from "axios";
import { errorAlert } from "../components/alert";

function GraphDashboard() {
    const [startTime, setStartTime] = useState();
    const [finishTime, setFinishTime] = useState();
    const [data, setData] = useState([]);   // 가져온 전체 데이터

    const handleStartTimeChange = (e) => {
        setStartTime(e.target.value);
    };

    const handleFinishTimeChange = (e) => {
        setFinishTime(e.target.value);
    };

    // 디바이스 아이디 : 로그인 시 로컬 스토리지에 저장
    const deviceId = localStorage.getItem("device-id");
    
    // useCallback은 useEffect
    const fetchData = useCallback(async () => {
        try {
            // 데이터 가져오는 함수 구성.
            const url = "http://localhost:8080/api/obdlog/date-range";
            const parameter = `${url}?deviceId=${encodeURIComponent(deviceId)}&startDate=${encodeURIComponent(startTime)}&endDate=${encodeURIComponent(finishTime)}`;
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

            // 가져온 데이터들을 Data에 저장.
            const result = await response.json();
            setData(result);
            
        } catch (error) {
            if (error.response) {
                console.error("응답 오류: ", error.response.data);
                console.error("응답 상태: ", error.response.status);
                console.error("응답 헤더: ", error.response.headers);
                errorAlert(`데이터 패치 실패: ${error.response.data}`);
              } else if (error.request) {
                console.error("요청 오류: ", error.request);
                errorAlert("서버 응답이 없습니다. 나중에 다시 시도해주세요.");
              } else {
                console.error("데이터 요청 중 오류 발생: ", error.message);
                errorAlert("데이터 요청 중 오류가 발생했습니다.");
            }
        }
    }, [startTime, finishTime, deviceId]);

    // startTime, finishTime 바뀔 때마다 fetchData 실행 
    useEffect(() => {
        fetchData();
    }, [fetchData])


    // Download 버튼 누를 시 전체 데이터 가져옴.
    const downloadData = () => {
        const url = "http://localhost:8080/api/obdlog/~~~";
        const parameter = `${url}?deviceId=${encodeURIComponent(deviceId)}&startDate=${encodeURIComponent(startTime)}&endDate=${encodeURIComponent(finishTime)}`;
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
        subtitle: { text: "" }
    }

    const fuel_level_options = {
        ...options,
        title: { text: "FUEL LEVEL" },
        subtitle: { text: "LEVEL"}
    }

    const barometric_press_options = {
        ...options,
        title: { text: "BAROMETRIC PRESSURE" },
        subtitle: { text: "℃"}
    }

    const run_time_options = {
        ...options,
        title: { text: "RUN TIME" },
        subtitle: { text: "TIME(DAY:HH:MM"}
    }

    const distance_options = {
        ...options,
        title: { text: "DISTANCE" },
        subtitle: { text: "℃"}
    }
    
    const coolant_temp_options = {
        ...options,
        title: { text: "COOLANT TEMPERATURE" },
        subtitle: { text: "℃"}
    }

    // 데이터 형식 통일 및 시간 데이터 처리
    const formattedData = data.map(entry => ({
        x: new Date(entry.timeStamp).getTime(),
        speed: entry.speed,
        rpm: entry.rpm,
        engineLoad: entry.engineLoad,
        throttlePos: entry.throttlePos,
        fuelLevel: entry.fuelLevel,
        barometricPressure: entry.barometricPressure,
        runTime: entry.runTime,
        distance: entry.distance,
        coolantTemp: entry.coolantTemp
    }));

    // x, y축에 맞춤.
    const speedSeries = { name: 'Speed', data: formattedData.map(entry => ({ x: entry.x, y: entry.speed })) };
    const rpmSeries = { name: 'RPM', data: formattedData.map(entry => ({ x: entry.x, y: entry.rpm })) };
    const engineLoadSeries = { name: 'Engine Load', data: formattedData.map(entry => ({ x: entry.x, y: entry.engineLoad })) };
    const throttlePosSeries = { name: 'Throttle Position', data: formattedData.map(entry => ({ x: entry.x, y: entry.throttlePos })) };
    const fuelLevelSeries = { name: 'Fuel Level', data: formattedData.map(entry => ({ x: entry.x, y: entry.fuelLevel })) };
    const barometricPressureSeries = { name: 'Barometric Pressure', data: formattedData.map(entry => ({ x: entry.x, y: entry.barometricPressure })) };
    const runTimeSeries = { name: 'Run Time', data: formattedData.map(entry => ({ x: entry.x, y: entry.runTime })) };
    const distanceSeries = { name: 'Distance', data: formattedData.map(entry => ({ x: entry.x, y: entry.distance })) };
    const coolantTempSeries = { name: 'Coolant Temperature', data: formattedData.map(entry => ({ x: entry.x, y: entry.coolantTemp })) };


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
                    <input type="datetime-local" id="start" value={startTime} onChange={handleStartTimeChange}/>
                </div>
                <div className="to">TO</div>
                <div className="finish-time">
                    <input type="datetime-local" id="finish" value={finishTime} onChange={handleFinishTimeChange}/>
                </div>
            </div>

            <div className="body">
                <Chart options={options} series={[speedSeries]} type="area" height={350}/>
                <Chart options={rpm_options} series={[rpmSeries]} type="area" height={350}/>
                <Chart options={engine_load_options} series={[engineLoadSeries]} type="area" height={350}/>
                <Chart options={throttle_pos_options} series={[throttlePosSeries]} type="area" height={350}/>
                <Chart options={fuel_level_options} series={[fuelLevelSeries]} type="area" height={350}/>
                <Chart options={barometric_press_options} series={[barometricPressureSeries]} type="area" height={350}/>
                <Chart options={runTimeSeries} series={[run_time_options]} type="area" height={350}/>
                <Chart options={distance_options} series={[distanceSeries]} type="area" height={350}/>
                <Chart options={coolant_temp_options} series={[coolantTempSeries]} type="area" height={350}/>
            </div>
        </div>
    </div>

    );
}

export default GraphDashboard;