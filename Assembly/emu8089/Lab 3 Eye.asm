.model tiny
.code
org 100h

start:
    ; Set text mode to allow color input
    mov  ax, 0003h     
    int  10h

    ; Display message to choose color
    mov  ah, 09h
    mov  dx, offset comment
    int  21h

    ; Wait for color input ('R' = Red, 'G' = Green, 'B' = Blue)
wait_color_input:
    mov  ah, 08h
    int  21h
    cmp  al, 'R'   ; Red
    je   color_red
    cmp  al, 'G'   ; Green
    je   color_green
    cmp  al, 'B'   ; Blue
    je   color_blue
    jmp  wait_color_input  ; Loop until valid input

color_red:
    mov  bx, offset points
    mov  al, byte ptr [bx]  ; Red color (binary)
    
    jmp  kt1_red   ; Jump to drawing eye with red color

color_green:
    mov  bx, offset points
    mov  al, byte ptr [bx+1]  ; Green color (binary)
    jmp  kt2_green   ; Jump to drawing eye with green color

color_blue:
    mov  bx, offset points
    mov  al, byte ptr [bx+2]  ; Blue color (binary)
    jmp  kt3_blue   ; Jump to drawing eye with blue color

; Red eye drawing (kt1)
kt1_red:
    ; Set graphics mode 320x200, 256 colors
    mov  ax, 0013h
    int  10h

    ; Initialize coordinates
    mov  cx, 90        ; x-coordinate
    mov  dx, 90        ; y-coordinate

    ; Draw diagonal line up-right 
draw_diagonal_up:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 7         ; Red color
    int  10h           ; Draw pixel
    dec  dx            ; Move up
    inc  cx            ; Move right
    cmp  dx, 80        ; Limit y
    jge  draw_diagonal_up

    ; Draw horizontal line 
draw_horizontal:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 7         ; Red color
    int  10h           ; Draw pixel
    inc  cx            ; Move right
    cmp  cx, 120       ; Limit x
    jle  draw_horizontal

    ; Draw diagonal line down-right 
draw_diagonal_down:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 7         ; Red color
    int  10h           ; Draw pixel
    inc  dx            ; Move down
    inc  cx            ; Move right
    cmp  dx, 90        ; Limit y
    jle  draw_diagonal_down

eyelash:
    mov  cx, 130        ; Start x-coordinate for the first point
    mov  dx, 90
eyelash_loop:
    mov  ah, 0Ch       
    mov  al,8          
    int  10h                      
    dec  dx
    inc cx
    inc cx            
    cmp  dx, 82       
    jge  eyelash_loop 

eyelash2:
    mov  cx, 125       
    mov  dx, 85
eyelash_loop2:
    mov  ah, 0Ch       
    mov  al, 7         
    int  10h                      
    dec  dx
        dec  dx
    inc cx
    inc cx            
    cmp  dx, 72       
    jge  eyelash_loop2
    
eyelash3:
    mov  cx, 122        ; Start x-coordinate for the first point
    mov  dx, 82
eyelash_loop3:
    mov  ah, 0Ch       
    mov  al, 8         
    int  10h                      
    dec  dx
        dec  dx
            dec  dx
    inc cx
    inc cx            
    cmp  dx, 72       
    jge  eyelash_loop3

    ; Draw hexagon (red) after diagonal line
    ; Define points for the hexagon
    mov  cx, 115        ; Start x-coordinate for the first point
    mov  dx, 100        ; Start y-coordinate for the first point    
    ; Draw line from (115,105) to (105,105)
draw_hexagon_1:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 4         ; Red color
    int  10h           ; Draw pixel
    dec  cx            ; Move left
    cmp  cx, 108       ; Limit x to 105
    jge  draw_hexagon_1

    ; Draw line from (105,105) to (95,95)
draw_hexagon_2:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 4         ; Red color
    int  10h           ; Draw pixel
    dec cx
    dec  dx            ; Move up
    cmp  dx, 95        ; Limit y to 100
    jge  draw_hexagon_2
draw_hexagon_3:
    mov  ah, 0Ch       
    mov  al, 4         
    int  10h           
    dec  dx            
    cmp  dx, 85       
    jge  draw_hexagon_3
draw_hexagon_4:       
    mov  ah, 0Ch       
    mov  al, 4         
    int  10h           
    inc cx
    dec  dx            
    cmp  dx, 80        
    jge  draw_hexagon_4


draw_hexagon_6:
    mov  cx, 115        ; Start x-coordinate for the first point
    mov  dx, 80
draw_hexagon_6_loop:
    mov  ah, 0Ch       
    mov  al, 4         
    int  10h                            
    inc cx
    inc dx            
    cmp  dx, 85        
    jle  draw_hexagon_6_loop

