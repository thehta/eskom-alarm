import eskomStatus

eskom_url = "http://loadshedding.eskom.co.za"
status_loc = "/LoadShedding/GetStatus"
url_glob = eskom_url + status_loc

if eskomStatus.getStatus(url_glob) == 200:
    print("Successful response 200")
    print(eskomStatus.getLevel(url_glob))
    stage = eskomStatus.getLevel(url_glob)
    shine()

else:
    print("Error")
