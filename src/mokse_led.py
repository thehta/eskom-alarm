import RPi.GPIO as io
import time




def shine(stage):
    # create matrix device
    serial = spi(port=0, device=0, gpio=noop())
    device = max7219(serial, cascaded=n or 1, block_orientation=block_orientation,
                     rotate=rotate or 0, blocks_arranged_in_reverse_order=inreverse)
    print("Created device")

    # start demo
    msg = "MAX7219 LED Matrix Demo"
    print(msg)
    show_message(device, msg, fill="white", font=proportional(CP437_FONT))
    time.sleep(1)

#    stages[stage]()
""" 
stages = {
    1 : none,
    2 : stageOne,
    3 : stageTwo,
    4 : stageThree,
    5 : stageFour,
    6 : stageFive,
    7 : stageSix,
    8 : stageSeven,
    9 : stageEight
}

def stageOne:
    print()

def stageTwo:
    print()
    
def stageThree:
    print()

def stageFour:
    print()

def stageFive:
    print()

def stageSix:
    print()

def stageSeven:
    print()

def stageEight:
    print()

def none:
 """