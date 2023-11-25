/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package lab1proyectofinal.vistas;

import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lab1proyectofinal.accesoADatos.CuartelData;
import lab1proyectofinal.entidades.*;

/**
 *
 * @author nahue
 */
public class ListadosCuartelData extends javax.swing.JInternalFrame {

    private final CuartelData cuartelData;
    private List<Cuartel> cuarteles;
    private List<Brigada> brigadas;
    private List<Bombero> bomberos;
    private Cuartel cuartel;
    private DefaultTableModel modeloTablaCuarteles = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };
    private DefaultTableModel modeloTablaBrigadas = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };
    private DefaultTableModel modeloTablaBomberos = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };

    public ListadosCuartelData(CuartelData cuartelData) {
        initComponents();
        this.cuartelData = cuartelData;
        armarCabeceras();
    }

    private void armarCabeceras() {
        modeloTablaCuarteles.addColumn("Nombre");
        modeloTablaCuarteles.addColumn("Dirección");
        modeloTablaCuarteles.addColumn("Coordenadas");
        modeloTablaCuarteles.addColumn("Teléfono");
        modeloTablaCuarteles.addColumn("Correo");
        modeloTablaBrigadas.addColumn("Nombre");
        modeloTablaBrigadas.addColumn("Especialidad");
        modeloTablaBrigadas.addColumn("Disponible");
        modeloTablaBomberos.addColumn("DNI");
        modeloTablaBomberos.addColumn("Nombre completo");
        modeloTablaBomberos.addColumn("Grupo sanguíneo");
        modeloTablaBomberos.addColumn("Nro de celular");
        modeloTablaBomberos.addColumn("Brigada");
        jTableTodosLosCuarteles.setModel(modeloTablaCuarteles);
        jTableTodosLosCuarteles.getColumnModel().getColumn(0).setPreferredWidth(80);
        jTableTodosLosCuarteles.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTableTodosLosCuarteles.getColumnModel().getColumn(2).setPreferredWidth(20);
        jTableTodosLosCuarteles.getColumnModel().getColumn(3).setPreferredWidth(10);
        jTableBrigadasDelCuartel.setModel(modeloTablaBrigadas);
        jTableBrigadasDelCuartel.getColumnModel().getColumn(0).setPreferredWidth(120);
        jTableBrigadasDelCuartel.getColumnModel().getColumn(1).setPreferredWidth(280);
        jTableBomberosDelCuartel.setModel(modeloTablaBomberos);
        jTableBomberosDelCuartel.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTableBomberosDelCuartel.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTableBomberosDelCuartel.getColumnModel().getColumn(2).setPreferredWidth(20);
        jTableBomberosDelCuartel.getColumnModel().getColumn(3).setPreferredWidth(20);
    }

    private void cargarDatosTablaCuarteles() {
        cuarteles = cuartelData.listarCuarteles();
        if (cuarteles.isEmpty()) {
            modeloTablaCuarteles.setRowCount(0);
            jLabelMensajeTablaCuarteles.setForeground(Color.RED);
            jLabelMensajeTablaCuarteles.setText("No hay cuarteles registrados");
        } else {
            modeloTablaCuarteles.setRowCount(0);
            for (Cuartel cuartel : cuarteles) {
                modeloTablaCuarteles.addRow(new Object[]{cuartel.getNombreCuartel(), cuartel.getDireccion(), "(" + cuartel.getCoordenadaX() + " ; " + cuartel.getCoordenadaY() + ")", cuartel.getTelefono(), cuartel.getCorreo()});
            }
            jLabelMensajeTablaCuarteles.setForeground(Color.BLACK);
            jLabelMensajeTablaCuarteles.setText("<html>Seleccione un cuartel de la tabla haciendo click en su fila para ver las brigadas y los bomberos vinculados al mismo</html>");
        }
    }

    private void cargarDatosTablaBrigadas(Cuartel cuartel) {
        brigadas = cuartelData.listarBrigadasDelCuartel(cuartel);
        if (brigadas.isEmpty()) {
            modeloTablaBrigadas.setRowCount(0);
            jLabelMensajeTablaBrigadas.setForeground(Color.RED);
            jLabelMensajeTablaBrigadas.setText("No hay brigadas registradas en el cuartel '" + cuartel.getNombreCuartel() + "'");
        } else {
            modeloTablaBrigadas.setRowCount(0);
            for (Brigada brigada : brigadas) {
                modeloTablaBrigadas.addRow(new Object[]{brigada.getNombreBrigada(), brigada.getEspecialidad(), brigada.isDisponible() ? "Disponible" : "No disponible"});
            }
            jLabelMensajeTablaBrigadas.setForeground(Color.BLACK);
            jLabelMensajeTablaBrigadas.setText("");
        }
    }

    private void cargarDatosTablaBomberos(Cuartel cuartel) {
        bomberos = cuartelData.listarBomberosDelCuartel(cuartel);
        if (bomberos.isEmpty()) {
            modeloTablaBomberos.setRowCount(0);
            jLabelMensajeTablaBomberos.setForeground(Color.RED);
            jLabelMensajeTablaBomberos.setText("No hay bomberos registrados en el cuartel '" + cuartel.getNombreCuartel() + "'");
        } else {
            modeloTablaBomberos.setRowCount(0);
            for (Bombero bombero : bomberos) {
                modeloTablaBomberos.addRow(new Object[]{bombero.getDni(), bombero.getNombreCompleto(), bombero.getGrupoSanguineo(), bombero.getCelular(), bombero.getBrigada().getNombreBrigada()});
            }
            jLabelMensajeTablaBomberos.setForeground(Color.BLACK);
            jLabelMensajeTablaBomberos.setText("");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPaneTodosLosCuarteles = new javax.swing.JScrollPane();
        jTableTodosLosCuarteles = new javax.swing.JTable();
        jLabelTodosLosCuarteles = new javax.swing.JLabel();
        jScrollPaneBomberosDelCuartel = new javax.swing.JScrollPane();
        jTableBomberosDelCuartel = new javax.swing.JTable();
        jLabelBomberosDelCuartel = new javax.swing.JLabel();
        jScrollPaneBrigadasDelCuartel = new javax.swing.JScrollPane();
        jTableBrigadasDelCuartel = new javax.swing.JTable();
        jLabelBrigadasDelCuartel = new javax.swing.JLabel();
        jLabelTituloPrincipal = new javax.swing.JLabel();
        jLabelMensajeTablaCuarteles = new javax.swing.JLabel();
        jLabelMensajeTablaBomberos = new javax.swing.JLabel();
        jLabelMensajeTablaBrigadas = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1477, 750));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTableTodosLosCuarteles.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTableTodosLosCuarteles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableTodosLosCuarteles.getTableHeader().setResizingAllowed(false);
        jTableTodosLosCuarteles.getTableHeader().setReorderingAllowed(false);
        jTableTodosLosCuarteles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableTodosLosCuartelesMouseReleased(evt);
            }
        });
        jScrollPaneTodosLosCuarteles.setViewportView(jTableTodosLosCuarteles);

        getContentPane().add(jScrollPaneTodosLosCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, 810, 170));

        jLabelTodosLosCuarteles.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabelTodosLosCuarteles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTodosLosCuarteles.setText("Todos los cuarteles");
        getContentPane().add(jLabelTodosLosCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 810, -1));

        jTableBomberosDelCuartel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableBomberosDelCuartel.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jScrollPaneBomberosDelCuartel.setViewportView(jTableBomberosDelCuartel);

        getContentPane().add(jScrollPaneBomberosDelCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 450, 780, 220));

        jLabelBomberosDelCuartel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabelBomberosDelCuartel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelBomberosDelCuartel.setText("Bomberos del cuartel seleccionado");
        getContentPane().add(jLabelBomberosDelCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 370, 780, -1));

        jTableBrigadasDelCuartel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableBrigadasDelCuartel.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jScrollPaneBrigadasDelCuartel.setViewportView(jTableBrigadasDelCuartel);

        getContentPane().add(jScrollPaneBrigadasDelCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, 540, 220));

        jLabelBrigadasDelCuartel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabelBrigadasDelCuartel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelBrigadasDelCuartel.setText("Brigadas del cuartel selecionado");
        getContentPane().add(jLabelBrigadasDelCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, 540, -1));

        jLabelTituloPrincipal.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelTituloPrincipal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTituloPrincipal.setText("Listados relacionados con cuarteles");
        getContentPane().add(jLabelTituloPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 1400, -1));

        jLabelMensajeTablaCuarteles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(jLabelMensajeTablaCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 120, 790, 32));

        jLabelMensajeTablaBomberos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(jLabelMensajeTablaBomberos, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 410, 760, 32));

        jLabelMensajeTablaBrigadas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(jLabelMensajeTablaBrigadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 410, 520, 32));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        cargarDatosTablaCuarteles();
    }//GEN-LAST:event_formInternalFrameActivated

    private void jTableTodosLosCuartelesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableTodosLosCuartelesMouseReleased
        int filaSelec = jTableTodosLosCuarteles.getSelectedRow();
        String nombreCuartel = (String) jTableTodosLosCuarteles.getValueAt(filaSelec, 0);
        cuartel = cuartelData.buscarCuartelPorNombre(nombreCuartel);
        cargarDatosTablaBrigadas(cuartel);
        cargarDatosTablaBomberos(cuartel);
    }//GEN-LAST:event_jTableTodosLosCuartelesMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelBomberosDelCuartel;
    private javax.swing.JLabel jLabelBrigadasDelCuartel;
    private javax.swing.JLabel jLabelMensajeTablaBomberos;
    private javax.swing.JLabel jLabelMensajeTablaBrigadas;
    private javax.swing.JLabel jLabelMensajeTablaCuarteles;
    private javax.swing.JLabel jLabelTituloPrincipal;
    private javax.swing.JLabel jLabelTodosLosCuarteles;
    private javax.swing.JScrollPane jScrollPaneBomberosDelCuartel;
    private javax.swing.JScrollPane jScrollPaneBrigadasDelCuartel;
    private javax.swing.JScrollPane jScrollPaneTodosLosCuarteles;
    private javax.swing.JTable jTableBomberosDelCuartel;
    private javax.swing.JTable jTableBrigadasDelCuartel;
    private javax.swing.JTable jTableTodosLosCuarteles;
    // End of variables declaration//GEN-END:variables
}