draw_hexagon_7:
    mov  ah, 0Ch       
    mov  al, 4         
    int  10h           
    inc  dx            
    cmp  dx, 95       
    jle  draw_hexagon_7

draw_hexagon_8:
    mov  ah, 0Ch       
    mov  al, 4         
    int  10h           
    dec  cx            
    inc  dx            
    cmp  dx, 100       
    jle  draw_hexagon_8

    

    ; Draw line from (95,95) to (95,85)


    jmp return_to_text

; **********************Green eye drawing (kt2)
kt2_green:
    ; Set graphics mode 320x200, 256 colors
    mov  ax, 0013h
    int  10h

    ; Initialize coordinates
    mov  cx, 90        ; x-coordinate
    mov  dx, 90        ; y-coordinate

    ; Draw diagonal line up-right 
draw_diagonal_up_g:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 7         ; Red color
    int  10h           ; Draw pixel
    dec  dx            ; Move up
    inc  cx            ; Move right
    cmp  dx, 80        ; Limit y
    jge  draw_diagonal_up_g

    ; Draw horizontal line 
draw_horizontal_g:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 7         ; Red color
    int  10h           ; Draw pixel
    inc  cx            ; Move right
    cmp  cx, 120       ; Limit x
    jle  draw_horizontal_g

    ; Draw diagonal line down-right 
draw_diagonal_down_g:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 7         ; Red color
    int  10h           ; Draw pixel
    inc  dx            ; Move down
    inc  cx            ; Move right
    cmp  dx, 90        ; Limit y
    jle  draw_diagonal_down_g

eyelash_g:
    mov  cx, 130        ; Start x-coordinate for the first point
    mov  dx, 90
eyelash_loop_g:
    mov  ah, 0Ch       
    mov  al,8          
    int  10h                      
    dec  dx
    inc cx
    inc cx            
    cmp  dx, 82       
    jge  eyelash_loop_g 

eyelash2_g:
    mov  cx, 125       
    mov  dx, 85
eyelash_loop2_g:
    mov  ah, 0Ch       
    mov  al, 7         
    int  10h                      
    dec  dx
        dec  dx
    inc cx
    inc cx            
    cmp  dx, 72       
    jge  eyelash_loop2_g
    
eyelash3_g:
    mov  cx, 122        ; Start x-coordinate for the first point
    mov  dx, 82
eyelash_loop3_g:
    mov  ah, 0Ch       
    mov  al, 8         
    int  10h                      
    dec  dx
        dec  dx
            dec  dx
    inc cx
    inc cx            
    cmp  dx, 72       
    jge  eyelash_loop3_g

    ; Draw hexagon (red) after diagonal line
    ; Define points for the hexagon
    mov  cx, 115        ; Start x-coordinate for the first point
    mov  dx, 100        ; Start y-coordinate for the first point    
    ; Draw line from (115,105) to (105,105)
draw_hexagon_1_g:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 2         ; Red color
    int  10h           ; Draw pixel
    dec  cx            ; Move left
    cmp  cx, 108       ; Limit x to 105
    jge  draw_hexagon_1_g

    ; Draw line from (105,105) to (95,95)
draw_hexagon_2_g:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 2         ; Red color
    int  10h           ; Draw pixel
    dec cx
    dec  dx            ; Move up
    cmp  dx, 95        ; Limit y to 100
    jge  draw_hexagon_2_g
draw_hexagon_3_g:
    mov  ah, 0Ch       
    mov  al, 2         
    int  10h           
    dec  dx            
    cmp  dx, 85       
    jge  draw_hexagon_3_g
draw_hexagon_4_g:       
    mov  ah, 0Ch       
    mov  al, 2         
    int  10h           
    inc cx
    dec  dx            
    cmp  dx, 80        
    jge  draw_hexagon_4_g


draw_hexagon_6_g:
    mov  cx, 115        ; Start x-coordinate for the first point
    mov  dx, 80
draw_hexagon_6_loop_g:
    mov  ah, 0Ch       
    mov  al, 2         
    int  10h                            
    inc cx
    inc dx            
    cmp  dx, 85        
    jle  draw_hexagon_6_loop_g

draw_hexagon_7_g:
    mov  ah, 0Ch       
    mov  al, 2         
    int  10h           
    inc  dx            
    cmp  dx, 95       
    jle  draw_hexagon_7_g

draw_hexagon_8_g:
    mov  ah, 0Ch       
    mov  al, 2         
    int  10h           
    dec  cx            
    inc  dx            
    cmp  dx, 100       
    jle  draw_hexagon_8_g

    jmp return_to_text

