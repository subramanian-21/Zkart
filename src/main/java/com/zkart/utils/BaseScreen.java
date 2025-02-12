package com.zkart.utils;

import java.util.Scanner;

public abstract class BaseScreen implements BaseView {
    @Override
    public void navigate(BaseView baseView) {
        baseView.display();
    }
    @Override
    public void display() {
        System.out.println("PAGE Not Found");
    }
    Scanner scanner;
    public BaseScreen() {
        scanner = new Scanner(System.in);
    }

    public int getInt(String msg) {
        int num = 0;
        while (true) {
            try {
                System.out.print(msg);
                num = scanner.nextInt();
                break;
            }catch (Exception e){
                alert("Enter a valid Number");
                scanner.nextLine();
            }
        }
        return num;
    }
    public String getString(String msg) {
        System.out.print(msg);
        return scanner.next();
    }
    public String getStringLine(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }
    public boolean getBoolean ( String msg ) {
        while (true) {
            System.out.print(msg+" (y/n) :");
            String temp = scanner.next();
            if(temp.length() > 1) {
                System.out.println("Invalid Character input");
                continue;
            }
            if(temp.charAt(0) == 'y') {
                return true;
            }
            if(temp.charAt(0) == 'n') {
                return false;
            }
            System.out.println("Invalid Character Input");
        }
    }
    public void header(String page) {
        System.out.println("======================================");
        System.out.println("        "+page);
        System.out.println("======================================");
    }
    public void alert(String msg) {
        System.out.println("********** "+msg+" **********");
    }
}
