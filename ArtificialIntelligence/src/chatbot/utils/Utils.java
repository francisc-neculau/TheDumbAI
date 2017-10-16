package chatbot.utils;

import java.io.File;
import java.security.SecureRandom;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import chatbot.aiml.component.Aiml;

public final class Utils
{
	private Utils()
	{
	}

	public static EntityManager getEntityManager()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AI");
		return emf.createEntityManager();
	}
	
	public static Aiml loadAimlFile(String aimlFilePath)
	{
		try
		{
			File file = new File(aimlFilePath);
			JAXBContext jaxbContext = JAXBContext.newInstance(Aiml.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			return (Aiml) jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static int nextRandomInt(int lowerBound, int upperBound)
	{
		Random random = new SecureRandom();
		return random.nextInt((upperBound - lowerBound)) + lowerBound;
	}
	
	public static String sanitizeUserReply(String userReply)
	{
		return userReply.toUpperCase();
	}
}
