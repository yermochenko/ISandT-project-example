package by.vsu.ist.controller.cashier;

import by.vsu.ist.service.*;
import by.vsu.ist.web.SumRequestParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/cashier/cash/deposit.html")
public class CashDepositController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String accountNumber = req.getParameter("account");
			if(accountNumber == null || accountNumber.isBlank()) throw new IllegalArgumentException();
			long sum = SumRequestParser.parse(req);
			try(ServiceContainer container = new ServiceContainer()) {
				TransferService transferService = container.getTransferServiceInstance();
				transferService.depositCash(accountNumber, sum);
				resp.sendRedirect(
				    req.getContextPath()
				    + "/cashier/account/list.html?msg="
				    + URLEncoder.encode("Операция выполнена успешно", StandardCharsets.UTF_8)
				);
			} catch(AccountNotExistsServiceException e) {
				resp.sendRedirect(
				    req.getContextPath()
				    + "/cashier/account/list.html?msg="
				    + URLEncoder.encode(String.format(
				        "Счёт с номером %s не существует",
				        e.getAccountNumber()
				    ), StandardCharsets.UTF_8)
				);
			} catch(AccountNotActiveServiceException e) {
				resp.sendRedirect(
				    req.getContextPath()
				    + "/cashier/account/list.html?msg="
				    + URLEncoder.encode(String.format(
				        "Счёт с номером %s не активен. Операции по этому счёту невозможны",
				        e.getAccount().getNumber()
				    ), StandardCharsets.UTF_8))
				;
			} catch(ServiceException e) {
				throw new ServletException(e);
			}
		} catch(IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
