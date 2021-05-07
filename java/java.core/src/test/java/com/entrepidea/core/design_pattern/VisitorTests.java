package com.entrepidea.core.design_pattern;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @Desc:
 * Visitor pattern involves two abstract/interface types, with referencing to each other. When the method of one type is invoked, the strategy is implemented inside the other.
 * JDK example include FileVistor and SimpleFileVisitor
 *
 * @Date: 04/16/20
 *
 * */
public class VisitorTests {
    /**
     * BNP Paribas on-site, 02/28/20
     * what's the visitor pattern?
     * */
    //Visitor pattern creates a separate visitor class to offer functionalities for a working class. Therefore, once new functions are added, the working class remains intact.
    //Example: JDK's FileVisitor interface and SimpleFileVisitor class.
    //this link: https://howtodoinjava.com/java/io/delete-directory-recursively/ shows how to use JDK API - SimpleFileVisitor to remove folder/files recursively
    @Test
    public void testDeleteDirectory(){
        Path dir = Paths.get("c:/temp/innerDir");
        try
        {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>()
            {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException
                {
                    System.out.println("Deleting file: " + file);
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir,
                                                          IOException exc) throws IOException
                {
                    System.out.println("Deleting dir: " + dir);
                    if (exc == null) {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    } else {
                        throw exc;
                    }
                }

            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