; ************************Blue eye drawing (kt3)
kt3_blue:
    ; Set graphics mode 320x200, 256 colors
    mov  ax, 0013h
    int  10h

    ; Initialize coordinates
    mov  cx, 90        ; x-coordinate
    mov  dx, 90        ; y-coordinate

    ; Draw diagonal line up-right 
draw_diagonal_up_b:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 7         ; Red color
    int  10h           ; Draw pixel
    dec  dx            ; Move up
    inc  cx            ; Move right
    cmp  dx, 80        ; Limit y
    jge  draw_diagonal_up_b

    ; Draw horizontal line 
draw_horizontal_b:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 7         ; Red color
    int  10h           ; Draw pixel
    inc  cx            ; Move right
    cmp  cx, 120       ; Limit x
    jle  draw_horizontal_b

    ; Draw diagonal line down-right 
draw_diagonal_down_b:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 7         ; Red color
    int  10h           ; Draw pixel
    inc  dx            ; Move down
    inc  cx            ; Move right
    cmp  dx, 90        ; Limit y
    jle  draw_diagonal_down_b

eyelash_b:
    mov  cx, 130        ; Start x-coordinate for the first point
    mov  dx, 90
eyelash_loop_b:
    mov  ah, 0Ch       
    mov  al,8          
    int  10h                      
    dec  dx
    inc cx
    inc cx            
    cmp  dx, 82       
    jge  eyelash_loop_b 

eyelash2_b:
    mov  cx, 125       
    mov  dx, 85
eyelash_loop2_b:
    mov  ah, 0Ch       
    mov  al, 7         
    int  10h                      
    dec  dx
        dec  dx
    inc cx
    inc cx            
    cmp  dx, 72       
    jge  eyelash_loop2_b
    
eyelash3_b:
    mov  cx, 122        ; Start x-coordinate for the first point
    mov  dx, 82
eyelash_loop3_b:
    mov  ah, 0Ch       
    mov  al, 8         
    int  10h                      
    dec  dx
        dec  dx
            dec  dx
    inc cx
    inc cx            
    cmp  dx, 72       
    jge  eyelash_loop3_b

    ; Define points for the hexagon
    mov  cx, 115        ; Start x-coordinate for the first point
    mov  dx, 100        ; Start y-coordinate for the first point    
    ; Draw line from (115,105) to (105,105)
draw_hexagon_1_b:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 1         ; Red color
    int  10h           ; Draw pixel
    dec  cx            ; Move left
    cmp  cx, 108       ; Limit x to 105
    jge  draw_hexagon_1_b

    ; Draw line from (105,105) to (95,95)
draw_hexagon_2_b:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 1         ; Red color
    int  10h           ; Draw pixel
    dec cx
    dec  dx            ; Move up
    cmp  dx, 95        ; Limit y to 100
    jge  draw_hexagon_2_b
draw_hexagon_3_b:
    mov  ah, 0Ch       
    mov  al, 1         
    int  10h           
    dec  dx            
    cmp  dx, 85       
    jge  draw_hexagon_3_b
draw_hexagon_4_b:       
    mov  ah, 0Ch       
    mov  al, 1         
    int  10h           
    inc cx
    dec  dx            
    cmp  dx, 80        
    jge  draw_hexagon_4_b


draw_hexagon_6_b:
    mov  cx, 115        ; Start x-coordinate for the first point
    mov  dx, 80
draw_hexagon_6_loop_b:
    mov  ah, 0Ch       
    mov  al, 1         
    int  10h                            
    inc cx
    inc dx            
    cmp  dx, 85        
    jle  draw_hexagon_6_loop_b

draw_hexagon_7_b:
    mov  ah, 0Ch       
    mov  al, 1         
    int  10h           
    inc  dx            
    cmp  dx, 95       
    jle  draw_hexagon_7_b

draw_hexagon_8_b:
    mov  ah, 0Ch       
    mov  al, 1         
    int  10h           
    dec  cx            
    inc  dx            
    cmp  dx, 100       
    jle  draw_hexagon_8_b

    jmp return_to_text

; Return to text mode
return_to_text:
      
    ; Set text mode to allow color input
    mov  ax, 0003h     
    int  10h

    ; Display message to choose O or C
    mov  ah, 09h
    mov  dx, offset msg
    int  21h       
    wait_option_input:
    mov  ah, 08h
    int  21h
    cmp  al, 'O'   ; open
    je   open_eye
    cmp  al, 'C'   ; Green
    je   closed_eye
    jmp  wait_option_input  ; Loop until valid input


open_eye:
mov  ax, 0013h
int  10h

 
   mov cx, 130        ; x-coordinate
    mov dx, 90        ; y-coordinate
   
