package com.entrepidea.parquet.main;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroSchemaConverter;
import org.apache.parquet.hadoop.metadata.ParquetMetadata;
import org.apache.parquet.schema.MessageType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.entrepidea.parquet.common.Utils.convert2HadoopPath;

/*
* This experiment shows how to read a parquet file and present the data in a Swing table component.
* If the testing file, employees.parquet is not there, run Table2ParquetWriter first.
*
* 06/22/24
*
* */
public class SwingParquetFileReader {
    private static final String PARQUET_FILE_PATH = "../data/parquet/employees.parquet";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Parquet Data Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            try {
                JTable table = new JTable();
                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane);
                loadParquetData(table);
            } catch (IOException e) {
                e.printStackTrace();
            }

            frame.setVisible(true);
        });
    }


    public static Schema getSchemaFromParquetFile(String parquetFilePath) throws IOException {
        Path path = convert2HadoopPath(parquetFilePath);
        Configuration conf = new Configuration();

        try (org.apache.parquet.hadoop.ParquetFileReader reader = org.apache.parquet.hadoop.ParquetFileReader.open(conf, path)) {
            ParquetMetadata readFooter = reader.getFooter();
            MessageType parquetSchema = readFooter.getFileMetaData().getSchema();
            return new AvroSchemaConverter().convert(parquetSchema);
        }
    }


    private static void loadParquetData(JTable table) throws IOException {

        Schema schema = getSchemaFromParquetFile(SwingParquetFileReader.PARQUET_FILE_PATH);
        List<Schema.Field> fields = schema.getFields();
        String[] columnNames = new String[fields.size()];
        int i =0;
        for(Schema.Field f : fields){
            columnNames[i++] = f.name();
        }

        AvroParquetReader<GenericRecord> reader = new AvroParquetReader<>(new Configuration(), convert2HadoopPath(SwingParquetFileReader.PARQUET_FILE_PATH));
        GenericRecord nextRecord;
        List<Object[]> rows = new ArrayList<>();
        int rowCount = 0;
        while((nextRecord = reader.read())!=null){
            Object[] row = new Object[fields.size()];
            i=0;
            for(Schema.Field f : fields) {
                row[i++] = nextRecord.get(f.name());
            }
            rows.add(row);
            rowCount++;
        }
        Object[][] data = new Object[rowCount][fields.size()];
        for(i=0;i<rowCount;i++) {
            Object[] row = rows.get(i);
            System.arraycopy(row, 0, data[i], 0, row.length);
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
    }
}
