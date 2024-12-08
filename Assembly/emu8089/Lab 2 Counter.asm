.model small
.stack 100h
.data
    prompt db 0Dh, 0Ah, 'Enter a word: $'
    resultMsg db 0Dh, 0Ah, 'The number of letters in the word is: $'
    nameMsg db 'A', 'n', 'n', 'a', ' ', 'K', 'u', 't', 'o', 'v', 'a', '$' 
    ColumnnameMsg db 0Dh, 0Ah, 'Anna Kutova$'
    formatMsg db 0Dh, 0Ah, 'Enter C for column or L for line: $'
    buffer db 255, 0   ; Buffer for input: first byte is max length, second byte is actual length
    count db 0         ; Variable to store the count of letters
.code
start:
    mov ax, @data
    mov ds, ax

    ; Display prompt for entering a word
    mov ah, 9
    lea dx, prompt
    int 21h

    ; Read the word from the keyboard
    mov ah, 0Ah       ; DOS buffered input
    lea dx, buffer    ; Store input in buffer
    int 21h

    ; Start counting the letters
    mov si, offset buffer + 2 ; Point to the first character of the entered word
    mov cl, [buffer + 1]      ; Get the actual length of the input

    mov count, 0       ; Initialize count to 0

count_loop:
    cmp cl, 0          ; Check if all characters have been processed
    je display_result

    mov al, [si]       ; Get the current character
    cmp al, ' '        ; Check for space
    je skip_character   ; If it's a space, skip

    cmp al, '0'        ; Check for digits
    jb check_uppercase  ; If below '0', check uppercase
    cmp al, '9'
    jbe skip_character  ; If between '0' and '9', skip

check_uppercase:
    cmp al, 'A'        ; Check for uppercase letters
    cmp al, 'Z'
    jbe is_letter       ; If between 'A' and 'Z', it's a letter
    jmp check_lowercase ; If above 'Z', check lowercase

check_lowercase:
    cmp al, 'a'        ; Check for lowercase letters
    jb skip_character   ; If below 'a', skip
    cmp al, 'z'
    ja skip_character   ; If above 'z', skip

is_letter:
    inc count          ; Increment the letter count for valid letters

skip_character:
    inc si             ; Move to the next character
    dec cl
    jmp count_loop

display_result:
    ; Display the result message
    mov ah, 9
    lea dx, resultMsg
    int 21h

    ; Convert count to ASCII and display
    mov al, count
    aam                ; Convert to ASCII (if count > 9)
    add ax, 3030h      ; Adjust to ASCII values
    mov dl, ah         ; Display tens place
    cmp dl, '0'        ; If tens place is '0', don't print it
    je skip_tens
    mov ah, 2
    int 21h
skip_tens:
    mov dl, al         ; Display ones place
    mov ah, 2
    int 21h

    ; Ask for format input
    mov ah, 9
    lea dx, formatMsg
    int 21h

    mov ah, 1          ; Read single character
    int 21h
    cmp al, 'C'        ; Check if 'C' was entered
    je print_column
    cmp al, 'L'        ; Check if 'L' was entered
    je print_line

    ; If neither, exit
    jmp end_program

print_column:
    mov cl, count      ; Set CL to the number of repetitions
repeat_column:
    mov ah, 9
    lea dx, ColumnnameMsg
    int 21h
    dec cl             ; Decrease the repetition counter
    jnz repeat_column
    jmp end_program

print_line:
    ; Print a new line before starting to print names
    mov ah, 2          ; Prepare to print a new line
    mov dl, 0Dh        ; Carriage return
    int 21h
    mov dl, 0Ah        ; Line feed
    int 21h

    mov cl, count      ; Set CL to the number of repetitions
repeat_line:
    mov ah, 9
    lea dx, nameMsg
    int 21h
    dec cl             ; Decrease the repetition counter
    cmp cl, 0         ; Check if it's the last iteration
    je end_print       ; If last, jump to end_print
    ; Print space
    mov ah, 2          ; Print space
    mov dl, ' '
    int 21h
    jmp repeat_line

end_print:
    ; No need to print an extra space at the end

end_program:
    ; Terminate program
    mov ah, 4Ch
    int 21h

end start
