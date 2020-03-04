import requests
import RPi.GPIO as io
import time
from bs4 import BeautifulSoup as bs

io.setmode(io.BCM)
io.setwarnings(False)
io.setup(17, io.OUT)
io.setup(27, io.OUT)

while (1):
    j = 0
    try:
        r = requests.get("http://loadshedding.eskom.co.za")
        io.output(17, io.HIGH)
        time.sleep(0.5)
        io.output(17, io.OUT)
    except expression as identifier:
        time.sleep(0.5)
        io.output(27, io.HIGH)
        time.sleep(0.5)
        io.output(27, io.HIGH)
        time.sleep(0.5)
        io.output(27, io.HIGH)
        pass
    soup = bs(r.text, "html.parser")
    stageText = soup.find_all(id="lsstatus")[0].text
    stageNum = 0
    for i in range(0, len(stageText)):
        if (ord(stageText[i])>=49 and ord(stageText[i])<=60):
            stageNum = stageText[i]
        else:
            stageNum = 0
    if stageNum == 0:
        io.output(17, io.HIGH)
    else:
        io.output(17, io.LOW)
        io.output(27, io.HIGH)
    print(stageNum)
    #if j >= 1000:
        #print(stageNum)
    #    j = 0
