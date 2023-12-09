/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package lab1proyectofinal.vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import lab1proyectofinal.entidades.Cuartel;
import lab1proyectofinal.accesoADatos.CuartelData;
import lab1proyectofinal.accesoADatos.Utils;

/**
 *
 * @author nahue
 */
public class GestionCuartel extends javax.swing.JInternalFrame {

    private final CuartelData cuartelData;
    private Cuartel cuartel;
    private List<Cuartel> listaCuartel;
    private boolean enAgregacion;
    private boolean enModificacion;
    private boolean programaCambiandoJCBCuarteles;
    private String nombreRegEncontrado;
    private JLabel jLabAux = Utils.jLabConfigurado();

    public GestionCuartel(CuartelData cuartelData) {
        initComponents();
        this.cuartelData = cuartelData;
        configurarJCBCuarteles();
        modoPrevioABusqueda();
    }

    private void configurarJCBCuarteles() {
        jLabMensajeCuartel.setText("");
        programaCambiandoJCBCuarteles = true;
        jCBCuarteles.removeAllItems();
        listaCuartel = cuartelData.listarCuarteles();
        if (listaCuartel.isEmpty()) {
            jLabMensajeCuartel.setForeground(Color.BLACK);
            jLabMensajeCuartel.setText(
                    "<html>Advertencia: no hay cuarteles cargados en el sistema.</html>");
            programaCambiandoJCBCuarteles = false;
            return;
        }
        for (Cuartel cuar : listaCuartel) {
            jCBCuarteles.addItem(cuar);
        }
        programaCambiandoJCBCuarteles = false;
    }

    private void setEnabledDemasDatos(boolean b) {
        jTFCoorX.setEnabled(b);
        jTFCoorY.setEnabled(b);
        jTFCorreoElec.setEnabled(b);
        jTFDireccion.setEnabled(b);
        jTFTelefono.setEnabled(b);
    }

    private void setEditableDemasDatos(boolean b) {
        jTFCoorX.setEditable(b);
        jTFCoorY.setEditable(b);
        jTFCorreoElec.setEditable(b);
        jTFDireccion.setEditable(b);
        jTFTelefono.setEditable(b);
    }

    private void limpiarEntradasDeJPDemasDatos() {
        jTFDireccion.setText("");
        jTFTelefono.setText("");
        jTFCorreoElec.setText("");
        jTFCoorX.setText("");
        jTFCoorY.setText("");
    }

    private void borrarMensajesMenosEnJCBCuarteles() {
        jLabMensajeCoorX.setText("");
        jLabMensajeCoorY.setText("");
        jLabMensajeCorreoElec.setText("");
        jLabMensajeDemasDatos.setText("");
        jLabMensajeDireccion.setText("");
        jLabMensajeNombre.setText("");
        jLabMensajeTelefono.setText("");
    }

    private void borrarMensajesDeDatos() {
        jLabMensajeCoorX.setText("");
        jLabMensajeCoorY.setText("");
        jLabMensajeCorreoElec.setText("");
        jLabMensajeDireccion.setText("");
        jLabMensajeTelefono.setText("");
        jLabMensajeNombre.setText("");
    }

    private void borrarMensajesDeDemasDatos() {
        jLabMensajeCoorX.setText("");
        jLabMensajeCoorY.setText("");
        jLabMensajeCorreoElec.setText("");
        jLabMensajeDireccion.setText("");
        jLabMensajeTelefono.setText("");
    }

    private void modoPrevioABusqueda() {
        /* 
            Modo "previo a búsqueda", se aplica cuando:
        1) La primera vez que se utiliza este JInternalFrame 
        2) Inmediatamente luego de una operación llevada a cabo exitosamente
        3) Se cambia el contenido de "jTextFieldNombre", sin modificar un registro.
         */

        if (jCBCuarteles.getSelectedIndex() != -1) {
            programaCambiandoJCBCuarteles = true;
            jCBCuarteles.setSelectedIndex(-1);
            programaCambiandoJCBCuarteles = false;
        }

        limpiarEntradasDeJPDemasDatos();
        borrarMensajesMenosEnJCBCuarteles();

        jBAgregar.setEnabled(false);
        jBBuscar.setEnabled(true);
        jBCancelar.setEnabled(false);
        jBDarDeBaja.setEnabled(false);
        jBGuardar.setEnabled(false);
        jBLimpiar.setEnabled(false);
        jBModificar.setEnabled(false);
        jCBCuarteles.setEnabled(true);
        jTFNombre.setEnabled(true);
        setEnabledDemasDatos(false);

        jTFNombre.setEditable(true);
    }

