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
import java.util.Optional;

@WebServlet("/cashier/account/view.html")
public class AccountViewController extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Long id = Long.parseLong(req.getParameter("id"));
			try(ServiceFactory factory = ServiceFactory.getInstance()) {
				AccountService accountService = factory.getAccountServiceInstance();
				Optional<Account> account = accountService.findByIdWithTransfers(id);
				if(account.isPresent()) {
					req.setAttribute("account", account.get());
					req.getRequestDispatcher("/WEB-INF/jsp/cashier/account/view.jsp").forward(req, resp);
				} else {
					throw new IllegalArgumentException();
				}
			} catch(ServiceException e) {
				throw new ServletException(e);
			}
		} catch(IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
