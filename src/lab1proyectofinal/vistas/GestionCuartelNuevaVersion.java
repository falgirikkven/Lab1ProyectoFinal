/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package lab1proyectofinal.vistas;

import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import lab1proyectofinal.entidades.Cuartel;
import lab1proyectofinal.accesoADatos.CuartelData;
import lab1proyectofinal.accesoADatos.Utils;

/**
 *
 * @author nahue
 */
public class GestionCuartelNuevaVersion extends javax.swing.JInternalFrame {

    CuartelData cuartelData;
    Cuartel cuartel;
    List<Cuartel> listaCuartel;
    boolean flagAgregar;

    public GestionCuartelNuevaVersion(CuartelData cuartelData) {
        this.cuartelData = cuartelData;
        initComponents();
        modoPrevioABusqueda();
    }

    private void configurarComboBox() {
        jcbCuarteles.removeAllItems();
        listaCuartel = cuartelData.listarCuarteles();
        if (listaCuartel.isEmpty()) {
            jlMensajeCuartel.setForeground(Color.RED);
            jlMensajeCuartel.setText("Advertencia: no hay cuarteles cargados en el sistema");
            return;
        }
        for (Cuartel cuar : listaCuartel) {
            jcbCuarteles.addItem(cuar);
        }
        jcbCuarteles.setSelectedIndex(-1);
    }

    private void limpiarCamposDistintosDeNombre() {
        jtfDireccion.setText("");
        jtfTelefono.setText("");
        jtfCorreoElec.setText("");
        jtfCoorX.setText("");
        jtfCoorY.setText("");
    }

    private void borrarMensajes() {
        jlMensajeCoorX.setText("");
        jlMensajeCoorY.setText("");
        jlMensajeCorreoElec.setText("");
        jlMensajeCuartel.setText("");
        jlMensajeDemasDatos.setText("");
        jlMensajeDireccion.setText("");
        jlMensajeNombre.setText("");
        jlMensajeTelefono.setText("");
    }

    private void modoPrevioABusqueda() {
        /* 
            Modo "previo a búsqueda": situación inicial del la vista y posterior a una operación llevada a cabo
         */

        limpiarCamposDistintosDeNombre();
        borrarMensajes();
        configurarComboBox();

        // limpiar 'jtfNombre' 
        jtfNombre.setText("");

        // campos distintos de 'jtfNombre' inhabilitados
        jtfCoorX.setEnabled(false);
        jtfCoorY.setEnabled(false);
        jtfCorreoElec.setEnabled(false);
        jtfDireccion.setEnabled(false);
        jtfTelefono.setEnabled(false);

        // botones distintos de 'jbBuscar' inhabilitados
        jbAgregar.setEnabled(false);
        jbModificar.setEnabled(false);
        jbDarDeBaja.setEnabled(false);
        jbLimpiar.setEnabled(false);
        jbGuardar.setEnabled(false);
        jbCancelar.setEnabled(false);

        // habilitar 'jbBuscar' 
        jbBuscar.setEnabled(true);

        // volver editable 'jtfNombre' (nunca debería ser inhabilitado, en primer lugar)
        jtfNombre.setEditable(true);

        // habilitar 'jcbCuarteles'
        jcbCuarteles.setEnabled(true);
    }

