
package ser422.lab4.controllers;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;

import ser422.lab4.BizLogic.BizLogic;
import edu.asupoly.ser422.lab4.dao.INewsDAO;
import edu.asupoly.ser422.lab4.dao.NewsDAOFactory;
import edu.asupoly.ser422.lab4.model.NewsItemBean;
import edu.asupoly.ser422.lab4.model.UserBean;
import edu.asupoly.ser422.lab4.model.UserBean.Role;

/**
 * Servlet implementation class CRUDArticle
 */
public class Controller extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	private static INewsDAO		controllerDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller()
	{

		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException
	{

		super.init(config);
		controllerDAO= NewsDAOFactory.getTheDAO();
		// NewsItemBean[] news= new NewsItemBean[1000];
		// for (int i= 0; i < news.length; i++)
		// {
		// boolean isPublic= false;
		// if (i % 200 == 0)
		// {
		// isPublic= true;
		// }
		// news[i]=
		// new NewsItemBean(
		// "Lorem Ipsum Stories : " + i,
		// "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed interdum cursus nisi, non tempor orci venenatis nec. Integer posuere nulla non est dapibus, a consectetur neque aliquet. Curabitur vitae facilisis est, in malesuada ligula. Cras tincidunt, libero ac sollicitudin ultrices, tortor enim vestibulum sapien, sed suscipit metus lacus interdum enim. Phasellus posuere est id tristique euismod. Vestibulum eleifend vestibulum leo in aliquet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Maecenas id mauris pretium, semper massa vitae, sagittis neque. Morbi a posuere mi."
		// + i, "reporter", isPublic);
		// controllerDAO.createNewsItem(news[i]);
		// }
		// controllerDAO= new NewsDummyDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		// TODO Auto-generated method stub
		// Get the action
		// Listing all news items - getNews();
		// Set response attribute with that array.
		// request dispatcher forward to the view with our response.
		String action= null;
		if (request.getSession(false) == null)
		{
			request.getSession();
			request.getSession().setAttribute("role", Role.GUEST.toString());
			request.getSession().setAttribute("user", "none");
			request.getSession().setMaxInactiveInterval(1800);
		}
		if ((request.getParameter("action") != null))
		{
			action= (request.getParameter("action"));
		}
		else
		{
			action= "news";
		}
		if (request.getSession(false) != null && request.getSession(false).getAttribute("authenticatedAction") != null)
		{
			action= (String) request.getSession(false).getAttribute("authenticatedAction");
		}
		switch (action)
		{
			case "viewArticle":
				NewsItemBean article= controllerDAO.getNewsItem(Integer.parseInt(request.getParameter("articleID")));
				boolean isReporter= ((String) request.getSession(false).getAttribute("role")).equalsIgnoreCase("Reporter");
				boolean isSubscriber= ((String) request.getSession(false).getAttribute("role")).equalsIgnoreCase("Subscriber");
				boolean isGuest= ((String) request.getSession(false).getAttribute("role")).equalsIgnoreCase("Guest");
				boolean isArticleAuthor= article.getReporterId().equalsIgnoreCase((String) request.getSession(false).getAttribute("user"));
				boolean didWriteArticle=
						((String) request.getSession(false).getAttribute("user")).equalsIgnoreCase(article.getReporterId());
				if ((article.isPublic() || isSubscriber) || (isReporter && didWriteArticle))
				{
					request.setAttribute("article", article);
					request.getRequestDispatcher("/NewsArticle/NewsArticle.jsp").include(request, response);
					for (int i= 0; i < article.getComments().length; i++)
					{
						request.setAttribute("comment", article.getComments()[i]);
						request.getRequestDispatcher("/Comments/view.jsp").include(request, response);
					}
					if (!isGuest || (isReporter && isArticleAuthor))
					{
						request.getRequestDispatcher("/Comments/add.jsp").include(request, response);
					}
					request.getRequestDispatcher("/goHome.jsp").include(request, response);
				}
				break;
			case "login":
				request.getRequestDispatcher("/Authentication/login.jsp").include(request, response);
				request.getRequestDispatcher("/goHome.jsp").include(request, response);
				break;
			case "logout":
				request.getSession().invalidate();
				response.sendRedirect("./");
				break;
			case "EditNews":
				response.getWriter().println("Implement EditNews");
				break;
			case "createNewsStory":
				request.getRequestDispatcher("./NewsArticle/CreateNewsStory.jsp").include(request, response);
				request.getRequestDispatcher("/goHome.jsp").include(request, response);
				break;
			case "news":
				if (controllerDAO.getNews() != null)
				{
					NewsItemBean[] stories= controllerDAO.getNews();
					request.setAttribute("articlesNumber", stories.length);
					request.setAttribute("stories", stories);
					request.getRequestDispatcher("home.jsp").include(request, response);
					request.getRequestDispatcher("/goHome.jsp").include(request, response);
					break;
				}
				else
				{
					break;
				}
			default:
				response.sendError(Response.SC_BAD_REQUEST);
				break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		String action= "";
		if (request.getParameter("action") != null)
		{
			action= request.getParameter("action");
		}
		switch (action)
		{
			case "newUser":
				request.getRequestDispatcher("/Authentication/newUser.jsp").include(request, response);
				request.getRequestDispatcher("/goHome.jsp").include(request, response);
				break;
			case "makeUser":
				BizLogic.makeUser(request.getParameter("userID"), request.getParameter("role"), controllerDAO);
				request.getSession();
				request.getSession(false).setAttribute("user", request.getParameter("userID"));
				request.getSession(false).setAttribute("role", request.getParameter("role"));
				response.sendRedirect("./");
				break;
			case "addComment":
				// TODO: GET this working
				BizLogic.addComment(Integer.parseInt(request.getParameter("articleID")),
						(String) request.getSession(false).getAttribute("user"), request.getParameter("comment"), controllerDAO);
				response.sendRedirect("?" + request.getParameter("from"));
				break;
			case "DeleteArticle":
				request.setAttribute("msg",
						"News Article deleted:" + BizLogic.deleteArticle(request.getParameter("articleID"), controllerDAO));
				response.sendRedirect("./?msg=ARTICLE DELETED");
				break;
			case "favArticle":
				response.getWriter().println("Implement favArticle");
				break;
			case "EditNews":
				response.getWriter().println("Implement EditNews");
				break;
			case "createNewsStory":
				BizLogic.createNewsStory(new NewsItemBean(request.getParameter("newsTitle"), request.getParameter("newsStory"),
						(String) request.getSession(false).getAttribute("user"), Boolean.parseBoolean((request.getParameter("isPublic")))),
						controllerDAO);
				response.sendRedirect("./");
				break;
			case "login":
				String checkUserResult=
				BizLogic.checkForUser(request.getParameter("userid"), request.getParameter("passwd"), controllerDAO);
				switch (checkUserResult)
				{
					case "nullFields":
						request.setAttribute("msg", "Must Have Username and Password entered.");
						request.getRequestDispatcher("/Authentication/login.jsp").include(request, response);
						request.getRequestDispatcher("/goHome.jsp").include(request, response);
						break;
					case "fieldMismatch":
						request.setAttribute("msg", "Username Must equal your Password!");
						request.getRequestDispatcher("/Authentication/login.jsp").include(request, response);
						request.getRequestDispatcher("/goHome.jsp").include(request, response);
						break;
					case "noUser":
						request.setAttribute("msg", "The User: " + request.getParameter("userid") + " does not exist.");
						request.getRequestDispatcher("/Authentication/newUser.jsp").include(request, response);
						request.getRequestDispatcher("/goHome.jsp").include(request, response);
						break;
					case "userFound":
						UserBean user= BizLogic.getUser(request.getParameter("userid"), controllerDAO);
						request.getSession();
						request.getSession(false).setAttribute("user", user.getUserId());
						request.getSession(false).setAttribute("role", user.getRole().toString());
						response.sendRedirect("./");
						break;
					default:
						response.sendError(Response.SC_BAD_REQUEST);
						break;
				}
				break;
			default:
				response.sendError(Response.SC_BAD_REQUEST);
				break;
		}
	}
}