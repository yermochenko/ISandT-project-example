package by.vsu.ist.controller;

import by.vsu.ist.domain.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/index.html")
public class MainController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession(false);
		if(session != null) {
			User user = (User) session.getAttribute("session_user");
			if(user != null) {
				switch(user.getRole()) {
					case ADMIN -> resp.sendRedirect(req.getContextPath() + "/admin/index.html");
					case MANAGER -> resp.sendRedirect(req.getContextPath() + "/manager/account/list.html");
					case CASHIER -> resp.sendRedirect(req.getContextPath() + "/cashier/account/list.html");
				}
			} else {
				resp.sendRedirect(req.getContextPath() + "/login/form.html");
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/login/form.html");
		}
	}
}
