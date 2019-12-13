package com.entrepidea.java.io;

/**
 * @description: this is simple write some strings to the file system. It's simple but effective, used in many occasions. 
 * 
 */
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterDemo {
	private static void fillTemplate(FileWriter writer, String id, String label, String level, String parentId)
	throws IOException {
		String basic = "<bean id=\""+id+"\" class=\"com.entrepidea.flyingnotes.client.dto.TreeBean\">\n"+
		"\t<property name=\"label\" value=\""+label+"\" />\n"+
		"\t<property name=\"level\" value=\""+level+"\" />\n"+
		"\t<property name=\"id\" value=\""+id+"\" />\n";
		
		String parentProp="";
		if(parentId!=null){
			parentProp="\t<property name=\"parent\">\n"+
					"\t\t<ref bean=\""+parentId+"\"/>\n"+
					"\t</property>\n";
		}
		String whole = basic+parentProp+"</bean>\n";
		
		writer.append(whole);
		
	}
	public static void main(String[] args) throws IOException {
		// this is a simple demo of write some string to a file
		final String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
"<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">\n";
		final String beansHeader = "<beans>\n";
		final String beansEnd = "</beans>\n";
		
		FileWriter aWriter = new FileWriter("tree.xml",false);
		aWriter.write(header);
		aWriter.write(beansHeader);
		String id="b1_0";
		String label="level1_node0";
		String level="1";
		String parentId="b0";
		fillTemplate(aWriter,id,label,level,parentId);		
		aWriter.write(beansEnd);
		aWriter.close();
	}

}
