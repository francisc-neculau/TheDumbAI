package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import error.UnknownReplyException;
import model.Category;

public class ChatbotMemory {

	List<Category> categoryList = new ArrayList<Category>();
	String 		   cachedBotReply;
	
	public ChatbotMemory() throws SAXException, IOException, ParserConfigurationException {
		super();
		this.deserializeMemory();
	}
	
	public void add(Category category) {
		this.categoryList.add(category);
	}
	
	public boolean hasReply(String userReply) {
		for (Category category : categoryList) {
			if(category.getPattern().contains(userReply.toUpperCase()) || userReply.toUpperCase().contains(category.getPattern())) {
				this.cachedBotReply = category.getTemplate();
				return true;
			}
		}
		return false;
	}
	
	public String getReply(String userReply) throws UnknownReplyException {
		String botReply;
		if(!hasReply(userReply)){
			throw new UnknownReplyException(userReply);
		} else {
			botReply = this.cachedBotReply;
			this.cachedBotReply = null;
		}
		return botReply;
	}
	
	public void deserializeMemory() throws SAXException, IOException, ParserConfigurationException {
		this.categoryList = (new ReaderXML()).read();
	}
	
	public void serializeMemory() throws SAXException, IOException, ParserConfigurationException, TransformerException {
		(new WriterXML(this.categoryList)).save();
	}

}