draw_diagonal_down2o:
    mov ah, 0Ch      ; Set pixel function
    mov al, 15       ; Color (white)
    int 10h          ; Draw pixel

    ; Move down and left
    inc dx            ; Y down
    dec cx            ; X left
    cmp dx, 100       ; Check y
    jle draw_diagonal_down2o ;


draw_horizontal3o:
    ; Set pixel at (cx, dx)
    mov ah, 0Ch      ; Set pixel function
    mov al, 15       ; Color (white)
    int 10h          ; Draw pixel

    ; Move left
    dec cx            ; X left
    cmp cx, 100       ; Check x
    jge draw_horizontal3o ; If x >= 70, continue

    ; Draw diagonal line up from (110, 110)
draw_diagonal_up_from_o:
    ; Set pixel at (cx, dx)
    mov ah, 0Ch      ; Set pixel function
    mov al, 15       ; Color (white)
    int 10h          ; Draw pixel

    ; Move up and left
    dec dx            ; Y up
    dec cx            ; X left
    cmp dx, 91        ; Check y
    jge draw_diagonal_up_from_o ; If y > 90, continue

    mov  cx, 90        ; x-coordinate
    mov  dx, 90        ; y-coordinate
    
    eyelash_an_o:
    mov  cx, 130        ; Start x-coordinate for the first point
    mov  dx, 90
    
    
eyelash_loop_an_o:
    mov  ah, 0Ch       
    mov  al, 8          
    int  10h                      
    inc  dx 
    inc  dx
    inc  cx          
    cmp  dx, 108     
    jle  eyelash_loop_an_o 

eyelash2_an_o:
    mov  cx, 125       
    mov  dx, 95
eyelash_loop2_an_o:
    mov  ah, 0Ch       
    mov  al, 8         
    int  10h
    inc cx                      
    inc  dx          
    cmp  dx, 112       
    jle  eyelash_loop2_an_o
    
eyelash3_an_o:
    mov  cx, 120        ; Start x-coordinate for the first point
    mov  dx, 100
eyelash_loop3_an_o:
    mov  ah, 0Ch       
    mov  al, 8         
    int  10h                      
    inc dx           
    cmp  dx, 119       
    jle  eyelash_loop3_an_o      
    

erased_eyelash_an_o:
    mov  cx, 130        ; Start x-coordinate for the first point
    mov  dx, 90
erased_eyelash_loop_an_o:
    mov  ah, 0Ch       
    mov  al, 0          
    int  10h                      
    inc  dx 
    inc  dx
    inc  cx          
    cmp  dx, 108     
    jle  erased_eyelash_loop_an_o 

erased_eyelash2_an_o:
    mov  cx, 125       
    mov  dx, 95
erased_eyelash_loop2_an_o:
    mov  ah, 0Ch       
    mov  al, 0         
    int  10h
    inc cx                      
    inc  dx          
    cmp  dx, 112       
    jle  erased_eyelash_loop2_an_o
    
erased_eyelash3_an_o:
    mov  cx, 120        ; Start x-coordinate for the first point
    mov  dx, 100
erased_eyelash_loop3_an_o:
    mov  ah, 0Ch       
    mov  al, 0         
    int  10h                      
    inc dx           
    cmp  dx, 119       
    jle  erased_eyelash_loop3_an_o      
    
 mov cx, 130
mov dx, 90

erased_draw_diagonal_down2o:
    mov ah, 0Ch
    mov al, 0
    int 10h

    inc dx
    dec cx
    cmp dx, 100
    jle erased_draw_diagonal_down2o

erased_draw_horizontal3o:
    mov ah, 0Ch
    mov al, 0
    int 10h

    dec cx
    cmp cx, 100
    jge erased_draw_horizontal3o

erased_draw_diagonal_up_from_o:
    mov ah, 0Ch
    mov al, 0
    int 10h

    dec dx
    dec cx
    cmp dx, 91
    jge erased_draw_diagonal_up_from_o 
    
   
    redrawn_horizontal:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 15         ; Red color
    int  10h           ; Draw pixel
    inc  cx            ; Move right
    cmp  cx, 130       ; Limit x
    jle  redrawn_horizontal
      
    ; Initialize coordinates
    mov  cx, 120        ; x-coordinate
    mov  dx, 92        ; y-coordinate    
redrawn_hexagon_7_animation:
    mov  ah, 0Ch       
    mov  al, 2         
    int  10h           
    inc  dx            
    cmp  dx, 95       
    jle  redrawn_hexagon_7_animation
    
redrawn_hexagon_8_animation:
    mov  ah, 0Ch       
    mov  al, 2         
    int  10h           
    dec  cx            
    inc  dx            
    cmp  dx, 100       
    jle  redrawn_hexagon_8_animation
    
