package lab1proyectofinal.vistas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import javax.swing.JOptionPane;
import lab1proyectofinal.accesoADatos.SiniestroData;
import lab1proyectofinal.accesoADatos.Utils;
import lab1proyectofinal.entidades.Siniestro;

/**
 *
 * @author Grupo-3
 */
public class FormularioSiniestro extends javax.swing.JInternalFrame {

    private final SiniestroData siniestroData;
    private final DateTimeFormatter formatter;

    /**
     * Creates new form FormularioSiniestro
     */
    public FormularioSiniestro(SiniestroData siniestroData) {
        initComponents();
        this.siniestroData = siniestroData;

        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    private void limpiarCampos() {
        tipoCB.setSelectedIndex(-1);
        fechaSiniestroDC.setCalendar(null);
        horaSiniestroTF.setText("");
        coordenadaXTF.setText("");
        coordenadaYTF.setText("");
        detallesTA.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tipoLabel = new javax.swing.JLabel();
        tipoCB = new javax.swing.JComboBox<>();
        fechaSiniestroLabel = new javax.swing.JLabel();
        fechaSiniestroDC = new com.toedter.calendar.JDateChooser();
        horaSiniestroLabel = new javax.swing.JLabel();
        horaSiniestroTF = new javax.swing.JFormattedTextField();
        coordenadaXLabel = new javax.swing.JLabel();
        coordenadaXTF = new javax.swing.JTextField();
        coordenadaYLabel = new javax.swing.JLabel();
        coordenadaYTF = new javax.swing.JTextField();
        detallesLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        detallesTA = new javax.swing.JTextArea();
        BtnLimpiar = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnSalir = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Formulario Siniestro");
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

        tipoLabel.setText("Tipo:");

        tipoCB.setModel(new javax.swing.DefaultComboBoxModel<>(Utils.obtenerEspecialidades()));

        fechaSiniestroLabel.setText("Fecha Siniestro:");

        horaSiniestroLabel.setText("Hora Siniestro:");

        horaSiniestroTF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance())));
        horaSiniestroTF.setToolTipText("Formato hora como HH:mm:ss");

        coordenadaXLabel.setText("Coordenada X:");

        coordenadaYLabel.setText("Coordenada Y:");

        detallesLabel.setText("Detalles:");

        detallesTA.setColumns(20);
        detallesTA.setRows(5);
        jScrollPane1.setViewportView(detallesTA);

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tipoLabel)
                                    .addComponent(fechaSiniestroLabel)
                                    .addComponent(coordenadaXLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tipoCB, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(fechaSiniestroDC, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(horaSiniestroLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(horaSiniestroTF, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(coordenadaXTF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(coordenadaYLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(coordenadaYTF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(detallesLabel)
                            .addComponent(jScrollPane1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtnLimpiar)
                        .addGap(18, 18, 18)
                        .addComponent(BtnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnSalir)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoLabel)
                    .addComponent(tipoCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fechaSiniestroLabel)
                    .addComponent(fechaSiniestroDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(horaSiniestroLabel)
                        .addComponent(horaSiniestroTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(coordenadaXLabel)
                    .addComponent(coordenadaXTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(coordenadaYLabel)
                    .addComponent(coordenadaYTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(detallesLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnLimpiar)
                    .addComponent(BtnGuardar)
                    .addComponent(BtnSalir))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        this.limpiarCampos();
    }//GEN-LAST:event_formInternalFrameActivated

    private void BtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLimpiarActionPerformed
        this.limpiarCampos();
    }//GEN-LAST:event_BtnLimpiarActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        String tipo = (String) tipoCB.getSelectedItem();
        Date date = fechaSiniestroDC.getDate();
        String horaStr = horaSiniestroTF.getText();
        String coordenadaXStr = coordenadaXTF.getText().trim();
        String coordenadaYStr = coordenadaYTF.getText().trim();
        String detalles = detallesTA.getText().trim();

        if (tipo == null || date == null || horaStr.isBlank() || coordenadaXStr.isBlank()
                || coordenadaYStr.isBlank() || detalles.isBlank()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int coordenadaX;
        try {
            coordenadaX = Integer.parseInt(coordenadaXStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Se esperaba un numero entero en Coordenada X.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int coordenadaY;
        try {
            coordenadaY = Integer.parseInt(coordenadaYStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Se esperaba un numero entero en Coordenada Y.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalTime lt;
        try {
            lt = LocalTime.parse(horaStr, formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "La hora introducida no parece ser correcta.\nAsegurese de que tenga el siguiente formato: HH:mm:ss", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime fechaHoraSiniestro = LocalDateTime.of(ld.getYear(), ld.getMonth(), ld.getDayOfMonth(), lt.getHour(), lt.getMinute(), lt.getSecond());

        Siniestro siniestro = new Siniestro(tipo, fechaHoraSiniestro, coordenadaX, coordenadaY, detalles);
        if (this.siniestroData.guardarSiniestro(siniestro)) {
            JOptionPane.showMessageDialog(this, "Siniestro guardado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el siniestro.\nContacte al administrador para mas información.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        this.hide();
    }//GEN-LAST:event_BtnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnLimpiar;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JLabel coordenadaXLabel;
    private javax.swing.JTextField coordenadaXTF;
    private javax.swing.JLabel coordenadaYLabel;
    private javax.swing.JTextField coordenadaYTF;
    private javax.swing.JLabel detallesLabel;
    private javax.swing.JTextArea detallesTA;
    private com.toedter.calendar.JDateChooser fechaSiniestroDC;
    private javax.swing.JLabel fechaSiniestroLabel;
    private javax.swing.JLabel horaSiniestroLabel;
    private javax.swing.JFormattedTextField horaSiniestroTF;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> tipoCB;
    private javax.swing.JLabel tipoLabel;
    // End of variables declaration//GEN-END:variables
}
