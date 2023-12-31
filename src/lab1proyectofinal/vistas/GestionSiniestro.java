package lab1proyectofinal.vistas;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import lab1proyectofinal.accesoADatos.BrigadaData;
import lab1proyectofinal.accesoADatos.SiniestroData;
import lab1proyectofinal.accesoADatos.Utils;
import lab1proyectofinal.entidades.Brigada;
import lab1proyectofinal.entidades.Siniestro;

/**
 *
 * @author Grupo-3
 */
public class GestionSiniestro extends javax.swing.JInternalFrame {

    private final SiniestroData siniestroData;
    private final BrigadaData brigadaData;
    private final DateTimeFormatter formatter;

    /**
     * Creates new form FormularioSiniestro
     */
    public GestionSiniestro(SiniestroData siniestroData, BrigadaData brigadaData) {
        initComponents();
        this.siniestroData = siniestroData;
        this.brigadaData = brigadaData;
        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    private void limpiarCampos() {
        siniestroCB.setSelectedIndex(-1);
        tipoCB.setSelectedIndex(-1);
        fechaSiniestroDC.setCalendar(null);
        horaSiniestroTF.setText("");
        coordenadaXTF.setText("");
        coordenadaYTF.setText("");
        detallesTA.setText("");
        brigadaCB.setSelectedIndex(-1);
        fechaResolucionDC.setCalendar(null);
        horaResolucionTF.setText("");
        puntuacionSpinner.setValue(Integer.valueOf(0));
    }

    private void setFieldsEditable(boolean flag) {
        tipoCB.setEnabled(flag);            // No hay un ineditable
        fechaSiniestroDC.setEnabled(flag);  // No hay un ineditable
        horaSiniestroTF.setEditable(flag);
        coordenadaXTF.setEditable(flag);
        coordenadaYTF.setEditable(flag);
        detallesTA.setEditable(flag);
        //brigadaCB.setEnabled(flag);         // No hay un ineditable
        //fechaResolucionDC.setEnabled(flag); // No hay un ineditable
        //horaResolucionTF.setEditable(flag);
        //puntuacionSpinner.setEnabled(flag); // No hay un ineditable
        BtnGuardar.setEnabled(flag);
    }

    private void setFieldsEnabled(boolean flag) {
        //tipoCB.setEnabled(flag);            // No hay un ineditable
        //fechaSiniestroDC.setEnabled(flag);  // No hay un ineditable
        horaSiniestroTF.setEnabled(flag);
        coordenadaXTF.setEnabled(flag);
        coordenadaYTF.setEnabled(flag);
        detallesTA.setEnabled(flag);
        //brigadaCB.setEnabled(flag);         // No hay un ineditable
        //fechaResolucionDC.setEnabled(flag); // No hay un ineditable
        //horaResolucionTF.setEditable(flag);
        //puntuacionSpinner.setEnabled(flag); // No hay un ineditable
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        siniestroLabel = new javax.swing.JLabel();
        siniestroCB = new javax.swing.JComboBox<>();
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
        brigadaLabel = new javax.swing.JLabel();
        brigadaCB = new javax.swing.JComboBox<>();
        fechaResolucionLabel = new javax.swing.JLabel();
        fechaResolucionDC = new com.toedter.calendar.JDateChooser();
        horaResolucionLabel = new javax.swing.JLabel();
        horaResolucionTF = new javax.swing.JFormattedTextField();
        puntuacionLabel = new javax.swing.JLabel();
        puntuacionSpinner = new javax.swing.JSpinner();
        BtnEditar = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnSalir = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Gestión Siniestro");
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

        siniestroLabel.setText("Siniestro:");

        siniestroCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siniestroCBActionPerformed(evt);
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

        brigadaLabel.setText("Brigada asignada:");

        brigadaCB.setEnabled(false);

        fechaResolucionLabel.setText("Fecha Resolución:");

        fechaResolucionDC.setEnabled(false);

        horaResolucionLabel.setText("Hora Resolución:");

        horaResolucionTF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance())));
        horaResolucionTF.setToolTipText("Formato hora como HH:mm:ss");
        horaResolucionTF.setEnabled(false);

        puntuacionLabel.setText("Puntuación:");

        puntuacionSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));
        puntuacionSpinner.setToolTipText("Puntuación de 1 a 10, 0 para especificar nulo");
        puntuacionSpinner.setEnabled(false);

        BtnEditar.setText("Editar");
        BtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditarActionPerformed(evt);
            }
        });

        BtnGuardar.setText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        BtnEliminar.setText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
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
                    .addComponent(detallesLabel)
                    .addComponent(siniestroLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(puntuacionLabel)
                        .addGap(43, 43, 43)
                        .addComponent(puntuacionSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(BtnEditar)
                            .addGap(18, 18, 18)
                            .addComponent(BtnGuardar)
                            .addGap(18, 18, 18)
                            .addComponent(BtnEliminar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnSalir))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(fechaResolucionLabel)
                                .addComponent(brigadaLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(fechaResolucionDC, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(horaResolucionLabel)
                                    .addGap(18, 18, 18)
                                    .addComponent(horaResolucionTF, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(brigadaCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tipoLabel)
                                .addComponent(fechaSiniestroLabel)
                                .addComponent(coordenadaXLabel))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(siniestroCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tipoCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(fechaSiniestroDC, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(29, 29, 29)
                                    .addComponent(horaSiniestroLabel)
                                    .addGap(18, 18, 18)
                                    .addComponent(horaSiniestroTF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(coordenadaXTF, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(coordenadaYLabel)
                                    .addGap(18, 18, 18)
                                    .addComponent(coordenadaYTF, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(siniestroLabel)
                    .addComponent(siniestroCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoLabel)
                    .addComponent(tipoCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fechaSiniestroLabel)
                    .addComponent(fechaSiniestroDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(horaSiniestroTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(horaSiniestroLabel)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(coordenadaXLabel)
                    .addComponent(coordenadaXTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(coordenadaYLabel)
                    .addComponent(coordenadaYTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(detallesLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(brigadaLabel)
                            .addComponent(brigadaCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(fechaResolucionLabel))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fechaResolucionDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(horaResolucionLabel)
                        .addComponent(horaResolucionTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(puntuacionLabel)
                    .addComponent(puntuacionSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnEditar)
                    .addComponent(BtnGuardar)
                    .addComponent(BtnSalir)
                    .addComponent(BtnEliminar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        siniestroCB.removeAllItems();
        List<Siniestro> siniestros = siniestroData.listarSiniestros();
        Set<Brigada> brigadas = new HashSet();
        for (Siniestro siniestro : siniestros) {
            siniestroCB.addItem(siniestro);
            brigadas.add(siniestro.getBrigada());
        }
        brigadaCB.removeAllItems();
        for (Brigada brigada : brigadas) {
            brigadaCB.addItem(brigada);
        }
        siniestroCB.setSelectedIndex(-1);
    }//GEN-LAST:event_formInternalFrameActivated

    private void siniestroCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siniestroCBActionPerformed
        setFieldsEditable(false);
        Siniestro siniestroSeleccionado = (Siniestro) siniestroCB.getSelectedItem();
        if (siniestroSeleccionado != null) {
            tipoCB.setSelectedItem(siniestroSeleccionado.getTipo());
            fechaSiniestroDC.setCalendar(Utils.localDateToCalendar(siniestroSeleccionado.getFechaHoraInicio().toLocalDate()));
            horaSiniestroTF.setText(siniestroSeleccionado.getFechaHoraInicio().toLocalTime().format(formatter));
            coordenadaXTF.setText(Integer.toString(siniestroSeleccionado.getCoordenadaX()));
            coordenadaYTF.setText(Integer.toString(siniestroSeleccionado.getCoordenadaY()));
            detallesTA.setText(siniestroSeleccionado.getDetalles());
            if (!siniestroSeleccionado.getBrigada().getNombreBrigada().equals(SiniestroData.nombreEntidadNula)) {
                brigadaCB.setSelectedItem(siniestroSeleccionado.getBrigada());
                if (siniestroSeleccionado.getFechaHoraResolucion() != null) {
                    fechaResolucionDC.setCalendar(Utils.localDateToCalendar(siniestroSeleccionado.getFechaHoraResolucion().toLocalDate()));
                    horaResolucionTF.setText(siniestroSeleccionado.getFechaHoraResolucion().toLocalTime().format(formatter));
                    puntuacionSpinner.setValue(Integer.valueOf(siniestroSeleccionado.getPuntuacion()));
                } else {
                    fechaResolucionDC.setCalendar(null);
                    horaResolucionTF.setText("");
                    puntuacionSpinner.setValue(Integer.valueOf(0));
                }
            } else {
                brigadaCB.setSelectedIndex(-1);
                fechaResolucionDC.setCalendar(null);
                horaResolucionTF.setText("");
                puntuacionSpinner.setValue(Integer.valueOf(0));
            }
            BtnEditar.setEnabled(true);
            BtnEliminar.setEnabled(true);
            setFieldsEnabled(true);
        } else {
            limpiarCampos();
            BtnEditar.setEnabled(false);
            BtnEliminar.setEnabled(false);
            setFieldsEnabled(false);
            if (siniestroCB.getItemCount() == 0) {
                siniestroCB.setEnabled(false);
            } else {
                siniestroCB.setEnabled(true);
            }
        }
    }//GEN-LAST:event_siniestroCBActionPerformed

    private void BtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditarActionPerformed
        Siniestro siniestroSeleccionado = (Siniestro) siniestroCB.getSelectedItem();
        if (siniestroSeleccionado != null) {
            BtnEditar.setEnabled(false);
            setFieldsEditable(true);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione primero un siniestro a editar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnEditarActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        Siniestro siniestroSeleccionado = (Siniestro) siniestroCB.getSelectedItem();
        if (siniestroSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione primero un siniestro a editar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipo = (String) tipoCB.getSelectedItem();
        Date date = fechaSiniestroDC.getDate();
        String horaStr = horaSiniestroTF.getText();
        String coordenadaXStr = coordenadaXTF.getText().trim();
        String coordenadaYStr = coordenadaYTF.getText().trim();
        String detalles = detallesTA.getText().trim();

        if (tipo == null || date == null || horaStr.isBlank() || coordenadaXStr.isBlank()
                || coordenadaYStr.isBlank() || detalles.isBlank()) {
            JOptionPane.showMessageDialog(this, "No puede haber campos vacios.", "Error", JOptionPane.ERROR_MESSAGE);
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

        LocalDateTime fechaHoraSiniestro = LocalDateTime.of(Utils.dateToLocalDate(date), lt);

        Siniestro siniestro = new Siniestro(tipo, fechaHoraSiniestro, coordenadaX, coordenadaY, detalles);
        siniestro.setCodigoSiniestro(siniestroSeleccionado.getCodigoSiniestro());
        if (this.siniestroData.modificarSiniestro(siniestro)) {
            JOptionPane.showMessageDialog(this, "Siniestro modificado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo modificar el siniestro.\nContacte al administrador para mas información.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        Siniestro siniestroSeleccionado = (Siniestro) siniestroCB.getSelectedItem();
        if (siniestroSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione primero un siniestro a eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este siniestro?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            if (siniestroData.eliminarSiniestro(siniestroSeleccionado.getCodigoSiniestro())) {
                JOptionPane.showMessageDialog(this, "Siniestro eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                siniestroCB.removeItem(siniestroSeleccionado);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        this.hide();
    }//GEN-LAST:event_BtnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnEditar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JComboBox<Brigada> brigadaCB;
    private javax.swing.JLabel brigadaLabel;
    private javax.swing.JLabel coordenadaXLabel;
    private javax.swing.JTextField coordenadaXTF;
    private javax.swing.JLabel coordenadaYLabel;
    private javax.swing.JTextField coordenadaYTF;
    private javax.swing.JLabel detallesLabel;
    private javax.swing.JTextArea detallesTA;
    private com.toedter.calendar.JDateChooser fechaResolucionDC;
    private javax.swing.JLabel fechaResolucionLabel;
    private com.toedter.calendar.JDateChooser fechaSiniestroDC;
    private javax.swing.JLabel fechaSiniestroLabel;
    private javax.swing.JLabel horaResolucionLabel;
    private javax.swing.JFormattedTextField horaResolucionTF;
    private javax.swing.JLabel horaSiniestroLabel;
    private javax.swing.JFormattedTextField horaSiniestroTF;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel puntuacionLabel;
    private javax.swing.JSpinner puntuacionSpinner;
    private javax.swing.JComboBox<Siniestro> siniestroCB;
    private javax.swing.JLabel siniestroLabel;
    private javax.swing.JComboBox<String> tipoCB;
    private javax.swing.JLabel tipoLabel;
    // End of variables declaration//GEN-END:variables
}