redrawn_hexagon_1_an:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 2         
    int  10h           ; Draw pixel
    dec  cx            ; Move left
    cmp  cx, 108       ; Limit x to 105
    jge  redrawn_hexagon_1_an
    
redrawn_hexagon_2_an:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 2         ; Red color
    int  10h           ; Draw pixel
    dec cx
    dec  dx            ; Move up
    cmp  dx, 95        ; Limit y to 100
    jge  redrawn_hexagon_2_an
redrawn_hexagon_3_an:
    mov  ah, 0Ch       
    mov  al, 2         
    int  10h           
    dec  dx            
    cmp  dx, 92       
    jge  redrawn_hexagon_3_an       
     
redrawn_eyelash_an_c:
    mov  cx, 130        ; Start x-coordinate for the first point
    mov  dx, 90
redrawn_eyelash_loop_an_c:
    mov  ah, 0Ch       
    mov  al, 8          
    int  10h                      
    inc  dx 
    inc  dx
    inc  cx          
    cmp  dx, 108     
    jle  redrawn_eyelash_loop_an_c 

redrawn_eyelash2_an_c:
    mov  cx, 122       
    mov  dx, 90
redrawn_eyelash_loop2_an_c:
    mov  ah, 0Ch       
    mov  al, 7         
    int  10h                      
    inc  dx          
    cmp  dx, 109       
    jle  redrawn_eyelash_loop2_an_c
    
redrawn_eyelash3_an_c:
    mov  cx, 122        ; Start x-coordinate for the first point
    mov  dx, 90
redrawn_eyelash_loop3_an_c:
    mov  ah, 0Ch       
    mov  al, 8         
    int  10h                      
    inc dx           
    cmp  dx, 109       
    jle  redrawn_eyelash_loop3_an_c
 erase_horizontal2:
    mov  ah, 0Ch
    mov  al, 0
    int  10h
    inc  cx
    cmp  cx, 130
    jle  erase_horizontal2

    mov  cx, 120
    mov  dx, 92
erase_hexagon_7_animation2:
    mov  ah, 0Ch
    mov  al, 0
    int  10h
    inc  dx
    cmp  dx, 95
    jle  erase_hexagon_7_animation2

erase_hexagon_8_animation2:
    mov  ah, 0Ch
    mov  al, 0
    int  10h
    dec  cx
    inc  dx
    cmp  dx, 100
    jle  erase_hexagon_8_animation2

erase_hexagon_1_an2:
    mov  ah, 0Ch
    mov  al, 0
    int  10h
    dec  cx
    cmp  cx, 108
    jge  erase_hexagon_1_an2

erase_hexagon_2_an2:
    mov  ah, 0Ch
    mov  al, 0
    int  10h
    dec cx
    dec  dx
    cmp  dx, 95
    jge  erase_hexagon_2_an2

erase_hexagon_3_an2:
    mov  ah, 0Ch
    mov  al, 0
    int  10h
    dec  dx
    cmp  dx, 92
    jge  erase_hexagon_3_an2

erase_eyelash_an_c2:
    mov  cx, 130
    mov  dx, 90
erase_eyelash_loop_an_c2:
    mov  ah, 0Ch
    mov  al, 0
    int  10h
    inc  dx 
    inc  dx
    inc  cx
    cmp  dx, 108
    jle  erase_eyelash_loop_an_c2

erase_eyelash2_an_c2:
    mov  cx, 122
    mov  dx, 90
erase_eyelash_loop2_an_c2:
    mov  ah, 0Ch
    mov  al, 0
    int  10h
    inc  dx
    cmp  dx, 109
    jle  erase_eyelash_loop2_an_c2

erase_eyelash3_an_c2:
    mov  cx, 122
    mov  dx, 90
erase_eyelash_loop3_an_c2:
    mov  ah, 0Ch
    mov  al, 0
    int  10h
    inc dx
    cmp  dx, 109
    jle  erase_eyelash_loop3_an_c2 
    mov  cx, 90        
mov  dx, 90  
    erase_horizontal:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 0         ; Red color
    int  10h           ; Draw pixel
    inc  cx            ; Move right
    cmp  cx, 130       ; Limit x
    jle  erase_horizontal

mov  cx, 90        
mov  dx, 90  
draw_diagonal_up_open:
    mov  ah, 0Ch
    mov  al, 7
    int  10h
    dec  dx
    inc  cx
    cmp  dx, 80
    jge  draw_diagonal_up_open

draw_horizontal_open:
    mov  ah, 0Ch
    mov  al, 7
    int  10h
    inc  cx
    cmp  cx, 120
    jle  draw_horizontal_open

