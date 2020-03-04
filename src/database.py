import pandas as panda
from pandas import ExcelWriter
from pandas import ExcelFile

import openpyxl

# Immutables
doc_loc = "loadshedding.eskom.co.za/loaddocs/"
extension = ".xlsx"

provinces = {
    "wc" : "WesternCape",
    "ec" : "EasternCape",
    "nc" : "NorthernCape",
    "nw" : "NorthWest",
    "fs" : "FreeState",
    "kn" : "Kwazulu-Natal",
    "ga" : "Gauteng",
    "mp" : "Mpumalanga",
    "lp" : "Limpopo"
}



excel_document = openpyxl.load_workbook('WesternCape_LS.xlsx')
print(excel_document.get_sheet_names())
sheet = excel_document.get_sheet_by_name('MP_List')
print(sheet['A8'].value)
#print(provinces)
#df = panda.read_excel(provinces['wc']+"_LS" + extension)
