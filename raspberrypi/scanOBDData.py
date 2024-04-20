import obd
from gps import *
import datetime

# 1초마다 OBD 데이터를 수집
# 타임스탬프도 찍기
# GPS도 보내주기 - 라이브러리의 파이썬 버전 호환 문제로 파일에 적어서 교환하는 식으로...
# VIN은 맨 처음에
# 제품 일련번호도 맨처음에

json_dict = {}  # 데이터를 저장할 변수 

connection = obd.OBD()  # 차량과 연결

VIN = str(connection.query(obd.commands.VIN).value.magnitude)  # 차량의 VIN

serialNumber = "F1234"  # 제품 일련번호









"""
now = datetime.datetime.now()
timestamp = now.strftime("%Y%m%d%H%M%S")
print(timestamp)

connection = obd.commands.SPEED

while True:
    response = connection.query(cmd)

    print(response.value)
"""
# 1초마다 OBD 데이터를 수집
# 타임스탬프도 찍기
# GPS도 보내주기
# VIN은 맨 처음에
# 제품 일련번호도 맨처음에