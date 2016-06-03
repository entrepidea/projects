package com.entrepidea.java.xml.xslt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtils {



    private XMLUtils() {

    }



    private static XMLUtils _instance = new XMLUtils();



    static public XMLUtils getInstance() {

        return _instance;

    }



    public Document getDocument(String str, final String validationResource) throws Exception {

        /* */

        try {

            DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder docBuilder = docBuildFactory.newDocumentBuilder();



            docBuilder.setEntityResolver(new EntityResolver() {

                public InputSource resolveEntity(String publicId, String systemId)

                        throws SAXException, java.io.IOException {

                    if (systemId != null && systemId.endsWith(validationResource)) {

                        // this deactivates the open office DTD

                        // return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));

                        //InputStream stream = getClass().getResourceAsStream(validationSystemId);

                        InputStream stream = getInputStreamFromClasspath(validationResource);

                        if (stream == null) {

                            return null;

                        } else {

                            return new InputSource(stream);

                        }

                    } else {

                        return null;

                    }

                }

            });



            Document document = docBuilder.parse(new InputSource(new StringReader(str)));

            return document;



        } catch (Exception e) {

            System.err.println("caught Exception: " + e.getMessage());

            throw e;

        }

        /* */

        // return XMLUtilities.parse(new ByteArrayInputStream(str.getBytes()));

    }



    InputStream getInputStreamFromClasspath(String systemId) {

        InputStream inputStream = null;

        String resource = systemId;

        if (systemId != null && systemId.startsWith("file:")) {

            resource = systemId.substring(systemId.indexOf(':') + 1);

        }

        inputStream = getClass().getResourceAsStream(resource);

               

        if (null == inputStream) {

            inputStream = ClassLoader.getSystemResourceAsStream(systemId);

            System.out.println( "Loading through class path: " + resource);

        }

        return inputStream;

    }



    public void serialize(Document doc, OutputStream outStream) throws Exception {



        TransformerFactory tfactory = TransformerFactory.newInstance();

        Transformer serializer;

        try {

            serializer = tfactory.newTransformer();

            //Setup indenting to "pretty print"

            serializer.setOutputProperty(OutputKeys.INDENT, "yes");

            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");



            serializer.transform(new DOMSource(doc), new StreamResult(outStream));

        } catch (TransformerException e) {

            System.err.println( "caught Exception: " + e.getMessage());

            throw e;

        }

    }



    public String transformDocument(Document document, String transformerResource)

            throws Exception {



        StreamResult strResult = null;

        ByteArrayOutputStream bstream = new ByteArrayOutputStream(10000);

        try {

            InputStream inputStream = getInputStreamFromClasspath(transformerResource);

            TransformerFactory tfactory = TransformerFactory.newInstance();

            Transformer transformer = tfactory.newTransformer(new StreamSource(inputStream));



            strResult = new StreamResult(bstream);

            DOMSource source = new DOMSource(document);

            transformer.transform(source, strResult);

        } catch (TransformerException e) {

            System.err.println( "caught Exception: " + e.getMessage());

            throw e;

        } catch (Exception e) {

            System.err.println("caught Exception: " + e.getMessage());

            throw e;

        }

        return bstream.toString();

    }

   

    public Document getFileAsDocument(String fileName) throws Exception

    {

        DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder docBuilder = docBuildFactory.newDocumentBuilder();

        Document document = docBuilder.parse(new File(fileName));       

        return document;       

    }

}

