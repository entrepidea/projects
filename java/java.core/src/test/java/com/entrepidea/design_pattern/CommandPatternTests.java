package com.entrepidea.design_pattern;

import org.junit.Test;

/**
 * 
 * @author Hai Yi
 * 
 * @description this is a demo for command design pattern. Command is a design pattern in which an object
 * is encapsulated and representing all info needed to call a method at a later time. This info include 
 * the method's name, parameters, values, etc
 * 
 * another explanation: Encapsulate a request as an object, thereby letting you parameterize clients with 
 * different requests
 * 
 * Three words are critical in Command design pattern:
 * 1. invoker - define when the method is to be invoked;
 * 2. receiver - is an instance of the method's business logic 
 * 3. client - responsible for instantiating the command object and providing conditions to trigger the invoker.
 * 
 * @note
 * see also
 * on wiki: http://en.wikipedia.org/wiki/Command_pattern
 * https://refactoring.guru/design-patterns/command
 *
 */

//the Invoker class: decide when to call the method 
 class Switch {
 
    private Command flipUpCommand;
    private Command flipDownCommand;
 
    public Switch(Command flipUpCmd,Command flipDownCmd){
         this.flipUpCommand=flipUpCmd;
         this.flipDownCommand=flipDownCmd;
    }
 
    public void flipUp(){
         flipUpCommand.execute();
    }
 
    public void flipDown(){
         flipDownCommand.execute();
    }
 
}
 

//receiver class: contains the methods
class Light{
	 
     public Light(){  }
 
     public void turnOn(){
        System.out.println("The light is on");
     }
 
     public void turnOff(){
        System.out.println("The light is off");
     }
}

//the Command interface: client? 
interface Command{
    void execute();
}
 
 
/*the Command for turning on the light*/	 
class FlipUpCommand implements Command{
 
   private Light theLight;
 
   public FlipUpCommand(Light light){
        this.theLight=light;
       }
 
   public void execute(){
      theLight.turnOn();
   }
 
}

//the Command for turning off the light
 class FlipDownCommand implements Command{
 
   private Light theLight;
 
   public FlipDownCommand(Light light){
        this.theLight=light;
       }
 
   public void execute(){
      theLight.turnOff();
   }
 
}

public class CommandPatternTests {


    @Test
	public void testOn() {
		Light lamp = new Light();
	       Command switchUp=new FlipUpCommand(lamp );
	       Command switchDown=new FlipDownCommand(lamp );
	       Switch s=new Switch(switchUp,switchDown);
	       
	       try {
	                s.flipUp();
	                System.exit(0);
	           System.out.println("Argument \"ON\" or \"OFF\" is required.");
	           } catch (Exception e){
	        	   e.printStackTrace();
	            System.out.println("Argument's required.");
	           }


	}

	//Test2 - see a demo app in com.entrepidea.swing.patterns.command.editor.Demo
    //it's put there because it's a Swing app thus not suitable to be treated as a unit test.
}
