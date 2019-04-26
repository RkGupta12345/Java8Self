package com.demo.reverse;

public class TestStringFormatter {

	public static String toggle(String str) {
		String words[] = str.split("\\s");
		String reverse = "";
		for (String w : words) {
			String first = w.substring(0,1);
			String afterFirst = w.substring(1);
			reverse += first+ afterFirst.toUpperCase() + " ";
		}
		return reverse;
	}

	public static void main(String[] args) {
		System.out.println(TestStringFormatter.toggle("good morning"));

	}
}