    private void modoRegistroEncontrado() {
        /* 
            Modo "registro encontrado", se aplica cuando:
        1) Se encuentra un registro mediante "jComboBoxCuarteles" o "jButtonBuscar". 
        2) Se cancela la modificación de un registro.
         */

        if (jCBCuarteles.getSelectedIndex() == -1) {
            programaCambiandoJCBCuarteles = true;
            jCBCuarteles.setSelectedItem(cuartel);
            programaCambiandoJCBCuarteles = false;
        }

        borrarMensajesDeDemasDatos();

        jLabMensajeNombre.setForeground(Color.BLACK);
        jLabMensajeNombre.setText("<html>Hay un cuartel con este nombre entre los "
                + "registrados.</html>");
        jLabMensajeDemasDatos.setForeground(Color.BLUE);
        jLabMensajeDemasDatos.setText("<html>Puede modificar el cuartel encontrado haciendo "
                + "click en \"" + jBModificar.getText() + "\" o darle de baja haciendo click "
                + "en \"" + jBDarDeBaja.getText() + "\".</html>");

        jBAgregar.setEnabled(false);
        jBBuscar.setEnabled(true);
        jBCancelar.setEnabled(false);
        jBDarDeBaja.setEnabled(true);
        jBGuardar.setEnabled(false);
        jBLimpiar.setEnabled(false);
        jBModificar.setEnabled(true);
        jCBCuarteles.setEnabled(true);
        jTFNombre.setEnabled(true);
        setEnabledDemasDatos(true);

        jTFNombre.setEditable(true);
        setEditableDemasDatos(false);

        jTFNombre.setText(cuartel.getNombreCuartel());
        jTFDireccion.setText(cuartel.getDireccion());
        jTFTelefono.setText(cuartel.getTelefono());
        jTFCorreoElec.setText(cuartel.getCorreo());
        jTFCoorX.setText(String.valueOf(cuartel.getCoordenadaX()));
        jTFCoorY.setText(String.valueOf(cuartel.getCoordenadaY()));

        // necesario para compararlo con una hipotética modificación del contenido de 
        // "jTextFieldNombre", en caso de que se proceda a modificar el registro
        nombreRegEncontrado = jTFNombre.getText();
    }

    private void modoRegistroNoEncontrado() {
        /* 
            Modo "registro no encontrado", se aplica cuando: 
        1) Se ha clickeado en "jButtonBuscar" y el nombre ingresado no se corresponde con el de 
        ningún registro en la BD (ni activo ni inactivo).
        2) Se cancela la agregación de un registro.        
         */

        limpiarEntradasDeJPDemasDatos();
        borrarMensajesDeDemasDatos();

        jLabMensajeNombre.setForeground(Color.BLACK);
        jLabMensajeNombre.setText("<html>No existe un cuartel registrado con este "
                + "nombre.</html>");
        jLabMensajeDemasDatos.setForeground(Color.BLUE);
        jLabMensajeDemasDatos.setText("<html>Puede registrar un cuartel con el nombre ingresado "
                + "haciendo click en \"" + jBAgregar.getText() + "\" e ingresando los demás "
                + "datos.</html>");

        jBAgregar.setEnabled(true);
        jBBuscar.setEnabled(true);
        jBCancelar.setEnabled(false);
        jBDarDeBaja.setEnabled(false);
        jBGuardar.setEnabled(false);
        jBLimpiar.setEnabled(false);
        jBModificar.setEnabled(false);
        jCBCuarteles.setEnabled(true);
        jTFNombre.setEnabled(true);
        setEnabledDemasDatos(false);

        jTFNombre.setEditable(true);
    }

