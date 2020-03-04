import requests
import json

def getStatus(url):
    return (requests.get(url)).status_code

def getLevel(url):
    return response(url).json()

def response(url):
    return requests.get(url)