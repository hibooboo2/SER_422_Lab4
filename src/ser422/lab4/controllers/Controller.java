
package ser422.lab4.controllers;

import java.io.IOException;
import java.util.ArrayList;
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
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException
	{

		super.init(config);
		BizLogic.makeRandomStories();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		this.setResponseStuff(response);
		this.createSession(request, response);
		this.bounceBack(request, response);
		String action= null;
		if ((request.getParameter("action") != null))
		{
			action= (request.getParameter("action"));
		}
		else
		{
			action= "news";
		}
		this.confirmAuthentication(response, request, action);
		switch (action)
		{
			case "viewArticle":
				NewsItemBean article=
				BizLogic.getNewsItem(this.currentUserID(request), Integer.parseInt(request.getParameter("articleID")));
				if (article != null)
				{
					boolean canComment=
							BizLogic.canComment(this.currentUserID(request), Integer.parseInt(request.getParameter("articleID")));
					request.setAttribute("canComment", String.valueOf(canComment));
					request.setAttribute("article", article);
					request.getRequestDispatcher("/NewsArticle/NewsArticle.jsp").include(request, response);
					this.userMenu(request, response);
				}
				else
				{
					response.sendRedirect("/Authentication/login.jsp");
				}
				// response.sendRedirect("/Authentication/login.jsp?returnTO=viewArticle&articleID="
				// + request.getParameter("articleID"));
				break;
			case "login":
				request.getRequestDispatcher("/Authentication/login.jsp").include(request, response);
				this.userMenu(request, response);
				break;
			case "logout":
				request.getSession().invalidate();
				response.sendRedirect("./");
				break;
			case "createNewsStory":
				boolean canAuthorArticle= BizLogic.canAuthorArticles(this.currentUserID(request));
				if (canAuthorArticle)
				{
					request.getRequestDispatcher("./NewsArticle/CreateNewsStory.jsp").include(request, response);
					this.userMenu(request, response);
				}
				else
				{
					request.getRequestDispatcher("/Authentication/login.jsp").include(request, response);
				}
				break;
			case "news":
				ArrayList<NewsItemBean[]> stories;
				if ((stories= BizLogic.getNews(this.currentUserID(request), this.getCookieMap(request))) != null)
				{
					request.setAttribute("favstories", stories.get(0));
					request.setAttribute("stories", stories.get(1));
					request.setAttribute("articlesCanManage", stories.get(2));
					request.getRequestDispatcher("home.jsp").include(request, response);
					this.userMenu(request, response);
					response.getWriter().println(NewsDAOFactory.getTheDAO().getClass().toString());
					break;
				}
				else
				{
					response.getWriter().println("WHAT THE FUCK?");
					this.userMenu(request, response);
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

		this.bounceBack(request, response);
		this.setResponseStuff(response);
		this.createSession(request, response);
		String action= "";
		if (request.getParameter("action") != null)
		{
			action= request.getParameter("action");
		}
		if (this.requiresLogin(action) && !this.isLoggedIN(request))
		{
			response.addHeader("from", request.getRequestURI() + "?" + request.getQueryString());
			response.sendRedirect("./?action=login");
		}
		else
		{
			switch (action)
			{
				case "newUser":
					request.getRequestDispatcher("/Authentication/newUser.jsp").include(request, response);
					this.userMenu(request, response);
					break;
				case "makeUser":
					BizLogic.makeUser(request.getParameter("userID"), request.getParameter("role"));
					request.getSession();
					request.getSession(false).setAttribute("user", request.getParameter("userID"));
					request.getSession(false).setAttribute("role", request.getParameter("role"));
					response.sendRedirect("./");
					break;
				case "addComment":
					BizLogic.addComment(Integer.parseInt(request.getParameter("articleID")), (String) request.getSession(false)
							.getAttribute("user"), request.getParameter("comment"));
					response.sendRedirect("?" + request.getParameter("from"));
					break;
				case "DeleteArticle":
					request.setAttribute("msg", "News Article deleted:" + BizLogic.deleteArticle(request.getParameter("articleID")));
					response.sendRedirect("./?msg=ARTICLE DELETED");
					break;
				case "favArticle":
					BizLogic.addFavorite(request.getParameter("articleID"), this.getCookieMap(request), response);
					response.sendRedirect("./");
					break;
				case "unFavArticle":
					BizLogic.removeFavorite(Integer.parseInt(request.getParameter("articleID")), this.getCookieMap(request), response);
					response.sendRedirect("./");
					break;
				case "EditArticleScreen":
					request.setAttribute("article",
							BizLogic.getNewsItem(this.currentUserID(request), Integer.parseInt(request.getParameter("articleID"))));
					request.getRequestDispatcher("./NewsArticle/EditNews.jsp").include(request, response);
					this.userMenu(request, response);
					break;
				case "EditArticle":
					BizLogic.editStory(Integer.parseInt(request.getParameter("articleID")), request.getParameter("newsTitle"),
							request.getParameter("newsStory"), Boolean.parseBoolean(request.getParameter("isPublic")));
					response.sendRedirect("./?msg=Updated%20" + request.getParameter("newsTitle"));
					break;
				case "createNewsStory":
					BizLogic.createNewsStory(new NewsItemBean(request.getParameter("newsTitle"), request.getParameter("newsStory"), this
							.currentUserID(request), Boolean.parseBoolean((request.getParameter("isPublic")))));
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
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	private String currentUserID(HttpServletRequest request)
	{
	
		return (String) request.getSession(false).getAttribute("user");
	}

	/**
	 * @param request
	 * @return
	 */
	private boolean isLoggedIN(HttpServletRequest request)
	{

		String currentUser= (String) request.getSession(false).getAttribute("user");
		return !currentUser.equalsIgnoreCase("none");
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
				this.userMenu(request, response);
				break;
			case "fieldMismatch":
				request.setAttribute("msg", "Username Must equal your Password!");
				request.getRequestDispatcher("/Authentication/login.jsp").include(request, response);
				this.userMenu(request, response);
				break;
			case "noUser":
				request.setAttribute("msg", "The User: " + request.getParameter("userid") + " does not exist.");
				request.getRequestDispatcher("/Authentication/newUser.jsp").include(request, response);
				this.userMenu(request, response);
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

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void userMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setAttribute("canAuthorArticles", String.valueOf(BizLogic.canAuthorArticles(this.currentUserID(request))));
		request.getRequestDispatcher("/goHome.jsp").include(request, response);
	}

	/**
	 * @param action
	 * @return
	 */
	private boolean requiresLogin(String action)
	{

		switch (action)
		{
			case "createNewsStory":
				return true;
			case "addComment":
				return true;
			case "DeleteArticle":
				return true;
			case "EditArticleScreen":
				return true;
			case "EditArticle":
				return true;
			default:
				return false;
		}
	}

	/**
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	private void confirmAuthentication(HttpServletResponse response, HttpServletRequest request, String action) throws IOException
	{

		if (this.requiresLogin(action) && !this.isLoggedIN(request))
		{
			response.addHeader("from", request.getRequestURI() + "?" + request.getQueryString());
			response.sendRedirect("./?action=login");
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void bounceBack(HttpServletRequest request, HttpServletResponse response) throws IOException
	{

		// TODO: MAYBE MAKE THIS WORK? LAST PRIORITY!
		if (request.getHeader("from") != null && !request.getHeader("from").equalsIgnoreCase("go"))
		{
			String to= request.getHeader("from");
			response.setHeader("from", "go");
			response.sendRedirect(to);
		}
	}
}
