# main.py -- put your code here!
from machine import Pin
import time


# create an output pin on pin LED1
green_LED = Pin("LED1", Pin.OUT)

while True:
	green_LED.low()
	time.sleep_ms(500)
	green_LED.high()
	time.sleep_ms(500)
