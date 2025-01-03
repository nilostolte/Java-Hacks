package com.list;

public class Test {
	
	static byte[] adding = {
		(byte) 0xF0, (byte) 0x9D, (byte) 0x94, (byte) 0xB8, (byte) 0xF0, (byte) 0x9D, 
		(byte) 0x94, (byte) 0xBB, (byte) 0xF0, (byte) 0x9D, (byte) 0x94, (byte) 0xBB, 
		(byte) 0xF0, (byte) 0x9D, (byte) 0x95, (byte) 0x80, (byte) 0xE2, (byte) 0x84, 
		(byte) 0x95, (byte) 0xF0, (byte) 0x9D, (byte) 0x94, (byte) 0xBE
	};
	static byte[] deleting = {
		(byte) 0xF0, (byte) 0x9D, (byte) 0x94, (byte) 0xBB, (byte) 0xF0, (byte) 0x9D, 
		(byte) 0x94, (byte) 0xBC, (byte) 0xF0, (byte) 0x9D, (byte) 0x95, (byte) 0x83, 
		(byte) 0xF0, (byte) 0x9D, (byte) 0x94, (byte) 0xBC, (byte) 0xF0, (byte) 0x9D, 
		(byte) 0x95, (byte) 0x8B, (byte) 0xF0, (byte) 0x9D, (byte) 0x95, (byte) 0x80, 
		(byte) 0xE2, (byte) 0x84, (byte) 0x95, (byte) 0xF0, (byte) 0x9D, (byte) 0x94, 
		(byte) 0xBE
	};
		
	static byte[] elements = { 
		(byte) 0xF0, (byte) 0x9D, (byte) 0x94, (byte) 0xBC, (byte) 0xF0, (byte) 0x9D,
		(byte) 0x95, (byte) 0x83, (byte) 0xF0, (byte) 0x9D, (byte) 0x94, (byte) 0xBC,
		(byte) 0xF0, (byte) 0x9D, (byte) 0x95, (byte) 0x84, (byte) 0xF0, (byte) 0x9D,
		(byte) 0x94, (byte) 0xBC, (byte) 0xE2, (byte) 0x84, (byte) 0x95, (byte) 0xF0,
		(byte) 0x9D, (byte) 0x95, (byte) 0x8B, (byte) 0xF0, (byte) 0x9D, (byte) 0x95, 
		(byte) 0x8A
	};
	
	
	static byte[] A = {
		(byte) 0xF0, (byte) 0x9D, (byte) 0x94, (byte) 0xB8	
	};
	
