package com.javapython.misc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class CommentWriter {
    public static void main(String[] args) {
        String comment = "This is a sample comment.";
        File file = new File("../data/comments.txt");
        System.out.println("Writing comment to file at: " + file.getAbsolutePath() + " ...");

        try (FileWriter writer = new FileWriter(file, true)) {
            if (file.length() != 0) {
                writer.write("\n\n");
            }
            writer.write("*** " + comment + " ***");
            System.out.println("FILE WRITE: Success! :)");
        } catch (IOException e) {
            System.out.println("FATAL ERROR: Failed to write to file. :(");
            e.printStackTrace();
        }
    }
}
