
org  100h

jmp start

file1 db "c:\emu8086\vdrive\C\file1.txt", 0
file2 db "c:\emu8086\vdrive\C\file2.txt", 0
file3 db "c:\emu8086\vdrive\C\file3.txt", 0
handle dw ?


; Prepare text to display filenames
msg1 db "1: file1.txt $", 0
msg2 db "2: file2.txt $", 0
msg3 db "3: file3.txt $", 0

; Prepare additional messages
header db "Free to change files: $", 0
footer db "Choose a file number to open it (1/2/3) :$", 0
file_name db "file1.txt", 0   ; Name of the file to open
buf db 256 dup(0)              ; Buffer for file content
message db 0dh, 0ah, ' $'                             
msg_n db "To display the first N lines of the file, enter N: $", 0 ; Message for user input
message_error db "Oops.., something went wrong $",0

replace_message db "Choose a file number to modify it (1/2/3):$", 0
    prompt db "Enter a character to replace: $"
    replacement db "Anna", 0
subdirectory db "C:\emu8086\vdrive\C\new_folder\", 0  
    new_file db "C:\emu8086\vdrive\C\new_folder\file2_corrected.txt", 0 
    success_message db "File successfully copied to the new folder.$", 0       

count_message db "Choose a file number to count the number of words in it (1/2/3):$", 0    
  prompt2 db "The total number of words is: $"
    word_count dw 0                             ; Variable to store word count



start:
mov ax, cs
;mov dx, ax
mov es, ax


; Display header
mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt
mov dx, offset header ; Load address of header
mov ah, 09h          ; DOS service to display string
int 21h              ; Call DOS interrupt
mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt

; Display name of file 1
mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt
mov dx, offset msg1  ; Load address of msg1
mov ah, 09h          ; DOS service to display string
int 21h              ; Call DOS interrupt


; Display name of file 2
mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt
mov dx, offset msg2  ; Load address of msg2
mov ah, 09h          ; DOS service to display string
int 21h              ; Call DOS interrupt


; Display name of file 3
mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt
mov dx, offset msg3  ; Load address of msg3
mov ah, 09h          ; DOS service to display string
int 21h              ; Call DOS interrupt

; Display question

choose_file:
mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt
mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt
mov dx, offset footer ; Load address of footer
mov ah, 09h          ; DOS service to display string
int 21h              ; Call DOS interrupt

             ; Call DOS interrupt

; Wait for user input

mov ah, 01h          ; DOS service to read character
int 21h              ; Call DOS interrupt
 
 

; Compare input directly with ASCII values for '1', '2', '3'
cmp al, '1'          ; Compare with '1'
je modify_file1
cmp al, '2'          ; Compare with '2'
je modify_file2
cmp al, '3'          ; Compare with '3'
je modify_file3

jmp invalid_input     ; If not valid input, jump to error handling

modify_file1:
mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt
    mov ah, 3Dh                ; Open file function
    mov al, 0                  ; Open for reading
    lea dx, file1              ; Load address of file_name
    int 21h
    mov bx, ax                 ; Store file handle in bx

    mov ah, 3Fh                ; Read file function
    lea dx, buf                ; Load address of buffer
    mov cx, 255                ; Number of bytes to read
    int 21h

    ; Check if any bytes were read
    cmp ax, 0
    je close_file              ; If no bytes read, close the file

    ; Display the content read from the file
    mov ah, 09h                ; Function to display a string
    lea dx, buf                ; Load address of buffer
    int 21h                    ; Display the content 
    
    mov cx, 256           ; Set CX to the size of the buffer (256 bytes)
    lea di, buf           ; Load the address of the buffer into DI
    xor al, al            ; Set AL to 0 (value to fill the buffer with)
    rep stosb     
    
    mov ah, 02h          ; DOS service to write character
    mov dl, 0Dh          ; Carriage Return
    int 21h              ; Call DOS interrupt
    mov dl, 0Ah          ; Line Feed
    int 21h              ; Call DOS interrupt
    mov ah, 3Dh                ; Open file function
    mov al, 0                  ; Open for reading only
    lea dx, file1              ; Load address of file1
    int 21h
    jc err_open                ; If error, jump to error handling
    mov bx, ax                 ; Store file handle in BX
    
    jmp read_file   
    


