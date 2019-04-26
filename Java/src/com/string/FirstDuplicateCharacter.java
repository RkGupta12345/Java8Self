package com.string;

public class FirstDuplicateCharacter {

	private static char findFirstDuplicateCharacter(String str) throws RuntimeException
	{
		checkForInvalidInput(str);
		char[] ch=str.toCharArray();
		
		int length=ch.length;
		
		for(int i=0;i<length;i++)
		{
			for(int j=i+1;j<length;j++)
			{
				if(ch[i]==ch[j])
				{
					return ch[j];
				}
			}
		}
		throw new RuntimeException("There is no duplicate character in String::");
	}
	
	private static void checkForInvalidInput(String str)
	{
		if(str==null || str.isEmpty())
		{
			throw new RuntimeException("Input Is Invalid::");
		}
	}
	
	public static void main(String[] args) {
		String str="Ravi Kumar";
		
		try {
			System.out.println("First Duplicate Character in String::==>"+findFirstDuplicateCharacter(str));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
