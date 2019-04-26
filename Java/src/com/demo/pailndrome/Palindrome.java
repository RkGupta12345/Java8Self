package com.demo.pailndrome;

public class Palindrome {

	public static void main(String[] args) {
		String s="radar";
		StringBuilder builder=new StringBuilder(s);
		builder.reverse();
		String rev=builder.toString();
		
		if(rev.equals(s))
		{
			System.out.println("Palindrome String"); 
		}
		else {
			System.out.println("Not Palindrome String");  
		}

	}

}
