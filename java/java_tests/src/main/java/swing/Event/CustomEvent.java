package swing.Event;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Toolkit;
import java.util.EventListener;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JFrame;


/**
 * @description: this demo how to create a customized listener to listen to customized events
 * 
 * Steps:
 * 1. Create an listener interface;
 * 2. Create a component-derived class that is responsible for 
 * 		a) registering a listener;
 * 		b) posting event to the queue;
 *    	c) in its inherited processEvent, call the above listener's methods.
 * 3. Create a Test class implementing the listener with buisness logic.
 *  
 * */
public class CustomEvent extends JPanel implements TimerListener{
	
	private JProgressBar bar = new JProgressBar(1,100);
	private int currentValue = 10;
	
	CustomEvent(){
		TimerComponent tc = new TimerComponent(1000);
		tc.addTimerListener(this);
		bar.setValue(currentValue);
		add(bar);
	}
	
	@Override
	public void timerElapsed(TimerEvent e){
		currentValue += 10;
		bar.setValue(currentValue);
	}
	public static void main(String[] args){
		JFrame f = new JFrame("Test");
		f.getContentPane().add(new CustomEvent());
		f.setSize(300,200);
		f.setVisible(true);
	}
}


interface TimerListener extends EventListener{
	public void timerElapsed(TimerEvent e);
}

class TimerComponent extends Component implements Runnable{
	private TimerListener listener;
	private int interval;
	private static EventQueue evtq;
	private Thread t = null;
	
	public void addTimerListener(TimerListener l){
		listener = l;
	}
	
	TimerComponent(int i){
		interval = i;
		t = new Thread(this);
		t.start();
		evtq = Toolkit.getDefaultToolkit().getSystemEventQueue();
		enableEvents(0);	
		
	}	
	public void run(){
		while(true){
			try{
				Thread.sleep(interval);
			}
			catch(InterruptedException e){}
			evtq.postEvent(new TimerEvent(this));
		}
	}
	
	@Override
	public void processEvent(AWTEvent e){
		if(e instanceof TimerEvent){
			listener.timerElapsed((TimerEvent)e);
		}
		else{
			super.processEvent(e);
		}
	}
}


class TimerEvent extends AWTEvent{
	private static int TIMER_EVENT = AWTEvent.RESERVED_ID_MAX + 5555;
	TimerEvent(TimerComponent t){
		super(t,TIMER_EVENT);
	}	
}
