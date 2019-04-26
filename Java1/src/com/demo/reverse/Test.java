package com.demo.reverse;

public class Test {

	public static void main(String[] args) {
		String s="Ravi";
		char[] arr=s.toCharArray();
		for(int i=arr.length-1; i>=0; i--)
		{
			System.out.print(arr[i]);
		}
		
		
		StringBuilder builder=new StringBuilder();
		builder.append(s);
		builder=builder.reverse();
		System.out.println("Reverse String ::==>"+builder);
	}

}
