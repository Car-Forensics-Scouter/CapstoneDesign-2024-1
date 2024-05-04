import obd
import datetime
import time

# 1초마다 OBD 데이터를 수집
# 타임스탬프도 찍기
# GPS도 보내주기 - 라이브러리의 파이썬 버전 호환 문제로 파일에 적어서 교환하는 식으로...
# VIN은 맨 처음에
# 제품 일련번호도 맨처음에
# 동영상 전송 부분도 구현해야 함

connection = obd.OBD("/dev/ttyUSB2")  # 차량과 연결

VIN = str(connection.query(obd.commands.VIN).value)  # 차량의 VIN

serialNumber = "F1234"  # 제품 일련번호

file_path = "./gps_log.txt"

while True:
    json_dict = {
        "VIN" : VIN,
        "serialNumber" : serialNumber
    }
    for i in range(60):
        now = datetime.datetime.now()
        timestamp = now.strftime("%Y%m%d%H%M%S")
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

        key = "partition" + str(i + 1)

        json_dict[key] = {
            "timestamp" : timestamp,
            "SPEED" : SPEED,
            "RPM" : RPM,
            "COOLANTTEMP" : COOLANTTEMP,
            "THROTTLEPOSITION" : THROTTLEPOSITION,
            "RUNTIME" : RUNTIME,
            "BAROMETRIC" : BAROMETRIC,
            "gps_lat" : lat,
            "gps_lon" : lon
        }

        time.sleep(1)
    
    # 전송 부분 구현
    print(json_dict)  # test



# DISTANCE는 백엔드에서 처리
