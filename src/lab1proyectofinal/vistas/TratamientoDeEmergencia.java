/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package lab1proyectofinal.vistas;

import java.awt.Color;
import java.beans.PropertyVetoException;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import lab1proyectofinal.accesoADatos.BrigadaData;
import lab1proyectofinal.accesoADatos.EmergenciaData;
import lab1proyectofinal.entidades.*;
import lab1proyectofinal.accesoADatos.Utils;

/**
 *
 * @author nahue
 */
public class TratamientoDeEmergencia extends javax.swing.JInternalFrame {

    private final BrigadaData brigadaData = new BrigadaData();
    private final EmergenciaData emergenciaData = new EmergenciaData();
    private List<Brigada> listaBrigada;
    private Brigada brigadaSeleccionada;
    private Emergencia emergenciaATratar;
    private DefaultTableModel modeloJTaBrigadasConEspec = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };
    private DefaultTableModel modeloJTaBrigadasSinEspec = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };
    private JLabel jLabAux = Utils.jLabConfigurado();

    public TratamientoDeEmergencia() {
        initComponents();
        this.emergenciaATratar = emergenciaATratar;
        configurarTabla(jTaBrigadasConEspec, modeloJTaBrigadasConEspec);
        configurarTabla(jTaBrigadasSinEspec, modeloJTaBrigadasSinEspec);
    }

    public Brigada getBrigadaSeleccionada() {
        return brigadaSeleccionada;
    }

    public void setEmergenciaATratar(Emergencia emergenciaATratar) {
        this.emergenciaATratar = emergenciaATratar;
    }

    private void configurarTabla(JTable tabla, DefaultTableModel dtm) {
        tabla.getTableHeader().setFont(Utils.fuenteNegrita);
        dtm.addColumn("Nombre");
        dtm.addColumn("Especialidad");
        dtm.addColumn("Distancia hasta la emergencia");
        tabla.setModel(dtm);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla.setRowHeight(20);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(dtm);
        sorter.toggleSortOrder(2);
        tabla.setRowSorter(sorter);
    }

    private void cargarDatosJTaBrigadasConEspec() {
        listaBrigada = emergenciaData.listarBrigadasAsignablesConLaEspecialidad(emergenciaATratar);
        modeloJTaBrigadasConEspec.setRowCount(0);
        if (listaBrigada.isEmpty()) {
            jLabMensajeJTaBrigadasConEspec.setForeground(Color.BLACK);
            jLabMensajeJTaBrigadasConEspec.setText("No hay ninguna brigada en condiciones de "
                    + "tratar la emergencia y que, a su vez, esté especializada para la misma.");
        } else {
            for (Brigada bri : listaBrigada) {
                double x1, x2, y1, y2;
                x1 = bri.getCuartel().getCoordenadaX();
                y1 = bri.getCuartel().getCoordenadaY();
                x2 = emergenciaATratar.getCoordenadaX();
                y2 = emergenciaATratar.getCoordenadaY();
                modeloJTaBrigadasConEspec.addRow(new Object[]{bri.getNombreBrigada(),
                    bri.getEspecialidad(), Utils.calcularDistancia(x1, y1, x2, y2) + " kms"});
            }
            jLabMensajeJTaBrigadasConEspec.setText("<html>Seleccione una brigada de la tabla "
                    + "haciendo click en su fila para asignarla a la emergencia. También puede "
                    + "hacer click en las columnas de la cabecera para ordenar las filas</html>");
        }
    }

    private void cargarDatosJTaBrigadasSinEspec() {
        listaBrigada = emergenciaData.listarBrigadasAsignablesSinLaEspecialidad(emergenciaATratar);
        modeloJTaBrigadasSinEspec.setRowCount(0);
        if (listaBrigada.isEmpty()) {
            jLabMensajeJTaBrigadasSinEspec.setForeground(Color.BLACK);
            jLabMensajeJTaBrigadasSinEspec.setText("No hay ninguna brigada en condiciones de "
                    + "tratar la emergencia con una especialización distinta de la del tipo de la"
                    + "emergencia.");
        } else {
            for (Brigada bri : listaBrigada) {
                double x1, x2, y1, y2;
                x1 = bri.getCuartel().getCoordenadaX();
                y1 = bri.getCuartel().getCoordenadaY();
                x2 = emergenciaATratar.getCoordenadaX();
                y2 = emergenciaATratar.getCoordenadaY();
                modeloJTaBrigadasSinEspec.addRow(new Object[]{bri.getNombreBrigada(),
                    bri.getEspecialidad(), Utils.calcularDistancia(x1, y1, x2, y2) + " kms"});
            }
            jLabMensajeJTaBrigadasSinEspec.setText("<html>Seleccione una brigada de la tabla haciendo "
                    + "click en su fila para ver los bomberos vinculados a la misma. También puede "
                    + "hacer click en las columnas de la cabecera para ordenar las filas</html>");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScPBomberosDeBrigada = new javax.swing.JScrollPane();
        jTaBrigadasSinEspec = new javax.swing.JTable();
        jLabBrigadasSinEspec = new javax.swing.JLabel();
        jScPBrigadas = new javax.swing.JScrollPane();
        jTaBrigadasConEspec = new javax.swing.JTable();
        jLabBrigadasConvenientesConEspec = new javax.swing.JLabel();
        jLabAsignacionDeBrigada = new javax.swing.JLabel();
        jLabMensajeJTaBrigadasSinEspec = new javax.swing.JLabel();
        jLabMensajeJTaBrigadasConEspec = new javax.swing.JLabel();
        jBSalir = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1200, 750));
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

        jTaBrigadasSinEspec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTaBrigadasSinEspec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTaBrigadasSinEspec.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTaBrigadasSinEspec.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTaBrigadasSinEspec.getTableHeader().setResizingAllowed(false);
        jTaBrigadasSinEspec.getTableHeader().setReorderingAllowed(false);
        jTaBrigadasSinEspec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTaBrigadasSinEspecMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTaBrigadasSinEspecMouseExited(evt);
            }
        });
        jScPBomberosDeBrigada.setViewportView(jTaBrigadasSinEspec);

        getContentPane().add(jScPBomberosDeBrigada, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 490, 990, 210));

        jLabBrigadasSinEspec.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabBrigadasSinEspec.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabBrigadasSinEspec.setText("Brigadas más cercanas a la emergencia pero con especialidad distinta a la de la emergencia");
        getContentPane().add(jLabBrigadasSinEspec, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, 990, -1));

        jTaBrigadasConEspec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTaBrigadasConEspec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTaBrigadasConEspec.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTaBrigadasConEspec.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTaBrigadasConEspec.getTableHeader().setResizingAllowed(false);
        jTaBrigadasConEspec.getTableHeader().setReorderingAllowed(false);
        jTaBrigadasConEspec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTaBrigadasConEspecMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTaBrigadasConEspecMouseExited(evt);
            }
        });
        jScPBrigadas.setViewportView(jTaBrigadasConEspec);

        getContentPane().add(jScPBrigadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 990, 210));

        jLabBrigadasConvenientesConEspec.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabBrigadasConvenientesConEspec.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabBrigadasConvenientesConEspec.setText("Brigadas más cercanas a la emergencia y especializadas en el tipo de la emergencia");
        getContentPane().add(jLabBrigadasConvenientesConEspec, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 990, -1));

        jLabAsignacionDeBrigada.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabAsignacionDeBrigada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabAsignacionDeBrigada.setText("Asignación de brigada");
        getContentPane().add(jLabAsignacionDeBrigada, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 990, -1));

        jLabMensajeJTaBrigadasSinEspec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(jLabMensajeJTaBrigadasSinEspec, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, 790, 43));

        jLabMensajeJTaBrigadasConEspec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(jLabMensajeJTaBrigadasConEspec, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 780, 46));

        jBSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBSalir.setText("Salir");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });
        getContentPane().add(jBSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 670, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        cargarDatosJTaBrigadasConEspec();
        cargarDatosJTaBrigadasSinEspec();
    }//GEN-LAST:event_formInternalFrameActivated

    private void jBSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalirActionPerformed
        this.hide();       
        
        // nota: ver por qué este fragmento es necesario
        try {
            setSelected(false);
        } catch (PropertyVetoException e) {
            System.out.println("[Main Frame Error]");
            e.printStackTrace();
        }
        
        if (jTaBrigadasConEspec.getRowCount() > 0) {
            jTaBrigadasConEspec.removeRowSelectionInterval(0,
                    jTaBrigadasConEspec.getRowCount() - 1);
        }
        if (jTaBrigadasSinEspec.getRowCount() > 0) {
            jTaBrigadasSinEspec.removeRowSelectionInterval(0,
                    jTaBrigadasSinEspec.getRowCount() - 1);
        }
    }//GEN-LAST:event_jBSalirActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        if (jTaBrigadasConEspec.getRowCount() > 0) {
            jTaBrigadasConEspec.removeRowSelectionInterval(0,
                    jTaBrigadasConEspec.getRowCount() - 1);
        }
        if (jTaBrigadasSinEspec.getRowCount() > 0) {
            jTaBrigadasSinEspec.removeRowSelectionInterval(0,
                    jTaBrigadasSinEspec.getRowCount() - 1);
        }
        GestionEmergencia ge = ((MainFrame) this.getTopLevelAncestor()).getGestionEmergencia();
        ((MainFrame) this.getTopLevelAncestor()).focusIFrame(ge);
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void jTaBrigadasConEspecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTaBrigadasConEspecMouseClicked
        int filaSelec = jTaBrigadasConEspec.getSelectedRow();
        String nombreBrigada = (String) jTaBrigadasConEspec.getValueAt(filaSelec, 0);
        jLabAux.setText("<html>¿Está seguro de querer seleccionar la brigada \"" + nombreBrigada
                + "\" para tratar la emergencia?</html>");
        if (JOptionPane.showConfirmDialog(this, jLabAux, "Advertencia", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            brigadaSeleccionada = brigadaData.buscarBrigadaPorNombre(nombreBrigada);
            if (brigadaSeleccionada != null) {                
                jBSalir.doClick();
            } else {
                jLabAux.setText("<html>No se pudo asignar la brigada para tratar la "
                        + "emergencia.</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jTaBrigadasConEspecMouseClicked

    private void jTaBrigadasSinEspecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTaBrigadasSinEspecMouseClicked
        int filaSelec = jTaBrigadasSinEspec.getSelectedRow();
        String nombreBrigada = (String) jTaBrigadasSinEspec.getValueAt(filaSelec, 0);
        jLabAux.setText("<html>¿Está seguro de querer seleccionar la brigada \"" + nombreBrigada
                + "\" para tratar la emergencia?</html>");
        if (JOptionPane.showConfirmDialog(this, jLabAux, "Advertencia", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            brigadaSeleccionada = brigadaData.buscarBrigadaPorNombre(nombreBrigada);
            if (brigadaSeleccionada != null) {                
                jBSalir.doClick();
            } else {
                jLabAux.setText("<html>No se pudo asignar la brigada para tratar la "
                        + "emergencia.</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jTaBrigadasSinEspecMouseClicked

    private void jTaBrigadasConEspecMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTaBrigadasConEspecMouseExited
        if (jTaBrigadasConEspec.getRowCount() > 0) {
            jTaBrigadasConEspec.removeRowSelectionInterval(0,
                    jTaBrigadasConEspec.getRowCount() - 1);
        }
    }//GEN-LAST:event_jTaBrigadasConEspecMouseExited

    private void jTaBrigadasSinEspecMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTaBrigadasSinEspecMouseExited
        if (jTaBrigadasSinEspec.getRowCount() > 0) {
            jTaBrigadasSinEspec.removeRowSelectionInterval(0,
                    jTaBrigadasSinEspec.getRowCount() - 1);
        }
    }//GEN-LAST:event_jTaBrigadasSinEspecMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBSalir;
    private javax.swing.JLabel jLabAsignacionDeBrigada;
    private javax.swing.JLabel jLabBrigadasConvenientesConEspec;
    private javax.swing.JLabel jLabBrigadasSinEspec;
    private javax.swing.JLabel jLabMensajeJTaBrigadasConEspec;
    private javax.swing.JLabel jLabMensajeJTaBrigadasSinEspec;
    private javax.swing.JScrollPane jScPBomberosDeBrigada;
    private javax.swing.JScrollPane jScPBrigadas;
    private javax.swing.JTable jTaBrigadasConEspec;
    private javax.swing.JTable jTaBrigadasSinEspec;
    // End of variables declaration//GEN-END:variables
}
