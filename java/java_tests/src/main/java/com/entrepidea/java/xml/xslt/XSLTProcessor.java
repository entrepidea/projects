package com.entrepidea.java.xml.xslt;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

public class XSLTProcessor {

    /**
     * @param args
     */
	private static String dealXMLFile = "resources\\deal_5139521.xml";
    private static String dtdFileName = "resources\\mxml.dtd";
    private static String MxmlTransformer = "resources\\MxmlTransformer.xsl";
    private static String[] xmlArray;
    private ByteArrayOutputStream bstream = new ByteArrayOutputStream(10000);
   
    private void createXMLArray(String fn) throws Exception {
        FileReader fd = new FileReader(fn);
        BufferedReader rd = new BufferedReader(fd);
        List<String> tmp = new ArrayList<String>();
        String line = null;
        while(rd!=null&& (line = rd.readLine())!=null){
            tmp.add(line);
        }
        xmlArray = new String [tmp.size()];
        xmlArray = (String[])tmp.toArray(xmlArray);
        for(String xml:xmlArray){
            System.out.println(xml);
        }       
    }
   
    List<Map> getTradesFromXmlStr(List<Map> trades,Object[] data) throws Exception {

        bstream.reset();

        Document doc = XMLUtils.getInstance().getDocument((String)data[0], dtdFileName);

        return getTradesFromXmlDoc(trades,doc);

        //return Processor.getInstance().start(list);

       

    }
   
    private Map createEvents(String string) {

        return createMapFromNameValues(string);

    }


    String convert(String name, String value) {
        if (name.endsWith("date")) {
        }
        return value;
    }

    private Map createMapFromNameValues(String line) {

        String[] attribs = line.split(";");

        Map po = new HashMap();



        for (String s : attribs) {

            if (!s.contains("="))

                continue;

            String[] pair = s.split("=");



            if (pair == null)

                continue;

            String name = pair[0].trim();

            String value = convert(name, pair.length == 2 ? pair[1].trim() : "");



            po.put(name, value);

        }



        return po;

    }

    private Map createCashFlow(Map parentMap, String string) {

        Map childMap = new HashMap();

        childMap.putAll(parentMap);

        childMap.remove("cashflows");

        childMap.putAll(createMapFromNameValues(string));



        // return MurexTradeBuilder.createOptionTradePO(childMap);

        return childMap;

    }
   
    private Map createTrade(String section) {

        String[] streams = section.split("\\[STREAM\\]");



        Map parentMap = createMapFromNameValues(streams[0]);

        List<Map> cahsflows = new ArrayList<Map>();

        parentMap.put("cashflows", cahsflows);



        for (int i = 1; i < streams.length; i++) {

            {

                String stream = streams[i];

                String[] lines = stream.split("\n");

                if (lines == null || lines.length == 0)

                    continue;

                lines[0] = lines[0].replaceAll("\r", "");



                for (String line : lines) {

                    if (line.startsWith("[CASHFLOW]")) {

                        cahsflows.add(createCashFlow(parentMap, line

                                .substring("[CASHFLOW]".length())));

                    }



                }

            }

        }



        // System.out.println(parentMap);

        return parentMap;

    }



    List<Map> getTradesFromXmlDoc(List<Map> trades,Document doc) throws Exception

    {
       
        String transformedStr = XMLUtils.getInstance().transformDocument(doc,

                MxmlTransformer);

        System.out.println( "TransformedStr  Msg: " + transformedStr);



        Map events = null;

        String[] data_sections = transformedStr.split("@");

        for (String section : data_sections) {

            if (section.startsWith("[EVENTS]")) {

                events = createEvents(section.substring("[EVENTS]".length()));

            } else if (section.startsWith("[TRADE]")) {

                trades.add(createTrade(section.substring("[TRADE]".length())));

            } else {

                ;

            }

        }



        //data[0] = events;

        return trades;

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("XSLT testing...");
        try{
            XSLTProcessor processor = new XSLTProcessor();
            processor.createXMLArray(dealXMLFile);
            List<Map> trades = new ArrayList<Map>();
            processor.getTradesFromXmlStr(trades,xmlArray);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
