package lab1proyectofinal.vistas;

import javax.swing.JOptionPane;
import lab1proyectofinal.accesoADatos.CuartelData;
import lab1proyectofinal.accesoADatos.Utils;
import lab1proyectofinal.entidades.Cuartel;

/**
 *
 * @author Grupo-3
 */
public class GestionCuartel extends javax.swing.JInternalFrame {

    /**
     * SUJETO A CAMBIOS
     */
    private final CuartelData cuartelData;

    /**
     * Creates new form GestionCuartel
     */
    public GestionCuartel(CuartelData cuartelData) {
        initComponents();
        this.cuartelData = cuartelData;
    }

    private void limpiarCampos() {
        codigoTF.setText("");
        nombreTF.setText("");
        direccionTF.setText("");
        telefonoTF.setText("");
        correoTF.setText("");
        coordenadaXTF.setText("");
        coordenadaYTF.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titulo = new javax.swing.JLabel();
        codigoLabel = new javax.swing.JLabel();
        codigoTF = new javax.swing.JTextField();
        nombreLabel = new javax.swing.JLabel();
        nombreTF = new javax.swing.JTextField();
        direccionLabel = new javax.swing.JLabel();
        direccionTF = new javax.swing.JTextField();
        telefonoLabel = new javax.swing.JLabel();
        telefonoTF = new javax.swing.JTextField();
        correoLabel = new javax.swing.JLabel();
        correoTF = new javax.swing.JTextField();
        coordenadaXLabel = new javax.swing.JLabel();
        coordenadaXTF = new javax.swing.JTextField();
        coordenadaYLabel = new javax.swing.JLabel();
        coordenadaYTF = new javax.swing.JTextField();
        BtnBuscarCodigo = new javax.swing.JButton();
        BtnBuscarNombre = new javax.swing.JButton();
        BtnLimpiar = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnSalir = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Cuarteles");

        titulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("Formulario Cuartel");

        codigoLabel.setText("Código:");

        nombreLabel.setText("Nombre:");

        direccionLabel.setText("Dirección:");

        telefonoLabel.setText("Teléfono:");

        correoLabel.setText("Correo Electrónico:");

        coordenadaXLabel.setText("Coordenada X:");

        coordenadaYLabel.setText("Coordenada Y:");

        BtnBuscarCodigo.setText("Buscar");
        BtnBuscarCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarCodigoActionPerformed(evt);
            }
        });

        BtnBuscarNombre.setText("Buscar");
        BtnBuscarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarNombreActionPerformed(evt);
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

        BtnEliminar.setText("Dar de Baja");
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
            .addComponent(titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombreLabel)
                            .addComponent(direccionLabel)
                            .addComponent(telefonoLabel)
                            .addComponent(codigoLabel)
                            .addComponent(correoLabel)
                            .addComponent(coordenadaXLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(coordenadaXTF, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(coordenadaYLabel)
                                .addGap(18, 18, 18)
                                .addComponent(coordenadaYTF, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(correoTF, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                                    .addComponent(nombreTF)
                                    .addComponent(direccionTF)
                                    .addComponent(telefonoTF, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(codigoTF, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BtnBuscarNombre)
                                    .addComponent(BtnBuscarCodigo, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(40, 40, 40))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtnLimpiar)
                        .addGap(18, 18, 18)
                        .addComponent(BtnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(BtnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnSalir)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codigoLabel)
                    .addComponent(codigoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnBuscarCodigo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreLabel)
                    .addComponent(nombreTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnBuscarNombre))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(direccionTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(direccionLabel))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telefonoLabel)
                    .addComponent(telefonoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(correoLabel)
                    .addComponent(correoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(coordenadaXLabel)
                    .addComponent(coordenadaXTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(coordenadaYLabel)
                    .addComponent(coordenadaYTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnLimpiar)
                    .addComponent(BtnGuardar)
                    .addComponent(BtnSalir)
                    .addComponent(BtnEliminar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBuscarCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarCodigoActionPerformed
        int codigo;
        try {
            codigo = Integer.parseInt(codigoTF.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Se esperaba un numero entero en Código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cuartel cuartel = cuartelData.buscarCuartel(codigo);
        if (cuartel != null) {
            codigoTF.setText(Integer.toString(cuartel.getCodigoCuartel()));
            nombreTF.setText(cuartel.getNombreCuartel());
            direccionTF.setText(cuartel.getDireccion());
            telefonoTF.setText(cuartel.getTelefono());
            correoTF.setText(cuartel.getCorreo());
            coordenadaXTF.setText(Integer.toString(cuartel.getCoordenadaX()));
            coordenadaYTF.setText(Integer.toString(cuartel.getCoordenadaY()));
        } else {
            JOptionPane.showMessageDialog(this, "Cuartel no encontrado.\nEs posible que haya sido dado de baja o no exista.", "Información", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnBuscarCodigoActionPerformed

    private void BtnBuscarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarNombreActionPerformed
        String nombreStr = nombreTF.getText().trim();
        if (nombreStr.isBlank()) {
            JOptionPane.showMessageDialog(this, "Se esperaba un nombre en el campo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cuartel cuartel = cuartelData.buscarCuartelPorNombre(nombreStr);
        if (cuartel != null) {
            codigoTF.setText(Integer.toString(cuartel.getCodigoCuartel()));
            nombreTF.setText(cuartel.getNombreCuartel());
            direccionTF.setText(cuartel.getDireccion());
            telefonoTF.setText(cuartel.getTelefono());
            correoTF.setText(cuartel.getCorreo());
            coordenadaXTF.setText(Integer.toString(cuartel.getCoordenadaX()));
            coordenadaYTF.setText(Integer.toString(cuartel.getCoordenadaY()));
        } else {
            JOptionPane.showMessageDialog(this, "Cuartel no encontrado.\nEs posible que haya sido dado de baja o no exista.", "Información", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnBuscarNombreActionPerformed

    private void BtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLimpiarActionPerformed
        this.limpiarCampos();
    }//GEN-LAST:event_BtnLimpiarActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        String codigoStr = codigoTF.getText().trim();
        String nombreStr = nombreTF.getText().trim();
        String direccionStr = direccionTF.getText().trim();
        String telefonoStr = telefonoTF.getText().trim();
        String correoStr = correoTF.getText().trim();
        String coordenadaXStr = coordenadaXTF.getText().trim();
        String coordenadaYStr = coordenadaYTF.getText().trim();

        if (codigoStr.isBlank() || nombreStr.isBlank() || direccionStr.isBlank()
                || telefonoStr.isBlank() || correoStr.isBlank()
                || coordenadaXStr.isBlank() || coordenadaYStr.isBlank()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int codigo;
        try {
            codigo = Integer.parseInt(codigoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Se esperaba un numero entero en Código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Utils.esTelefonoValido(telefonoStr)) {
            JOptionPane.showMessageDialog(this, "Se esperaba un numero entero en Teléfono", "Error", JOptionPane.ERROR_MESSAGE);
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

        Cuartel cuartel = new Cuartel(nombreStr, direccionStr, coordenadaX, coordenadaY, telefonoStr, correoStr);
        if (cuartelData.guardarCuartel(cuartel)) {
            JOptionPane.showMessageDialog(this, "Cuartel guardado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el cuartel.\nQuizás ya haya un cuartel guardado con este código o nombre.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        String codigoStr = codigoTF.getText().trim();
        String nombreStr = nombreTF.getText().trim();

        if (codigoStr.isBlank() && nombreStr.isBlank()) {
            JOptionPane.showMessageDialog(this, "Debe especificar código o nombre del cuartel a eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int codigo = -1;
        try {
            codigo = Integer.parseInt(codigoStr);
        } catch (NumberFormatException e) {
        }

        boolean resultado = false;
        if (codigo != -1) {
            resultado = cuartelData.eliminarCuartel(codigo);
        } else {
            resultado = cuartelData.eliminarCuartelPorNombre(nombreStr);
        }
        if (resultado) {
            this.limpiarCampos();
            JOptionPane.showMessageDialog(this, "Cuartel dado de baja.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo dar de baja el cuartel.\nQuizás el cuartel ya haya sido dado de baja o no exista.", "Información", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSalirActionPerformed
        this.hide();
    }//GEN-LAST:event_BtnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBuscarCodigo;
    private javax.swing.JButton BtnBuscarNombre;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnLimpiar;
    private javax.swing.JButton BtnSalir;
    private javax.swing.JLabel codigoLabel;
    private javax.swing.JTextField codigoTF;
    private javax.swing.JLabel coordenadaXLabel;
    private javax.swing.JTextField coordenadaXTF;
    private javax.swing.JLabel coordenadaYLabel;
    private javax.swing.JTextField coordenadaYTF;
    private javax.swing.JLabel correoLabel;
    private javax.swing.JTextField correoTF;
    private javax.swing.JLabel direccionLabel;
    private javax.swing.JTextField direccionTF;
    private javax.swing.JLabel nombreLabel;
    private javax.swing.JTextField nombreTF;
    private javax.swing.JLabel telefonoLabel;
    private javax.swing.JTextField telefonoTF;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
