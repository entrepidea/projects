package com.entrepidea.jersey.swing.components.table;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class JRadioAsRendererEditor extends JPanel {

    private static final String[] COLUMN_NAMES = {
        "List ID", "Expiration Date", "Status", "Date Created"};
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final DateFormat TIME_FORMAT_LONG = new SimpleDateFormat("HH:mm:ss,SSS");
    private MyTableModel tableModel;
    private JTable table;
    private JFrame frame = new JFrame("TestRadioButtonRenderer");

    public JRadioAsRendererEditor() {
        super(new BorderLayout(0, 5));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tableModel = new MyTableModel();
        table = new JTable(tableModel);
        table.setDefaultEditor(Date.class, new DateEditor());
        table.setDefaultRenderer(Date.class, new DateRenderer());
        table.setDefaultEditor(Status.class, new StatusEditor());
        table.setDefaultRenderer(Status.class, new StatusRenderer());
// comment the two preceding lines and uncomment the following for a standard editor
// table.setDefaultEditor(Status.class, new DefaultCellEditor(
// new JComboBox(new Status[]{Status.Single, Status.Married, Status.Divorced})));

        add(new JScrollPane(table), BorderLayout.CENTER);
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(new AbstractAction("Add new") {

            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.add(new TableEntry());
                packTable();
            }
        });
        toolBar.add(new AbstractAction("Remove") {

            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.remove(table.getSelectedRow());
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(toolBar, BorderLayout.NORTH);
        frame.pack();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                frame.setVisible(true);
            }
        });
    }

    private void packTable() {
        TableColumnModel columnModel = table.getColumnModel();
        int columnCount = table.getColumnCount();
        int rowCount = table.getRowCount();
        int[][] preferredHeights = new int[columnCount][rowCount];
        TableCellRenderer renderer;
        Component comp;
        for (int col = 0; col < columnCount; col++) {
            renderer = columnModel.getColumn(col).getCellRenderer();
            if (renderer == null) {
                renderer = table.getDefaultRenderer(tableModel.getColumnClass(col));
            }
            for (int row = 0; row < rowCount; row++) {
                comp = renderer.getTableCellRendererComponent(table,
                    tableModel.getValueAt(row, col), false, false, row, col);
                preferredHeights[col][row] = (int) comp.getPreferredSize().getHeight();
            }
        }
        for (int row = 0; row < rowCount; row++) {
            int pref = 0;
            for (int col = 0; col < columnCount; col++) {
                pref = Math.max(pref, preferredHeights[col][row]);
            }
            table.setRowHeight(row, pref);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JRadioAsRendererEditor tableTestPanel = new JRadioAsRendererEditor();
            }
        });
    }

    private enum Status {

        Single, Married, Divorced;
    }

    private class StatusPanel extends JPanel {

        private JRadioButton theSingleOption;
        private JRadioButton theMarriedOption;
        private JRadioButton theDivorcedOption;
        private ButtonGroup buttonGroup = new ButtonGroup();

        StatusPanel() {
            super(new GridLayout(0, 1));
            setOpaque(true);
            theSingleOption = createRadio(Status.Single);
            theMarriedOption = createRadio(Status.Married);
            theDivorcedOption = createRadio(Status.Divorced);
        }

        private JRadioButton createRadio(Status status) {
            JRadioButton jrb = new JRadioButton(status.toString());
            jrb.setOpaque(false);
            add(jrb);
            buttonGroup.add(jrb);
            return jrb;
        }

        public Status getStatus() {
            if (theMarriedOption.isSelected()) {
                return Status.Married;
            } else if (theDivorcedOption.isSelected()) {
                return Status.Divorced;
            } else {
                return Status.Single;
            }
        }

        public void setStatus(Status status) {
            if (status == Status.Married) {
                theMarriedOption.setSelected(true);
            } else if (status == Status.Divorced) {
                theDivorcedOption.setSelected(true);
            } else {
                theSingleOption.setSelected(true);
            }
        }
    }

    private class TableEntry {

        private int instanceNumber;
        private Long theId;
        private Date theExpirationDate;
        private Status theStatus;
        private Date theCreationDate;

        TableEntry() {
            instanceNumber++;
            theId = new Long(instanceNumber);
            theExpirationDate = new Date();
            theStatus = Status.Single;
            theCreationDate = new Date();
        }

        TableEntry(Long anId, Date anExpirationDate, Status aStatus, Date aCreationDate) {
            theId = anId;
            theExpirationDate = anExpirationDate;
            theStatus = aStatus;
            theCreationDate = aCreationDate;
        }

        public Long getId() {
            return theId;
        }

        public Date getExpirationDate() {
            return theExpirationDate;
        }

        public Status getStatus() {
            return theStatus;
        }

        public Date getCreationDate() {
            return theCreationDate;
        }

        public void setId(Long anId) {
            theId = anId;
        }

        public void setExpirationDate(Date anExpirationDate) {
            theExpirationDate = anExpirationDate;
        }

        public void setStatus(Status aStatus) {
            theStatus = aStatus;
        }

        public void setCreationDate(Date aCreationDate) {
            theCreationDate = aCreationDate;
        }
    }

    private class MyTableModel extends AbstractTableModel {

        private Vector<Object> theEntries;

        MyTableModel() {
            theEntries = new Vector<Object>();
        }

        @SuppressWarnings("unchecked")
        public void add(TableEntry anEntry) {
            int index = theEntries.size();
            theEntries.add(anEntry);
            fireTableRowsInserted(index, index);
        }

        public void remove(int aRowIndex) {
            if (aRowIndex < 0 || aRowIndex >= theEntries.size()) {
                return;
            }
            theEntries.removeElementAt(aRowIndex);
            fireTableRowsDeleted(aRowIndex, aRowIndex);

        }

        public int getRowCount() {
            return theEntries.size();
        }

        @Override
        public String getColumnName(int column) {
            return COLUMN_NAMES[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return Long.class;
                case 1:
                    return Date.class;
                case 2:
                    return Status.class;
                case 3:
                    return Date.class;
            }
            return Object.class;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            TableEntry entry = (TableEntry) theEntries.elementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    try {
                        entry.setId(new Long(Long.parseLong(aValue.toString())));
                    } catch (NumberFormatException nfe) {
                        return;
                    }
                    break;
                case 1:
                    entry.setExpirationDate((Date) aValue);
                    break;
                case 2:
                    entry.setStatus((Status) aValue);
                    break;
                case 3:
                    entry.setCreationDate((Date) aValue);
                    break;
                default:
                    return;
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            TableEntry entry = (TableEntry) theEntries.elementAt(rowIndex);
            switch (columnIndex) {
                case 0:
                    return entry.getId();
                case 1:
                    return entry.getExpirationDate();
                case 2:
                    return entry.getStatus();
                case 3:
                    return entry.getCreationDate();
            }
            return null;
        }
    }

    private class DateRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!(value instanceof Date)) {
                return this;
            }
            setText(DATE_FORMAT.format((Date) value));
            return this;
        }
    }

    private class TimeRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!(value instanceof Date)) {
                return this;
            }
            setText(TIME_FORMAT_LONG.format((Date) value));
            return this;
        }
    }

    private class DateEditor extends AbstractCellEditor implements TableCellEditor {

        private JSpinner theSpinner;

        DateEditor() {
            theSpinner = new JSpinner(new SpinnerDateModel());
            theSpinner.setOpaque(true);
            theSpinner.setEditor(new JSpinner.DateEditor(theSpinner, "dd/MM/yyyy"));
        }

        @Override
        public Object getCellEditorValue() {
            return theSpinner.getValue();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
            theSpinner.setValue(value);
            if (isSelected) {
                theSpinner.setBackground(table.getSelectionBackground());
            } else {
                theSpinner.setBackground(table.getBackground());
            }
            return theSpinner;
        }
    }

    private class TimeEditor extends AbstractCellEditor implements TableCellEditor {

        private JSpinner theSpinner;
        private Object value;

        TimeEditor() {
            theSpinner = new JSpinner(new SpinnerDateModel());
            theSpinner.setOpaque(true);
            theSpinner.setEditor(new JSpinner.DateEditor(theSpinner, "HH:mm:ss,SSS"));
        }

        @Override
        public Object getCellEditorValue() {
            return theSpinner.getValue();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
        boolean isSelected, int row, int column) {
            theSpinner.setValue(value);
            if (isSelected) {
                theSpinner.setBackground(table.getSelectionBackground());
            } else {
                theSpinner.setBackground(table.getBackground());
            }
            return theSpinner;
        }
    }

    private class StatusEditor extends AbstractCellEditor implements TableCellEditor {

        private StatusPanel theStatusPanel;

        StatusEditor() {
            theStatusPanel = new StatusPanel();
        }

        @Override
        public Object getCellEditorValue() {
            return theStatusPanel.getStatus();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
            theStatusPanel.setStatus((Status) value);
            if (isSelected) {
                theStatusPanel.setBackground(table.getSelectionBackground());
            } else {
                theStatusPanel.setBackground(table.getBackground());
            }
            return theStatusPanel;
        }
    }

    private class StatusRenderer extends StatusPanel implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            setStatus((Status) value);
            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(table.getBackground());
            }
            return this;
        }
    }
}