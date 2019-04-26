package com.demo.pattern;

import java.util.Scanner;

public class Star1 {

	public static void main(String[] args) {
		int i,j;
		for(i=1;i<=6;i++)
		{
			for(j=1;j<=i;j++)
			{
				System.out.print(j +" ");
			}
			System.out.println("");
		}
		System.out.println("====================");
		
		for(i=6;i>=1;i--)
		{
			for(j=1;j<=i;j++)
			{
				System.out.print(j+" ");
			}
			System.out.println("");
		}
		
		System.out.println("=========================");
		
//*************************************************************************************************		
		Scanner sc=new Scanner(System.in);
		System.out.println("How many rows you want in this pattern?");
		int rows=sc.nextInt();
		
		for(int k=1;k<=rows;k++)
		{
			for(int l=1;l<=k;l++)
			{
				System.out.print(l+" ");
			}
			System.out.println("");
		}
		
		for(int k=rows;k>=1;k--)
		{
			for(int l=1;l<=k;l++)
			{
				System.out.print(l+" ");
			}
			System.out.println("");
		}
		System.out.println("===========================");
		for(int k=1;k<=rows;k++)
		{
			for(int l=rows;l>=k;l--)
			{
				System.out.print(l+" ");
			} 
			System.out.println("");
		}
			
			
	}
	
	

}

