
package ser422.lab4.BizLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import de.svenjacobs.loremipsum.LoremIpsum;
import edu.asupoly.ser422.lab4.dao.INewsDAO;
import edu.asupoly.ser422.lab4.dao.NewsDAOFactory;
import edu.asupoly.ser422.lab4.model.NewsItemBean;
import edu.asupoly.ser422.lab4.model.UserBean;
import edu.asupoly.ser422.lab4.model.UserBean.Role;

public class BizLogic
{

	private static INewsDAO	theDAO	= NewsDAOFactory.getTheDAO();

	public static String checkForUser(String userId, String passwd)
	{

		String action= "";
		if (userId == null || userId.length() == 0 || passwd == null || passwd.length() == 0)
		{
			action= "nullFields";
		}
		else if (!userId.equals(passwd))
		{
			action= "fieldMismatch";
		}
		// HERE YOU HAVE TO CHECK IF THE USER EXISTS OR NOT!
		else
		{
			UserBean user= theDAO.getUser(userId);
			if (user == null)
			{
				action= "noUser";
			}
			else
			{
				action= "userFound";
			}
		}
		return action;
	}

	/**
	 * @param userName
	 * @param role
	 * @param passWord
	 */
	public static boolean makeUser(String userName, String role)
	{

		Role userRole= null;
		switch (role)
		{
			case "subscriber":
				userRole= Role.SUBSCRIBER;
				break;
			case "reporter":
				userRole= Role.REPORTER;
				break;
		}
		return theDAO.createUser(new UserBean(userName, userName, userRole));
	}

	/**
	 * @param newsItemId
	 * @param userId
	 * @param comment
	 * @param controllerDAO
	 */
	public static void addComment(int newsItemId, String userId, String comment)
	{

		theDAO.storeComment(newsItemId, userId, comment);
	}

	/**
	 * @param userID
	 * @return
	 */
	public static UserBean getUser(String userID)
	{

		return theDAO.getUser(userID);
	}

	/**
	 * @param articleID
	 * @param controllerDAO
	 */
	public static boolean deleteArticle(String articleID)
	{

		return theDAO.deleteNewsItem(Integer.parseInt(articleID));
	}

	/**
	 * @param parameter
	 * @param parameter2
	 * @param controllerDAO
	 */
	public static boolean createNewsStory(NewsItemBean newArticle)
	{

		return theDAO.createNewsItem(newArticle);
	}

	/**
	 * @param articleID
	 * @param cookieMap
	 * @param response
	 */
	public static void addFavorite(int articleID, HashMap<String,String> cookieMap, HttpServletResponse response)
	{

		if (cookieMap.containsKey("favs"))
		{
			cookieMap.get("favs");
			Cookie favsCookie= new Cookie("favs", cookieMap.get("favs") + ":" + articleID);
			response.addCookie(favsCookie);
		}
		else
		{
			Cookie favsCookie= new Cookie("favs", "" + articleID);
			response.addCookie(favsCookie);
		}
	}


	/**
	 * @param articleID
	 * @param cookieMap
	 * @param response
	 */
	public static void removeFavorite(int articleID, HashMap<String,String> cookieMap, HttpServletResponse response)
	{

		if (cookieMap.containsKey("favs"))
		{
			ArrayList<Integer> favs= parseFavs(cookieMap.get("favs"));
			favs.remove((Integer) articleID);
			if (favs.size() > 0)
			{
				String newFavs= "" + favs.get(0);
				for (int i= 0; i < (favs.size() - 1); i++)
				{
					newFavs+= ":" + favs.get(i + 1);
				}
				Cookie favsCookie= new Cookie("favs", newFavs);
				response.addCookie(favsCookie);
			}
			else
			{
				Cookie favsCookie= new Cookie("favs", "");
				favsCookie.setMaxAge(0);
				response.addCookie(favsCookie);
			}
		}
	}

	/**
	 * @param isPublic
	 * @param parameter
	 * @param parameter2
	 * @param parameter3
	 */
	public static boolean editStory(int articleID, String newsTitle, String newsStory, Boolean isPublic)
	{

		boolean updated;
		if (updated= theDAO.updateNewsItem(articleID, newsTitle, newsStory))
		{
			NewsItemBean article= theDAO.getNewsItem(articleID);
			synchronized (article)
			{
				article.setPublic(isPublic);
			}
		}
		return updated;
	}

	/**
	 * @param parseInt
	 * @return
	 */
	public static NewsItemBean getNewsItem(String userName, int articleID)
	{

		if (canViewArticle(userName, articleID))
		{
			return theDAO.getNewsItem(articleID);
		}
		else
		{
			return null;
		}
	}

