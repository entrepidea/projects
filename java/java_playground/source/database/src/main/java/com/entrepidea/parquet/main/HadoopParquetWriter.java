package com.entrepidea.parquet.main;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.column.ParquetProperties;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;

import java.io.IOException;

/*
* This program is to test writing a sample parquet file into the single-node Hadoop cluster.
* start the Hadoop name and data node from this link: http://entrepidea.com/wiki/index.php/Linux#Apache_Hadoop
* TODO this is not exactly working yet.
*
* 06/23/24
*
* */
public class HadoopParquetWriter {
    public static void main(String[] args) throws IOException {
        // Set Hadoop configuration
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.86.41:9000");

        // Define the schema
        String schemaString = "message example { required binary name (UTF8); required int32 age; }";
        MessageType schema = MessageTypeParser.parseMessageType(schemaString);
        GroupWriteSupport.setSchema(schema, conf);

        // Create a ParquetWriter
        //FileSystem fs = FileSystem.get(conf);
        int i = 0;
        for (ParquetProperties.WriterVersion version : ParquetProperties.WriterVersion.values()) {
            String filePath = "hdfs://192.168.86.41:9000/user/Jon/example"+i+".parquet";
            Path path = new Path(filePath);
            try (ParquetWriter<Group> writer = new ParquetWriter<>(path, new GroupWriteSupport(), CompressionCodecName.SNAPPY,
                    ParquetWriter.DEFAULT_BLOCK_SIZE, ParquetWriter.DEFAULT_PAGE_SIZE, 512, true, false, version, conf)) {
                SimpleGroupFactory groupFactory = new SimpleGroupFactory(schema);

                // Write data
                for (i = 0; i < 100; i++) {
                    Group group = groupFactory.newGroup()
                            .append("name", "name" + i)
                            .append("age", i);
                    writer.write(group);
                }
            }
        }
    }
}
