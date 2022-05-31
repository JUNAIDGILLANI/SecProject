/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import java.sql.*;  
import oracle.jdbc.driver.OracleDriver;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 786
 */
@WebServlet(urlPatterns = {"/MyServlet"})
public class MyServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        String cap = request.getParameter("s");
        if(cap.equals("signin")){
            response.sendRedirect("index.html");
        }
        if(cap.equals("signIn")){
            try{
                String email = request.getParameter("Email");
                String password = request.getParameter("Password");
                if(email.equals("ali011@gmail.com") && password.equals("12345678")){
                    response.sendRedirect("CasesManagement.html");
                }else{
                    pw.println("INVALID EMAIL/PASSWORD");
                }
            }finally{
                
            }
        }else if(cap.equals("CaseRegisteration")){
           response.sendRedirect("CaseRegisteration.html");
        }else if(cap.equals("EditCase")){
            response.sendRedirect("EditCase.html");
        }else if(cap.equals("DeleteCase")){
            response.sendRedirect("DeleteCase.html");
        }else if(cap.equals("searchCase")){
            response.sendRedirect("SearchCase.html");
        }else if(cap.equals("LOGOUT")){
            response.sendRedirect("index.html");
        }else if(cap.equals("ADD")){
            String add_case_name = request.getParameter("add_case_name");
            String add_Case_Description = request.getParameter("add_Case_Description");
            String add_issue_Date = request.getParameter("add_issue_Date");
            String add_address = request.getParameter("add_address");
            String case_Type  = request.getParameter("add_case_Type");
            String add_email_Address  = request.getParameter("add_email_Address");
            if(!add_case_name.equals("") && (!add_Case_Description.equals("")) && (!add_issue_Date.equals("")) && (!add_address.equals("")) && (!(case_Type == null)) && (!add_email_Address.equals(""))){
             try{ 
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sec","sec");  
                    
                    Statement stmt=con.createStatement();  
                    String query = "Insert into Case values(?,?,?,?,?,?)";
                    PreparedStatement ps = con.prepareStatement(query);
                    
                    ps.setString(1, add_case_name);
                    ps.setString(2, add_Case_Description);
                    ps.setString(3, add_issue_Date);
                    ps.setString(4, add_address);
                    ps.setString(5, case_Type);
                    ps.setString(6, add_email_Address);
                    ps.executeUpdate();

                        pw.println("THE CASE BEEN ADDED.");
                } catch (SQLIntegrityConstraintViolationException ex) {
                    pw.println("SORRY THE CASE WITH THIS NAME IS ALREADY PRESENT.");
                }
                catch(Exception ex){
                    pw.println(ex);
                }
                
                }else{
                    pw.println("Please Fill All The Blanks........");
                }
            }else if(cap.equals("UPDATE")){
            String CASENAME = request.getParameter("update_case_name");
            String CASEDESCRIPTION = request.getParameter("update_Case_Description");
            String DATEOFISSUE = request.getParameter("update_issue_Date");
            String ADDRESS = request.getParameter("update_address");
            String CASETYPE  = request.getParameter("update_case_Type");
            String EMAILADDRESS  = request.getParameter("update_email_Address");
            if(!CASENAME.equals("") && (!CASEDESCRIPTION.equals("")) &&
                    (!DATEOFISSUE.equals("")) && (!ADDRESS.equals("")) && (!CASETYPE.equals("") )
                    && (!EMAILADDRESS.equals(""))){
            
                try{ 
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sec","sec");  
                     
                    String query = "update Case set CASEDESCRIPTION=?, DATEOFISSUE=?,ADDRESS=?,CASETYPE=?,EMAILADDRESS=? where CASENAME=?";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, CASEDESCRIPTION);
                    ps.setString(2, DATEOFISSUE);
                    ps.setString(3, ADDRESS);
                    ps.setString(4, CASETYPE);
                    ps.setString(5, EMAILADDRESS);
                    ps.setString(6, CASENAME);
                    int result = ps.executeUpdate();
                        if(result == 1){
                            pw.println("THE CASE BEEN UPDATED.");
                        }else{
                            pw.println("SORRY ANY CASE WITH SUCH NAME IS NOT PRESENT");
                        }
                    
                } catch (Exception ex) {
                    pw.println(ex);
                }
                
                }else{
                    pw.println("Please Fill All The Blanks........");
                }
            }else if(cap.equals("SEARCH")){
            String CASENAME = request.getParameter("search_case_name");
            try{
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sec","sec");  
                    boolean flag = false;
                    PreparedStatement ps = con.prepareStatement("select * from Case where CASENAME=?");
                    ps.setString(1, CASENAME);
                    ResultSet rs = ps.executeQuery();
                        while(rs.next()){
                            flag = true;
                           pw.print("Case Name -> "+rs.getString(1));
                           pw.print("<br />Case Description -> "+rs.getString(2));
                           pw.println("<br />Case issue Date -> "+rs.getString(3));
                           pw.println("<br />Address -> "+rs.getString(4));
                           pw.println("<br />Case Type -> "+rs.getString(5));
                           pw.println("<br />Email Address -> "+rs.getString(6));
                        }
                        if(!flag){
                            pw.println("SORRY! THE CASE YOU SEARCHED FOR IS NOT FOUND");
                        }
                } catch (Exception ex) {
                        pw.println(ex);
                }
            }else if(cap.equals("Delete")){
                String CASENAME = request.getParameter("Delete_Case_Name");
                try{
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sec","sec");  
                        String query = "delete from Case where CASENAME=?";
                        
                        PreparedStatement ps = con.prepareStatement(query);
 
                        ps.setString(1, CASENAME);
                        int result = ps.executeUpdate();
                        if(result == 1){
                            response.sendRedirect("CasesManagement.html");
                        }else{
                            pw.println("SORRY ANY CASE WITH SUCH NAME IS NOT PRESENT");
                        }
                }catch (Exception ex) {
                        pw.println(ex);
                }
            
            }else if(cap.equals("back")){
                response.sendRedirect("CasesManagement.html");
            }
}
}
