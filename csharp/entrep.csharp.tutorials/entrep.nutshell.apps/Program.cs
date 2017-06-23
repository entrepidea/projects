using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using entrep.nutshell.apps.database;

namespace entrep.nutshell.apps
{
    class Program
    {
        static readonly string AppName = "entrep.nutshell.apps";
        static readonly string readme = string.Format("This project ({0}) is meants to create small, runnable console apps for learning purpose.", AppName);

        static void Readme() {
            Console.WriteLine(readme);
        }



        static void Main(string[] args)
        {
            //Readme();
            HRManager.Run();
        }
    }
}
