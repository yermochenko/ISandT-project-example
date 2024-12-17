package by.vsu.ist.web;

import by.vsu.ist.repository.jdbc.DatabaseConnector;
import by.vsu.ist.repository.jdbc.cp.ConnectionPool;
import by.vsu.ist.repository.jdbc.cp.ConnectionPoolException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationLifecycleListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext context = event.getServletContext();
			String jdbcDriver   = context.getInitParameter("jdbc-driver");
			String jdbcUrl      = context.getInitParameter("jdbc-url");
			String jdbcUser     = context.getInitParameter("jdbc-user");
			String jdbcPassword = context.getInitParameter("jdbc-password");
			int poolSize = Integer.parseInt(context.getInitParameter("connection-pool-size"));
			DatabaseConnector.init(jdbcDriver, jdbcUrl, jdbcUser, jdbcPassword);
			ConnectionPool.getInstance().init(poolSize);
		} catch(ClassNotFoundException | ConnectionPoolException | NumberFormatException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ConnectionPool.getInstance().destroy();
	}
}
