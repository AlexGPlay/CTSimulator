package yiplay.util;

public class Translate {
	
	public static String toBinaryString(String data, int radix, int bits) {
		short temp;
		
		if(radix == 10) {
			temp = Short.parseShort(data);
			return toBinaryString(temp,bits);
		}
		
		else if(radix == 16) {
			int value = Integer.parseInt(data, 16);
		    String binary = Integer.toBinaryString(value);
		    return normalizeToNBits(binary,bits);
		}
		
		else if(radix == 2) 
			return data;
			
		return null;
	}

	public static String toBinaryString(short number, int bits) {
		if(number>=0)
			return normalizeToNBits(Integer.toBinaryString(number),bits);
		
		int n = number*-1;
		String binary = normalizeToNBits(Integer.toBinaryString(n),bits);
		
		return getTwosComplement(binary, bits);
	}
	
	public static int toMemoryPosition(String binary) {
		return Integer.parseInt(binary,2);
	}
	
	public static short toDecimalInteger(String binary) {
		if(binary.charAt(0) == '0')
			return Short.parseShort(binary,2);
		
		binary = invertDigits(binary);
		binary = add(binary,"1",16);
		return (short) (-1*Integer.parseInt(binary,2));
	}
	
	public static short toDecimalInteger(String binary, boolean register) {
		if(binary.charAt(0) == '0' || register)
			return Short.parseShort(binary,2);
		
		binary = invertDigits(binary);
		binary = add(binary,"1",16);
		return (short) (-1*Short.parseShort(binary,2));
	}

	private static String getTwosComplement(String binaryInt, int bits) {
		binaryInt = invertDigits(binaryInt);
		binaryInt = add(binaryInt,"1", bits);
		return binaryInt;
	}

	private static String invertDigits(String binaryInt) {
		String result = binaryInt;
		result = result.replace("0", " ");
		result = result.replace("1", "0");
		result = result.replace(" ", "1");
		return result;
	}
	
	private static String normalizeToNBits(String op, int nBits) {
		while(op.length()<nBits)
			op = "0" + op;
		return op;
	}

	private static String add(String op1, String op2, int bits) {
		String res = "";
		boolean carry = false;

		op1 = normalizeToNBits(op1,bits);
		op2 = normalizeToNBits(op2,bits);
		
		for(int i=bits-1;i>=0;i--) {
			char n1 = op1.charAt(i);
			char n2 = op2.charAt(i);

			if(n1 == '0' && n2 == '0') {
				if(carry)
					res = '1' + res;
				else
					res = '0' + res;
				carry = false;
			}

			else if(n1 == '0' && n2 == '1' || n1 == '1' && n2 == '0') {
				if(carry) {
					res = '0' + res;
					carry = true;
				}
				else {
					res = '1' + res;
					carry = false;
				}
			}


			else {
				if(carry) 
					res = '1' + res;
				else 
					res = '0' + res;
				
				carry = true;
			}

		}
		
		return res;
	}

}
