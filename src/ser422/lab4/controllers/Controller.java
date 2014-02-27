
package ser422.lab4.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.asupoly.ser422.lab4.dao.NewsDAOFactory;
import edu.asupoly.ser422.lab4.model.NewsItemBean;

/**
 * Servlet implementation class CRUDArticle
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
		if (request.getParameter("action") != null)
		{
			action= request.getParameter("action");
		}
		else
		{
			action= "news";
		}
		switch (action)
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
			case "news":
				NewsItemBean[] stories= NewsDAOFactory.getTheDAO().getNews();
				request.setAttribute("articlesNumber", stories.length);
				request.setAttribute("stories", stories);
				for (int i= 0; i < stories.length; i++)
				{
					request.setAttribute("article" + i, stories[i]);
				}
				request.getRequestDispatcher("home.jsp").forward(request, response);
				break;
			default:
				break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		// TODO Auto-generated method stub
		PrintWriter out= response.getWriter();
		out.println("Hello!");
	}
}
