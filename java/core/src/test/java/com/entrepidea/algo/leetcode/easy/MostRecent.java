package com.entrepidea.algo.leetcode.easy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class MostRecent {
    public static void main(String[] args) throws IOException {
        //File[] files = Files.list().listRoots();
        String dir = "java.core/src/test/java/com/entrepidea/algo/leetcode/easy";
        Integer max = Arrays.stream(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(f  -> !("MostRecent.java").equals(f.getName()))
                .mapToInt(file-> Integer.parseInt(file.getName().substring(2, 5))).max().orElseThrow(NoSuchElementException::new);

        System.out.println(max);

        //TODO show names of the files that contains keyword "//TODO"


    }
}
