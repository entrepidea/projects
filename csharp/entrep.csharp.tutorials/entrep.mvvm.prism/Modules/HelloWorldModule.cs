using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Practices.Prism.Modularity;
using Microsoft.Practices.Prism.Regions;

namespace entrep.mvvm.prism.Modules
{
    class HelloWorldModule : IModule
    {
        private readonly IRegionManager regionManager;

        public HelloWorldModule(IRegionManager regionManager)
        {
            this.regionManager = regionManager;
        }

        public void Initialize()
        {
            regionManager.RegisterViewWithRegion("MainRegion", typeof(View.HelloWorldView));
        }
    }
}
