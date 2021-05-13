package xmlTo;

/*
* Steps to use DOM to parse XML files:
* step1, create a parser factory object DocumentBuildFactory;
* step2, the parser object DocumentBuilder is created by the parser factory object;
* step3. The specified XML file is parsed by the parser object, the corresponding DOM tree is constructed, and the Document object is created;
* step4. Query the nodes of the DOM tree with the Document object as the starting point;
* step5. Use the getElementsByTagName method of Document to get the element name and generate a NodeList set;
* step6, traverse the collection;
 */
import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
public class xmlToDOM{
    public static void main(String[] args){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document doc=builder.parse(new File("D:\\test\\XML.xml"));
            NodeList nl=doc.getElementsByTagName("hang");
            for (int i=0; i < nl.getLength(); I++) {
                System.out.println(doc.getElementsByTagName(“ Person ID").item(i)" +
                            ".getFirstChild().getNodeValue());
                System.out.println(doc.getElementsByTagName(“Person Name").item(i)
                        .getFirstChild().getNodeValue());
                System.out.println();
            }
        }
        catch (ParserConfigurationException e){
            e.printStackTrace();
        }
        catch (SAXException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
