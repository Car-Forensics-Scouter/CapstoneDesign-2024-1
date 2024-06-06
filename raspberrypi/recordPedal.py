import picamera
import time
import obd
import requests

connection = obd.OBD("/dev/ttyUSB_DEV1")  # 차량과 연결

# 녹화할 동영상 파일명
output_file = "video.h264"

response = requests.get("http://localhost:8080/API_PATH")
url = response

# 카메라 초기화
camera = picamera.PiCamera()

class EngineOff(Exception):
    pass


try:
    # 녹화 시작
    camera.start_recording(output_file)

    # 무한 루프
    while True:
        time.sleep(1)  # CPU 자원을 낭비하지 않기 위해 대기
        if(connection.status() != obd.OBDStatus.CAR_CONNECTED):
            raise EngineOff

except EngineOff:
    camera.stop_recording()
    camera.close()
    files = { "file": open("video.h264", "rb") }
    reponse = requests.post(url, files=files)

except KeyboardInterrupt:
    # 사용자가 Ctrl+C를 눌러 프로그램을 종료한 경우
    camera.stop_recording()
    camera.close()