draw_diagonal_down_open:
    mov  ah, 0Ch
    mov  al, 7
    int  10h
    inc  dx
    inc  cx
    cmp  dx, 90
    jle  draw_diagonal_down_open

eyelash_open:
    mov  cx, 130
    mov  dx, 90
eyelash_loop_open:
    mov  ah, 0Ch
    mov  al, 8
    int  10h
    dec  dx
    inc  cx
    inc  cx
    cmp  dx, 82
    jge  eyelash_loop_open

eyelash2_open:
    mov  cx, 125
    mov  dx, 85
eyelash_loop2_open:
    mov  ah, 0Ch
    mov  al, 7
    int  10h
    dec  dx
    dec  dx
    inc  cx
    inc  cx
    cmp  dx, 72
    jge  eyelash_loop2_open

eyelash3_open:
    mov  cx, 122
    mov  dx, 82
eyelash_loop3_open:
    mov  ah, 0Ch
    mov  al, 8
    int  10h
    dec  dx
    dec  dx
    dec  dx
    inc  cx
    inc  cx
    cmp  dx, 72
    jge  eyelash_loop3_open

draw_hexagon_1_open:
    mov  cx, 115
    mov  dx, 100
draw_hexagon_1_open_loop:
    mov  ah, 0Ch
    mov  al, 4
    int  10h
    dec  cx
    cmp  cx, 108
    jge  draw_hexagon_1_open_loop

draw_hexagon_2_open:
    mov  ah, 0Ch
    mov  al, 4
    int  10h
    dec  cx
    dec  dx
    cmp  dx, 95
    jge  draw_hexagon_2_open

draw_hexagon_3_open:
    mov  ah, 0Ch
    mov  al, 4
    int  10h
    dec  dx
    cmp  dx, 85
    jge  draw_hexagon_3_open

draw_hexagon_4_open:
    mov  ah, 0Ch
    mov  al, 4
    int  10h
    inc  cx
    dec  dx
    cmp  dx, 80
    jge  draw_hexagon_4_open

draw_hexagon_6_open:
    mov  cx, 115
    mov  dx, 80
draw_hexagon_6_loop_open:
    mov  ah, 0Ch
    mov  al, 4
    int  10h
    inc  cx
    inc  dx
    cmp  dx, 85
    jle  draw_hexagon_6_loop_open

draw_hexagon_7_open:
    mov  ah, 0Ch
    mov  al, 4
    int  10h
    inc  dx
    cmp  dx, 95
    jle  draw_hexagon_7_open

draw_hexagon_8_open:
    mov  ah, 0Ch
    mov  al, 4
    int  10h
    dec  cx
    inc  dx
    cmp  dx, 100
    jle  draw_hexagon_8_open
  
    jmp return_to_text
   
;***************** Closed EYE
closed_eye:
mov  ax, 0013h
int  10h

mov  cx, 90        
mov  dx, 90        

draw_diagonal_up_green_eye:
mov  ah, 0Ch       
mov  al, 7         
int  10h           
dec  dx            
inc  cx            
cmp  dx, 80        
jge  draw_diagonal_up_green_eye

draw_horizontal_green_eye:
mov  ah, 0Ch       
mov  al, 7         
int  10h           
inc  cx            
cmp  cx, 120       
jle  draw_horizontal_green_eye

draw_diagonal_down_green_eye:
mov  ah, 0Ch       
mov  al, 7         
int  10h           
inc  dx            
inc  cx            
cmp  dx, 90        
jle  draw_diagonal_down_green_eye

eyelash_green_eye:
mov  cx, 130        
mov  dx, 90
eyelash_loop_green_eye:
mov  ah, 0Ch       
mov  al,8          
int  10h                      
dec  dx
inc cx
inc cx            
cmp  dx, 82       
jge  eyelash_loop_green_eye 

eyelash2_green_eye:
mov  cx, 125       
mov  dx, 85
eyelash_loop2_green_eye:
mov  ah, 0Ch       
mov  al, 7         
int  10h                      
dec  dx
dec  dx
inc cx
inc cx            
cmp  dx, 72       
jge  eyelash_loop2_green_eye

eyelash3_green_eye:
mov  cx, 122        
mov  dx, 82
eyelash_loop3_green_eye:
mov  ah, 0Ch       
mov  al, 8         
int  10h                      
dec  dx
dec  dx
dec  dx
inc cx
inc cx            
cmp  dx, 72       
jge  eyelash_loop3_green_eye

mov  cx, 115        
mov  dx, 100        
draw_hexagon_1_green_eye:
mov  ah, 0Ch       
mov  al, 2         
int  10h           
dec  cx            
cmp  cx, 108       
jge  draw_hexagon_1_green_eye

