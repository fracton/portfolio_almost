;MENU.asm
;jaron lake
.model small
.stack 100h
.data
msg1 db 'Option1', 0dh, 0ah, '$'
msg2 db 'Option2', 0dh, 0ah, '$'
msg3 db 'Option3', 0dh, 0ah, '$'
msg4 db 'Option4', 0dh, 0ah, '$'
csr db 06h 
last db 00h 

.code
main proc
	mov ax, @data
	mov ds, ax        ;//Data stuff

	mov ah, 00h
	mov al, 03h       
	int 10h            ;Set grphx Mode >3
	
	call face			;draw the menu
	call cursor_ 		;draw le cursour
	mov al, 00h 
	WHILE_:
		cmp     AL,1Bh          ;ESC (exit character)?
		je      END_WHILE       ;yes, exit
;if function key
		cmp     AL,0            ;AL = 0?
		jne     ELSE_           ;no, boring character key
;then
		call    move_cursor     ;execute function
		call    cursor_			;draw cursor
	ELSE_:                         
	NEXT_KEY:
		mov     AH,0            ;get keystroke function
		int     16h             ;AH=scan code,AL=ASCII code
		jmp     WHILE_
	END_WHILE:

	mov ax, 0600h
	mov bh, 70h
	mov cx, 0000h
	mov dx, 184Fh
	int 10h            ;clear screeen

	mov ah, 4ch
	int 21h ;give controll to doss

main endp

face: 
	mov ax, 0606h     ;scroll 6 lines
	mov cx, 0505h
	mov dx, 0A32h
	mov bh, 02fh
	int 10h

	mov ah, 02h
	mov bh, 00h
	mov dx, 0606h
	int 10h            ;cursor to 6,6

	mov ah, 09h
	mov dx, offset msg1
		int 21h            ;display message

	mov ah, 02h
	mov bh, 00h
	mov dx, 0706h
	int 10h            ;cursor to 7,6

	mov ah, 09h
	mov dx, offset msg2
	int 21h            ;display message

	mov ah, 02h
	mov bh, 00h
	mov dx, 0806h
	int 10h            ;cursor to 8,6

	mov ah, 09h
	mov dx, offset msg3
	int 21h            ;display message

	mov ah, 02h
	mov bh, 00h
	mov dx, 0906h
	int 10h            ;cursor to 9,6

	mov ah, 09h
	mov dx, offset msg4
	int 21h            ;display message

ret

cursor_: 
	mov ah, 02h
	mov bh, 00h
	mov dh, last	;erase the last spot	
	mov dl, 05
	int 10h            

	MOV AH,2       ; write a character
	MOV DL,' '     ; ascii code goes in DL
	INT 21H

	mov ah, 02h
	mov bh, 00h
	mov dh, csr		;new spot
	mov last, dh
	mov dl, 05
	int 10h            

	MOV AH,2       ; write a character
	MOV DL,'>'     ; ascii code goes in DL
	INT 21H
ret

move_cursor:
	push ax
	mov     AH,3            ;get cursor location
	mov     BH,0            ;on page 0
	int     10h             ;DH = row, DL = col
	pop ax
;case scan code of
	cmp     AH,72           ;up arrow?
	je      CURSOR_UP       ;
	cmp     AH,80           ;down arrow?
	je      CURSOR_DOWN     ;
	jmp     end_case        ;other function key
	CURSOR_UP:
		cmp     DH,06h            ;row 5?
		je      end_case     	;can't go up
		dec     DH              ;row = row - 1
		mov 	csr, dh
		jmp     end_case         
	CURSOR_DOWN:
		cmp     DH, 09h           ;last row?
		je      end_case       ;can't go down
		inc     DH              ;row = row + 1
		mov 	csr, dh
	end_case: 
ret

end main