package swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.StyledDocument;

import java.beans.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class ProgressBarDemo extends JPanel implements ActionListener,
		PropertyChangeListener {

	private JProgressBar progressBar;

	private JButton startButton;

	private JTextArea taskOutput;

	private Task task;

	class Task extends SwingWorker<Void, Void> {
		/*
		 * Main task. Executed in background thread.
		 */
		private File file;

		private File[] files;

		Task(File f) {
			file = f;
		}

		Task(File[] files) {
			this.files = files;
		}

		@Override
		public Void doInBackground() {

			try {
				for (File file : files) {
					FileInputStream fstrm = new FileInputStream(file);
					ObjectInput istrm = new ObjectInputStream(fstrm);
					int progress = 0;
					// Initialize progress property.
					setProgress(0);

					int size = istrm.readInt();
					int step = 100 / size;
					for (int i = 0; i < size; i++) {
						StyledDocument ques = (StyledDocument) istrm
								.readObject();
						StyledDocument ans = (StyledDocument) istrm
								.readObject();
						while (progress < 100) {
							progress += step;
							setProgress(Math.min(progress, 100));
						}
					}
				}
			} catch (Exception ioe) {
				ioe.printStackTrace();
			}
			return null;
		}

		/*
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			startButton.setEnabled(true);
			setCursor(null); // turn off the wait cursor
			taskOutput.append("Done!\n");
		}
	}

	public ProgressBarDemo() {
		super(new BorderLayout());

		// Create the demo's UI.
		startButton = new JButton("Start");
		startButton.setActionCommand("start");
		startButton.addActionListener(this);

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);

		taskOutput = new JTextArea(5, 20);
		taskOutput.setMargin(new Insets(5, 5, 5, 5));
		taskOutput.setEditable(false);

		JPanel panel = new JPanel();
		panel.add(startButton);
		panel.add(progressBar);

		add(panel, BorderLayout.PAGE_START);
		add(new JScrollPane(taskOutput), BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	}

	/**
	 * Invoked when the user presses the start button.
	 */
	public void actionPerformed(ActionEvent evt) {
		startButton.setEnabled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		// Instances of javax.swing.SwingWorker are not reusuable, so
		// we create new instances as needed.
		File[] files = new File[]{new File("J2EE"), new File("XML")};
		task = new Task(files);
		task.addPropertyChangeListener(this);
		task.execute();
	}

	/**
	 * Invoked when task's progress property changes.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
			taskOutput.append(String.format("Completed %d%% of task.\n", task
					.getProgress()));
		}
	}

	/**
	 * Create the GUI and show it. As with all GUI code, this must run on the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("ProgressBarDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new ProgressBarDemo();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
