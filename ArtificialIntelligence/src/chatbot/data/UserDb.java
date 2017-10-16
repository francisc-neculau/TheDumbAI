package chatbot.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import chatbot.utils.Utils;


public class UserDb
{	
	private UserDb() {} 
	
	public static void insert(User user)
	{
		EntityManager entityManager = Utils.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		try
		{
			entityManager.persist(user);
			entityTransaction.commit();
		} catch(Exception e)
		{
			entityTransaction.rollback();
		} finally
		{
			entityManager.close();
		}
	}
	
	public static void update(User user)
	{
		EntityManager entityManager = Utils.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		try
		{
			entityManager.merge(user);
			entityTransaction.commit();
		} catch(Exception e)
		{
			entityTransaction.rollback();
		} finally
		{
			entityManager.close();
		}
	}
	
	public static void delete(User user)
	{
		EntityManager entityManager = Utils.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		try
		{
			entityManager.remove(entityManager.merge(user));
			entityTransaction.commit();
		} catch(Exception e)
		{
			entityTransaction.rollback();
		} finally
		{
			entityManager.close();
		}
	}
	
	public static User getUserByEmail(String email)
	{
		EntityManager em = Utils.getEntityManager();
		User user = null;
		try
		{
			String jpqlStatement = ""
					+ "SELECT u FROM User u "
					+ "WHERE u.email = :email";
			TypedQuery<User> query = em.createQuery(jpqlStatement, User.class);
			query.setParameter("email", email);
			user = query.getSingleResult();
		}
		catch (NoResultException e)
		{
			// FIXME: logger
		}
		finally
		{
			em.close();
		}
		return user;
	}
	
	public static User getUserByName(String name)
	{
		EntityManager em = Utils.getEntityManager();
		User user = null;
		try
		{
			String jpqlStatement = ""
					+ "SELECT u FROM User u "
					+ "WHERE u.name = :name";
			TypedQuery<User> query = em.createQuery(jpqlStatement, User.class);
			query.setParameter("name", name);
			user = query.getSingleResult();
		}
		catch (NoResultException e)
		{
			// FIXME: logger
		}
		finally
		{
			em.close();
		}
		return user;
	}

}

