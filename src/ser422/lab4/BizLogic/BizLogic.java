
package ser422.lab4.BizLogic;

import edu.asupoly.ser422.lab4.dao.NewsDAOFactory;
import edu.asupoly.ser422.lab4.model.UserBean;

public class BizLogic
{

	public static String checkForUser(String userId, String passwd)
	{

		String action= "";
		if (userId == null || userId.length() == 0 || passwd == null || passwd.length() == 0)
		{
			action= "nullFields";
		}
		else if (!userId.equals(passwd))
		{
			action= "fieldsNotEqual";
		}
		// HERE YOU HAVE TO CHECK IF THE USER EXISTS OR NOT!
		else
		{
			UserBean user= NewsDAOFactory.getTheDAO().getUser(userId);
			if (user == null)
			{
				action= "noUser";
			}
			else
			{
				action= "loggedIn$" + user.toString();
			}
		}
		return action;
	}
}
