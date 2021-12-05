package com.company;

import java.util.Scanner;

public class Input {

    public static String inputString() {

        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    public static float inputFloat() {

        Scanner input = new Scanner(System.in);
        return input.nextFloat();
    }

    public static int inputInt() {

        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }

}
