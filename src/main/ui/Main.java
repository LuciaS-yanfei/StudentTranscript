package ui;

import javax.swing.*;
import java.io.FileNotFoundException;

//represents main
public class Main {
    public static void main(String[] args) {
//        String input = JOptionPane.showInputDialog("Enter name");
        try {
            new StudentService();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
