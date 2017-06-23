using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace entrep.nutshell.tests.Basics
{
    [TestClass]
    public class BasicsTests
    {
        [TestMethod]
        //Test basic types' conversion
        //http://stackoverflow.com/questions/10754251/converting-a-double-to-an-int-in-c-sharp
        public void TestCasting()
        {
            int notional = 1000;
            string key = "test "+notional;
            Console.WriteLine(key);
            double notional2 = 1000.00;
            key = "test2 " + Math.Round(notional2);
            Console.WriteLine(key);

            key = "test3 " + Convert.ToInt32(notional2);
            Console.WriteLine(key);

        }

        
        /*
         Excel has its own date format, it might look like 46204.
         Bwlow test shows how to convert Excel date to a C# DateTime instance.
         The code was from a Stackoverflow post, link forgotten but can be easily googled with the proper key words
             
             */
        public static DateTime FromExcelSerialDate(int SerialDate)
        {
            if (SerialDate > 59) SerialDate -= 1; //Excel/Lotus 2/29/1900 bug   
            return new DateTime(1899, 12, 31).AddDays(SerialDate);
        }

        [TestMethod]
        public void TestTime() {
            DateTime dt = FromExcelSerialDate(46204);
            string format = "yyyyMMdd";
            Console.WriteLine("   {0}: {1}", format,dt.ToString(format));
        }

    }
}
