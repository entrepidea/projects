package com.entrepidea.parquet.service;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

@Service
public class DatabaseService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void fetchDataInBatch(String sql) {
        jdbcTemplate.query(sql, new MyRowCallbackHandler());
    }

    private static class MyRowCallbackHandler implements RowCallbackHandler {

        private static final int BATCH_SIZE = 1000; // Adjust batch size as needed
        private int rowCount = 0;

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            Schema schema = buildSchemaFromResultSet(rs);



            try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(new Path("file:///C:\\Users\\Jon\\projects\\github\\java\\java_playground\\data\\parquet\\output.parquet"))
                    .withSchema(schema) // Define schema based on data
                    .withConf(new Configuration())
                    .build()) {

            while (rs.next()) {
                    // Access columns by name or index
                    int id = rs.getInt("EMPLOYEE_ID");
                    String fName = rs.getString("FIRST_NAME");
                    String lName = rs.getString("LAST_NAME");
                    String email = rs.getString("EMAIL");
                    String phoneNum = rs.getString("PHONE_NUMBER");
                    String hireDate = rs.getDate("HIRE_DATE").toString();
                    String jobId = rs.getString("JOB_ID");
                    int salary = rs.getInt("SALARY");
                    int commPct = rs.getInt("COMMISSION_PCT");
                    int mgrId = rs.getInt("MANAGER_ID");
                    int deptId = rs.getInt("DEPARTMENT_ID");
                    // Access other columns similarly

                GenericRecord record = new GenericData.Record(schema);
                    record.put("EMPLOYEE_ID", id);
                    record.put("FIRST_NAME", fName);
                    record.put("LAST_NAME", lName);
                    record.put("EMAIL", email);
                    record.put("PHONE_NUMBER", phoneNum);
                    record.put("HIRE_DATE", hireDate);
                    record.put("JOB_ID", jobId);
                    record.put("SALARY", salary);
                    record.put("COMMISSION_PCT", commPct);
                    record.put("MANAGER_ID", mgrId);
                    record.put("DEPARTMENT_ID", deptId);

                    writer.write(record);
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            // Process each row
            // Example: Log progress every BATCH_SIZE rows
            if (++rowCount % BATCH_SIZE == 0) {
                System.out.println("Processed " + rowCount + " rows");
            }
        }
    }

    public static Schema buildSchemaFromResultSet(ResultSet resultSet) throws SQLException {
        SchemaBuilder.RecordBuilder<Schema> recordBuilder = SchemaBuilder.record("Record");
        SchemaBuilder.FieldAssembler<Schema> fieldAssembler = recordBuilder.fields();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            int columnType = metaData.getColumnType(i);
            switch (columnType) {
                case java.sql.Types.NUMERIC:
                case java.sql.Types.INTEGER:
                    fieldAssembler.name(columnName).type().intType().noDefault();
                    break;
                case Types.DATE:
                case java.sql.Types.VARCHAR:
                    fieldAssembler.name(columnName).type().stringType().noDefault();
                    break;
                // Add cases for other supported types as needed
                default:
                    // Handle unsupported types or add support for more types
                    //throw new IllegalArgumentException("Unsupported column type: " + columnType);
                    fieldAssembler.name(columnName).type().stringType().noDefault();
                    break;
            }
        }

        return fieldAssembler.endRecord();
    }
}
