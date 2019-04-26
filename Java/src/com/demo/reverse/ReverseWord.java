package com.demo.reverse;

public class ReverseWord {
	
	public static String reverse(String str)
	{
		String word[]=str.split("\\s");
		String reverseWord="";
		for(String w:word)
		{
			StringBuilder sb=new StringBuilder(w);
			sb.reverse();
			reverseWord+=sb.toString()+" ";
		}
		return reverseWord;
	}
	

	public static void main(String[] args) {
		System.out.println(ReverseWord.reverse("How are you"));
		
	}

}
