package chatbot.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User
{
	
	@Id
	@Column(name="name")
	private String name;
	
	@Column(name="age")
	private int age;
	
	@Column(name="profession")
	private String profession;

	public User()
	{
		name = "";
		age  = 0;
		profession = "";
	}

	
	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public int getAge()
	{
		return age;
	}


	public void setAge(int age)
	{
		this.age = age;
	}


	public String getProfession()
	{
		return profession;
	}


	public void setProfession(String profession)
	{
		this.profession = profession;
	}


	@Override
	public String toString()
	{
		return name + " / " + age + " / " + profession;
	}
	
}
