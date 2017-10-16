package parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Category;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReaderXML {

	private static final String xmlFilePath  = "AIML/chat.xml";
	private List<Category> categoryList = new ArrayList<>();
	
	public ReaderXML() throws SAXException, IOException, ParserConfigurationException {
		this.deserialize();
	}

	public void deserialize() throws SAXException, IOException, ParserConfigurationException {
		File xmlFile = new File(xmlFilePath);

		Document document = DocumentBuilderFactory.
				newInstance().
				newDocumentBuilder().
				parse(xmlFile);
		
		document.getDocumentElement().normalize();
		
		/*
		 * 1.Obtaining the root element
		 */
		Element root = document.getDocumentElement();
		
		/*
		 * 2.Obtain a list of all elements of same range
		 */
		NodeList categories = root.getElementsByTagName("category");
		
		/*
		 * 3.Loop each category
		 */
		Element category  = null;
		Element pattern   = null;
		Element template  = null;
		Element random    = null;
		Category listItem = null;

		for (int i = 0; i < categories.getLength(); i++) {
			
			listItem = new Category();
			
			category = (Element)categories.item(i);
			pattern  = (Element)category.getElementsByTagName("pattern").item(0);
			template = (Element)category.getElementsByTagName("template").item(0);
			
			listItem.setPattern(pattern.getTextContent());
			
			if(( random = (Element)template.getElementsByTagName("random").item(0)) != null) {
				NodeList li = random.getElementsByTagName("li");
				for (int j = 0; j < li.getLength(); j++) {
					listItem.addTemplate(((Element)li.item(j)).getTextContent());
				}
				listItem.setRandom(true);
			} else {
				listItem.addTemplate(template.getTextContent());
			}
			
			categoryList.add(listItem);
			
		}
			
		
	}
	
	public List<Category> read(){
		return this.categoryList;
	}

}
