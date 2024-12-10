package by.vsu.ist.controller.cashier;

import by.vsu.ist.service.ServiceContainer;
import by.vsu.ist.service.TransferService;
import by.vsu.ist.web.SumRequestParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet("/cashier/transfer.html")
public class TransferController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String senderNumber = req.getParameter("sender");
			if(senderNumber == null || senderNumber.isBlank()) throw new IllegalArgumentException();
			String receiverNumber = req.getParameter("receiver");
			if(receiverNumber == null || receiverNumber.isBlank()) throw new IllegalArgumentException();
			long sum = SumRequestParser.parse(req);
			String purpose = req.getParameter("purpose");
			if(purpose != null && purpose.isBlank()) purpose = null;
			try(ServiceContainer container = new ServiceContainer()) {
				TransferService transferService = container.getTransferServiceInstance();
				transferService.transfer(senderNumber, receiverNumber, sum, purpose);
				resp.sendRedirect(req.getContextPath() + "/cashier/account/list.html?msg=" + URLEncoder.encode("Операция выполнена успешно", StandardCharsets.UTF_8));
			} catch(SQLException e) {
				throw new ServletException(e);
			}
		} catch(IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