modify_file2:
mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt
    mov ah, 3Dh                ; Open file function
    mov al, 0                  ; Open for reading
    lea dx, file2              ; Load address of file_name
    int 21h
    mov bx, ax                 ; Store file handle in bx

    mov ah, 3Fh                ; Read file function
    lea dx, buf                ; Load address of buffer
    mov cx, 255                ; Number of bytes to read
    int 21h

    ; Check if any bytes were read
    cmp ax, 0
    je close_file              ; If no bytes read, close the file

    ; Display the content read from the file
    mov ah, 09h                ; Function to display a string
    lea dx, buf                ; Load address of buffer
    int 21h                    ; Display the content
    
    mov cx, 256           ; Set CX to the size of the buffer (256 bytes)
    lea di, buf           ; Load the address of the buffer into DI
    xor al, al            ; Set AL to 0 (value to fill the buffer with)
    rep stosb   
    
    mov ah, 02h          ; DOS service to write character
    mov dl, 0Dh          ; Carriage Return
    int 21h              ; Call DOS interrupt
    mov dl, 0Ah          ; Line Feed
    int 21h              ; Call DOS interrupt
    mov ah, 3Dh                ; Open file function
    mov al, 0                  ; Open for reading only
    lea dx, file2              ; Load address of file1
    int 21h
    jc err_open                ; If error, jump to error handling
    mov bx, ax                 ; Store file handle in BX
    
jmp read_file


modify_file3:
mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt
    mov ah, 3Dh                ; Open file function
    mov al, 0                  ; Open for reading
    lea dx, file3              ; Load address of file_name
    int 21h
    mov bx, ax                 ; Store file handle in bx

    mov ah, 3Fh                ; Read file function
    lea dx, buf                ; Load address of buffer
    mov cx, 255                ; Number of bytes to read
    int 21h

    ; Check if any bytes were read
    cmp ax, 0
    je close_file              ; If no bytes read, close the file

    ; Display the content read from the file
    mov ah, 09h                ; Function to display a string
    lea dx, buf                ; Load address of buffer
    int 21h                    ; Display the content  
    
    mov cx, 256           ; Set CX to the size of the buffer (256 bytes)
    lea di, buf           ; Load the address of the buffer into DI
    xor al, al            ; Set AL to 0 (value to fill the buffer with)
    rep stosb     
    
    mov ah, 02h          ; DOS service to write character
    mov dl, 0Dh          ; Carriage Return
    int 21h              ; Call DOS interrupt
    mov dl, 0Ah          ; Line Feed
    int 21h              ; Call DOS interrupt
    mov ah, 3Dh                ; Open file function
    mov al, 0                  ; Open for reading only
    lea dx, file3              ; Load address of file1
    int 21h
    jc err_open                ; If error, jump to error handling
    mov bx, ax                 ; Store file handle in BX
    
    jmp read_file

read_file:


    ; Clear the buffer
    mov cx, 256
    lea di, buf
    xor al, al
    rep stosb

    ; Read file content into buffer
    mov ah, 3Fh                ; Read file function
    lea dx, buf                ; Load address of buffer
    mov cx, 255                ; Number of bytes to read
    int 21h
    jc err_read                ; If error, jump to error handling
    mov si, offset buf         ; Start processing the buffer

    ; Display message to ask for N
    mov ah, 09h
    lea dx, msg_n              ; Display "To display the first N lines..."
    int 21h
    

    ; Read the number N
    mov ah, 01h                ; Read character from keyboard
    int 21h
    sub al, '0'                ; Convert ASCII to numeric
    mov cl, al                 ; Store N in CL

    ; Display the first N lines
    mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt

display_lines:
    cmp cl, 0                  ; Check if we reached N lines
    je choose_file_for_replacement              ; If yes, close the file
    lodsb                      ; Load next byte from buffer
    cmp al, 0                  ; Check for end of buffer
    je choose_file_for_replacement              ; If end of buffer, close the file
    cmp al, 0Dh                ; Check for Carriage Return (new line)
    jne write_char             ; If not a new line, write character
    dec cl                     ; Decrement line count for each new line

write_char:
    mov ah, 02h                ; DOS service to write character
    mov dl, al                 ; Load character to print
    int 21h                    ; Print character
    jmp display_lines          ; Loop to process the next character

    
         

