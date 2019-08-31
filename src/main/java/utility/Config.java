package utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Product;

public class Config {
	
	private ArrayList<Product> extractFileContent(ArrayList<Product> product_list) {
		try {
			String path = getClass().getClassLoader().getResource("products.xml").getFile();
			File file 	= new File(path);
			
			// DOM PARSER (READ PRODUCTS.XML FILE)
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			
			// get all "item" elements (get all products/items in the xml file)
			NodeList nList = doc.getElementsByTagName("item");
			Node node;
			Product p = null;

			// iterate through each item and get data
			for (int i = 0; i < nList.getLength(); i++) {
				node = nList.item(i);		
				
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					
					p = new Product(
						e.getElementsByTagName("id").item(0).getTextContent(), 							/* ID */
						e.getElementsByTagName("name").item(0).getTextContent(), 						/* NAME */
						e.getElementsByTagName("image-name").item(0).getTextContent(), 					/* IMAGE NAME */
						Double.parseDouble(e.getElementsByTagName("price").item(0).getTextContent()), 	/* PRICE */
						Integer.parseInt(e.getElementsByTagName("stocks").item(0).getTextContent()),	/* STOCKS */
						Integer.parseInt(e.getElementsByTagName("rating").item(0).getTextContent()) 	/* RATING */
					);
					
					// add item to product_list
					product_list.add(p);
				}
			}
			
			return product_list;
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<Product> getProducts() {
		ArrayList<Product> product_list = new ArrayList<Product>();
		product_list = extractFileContent(product_list);
		
		if(product_list.size() < 1) {
			Logger.log("ATTENTION: THERE ARE NO ITEMS IN THE PRODUCT LIST.");
		} else {
			Logger.log("LOADING PRODUCTS LIST TO FIRESHIP!");
		}
		
		return product_list;
	}
	
}
