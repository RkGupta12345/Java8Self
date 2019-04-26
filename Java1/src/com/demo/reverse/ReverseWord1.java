package com.demo.reverse;

public class ReverseWord1 {
	
	public static String reverse(String str)
	{
		String words[]=str.split("\\s");
		String reverseWord="";
		for(String s:words)
		{
			StringBuilder sb=new StringBuilder(s);
			sb.reverse();
			String first=sb.substring(0, 1);
			String afterFirst=sb.substring(1);
			reverseWord+=first.toLowerCase()+afterFirst.toUpperCase()+" ";
		}
		return reverseWord;
	}

	public static void main(String[] args) {
		System.out.println(ReverseWord1.reverse("Ravi Kumar"));
		
	}

}
