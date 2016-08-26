package com.entrepidea.spring.core.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommandManager {

	private Logger log = LoggerFactory.getLogger(CommandManager.class);
	
	protected abstract Command createCommand();
	
	public void getCommand(){
		Command c = createCommand();
		log.debug("the command is ===> {} ",c);
	}
}
