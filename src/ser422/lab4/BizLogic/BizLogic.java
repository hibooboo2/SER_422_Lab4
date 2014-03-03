
package ser422.lab4.BizLogic;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import edu.asupoly.ser422.lab4.dao.INewsDAO;
import edu.asupoly.ser422.lab4.model.CommentBean;
import edu.asupoly.ser422.lab4.model.NewsItemBean;
import edu.asupoly.ser422.lab4.model.UserBean;
import edu.asupoly.ser422.lab4.model.UserBean.Role;

public class BizLogic
{

	public static String checkForUser(String userId, String passwd, INewsDAO controllerDOA)
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
			UserBean user= controllerDOA.getUser(userId);
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
	public static boolean makeUser(String userName, String role, INewsDAO controllerDOA)
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
		return controllerDOA.createUser(new UserBean(userName, userName, userRole));
	}

	{
	}

	/**
	 * @param newsItemId
	 * @param userId
	 * @param comment
	 * @param controllerDAO
	 */
	public static void addComment(int newsItemId, String userId, String comment, INewsDAO controllerDAO)
	{

		NewsItemBean newsItem= controllerDAO.getNewsItem(newsItemId);
		newsItem.addComment(new CommentBean(newsItem, userId, comment));
	}

	/**
	 * @param userID
	 * @param controllerDAO
	 * @return
	 */
	public static UserBean getUser(String userID, INewsDAO controllerDAO)
	{

		return controllerDAO.getUser(userID);
	}

	/**
	 * @param articleID
	 * @param controllerDAO
	 */
	public static boolean deleteArticle(String articleID, INewsDAO controllerDAO)
	{

		return controllerDAO.deleteNewsItem(Integer.parseInt(articleID));
	}

	/**
	 * @param parameter
	 * @param parameter2
	 * @param controllerDAO
	 */
	public static boolean createNewsStory(NewsItemBean newArticle, INewsDAO controllerDAO)
	{
		return controllerDAO.createNewsItem(newArticle);
	}

	/**
	 * @param newFav
	 * @param cookies
	 * @param response
	 */
	public static void addFavorite(String newFav, Cookie[] cookies, HttpServletResponse response)
	{

		HashMap<String,String> cookiesMap= new HashMap<String,String>();
		if (cookies != null)
		{
			for (Cookie coo : cookies)
			{
				cookiesMap.put(coo.getName(), coo.getValue());
				response.addCookie(coo);
			}
			cookiesMap.get("favs");
			Cookie favsCookie= new Cookie("favs", cookiesMap.get("favs") + ":" + newFav);
			response.addCookie(favsCookie);
		}
		else
		{
			Cookie favsCookie= new Cookie("favs", newFav);
			response.addCookie(favsCookie);
		}
	}
}