    private void modoRegistroEncontrado() {
        /* 
            Modo "registro no encontrado": situación en la cual se ha clickeado en 'jbBuscar' y el nombre ingresado se corresponde con el
        de un registro activo en la BD
         */

        // se setea el texto de los jtf distintos de 'Nombre'
        jtfDireccion.setText(cuartel.getDireccion());
        jtfTelefono.setText(cuartel.getTelefono());
        jtfCorreoElec.setText(cuartel.getCorreo());
        jtfCoorX.setText(String.valueOf(cuartel.getCoordenadaX()));
        jtfCoorY.setText(String.valueOf(cuartel.getCoordenadaY()));

        // campos distintos de 'jtfNombre' habilitados pero ineditables
        jtfCoorX.setEnabled(true);
        jtfCoorY.setEnabled(true);
        jtfCorreoElec.setEnabled(true);
        jtfDireccion.setEnabled(true);
        jtfNombre.setEnabled(true);
        jtfTelefono.setEnabled(true);
        jtfCoorX.setEditable(false);
        jtfCoorY.setEditable(false);
        jtfCorreoElec.setEditable(false);
        jtfDireccion.setEditable(false);
        jtfNombre.setEditable(false);
        jtfTelefono.setEditable(false);

        // habilitar 'jbModificar' y 'jbDarDeBaja'
        jbModificar.setEnabled(true);
        jbDarDeBaja.setEnabled(true);

        // inhabilitar 'jbGuardar', 'jbCancelar' y 'jbLimpiar' (si se pasa del modo 'operación' al modo 'busqueda encontrado' mediante 'jbCancelar', entonces estas acciones se vuelven necesarias)
        jbGuardar.setEnabled(false);
        jbCancelar.setEnabled(false);
        jbLimpiar.setEnabled(false);
    }

    private void modoRegistroNoEncontrado() {
        /* 
            Modo "registro no encontrado": situación en la cual se ha clickeado en 'jbBuscar' y el nombre ingresado no se corresponde 
        con el de ningún registro en la BD (ni activo ni inactivo)
         */

        // campos distintos de 'jtfNombre' habilitados pero ineditables
//        jtfCoorX.setEnabled(true);    (potencialmente descartable)
//        jtfCoorY.setEnabled(true);
//        jtfCorreoElec.setEnabled(true);
//        jtfDireccion.setEnabled(true);
//        jtfNombre.setEnabled(true);
//        jtfTelefono.setEnabled(true);
//        jtfCoorX.setEditable(false);
//        jtfCoorY.setEditable(false);
//        jtfCorreoElec.setEditable(false);
//        jtfDireccion.setEditable(false);
//        jtfNombre.setEditable(false);
//        jtfTelefono.setEditable(false);
        // habilitar 'jbAgregar'
        jbAgregar.setEnabled(true);

        // inhabilitar 'jbGuardar', 'jbCancelar' y 'jbLimpiar' (si se pasa del modo 'operación' al modo 'busqueda encontrado' mediante 'jbCancelar', entonces estas acciones se vuelven necesarias)
        jbGuardar.setEnabled(false);
        jbCancelar.setEnabled(false);
        jbLimpiar.setEnabled(false);
    }

