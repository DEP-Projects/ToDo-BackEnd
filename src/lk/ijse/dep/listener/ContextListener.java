package lk.ijse.dep.listener;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BasicDataSource bds = new BasicDataSource();
        bds.setUsername("root");
        bds.setPassword("1234");
        bds.setUrl("jdbc:mysql://localhost:3306/ToDo");
        bds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        bds.setInitialSize(5);
        bds.setMaxTotal(5);
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("cp",bds);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
         BasicDataSource cp = (BasicDataSource) sce.getServletContext().getAttribute("cp");
        try {
            cp.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
