package com.javapython.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class JavaPythonTestPassword {
    static String password = "taco";
    static String username = "QazGuyChess";
    private static final String correctPassword = "HelloAdvay";
    static boolean correct = false;
    static String fancyCorrect = "";

    public static void main(String[] args) {
        correct = checkPassword(password);
        fancyCorrect = correct ? "CORRECT" : "INCORRECT";

        LocalDateTime now = LocalDateTime.now();
        String formatted =
                now.getDayOfMonth() + "/" +
                        now.getMonthValue() + "/" +
                        now.getYear() + " " +
                        now.getHour() + ":" +
                        String.format("%02d", now.getMinute()) + ":" +
                        String.format("%02d", now.getSecond());

        File file = new File("../data/PasswordNotes.txt");
        System.out.println("Writing to file at: " + file.getAbsolutePath() + " ...");

        try (FileWriter writer = new FileWriter(file, true)) {
            if (file.length() != 0) {
                writer.write("\n\n");
            }
            writer.write("Password: " + fancyCorrect + " on " + formatted + " by " + username);
            System.out.println("FILE WRITE: Success! :)");
        } catch (IOException e) {
            System.out.println("FATAL ERROR: Failed to write to file. :(");
            e.printStackTrace();
        }
    }

    public static boolean checkPassword(String input) {
        return input.equals(correctPassword);
    }
}