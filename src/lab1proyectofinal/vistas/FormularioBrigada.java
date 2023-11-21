package lab1proyectofinal.vistas;

import java.util.List;
import javax.swing.JOptionPane;
import lab1proyectofinal.accesoADatos.BrigadaData;
import lab1proyectofinal.accesoADatos.CuartelData;
import lab1proyectofinal.accesoADatos.Utils;
import lab1proyectofinal.entidades.Brigada;
import lab1proyectofinal.entidades.Cuartel;

/**
 *
 * @author Grupo-3
 */
public class FormularioBrigada extends javax.swing.JInternalFrame {

    private final BrigadaData brigadaData;
    private final CuartelData cuartelData;

    /**
     * Creates new form GestionBrigada
     */
    public FormularioBrigada(CuartelData cuartelData, BrigadaData brigadaData) {
        initComponents();
        this.cuartelData = cuartelData;
        this.brigadaData = brigadaData;
    }

    private void limpiarCampos() {
        nombreTF.setText("");
        especialidadCB.setSelectedIndex(-1);
        cuartelCB.setSelectedIndex(-1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nombreLabel = new javax.swing.JLabel();
        nombreTF = new javax.swing.JTextField();
        especialidadLabel = new javax.swing.JLabel();
        especialidadCB = new javax.swing.JComboBox<>();
        cuartelLabel = new javax.swing.JLabel();
        cuartelCB = new javax.swing.JComboBox<>();
        BtnComprobar = new javax.swing.JButton();
        BtnLimpiar = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnSalir = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Formulario Brigada");
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

        nombreLabel.setText("Nombre:");

        especialidadLabel.setText("Especialidad:");

        especialidadCB.setModel(new javax.swing.DefaultComboBoxModel<>(Utils.obtenerEspecialidades()));

        cuartelLabel.setText("Cuartel:");

        BtnComprobar.setText("Comprobar");
        BtnComprobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnComprobarActionPerformed(evt);
            }
        });

        BtnLimpiar.setText("Limpiar Campos");
        BtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLimpiarActionPerformed(evt);
            }
        });

        BtnGuardar.setText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        BtnSalir.setText("Salir");
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombreLabel)
                            .addComponent(especialidadLabel)
                            .addComponent(cuartelLabel))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombreTF)
                            .addComponent(especialidadCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cuartelCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(BtnComprobar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtnLimpiar)
                        .addGap(18, 18, 18)
                        .addComponent(BtnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 247, Short.MAX_VALUE)
                        .addComponent(BtnSalir)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreLabel)
                    .addComponent(nombreTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnComprobar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(especialidadLabel)
                    .addComponent(especialidadCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cuartelLabel)
                    .addComponent(cuartelCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnLimpiar)
                    .addComponent(BtnGuardar)
                    .addComponent(BtnSalir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnComprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnComprobarActionPerformed
        String nombreStr = nombreTF.getText().trim();
        if (nombreStr.isBlank()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre a comprobar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Brigada brigada = brigadaData.buscarBrigadaPorNombre(nombreStr);
        if (brigada == null) {
            JOptionPane.showMessageDialog(this, "Este nombre está disponible.", "Disponible", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Este nombre no se encuentra disponible.", "No disponible", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_BtnComprobarActionPerformed

    private void BtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLimpiarActionPerformed
        this.limpiarCampos();
    }//GEN-LAST:event_BtnLimpiarActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        String nombreStr = nombreTF.getText().trim();
        String especialidadStr = (String) especialidadCB.getSelectedItem();
        Cuartel cuartel = (Cuartel) cuartelCB.getSelectedItem();

        if (nombreStr.isBlank() || especialidadStr == null || cuartel == null) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Brigada brigada = new Brigada(nombreStr, especialidadStr, true, cuartel);
        if (brigadaData.guardarBrigada(brigada)) {
            JOptionPane.showMessageDialog(this, "Brigada guardada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la brigada.\nQuizás ya haya una brigada guardada con este nombre.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        this.hide();
    }//GEN-LAST:event_BtnSalirActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        cuartelCB.removeAllItems();

        List<Cuartel> cuarteles = cuartelData.listarCuarteles();
        if (!cuarteles.isEmpty()) {
            for (Cuartel cuartel : cuarteles) {
                cuartelCB.addItem(cuartel);
            }
            cuartelCB.setEnabled(true);
            BtnGuardar.setEnabled(true);
        } else {
            cuartelCB.setEnabled(false);
            BtnGuardar.setEnabled(false);
        }

        this.limpiarCampos();
    }//GEN-LAST:event_formInternalFrameActivated


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnComprobar;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnLimpiar;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JComboBox<Cuartel> cuartelCB;
    private javax.swing.JLabel cuartelLabel;
    private javax.swing.JComboBox<String> especialidadCB;
    private javax.swing.JLabel especialidadLabel;
    private javax.swing.JLabel nombreLabel;
    private javax.swing.JTextField nombreTF;
    // End of variables declaration//GEN-END:variables
}
