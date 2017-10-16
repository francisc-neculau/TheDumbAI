package parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import model.Category;

public class WriterXML {

	private static final String xmlFilePath = "AIML/chat.xml";
	private List<Category> categoryList;

	public WriterXML(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public void save() throws SAXException, IOException, ParserConfigurationException, TransformerException {

		Document document = DocumentBuilderFactory.
				newInstance().
				newDocumentBuilder().
				newDocument();
		
		Element root = document.createElement("aiml");
		document.appendChild(root);
		Element categoryElement;
		Element patternElement;
		Element templateElement;
		Element randomElement;
		Element liElement;
		
		for (Category category : categoryList) {
			categoryElement = document.createElement("category");
			root.appendChild(categoryElement);
			
			patternElement = document.createElement("pattern");
			patternElement.appendChild(document.createTextNode(category.getPattern()));
			categoryElement.appendChild(patternElement);
			
			templateElement = document.createElement("template");
			if(category.isRandom()) {
				randomElement = document.createElement("random");
				templateElement.appendChild(randomElement);
				List<String> templateLiList = category.getTemplateList();
				for (int i = 0; i < templateLiList.size(); i++) {
					liElement = document.createElement("li");
					liElement.appendChild(document.createTextNode(templateLiList.get(i)));
					randomElement.appendChild(liElement);
				}
			} else {
				templateElement.appendChild(document.createTextNode(category.getTemplate()));
			}
			categoryElement.appendChild(templateElement);	
		}
		TransformerFactory.newInstance().
				newTransformer().
				transform(
							new DOMSource(document), 
							new StreamResult(new File(xmlFilePath))
						 );
	}

}
