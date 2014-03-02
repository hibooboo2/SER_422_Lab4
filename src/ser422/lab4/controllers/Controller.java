
package ser422.lab4.controllers;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ser422.lab4.BizLogic.BizLogic;
import edu.asupoly.ser422.lab4.dao.INewsDAO;
import edu.asupoly.ser422.lab4.dao.NewsDAOFactory;
import edu.asupoly.ser422.lab4.model.NewsItemBean;

/**
 * Servlet implementation class CRUDArticle
 */
public class Controller extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	private static INewsDAO		controllerDOA;

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
		controllerDOA= NewsDAOFactory.getTheDAO();
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
		if ((request.getParameter("action") != null) && request.getSession(false) != null)
		{
			request.getSession(false).setAttribute("currentAction", request.getParameter("action"));
		}
		if (request.getSession(false) == null)
		{
			request.getSession();
			request.getSession(false).setAttribute("currentAction", "news");
			response.sendRedirect("./");
		}
		else
		{
			switch ((String) request.getSession(false).getAttribute("currentAction"))
			{
				case "viewArticle":
					NewsItemBean article= NewsDAOFactory.getTheDAO().getNewsItem(Integer.parseInt(request.getParameter("articleID")));
					request.setAttribute("article", article);
					request.getRequestDispatcher("/NewsArticle/NewsArticle.jsp").include(request, response);
					for (int i= 0; i < article.getComments().length; i++)
					{
						request.setAttribute("comment", article.getComments()[i]);
						request.getRequestDispatcher("/Comments/view.jsp").include(request, response);
					}
					request.getRequestDispatcher("/Comments/add.jsp").include(request, response);
					break;
				case "login":
					request.getRequestDispatcher("/Authentication/login.jsp").include(request, response);
					break;
				case "news":
					if (controllerDOA.getNews() != null)
					{
						NewsItemBean[] stories= controllerDOA.getNews();
						request.setAttribute("articlesNumber", stories.length);
						request.setAttribute("stories", stories);
						for (int i= 0; i < stories.length; i++)
						{
							request.setAttribute("article" + i, stories[i]);
						}
						request.getRequestDispatcher("home.jsp").forward(request, response);
						break;
					}
					else
					{
						break;
					}
				default:
					response.getWriter().println("ITS BROKEN! In the get on controller!");
					break;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		if (request.getSession(false) == null)
		{
			request.getSession(true).setAttribute("currentAction", "login");
			response.sendRedirect("./");
		}
		else
		{
			switch ((String) request.getSession(false).getAttribute("currentAction"))
			{
				case "newUser":
					request.getRequestDispatcher("/Authentication/newUser.jsp").include(request, response);
					break;
				case "makeUser":
					BizLogic.makeUser(request.getParameter("userid"), request.getParameter("passwd"), controllerDOA);
					request.getSession(false).setAttribute("currentAction", "news");
					request.getSession(false).setAttribute("user", request.getParameter("userid"));
					response.sendRedirect("./");
					break;
				case "login":
					String checkUserResult= BizLogic.checkForUser(request.getParameter("userid"), request.getParameter("passwd"));
					switch (checkUserResult.split("$")[0])
					{
						case "nullFields":
							request.setAttribute("msg", "Must Have Username and Password entered.");
							request.getRequestDispatcher("/Authentication/login.jsp").include(request, response);
							break;
						case "fieldMismatch":
							request.setAttribute("msg", "Username Must equal your Password!");
							request.getRequestDispatcher("/Authentication/login.jsp").include(request, response);
							break;
						case "noUser":
							request.setAttribute("msg", "The User: " + request.getParameter("userid") + " does not exist.");
							request.getSession(false).setAttribute("currentAction", "makeUser");
							request.getRequestDispatcher("/Authentication/newUser.jsp").include(request, response);
							break;
						case "login":
							request.setAttribute("msg", checkUserResult);
							request.getRequestDispatcher("/Authentication/login.jsp").include(request, response);
							// response.getWriter().println("ITS BROKEN! In the Login ON POST!");
							break;
					}
					break;
				default:
					response.getWriter().println("ITS BROKEN! IN THE MAIN SWITCH ON CONTROLLER POST!");
					break;
			}
		}
	}
}
