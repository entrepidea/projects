using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace entrep.nutshell.tests.Basics
{
    [TestClass]
    public class StringTests
    {
        [TestMethod]
        public void TestMethod1()
        {
            String notional = "1000.0000";
            int idx = notional.IndexOf('.');

            if (idx != -1)
            {
                notional = notional.Substring(0, idx);
            }

            Console.WriteLine(string.Format("the index = {0}, integral part of the notional={1}",idx, notional));

            Assert.AreEqual("1000", notional);

        }
    }
}
