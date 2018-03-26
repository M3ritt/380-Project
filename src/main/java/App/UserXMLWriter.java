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
	public void writeForUsers(ArrayList<User> ul) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			Element user = doc.createElement("Users");
			doc.appendChild(user);

			for(User u:ul) {
				Element info = doc.createElement("user");

				Attr attr1 = doc.createAttribute("password");
				attr1.setValue(u.getPassword());
				info.setAttributeNode(attr1);

				Attr attr2 = doc.createAttribute("username");
				attr2.setValue(u.getUserName());
				info.setAttributeNode(attr2);
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
		} catch (Exception e) {
			e.printStackTrace();
		}	
    }
	
	public void writeForInventory(ArrayList<Item> il) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			Element item = doc.createElement("Items");
			doc.appendChild(item);

			for(Item i:il) {
				//Create a counter so we keep the correct number of items in the inventory
				int counter = 0;

				while(counter < i.getAmount()) {
					//Create the item element
					Element info = doc.createElement("item");

					//Create the name att for the item
					Attr attr1 = doc.createAttribute("name");
					attr1.setValue(i.getName());
					info.setAttributeNode(attr1);

					//Create the price att for the item
					Attr attr2 = doc.createAttribute("price");
					attr2.setValue("" + i.getPrice());
					info.setAttributeNode(attr2);

					//Create the brand att for the item
					Attr attr3 = doc.createAttribute("brand");
					attr3.setValue(i.getBrand());
					info.setAttributeNode(attr3);
					item.appendChild(info);

					//Increase the count so we get the right number of items
					counter++;
				}
			}

			//Write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);

			//To outside target/
			StreamResult result = new StreamResult(new File("..//InventoryFile.xml"));
			transformer.transform(source, result);

			//Inside target/
			StreamResult result2 = new StreamResult(new File("InventoryFile.xml"));
			transformer.transform(source, result2);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
