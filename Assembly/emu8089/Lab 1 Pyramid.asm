.model small
.stack 100h
.data
    fullName db 65, 110, 110, 97, 75, 117, 116, 111, 118, 97, 0Dh, 0Ah, '$' ; Full name as ASCII
.code
start:
    mov ax, @data
    mov ds, ax
    
    ; Line 1: Print the full name from column 1, line 5
    mov ah, 02h        ; Function to move cursor
    mov bh, 0          ; Page number 0
    mov dh, 5          ; Line 1
    mov dl, 0          ; Column 1
    int 10h            ; Move cursor
    mov ah, 09h        ; Function to print string
    lea dx, fullName   ; Print the full name using ASCII
    int 21h            ; DOS interrupt
    
    ; Line 2: Print "nnaKuto" from column 2, line 4
    mov ah, 02h        ; Move cursor
    mov dh, 4          ; Line 2
    mov dl, 1          ; Column 2
    int 10h            ; Move cursor
    mov ah, 09h
    lea dx, fullName + 1 ; Starting from the second character
    mov byte ptr fullName + 9, '$'  ; Set end of string to truncate after "nna Kuto"
    int 21h
    
    ; Line 3: Print "naKuto" from column 3, line 3
    mov ah, 02h
    mov dh, 3
    mov dl, 2
    int 10h
    mov ah, 09h
    lea dx, fullName + 2 ; Starting from the third character
    mov byte ptr fullName + 8, '$'  ; Truncate after "naKuto"
    int 21h
    
    ; Line 4: Print "a Kut" from column 4, line 2
    mov ah, 02h
    mov dh, 2
    mov dl, 3
    int 10h
    mov ah, 09h
    lea dx, fullName + 3 ; Starting from the fourth character
    mov byte ptr fullName + 7, '$'  ; Truncate after "aKut"
    int 21h
    
    ; Line 5: Print "Ku" from column 5, line 1
    mov ah, 02h
    mov dh, 1
    mov dl, 4
    int 10h
    mov ah, 09h
    lea dx, fullName + 4 ; Starting from 'K'
    mov byte ptr fullName + 6, '$'  ; Truncate after "Ku"
    int 21h
    
    ; End the program
    mov ah, 4Ch
    int 21h
end start
