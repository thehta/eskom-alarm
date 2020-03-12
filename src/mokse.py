import eskomStatus
import mokse_led.py
import time

eskom_url = "http://loadshedding.eskom.co.za"
status_loc = "/LoadShedding/GetStatus"
url_glob = eskom_url + status_loc

while(1):
    if eskomStatus.getStatus(url_glob) == 200:
        print("Successful response 200")
        #print(eskomStatus.getLevel(url_glob))
        stage = eskomStatus.getLevel(url_glob)
        shine(stage-1)

    else:
        print("Error")
    time.sleep(1)