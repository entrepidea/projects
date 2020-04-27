package com.entrepidea.core.database;

import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.entrepidea.core.database.JDBCC3P0DataSourceTests.*;
/**
 * @Desc: Load generic DriverManager to establish a sql.Connection.
 *
 *
 * @Note: version of the underlying MySql database on evolution is 5.x.x. If you use the latest MyDql driver (it's 8.x.x as of 04/2020), it won't work.
 * This is why in this POM.xml, the MySql version is 5.1.25. Beware of it.
 *
 * @Date: 04/25/20
 *
 * */
public class JDBCDriverManagerTests {

    private static final Logger log = LoggerFactory.getLogger(JDBCDriverManagerTests.class);

    /**
     * 10/15/14, Markit on site
     *
     * JDBC - We have 50 threads, can they use the same Connection instance to create Statement and operate on database? query/update/delete, etc
       What's the difference PreparedStatement and regular Statement, beside the obvious ones?
     * */
    //answers: TODO


    @Before
    public void setup(){
        BasicConfigurator.configure();
    }

    //below is the most basic way to connect to a MySql database and
    private int[] batchInsertion(final String conStr, final int batchSize, final boolean enableLog){

        long start = System.currentTimeMillis();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection con = DriverManager.getConnection(conStr)){
            con.setAutoCommit(false);
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO "
                    + "Books(isbn,title,"
                    + "edition,price) VALUES (?,?,?,?)");

            for(int i=0;i<batchSize;i++) {
                String isbn = "WEG3477STG";
                String title = "Harry Porter";
                String edition = "1";
                float price = 20.99f;

                pstmt.setString(1, isbn);
                pstmt.setString(2, title);
                pstmt.setString(3, edition);
                pstmt.setDouble(4, price);
                pstmt.addBatch();
            }
            if(enableLog) {
                log.info("{} statements added to batch, before execution, taking {} millseconds. \n", batchSize, (System.currentTimeMillis() - start));
            }

            start = System.currentTimeMillis();
            int[] ret = pstmt.executeBatch();

            if(enableLog) {
                log.info("Complete executing the batch({}), it has taken {} millseconds. \n", batchSize, (System.currentTimeMillis() - start));
            }

            con.commit();
            return ret;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    @Test
    public void testGenericConnection(){
        final String conStr = "jdbc:mysql://127.0.0.1:3306/entrepidea?user=aaron&password=f0rever";
        final int batchSize = 1000000;
        int[] ret = batchInsertion(conStr, batchSize, true);
        Assert.assertTrue(ret.length==batchSize);
    }

    //as per this article, https://dzone.com/articles/performant-batch-inserts-using-jdbc,
    //appending the line "rewriteBatchedStatements=true" can dramatically speed up the transaction -
    // the result seemed agree to this assertion. 9 sec with vs. 202 sec w/o for 1 million insertions.
    //for 10 millions insertions, the below takes 126 sec, and the heap size was set to 6g.
    @Test
    public void testGenericConnection2(){
        final String conStr = "jdbc:mysql://127.0.0.1:3306/entrepidea?rewriteBatchedStatements=true&user=aaron&password=f0rever";
        final int batchSize = 10000000;
        int[] ret = batchInsertion(conStr, batchSize, true);
        Assert.assertTrue(ret.length==batchSize);
    }

    //for 10 millions insertions, the below takes 52 seconds, about half time of that when single thread is employed.
    @Test
    public void testConcurrent() throws InterruptedException {

        final long start = System.currentTimeMillis();

        System.out.println(Runtime.getRuntime().availableProcessors());
        final int batchSize = 10000000;
        final int nCore = Runtime.getRuntime().availableProcessors();
        final int batchSizePerCpuCore = batchSize/nCore;
        final CountDownLatch taskCounter = new CountDownLatch(nCore);
        final CountDownLatch kickOffLatch = new CountDownLatch(1);
        final String conStr = "jdbc:mysql://127.0.0.1:3306/entrepidea?rewriteBatchedStatements=true&user=aaron&password=f0rever";
        ExecutorService es = Executors.newFixedThreadPool(nCore);
        for(int i=0;i<nCore;i++) {
            es.submit(() -> {
                int[] ret = batchInsertion(conStr, batchSizePerCpuCore,false);
                taskCounter.countDown();
            });
        }

        es.shutdown();
        kickOffLatch.countDown();
        taskCounter.await(); //set the barrier here, blocking until all working threads are completed.
        log.info("It has taken {} milliseconds to complete the insertion of {} rows. ", (System.currentTimeMillis()-start), batchSize);
    }


}
