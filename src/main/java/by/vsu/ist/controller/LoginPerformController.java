package by.vsu.ist.controller;

import by.vsu.ist.domain.User;
import by.vsu.ist.service.ServiceFactory;
import by.vsu.ist.service.UserService;
import by.vsu.ist.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@WebServlet("/login/perform.html")
public class LoginPerformController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		if(login != null && password != null && !login.isBlank() && !password.isBlank()) {
			try(ServiceFactory factory = ServiceFactory.getInstance()) {
				UserService userService = factory.getUserServiceInstance();
				Optional<User> user = userService.findByLoginAndPassword(login, password);
				if(user.isPresent()) {
					HttpSession session = req.getSession();
					session.setAttribute("session_user", user.get());
					resp.sendRedirect(req.getContextPath());
				} else {
					resp.sendRedirect(
						req.getContextPath()
						+ "/login/form.html?msg="
						+ URLEncoder.encode("Имя пользователя или пароль не опознаны", StandardCharsets.UTF_8)
					);
				}
			} catch(ServiceException e) {
				throw new ServletException(e);
			}
		} else {
			resp.sendRedirect(
				req.getContextPath()
				+ "/login/form.html?msg="
				+ URLEncoder.encode("Имя пользователя или пароль не указаны", StandardCharsets.UTF_8)
			);
		}
	}
}
