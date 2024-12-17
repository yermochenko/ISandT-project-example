package by.vsu.ist.controller.manager;

import by.vsu.ist.service.AccountService;
import by.vsu.ist.service.ServiceFactory;
import by.vsu.ist.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/manager/account/delete.html")
public class AccountDeleteController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Long id = Long.parseLong(req.getParameter("id"));
			try(ServiceFactory factory = ServiceFactory.getInstance()) {
				AccountService accountService = factory.getAccountServiceInstance();
				accountService.delete(id).orElseThrow(IllegalArgumentException::new);
				resp.sendRedirect(req.getContextPath() + "/manager/account/list.html");
			} catch(ServiceException e) {
				throw new ServletException(e);
			}
		} catch(IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