;********** REPLACEMENT ********************* 
    
choose_file_for_replacement:
mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt
mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt
mov dx, offset replace_message ; Load address of footer
mov ah, 09h          ; DOS service to display string
int 21h              ; Call DOS interrupt

             ; Call DOS interrupt

; Wait for user input

mov ah, 01h          ; DOS service to read character
int 21h              ; Call DOS interrupt
 
 

; Compare input directly with ASCII values for '1', '2', '3'
cmp al, '1'          ; Compare with '1'
je replace_file1
cmp al, '2'          ; Compare with '2'
je replace_file2
cmp al, '3'          ; Compare with '3'      
je replace_file3

replace_file1:

; Initialize data segment
    mov ax, @data
    mov ds, ax

    ; Open the file
    mov ah, 3Dh                ; Open file function
    mov al, 0                  ; Open for reading
    lea dx, file1              ; Load address of file name
    int 21h
    jc file_error              ; If error, jump to error handling
    mov bx, ax                 ; Store file handle in BX
    jmp read_into_buffer
    
replace_file2:

; Initialize data segment
    mov ax, @data
    mov ds, ax

    ; Open the file
    mov ah, 3Dh                ; Open file function
    mov al, 0                  ; Open for reading
    lea dx, file2              ; Load address of file name
    int 21h
    jc file_error              ; If error, jump to error handling
    mov bx, ax                 ; Store file handle in BX
    jmp read_into_buffer
    
    
replace_file3:

; Initialize data segment
    mov ax, @data
    mov ds, ax

    ; Open the file
    mov ah, 3Dh                ; Open file function
    mov al, 0                  ; Open for reading
    lea dx, file3              ; Load address of file name
    int 21h
    jc file_error              ; If error, jump to error handling
    mov bx, ax                 ; Store file handle in BX
    jmp read_into_buffer


read_into_buffer:
  ; Read the file into the buffer
    mov ah, 3Fh                ; Read file function
    lea dx, buf                ; Load address of buffer
    mov cx, 255                ; Number of bytes to read
    int 21h
    jc file_error              ; If error, jump to error handling
    mov si, offset buf         ; SI points to the buffer

    lea dx, subdirectory       ; Create subdirectory
mov ah, 39h
int 21h

lea dx, new_file           ; Create new file
mov ah, 3Ch
mov al, 0
int 21h
jc file_error
mov bx, ax

    ; Prompt user to enter the character to replace
    mov dx, offset prompt
    mov ah, 9
    int 21h

    ; Read the character from the user
    mov ah, 1
    int 21h
    mov bl, al ; Store user input in BL

    ; Process the buffer
process_message:
    mov al, [si]           ; Load current character
    cmp al, 0              ; End of buffer?
    je write_to_file         ; If yes, exit
    cmp al, bl             ; Match user-specified character?
    jne skip_replacement   ; If not, skip replacement

    ; Replace character with "Anna"
    mov di, offset replacement ; DI points to replacement string

output_replacement:
    mov al, [di]           ; Load current character from replacement
    cmp al, 0              ; End of replacement string?
    je continue_processing ; If yes, continue processing buffer
    mov dl, al             ; Output current character
    mov ah, 2
    int 21h
    inc di                 ; Move to next character in replacement
    jmp output_replacement ; Repeat loop

skip_replacement:
    ; Display the current character
    mov dl, al
    mov ah, 2
    int 21h

continue_processing:
    inc si                 ; Move to the next character in buffer
    jmp process_message    ; Repeat loop

write_to_file:
    lea dx, buf        ; ?????? ??????
    mov ah, 40h        ; ??????? ??????
    int 21h
    jc file_error      ; ????????? ?? ???????

    ; ?????????? ????
    mov ah, 3Eh
    int 21h

    ; ????????? ???????????? ??? ?????
    lea dx, success_message
    mov ah, 9
    int 21h

    jmp choose_file_for_count


file_error:
    ; Display error message
    mov dx, offset message_error
    mov ah, 9
    int 21h
    jmp end_program

;********** COUNT ********************* 
    
