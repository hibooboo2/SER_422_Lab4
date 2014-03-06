
package ser422.lab4.controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;

import ser422.lab4.BizLogic.BizLogic;
import edu.asupoly.ser422.lab4.dao.NewsDAOFactory;
import edu.asupoly.ser422.lab4.model.NewsItemBean;
import edu.asupoly.ser422.lab4.model.UserBean;

/**
 * Servlet implementation class For the Controller!
 */
public class Controller extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

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
		BizLogic.makeRandomStories();
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
		this.setResponseStuff(response);
		this.createSession(request, response);
		String action= null;
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
				NewsItemBean article= BizLogic.getNewsItem(Integer.parseInt(request.getParameter("articleID")));
				boolean isReporter= ((String) request.getSession(false).getAttribute("role")).equalsIgnoreCase("Reporter");
				boolean isSubscriber= ((String) request.getSession(false).getAttribute("role")).equalsIgnoreCase("Subscriber");
				boolean isArticleAuthor= article.getReporterId().equalsIgnoreCase((String) request.getSession(false).getAttribute("user"));
				if ((article.isPublic() || isSubscriber) || (isReporter && isArticleAuthor))
				{
					request.setAttribute("article", article);
					request.getRequestDispatcher("/NewsArticle/NewsArticle.jsp").include(request, response);
					request.getRequestDispatcher("/goHome.jsp").include(request, response);
				}
				else
				{
					response.sendRedirect("/Authentication/login.jsp?returnTO=viewArticle&articleID=" + request.getParameter("articleID"));
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
			case "createNewsStory":
				request.getRequestDispatcher("./NewsArticle/CreateNewsStory.jsp").include(request, response);
				request.getRequestDispatcher("/goHome.jsp").include(request, response);
				break;
			case "news":
				NewsItemBean[] stories;
				if ((stories= BizLogic.getNews((String) request.getSession(false).getAttribute("user"), this.getCookieMap(request))) != null)
				{
					request.setAttribute("articlesNumber", stories.length);
					request.setAttribute("stories", stories);
					request.getRequestDispatcher("home.jsp").include(request, response);
					request.getRequestDispatcher("/goHome.jsp").include(request, response);
					response.getWriter().println(NewsDAOFactory.getTheDAO().getClass().toString());
					break;
				}
				else
				{
					response.getWriter().println("WHAT THE FUCK?");
					request.getRequestDispatcher("/goHome.jsp").include(request, response);
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

		this.setResponseStuff(response);
		this.createSession(request, response);
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
				BizLogic.makeUser(request.getParameter("userID"), request.getParameter("role"));
				request.getSession();
				request.getSession(false).setAttribute("user", request.getParameter("userID"));
				request.getSession(false).setAttribute("role", request.getParameter("role"));
				response.sendRedirect("./");
				break;
			case "addComment":
				BizLogic.addComment(Integer.parseInt(request.getParameter("articleID")),
						(String) request.getSession(false).getAttribute("user"), request.getParameter("comment"));
				response.sendRedirect("?" + request.getParameter("from"));
				break;
			case "DeleteArticle":
				request.setAttribute("msg", "News Article deleted:" + BizLogic.deleteArticle(request.getParameter("articleID")));
				response.sendRedirect("./?msg=ARTICLE DELETED");
				break;
			case "favArticle":
				// TODO: Must do the logic to display the favorite articles separately. Favorites them with cookies right now. Commented
				BizLogic.addFavorite(request.getParameter("articleID"), this.getCookieMap(request), response);
				response.sendRedirect("./");
				break;
			case "EditArticleScreen":
				request.setAttribute("article", BizLogic.getNewsItem(Integer.parseInt(request.getParameter("articleID"))));
				request.getRequestDispatcher("./NewsArticle/EditNews.jsp").include(request, response);
				request.getRequestDispatcher("/goHome.jsp").include(request, response);
				break;
			case "EditArticle":
				BizLogic.editStory(Integer.parseInt(request.getParameter("articleID")), request.getParameter("newsTitle"),
						request.getParameter("newsStory"), Boolean.parseBoolean(request.getParameter("isPublic")));
				response.sendRedirect("./?msg=Updated%20" + request.getParameter("newsTitle"));
				break;
			case "createNewsStory":
				BizLogic.createNewsStory(new NewsItemBean(request.getParameter("newsTitle"), request.getParameter("newsStory"),
						(String) request.getSession(false).getAttribute("user"), Boolean.parseBoolean((request.getParameter("isPublic")))));
				response.sendRedirect("./");
				break;
			case "login":
				this.loginUser(request, response);
				break;
			default:
				response.sendError(Response.SC_BAD_REQUEST);
				break;
		}
	}

	/**
	 * @param request
	 * @return
	 */
	private HashMap<String,String> getCookieMap(HttpServletRequest request)
	{

		HashMap<String,String> cookiesMap= new HashMap<String,String>();
		if (request.getCookies() != null)
		{
			for (Cookie coo : request.getCookies())
			{
				cookiesMap.put(coo.getName(), coo.getValue());
			}
		}
		return cookiesMap;
	}

	/**
	 * @param response
	 */
	private void setResponseStuff(HttpServletResponse response)
	{

		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setContentType("text/html; charset=ISO-8859-1");
		response.addHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);
	}

	/**
	 * @param request
	 * @param response
	 */
	private void createSession(HttpServletRequest request, HttpServletResponse response)
	{

		if (request.getSession(false) == null)
		{
			request.getSession();
			request.getSession().setAttribute("role", "Guest");
			request.getSession().setAttribute("user", "none");
			request.getSession().setMaxInactiveInterval(1800);
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		String checkUserResult= BizLogic.checkForUser(request.getParameter("userid"), request.getParameter("passwd"));
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
				UserBean user= BizLogic.getUser(request.getParameter("userid"));
				request.getSession();
				request.getSession(false).setAttribute("user", user.getUserId());
				request.getSession(false).setAttribute("role", user.getRole().toString());
				if (request.getParameter("returnTO") != null)
				{
					response.sendRedirect(request.getParameter("returnTO"));
				}
				else
				{
					response.sendRedirect("./?");
				}
				break;
			default:
				response.sendError(Response.SC_BAD_REQUEST);
				break;
		}
	}
}
