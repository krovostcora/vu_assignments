from pyb import Pin, Timer

# LED2 is on TIM4, CH2
p = Pin("LED2")

# Set TIM4 frequency to 6000Hz
tim = Timer(4, freq=6000)

# Configure timer in PWM mode on channel 2
ch = tim.channel(2, Timer.PWM, pin=p)

# Initial duty cycle
brightness = 0
step = 5  # Brightness change step

def fade_led(timer):
    global brightness, step
    brightness += step
    if brightness >= 100 or brightness <= 0:
        step = -step  # Reverse direction
    ch.pulse_width_percent(brightness)

# Use timer interrupt to change brightness
fade_timer = Timer(5)  # Create Timer 5
fade_timer.init(freq=20, callback=fade_led)  # 20Hz → period 50ms

while True:
    pass
