import picamera
import time
import obd
import requests
import datetime

connection = obd.OBD("/dev/ttyUSB_DEV1")  # 차량과 연결

# 녹화할 동영상 파일명
output_file = "video.h264"
deviceId = "F1234"
extension = "h264"
file_name = "video"

now = datetime.datetime.now()
createdDate = now.strftime("%Y-%m-%dT%H:%M:%S")

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

    now = datetime.datetime.now()
    endDate = now.strftime("%Y-%m-%dT%H:%M:%S")

    url = f"http://localhost:8080/video/URL-upload?deviceId={deviceId}&fileName={file_name}&extension={extension}&createdDate={createdDate}&endDate={endDate}"
    urlResponse = requests.get(url)
    uploadUrl = urlResponse.content

    print(f"URL: {uploadUrl}")

    files = { "file": open(output_file, "rb") }
    response = requests.put(url, files=files, verify=False)
    print(response)

except KeyboardInterrupt:
    # 사용자가 Ctrl+C를 눌러 프로그램을 종료한 경우
    camera.stop_recording()
    camera.close()