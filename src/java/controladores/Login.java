/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Producto;

/**
 *
 * @author Usuario
 */
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //recibir parámetros desde la solicitud
        String usuario = request.getParameter("usuario");
        String contrasenia = request.getParameter("contrasenia");
        
        RequestDispatcher rd = null;
        if(usuario != null && !usuario.equals("")) {
            rd = request.getRequestDispatcher("jsp/home.jsp");
            request.setAttribute("mensaje", "Bienvenido " + usuario);
            List<Producto> listaProductos = listarProductos();
            request.setAttribute("listaProductos", listaProductos);
        } else {
            rd = request.getRequestDispatcher("jsp/login.jsp");
            request.setAttribute("mensaje", "Llene los campos del formulario correctamente");
        }
        //pasar parámetros desde la solicitud
        rd.forward(request, response);
    }
    
    
    List<Producto> listarProductos() {
        List<Producto> listaProductos = new ArrayList<Producto>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejemplo", "root", "");
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM producto");
            ResultSet resultados = ps.executeQuery();
            while(resultados.next()) {
                String nombre = resultados.getString("nombre");
                int precio = resultados.getInt("precio");
                Producto p = new Producto();
                p.nombre = nombre;
                p.precio = precio;
                listaProductos.add(p);
            }
            conexion.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaProductos;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
