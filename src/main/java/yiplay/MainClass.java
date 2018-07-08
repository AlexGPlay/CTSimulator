package yiplay;

import yiplay.language.Compiler;
import yiplay.language.errorManagement.ErrorManager;

public class MainClass {

	public static void main(String args[]) {
		Compiler compiler = new Compiler();
		compiler.compile("movl r3, 4 ;  Pasar el primer parámetro en r1 \r\n" + 
				"movh r3, 00\r\n" + 
				"movl r7, 7 ;  Pasar el segundo parámetro en r2\r\n" + 
				"movh r4, 0\r\n" + 
				"push r4 \r\n" + 
				"push r3 \r\n" + 
				"call suma\r\n" + 
				"inc r7\r\n" + 
				"inc r7\r\n" + 
				"movl r3, 0\r\n" + 
				"movh r3, 10h\r\n" + 
				"mov [r3], r0\r\n" + 
				"\r\n" + 
				"suma: \r\n" + 
				"    push r6 ; Prólogo\r\n" + 
				"    mov r6, r7\r\n" + 
				"\r\n" + 
				"    push r1 ; Salvaguarda de registros\r\n" + 
				"    push r2 \r\n" + 
				"\r\n" + 
				"    inc r6\r\n" + 
				"    inc r6\r\n" + 
				"    mov r1, [r6] ; Acceso al primer parámetro\r\n" + 
				"    inc r6\r\n" + 
				"    mov r2, [r6] ; Acceso al segundo parámetro\r\n" + 
				"\r\n" + 
				"    add r0, r1, r2 ; Cuerpo del procedimiento\r\n" + 
				"\r\n" + 
				"    pop r2 ; Restauración de registros\r\n" + 
				"    pop r1\r\n" + 
				"\r\n" + 
				"    pop r6 ; Epílogo\r\n" + 
				"    ret ");
		
		if(ErrorManager.getManager().hasErrors())
			ErrorManager.getManager().printErrors();
		
	}
}