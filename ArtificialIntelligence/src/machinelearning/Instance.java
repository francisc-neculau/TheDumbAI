package machinelearning;

import java.util.Map;

public class Instance
{
	
	private Map<String, String> attributeToValue;
	
	public Instance(Map<String, String> attributeToValue)
	{
		this.attributeToValue = attributeToValue;
	}
	
	public String getValueFor(String attribute)
	{
		return this.attributeToValue.get(attribute);
	}
	
}
