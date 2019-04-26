package com.demo.pattern;

public class Pattern {

    public static void main(String[] args) {
        int rows = 5, k = 0;

        for(int i = 1; i <= rows; i++, k = 0) {
            for(int j = 1; j <= rows - i; j++) {
                System.out.print("  ");
            }

            while(k != 2 * i - 1) {
                System.out.print("* ");
                ++k;
            }

            System.out.println();
        }
        System.out.println("============+++++++++++++===============");
        
        /*int row = 4, number = 1;

        for(int i = 1; i <= row; i++) {

            for(int j = 1; j <= i; j++) {
                System.out.print(number + " ");
                ++number;
            }

            System.out.println();
        }*/
    
    }
}