	static void print(FastByteArrayList array) {
		System.out.print("𝙤𝙘𝙘𝙪𝙥𝙞𝙚𝙙 𝙨𝙞𝙯𝙚: ");
		System.out.print(""+array.length());
		System.out.print(" - 𝙧𝙚𝙖𝙡 𝙨𝙞𝙯𝙚: ");
		System.out.print(array.real_length());
		System.out.print(" - 𝙘𝙤𝙣𝙩𝙚𝙣𝙩𝙨: ");
		if (array.length() == 0 ) {
			System.out.println();
			return;
		}
		System.out.println(array.toString());
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastByteArrayList array = new FastByteArrayList(5);
		System.out.println("🞽🞽🞽 😎 𝗧𝗘𝗦𝗧𝗜𝗡𝗚 𝗙𝗮𝘀𝘁𝗕𝘆𝘁𝗲𝗔𝗿𝗿𝗮𝘆𝗟𝗶𝘀𝘁  😎🞽🞽🞽");
		print(array);
		System.out.println("𝘼𝙋𝙋𝙀𝙉𝘿𝙄𝙉𝙂 𝙀𝙇𝙀𝙈𝙀𝙉𝙏𝙎");
		array.add('a');
		array.add('b');
		array.add('c');
		array.add('d');
		array.add('e');
		array.add('f');
		print(array);
		System.out.println("𝘾𝙊𝙋𝙔𝙄𝙉𝙂 𝙏𝙃𝙀 𝘾𝙊𝙉𝙏𝙀𝙉𝙏𝙎 𝙏𝙊 𝘼𝙍𝙍𝘼𝙔 '𝙖'");
	    byte[] a = array.copy(); // a = { 'a','b','c','d','e','f'}
	    System.out.println("𝙖 = { '𝙖',' 𝙗', '𝙘', '𝙙', '𝙚', '𝙛' }");
	    System.out.println("𝘿𝙀𝙇𝙀𝙏𝙄𝙉𝙂 𝙏𝙃𝙀 𝙇𝘼𝙎𝙏 𝙀𝙇𝙀𝙈𝙀𝙉𝙏");
		array.delete();
		print(array);
		array.clear();
		System.out.println("𝙎𝙀𝙏𝙏𝙄𝙉𝙂 𝙒𝙄𝙏𝙃 { '𝔸','𝔻','𝔻','𝕀','ℕ','𝔾' }");
		array.set(adding);
		print(array);
		array.add(' ');
		System.out.print("𝙎𝙏𝙊𝙍𝙀 𝙏𝙃𝙀 𝙎𝙄𝙕𝙀 𝙊𝙁 𝙄𝙉𝙏𝙀𝙍𝙉𝘼𝙇 𝘼𝙍𝙍𝘼𝙔 𝙄𝙉 𝙑𝘼𝙍𝙄𝘼𝘽𝙇𝙀 𝙞𝙚 𝘼𝙉𝘿 ");
		System.out.println("𝘼𝙋𝙋𝙀𝙉𝘿 { '𝔼','𝕃','𝔼','𝕄','𝔼','ℕ','𝕋','𝕊' } ");
		int ie = array.length();
		array.append(elements);
		print(array);
		System.out.println("𝘼𝙋𝙋𝙀𝙉𝘿𝙄𝙉𝙂  ':' 𝘼𝙉𝘿 '𝟱'");
		array.add(':');
		array.add('5');
		print(array);
		System.out.println("𝙋𝙐𝙏 𝙎𝙋𝘼𝘾𝙀 𝙊𝙑𝙀𝙍 𝟱");
		array.put(' ', array.length() - 1);
		print(array);
		System.out.println("𝘼𝙋𝙋𝙀𝙉𝘿𝙄𝙉𝙂 𝘼𝙍𝙍𝘼𝙔 𝙖 = { '𝙖',' 𝙗', '𝙘', '𝙙', '𝙚', '𝙛' }");
		array.append(a);
		print(array);
		System.out.println("𝘿𝙀𝙇𝙀𝙏𝙄𝙉𝙂   { '𝙖',' 𝙗', '𝙘', '𝙙', '𝙚', '𝙛' }");
		array.delete(a.length);
		print(array);
		int i = array.length();
		System.out.println("𝙄𝙉𝘾𝙍𝙀𝘼𝙎𝙀 𝙎𝙄𝙕𝙀 𝙊𝙁 𝙄𝙉𝙏𝙀𝙍𝙉𝘼𝙇 𝘼𝙍𝙍𝘼𝙔 𝘼𝙉𝘿 𝘼𝙋𝙋𝙀𝙉𝘿  {  '𝙗', '𝙘', '𝙙', '𝙚' } 𝙒𝙄𝙏𝙃 𝘼 𝙋𝙐𝙏");
		array.increase(4);
		array.put(a, 1, 5, i);
		print(array);
		System.out.print("𝙀𝙓𝙏𝙍𝘼𝘾𝙏 { '𝔼','𝕃','𝔼','𝕄','𝔼','ℕ','𝕋','𝕊','𝙗','𝙘' } 𝙎𝙐𝘽𝘼𝙍𝙍𝘼𝙔, ");
		System.out.println("𝙎𝙀𝙏 𝙡𝙚𝙣𝙜𝙩𝙝 𝙏𝙊 𝙞𝙚, 𝘼𝙉𝘿 𝘼𝙋𝙋𝙀𝙉𝘿𝙎 𝙏𝙃𝙀 𝙎𝙐𝘽𝘼𝙍𝙍𝘼𝙔");
		byte[] elem = array.subArray(ie, i+2);
		array.set_length(ie);
		array.append(elem);
		print(array);
		System.out.println("𝘿𝙀𝙇𝙀𝙏𝙄𝙉𝙂  ':', ' ',  '𝙗' 𝘼𝙉𝘿 '𝙘'");
		array.delete(4);
		print(array);
		System.out.println("𝙋𝙐𝙏 '𝔸' 𝘼𝙏 𝙋𝙊𝙎𝙄𝙏𝙄𝙊𝙉 𝟭𝟮  𝘼𝙉𝘿 𝙞𝙚 +𝟭𝟮 𝘼𝙉𝘿 '𝔾' 𝘼𝙏 𝙋𝙊𝙎𝙄𝙏𝙄𝙊𝙉 𝙞𝙚 +𝟭𝟮");
		array.put(A, 12);
		// construct a G with an A
		byte[] G = A.clone();
		G[3] += 6;
		array.put(G, ie+12);
		array.put(A, ie+16);
		print(array);
		System.out.println("𝘿𝙀𝙇𝙀𝙏𝙄𝙉𝙂 𝙀𝙑𝙀𝙍𝙔𝙏𝙃𝙄𝙉𝙂");
		array.clear();
		print(array);
	}

}
