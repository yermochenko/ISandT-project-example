package by.vsu.ist.controller.manager;

import by.vsu.ist.domain.Account;
import by.vsu.ist.service.AccountService;
import by.vsu.ist.service.ServiceContainer;
import by.vsu.ist.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/manager/account/save.html")
public class AccountSaveController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Account account = new Account();
			String idParam = req.getParameter("id");
			if(idParam != null) {
				account.setId(Long.parseLong(idParam));
				account.setActive(req.getParameter("active") != null);
			} else {
				String number = req.getParameter("number");
				if(number == null) throw new IllegalArgumentException();
				account.setNumber(number);
			}
			String owner = req.getParameter("owner");
			if(owner == null) throw new IllegalArgumentException();
			account.setOwner(owner);
			try(ServiceContainer container = new ServiceContainer()) {
				AccountService accountService = container.getAccountServiceInstance();
				accountService.save(account);
				resp.sendRedirect(req.getContextPath() + "/manager/account/list.html");
			} catch(ServiceException e) {
				throw new ServletException(e);
			}
		} catch(IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
