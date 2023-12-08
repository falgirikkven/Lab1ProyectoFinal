/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package lab1proyectofinal.vistas;

import java.awt.Color;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import lab1proyectofinal.accesoADatos.CuartelData;
import lab1proyectofinal.accesoADatos.Utils;
import lab1proyectofinal.entidades.*;

/**
 *
 * @author nahue
 */
public class ListadosPorCuartel extends javax.swing.JInternalFrame {

    private final CuartelData cuartelData;
    private List<Cuartel> listaCuartel;
    private List<Brigada> listaBrigada;
    private List<Bombero> listaBombero;
    private Cuartel cuartel;
    private int filaSelec;
    private DefaultTableModel modeloJTaCuarteles = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };
    private DefaultTableModel modeloJTaBrigadas = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };
    private DefaultTableModel modeloJTaBomberos = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };

    public ListadosPorCuartel(CuartelData cuartelData) {
        initComponents();
        this.cuartelData = cuartelData;
        configurarTablas();
    }

    private void configurarTablas() {
        jTaTodosLosCuarteles.getTableHeader().setFont(Utils.fuenteNegrita);
        jTaBrigadasDelCuartel.getTableHeader().setFont(Utils.fuenteNegrita);
        jTaBomberosDelCuartel.getTableHeader().setFont(Utils.fuenteNegrita);
        modeloJTaCuarteles.addColumn("Nombre");
        modeloJTaCuarteles.addColumn("Dirección");
        modeloJTaCuarteles.addColumn("Coordenadas");
        modeloJTaCuarteles.addColumn("Teléfono");
        modeloJTaCuarteles.addColumn("Correo");
        modeloJTaBrigadas.addColumn("Nombre");
        modeloJTaBrigadas.addColumn("Especialidad");
        modeloJTaBrigadas.addColumn("Disponible");
        modeloJTaBomberos.addColumn("DNI");
        modeloJTaBomberos.addColumn("Nombre completo");
        modeloJTaBomberos.addColumn("Grupo sanguíneo");
        modeloJTaBomberos.addColumn("Nro de celular");
        modeloJTaBomberos.addColumn("Brigada");
        jTaTodosLosCuarteles.setModel(modeloJTaCuarteles);

        // no conozco en su totalidad cómo funciona la asignación del ancho a las columnas pero el
        // resultado es aceptable
        jTaTodosLosCuarteles.getColumnModel().getColumn(0).setPreferredWidth(80);
        jTaTodosLosCuarteles.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTaTodosLosCuarteles.getColumnModel().getColumn(2).setPreferredWidth(20);
        jTaTodosLosCuarteles.getColumnModel().getColumn(3).setPreferredWidth(5);
        jTaBrigadasDelCuartel.setModel(modeloJTaBrigadas);
        jTaBrigadasDelCuartel.getColumnModel().getColumn(0).setPreferredWidth(120);
        jTaBrigadasDelCuartel.getColumnModel().getColumn(1).setPreferredWidth(280);
        jTaBomberosDelCuartel.setModel(modeloJTaBomberos);
        jTaBomberosDelCuartel.getColumnModel().getColumn(0).setPreferredWidth(5);
        jTaBomberosDelCuartel.getColumnModel().getColumn(1).setPreferredWidth(165);
        jTaBomberosDelCuartel.getColumnModel().getColumn(2).setPreferredWidth(20);
        jTaBomberosDelCuartel.getColumnModel().getColumn(3).setPreferredWidth(20);

        jTaTodosLosCuarteles.setRowHeight(20);
        jTaBrigadasDelCuartel.setRowHeight(20);
        jTaBomberosDelCuartel.setRowHeight(20);
    }

    private void cargarDatosJTabCuarteles() {
        listaCuartel = cuartelData.listarCuarteles();
        modeloJTaCuarteles.setRowCount(0);
        if (listaCuartel.isEmpty()) {
            jLabMensajeTablaCuarteles.setForeground(Color.BLACK);
            jLabMensajeTablaCuarteles.setText("No hay cuarteles registrados.");
        } else {
            for (Cuartel cuartel : listaCuartel) {
                modeloJTaCuarteles.addRow(new Object[]{cuartel.getNombreCuartel(),
                    cuartel.getDireccion(), "(" + cuartel.getCoordenadaX() + " ; "
                    + cuartel.getCoordenadaY() + ")", cuartel.getTelefono(),
                    cuartel.getCorreo()});
            }
            jLabMensajeTablaCuarteles.setForeground(Color.BLACK);
            jLabMensajeTablaCuarteles.setText("<html>Seleccione un cuartel de la tabla haciendo "
                    + "click en su fila para ver las brigadas y los bomberos vinculados al "
                    + "mismo.</html>");
        }
    }

    private void cargarDatosJTabBrigadas(Cuartel cuartel) {
        listaBrigada = cuartelData.listarBrigadasDelCuartel(cuartel);
        modeloJTaBrigadas.setRowCount(0);
        if (listaBrigada.isEmpty()) {
            jLabMensajeTablaBrigadas.setForeground(Color.BLACK);
            jLabMensajeTablaBrigadas.setText("No hay brigadas registradas en el cuartel \""
                    + cuartel.getNombreCuartel() + "\".");
        } else {
            for (Brigada brigada : listaBrigada) {
                modeloJTaBrigadas.addRow(new Object[]{brigada.getNombreBrigada(),
                    brigada.getEspecialidad(), brigada.isDisponible() ? "Disponible" : "No "
                    + "disponible"});
            }
            jLabMensajeTablaBrigadas.setText("");
        }
    }

    private void cargarDatosJTabBomberos(Cuartel cuartel) {
        listaBombero = cuartelData.listarBomberosDelCuartel(cuartel);
        modeloJTaBomberos.setRowCount(0);
        if (listaBombero.isEmpty()) {
            jLabMensajeTablaBomberos.setForeground(Color.BLACK);
            jLabMensajeTablaBomberos.setText("No hay bomberos registrados en el cuartel \""
                    + cuartel.getNombreCuartel() + "\".");
        } else {
            for (Bombero bombero : listaBombero) {
                modeloJTaBomberos.addRow(new Object[]{bombero.getDni(),
                    bombero.getNombreCompleto(), bombero.getGrupoSanguineo(), bombero.getCelular(),
                    bombero.getBrigada().getNombreBrigada()});
            }
            jLabMensajeTablaBomberos.setText("");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScPTodosLosCuarteles = new javax.swing.JScrollPane();
        jTaTodosLosCuarteles = new javax.swing.JTable();
        jLabTodosLosCuarteles = new javax.swing.JLabel();
        jScPBomberosDelCuartel = new javax.swing.JScrollPane();
        jTaBomberosDelCuartel = new javax.swing.JTable();
        jLabBomberosDelCuartel = new javax.swing.JLabel();
        jScPBrigadasDelCuartel = new javax.swing.JScrollPane();
        jTaBrigadasDelCuartel = new javax.swing.JTable();
        jLabBrigadasDelCuartel = new javax.swing.JLabel();
        jLabListadosPorCuartel = new javax.swing.JLabel();
        jLabMensajeTablaCuarteles = new javax.swing.JLabel();
        jLabMensajeTablaBomberos = new javax.swing.JLabel();
        jLabMensajeTablaBrigadas = new javax.swing.JLabel();
        jBSalir = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1140, 765));
        setRequestFocusEnabled(false);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameDeactivated(evt);
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTaTodosLosCuarteles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTaTodosLosCuarteles.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTaTodosLosCuarteles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTaTodosLosCuarteles.getTableHeader().setResizingAllowed(false);
        jTaTodosLosCuarteles.getTableHeader().setReorderingAllowed(false);
        jTaTodosLosCuarteles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTaTodosLosCuartelesMouseReleased(evt);
            }
        });
        jScPTodosLosCuarteles.setViewportView(jTaTodosLosCuarteles);

        getContentPane().add(jScPTodosLosCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 940, 130));

        jLabTodosLosCuarteles.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabTodosLosCuarteles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabTodosLosCuarteles.setText("Todos los cuarteles");
        getContentPane().add(jLabTodosLosCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 940, -1));

        jTaBomberosDelCuartel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTaBomberosDelCuartel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTaBomberosDelCuartel.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTaBomberosDelCuartel.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTaBomberosDelCuartel.getTableHeader().setResizingAllowed(false);
        jTaBomberosDelCuartel.getTableHeader().setReorderingAllowed(false);
        jScPBomberosDelCuartel.setViewportView(jTaBomberosDelCuartel);

        getContentPane().add(jScPBomberosDelCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 580, 940, 130));

        jLabBomberosDelCuartel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabBomberosDelCuartel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabBomberosDelCuartel.setText("Bomberos del cuartel seleccionado");
        getContentPane().add(jLabBomberosDelCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 520, 940, -1));

        jTaBrigadasDelCuartel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTaBrigadasDelCuartel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTaBrigadasDelCuartel.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTaBrigadasDelCuartel.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTaBrigadasDelCuartel.getTableHeader().setResizingAllowed(false);
        jTaBrigadasDelCuartel.getTableHeader().setReorderingAllowed(false);
        jScPBrigadasDelCuartel.setViewportView(jTaBrigadasDelCuartel);

        getContentPane().add(jScPBrigadasDelCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 360, 660, 130));

        jLabBrigadasDelCuartel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabBrigadasDelCuartel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabBrigadasDelCuartel.setText("Brigadas del cuartel selecionado");
        getContentPane().add(jLabBrigadasDelCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 660, -1));

        jLabListadosPorCuartel.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabListadosPorCuartel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabListadosPorCuartel.setText("Listados por cuartel");
        getContentPane().add(jLabListadosPorCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 940, -1));

        jLabMensajeTablaCuarteles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(jLabMensajeTablaCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 115, 920, 20));

        jLabMensajeTablaBomberos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(jLabMensajeTablaBomberos, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 555, 920, 20));

        jLabMensajeTablaBrigadas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(jLabMensajeTablaBrigadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 335, 640, 20));

        jBSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBSalir.setText("Salir");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });
        getContentPane().add(jBSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 670, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        cargarDatosJTabCuarteles();
    }//GEN-LAST:event_formInternalFrameActivated

    private void jTaTodosLosCuartelesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTaTodosLosCuartelesMouseReleased
        filaSelec = jTaTodosLosCuarteles.getSelectedRow();
        cuartel = cuartelData.buscarCuartelPorNombre(
                (String) jTaTodosLosCuarteles.getValueAt(filaSelec, 0));
        cargarDatosJTabBrigadas(cuartel);
        cargarDatosJTabBomberos(cuartel);
    }//GEN-LAST:event_jTaTodosLosCuartelesMouseReleased

    private void jBSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalirActionPerformed
        this.hide();
        jTaTodosLosCuarteles.removeRowSelectionInterval(0,
                jTaTodosLosCuarteles.getRowCount() - 1);
        modeloJTaBrigadas.setRowCount(0);
        jLabMensajeTablaBrigadas.setText("");
        modeloJTaBomberos.setRowCount(0);
        jLabMensajeTablaBomberos.setText("");
    }//GEN-LAST:event_jBSalirActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        if (jTaTodosLosCuarteles.getRowCount() > 0) {
            jTaTodosLosCuarteles.removeRowSelectionInterval(0,
                    jTaTodosLosCuarteles.getRowCount() - 1);
        }
        modeloJTaBrigadas.setRowCount(0);
        jLabMensajeTablaBrigadas.setText("");
        modeloJTaBomberos.setRowCount(0);
        jLabMensajeTablaBomberos.setText("");
    }//GEN-LAST:event_formInternalFrameDeactivated


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBSalir;
    private javax.swing.JLabel jLabBomberosDelCuartel;
    private javax.swing.JLabel jLabBrigadasDelCuartel;
    private javax.swing.JLabel jLabListadosPorCuartel;
    private javax.swing.JLabel jLabMensajeTablaBomberos;
    private javax.swing.JLabel jLabMensajeTablaBrigadas;
    private javax.swing.JLabel jLabMensajeTablaCuarteles;
    private javax.swing.JLabel jLabTodosLosCuarteles;
    private javax.swing.JScrollPane jScPBomberosDelCuartel;
    private javax.swing.JScrollPane jScPBrigadasDelCuartel;
    private javax.swing.JScrollPane jScPTodosLosCuarteles;
    private javax.swing.JTable jTaBomberosDelCuartel;
    private javax.swing.JTable jTaBrigadasDelCuartel;
    private javax.swing.JTable jTaTodosLosCuarteles;
    // End of variables declaration//GEN-END:variables
}
