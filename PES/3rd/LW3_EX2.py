from pyb import Pin, Timer


# LED1 is on  TIM3, CH3
p = Pin("LED1")
# set TIM3 frequecy to 1000Hz
tim = Timer(3, freq=1000)
# configure timer in PWM mode on channel 3
ch = tim.channel(3, Timer.PWM, pin=p)
# set duty to 50%
ch.pulse_width_percent(25)

while True:
	pass
