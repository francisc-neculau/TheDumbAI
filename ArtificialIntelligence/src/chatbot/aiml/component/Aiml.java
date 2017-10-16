package chatbot.aiml.component;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Aiml
{
	private List<Category> categories;

	public List<Category> getCategories()
	{
		return categories;
	}

	@XmlElement(name = "category")
	public void setCategories(List<Category> categories)
	{
		this.categories = categories;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (Category category : categories)
		{
			sb.append(category).append(System.lineSeparator());
		}
		return sb.toString();
	}
}
