package App;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

import java.util.ArrayList;

public class UserXMLWriter {
	public void write(ArrayList<User> ul) {
		try {
        	DocumentBuilderFactory dbFactory =
        	DocumentBuilderFactory.newInstance();
        	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        	Document doc = dBuilder.newDocument();

        	Element user = doc.createElement("Users");
        	doc.appendChild(user);
        	
        	for(User u:ul) {
        		Element info = doc.createElement("user");
        		Attr attrType1 = doc.createAttribute("password");
        		attrType1.setValue(u.getPassword());
        		info.setAttributeNode(attrType1);
        		
         		Attr attrType2 = doc.createAttribute("username");
         		attrType2.setValue(u.getUserName());
         		info.setAttributeNode(attrType2);
         		user.appendChild(info);
         	}
         
         	// write the content into xml file
         	TransformerFactory transformerFactory = TransformerFactory.newInstance();
         	Transformer transformer = transformerFactory.newTransformer();
         	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
         	DOMSource source = new DOMSource(doc);
         	//To outside target/
         	StreamResult result = new StreamResult(new File("..//users.xml"));
         	transformer.transform(source, result);
         	//Inside target/
         	StreamResult result2 = new StreamResult(new File("users.xml"));
         	transformer.transform(source, result2);
         	
         	// Output to console for testing
         	//StreamResult consoleResult = new StreamResult(System.out);
         	//transformer.transform(source, consoleResult);
      	} catch (Exception e) {
         	e.printStackTrace();
      	}	
    }
}