	/**
	 * @param attribute
	 * @return
	 */
	public static ArrayList<NewsItemBean[]> getNews(String userName, HashMap<String,String> cookieMap)
	{

		// TODO: Implement THIS SHIT!
		// TODO: This should filter Articles Based On roles!
		ArrayList<NewsItemBean[]> allForUser= new ArrayList<NewsItemBean[]>();
		ArrayList<NewsItemBean> allArticles= new ArrayList<NewsItemBean>(Arrays.asList(theDAO.getNews()));
		ArrayList<NewsItemBean> favArticles= new ArrayList<NewsItemBean>();
		ArrayList<NewsItemBean> canManage= new ArrayList<NewsItemBean>();
		ArrayList<Integer> favs= getFavs(userName, cookieMap);
		ArrayList<NewsItemBean> cantView= new ArrayList<NewsItemBean>();
		for (NewsItemBean article : allArticles)
		{
			if (!canViewArticle(userName, article.getItemId()))
			{
				cantView.add(article);
			}
		}
		allArticles.removeAll(cantView);
		for (NewsItemBean article : allArticles)
		{
			if (favs.contains(article.getItemId()))
			{
				favArticles.add(article);
			}
			if (canManageArticle(userName, article.getItemId()))
			{
				canManage.add(article);
			}
		}
		allArticles.removeAll(favArticles);
		allForUser.add(favArticles.toArray(new NewsItemBean[favArticles.size()]));
		allForUser.add(allArticles.toArray(new NewsItemBean[allArticles.size()]));
		allForUser.add(canManage.toArray(new NewsItemBean[canManage.size()]));
		return allForUser;
	}

	/**
	 * @param userName
	 * @param cookieMap
	 * @return
	 */
	private static ArrayList<Integer> getFavs(String userName, HashMap<String,String> cookieMap)
	{

		if (userName == null)
		{
			return parseFavs(cookieMap.get("favs"));
		}
		else {

			return theDAO.getUser(userName).getFavArticles();
		}
	}

	/**
	 * @param string
	 */
	private static ArrayList<Integer> parseFavs(String favs)
	{

		ArrayList<Integer> theFavs= new ArrayList<Integer>();
		if (favs != null)
		{
			for (String fav : favs.split(":"))
			{
				theFavs.add(Integer.parseInt(fav));
			}
		}
		return theFavs;
	}

	/**
	 * @param attribute
	 * @param articleID
	 * @return
	 */
	public static boolean canViewArticle(String user, int articleID)
	{

		if (user != null)
		{
			boolean isReporter= ((theDAO.getUser(user).getRole().toString()).equalsIgnoreCase("Reporter"));
			boolean isSubscriber= theDAO.getUser(user).getRole().toString().equalsIgnoreCase("Subscriber");
			boolean isArticleAuthor= theDAO.getNewsItem(articleID).getReporterId().equalsIgnoreCase(user);
			if ((theDAO.getNewsItem(articleID).isPublic() || isSubscriber) || (isReporter && isArticleAuthor))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (theDAO.getNewsItem(articleID).isPublic())
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	/**
	 * @param attribute
	 * @param parseInt
	 * @return
	 */
	public static boolean canComment(String user, int articleID)
	{

		if (user != null)
		{
			boolean isSubscriber= theDAO.getUser(user).getRole().toString().equalsIgnoreCase("Subscriber");
			boolean isReporter= ((theDAO.getUser(user).getRole().toString()).equalsIgnoreCase("Reporter"));
			boolean isArticleAuthor= theDAO.getNewsItem(articleID).getReporterId().equalsIgnoreCase(user);
			return ((isSubscriber) || (isReporter && isArticleAuthor));
		}
		else
		{
			return false;
		}

	}

	/**
	 * @param user
	 * @return
	 */
	public static boolean canAuthorArticles(String user)
	{

		if (user != null)
		{
			return theDAO.getUser(user).getRole().toString().equalsIgnoreCase("Reporter");
		}
		else
		{
			return false;
		}
	}

	public static boolean canManageArticle(String user, int articleID)
	{

		if (user != null)
		{
			boolean isReporter= ((theDAO.getUser(user).getRole().toString()).equalsIgnoreCase("Reporter"));
			boolean isArticleAuthor= theDAO.getNewsItem(articleID).getReporterId().equalsIgnoreCase(user);
			return (isReporter && isArticleAuthor);
		}
		else
		{
			return false;
		}
	}

	/**
	 * 
	 */
	public static void makeRandomStories()
	{

		theDAO= NewsDAOFactory.getTheDAO();
		NewsItemBean[] news= new NewsItemBean[10];
		LoremIpsum rand= new LoremIpsum();
		for (int i= 0; i < news.length; i++)
		{
			boolean isPublic= false;
			if (i % 2 == 0)
			{
				isPublic= true;
			}
			news[i]= new NewsItemBean(rand.getWords(3) + i, rand.getParagraphs() + i, "reporter", isPublic);
			theDAO.createNewsItem(news[i]);
		}
	}

	/**
	 * @param articleID
	 * @param currentUserID
	 * @param response
	 */
	public static void addFavorite(int articleID, String currentUserID, HttpServletResponse response)
	{

		theDAO.getUser(currentUserID).getFavArticles().add(articleID);
	}

	/**
	 * @param parseInt
	 * @param currentUserID
	 * @param response
	 */
	public static void removeFavorite(int articleID, String currentUserID, HttpServletResponse response)
	{

		theDAO.getUser(currentUserID).getFavArticles().remove((Integer) articleID);
	}
}
