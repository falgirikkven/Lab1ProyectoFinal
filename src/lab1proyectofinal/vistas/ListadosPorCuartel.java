/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package lab1proyectofinal.vistas;

import java.awt.Color;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
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
    private DefaultTableModel defTabModBomberos = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };
    private DefaultTableModel defTabModBrigadas = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };
    private DefaultTableModel defTabModCuarteles = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };
    private TableRowSorter<TableModel> sorterBrigadas;
    private TableRowSorter<TableModel> sorterBomberos;

    public ListadosPorCuartel(CuartelData cuartelData) {
        initComponents();
        this.cuartelData = cuartelData;
    }

    private void configJTaCuarteles() {
        jTaCuarteles.getTableHeader().setFont(Utils.fuenteNegrita);

        defTabModCuarteles.addColumn("Nombre");
        defTabModCuarteles.addColumn("Dirección");
        defTabModCuarteles.addColumn("Coordenadas");
        defTabModCuarteles.addColumn("Teléfono");
        defTabModCuarteles.addColumn("Correo");
        jTaCuarteles.setModel(defTabModCuarteles);

        jTaCuarteles.getColumnModel().getColumn(0).setPreferredWidth(300);
        jTaCuarteles.getColumnModel().getColumn(1).setPreferredWidth(480);
        jTaCuarteles.getColumnModel().getColumn(2).setPreferredWidth(200);
        jTaCuarteles.getColumnModel().getColumn(3).setPreferredWidth(180);
        jTaCuarteles.getColumnModel().getColumn(4).setPreferredWidth(480);

        TableRowSorter<TableModel> sorterCuarteles = new TableRowSorter<>(defTabModCuarteles);
        sorterCuarteles.toggleSortOrder(0);
        jTaCuarteles.setRowSorter(sorterCuarteles);
    }

    private void configColModJTaEntidadesInternasBrigadas() {
        jTaEntidadesInternas.getColumnModel().getColumn(0).setPreferredWidth(300);
        jTaEntidadesInternas.getColumnModel().getColumn(1).setPreferredWidth(480);
        jTaEntidadesInternas.getColumnModel().getColumn(2).setPreferredWidth(100);
    }

    private void configColModJTaEntidadesInternasBomberos() {
        jTaEntidadesInternas.getColumnModel().getColumn(0).setPreferredWidth(100);
        jTaEntidadesInternas.getColumnModel().getColumn(1).setPreferredWidth(390);
        jTaEntidadesInternas.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTaEntidadesInternas.getColumnModel().getColumn(3).setPreferredWidth(180);
        jTaEntidadesInternas.getColumnModel().getColumn(4).setPreferredWidth(180);
        jTaEntidadesInternas.getColumnModel().getColumn(5).setPreferredWidth(300);
    }

    private void configJTaEntidadesInternas() {
        jTaEntidadesInternas.getTableHeader().setFont(Utils.fuenteNegrita);

        defTabModBrigadas.addColumn("Nombre");
        defTabModBrigadas.addColumn("Especialidad");
        defTabModBrigadas.addColumn("En servicio");
        jTaEntidadesInternas.setModel(defTabModBrigadas);

        configColModJTaEntidadesInternasBrigadas();

        sorterBrigadas = new TableRowSorter<>(defTabModBrigadas);
        sorterBrigadas.toggleSortOrder(0);

        defTabModBomberos.addColumn("DNI");
        defTabModBomberos.addColumn("Nombre completo");
        defTabModBomberos.addColumn("Grupo Sanguíneo");
        defTabModBomberos.addColumn("Fecha de nacimiento");
        defTabModBomberos.addColumn("Celular");
        defTabModBomberos.addColumn("Brigada");
        jTaEntidadesInternas.setModel(defTabModBomberos);

        configColModJTaEntidadesInternasBomberos();

        sorterBomberos = new TableRowSorter<>(defTabModBomberos);
        sorterBomberos.toggleSortOrder(0);
    }

    private void cargarDatosJTaCuarteles() {
        listaCuartel = cuartelData.listarCuarteles();
        defTabModCuarteles.setRowCount(0);
        if (listaCuartel.isEmpty()) {
            jLabMensajeJTaCuarteles.setForeground(Color.BLACK);
            jLabMensajeJTaCuarteles.setText("No hay cuarteles registrados.");
        } else {
            for (Cuartel c : listaCuartel) {
                defTabModCuarteles.addRow(new Object[]{c.getNombreCuartel(),
                    c.getDireccion(), "(" + c.getCoordenadaX() + " ; "
                    + c.getCoordenadaY() + ")", c.getTelefono(),
                    c.getCorreo()});
            }
            jLabMensajeJTaCuarteles.setForeground(Color.BLACK);
            jLabMensajeJTaCuarteles.setText("<html>Seleccione un cuartel de la tabla haciendo "
                    + "click en su fila para poder ver las brigadas y los bomberos vinculados al "
                    + "mismo.</html>");
        }
    }

    private void cargarDatosBrigadasJTaEntidadesInternas(Cuartel cuartel) {
        listaBrigada = cuartelData.listarBrigadasDelCuartel(cuartel);
        defTabModBrigadas.setRowCount(0);
        if (listaBrigada.isEmpty()) {
            jLabMensajeJTaEntidadesInternas.setForeground(Color.BLACK);
            jLabMensajeJTaEntidadesInternas.setText("No hay brigadas registradas en el cuartel \""
                    + cuartel.getNombreCuartel() + "\".");
        } else {
            for (Brigada brigada : listaBrigada) {
                defTabModBrigadas.addRow(new Object[]{brigada.getNombreBrigada(),
                    brigada.getEspecialidad(), brigada.isEnServicio() ? "Si" : "No"});
            }
            jLabMensajeJTaEntidadesInternas.setText("");
        }
    }

    private void cargarDatosBomberosJTaEntidadesInternas(Cuartel cuartel) {
        listaBombero = cuartelData.listarBomberosDelCuartel(cuartel);
        defTabModBomberos.setRowCount(0);
        if (listaBombero.isEmpty()) {
            jLabMensajeJTaEntidadesInternas.setForeground(Color.BLACK);
            jLabMensajeJTaEntidadesInternas.setText("No hay bomberos registrados en el cuartel \""
                    + cuartel.getNombreCuartel() + "\".");
        } else {
            for (Bombero bombero : listaBombero) {
                defTabModBomberos.addRow(new Object[]{bombero.getDni(),
                    bombero.getNombreCompleto(), bombero.getGrupoSanguineo(), bombero.getCelular(),
                    bombero.getBrigada().getNombreBrigada()});
            }
            jLabMensajeJTaEntidadesInternas.setText("");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScPCuarts = new javax.swing.JScrollPane();
        jTaCuarteles = new javax.swing.JTable();
        jLabCuarteles = new javax.swing.JLabel();
        jScPBrigsBoms = new javax.swing.JScrollPane();
        jTaEntidadesInternas = new javax.swing.JTable();
        jLabEntidadesInternas = new javax.swing.JLabel();
        jLabListsCuarts = new javax.swing.JLabel();
        jLabMensajeJTaCuarteles = new javax.swing.JLabel();
        jLabMensajeJTaEntidadesInternas = new javax.swing.JLabel();
        jBSalir = new javax.swing.JButton();
        jBVerBrigadas = new javax.swing.JButton();
        jBVerBomberos = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1530, 765));
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

        jTaCuarteles.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jTaCuarteles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTaCuarteles.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTaCuarteles.setRowHeight(23);
        jTaCuarteles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTaCuarteles.getTableHeader().setResizingAllowed(false);
        jTaCuarteles.getTableHeader().setReorderingAllowed(false);
        jTaCuarteles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTaCuartelesMouseClicked(evt);
            }
        });
        jScPCuarts.setViewportView(jTaCuarteles);
        configJTaCuarteles();

        getContentPane().add(jScPCuarts, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 1480, 220));

        jLabCuarteles.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 18)); // NOI18N
        jLabCuarteles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabCuarteles.setText("Todos los cuarteles");
        getContentPane().add(jLabCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 1480, -1));

        jScPBrigsBoms.setPreferredSize(new java.awt.Dimension(1360, 400));

        jTaEntidadesInternas.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jTaEntidadesInternas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTaEntidadesInternas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTaEntidadesInternas.setRowHeight(23);
        jTaEntidadesInternas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTaEntidadesInternas.getTableHeader().setResizingAllowed(false);
        jTaEntidadesInternas.getTableHeader().setReorderingAllowed(false);
        jScPBrigsBoms.setViewportView(jTaEntidadesInternas);
        configJTaEntidadesInternas();

        getContentPane().add(jScPBrigsBoms, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 1360, 200));

        jLabEntidadesInternas.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 18)); // NOI18N
        jLabEntidadesInternas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabEntidadesInternas.setText("Brigadas/bomberos del cuartel seleccionado");
        getContentPane().add(jLabEntidadesInternas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 1360, 30));

        jLabListsCuarts.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 24)); // NOI18N
        jLabListsCuarts.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabListsCuarts.setText("Listados por cuartel");
        getContentPane().add(jLabListsCuarts, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 1480, -1));

        jLabMensajeJTaCuarteles.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        getContentPane().add(jLabMensajeJTaCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 115, 1460, 20));

        jLabMensajeJTaEntidadesInternas.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        getContentPane().add(jLabMensajeJTaEntidadesInternas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, 1340, 20));

        jBSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBSalir.setText("Salir");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });
        getContentPane().add(jBSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1420, 670, -1, -1));

        jBVerBrigadas.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jBVerBrigadas.setText("Ver Brigadas");
        jBVerBrigadas.setEnabled(false);
        jBVerBrigadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVerBrigadasActionPerformed(evt);
            }
        });
        getContentPane().add(jBVerBrigadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 380, -1, -1));

        jBVerBomberos.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jBVerBomberos.setText("Ver Bomberos");
        jBVerBomberos.setEnabled(false);
        jBVerBomberos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVerBomberosActionPerformed(evt);
            }
        });
        getContentPane().add(jBVerBomberos, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 380, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        cargarDatosJTaCuarteles();
    }//GEN-LAST:event_formInternalFrameActivated

    private void jBSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalirActionPerformed
        this.hide();

        if (jTaCuarteles.getRowCount() > 0) {
            jTaCuarteles.removeRowSelectionInterval(0, jTaCuarteles.getRowCount() - 1);
        }
        defTabModBrigadas.setRowCount(0);
        defTabModBomberos.setRowCount(0);

        jLabMensajeJTaEntidadesInternas.setText("");

        jBVerBrigadas.setEnabled(false);
        jBVerBomberos.setEnabled(false);
    }//GEN-LAST:event_jBSalirActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        if (jTaCuarteles.getRowCount() > 0) {
            jTaCuarteles.removeRowSelectionInterval(0, jTaCuarteles.getRowCount() - 1);
        }
        defTabModBrigadas.setRowCount(0);
        defTabModBomberos.setRowCount(0);

        jLabMensajeJTaEntidadesInternas.setText("");

        jBVerBrigadas.setEnabled(false);
        jBVerBomberos.setEnabled(false);
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void jBVerBrigadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVerBrigadasActionPerformed
        jTaEntidadesInternas.setModel(defTabModBrigadas);
        configColModJTaEntidadesInternasBrigadas();
        jTaEntidadesInternas.setRowSorter(sorterBrigadas);
        cargarDatosBrigadasJTaEntidadesInternas(cuartel);
    }//GEN-LAST:event_jBVerBrigadasActionPerformed

    private void jBVerBomberosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVerBomberosActionPerformed
        jTaEntidadesInternas.setModel(defTabModBomberos);
        configColModJTaEntidadesInternasBomberos();
        jTaEntidadesInternas.setRowSorter(sorterBomberos);
        cargarDatosBomberosJTaEntidadesInternas(cuartel);
    }//GEN-LAST:event_jBVerBomberosActionPerformed

    private void jTaCuartelesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTaCuartelesMouseClicked
        filaSelec = jTaCuarteles.getSelectedRow();
        cuartel = cuartelData.buscarCuartelPorNombre(
                (String) jTaCuarteles.getValueAt(filaSelec, 0));

        jBVerBrigadas.setEnabled(true);
        jBVerBomberos.setEnabled(true);
    }//GEN-LAST:event_jTaCuartelesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBSalir;
    private javax.swing.JButton jBVerBomberos;
    private javax.swing.JButton jBVerBrigadas;
    private javax.swing.JLabel jLabCuarteles;
    private javax.swing.JLabel jLabEntidadesInternas;
    private javax.swing.JLabel jLabListsCuarts;
    private javax.swing.JLabel jLabMensajeJTaCuarteles;
    private javax.swing.JLabel jLabMensajeJTaEntidadesInternas;
    private javax.swing.JScrollPane jScPBrigsBoms;
    private javax.swing.JScrollPane jScPCuarts;
    private javax.swing.JTable jTaCuarteles;
    private javax.swing.JTable jTaEntidadesInternas;
    // End of variables declaration//GEN-END:variables
}