draw_hexagon_2_green_eye:
mov  ah, 0Ch       
mov  al, 2         
int  10h           
dec cx
dec  dx            
cmp  dx, 95        
jge  draw_hexagon_2_green_eye
draw_hexagon_3_green_eye:
mov  ah, 0Ch       
mov  al, 2         
int  10h           
dec  dx            
cmp  dx, 85       
jge  draw_hexagon_3_green_eye
draw_hexagon_4_green_eye:       
mov  ah, 0Ch       
mov  al, 2         
int  10h           
inc cx
dec  dx            
cmp  dx, 80        
jge  draw_hexagon_4_green_eye

draw_hexagon_6_green_eye:
mov  cx, 115        
mov  dx, 80
draw_hexagon_6_loop_green_eye:
mov  ah, 0Ch       
mov  al, 2         
int  10h                            
inc cx
inc dx            
cmp  dx, 85        
jle  draw_hexagon_6_loop_green_eye

draw_hexagon_7_green_eye:
mov  ah, 0Ch       
mov  al, 2         
int  10h           
inc  dx            
cmp  dx, 95       
jle  draw_hexagon_7_green_eye

draw_hexagon_8_green_eye:
mov  ah, 0Ch       
mov  al, 2         
int  10h           
dec  cx            
inc  dx            
cmp  dx, 100       
jle  draw_hexagon_8_green_eye
    mov  ax, 0013h
    int  10h
                                  mov  cx, 90        ; x-coordinate
    mov  dx, 90        ; y-coordinate

    draw_horizontal2:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 7         ; Red color
    int  10h           ; Draw pixel
    inc  cx            ; Move right
    cmp  cx, 130       ; Limit x
    jle  draw_horizontal2
      
    ; Initialize coordinates
    mov  cx, 120        ; x-coordinate
    mov  dx, 92        ; y-coordinate    
    draw_hexagon_7_animation:
    mov  ah, 0Ch       
    mov  al, 2         
    int  10h           
    inc  dx            
    cmp  dx, 95       
    jle  draw_hexagon_7_animation
    
    draw_hexagon_8_animation:
    mov  ah, 0Ch       
    mov  al, 2         
    int  10h           
    dec  cx            
    inc  dx            
    cmp  dx, 100       
    jle  draw_hexagon_8_animation
    
    draw_hexagon_1_an:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 2         ; Red color
    int  10h           ; Draw pixel
    dec  cx            ; Move left
    cmp  cx, 108       ; Limit x to 105
    jge  draw_hexagon_1_an

    ; Draw line from (105,105) to (95,95)
draw_hexagon_2_an:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 2         ; Red color
    int  10h           ; Draw pixel
    dec cx
    dec  dx            ; Move up
    cmp  dx, 95        ; Limit y to 100
    jge  draw_hexagon_2_an
draw_hexagon_3_an:
    mov  ah, 0Ch       
    mov  al, 2         
    int  10h           
    dec  dx            
    cmp  dx, 92       
    jge  draw_hexagon_3_an   
    
eyelash_an:
    mov  cx, 130        ; Start x-coordinate for the first point
    mov  dx, 90
eyelash_loop_an:
    mov  ah, 0Ch       
    mov  al, 8          
    int  10h                      
    inc  dx 
    inc  dx
    inc  cx          
    cmp  dx, 108     
    jle  eyelash_loop_an 

eyelash2_an:
    mov  cx, 122       
    mov  dx, 90
eyelash_loop2_an:
    mov  ah, 0Ch       
    mov  al, 8         
    int  10h                      
    inc  dx          
    cmp  dx, 109       
    jle  eyelash_loop2_an
    
eyelash3_an:
    mov  cx, 122        ; Start x-coordinate for the first point
    mov  dx, 90
eyelash_loop3_an:
    mov  ah, 0Ch       
    mov  al, 8         
    int  10h                      
    inc dx           
    cmp  dx, 109       
    jle  eyelash_loop3_an  
    
 
    mov cx, 130        ; x-coordinate
    mov dx, 90        ; y-coordinate
   
draw_diagonal_down2:
    mov ah, 0Ch      ; Set pixel function
    mov al, 15       ; Color (white)
    int 10h          ; Draw pixel

    ; Move down and left
    inc dx            ; Y down
    dec cx            ; X left
    cmp dx, 100       ; Check y
    jle draw_diagonal_down2 ; If y <= 100, continue

    ; Draw horizontal line left
