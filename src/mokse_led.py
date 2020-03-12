import RPi.GPIO as io
import time


from luma.led_matrix.device import max7219
from luma.core.interface.serial import spi, noop
from luma.core.render import canvas
from luma.core.virtual import viewport
from luma.core.legacy import text, show_message
from luma.core.legacy.font import proportional, CP437_FONT, TINY_FONT, SINCLAIR_FONT, LCD_FONT


def shine(stage):
    # create matrix device
    serial = spi(port=0, device=0, gpio=noop())
    device = max7219(serial, cascaded=1, block_orientation=0,
                     rotate=0, blocks_arranged_in_reverse_order=0)
    #print("Created device")

    # start demo
    #msg = "MAX7219 LED Matrix Demo"
    msg =str(stage-1) 
    print(msg)
    show_message(device, msg, fill="white", font=proportional(CP437_FONT))
    time.sleep(1)
    while(1):
#        print(msg)
#        show_message(device, msg, fill="white", font=proportional(CP437_FONT))
#        time.sleep(1)
        time.sleep(1)
        with canvas(device) as draw:
            text(draw, (0, 0), msg, fill="white")
        time.sleep(1)
        for _ in range(5):
            for intensity in range(16):
                device.contrast(intensity * 16)
                time.sleep(0.1)
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