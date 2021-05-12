import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
public class Xerces {

    public static void main(String[] args) {
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append("<?xml version = \"1.0\"?> <class> </class>");
        ByteArrayInputStream input = null;
        Document doc = null;
        try {
            input = new ByteArrayInputStream(
                    xmlStringBuilder.toString().getBytes("UTF-8"));
            doc = builder.parse(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element root = doc.getDocumentElement();
        System.out.println(root.toString());
    }
}
