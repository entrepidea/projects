package jide;

import com.jidesoft.app.framework.file.TextFileFormat;
import com.jidesoft.app.framework.gui.*;
import com.jidesoft.app.framework.gui.filebased.FileBasedApplication;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.docking.DockableHolder;
import com.jidesoft.docking.DockingManager;
import com.jidesoft.icons.JideIconsFactory;

import javax.swing.*;
import java.awt.*;


/**
 * Created by jonat on 9/10/2017.
 */
public class DockedTextEditor extends FileBasedApplication {

    public static void main(String[] args) {
        com.jidesoft.utils.Lm.verifyLicense("Entrepidea, Inc", "DVB Font manager", "qFH.rVJcIHhOIrwBrt79a8OzoZi5BKj1");

        new DockedTextEditor().run(args);
    }

    class TextView extends DataViewPane {

        @Override
        protected void initializeComponents() {
            // TODO Auto-generated method stub

        }
    }

    public DockedTextEditor() {
        super("Docked Text Editor", TDI_APPLICATION_STYLE);
        addFileMapping(new TextFileFormat("txt", "Text"), TextView.class);

        // docking
        getApplicationUIManager().setUseJideDockingFramework(true);

        ApplicationToolBarsUI t = getApplicationUIManager().getToolBarsUI();
        Container c = t.defaultToolBar("Standard");

        GUIApplicationActionMap map = (GUIApplicationActionMap)getActionMap();
        map.get("about").putValue(Action.SMALL_ICON, JideIconsFactory.getImageIcon(JideIconsFactory.JIDELOGO));
        t.addToolBarButton(c, map.get("about"));
        getApplicationUIManager().getWindowsUI().addWindowCustomizer(new WindowCustomizer() {
            public void customizeWindow(ApplicationWindowsUI windowsUI, Container window) {
                if(getApplicationUIManager().isUseJideDockingFramework()) {
                    DockableHolder holder = (DockableHolder)window;
                    holder.getDockingManager().setInitSplitPriority(DockingManager.SPLIT_EAST_WEST_SOUTH_NORTH);
                    installDockableFrame(holder, null, "Data", DockContext.DOCK_SIDE_WEST);
                    installDockableFrame(holder, null, "Notes", DockContext.DOCK_SIDE_SOUTH);
                }
            }
            public void disposingWindow(ApplicationWindowsUI windowsUI, Container window) {
            }
        });
    }

    private void installDockableFrame(DockableHolder holder, Component content, String title, int initSide) {
        if(holder.getDockingManager().getFrame(title) == null) {
            DockableFrame dockableFrame = new DockableFrame(title);
            dockableFrame.setInitMode(DockContext.STATE_FRAMEDOCKED);
            dockableFrame.setInitSide(initSide);
            if(content != null) {
                dockableFrame.getContentPane().add(content);
            }
            else {
                dockableFrame.setPreferredSize(new Dimension(300, 150));
            }
            holder.getDockingManager().addFrame(dockableFrame);
        }
    }
}
