/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package lab1proyectofinal.vistas;

import java.awt.Color;
import java.beans.PropertyVetoException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import lab1proyectofinal.accesoADatos.*;
import lab1proyectofinal.entidades.*;

/**
 *
 * @author nahue
 */
public class ListadoDeEmergencias extends javax.swing.JInternalFrame {

    private final EmergenciaData emergenciaData;
    private List<Emergencia> emergencias;
    private Emergencia emergencia;
    private int filaSelec;
    private boolean programaCambiandoJCBEmergencias = false;
    private DefaultTableModel modeloJTaEmergencias = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };

    public ListadoDeEmergencias(EmergenciaData emergenciaData) {
        initComponents();
        this.emergenciaData = emergenciaData;
        configurarTabla();
    }

    private void configurarJCBEmergencias() {
        jLabMensajeJCBEmergencias.setText("");

        programaCambiandoJCBEmergencias = true;
        jCBEmergencias.removeAllItems();
        if (emergencias.isEmpty()) {
            jLabMensajeJCBEmergencias.setForeground(Color.BLACK);
            if (jRBTodas.isSelected()) {
                jLabMensajeJCBEmergencias.setText("<html>Advertencia: no hay emergencias "
                        + "registradas en el sistema.</html>");
            } else if (jRBResueltas.isSelected()) {
                jLabMensajeJCBEmergencias.setText("<html>Advertencia: no hay emergencias resueltas "
                        + "registradas en el sistema.</html>");
            } else if (jRBNoResueltas.isSelected()) {
                jLabMensajeJCBEmergencias.setText("<html>Advertencia: no hay emergencias sin "
                        + "resolver registradas en el sistema.</html>");
            }

            programaCambiandoJCBEmergencias = false;
            return;
        }
        for (Emergencia emer : emergencias) {
            jCBEmergencias.addItem(emer);
        }
        jCBEmergencias.setSelectedIndex(-1);
        programaCambiandoJCBEmergencias = false;

    }

    private void configurarTabla() {
        jTaEmergencias.getTableHeader().setFont(Utils.fuenteNegrita);
        modeloJTaEmergencias.addColumn("Fecha y hora de inicio");
        modeloJTaEmergencias.addColumn("Coordenadas");
        modeloJTaEmergencias.addColumn("Tipo");
        modeloJTaEmergencias.addColumn("Brigada encargada");
        modeloJTaEmergencias.addColumn("Fecha y hora de resolución");
        modeloJTaEmergencias.addColumn("Desempeño");
        jTaEmergencias.setModel(modeloJTaEmergencias);
        jTaEmergencias.getColumnModel().getColumn(0).setPreferredWidth(100);
        jTaEmergencias.getColumnModel().getColumn(1).setPreferredWidth(50);
        jTaEmergencias.getColumnModel().getColumn(2).setPreferredWidth(250);
        jTaEmergencias.getColumnModel().getColumn(3).setPreferredWidth(170);
        jTaEmergencias.getColumnModel().getColumn(4).setPreferredWidth(140);

        jTaEmergencias.setRowHeight(23);
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(modeloJTaEmergencias);
        sorter.toggleSortOrder(0);
        jTaEmergencias.setRowSorter(sorter);
    }

    private void cargarDatosTablaEmergencias() {
        modeloJTaEmergencias.setRowCount(0);
        if (!emergencias.isEmpty()) {
            for (Emergencia emer : emergencias) {
                String fechaHoraResolucion = emer.getFechaHoraResolucion() == null ? "Sin resolución"
                        : emer.getFechaHoraResolucion().toString();
                String desempeño = emer.getDesempenio() == -1 ? "Sin resolución"
                        : emer.getDesempenio() + "";
                String brigada = emer.getBrigada().getNombreBrigada().equals(Utils.nombreEntidadNula)
                        ? "Sin brigada asignada" : emer.getBrigada().getNombreBrigada();
                modeloJTaEmergencias.addRow(new Object[]{emer.getFechaHoraInicio(),
                    "(" + emer.getCoordenadaX() + "," + emer.getCoordenadaY() + ")", emer.getTipo(),
                    brigada, fechaHoraResolucion, desempeño});
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBGListados = new javax.swing.ButtonGroup();
        jScPBomberos = new javax.swing.JScrollPane();
        jTaEmergencias = new javax.swing.JTable();
        jLabTituloLista = new javax.swing.JLabel();
        jLabListadosEmergencias = new javax.swing.JLabel();
        jBSalir = new javax.swing.JButton();
        jScPDetalles = new javax.swing.JScrollPane();
        jTADetalles = new javax.swing.JTextArea();
        jLabDetalles = new javax.swing.JLabel();
        jLabBuscarConCB = new javax.swing.JLabel();
        jCBEmergencias = new javax.swing.JComboBox<>();
        jLabMensajeJCBEmergencias = new javax.swing.JLabel();
        jLabSeleccionRBs = new javax.swing.JLabel();
        jRBTodas = new javax.swing.JRadioButton();
        jRBResueltas = new javax.swing.JRadioButton();
        jRBNoResueltas = new javax.swing.JRadioButton();

        setPreferredSize(new java.awt.Dimension(1400, 740));
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

        jTaEmergencias.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTaEmergencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTaEmergencias.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTaEmergencias.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTaEmergencias.getTableHeader().setResizingAllowed(false);
        jTaEmergencias.getTableHeader().setReorderingAllowed(false);
        jTaEmergencias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTaEmergenciasMouseClicked(evt);
            }
        });
        jScPBomberos.setViewportView(jTaEmergencias);

        getContentPane().add(jScPBomberos, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, 1240, 250));

        jLabTituloLista.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabTituloLista.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabTituloLista.setText("Todos las emergencias ");
        getContentPane().add(jLabTituloLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 1240, -1));

        jLabListadosEmergencias.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabListadosEmergencias.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabListadosEmergencias.setText("Listados de emergencias");
        getContentPane().add(jLabListadosEmergencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 1250, -1));

        jBSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBSalir.setText("Salir");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });
        getContentPane().add(jBSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1300, 660, -1, -1));

        jScPDetalles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jTADetalles.setEditable(false);
        jTADetalles.setColumns(20);
        jTADetalles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTADetalles.setRows(5);
        jScPDetalles.setViewportView(jTADetalles);

        getContentPane().add(jScPDetalles, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 680, 90));

        jLabDetalles.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabDetalles.setText("Detalles de la emergencia:");
        getContentPane().add(jLabDetalles, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, -1, -1));

        jLabBuscarConCB.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabBuscarConCB.setText("Puede seleccionar una emergencia en esta lista desplegable o la tabla de abajo:");
        getContentPane().add(jLabBuscarConCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, -1, -1));

        jCBEmergencias.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jCBEmergencias.setMaximumRowCount(10);
        jCBEmergencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBEmergenciasActionPerformed(evt);
            }
        });
        getContentPane().add(jCBEmergencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 700, -1));

        jLabMensajeJCBEmergencias.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabMensajeJCBEmergencias.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabMensajeJCBEmergencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 680, 23));

        jLabSeleccionRBs.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabSeleccionRBs.setText("Seleccione el tipo de emergencias que desea listar:");
        getContentPane().add(jLabSeleccionRBs, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 140, -1, -1));

        jBGListados.add(jRBTodas);
        jRBTodas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jRBTodas.setSelected(true);
        jRBTodas.setText("Todas");
        jRBTodas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBTodasActionPerformed(evt);
            }
        });
        getContentPane().add(jRBTodas, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 170, -1, -1));

        jBGListados.add(jRBResueltas);
        jRBResueltas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jRBResueltas.setText("Resueltas");
        jRBResueltas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBResueltasActionPerformed(evt);
            }
        });
        getContentPane().add(jRBResueltas, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 170, -1, -1));

        jBGListados.add(jRBNoResueltas);
        jRBNoResueltas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jRBNoResueltas.setText("No resueltas");
        jRBNoResueltas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBNoResueltasActionPerformed(evt);
            }
        });
        getContentPane().add(jRBNoResueltas, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 170, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        if (jRBTodas.isSelected()) {
            emergencias = emergenciaData.listarEmergencias();
        } else if (jRBResueltas.isSelected()) {
            emergencias = emergenciaData.listarEmergenciasResueltos();
        } else if (jRBNoResueltas.isSelected()) {
            emergencias = emergenciaData.listarEmergenciasSinResolucion();
        }
        configurarJCBEmergencias();
        cargarDatosTablaEmergencias();
    }//GEN-LAST:event_formInternalFrameActivated

    private void jBSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalirActionPerformed
        this.hide();
