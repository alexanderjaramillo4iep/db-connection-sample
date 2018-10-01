package controladores;


import modelos.Usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author USUARIO
 */
public class InicioServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("emailsesion_txt");
        String contrasena = request.getParameter("contrasenasesion_txt");
        
        RequestDispatcher rd = null;
        if (email != null) {
            Usuario u = consultarUsuario(email);

            if(u != null && u.getClave().equals(contrasena)) {
                u.setEmail(email);
                HttpSession session = request.getSession(true);
                session.setAttribute("email", email);
                rd = request.getRequestDispatcher("jsp/inicio.jsp");
            } else {
                rd = request.getRequestDispatcher("jsp/iniciarsesion.jsp");

            }
        }else {
            
            HttpSession session = request.getSession(false);
            if(session != null && session.getAttribute("email") != null) {
                rd = request.getRequestDispatcher("jsp/inicio.jsp");
            } else {
                rd = request.getRequestDispatcher("jsp/iniciarsesion.jsp");
            }
        }
        
        
        
        rd.forward(request, response);
    }
    
    
    Usuario consultarUsuario(String email) {
        Connection conexion = null;
        Usuario u = new Usuario();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejemplo", "root", "");
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM usuario WHERE email = ?");
            ResultSet resultados = ps.executeQuery();
            while(resultados.next()) {
                u.setNombre(resultados.getString("nombre"));
                u.setClave(resultados.getString("clave"));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InicioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InicioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(conexion != null && !conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(InicioServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return u;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
