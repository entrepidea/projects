package com.entrepidea.core.database;

import com.entrepidea.core.database.supports.C3P0DataSource;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @Desc: difference b/w a DataSource and DriverManager is that the former support Connection pool thus saving the expensive overhead such as:
 * The JDBC driver requests a JVM socket. -> security check -> penetrate firewall, etc
 *
 * @Source: https://www.developer.com/java/data/understanding-jdbc-connection-pooling.html
 *
 * @Date: 04/26/20
 *
 * */
public class JDBCC3P0DataSourceTests {

    private static final Logger log = LoggerFactory.getLogger(JDBCC3P0DataSourceTests.class);


    @Before
    public void setup(){
        BasicConfigurator.configure();
    }

    public static void insertOrUpdate(String isbn, String title,
                                      String edition, float price) {
        try (Connection con = C3P0DataSource.getInstance()
                .getConnection()) {
            con.setAutoCommit(false);
            PreparedStatement pstmt=null;
            if(isIsbnExists(isbn)){
                pstmt = con.prepareStatement("UPDATE Books "
                        + "SET title=?,edition=?,"
                        + "price=? WHERE isbn LIKE ?");
                pstmt.setString(1, title);
                pstmt.setString(2, edition);
                pstmt.setFloat(3, price);
                pstmt.setString(4, isbn);
            }else{
                pstmt = con.prepareStatement("INSERT INTO "
                        + "Books(isbn,title,"
                        + "edition,price) VALUES (?,?,?,?)");
                pstmt.setString(1, isbn);
                pstmt.setString(2, title);
                pstmt.setString(3, edition);
                pstmt.setFloat(4, price);
            }
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isIsbnExists(String isbn) {
        Boolean flag = false;
        try (Connection con = C3P0DataSource.getInstance()
                .getConnection()) {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT isbn "
                    +"FROM Books WHERE "
                    +"isbn LIKE '"+isbn+"'");
            flag=rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void delete(String isbn) {
        try (Connection con = C3P0DataSource.getInstance()
                .getConnection()) {
            PreparedStatement pstmt=null;
            if(isIsbnExists(isbn)){
                pstmt = con.prepareStatement("DELETE FROM "
                        + "Books "
                        + "WHERE isbn LIKE ?");
                pstmt.setString(1, isbn);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showAll() {
        try (Connection con = C3P0DataSource.getInstance().getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Books");
            ResultSetMetaData metadata = rs.getMetaData();
            int cols = metadata.getColumnCount();
            System.out.println("\n-----------------------------"
                    + "--------------------------------");
            for (int i = 0; i < cols; i++) {
                System.out.printf("%-20s\t",
                        metadata.getColumnName(i + 1).toUpperCase());
            }
            System.out.println("\n-----------------------------"
                    + "--------------------------------");
            while (rs.next()) {
                for (int i = 0; i < cols; i++)
                    System.out.printf("%-20s\t", rs.getObject(i + 1));
                System.out.println();
            }
            System.out.println("-------------------------------"
                    + "--------------------------------");
        }
      catch (SQLException e) {
                e.printStackTrace();
            }
        }



    @Test
    public void test(){
        showAll();
        insertOrUpdate("111111", "Complex Numbers",
                "Second", 56.78f);
        showAll();
        insertOrUpdate("111111", "Complex Numbers",
                "Fourth", 87.50f);
        showAll();
        //delete("111111");
        //showAll();
    }

    //TODO problematic - fix it!
    @Test
    public void testInsertManyRows(){

        try (Connection con = C3P0DataSource.getInstance().getConnection()) {

            long start = System.currentTimeMillis();

            con.setAutoCommit(false);

            int batchSize = 2;
            PreparedStatement pstmt = null;
            for(int i=0;i<batchSize;i++) {

                pstmt = con.prepareStatement("INSERT INTO "
                        + "Books(isbn,title,"
                        + "edition,price) VALUES (?,?,?,?)");

                String isbn = String.valueOf(i);
                String title ="Shalock Homes";
                String edition = "5th Ed";
                float price = 23.99f;

                pstmt.setString(1, isbn);
                pstmt.setString(2, title);
                pstmt.setString(3, edition);
                pstmt.setFloat( 4, price);
                pstmt.addBatch();
            }

            int[] rets = pstmt.executeBatch();
            con.commit();
            Assert.assertEquals(rets.length, batchSize);
            log.info("It has taken {} milliseconds to complete the insertion of {} rows. ", (System.currentTimeMillis()-start), batchSize);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

}
