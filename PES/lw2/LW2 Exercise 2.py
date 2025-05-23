# main.py -- put your code here!
from pyb import DAC
from machine import Pin
from machine import ADC
import time


# create DAC 2 on pin D13
dac = DAC(2)
pin_SW = Pin("SW", Pin.IN, Pin.PULL_DOWN)

# write a value to the DAC (makes D24 1.65V)
D24 = Pin("D24", Pin.ANALOG)
adc = ADC(D24)


#D24 is connected to A0 via wire
a=0

while True:
    if pin_SW.value() !=0:
        if (a >= 255):
            a = 0
        else:
            a += 15
            if (a > 255):
                a=0
            
    
    dac.write(int (a))
    val = adc.read_u16()
    
    
    
    #print value in 16 bit ADC range
    # print (val)
    print (a)
    
    #print value in Volts
    print ("Humidity = ",val*(100/65536),"%")
    time.sleep_ms(1000);
