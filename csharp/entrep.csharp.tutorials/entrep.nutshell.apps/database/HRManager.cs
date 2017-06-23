using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Text;

using Oracle.DataAccess.Client;


namespace entrep.nutshell.apps.database
{

    /**
         This is a testing class to operate against the HR tables in Oracle XE.
         The code demostrates how to use OracleDataAccess API to connect a oracle database and how to load the resultset into a DataTable.
         The connect string patten is referred from here:    http://www.oracle.com/technetwork/articles/dotnet/vs2010-oracle-dev-410461.html
         
         */
    public class HRManager
    {
        private readonly string connStr = "Data Source=(DESCRIPTION="
             + "(ADDRESS=(PROTOCOL=TCP)(HOST=LOCALHOST)(PORT=1521))"
             + "(CONNECT_DATA=(SERVICE_NAME=XE)));"
             + "User Id=hr;Password=hr;";
        private OracleConnection conn = null;
        private bool connected = false;

        public HRManager() { }

        public void connect() {
            conn = new OracleConnection();
            conn.ConnectionString = connStr;
            conn.Open();
            connected = true;
        }

        public bool isConnected() {
            return connected;
        }

        public List<string> GetAllEmployeeNames() {
            string sql = " select e.last_name, e.first_name, d.department_name from employees e left outer join departments d on e.department_id = d.department_id";
            DataTable dt = new DataTable();
            using (OracleDataAdapter adapter = new OracleDataAdapter(sql, conn)) {
                adapter.Fill(dt);
            }

            if (dt.Rows.Count != 0) {
                StringBuilder sb = new StringBuilder();
                foreach (DataColumn col in dt.Columns) {
                    sb.Append(col);
                    sb.Append(',');
                }
                Console.WriteLine(sb.ToString());

                foreach (DataRow row in dt.Rows) {
                    Console.WriteLine(string.Join(",", row.ItemArray));
                }
            }
            return null;
        }

        //TODO
        //goal: to get SQL strings which are orgnized in a text file.
        public string RetrieveSql(string fileName) {
            string sql = "";
            using (StreamReader sr = new StreamReader(fileName)) {
            }
            return sql;
        }

        public static void Run() {

            HRManager hrm = new HRManager();
            hrm.connect();
            if (hrm.isConnected())
            {
                hrm.GetAllEmployeeNames();
            }
        }

    }
}
