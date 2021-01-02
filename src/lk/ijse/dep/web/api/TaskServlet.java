package lk.ijse.dep.web.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.dep.web.model.Task;
import lk.ijse.dep.web.util.Priority;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Servlet", urlPatterns = "/tasks")
public class TaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");
        try( Connection con = cp.getConnection();){
             Jsonb jsonb = JsonbBuilder.create();
             Task task = jsonb.fromJson(request.getReader(), Task.class);
             if (task.getTaskName() == null || task.getTaskId()==0 || task.getDescription()==null){
                 response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                 return;
             }
            if (task.getTaskName().isEmpty() || task.getDescription().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            PreparedStatement pstm = con.prepareStatement("INSERT INTO task VALUES (?,?,?,?,?,?)");
            pstm.setString(1,String.valueOf(task.getTaskId()));
            pstm.setString(2, task.getUserName());
            pstm.setString(3,task.getTaskName());
            pstm.setString(4,task.getDescription());
            pstm.setString(5,String.valueOf(task.getPriority()));
            pstm.setBoolean(6, task.getCompleted());
            boolean success =pstm.executeUpdate() > 0;
            if (success){
                response.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
         BasicDataSource cp = (BasicDataSource) getServletContext().getAttribute("cp");
         String taskId = req.getParameter("task_id");
         if (taskId == null){
             resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
             return;
         }
        try( Connection con = cp.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT *  FROM task WHERE task_id=?");
            pstm.setInt(1, Integer.parseInt(taskId));
            if (pstm.executeQuery().next()) {
                pstm = con.prepareStatement("DELETE FROM task WHERE task_id=?");
                pstm.setInt(1, Integer.parseInt(taskId));
                boolean success = pstm.executeUpdate() > 0;
                if (success) {
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    System.out.println("11111111111");
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }catch (SQLIntegrityConstraintViolationException x){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException throwables) {
            System.out.println("22222222222");
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            throwables.printStackTrace();
        }

    }
}
