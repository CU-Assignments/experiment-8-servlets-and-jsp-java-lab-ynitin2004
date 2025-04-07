import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class EmployeeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String empId = request.getParameter("empId");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "password");

            String query = "SELECT * FROM employees WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, empId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                out.println("<h3>Employee Found:</h3>");
                out.println("ID: " + rs.getInt("id") + "<br>");
                out.println("Name: " + rs.getString("name") + "<br>");
                out.println("Department: " + rs.getString("department"));
            } else {
                out.println("<h3>No employee found with ID " + empId + "</h3>");
            }

            con.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
