package com.entrepidea.core.database;

    /*
    *   TODO 10/01/14, 5:30PM, BofA phone interview with Wilson
    *     1. What's Union and Union all?
          2. explain left join and inner join
          3. There is a table "Salary" with two columns "id" and "salary", this is oracle, write sql to list first 4. 100 record with salary from highest to lowest
          4. How do you dump the data from table A to table B, if table B doesn't exist. Do it in one SQL statement.
          5. Difference b/w function and store procedure
          6. What's view? What's trigger?
          7. How to call a store procedure from Java? The JDBC API.
          8. What are the transaction isolation levels?
          9. How do you get the current date using Oracle?
          10. In PL/SQL, how do you throw an exception and how to catch it?
    *
    * **/

        //10/01/14, 4:30 PM, BofA phone interview
        //TODO Deadlock in database - what's the cause? when it happens, how is it being handled?
        //TODO Left join, explain.

        //Alliance Bernstein, 08/14/14, phone interview
        //TODO If there is a store proc running slow after a while, what's the cause and how to tackle it?


        //Phone Interview with BofA, 07/11/14
        //TODO Transaction levels.
        //TODO Cluster index.

        //3 rounds of Interview with Neuberger Berman
        //TODO 3. deisgn a table with performance in mind,  what would be your approach ?  it's millions and millinos records and index won't help

        /**
         * BNP Paribas onsite, 02/18/20
         * Table:
         * Dept  Employee  Salary
         * D1     E1              10K
         * D1     E2              15K
         * D2     E3              9K
         * D2     E4              17K
         *
         * find out the highest paid employees from respective departments?
         * D1   E2
         * D2   E4
         *
         * */
        //select s.dept,s.emp from Salary s, (select max(salary) as top_money, dept from Salary group by dept) m where s.salary = m.top_money ;
