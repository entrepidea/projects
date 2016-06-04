using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;


/*
desc: anonymous function in C# is called lambda. Anonymous functions have different uses such as sorting, closure, currying and more complex ones
such as map, filter and fold.
link: https://en.wikipedia.org/wiki/Anonymous_function
*/
namespace entrep.nutshell.tests.Lambda
{
    /// <summary>
    /// Summary description for LambdaTests
    /// </summary>
    [TestClass]
    public class LambdaTests
    {
        public LambdaTests()
        {
        }

        private TestContext testContextInstance;

        /// <summary>
        ///Gets or sets the test context which provides
        ///information about and functionality for the current test run.
        ///</summary>
        public TestContext TestContext
        {
            get
            {
                return testContextInstance;
            }
            set
            {
                testContextInstance = value;
            }
        }

        #region Additional test attributes
        //
        // You can use the following additional attributes as you write your tests:
        //
        // Use ClassInitialize to run code before running the first test in the class
        // [ClassInitialize()]
        // public static void MyClassInitialize(TestContext testContext) { }
        //
        // Use ClassCleanup to run code after all tests in a class have run
        // [ClassCleanup()]
        // public static void MyClassCleanup() { }
        //
        // Use TestInitialize to run code before running each test 
        // [TestInitialize()]
        // public void MyTestInitialize() { }
        //
        // Use TestCleanup to run code after each test has run
        // [TestCleanup()]
        // public void MyTestCleanup() { }
        //
        #endregion

        [TestMethod]
        public void TestMethod1()
        {
            Func<int, int> foo = (x) => x * x;
            Console.WriteLine(foo(5));
            Assert.AreEqual(25, foo(5));
        }

        [TestMethod]
        public void PredicateTest() {
            List<int> l = new List<int>();
            for(int i = 0; i < 10; i++)
            {
                l.Add(i + 1);
            }

            int n = l.FindLast(x => x < 10);
            Console.WriteLine("The largest number less than 10 is: {0}", n);
            Assert.AreEqual(9, n);
        }
    }
}
