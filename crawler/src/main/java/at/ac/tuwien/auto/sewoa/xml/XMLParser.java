package at.ac.tuwien.auto.sewoa.xml;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.StringReader;

/**
 * User: pelesic
 * Date: 01/07/15
 * Time: 21:21
 */
public class XMLParser {

    public Document parse(String xml){
        ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse( stream );
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