//        if (jTaEmergencias.getRowCount() > 0) {
//            jTaEmergencias.removeRowSelectionInterval(0, jTaEmergencias.getRowCount() - 1);
//        }        
        jLabMensajeJCBEmergencias.setText("");
        jTADetalles.setText("");
        modeloJTaEmergencias.setRowCount(0);

        try {
            setSelected(false);
        } catch (PropertyVetoException ex) {
            System.out.println("[Main Frame Error]");
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jBSalirActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
//        if (jTaEmergencias.getRowCount() > 0) {
//            jTaEmergencias.removeRowSelectionInterval(0, jTaEmergencias.getRowCount() - 1);
//        }                
        jLabMensajeJCBEmergencias.setText("");
        jTADetalles.setText("");
        modeloJTaEmergencias.setRowCount(0);
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void jCBEmergenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBEmergenciasActionPerformed
        emergencia = (Emergencia) jCBEmergencias.getSelectedItem();

        // evita que el programa entre en modo "registro encontrado" cada vez que se genera un
        // actionEvent en "jCBEmergencias" sin la intervención del usuario y, además, el índice
        // seleccionado en dicho componente es distinto de -1 (situación que ocurre al agregar el
        // primer item a "jCBEmergencias" en "configurarJCBEmergencias")
        if (emergencia != null && programaCambiandoJCBEmergencias == false) {
            int index = jCBEmergencias.getSelectedIndex();
            jTaEmergencias.setRowSelectionInterval(index, index);
            jTADetalles.setText(emergencia.getDetalles());
        }
    }//GEN-LAST:event_jCBEmergenciasActionPerformed

    private void jRBTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBTodasActionPerformed
        emergencias = emergenciaData.listarEmergencias();
        jTADetalles.setText("");
        configurarJCBEmergencias();
        cargarDatosTablaEmergencias();
        jLabTituloLista.setText("Todas las emergencias");
    }//GEN-LAST:event_jRBTodasActionPerformed

    private void jRBResueltasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBResueltasActionPerformed
        emergencias = emergenciaData.listarEmergenciasResueltos();
        jTADetalles.setText("");
        configurarJCBEmergencias();
        cargarDatosTablaEmergencias();
        jLabTituloLista.setText("Emergencias resueltas");
    }//GEN-LAST:event_jRBResueltasActionPerformed

    private void jRBNoResueltasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBNoResueltasActionPerformed
        emergencias = emergenciaData.listarEmergenciasSinResolucion();
        jTADetalles.setText("");
        configurarJCBEmergencias();
        cargarDatosTablaEmergencias();
        jLabTituloLista.setText("Emergencias no resueltas");
    }//GEN-LAST:event_jRBNoResueltasActionPerformed

    private void jTaEmergenciasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTaEmergenciasMouseClicked
        filaSelec = jTaEmergencias.getSelectedRow();
        jCBEmergencias.setSelectedIndex(filaSelec);
    }//GEN-LAST:event_jTaEmergenciasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup jBGListados;
    private javax.swing.JButton jBSalir;
    private javax.swing.JComboBox<Emergencia> jCBEmergencias;
    private javax.swing.JLabel jLabBuscarConCB;
    private javax.swing.JLabel jLabDetalles;
    private javax.swing.JLabel jLabListadosEmergencias;
    private javax.swing.JLabel jLabMensajeJCBEmergencias;
    private javax.swing.JLabel jLabSeleccionRBs;
    private javax.swing.JLabel jLabTituloLista;
    private javax.swing.JRadioButton jRBNoResueltas;
    private javax.swing.JRadioButton jRBResueltas;
    private javax.swing.JRadioButton jRBTodas;
    private javax.swing.JScrollPane jScPBomberos;
    private javax.swing.JScrollPane jScPDetalles;
    private javax.swing.JTextArea jTADetalles;
    private javax.swing.JTable jTaEmergencias;
    // End of variables declaration//GEN-END:variables
}