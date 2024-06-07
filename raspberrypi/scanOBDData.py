import obd
import datetime
import time
import json
import requests

connection = obd.OBD("/dev/ttyUSB_DEV1")  # 차량과 연결

VIN = str(connection.query(obd.commands.VIN).value)  # 차량의 VIN

serialNumber = "F1234"  # 제품 일련번호

file_path = "./gps_log.txt"

while True:
    json_dict = []
    for i in range(10):
        now = datetime.datetime.now()
        timestamp = now.strftime("%Y-%m-%dT%H:%M:%S")
        with open(file_path, "r") as file:
            lat = file.readline().rstrip('\n')
            lon = file.readline()
        SPEED = str(connection.query(obd.commands.SPEED).value.magnitude)
        RPM = str(connection.query(obd.commands.RPM).value.magnitude)
        ENGINELOAD = str(connection.query(obd.commands.ENGINE_LOAD).value.magnitude)
        FUELLEVEL = str(connection.query(obd.commands.FUEL_LEVEL).value.magnitude)
        COOLANTTEMP = str(connection.query(obd.commands.COOLANT_TEMP).value.magnitude)
        THROTTLEPOSITION = str(connection.query(obd.commands.THROTTLE_POS).value.magnitude)
        RUNTIME = str(connection.query(obd.commands.RUN_TIME).value.magnitude)
        BAROMETRIC = str(connection.query(obd.commands.BAROMETRIC_PRESSURE).value.magnitude)

        json_dict.append({
            "deviceId": serialNumber,
            "timeStamp": timestamp,
            "vin": VIN,
            "speed": SPEED,
            "rpm": RPM,
            "engineLoad": ENGINELOAD,
            "fuelLevel": FUELLEVEL,
            "barometricPressure": BAROMETRIC,
            "coolantTemp": COOLANTTEMP,
            "throttlePos": THROTTLEPOSITION,
            "runTime": RUNTIME,
            "lat": lat,
            "lon": lon
        })

        time.sleep(1)

    url = "http://localhost:8080/api/obdlog/save"

    response = requests.post(url, json=json_dict)

    print(response.content.decode("utf-8"))

