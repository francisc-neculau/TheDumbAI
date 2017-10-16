package chatbot;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import chatbot.aiml.component.Aiml;
import chatbot.aiml.component.Category;
import chatbot.data.User;
import chatbot.data.UserDb;
import chatbot.utils.Utils;

public class Bot
{
	public static final int CATEGORY_HIT_THRESHOLD = 2;
	
	private String botName;
	private String aimlFilePath;
	private Aiml aiml;
	private Map<String, Category> patternToCategoryMap;
	private Map<String, Integer>  patternToCounterMap;
	
	private String userName;
	private String profession;
	private Integer age;
	private boolean userPersisted = false;
	private boolean userExisted = false;
	private boolean responseOverriden = false;
	
	public Bot(String botName, String aimlFilePath)
	{
		this.botName = botName;
		this.aimlFilePath = aimlFilePath;
		this.patternToCategoryMap = new HashMap<>();
		this.patternToCounterMap  = new HashMap<>();
		this.initialize();
	}
	
	private void initialize()
	{
		// load aiml file
		this.aiml = Utils.loadAimlFile(this.aimlFilePath);
		// 1. build the map
		for (Category category : this.aiml.getCategories())
		{
			this.patternToCategoryMap.put(category.getPattern(), category);
		}
		// 2. adjust elements within the map
		for (Category category : this.aiml.getCategories())
		{
			if(category.hasRedirectPattern())
				category.setRedirectCategory(patternToCategoryMap.get(category.getRedirectPattern()));
		}
		return;
	}
	
	private Category findCategory(String input)
	{
		for (Category category : this.aiml.getCategories())
		{
			Matcher matcher = category.getPrecompiledPattern().matcher(input);
			if(matcher.find())
			{
				if(category.hasRedirectPattern())
					category = category.getRedirectCategory();
				
				if(category.hasCaptureGroupTrigger())
					processCaptureGroupTrigger(
							category.getCaptureGroupNumber(),
							category.getCaptureGroupValueMeaning(),
							category.getPrecompiledPattern(),
							input);
				return category;
			}
		}
		// FIXME : It MUST always return something different from null or the default category!;
		System.out.println("WARNING : Category is null!");
		return null;
	}
		private void processCaptureGroupTrigger(Integer groupNumber, String groupMeaning, Pattern pattern, String input)
	{
		Matcher matcher = pattern.matcher(input);
		if(matcher.find())
		{
			if(groupMeaning.equals("name") && !userPersisted)
			{
				userName = matcher.group(groupNumber);
				User user = UserDb.getUserByName(userName);
				if(user != null)
				{
					userPersisted = true;
					userExisted = true;
					profession = user.getProfession();
					age = user.getAge();
				}
			}
			else if(groupMeaning.equals("profession"))
				profession = matcher.group(groupNumber);
			else if(groupMeaning.equals("age"))
				age = Integer.valueOf(matcher.group(groupNumber).trim());
			if(userName != null && profession != null && age != null && !userPersisted)
			{
				userPersisted = true;
				User newUser = new User();
				newUser.setName(userName);
				newUser.setAge(age);
				newUser.setProfession(profession);
				UserDb.insert(newUser);
			}
		}
	}

	public String generateReply(String userReply)
	{
		String sanitizedUserReply = Utils.sanitizeUserReply(userReply);
		Category category = this.findCategory(sanitizedUserReply);
		//register a hit on the question
		if(category == null)
		{
			return "Vorbeste pe limba mea te rog!";
		}
		if(!patternToCounterMap.containsKey(category.getPattern()))
			patternToCounterMap.put(category.getPattern(), 0);
		Integer counter = patternToCounterMap.get(category.getPattern()) + 1;
		patternToCounterMap.put(category.getPattern(), counter);
		if(counter > CATEGORY_HIT_THRESHOLD)
		{
			return "Te rog inceteaza sa imi pui aceeasi intrebare mereu !";
		}
		if(userExisted && !responseOverriden)
		{
			responseOverriden = true;
			return userName + ", " + profession + ", corect nu ?";
		}
		return category.getRandomAnswer();
	}

	public String getName()
	{
		return this.botName;
	}
	
}
