package com.entrepidea.algo.leetcode.easy;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class MostRecent {
    public static void main(String[] args){
        //File[] files = Files.list().listRoots();
        String dir = ".\\src\\test\\java\\com\\entrepidea\\algo\\leetcode\\easy";

        Integer max = Arrays.asList(new File(dir).listFiles()).stream()
                .filter(f  -> !("MostRecent.java").equals(f.getName()))
                .mapToInt(file->new Integer (file.getName().substring(2,5))).max().orElseThrow(NoSuchElementException::new);

        System.out.println(max);
    }
}
