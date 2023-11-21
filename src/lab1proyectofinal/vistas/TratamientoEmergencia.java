package lab1proyectofinal.vistas;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import lab1proyectofinal.accesoADatos.SiniestroData;
import lab1proyectofinal.accesoADatos.Utils;
import lab1proyectofinal.entidades.BrigadaDistancia;
import lab1proyectofinal.entidades.Siniestro;

/**
 *
 * @author Grupo-3
 */
public class TratamientoEmergencia extends javax.swing.JInternalFrame {

    private final SiniestroData siniestroData;
    private final DateTimeFormatter formatter;

    /**
     * Creates new form TratamientoEmergencia
     */
    public TratamientoEmergencia(SiniestroData siniestroData) {
        initComponents();
        this.siniestroData = siniestroData;
        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        emergenciaSinBrigadaLabel = new javax.swing.JLabel();
        emergenciaSinBrigadaCB = new javax.swing.JComboBox<>();
        brigadasAsignablesLabel = new javax.swing.JLabel();
        brigadasAsignablesCB = new javax.swing.JComboBox<>();
        emergenciaConBrigadaLabel = new javax.swing.JLabel();
        emergenciaConBrigadaCB = new javax.swing.JComboBox<>();
        BtnConfirmarBrigada = new javax.swing.JButton();
        BtnSalir = new javax.swing.JButton();
        fechaResolucionLabel = new javax.swing.JLabel();
        fechaResolucionDC = new com.toedter.calendar.JDateChooser();
        horaResolucionLabel = new javax.swing.JLabel();
        horaResolucionTF = new javax.swing.JFormattedTextField();
        puntuacionLabel = new javax.swing.JLabel();
        puntuacionSpinner = new javax.swing.JSpinner();
        BtnAsignarResolución = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Tratamiento de Emergencia");
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

        emergenciaSinBrigadaLabel.setText("Emergencias sin brigada asignada");

        emergenciaSinBrigadaCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emergenciaSinBrigadaCBActionPerformed(evt);
            }
        });

        brigadasAsignablesLabel.setText("Brigadas más adecuadas para asignar a la emergencia seleccionada");

        emergenciaConBrigadaLabel.setText("Emergencias con brigada asignada");

        emergenciaConBrigadaCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emergenciaConBrigadaCBActionPerformed(evt);
            }
        });

        BtnConfirmarBrigada.setText("Confirmar");
        BtnConfirmarBrigada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnConfirmarBrigadaActionPerformed(evt);
            }
        });

        BtnSalir.setText("Salir");
        BtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSalirActionPerformed(evt);
            }
        });

        fechaResolucionLabel.setText("Fecha Resolución:");

        fechaResolucionDC.setEnabled(false);

        horaResolucionLabel.setText("Hora Resolución:");

        horaResolucionTF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance())));
        horaResolucionTF.setToolTipText("Formato hora como HH:mm:ss");
        horaResolucionTF.setEnabled(false);

        puntuacionLabel.setText("Puntuación");

        puntuacionSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));
        puntuacionSpinner.setToolTipText("Puntuación de 1 a 10, 0 para especificar nulo");
        puntuacionSpinner.setEnabled(false);

        BtnAsignarResolución.setText("Asignar Resolución");
        BtnAsignarResolución.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAsignarResoluciónActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emergenciaConBrigadaCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(emergenciaSinBrigadaCB, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(brigadasAsignablesCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(BtnConfirmarBrigada))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(BtnSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fechaResolucionLabel)
                        .addGap(18, 18, 18)
                        .addComponent(fechaResolucionDC, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(horaResolucionLabel)
                        .addGap(18, 18, 18)
                        .addComponent(horaResolucionTF, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(brigadasAsignablesLabel)
                            .addComponent(emergenciaSinBrigadaLabel)
                            .addComponent(emergenciaConBrigadaLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(puntuacionLabel)
                                .addGap(52, 52, 52)
                                .addComponent(puntuacionSpinner)
                                .addGap(80, 80, 80)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addComponent(BtnAsignarResolución)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(emergenciaSinBrigadaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(emergenciaSinBrigadaCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(brigadasAsignablesLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(brigadasAsignablesCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnConfirmarBrigada))
                .addGap(45, 45, 45)
                .addComponent(emergenciaConBrigadaLabel)
                .addGap(18, 18, 18)
                .addComponent(emergenciaConBrigadaCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaResolucionLabel)
                    .addComponent(fechaResolucionDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(horaResolucionLabel)
                        .addComponent(horaResolucionTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(puntuacionLabel)
                    .addComponent(puntuacionSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(BtnAsignarResolución)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(BtnSalir)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        emergenciaSinBrigadaCB.removeAllItems();
        emergenciaConBrigadaCB.removeAllItems();
        List<Siniestro> siniestros = siniestroData.listarSiniestrosSinResolucion();
        for (Siniestro siniestro : siniestros) {
            if (siniestro.getBrigada().getNombreBrigada().equals(SiniestroData.nombreEntidadNula)) {
                emergenciaSinBrigadaCB.addItem(siniestro);
            } else {
                emergenciaConBrigadaCB.addItem(siniestro);
            }
        }
        emergenciaSinBrigadaCB.setSelectedIndex(-1);
        emergenciaConBrigadaCB.setSelectedIndex(-1);
    }//GEN-LAST:event_formInternalFrameActivated

    private void emergenciaSinBrigadaCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emergenciaSinBrigadaCBActionPerformed
        brigadasAsignablesCB.removeAllItems();
        brigadasAsignablesCB.setEnabled(false);
        BtnConfirmarBrigada.setEnabled(false);

        Siniestro siniestroSeleccionado = (Siniestro) emergenciaSinBrigadaCB.getSelectedItem();
        if (siniestroSeleccionado != null) {
            List<BrigadaDistancia> brigadas = siniestroData.listarBrigadasConvenientes(siniestroSeleccionado);
            for (BrigadaDistancia bd : brigadas) {
                brigadasAsignablesCB.addItem(bd);
            }
            if (brigadasAsignablesCB.getItemCount() > 0) {
                brigadasAsignablesCB.setEnabled(true);
                BtnConfirmarBrigada.setEnabled(true);
            }
            emergenciaSinBrigadaCB.setEnabled(true);
        } else if (emergenciaSinBrigadaCB.getItemCount() == 0) {
            emergenciaSinBrigadaCB.setEnabled(false);
        } else {
            emergenciaSinBrigadaCB.setEnabled(true);
        }
    }//GEN-LAST:event_emergenciaSinBrigadaCBActionPerformed

    private void emergenciaConBrigadaCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emergenciaConBrigadaCBActionPerformed
        fechaResolucionDC.setCalendar(null);
        horaResolucionTF.setText("");
        puntuacionSpinner.setValue(Integer.valueOf(0));

        Siniestro siniestroSeleccionado = (Siniestro) emergenciaConBrigadaCB.getSelectedItem();
        if (siniestroSeleccionado != null) {
            fechaResolucionDC.setEnabled(true);
            horaResolucionTF.setEnabled(true);
            puntuacionSpinner.setEnabled(true);
            BtnAsignarResolución.setEnabled(true);
            emergenciaConBrigadaCB.setEnabled(true);
        } else {
            fechaResolucionDC.setEnabled(false);
            horaResolucionTF.setEnabled(false);
            puntuacionSpinner.setEnabled(false);
            BtnAsignarResolución.setEnabled(false);
            if (emergenciaConBrigadaCB.getItemCount() == 0) {
                emergenciaConBrigadaCB.setEnabled(false);
            } else {
                emergenciaConBrigadaCB.setEnabled(true);
            }
        }
    }//GEN-LAST:event_emergenciaConBrigadaCBActionPerformed

    private void BtnConfirmarBrigadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConfirmarBrigadaActionPerformed
        Siniestro siniestroSeleccionado = (Siniestro) emergenciaSinBrigadaCB.getSelectedItem();
        if (siniestroSeleccionado == null) { // Este caso no deberia ocurrir nunca
            JOptionPane.showMessageDialog(this, "Seleccione primero una emergencia sin brigada asignada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BrigadaDistancia bd = (BrigadaDistancia) brigadasAsignablesCB.getSelectedItem();
        if (bd == null) {
            JOptionPane.showMessageDialog(this, "Seleccione primero una brigada de la lista.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        siniestroSeleccionado.setBrigada(bd.getBrigada());
        if (siniestroData.modificarSiniestro(siniestroSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Brigada asignada satisfactoriamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            emergenciaSinBrigadaCB.removeItem(siniestroSeleccionado);
            brigadasAsignablesCB.removeItem(bd);
            emergenciaConBrigadaCB.addItem(siniestroSeleccionado);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo asignar la brigada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnConfirmarBrigadaActionPerformed

    private void BtnAsignarResoluciónActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAsignarResoluciónActionPerformed
        Siniestro ss = (Siniestro) emergenciaConBrigadaCB.getSelectedItem();
        if (ss == null) { // Este caso no deberia ocurrir nunca
            JOptionPane.showMessageDialog(this, "Seleccione primero una emergencia con brigada asignada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date date = fechaResolucionDC.getDate();
        String horaStr = horaResolucionTF.getText();
        Integer puntuacion = (Integer) puntuacionSpinner.getValue();

        System.out.println(puntuacion);
        if (date == null || horaStr.isBlank() || puntuacion < 1 || puntuacion > 10) {
            JOptionPane.showMessageDialog(this, "Se debe asignar fecha y hora de resolución y un puntaje entre 1 y 10.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalTime lt;
        try {
            lt = LocalTime.parse(horaStr, formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "La hora introducida no parece ser correcta.\nAsegurese de que tenga el siguiente formato: HH:mm:ss", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDateTime fechaHoraResolucion = LocalDateTime.of(Utils.dateToLocalDate(date), lt);
        if (ss.getFechaHoraInicio().isAfter(fechaHoraResolucion)) {
            JOptionPane.showMessageDialog(this, "La fecha y hora de resolución no puede ser previa a la fecha y hora del inicio del siniestro.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Siniestro siniestro = new Siniestro(ss.getTipo(), ss.getFechaHoraInicio(), ss.getCoordenadaX(), ss.getCoordenadaY(), ss.getDetalles(), ss.getBrigada());
        siniestro.setCodigoSiniestro(ss.getCodigoSiniestro());
        siniestro.setFechaHoraResolucion(fechaHoraResolucion);
        siniestro.setPuntuacion(puntuacion.intValue());
        if (siniestroData.modificarSiniestro(siniestro)) {
            JOptionPane.showMessageDialog(this, "Resolución asignada satisfactoriamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            emergenciaConBrigadaCB.removeItem(ss);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo asignar la resolución.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnAsignarResoluciónActionPerformed

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        this.hide();
    }//GEN-LAST:event_BtnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAsignarResolución;
    private javax.swing.JButton BtnConfirmarBrigada;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JComboBox<BrigadaDistancia> brigadasAsignablesCB;
    private javax.swing.JLabel brigadasAsignablesLabel;
    private javax.swing.JComboBox<Siniestro> emergenciaConBrigadaCB;
    private javax.swing.JLabel emergenciaConBrigadaLabel;
    private javax.swing.JComboBox<Siniestro> emergenciaSinBrigadaCB;
    private javax.swing.JLabel emergenciaSinBrigadaLabel;
    private com.toedter.calendar.JDateChooser fechaResolucionDC;
    private javax.swing.JLabel fechaResolucionLabel;
    private javax.swing.JLabel horaResolucionLabel;
    private javax.swing.JFormattedTextField horaResolucionTF;
    private javax.swing.JLabel puntuacionLabel;
    private javax.swing.JSpinner puntuacionSpinner;
    // End of variables declaration//GEN-END:variables
}
