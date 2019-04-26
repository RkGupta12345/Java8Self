package com.demo.pailndrome;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		String str,rev="";
		
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter The String");
		str=sc.next();
		
		int length=str.length();
		System.out.println("length::=>"+length);
		
		for(int i=length-1;i>=0;i--)
			rev=rev+str.charAt(i);
		System.out.println("rev::=>"+rev);
		
		if(rev.equals(str))
		{
			System.out.println(str +"-> Given String Is Palindrome::");
			
				
			}
		else {
			System.out.println(str +"=> String Is Not a Palindrome:");
		}
	

}
}
