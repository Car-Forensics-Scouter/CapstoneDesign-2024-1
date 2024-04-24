import picamera
import time

# 녹화할 동영상 파일명
output_file = 'video.h264'

# 카메라 초기화
camera = picamera.PiCamera()

# 전원 감지 콜백 함수
def power_off_callback(channel):
    print("전원이 꺼집니다. 녹화를 중지합니다.")
    camera.stop_recording()
    camera.close()
    GPIO.cleanup()
    exit()

# 위와 비슷한 별도의 중지 매커니즘이 필요함

try:
    # 녹화 시작
    camera.start_recording(output_file)

    # 무한 루프
    while True:
        time.sleep(1)  # CPU 자원을 낭비하지 않기 위해 대기

except KeyboardInterrupt:
    # 사용자가 Ctrl+C를 눌러 프로그램을 종료한 경우
    camera.stop_recording()
    camera.close()
    GPIO.cleanup()