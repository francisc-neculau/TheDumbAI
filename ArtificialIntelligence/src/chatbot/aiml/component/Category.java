package chatbot.aiml.component;

import java.util.List;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import chatbot.utils.Utils;

public class Category
{
	private Template template;
	private Category redirectCategory;
	private Pattern precompiledPattern;
	private CaptureGroupTrigger captureGroupTrigger;
	
	private String pattern;
	private Integer captureGroupNumber;
	private String  captureGroupValueMeaning;
	
	public Integer getCaptureGroupNumber()
	{
		return captureGroupNumber;
	}

	public String getCaptureGroupValueMeaning()
	{
		return captureGroupValueMeaning;
	}
	
	public boolean hasCaptureGroupTrigger()
	{
		return captureGroupTrigger != null;
	}
	
	public String getRandomAnswer()
	{
		return template.getRandomAnswer();
	}

	public String getPattern()
	{
		return pattern;
	}

	@XmlElement
	public void setPattern(String pattern)
	{
		this.pattern = pattern;
		this.precompiledPattern = Pattern.compile(pattern);
	}

	public Template getTemplate()
	{
		return template;
	}

	@XmlElement
	public void setTemplate(Template template)
	{
		this.template = template;
	}

	public Category getRedirectCategory()
	{
		return redirectCategory;
	}

	public void setRedirectCategory(Category redirectCategory)
	{
		this.redirectCategory = redirectCategory;
	}

	public String getRedirectPattern()
	{
		return template.getSrai();
	}
	
	public boolean hasRedirectPattern()
	{
		return template.getSrai() != null;
	}
	
	public Pattern getPrecompiledPattern()
	{
		return precompiledPattern;
	}

	public CaptureGroupTrigger getCaptureGroupTrigger()
	{
		return captureGroupTrigger;
	}
	
	@XmlElement(name="captureGroupTrigger")
	public void setCaptureGroupTrigger(CaptureGroupTrigger captureGroupTrigger)
	{
		this.captureGroupTrigger = captureGroupTrigger;
		this.captureGroupNumber = captureGroupTrigger.getCaptureGroupNumber();
		this.captureGroupValueMeaning = captureGroupTrigger.getCaptureGroupValueMeaning();
	}

	@Override
	public String toString()
	{
		return pattern + System.lineSeparator();
	}

}

class CaptureGroupTrigger
{
	private Integer captureGroupNumber;
	private String  captureGroupValueMeaning;
	
	public Integer getCaptureGroupNumber()
	{
		return captureGroupNumber;
	}
	@XmlAttribute(name="number")
	public void setCaptureGroupNumber(Integer captureGroupNumber)
	{
		this.captureGroupNumber = captureGroupNumber;
	}
	public String getCaptureGroupValueMeaning()
	{
		return captureGroupValueMeaning;
	}
	@XmlAttribute(name="valueMeaning")
	public void setCaptureGroupValueMeaning(String captureGroupValueMeaning)
	{
		this.captureGroupValueMeaning = captureGroupValueMeaning;
	}
}

class Template
{
	private Random random;
	private String srai;
	private String defaultAnswer;
	
	public String getRandomAnswer()
	{
		if (random == null)
			return defaultAnswer;
		else
			return random.getAnswers().get(Utils.nextRandomInt(0, random.getAnswers().size() - 1));
	}

	public Random getRandom()
	{
		return random;
	}

	@XmlElement(name = "random")
	public void setRandom(Random random)
	{
		this.random = random;
	}

	public String getSrai()
	{
		return srai;
	}

	@XmlElement(name = "srai", defaultValue = "")
	public void setSrai(String srai)
	{
		this.srai = srai;
	}

	public String getDefaultAnswer()
	{
		return defaultAnswer;
	}

	@XmlElement(name = "default", defaultValue = "")
	public void setDefaultAnswer(String defaultAnswer)
	{
		this.defaultAnswer = defaultAnswer;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("default : ")
			.append(defaultAnswer)
			.append(System.lineSeparator())
			.append("random : ")
			.append(random)
			.append(System.lineSeparator());
		return sb.toString();
	}

}

class Random
{
	private List<String> answers;

	public List<String> getAnswers()
	{
		return answers;
	}

	@XmlElement(name = "li")
	public void setAnswers(List<String> answers)
	{
		this.answers = answers;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (String category : answers)
		{
			sb.append(category).append(" ").append(System.lineSeparator());
		}
		return sb.toString();
	}
}
