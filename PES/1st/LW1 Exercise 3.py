# main.py -- put your code here!
from machine import Pin
import time
import pyb


# create an output pin on pin LED1


# create an input pin on pin #SW with pull down resistor enabled
pin_D2 = Pin("D2", Pin.IN, Pin.PULL_UP)
pin_D3 = Pin("D3", Pin.IN, Pin.PULL_DOWN)
x=100 


while True:
    if pin_D2.value() == 0:
         #print("Button is not pressed")

        time.sleep_ms(500)
        if pin_D3.value() == 1:
            x=(x-2)
        else:
            x=x
            

        
        
    else:
        #print("Button pressed")
        
        #green_LED.low()
        time.sleep_ms(500)
        pyb.LED(1).toggle()
        #time.sleep_ms(500)
        if pin_D3.value() == 1:
            x=(x-2)
        else:
            x=x        
    print(x)


       