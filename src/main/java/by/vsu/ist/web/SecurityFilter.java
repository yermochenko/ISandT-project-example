package by.vsu.ist.web;

import by.vsu.ist.domain.Role;
import by.vsu.ist.domain.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebFilter({
	"/logout/perform.html",
	"/cashier/*",
	"/manager/*"
})
public class SecurityFilter extends HttpFilter {
	public static final Map<Role, List<String>> ALLOWED_URLS = Map.ofEntries(
		Map.entry(
			Role.CASHIER,
			List.of(
				"/cashier/*",
				"/logout/perform.html"
			)
		),
		Map.entry(
			Role.MANAGER,
			List.of(
				"/manager/*",
				"/logout/perform.html"
			)
		)
	);

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
		String contextPath = req.getContextPath();
		String loginFormUrl = contextPath + "/login/form.html";
		HttpSession session = req.getSession(false);
		if(session != null) {
			User user = (User) session.getAttribute("session_user");
			if(user != null) {
				String uri = req.getRequestURI().substring(contextPath.length());
				Role role = user.getRole();
				List<String> allowedUrls = ALLOWED_URLS.get(role);
				boolean allowed = false;
				for(String allowedUrl : allowedUrls) {
					if(allowedUrl.equals(uri)
					|| (allowedUrl.startsWith("*") && uri.endsWith(allowedUrl.substring(1)))
					|| (allowedUrl.endsWith("*") && uri.startsWith(allowedUrl.substring(0, allowedUrl.length() - 1)))) {
						allowed = true;
						break;
					}
				}
				if(allowed) {
					chain.doFilter(req, resp);
				} else {
					resp.sendRedirect(loginFormUrl);
				}
			} else {
				resp.sendRedirect(loginFormUrl);
			}
		} else {
			resp.sendRedirect(loginFormUrl);
		}
	}
}
