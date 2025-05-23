from pyb import USB_VCP, Pin, Timer

# Initialize button SW
pin_SW = Pin("SW", Pin.IN, Pin.PULL_DOWN)

#-------- LED_A (on/off control)
vcp = USB_VCP()
blue_light = Pin("LED2", Pin.OUT)  # LED_A is LED2

#-------- LED_B (PWM control on LED1)
p = Pin("LED1")  # LED_B is LED1

# Set TIM3 frequency to 6000Hz
tim = Timer(3, freq=6000)

# Configure PWM on Channel 3
ch = tim.channel(3, Timer.PWM, pin=p)

# Initial PWM duty cycle
brightness = 0   # Start at 0%
step = 15         # Step for manual brightness change

def fade_led(timer):
    global brightness, step
    brightness += step
    if brightness >= 100 or brightness <= 0:
        step = -step  # Reverse direction
    ch.pulse_width_percent(brightness)

# Use timer interrupt to change brightness
fade_timer = Timer(5)
fade_timer.init(freq=20, callback=fade_led)  # 20Hz → period 50ms

# Initialize the button state
prev_state = pin_SW.value()

while True:
    cmd = vcp.recv(1, timeout=5000)  # Expecting 1-character commands
    if cmd:  # Check if cmd is not None
        if cmd == b'1':  # Turn LED_A ON
            blue_light.high()
            vcp.send("So bright! \r\n", timeout=5000)
        elif cmd == b'n':  # Turn LED_A OFF
            blue_light.low()
            vcp.send("It's dark in here( \r\n", timeout=5000)
        elif cmd == b'u':  # Increase LED_B brightness
            if brightness + step <= 100:
                brightness += step
            ch.pulse_width_perrcent(brightness)
            vcp.send(f"Brightness: {brightness}%\r\n", timeout=5000)
        elif cmd == b'd':  # Decrease LED_B brightness
            if brightness - step >= 0:
                brightness -= step
            ch.pulse_width_percent(brightness)
            vcp.send(f"Brightness: {brightness}%\r\n", timeout=5000)
        elif cmd == b's':  # Get button state
            state = "PRESSED" if pin_SW.value() else "RELEASED"
            vcp.send(f"Button state: {state}\r\n", timeout=5000)

    # Detect button state change
    current_state = pin_SW.value()
    if current_state != prev_state:
        if current_state:
            print("BUTTON PRESSED")
        else:
            print("BUTTON RELEASED")
        prev_state = current_state
