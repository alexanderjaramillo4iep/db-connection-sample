/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.io.PrintWriter;
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
import modelos.Imagen;
import modelos.Tipo;

/**
 *
 * @author USUARIO
 */
public class NuevaImagen extends HttpServlet {

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
        
        RequestDispatcher rd = request.getRequestDispatcher("jsp/nueva-imagen.jsp");
        
        List<Tipo> tipos = tipos();
        request.setAttribute("tipos", tipos);
        
        List<Imagen> imagenes = imagenes();
        request.setAttribute("imagenes", imagenes);
        
        rd.forward(request, response);
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
        
        RequestDispatcher rd = request.getRequestDispatcher("jsp/nueva-imagen.jsp");
        List<Tipo> tipos = tipos();
        request.setAttribute("tipos", tipos);
        
        String idimagenesStr = request.getParameter("idimagenes");
        String nombre = request.getParameter("nombre");
        String ruta = request.getParameter("ruta");
        
        int tipo = Integer.parseInt(request.getParameter("tipo"));
        
        if(idimagenesStr != null && !idimagenesStr.equals("")){
            int idimagenes = Integer.parseInt(idimagenesStr);
            actualizarImagen(idimagenes, nombre, ruta, tipo);
        } else {
            guardarImagen(nombre, ruta, tipo);
        }
        
        List<Imagen> imagenes = imagenes();
        request.setAttribute("imagenes", imagenes);
        
        rd.forward(request, response);
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

    private void guardarImagen(String nombre, String ruta, int tipo) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejemplo", "root", "");
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO `ejemplo`.`imagenes` (`nombre`, `ruta`, tipo) VALUES (?, ?, ?)");
            ps.setString(1, nombre);
            ps.setString(2, ruta);
            ps.setInt(3, tipo);
            ps.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NuevaImagen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(NuevaImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    List<Tipo> tipos() {
        List<Tipo> listaTipos = new ArrayList<Tipo>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejemplo", "root", "");
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM tipo");
            ResultSet resultados = ps.executeQuery();
            while(resultados.next()) {
                int id = resultados.getInt("id");
                String nombre = resultados.getString("nombre");
                Tipo t = new Tipo();
                t.id = id;
                t.nombre = nombre;
                listaTipos.add(t);
            }
            conexion.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NuevaImagen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(NuevaImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaTipos;
    }

    List<Imagen> imagenes() {
        List<Imagen> listaImagenes = new ArrayList<Imagen>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejemplo", "root", "");
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM imagenes");
            ResultSet resultados = ps.executeQuery();
            while(resultados.next()) {
                int idimagenes = resultados.getInt("idimagenes");
                String nombre = resultados.getString("nombre");
                String ruta = resultados.getString("ruta");
                Imagen i = new Imagen();
                i.idimagenes = idimagenes;
                i.nombre = nombre;
                i.ruta = ruta;
                listaImagenes.add(i);
            }
            conexion.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Galeria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Galeria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaImagenes;
    }

    private void actualizarImagen(int idimagenes, String nombre, String ruta, int tipo) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejemplo", "root", "");
            PreparedStatement ps = conexion.prepareStatement("UPDATE `ejemplo`.`imagenes` SET `nombre` = ?, `ruta` = ?, `tipo` = ? WHERE `idimagenes` = ?");
            ps.setString(1, nombre);
            ps.setString(2, ruta);
            ps.setInt(3, tipo);
            ps.setInt(4, idimagenes);
            ps.execute();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NuevaImagen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(NuevaImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