choose_file_for_count:
mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt
mov ah, 02h          ; DOS service to write character
mov dl, 0Dh          ; Carriage Return
int 21h              ; Call DOS interrupt
mov dl, 0Ah          ; Line Feed
int 21h              ; Call DOS interrupt
mov dx, offset count_message ; Load address of footer
mov ah, 09h          ; DOS service to display string
int 21h              ; Call DOS interrupt

             ; Call DOS interrupt

; Wait for user input

mov ah, 01h          ; DOS service to read character
int 21h              ; Call DOS interrupt
 
 

; Compare input directly with ASCII values for '1', '2', '3'
cmp al, '1'          ; Compare with '1'
je count_file1
cmp al, '2'          ; Compare with '2'
je count_file2
cmp al, '3'          ; Compare with '3'      
je count_file3

count_file1:
; Initialize data segment
    mov ax, @data
    mov ds, ax

    ; Open the file
    mov ah, 3Dh                ; Open file function
    mov al, 0                  ; Open for reading
    lea dx, file1              ; Load address of file name
    int 21h
    jc file_error              ; If error, jump to error handling
    mov bx, ax                 ; Store file handle in BX
	jmp read_to_buffer_again

count_file2:
; Initialize data segment
    mov ax, @data
    mov ds, ax

    ; Open the file
    mov ah, 3Dh                ; Open file function
    mov al, 0                  ; Open for reading
    lea dx, file2              ; Load address of file name
    int 21h
    jc file_error              ; If error, jump to error handling
    mov bx, ax                 ; Store file handle in BX
	jmp read_to_buffer_again

count_file3:
; Initialize data segment
    mov ax, @data
    mov ds, ax

    ; Open the file
    mov ah, 3Dh                ; Open file function
    mov al, 0                  ; Open for reading
    lea dx, file3              ; Load address of file name
    int 21h
    jc file_error              ; If error, jump to error handling
    mov bx, ax                 ; Store file handle in BX
	jmp read_to_buffer_again

read_to_buffer_again:
    mov ah, 3Fh                ; Read file function
    lea dx, buf                ; Load address of buffer
    mov cx, 255                ; Number of bytes to read
    int 21h
    jc file_error              ; If error, jump to error handling
    mov si, offset buf         ; SI points to the buffer

    ; Initialize word count
    mov cx, 0                  ; CX will store the count of spaces

    ; Process the buffer
process_message2:
    mov al, [si]               ; Load current character
    cmp al, 0                  ; End of buffer?
    je calculate_words         ; If yes, calculate words
    cmp al, ' '                ; Check if character is a space
    jne skip_space             ; If not a space, skip

    inc cx                     ; Increment space count

skip_space:
    inc si                     ; Move to the next character in buffer
    jmp process_message2        ; Repeat loop

calculate_words:
    mov ax, cx                 ; Load space count into AX
    inc ax                     ; Add 1 to count words (spaces + 1)
    mov word_count, ax         ; Store result in word_count

    ; Display the result
    mov dx, offset prompt      ; Display prompt
    mov ah, 9
    int 21h

    ; Convert word count to string and display
    mov ax, word_count         ; Load word count
    call print_number          ; Call helper function to print number

    jmp end_program

    
close_file:
    mov ah, 3Eh                ; Close file function
    int 21h                    ; Close the file
    jmp end_program            ; Jump to end program
    
 


invalid_input:
; Handle invalid input (optional)

end_program:
    ; Exit program
    mov ax, 4C00h
    int 21h

err_open:
    ; Handle file open error
    mov ah, 09h
    mov si, offset message_error
    int 21h
    jmp choose_file

err_read:
    ; Handle file read error
    mov ah, 09h
    mov si, offset message_error
    int 21h
    jmp choose_file              
    
; Helper function to print a number
print_number proc
    mov bx, 10                 ; Divisor for decimal
    xor cx, cx                 ; Clear CX (to store digits)

convert_loop:
    xor dx, dx                 ; Clear DX for division
    div bx                     ; Divide AX by 10
    push dx                    ; Store remainder (digit) on stack
    inc cx                     ; Increment digit count
    cmp ax, 0                  ; Check if quotient is 0
    jne convert_loop           ; Repeat if not 0

print_digits:
    pop dx                     ; Get digit from stack
    add dl, '0'                ; Convert to ASCII
    mov ah, 2
    int 21h                    ; Print digit
    loop print_digits          ; Repeat for all digits

    ret
print_number endp
end start