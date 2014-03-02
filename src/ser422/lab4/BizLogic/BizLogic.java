
package ser422.lab4.BizLogic;

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
				action= "login$" + user.toString();
			}
		}
		return action;
	}

	/**
	 * @param userName
	 * @param passWord
	 */
	public static boolean makeUser(String userName, String passWord, INewsDAO controllerDOA)
	{

		return controllerDOA.createUser(new UserBean(userName, passWord, Role.GUEST));
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
}
