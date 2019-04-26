package com.demo.fibonacci;

public class FibonacciExample {

	public static void main(String[] args) {
		int maxNumber=5;
		int previousNumber=0;
		int nextNumber=1;
		
		System.out.println("Fibonacci Series of "+maxNumber+" numbers:");
		
		for(int i=1; i<=maxNumber;++i)
		{
			int sum=previousNumber+nextNumber;
			System.out.println(previousNumber+"");
			previousNumber=nextNumber;
			nextNumber=sum;
		}

	}

}
