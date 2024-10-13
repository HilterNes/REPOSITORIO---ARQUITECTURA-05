/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HILTER
 */
public class persona {
    conexion conectar = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<modelo> listar() {
        List<modelo> datos = new ArrayList<>();
        String sql = "SELECT * FROM datos";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                modelo p = new modelo();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setCorreo(rs.getString(3));
                p.setTelefono(rs.getString(4));
                datos.add(p);
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
            }
        }
        return datos;
    }

    public boolean agregar(modelo p) {
        String sql = "INSERT INTO datos (Nombres, correo, Telefono) VALUES (?, ?, ?)";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCorreo());
            ps.setString(3, p.getTelefono());
            ps.execute();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
            }
        }
    }

    public boolean actualizar(modelo p) {
        String sql = "UPDATE datos SET Nombres = ?, correo = ?, Telefono = ? WHERE id = ?";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCorreo());
            ps.setString(3, p.getTelefono());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
            }
        }
    }
    
    public boolean eliminar(int id) {
        String sqlDelete = "DELETE FROM datos WHERE id = ?";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sqlDelete);
            ps.setInt(1, id);
            ps.executeUpdate(); // Ejecutar la eliminación
            
            // Paso 2: Obtener todos los registros restantes
            List<modelo> datos = listar();

            // Paso 3: Reajustar IDs
            String sqlUpdate = "UPDATE datos SET id = ? WHERE id = ?";
            for (int i = 0; i < datos.size(); i++) {
                modelo registro = datos.get(i);
                ps = con.prepareStatement(sqlUpdate);
                ps.setInt(1, i + 1); // Reasignar ID (comenzando desde 1)
                ps.setInt(2, registro.getId()); // ID original
                ps.executeUpdate();
            }
            return true; // Eliminación y reajuste exitoso
        } catch (SQLException e) { // Mostrar el error en caso de excepciones
            // Mostrar el error en caso de excepciones
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
            }
        }
    }
}
