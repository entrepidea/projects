package com.entrepidea.spring.core.ioc.supports.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommandManager {

	private Logger log = LoggerFactory.getLogger(CommandManager.class);
	protected abstract Command createCommand();

	public void execute(){
		createCommand().foo();
	}
}
