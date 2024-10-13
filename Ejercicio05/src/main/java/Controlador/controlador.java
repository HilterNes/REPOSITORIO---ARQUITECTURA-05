/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.modelo;
import Modelo.persona;
import Vista.vista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HILTER
 */
public class controlador implements ActionListener {

    persona dao = new persona();
    modelo p = new modelo();
    vista vista = new vista();
    DefaultTableModel modelo = new DefaultTableModel();
    int idEditar = -1;

    public controlador(vista v) {
        this.vista = v;
        this.vista.btn_listar.addActionListener(this);
        this.vista.btn_guardar.addActionListener(this);
        this.vista.btn_editar.addActionListener(this);
        this.vista.btn_ok.addActionListener(this);
        this.vista.btn_eliminar.addActionListener(this);
        this.vista.tbl_datos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filaSeleccionada(evt);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btn_listar) {
            listar(vista.tbl_datos);
        }

        if (e.getSource() == vista.btn_guardar) {
            agregar();
            
        }

        if (e.getSource() == vista.btn_editar) {
            editar();
        }

        if (e.getSource() == vista.btn_ok) {
            actualizar();
            listar(vista.tbl_datos);
        }
        
        if (e.getSource() == vista.btn_eliminar) {
            eliminar();
            listar(vista.tbl_datos);
        }
    }

    
    public void listar(JTable tabla) {
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        List<modelo> lista = dao.listar();
        Object[] object = new Object[4];

        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getNombre();
            object[2] = lista.get(i).getCorreo();
            object[3] = lista.get(i).getTelefono();
            modelo.addRow(object);
        }
        vista.tbl_datos.setModel(modelo);
    }

    
    public void agregar() {
        
        String nombre = vista.txt_nombres.getText();
        String correo = vista.txt_correo.getText();
        String telefono = vista.txt_telefono.getText();
        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios");
        } else {
            p.setNombre(nombre);
            p.setCorreo(correo);
            p.setTelefono(telefono);
            boolean resultado = dao.agregar(p);
            if (resultado) {
                JOptionPane.showMessageDialog(vista, "Datos guardados correctamente");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar los datos");
            }
        }
        limpiarCampos();
    }

    public void filaSeleccionada(java.awt.event.MouseEvent evt) {
        int fila = vista.tbl_datos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar una fila");
        } else {
            idEditar = Integer.parseInt(vista.tbl_datos.getValueAt(fila, 0).toString());
            String nombre = vista.tbl_datos.getValueAt(fila, 1).toString();
            String correo = vista.tbl_datos.getValueAt(fila, 2).toString();
            String telefono = vista.tbl_datos.getValueAt(fila, 3).toString();
            vista.txt_nombres.setText(nombre);
            vista.txt_correo.setText(correo);
            vista.txt_telefono.setText(telefono);
        }
    }

    public void editar() {
        if (idEditar == -1) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar una fila para editar");
        } else {
            JOptionPane.showMessageDialog(vista, "Puede modificar los datos y luego hacer clic en 'OK'");
        }
    }

    public void actualizar() {
        if (idEditar == -1) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar una fila para actualizar");
        } else {
            String nombre = vista.txt_nombres.getText();
            String correo = vista.txt_correo.getText();
            String telefono = vista.txt_telefono.getText();
            if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios");
            } else {
                p.setId(idEditar);
                p.setNombre(nombre);
                p.setCorreo(correo);
                p.setTelefono(telefono);
                boolean resultado = dao.actualizar(p);
                if (resultado) {
                    JOptionPane.showMessageDialog(vista, "Datos actualizados correctamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al actualizar los datos");
                }
                idEditar = -1; 
            }
            limpiarCampos();
        }
    }
    
    public void eliminar() {
        if (idEditar == -1) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar una fila para eliminar");
        } else {
            int respuesta = JOptionPane.showConfirmDialog(vista, "¿Está seguro de que desea eliminar este registro?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                boolean resultado = dao.eliminar(idEditar);
                if (resultado) {
                    JOptionPane.showMessageDialog(vista, "Registro eliminado correctamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al eliminar el registro");
                }
                idEditar = -1;
            }
        }
    }
    public void limpiarCampos() {
        vista.txt_nombres.setText("");
        vista.txt_correo.setText("");
        vista.txt_telefono.setText("");
    }
}
