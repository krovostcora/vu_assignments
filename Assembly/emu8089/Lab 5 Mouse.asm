.model tiny

org 100h
.data
message db 'Anna Kutova $'

.code
start:
    ; Set video mode to 0x13 (graphic mode)
    mov     ax, 13h
    int     10h

    ; Initialize mouse (AX = 0)
    xor     ax, ax
    int     33h

    ; Show mouse pointer
    mov     ax, 1
    int     33h

    ; Check for mouse click inside a specific area
check_mouse:
    ; Get mouse position and button state (AX = 3)
    mov     ax, 3
    int     33h
    ; CX = X coordinate, DX = Y coordinate 
    
    ; Compare mouse coordinates with the area 
    cmp     cx, 00           ; If x >= 0 
    jl      check_mouse
    cmp     cx, 260           ; If x <= 260
    jg      check_mouse
    cmp     dx, 150           ; If y >= 150 
    jl      check_mouse
    cmp     dx, 160           ; If y <= 160
    jg      check_mouse      
    
    
    ; Check if the left button (BX = 1) is pressed
    cmp     bx, 1
    jne     check_mouse

    ; If inside area and left mouse button clicked, switch to text mode and display text
    mov     ax, 03h           ; Set text mode (mode 0x03)
    int     10h

    ; Display message in text mode
    mov ah, 02h           ; Set cursor position
    mov dh, 8          ; Line 8
    mov dl, 1          ; Column 1
    int     10h

    mov  ah, 09h
    mov  dx, offset message
    int  21h

    ; Continue checking mouse position
    jmp     check_mouse

buf:    ; Buffer for storing mouse event data
    pusha
    popa
    retf
end start
