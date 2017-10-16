package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Category {
	
	private String   		  pattern      = "";
	private ArrayList<String> templateList = new ArrayList<>();
	private Boolean  		  random       = false;
	
	public Category() {}
	
	public void addTemplate(String item) {
		this.templateList.add(item);
	}
	
	public String getTemplate() {
		if(random) {
			return this.templateList.get((new Random().nextInt(this.templateList.size() - 1)));
		} else {
			return this.templateList.get(0);
		}
	}
	
	public List<String> getTemplateList() {
		return this.templateList;
	}
	
	public Boolean isRandom() {
		return random;
	}
	
	public void setRandom(Boolean random) {
		this.random = random;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("pattern : " + this.pattern + "\n");
		
		for (String template : templateList) {
			builder.append(" -" + template + "\n");
		}
		
		return builder.toString();
	}
	
}