    private void modoOperacion() {
        /* 
            Modo "operación": situación en la cual se ha clickeado en 'jbAgregar' o 'jbModificar' (no aplica para 'jbDarDeBaja', 
        dado que la operación llevada a cabo por este último solo requiere del click en el mismo y de la confirmación o 
        declinación de la solicitud de confirmación posterior).
         */

        // campos distintos de 'jtfNombre' habilitados y editables (se los habilita para cubrir el caso de 'Agregar cuartel' aunque sea innecesario para 'Modificar cuartel')
        jtfCoorX.setEnabled(true);
        jtfCoorY.setEnabled(true);
        jtfCorreoElec.setEnabled(true);
        jtfDireccion.setEnabled(true);
        jtfNombre.setEnabled(true);
        jtfTelefono.setEnabled(true);
        jtfCoorX.setEditable(true);
        jtfCoorY.setEditable(true);
        jtfCorreoElec.setEditable(true);
        jtfDireccion.setEditable(true);
        jtfNombre.setEditable(true);
        jtfTelefono.setEditable(true);

        // campo 'jtfNombre' ineditables
        jtfNombre.setEditable(false);

        // habilitar 'jbGuardar' y 'jbCancelar'
        jbGuardar.setEnabled(true);
        jbCancelar.setEnabled(true);

        // inhabilitar 'jbBuscar', 'jbAgregar', 'jbModificar', 'jbDarDeBaja' y 'jcbCuarteles'
        jbBuscar.setEnabled(false);
        jbAgregar.setEnabled(false);
        jbModificar.setEnabled(false);
        jbDarDeBaja.setEnabled(false);
        jcbCuarteles.setEnabled(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtfCorreoElec = new javax.swing.JTextField();
        jlCoorX = new javax.swing.JLabel();
        jtfCoorX = new javax.swing.JTextField();
        jlCoorY = new javax.swing.JLabel();
        jlNombre = new javax.swing.JLabel();
        jtfCoorY = new javax.swing.JTextField();
        jtfNombre = new javax.swing.JTextField();
        jlDireccion = new javax.swing.JLabel();
        jbLimpiar = new javax.swing.JButton();
        jtfDireccion = new javax.swing.JTextField();
        jbGuardar = new javax.swing.JButton();
        jlTelefono = new javax.swing.JLabel();
        jbSalir = new javax.swing.JButton();
        jtfTelefono = new javax.swing.JTextField();
        jlCorreoElec = new javax.swing.JLabel();
        jlGestionCuarteles = new javax.swing.JLabel();
        jbAgregar = new javax.swing.JButton();
        jbModificar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jbDarDeBaja = new javax.swing.JButton();
        jlMensajeNombre = new javax.swing.JLabel();
        jlMensajeDireccion = new javax.swing.JLabel();
        jlMensajeTelefono = new javax.swing.JLabel();
        jlMensajeCorreoElec = new javax.swing.JLabel();
        jlMensajeCoorX = new javax.swing.JLabel();
        jlMensajeCoorY = new javax.swing.JLabel();
        jlCuartel = new javax.swing.JLabel();
        jcbCuarteles = new javax.swing.JComboBox<>();
        jlBuscarConCB = new javax.swing.JLabel();
        jlBuscarConTF = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jbBuscar = new javax.swing.JButton();
        jlMensajeDemasDatos = new javax.swing.JLabel();
        jlMensajeCuartel = new javax.swing.JLabel();

        jtfCorreoElec.setEditable(false);
        jtfCorreoElec.setEnabled(false);

        jlCoorX.setText("Coordenada X:");

        jtfCoorX.setEditable(false);
        jtfCoorX.setEnabled(false);

        jlCoorY.setText("Coordenada Y:");

        jlNombre.setText("Nombre:");

        jtfCoorY.setEditable(false);
        jtfCoorY.setEnabled(false);

        jtfNombre.setColumns(20);

        jlDireccion.setText("Dirección:");

        jbLimpiar.setText("Limpiar campos");
        jbLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimpiarActionPerformed(evt);
            }
        });

        jtfDireccion.setEditable(false);
        jtfDireccion.setEnabled(false);

        jbGuardar.setText("Guardar");
        jbGuardar.setEnabled(false);
        jbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarActionPerformed(evt);
            }
        });

        jlTelefono.setText("Teléfono:");

        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        jtfTelefono.setEditable(false);
        jtfTelefono.setEnabled(false);

        jlCorreoElec.setText("Correo Electrónico:");

        jlGestionCuarteles.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jlGestionCuarteles.setText("Gestion de cuarteles");

        jbAgregar.setText("Agregar cuartel");
        jbAgregar.setEnabled(false);
        jbAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAgregarActionPerformed(evt);
            }
        });

        jbModificar.setText("Modificar cuartel");
        jbModificar.setEnabled(false);
        jbModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbModificarActionPerformed(evt);
            }
        });

        jbCancelar.setText("Cancelar");
        jbCancelar.setEnabled(false);
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        jbDarDeBaja.setText("Dar de baja cuartel");
        jbDarDeBaja.setEnabled(false);
        jbDarDeBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDarDeBajaActionPerformed(evt);
            }
        });

        jlCuartel.setText("Cuartel:");

        jcbCuarteles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbCuartelesActionPerformed(evt);
            }
        });

        jlBuscarConCB.setText("Puede buscar un cuartel entre los que se encuentran registrados:");

        jlBuscarConTF.setText("Puede ingresar un nombre de cuartel y ver si está registrado:");

        jLabel9.setText("Demás datos del cuartel:");

        jbBuscar.setText("Buscar");
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbSalir))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlGestionCuarteles)
                                    .addComponent(jlBuscarConCB)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlBuscarConTF)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(jlMensajeDemasDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(146, 146, 146)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jlDireccion)
                                                    .addComponent(jlTelefono)
                                                    .addComponent(jlCorreoElec)
                                                    .addComponent(jlCoorX)
                                                    .addComponent(jlCoorY))
                                                .addGap(18, 18, 18))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jlNombre)
                                                .addGap(72, 72, 72)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jtfCoorX, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlMensajeCoorX, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlMensajeCoorY, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtfCorreoElec, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtfDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlMensajeDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlMensajeTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlMensajeCorreoElec, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jtfCoorY, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jbLimpiar))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jbBuscar))
                                            .addComponent(jlMensajeNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(jbAgregar)
                                        .addGap(18, 18, 18)
                                        .addComponent(jbModificar)
                                        .addGap(18, 18, 18)
                                        .addComponent(jbDarDeBaja))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(138, 138, 138)
                                        .addComponent(jbGuardar)
                                        .addGap(48, 48, 48)
                                        .addComponent(jbCancelar))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(178, 178, 178)
                                .addComponent(jlCuartel)
                                .addGap(75, 75, 75)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbCuarteles, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlMensajeCuartel, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 48, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlGestionCuarteles)
                .addGap(21, 21, 21)
                .addComponent(jlBuscarConCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbCuarteles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlCuartel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlMensajeCuartel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jlBuscarConTF)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlNombre)
                    .addComponent(jbBuscar))
                .addGap(3, 3, 3)
                .addComponent(jlMensajeNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jlMensajeDemasDatos, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlDireccion)
                    .addComponent(jtfDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jlMensajeDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlTelefono)
                    .addComponent(jtfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jlMensajeTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCorreoElec)
                    .addComponent(jtfCorreoElec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jlMensajeCorreoElec, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCoorX)
                    .addComponent(jtfCoorX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jlMensajeCoorX, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCoorY)
                    .addComponent(jtfCoorY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbLimpiar))
                .addGap(3, 3, 3)
                .addComponent(jlMensajeCoorY, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbGuardar)
                    .addComponent(jbCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAgregar)
                    .addComponent(jbModificar)
                    .addComponent(jbDarDeBaja))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbSalir)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimpiarActionPerformed
        limpiarCamposDistintosDeNombre();
    }//GEN-LAST:event_jbLimpiarActionPerformed

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarActionPerformed

        // si no se encontró un cuartel
        if (cuartel == null) {
            cuartel = new Cuartel();
        }

        // obtención y validación de entradas
        boolean entradasValidas = true;

        String nombre = jtfNombre.getText();
        cuartel.setNombreCuartel(nombre);

        String direccion = jtfDireccion.getText();
        if (direccion.isBlank()) {
            entradasValidas = false;
            jlMensajeDireccion.setForeground(Color.RED);
            jlMensajeDireccion.setText("Debe completar este campo");
        } else {
            cuartel.setDireccion(direccion);
        }

        String telefono = jtfTelefono.getText();
        if (telefono.isBlank()) {
            entradasValidas = false;
            jlMensajeTelefono.setForeground(Color.RED);
            jlMensajeTelefono.setText("Debe completar este campo");
        } else if (!Utils.esTelefonoValido(telefono)) {
            entradasValidas = false;
            jlMensajeTelefono.setForeground(Color.RED);
            jlMensajeTelefono.setText("Debe ingresar un número de teléfono válido");
        } else {
            cuartel.setTelefono(telefono);
        }

        String correoElec = jtfCorreoElec.getText();
        if (correoElec.isBlank()) {
            entradasValidas = false;
            jlMensajeCorreoElec.setForeground(Color.RED);
            jlMensajeCorreoElec.setText("Debe completar este campo");
        } else {
            cuartel.setCorreo(correoElec);
        }

        try {
            int coorX = Integer.parseInt(jtfCoorX.getText());
            cuartel.setCoordenadaX(coorX);
        } catch (NumberFormatException e) {
            jlMensajeCoorX.setForeground(Color.RED);
            jlMensajeCoorX.setText("Debe ingresar un número entero");
            entradasValidas = false;
        }

        try {
            int coorY = Integer.parseInt(jtfCoorY.getText());
            cuartel.setCoordenadaY(coorY);
        } catch (NumberFormatException e) {
            jlMensajeCoorY.setForeground(Color.RED);
            jlMensajeCoorY.setText("Debe ingresar un número entero");
            entradasValidas = false;
        }

        if (entradasValidas && flagAgregar) {       // si se está agregando un cuartel y las entradas son válidas
            if (cuartelData.guardarCuartel(cuartel)) {
                JOptionPane.showMessageDialog(this, "Se ha agregado el cuartel a los registros", "Información", JOptionPane.INFORMATION_MESSAGE);
                modoPrevioABusqueda();
            } else {
                JOptionPane.showMessageDialog(this, "No se ha podido agregar el cuartel a los registros", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (entradasValidas && !flagAgregar) {   // si se está modificando un cuartel y las entradas son válidas
            if (cuartelData.modificarCuartel(cuartel)) {
                JOptionPane.showMessageDialog(this, "Se ha modificado el cuartel", "Información", JOptionPane.INFORMATION_MESSAGE);
                modoPrevioABusqueda();
            } else {
                JOptionPane.showMessageDialog(this, "No se ha podido modificar el cuartel", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jbGuardarActionPerformed

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        this.hide();
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jbModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbModificarActionPerformed

        modoOperacion();

        // limpiar 'jlMensajeCuartel' y 'jlMensajeNombre' 
        jlMensajeCuartel.setText("");
        jlMensajeNombre.setText("");

        // cambiar mensaje en 'jlMensajeDemasDatos' 
        jlMensajeDemasDatos.setForeground(Color.BLUE);
        jlMensajeDemasDatos.setText("Modifique los campos que desee");

        // se establece 'flagAgregar' 
        flagAgregar = false;
    }//GEN-LAST:event_jbModificarActionPerformed

    private void jcbCuartelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCuartelesActionPerformed
        if (jcbCuarteles.getSelectedIndex() == -1) {
            limpiarCamposDistintosDeNombre();
            return;
        }
        Cuartel cuartelSeleccionado = (Cuartel) jcbCuarteles.getSelectedItem();
        if (cuartelSeleccionado != null) {
            jtfNombre.setText(cuartelSeleccionado.getNombreCuartel());
            jtfDireccion.setText(cuartelSeleccionado.getDireccion());
            jtfTelefono.setText(cuartelSeleccionado.getTelefono());
            jtfCorreoElec.setText(cuartelSeleccionado.getCorreo());
            jtfCoorX.setText(Integer.toString(cuartelSeleccionado.getCoordenadaX()));
            jtfCoorY.setText(Integer.toString(cuartelSeleccionado.getCoordenadaY()));

        }
    }//GEN-LAST:event_jcbCuartelesActionPerformed

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed

        // setear el índice de 'jcbCuarteles' en -1 (OJO)
        jcbCuarteles.setSelectedIndex(-1);

        String nombreCuartel = jtfNombre.getText();

        // si no se ingresó nada que no sean espacios en blanco
        if (nombreCuartel.isBlank()) {
            jlMensajeNombre.setForeground(Color.RED);
            jlMensajeNombre.setText("Debe ingresar un nombre válido");
            return;
        }

        if (cuartelData.buscarNombreEnRegistrosElim(nombreCuartel)) {
            jlMensajeNombre.setForeground(Color.RED);
            jlMensajeNombre.setText("<html>Este nombre ya se encuentra ocupado por un registro eliminado. Por favor, ingrese otro</html>");
            return;
        }

        cuartel = cuartelData.buscarCuartelPorNombre(nombreCuartel);
        if (cuartel != null) {  // si se encontró un cuartel

            // se limpia 'jlDemasDatos' (potencialmente innecesario)
//            jlMensajeDemasDatos.setText("");
            // se advierte al usuario mediante un label
            jlMensajeNombre.setForeground(Color.BLACK);
            jlMensajeNombre.setText("Hay un cuartel con ese nombre entre los registrados");
            jlMensajeDemasDatos.setForeground(Color.BLUE);
            jlMensajeDemasDatos.setText("<html>Puede modificar el cuartel encontrado haciendo click en '" + jbModificar.getText() + "' o eliminarlo haciendo click en '" + jbDarDeBaja.getText() + "'</html>");   // el html se utiliza para poder escribir más de una línea en el label    

            modoRegistroEncontrado();

        } else {    // si no se encontró un cuartel

            // se advierte al usuario mediante label's
            jlMensajeNombre.setForeground(Color.BLACK);
            jlMensajeNombre.setText("No existe un cuartel registrado con este nombre");
            jlMensajeDemasDatos.setForeground(Color.BLUE);
            jlMensajeDemasDatos.setText("<html>Puede registrar un cuartel con el nombre ingresado haciendo click en '" + jbAgregar.getText() + "' y completando los campos de abajo</html>");   // el html se utiliza para poder escribir más de una línea en el label    

            modoRegistroNoEncontrado();
        }
    }//GEN-LAST:event_jbBuscarActionPerformed

    private void jbAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAgregarActionPerformed

        modoOperacion();

        // limpiar 'jlMensajeCuartel' y 'jlMensajeNombre' 
        jlMensajeCuartel.setText("");
        jlMensajeNombre.setText("");

        // cambiar mensaje en 'jlMensajeDemasDatos' 
        jlMensajeDemasDatos.setForeground(Color.BLUE);
        jlMensajeDemasDatos.setText("Complete todos los campos de abajo");

        // se establece 'flagAgregar' 
        flagAgregar = true;
    }//GEN-LAST:event_jbAgregarActionPerformed

    private void jbDarDeBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDarDeBajaActionPerformed
        String nombreCuartel = cuartel.getNombreCuartel();
        if (JOptionPane.showConfirmDialog(this, "¿Está seguro de querer dar de baja al cuartel '" + nombreCuartel + "'?", "Advertencia", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            cuartelData.eliminarCuartelPorNombre(nombreCuartel);
        }
    }//GEN-LAST:event_jbDarDeBajaActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        if (flagAgregar) {
            modoRegistroNoEncontrado();
        } else {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jbCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton jbAgregar;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbDarDeBaja;
    private javax.swing.JButton jbGuardar;
    private javax.swing.JButton jbLimpiar;
    private javax.swing.JButton jbModificar;
    private javax.swing.JButton jbSalir;
    private javax.swing.JComboBox<Cuartel> jcbCuarteles;
    private javax.swing.JLabel jlBuscarConCB;
    private javax.swing.JLabel jlBuscarConTF;
    private javax.swing.JLabel jlCoorX;
    private javax.swing.JLabel jlCoorY;
    private javax.swing.JLabel jlCorreoElec;
    private javax.swing.JLabel jlCuartel;
    private javax.swing.JLabel jlDireccion;
    private javax.swing.JLabel jlGestionCuarteles;
    private javax.swing.JLabel jlMensajeCoorX;
    private javax.swing.JLabel jlMensajeCoorY;
    private javax.swing.JLabel jlMensajeCorreoElec;
    private javax.swing.JLabel jlMensajeCuartel;
    private javax.swing.JLabel jlMensajeDemasDatos;
    private javax.swing.JLabel jlMensajeDireccion;
    private javax.swing.JLabel jlMensajeNombre;
    private javax.swing.JLabel jlMensajeTelefono;
    private javax.swing.JLabel jlNombre;
    private javax.swing.JLabel jlTelefono;
    private javax.swing.JTextField jtfCoorX;
    private javax.swing.JTextField jtfCoorY;
    private javax.swing.JTextField jtfCorreoElec;
    private javax.swing.JTextField jtfDireccion;
    private javax.swing.JTextField jtfNombre;
    private javax.swing.JTextField jtfTelefono;
    // End of variables declaration//GEN-END:variables
}
