# main.py -- put your code here!
from machine import Pin
import time
import pyb

# create an output pin on pin LED1
green_LED = Pin("LED1", Pin.OUT)
# set led_green high
green_LED.high()
pyb.LED(1).off()

while True:
    pyb.LED(1).toggle()
    time.sleep_ms(500)
    pass
