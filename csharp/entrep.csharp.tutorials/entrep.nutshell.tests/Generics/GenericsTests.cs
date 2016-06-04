using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.VisualStudio.TestTools.UnitTesting;


/*
desc: tests of C# generics
link: http://www.tutorialspoint.com/csharp/csharp_generics.htm
*/
namespace UnitTestProject1.Generics
{
    [TestClass]
    public class GenericsTests
    {
        [TestMethod]
        public void TestMethod1()
        {
            //declaring an int array
            MyGenericArray<int> intArray = new MyGenericArray<int>(5);

            //setting values
            for (int c = 0; c < 5; c++)
            {
                intArray.setItem(c, c * 5);
            }

            //retrieving the values
            for (int c = 0; c < 5; c++)
            {
                Console.Write(intArray.getItem(c) + " ");
            }

            Console.WriteLine();
        }
    }
}
