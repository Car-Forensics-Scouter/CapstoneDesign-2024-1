# 하기 전에 소켓 켜야함!!!

from gps import *
import time

gpsd = gps(mode=WATCH_ENABLE|WATCH_NEWSTYLE)

file_path = "./gps_log.txt"

while True:
    report = gpsd.next()
    if report["class"] == "TPV":
        lat = getattr(report, "lat", 0.0)  # 문자열로의 변환이 필요할 수 있음
        lon = getattr(report, "lon", 0.0)
    with open(file_path, "w") as file:
        file.write(lat + "\n" + lon)
    time.sleep(1)