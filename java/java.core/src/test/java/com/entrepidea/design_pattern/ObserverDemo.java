package com.entrepidea.design_pattern;

/**
 * @author: Hai Yi
 * @description: this is to demo Observer pattern. The observable, also known as subject, maintains a list of observers. Once a status changes, it's responsible for notifying 
 * each observable in the list of the change, to allow them to response accordingly.
 * 
 * @note: it looks like the new JDK API adds support for observer / observable; this pattern is extensively used in the distributed event handling system, like that in the Swing framework
 * 
 * @ewference: http://en.wikipedia.org/wiki/Observer_pattern
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;
class EventSource extends Observable implements Runnable {

	@Override
	public void run() {
		try{
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			
			do{
				String response = br.readLine();
				setChanged();
				notifyObservers(response);
			}while(true);
		}
		catch(IOException ioer){
			ioer.printStackTrace();
		}
		
	}
	
}

class ResponseHandler implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof String ){
			System.out.println("response: "+arg);
		}
	}
	
}

class AntiResponseHandler implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof String){
			System.out.println("anti reponse: not "+arg);
		}
	}
	
}
public class ObserverDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventSource source = new EventSource();
		//the observable, source, maintains a list of two observers
		source.addObserver(new ResponseHandler());
		source.addObserver(new AntiResponseHandler());
		new Thread(source).start();		
	}
}
