import "./GraphDashboard.css";
import React, { useEffect, useState } from "react";
import Chart from "react-apexcharts";

const series = {
    monthDataSeries1: {
      prices: [8107.85, 8128.0, 8122.9, 8165.5, 8340.7, 8423.7, 8423.5, 8514.3, 8481.85, 8487.7, 8506.9, 8626.2, 8668.95, 8602.3, 8607.55, 8512.9, 8496.25, 8600.65, 8881.1, 9340.85],
      dates: [
        "2018-09-19T00:00:00.000Z", "2018-09-20T00:00:00.000Z", "2018-09-21T00:00:00.000Z", 
        "2018-09-22T00:00:00.000Z", "2018-09-23T00:00:00.000Z", "2018-09-24T00:00:00.000Z", 
        "2018-09-25T00:00:00.000Z", "2018-09-26T00:00:00.000Z", "2018-09-27T00:00:00.000Z", 
        "2018-09-28T00:00:00.000Z", "2018-09-29T00:00:00.000Z", "2018-09-30T00:00:00.000Z", 
        "2018-10-01T00:00:00.000Z", "2018-10-02T00:00:00.000Z", "2018-10-03T00:00:00.000Z", 
        "2018-10-04T00:00:00.000Z", "2018-10-05T00:00:00.000Z", "2018-10-06T00:00:00.000Z", 
        "2018-10-07T00:00:00.000Z", "2018-10-08T00:00:00.000Z"
      ]
    }
};

function GraphDashboard(props) {
    const downloadData = () => {
        // 다운로드 기능 구현
    };

    const [startDate, setStartDate] = useState();
    const [endDate, setEndDate] = useState();

    const [data, setData] = useState({});

    /* 랜더링될 때 마다 (= startDate/endDate가 변경될 때마다)
    fetchData 함수가 수행되도록 함. */
    useEffect(() => {
        fetchData();
    }, [startDate, endDate]);

    const fetchData = async () =>{
        if (!startDate || !endDate) return;

        try {
            // 데이터 가져오는 fetch 함수 구성해야 함.
            const response = await fetch();
            const result = await response.json();
            setData(result);
        } catch (error) {
            console.error("Error fetching data: ", error);
        }
    };

    const handleStartDate = date => {
        setStartDate(data);
    };

    const handleEndDate = date => {
        setEndDate(data);
    };

    const [startTime, setStartTime] = useState("");
    const [finishTime, setFinishTime] = useState("");


    const handleStartTimeChange = (e) => {
        setStartTime(e.target.value);
    };

    const handleFinishTimeChange = (e) => {
        setFinishTime(e.target.value);
    };

    useEffect(() => {
        // 서버에 데이터 요청하는 코드
    }, []);

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
        labels: series.monthDataSeries1.dates,
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

    const seriesData = [{
        name: "STOCK ABC",
        data: series.monthDataSeries1.prices
      }];



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
                    <input type="datetime-local" id="start" value={startTime} onChange={handleStartTimeChange}/>
                </div>
                <div className="to">TO</div>
                <div className="finish-time">
                    <input type="datetime-local" id="finish" value={finishTime} onChange={handleFinishTimeChange}/>
                </div>
            </div>

            <div className="body">
                <Chart
                    options={options}
                    series={seriesData}
                    type="area"
                    height={350}
                />

                <Chart
                    options={rpm_options}
                    series={seriesData}
                    type="area"
                    height={350}
                />

                <Chart
                    options={throttle_pos_options}
                    series={seriesData}
                    type="area"
                    height={350}
                />

                <Chart
                    options={engine_load_options}
                    series={seriesData}
                    type="area"
                    height={350}
                />

                <Chart
                    options={oil_temp_options}
                    series={seriesData}
                    type="area"
                    height={350}
                />

                <Chart
                    options={intake_press_options}
                    series={seriesData}
                    type="area"
                    height={350}
                />

                <Chart
                    options={intake_temp_options}
                    series={seriesData}
                    type="area"
                    height={350}
                />
                    
            </div>
            
        </div>
    </div>

    );
}

export default GraphDashboard;