draw_horizontal3:
    ; Set pixel at (cx, dx)
    mov ah, 0Ch      ; Set pixel function
    mov al, 15       ; Color (white)
    int 10h          ; Draw pixel

    ; Move left
    dec cx            ; X left
    cmp cx, 100       ; Check x
    jge draw_horizontal3 ; If x >= 70, continue

    ; Draw diagonal line up from (110, 110)
draw_diagonal_up_from_110_110:
    ; Set pixel at (cx, dx)
    mov ah, 0Ch      ; Set pixel function
    mov al, 15       ; Color (white)
    int 10h          ; Draw pixel

    ; Move up and left
    dec dx            ; Y up
    dec cx            ; X left
    cmp dx, 91        ; Check y
    jge draw_diagonal_up_from_110_110 ; If y > 90, continue

    mov  cx, 90        ; x-coordinate
    mov  dx, 90        ; y-coordinate
    jmp erase 
    
;*************  erase
erase:
    erase_horizontal_y:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 0         ; Red color
    int  10h           ; Draw pixel
    inc  cx            ; Move right
    cmp  cx, 130       ; Limit x
    jle  erase_horizontal_y
      
    ; Initialize coordinates
    mov  cx, 120        ; x-coordinate
    mov  dx, 92        ; y-coordinate    
    erase_hexagon_7_animation:
    mov  ah, 0Ch       
    mov  al, 0         
    int  10h           
    inc  dx            
    cmp  dx, 95       
    jle  erase_hexagon_7_animation
    
    erase_hexagon_8_animation:
    mov  ah, 0Ch       
    mov  al, 0         
    int  10h           
    dec  cx            
    inc  dx            
    cmp  dx, 100       
    jle  erase_hexagon_8_animation
    
    erase_hexagon_1_an:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 15         
    int  10h           ; Draw pixel
    dec  cx            ; Move left
    cmp  cx, 108       ; Limit x to 105
    jge  erase_hexagon_1_an
    
erase_hexagon_2_an:
    mov  ah, 0Ch       ; Set pixel function
    mov  al, 0         ; Red color
    int  10h           ; Draw pixel
    dec cx
    dec  dx            ; Move up
    cmp  dx, 95        ; Limit y to 100
    jge  erase_hexagon_2_an
erase_hexagon_3_an:
    mov  ah, 0Ch       
    mov  al, 0         
    int  10h           
    dec  dx            
    cmp  dx, 92       
    jge  erase_hexagon_3_an       
     
erase_eyelash_an_c:
    mov  cx, 130        ; Start x-coordinate for the first point
    mov  dx, 90
erase_eyelash_loop_an_c:
    mov  ah, 0Ch       
    mov  al, 0          
    int  10h                      
    inc  dx 
    inc  dx
    inc  cx          
    cmp  dx, 108     
    jle  erase_eyelash_loop_an_c 

erase_eyelash2_an_c:
    mov  cx, 122       
    mov  dx, 90
erase_eyelash_loop2_an_c:
    mov  ah, 0Ch       
    mov  al, 0         
    int  10h                      
    inc  dx          
    cmp  dx, 109       
    jle  erase_eyelash_loop2_an_c
    
erase_eyelash3_an_c:
    mov  cx, 122        ; Start x-coordinate for the first point
    mov  dx, 90
erase_eyelash_loop3_an_c:
    mov  ah, 0Ch       
    mov  al, 0         
    int  10h                      
    inc dx           
    cmp  dx, 109       
    jle  erase_eyelash_loop3_an_c  
    ; ********** Draw eyelashes again
eyelash_an_c:
    mov  cx, 130        ; Start x-coordinate for the first point
    mov  dx, 90
eyelash_loop_an_c:
    mov  ah, 0Ch       
    mov  al, 8          
    int  10h                      
    inc  dx 
    inc  dx
    inc  cx          
    cmp  dx, 108     
    jle  eyelash_loop_an_c 

eyelash2_an_c:
    mov  cx, 125       
    mov  dx, 95
eyelash_loop2_an_c:
    mov  ah, 0Ch       
    mov  al, 8         
    int  10h
    inc cx                      
    inc  dx          
    cmp  dx, 112       
    jle  eyelash_loop2_an_c
    
eyelash3_an_c:
    mov  cx, 120        ; Start x-coordinate for the first point
    mov  dx, 100
eyelash_loop3_an_c:
    mov  ah, 0Ch       
    mov  al, 8         
    int  10h                      
    inc dx           
    cmp  dx, 119       
    jle  eyelash_loop3_an_c      
    
    
    jmp return_to_text
    

msg db 'Tap to open eye "O", to close it "C"$'
comment db 'Choose color: R - Red, G - Green, B - Blue$'
points db 10010001b,00110010b,01111011b  ; Binary values for red, green, blue

end start