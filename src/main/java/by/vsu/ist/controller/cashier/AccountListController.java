package by.vsu.ist.controller.cashier;

import by.vsu.ist.domain.Account;
import by.vsu.ist.service.AccountService;
import by.vsu.ist.service.ServiceFactory;
import by.vsu.ist.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/cashier/account/list.html")
public class AccountListController extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(ServiceFactory factory = ServiceFactory.getInstance()) {
			AccountService accountService = factory.getAccountServiceInstance();
			List<Account> accounts = accountService.findActive();
			req.setAttribute("accounts", accounts);
			req.getRequestDispatcher("/WEB-INF/jsp/cashier/account/list.jsp").forward(req, resp);
		} catch(ServiceException e) {
			throw new ServletException(e);
		}
	}
}
