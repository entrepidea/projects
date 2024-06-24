package com.entrepidea.parquet.common;

import org.apache.hadoop.fs.Path;

public class Utils {
    public static Path convert2HadoopPath(String filePath){
        java.nio.file.Path nioPath = java.nio.file.Paths.get(filePath);
        return new Path(nioPath.toUri());
    }
}
