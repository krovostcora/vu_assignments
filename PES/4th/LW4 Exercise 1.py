from pyb import USB_VCP

vcp = USB_VCP()

while True:
    cmd = vcp.recv(1, timeout=5000)
    if (cmd == b'm'):
        vcp.send("message received!!!! \r\n", timeout=5000)