    private void modoOperacion() {
        /* 
            Modo "operación", se aplica cuando: 
        1) Se está agregando o modificando un registro (no aplica para la baja, dado que esta 
        última solo requiere del click en "jbDarDeBaja" y de la confirmación o declinación de 
        la solicitud de confirmación posterior).
         */

        borrarMensajesDeDatos();

        jBAgregar.setEnabled(false);
        jBBuscar.setEnabled(false);
        jBCancelar.setEnabled(true);
        jBDarDeBaja.setEnabled(false);
        jBGuardar.setEnabled(true);
        jBLimpiar.setEnabled(true);
        jBModificar.setEnabled(false);
        jCBCuarteles.setEnabled(false);
        jTFNombre.setEnabled(true);
        setEnabledDemasDatos(true);

        if (enAgregacion) {
            jTFNombre.setEditable(false);
        } else if (enModificacion) {
            jTFNombre.setEditable(true);
        }
        setEditableDemasDatos(true);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabNombre = new javax.swing.JLabel();
        jTFNombre = new javax.swing.JTextField();
        jBGuardar = new javax.swing.JButton();
        jBSalir = new javax.swing.JButton();
        jLabGestionCuarteles = new javax.swing.JLabel();
        jBAgregar = new javax.swing.JButton();
        jBModificar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jBDarDeBaja = new javax.swing.JButton();
        jLabMensajeNombre = new javax.swing.JLabel();
        jLabCuartel = new javax.swing.JLabel();
        jCBCuarteles = new javax.swing.JComboBox<>();
        jLabSelecCuartel = new javax.swing.JLabel();
        jLabBuscarCuartel = new javax.swing.JLabel();
        jLabDemasDatos = new javax.swing.JLabel();
        jBBuscar = new javax.swing.JButton();
        jLabMensajeDemasDatos = new javax.swing.JLabel();
        jLabMensajeCuartel = new javax.swing.JLabel();
        jPDemasDatos = new javax.swing.JPanel();
        jLabMensajeCoorY = new javax.swing.JLabel();
        jLabCoorX = new javax.swing.JLabel();
        jLabDireccion = new javax.swing.JLabel();
        jTFDireccion = new javax.swing.JTextField();
        jLabCorreoElec = new javax.swing.JLabel();
        jTFCoorY = new javax.swing.JTextField();
        jLabMensajeCorreoElec = new javax.swing.JLabel();
        jTFTelefono = new javax.swing.JTextField();
        jLabMensajeDireccion = new javax.swing.JLabel();
        jTFCorreoElec = new javax.swing.JTextField();
        jTFCoorX = new javax.swing.JTextField();
        jLabCoorY = new javax.swing.JLabel();
        jLabMensajeTelefono = new javax.swing.JLabel();
        jLabMensajeCoorX = new javax.swing.JLabel();
        jBLimpiar = new javax.swing.JButton();
        jLabTelefono = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1085, 560));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabNombre.setText("Nombre:");
        getContentPane().add(jLabNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 274, -1, -1));

        jTFNombre.setColumns(20);
        jTFNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTFNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTFNombreKeyTyped(evt);
            }
        });
        getContentPane().add(jTFNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 200, -1));

        jBGuardar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBGuardar.setText("Guardar");
        jBGuardar.setEnabled(false);
        jBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(jBGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 470, -1, -1));

        jBSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBSalir.setText("Salir");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });
        getContentPane().add(jBSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 470, -1, -1));

        jLabGestionCuarteles.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabGestionCuarteles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabGestionCuarteles.setText("Gestión de cuarteles");
        getContentPane().add(jLabGestionCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1070, -1));

        jBAgregar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBAgregar.setText("Agregar cuartel");
        jBAgregar.setEnabled(false);
        jBAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(jBAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, -1));

        jBModificar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBModificar.setText("Modificar cuartel");
        jBModificar.setEnabled(false);
        jBModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBModificarActionPerformed(evt);
            }
        });
        getContentPane().add(jBModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, -1, -1));

        jBCancelar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBCancelar.setText("Cancelar");
        jBCancelar.setEnabled(false);
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(jBCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 470, -1, -1));

        jBDarDeBaja.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBDarDeBaja.setText("Dar de baja cuartel");
        jBDarDeBaja.setEnabled(false);
        jBDarDeBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDarDeBajaActionPerformed(evt);
            }
        });
        getContentPane().add(jBDarDeBaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, -1, -1));

        jLabMensajeNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabMensajeNombre.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabMensajeNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 305, 340, 40));

        jLabCuartel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabCuartel.setText("Cuartel:");
        getContentPane().add(jLabCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 133, -1, -1));

        jCBCuarteles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jCBCuarteles.setMaximumRowCount(10);
        jCBCuarteles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBCuartelesActionPerformed(evt);
            }
        });
        getContentPane().add(jCBCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 200, -1));

        jLabSelecCuartel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabSelecCuartel.setText("Puede seleccionar un cuartel de entre los registrados:");
        getContentPane().add(jLabSelecCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jLabBuscarCuartel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabBuscarCuartel.setText("Puede ingresar un nombre y ver si está registrado como cuartel:");
        getContentPane().add(jLabBuscarCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabDemasDatos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabDemasDatos.setText("Demás datos del cuartel:");
        getContentPane().add(jLabDemasDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 90, -1, -1));

        jBBuscar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBBuscar.setText("Buscar");
        jBBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(jBBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 270, -1, -1));

        jLabMensajeDemasDatos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabMensajeDemasDatos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabMensajeDemasDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 410, 450, 55));

        jLabMensajeCuartel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabMensajeCuartel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabMensajeCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 165, 340, 40));

        jPDemasDatos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPDemasDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabMensajeCoorY.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jLabMensajeCoorY, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 295, 250, 23));

        jLabCoorX.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabCoorX.setText("Coordenada X:");
        jPDemasDatos.add(jLabCoorX, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 204, -1, -1));

        jLabDireccion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabDireccion.setText("Dirección:");
        jPDemasDatos.add(jLabDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 24, -1, -1));

        jTFDireccion.setEditable(false);
        jTFDireccion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTFDireccion.setEnabled(false);
        jPDemasDatos.add(jTFDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 200, -1));

        jLabCorreoElec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabCorreoElec.setText("Correo Electrónico:");
        jPDemasDatos.add(jLabCorreoElec, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 144, -1, -1));

        jTFCoorY.setEditable(false);
        jTFCoorY.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTFCoorY.setEnabled(false);
        jPDemasDatos.add(jTFCoorY, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, 75, -1));

        jLabMensajeCorreoElec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jLabMensajeCorreoElec, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 170, 250, 23));

        jTFTelefono.setEditable(false);
        jTFTelefono.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTFTelefono.setEnabled(false);
        jPDemasDatos.add(jTFTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 200, -1));

        jLabMensajeDireccion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jLabMensajeDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 50, 250, 23));

        jTFCorreoElec.setEditable(false);
        jTFCorreoElec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTFCorreoElec.setEnabled(false);
        jPDemasDatos.add(jTFCorreoElec, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 200, -1));

        jTFCoorX.setEditable(false);
        jTFCoorX.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTFCoorX.setEnabled(false);
        jPDemasDatos.add(jTFCoorX, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 75, -1));

        jLabCoorY.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabCoorY.setText("Coordenada Y:");
        jPDemasDatos.add(jLabCoorY, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 264, -1, -1));

        jLabMensajeTelefono.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jLabMensajeTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 110, 250, 23));

        jLabMensajeCoorX.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jLabMensajeCoorX, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 230, 250, 23));

        jBLimpiar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBLimpiar.setText("Limpiar campos");
        jBLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimpiarActionPerformed(evt);
            }
        });
        jPDemasDatos.add(jBLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, -1, -1));

        jLabTelefono.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabTelefono.setText("Teléfono:");
        jPDemasDatos.add(jLabTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 84, -1, -1));

        getContentPane().add(jPDemasDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 440, 330));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimpiarActionPerformed
        limpiarEntradasDeJPDemasDatos();
    }//GEN-LAST:event_jBLimpiarActionPerformed

    private void jBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarActionPerformed
        borrarMensajesDeDatos();

        boolean entradasValidas = true;

        String nombre = jTFNombre.getText().trim();
        if (enModificacion) {
            if (nombre.isBlank()) {
                entradasValidas = false;
                jLabMensajeNombre.setForeground(Color.RED);
                jLabMensajeNombre.setText("<html>El nombre no puede estar compuesto solo por "
                        + "espacios en blanco.</html>");
            } else if (cuartelData.estaNombreEntreInactivos(nombre)) {
                entradasValidas = false;
                jLabMensajeNombre.setForeground(Color.RED);
                jLabMensajeNombre.setText("<html>Este nombre se encuentra ocupado por un cuartel "
                        + "dado de baja y no puede ser utilizado por otro.</html>");
            } else {
                Cuartel cuartelBuscado = cuartelData.buscarCuartelPorNombre(nombre);
                if (cuartelBuscado != null) {
                    if (cuartelBuscado.getNombreCuartel().equals(nombreRegEncontrado)) {
                        cuartel.setNombreCuartel(nombre);
                    } else {
                        entradasValidas = false;
                        jLabMensajeNombre.setForeground(Color.RED);
                        jLabMensajeNombre.setText("<html>Este nombre se encuentra ocupado por un "
                                + "cuartel y no puede ser utilizado por otro.</html>");
                    }
                } else {
                    cuartel.setNombreCuartel(nombre);
                }
            }
        } else {
            cuartel.setNombreCuartel(nombre);
        }

        String direccion = jTFDireccion.getText();
        if (direccion.isBlank()) {
            entradasValidas = false;
            jLabMensajeDireccion.setForeground(Color.RED);
            jLabMensajeDireccion.setText("Debe completar este campo.");
        } else {
            cuartel.setDireccion(direccion);
        }

        String telefono = jTFTelefono.getText();
        if (telefono.isBlank()) {
            entradasValidas = false;
            jLabMensajeTelefono.setForeground(Color.RED);
            jLabMensajeTelefono.setText("Debe completar este campo.");
        } else if (!Utils.esTelefonoValido(telefono)) {
            entradasValidas = false;
            jLabMensajeTelefono.setForeground(Color.RED);
            jLabMensajeTelefono.setText("Debe ingresar un número de teléfono válido.");
        } else {
            cuartel.setTelefono(telefono);
        }

        String correoElec = jTFCorreoElec.getText();
        if (correoElec.isBlank()) {
            entradasValidas = false;
            jLabMensajeCorreoElec.setForeground(Color.RED);
            jLabMensajeCorreoElec.setText("Debe completar este campo.");
        } else {
            cuartel.setCorreo(correoElec);
        }

        try {
            int coorX = Integer.parseInt(jTFCoorX.getText());
            cuartel.setCoordenadaX(coorX);
        } catch (NumberFormatException e) {
            jLabMensajeCoorX.setForeground(Color.RED);
            jLabMensajeCoorX.setText("Debe ingresar un número entero.");
            entradasValidas = false;
        }

        try {
            int coorY = Integer.parseInt(jTFCoorY.getText());
            cuartel.setCoordenadaY(coorY);
        } catch (NumberFormatException e) {
            jLabMensajeCoorY.setForeground(Color.RED);
            jLabMensajeCoorY.setText("Debe ingresar un número entero.");
            entradasValidas = false;
        }

        if (entradasValidas && enAgregacion) {
            if (cuartelData.guardarCuartel(cuartel)) {
                jLabAux.setText("<html>Se registró el cuartel \""
                        + cuartel.getNombreCuartel() + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarJCBCuarteles();
                modoPrevioABusqueda();
                jTFNombre.setText("");
                enAgregacion = false;
            } else {
                jLabAux.setText("<html>No se pudo registrar el cuartel \""
                        + cuartel.getNombreCuartel() + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (entradasValidas && enModificacion) {
            if (cuartelData.modificarCuartel(cuartel)) {
                jLabAux.setText("<html>Se modificó el cuartel \"" + cuartel.getNombreCuartel()
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarJCBCuarteles();
                modoPrevioABusqueda();
                jTFNombre.setText("");
                enModificacion = false;
            } else {
                jLabAux.setText("<html>No se pudo modificar el cuartel \""
                        + cuartel.getNombreCuartel() + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBGuardarActionPerformed

    private void jBSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalirActionPerformed
        this.hide();
    }//GEN-LAST:event_jBSalirActionPerformed

    private void jBModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModificarActionPerformed
        enModificacion = true;
        modoOperacion();

        jLabMensajeNombre.setText("");
        jLabMensajeDemasDatos.setForeground(Color.BLUE);
        jLabMensajeDemasDatos.setText("<html>Modifique los datos que desee. También puede "
                + "modificar el nombre.</html>");
    }//GEN-LAST:event_jBModificarActionPerformed

    private void jCBCuartelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCuartelesActionPerformed
        cuartel = (Cuartel) jCBCuarteles.getSelectedItem();

        // evita que el programa entre en modo "registro encontrado" cada vez que se genera un 
        // actionEvent en "jCBCuarteles" sin la intervención del usuario y, además, el índice 
        // seleccionado en dicho componente es distinto de -1 (situación que ocurre al agregar el 
        // primer item a "jCBCuarteles" en "configurarJCBCuarteles")
        if (cuartel != null && programaCambiandoJCBCuarteles == false) {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jCBCuartelesActionPerformed

    private void jBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarActionPerformed
        String nombreCuartel = jTFNombre.getText().trim();

        if (nombreCuartel.isBlank()) {
            jLabMensajeDemasDatos.setText("");
            jLabMensajeNombre.setForeground(Color.RED);
            jLabMensajeNombre.setText("<html>El nombre no puede estar compuesto solo por "
                    + "espacios en blanco.</html>");
            return;
        }
        if (cuartelData.estaNombreEntreInactivos(nombreCuartel)) {
            jLabMensajeDemasDatos.setText("");
            jLabMensajeNombre.setForeground(Color.RED);
            jLabMensajeNombre.setText("<html>Este nombre ya se encuentra ocupado por un "
                    + "cuartel dado de baja. Por favor, ingrese otro.</html>");
            return;
        }

        cuartel = cuartelData.buscarCuartelPorNombre(nombreCuartel);
        if (cuartel != null) {
            modoRegistroEncontrado();
        } else {
            cuartel = new Cuartel();
            modoRegistroNoEncontrado();
        }
    }//GEN-LAST:event_jBBuscarActionPerformed

    private void jBAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarActionPerformed
        enAgregacion = true;
        modoOperacion();

        jLabMensajeNombre.setText("");
        jLabMensajeDemasDatos.setForeground(Color.BLUE);
        jLabMensajeDemasDatos.setText("<html>Complete los campos de los demás datos del "
                + "cuartel.</html>");
    }//GEN-LAST:event_jBAgregarActionPerformed

    private void jBDarDeBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDarDeBajaActionPerformed
        String nombreCuartel = cuartel.getNombreCuartel();

        jLabAux.setText("<html>¿Está seguro de querer dar de baja al cuartel \"" + nombreCuartel
                + "\"?</html>");
        if (JOptionPane.showConfirmDialog(this, jLabAux, "Advertencia", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            jLabAux.setText("Se dió de baja al cuartel \"" + nombreCuartel + "\".");
            if (cuartelData.eliminarCuartelPorNombre(nombreCuartel)) {
                JOptionPane.showMessageDialog(this, jLabAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarJCBCuarteles();
                jTFNombre.setText("");
                modoPrevioABusqueda();
            } else {
                jLabAux.setText("<html>No se pudo dar de baja al cuartel \"" + nombreCuartel
                        + "\". Asegurese de que no hay brigadas activas en este cuartel antes de "
                        + "intentar darle de baja.</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBDarDeBajaActionPerformed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        if (enAgregacion) {
            modoRegistroNoEncontrado();
        } else if (enModificacion) {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jTFNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFNombreKeyTyped
        if (!enModificacion) {
            modoPrevioABusqueda();
        }
    }//GEN-LAST:event_jTFNombreKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAgregar;
    private javax.swing.JButton jBBuscar;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBDarDeBaja;
    private javax.swing.JButton jBGuardar;
    private javax.swing.JButton jBLimpiar;
    private javax.swing.JButton jBModificar;
    private javax.swing.JButton jBSalir;
    private javax.swing.JComboBox<Cuartel> jCBCuarteles;
    private javax.swing.JLabel jLabBuscarCuartel;
    private javax.swing.JLabel jLabCoorX;
    private javax.swing.JLabel jLabCoorY;
    private javax.swing.JLabel jLabCorreoElec;
    private javax.swing.JLabel jLabCuartel;
    private javax.swing.JLabel jLabDemasDatos;
    private javax.swing.JLabel jLabDireccion;
    private javax.swing.JLabel jLabGestionCuarteles;
    private javax.swing.JLabel jLabMensajeCoorX;
    private javax.swing.JLabel jLabMensajeCoorY;
    private javax.swing.JLabel jLabMensajeCorreoElec;
    private javax.swing.JLabel jLabMensajeCuartel;
    private javax.swing.JLabel jLabMensajeDemasDatos;
    private javax.swing.JLabel jLabMensajeDireccion;
    private javax.swing.JLabel jLabMensajeNombre;
    private javax.swing.JLabel jLabMensajeTelefono;
    private javax.swing.JLabel jLabNombre;
    private javax.swing.JLabel jLabSelecCuartel;
    private javax.swing.JLabel jLabTelefono;
    private javax.swing.JPanel jPDemasDatos;
    private javax.swing.JTextField jTFCoorX;
    private javax.swing.JTextField jTFCoorY;
    private javax.swing.JTextField jTFCorreoElec;
    private javax.swing.JTextField jTFDireccion;
    private javax.swing.JTextField jTFNombre;
    private javax.swing.JTextField jTFTelefono;
    // End of variables declaration//GEN-END:variables
}
