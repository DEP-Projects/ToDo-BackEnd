package lk.ijse.dep.web.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.dep.web.model.Task;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Servlet", urlPatterns = "/tasks")
public class TaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");
         response.setContentType("application/json");

        try(PrintWriter out = response.getWriter(); Connection con = cp.getConnection()) {
            String username = request.getParameter("username");
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM task" + ((username != null)? " WHERE username=?" : ""));

            if(username!=null){
               pstm.setObject(1,username);
            }
             ResultSet rst = pstm.executeQuery();
            List<Task> taskList=new ArrayList<>();
            while (rst.next()){
                int taskId =rst.getInt(1);
                String userName = rst.getString(2);
                String taskName = rst.getString(3);
                String description =rst.getString(4);
                String priority= rst.getString(5);
                Boolean completed=rst.getBoolean(6);
                taskList.add(new Task(taskId,userName,taskName,description,priority,completed));
            }
            if (username != null && taskList.isEmpty()){
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }else {
                 Jsonb jsonb = JsonbBuilder.create();
                 out.println(jsonb.toJson(taskList));
                 con.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
