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
public class GestionCuartelNV extends javax.swing.JInternalFrame {

    private final CuartelData cuartelData;
    private Cuartel cuartel;
    private List<Cuartel> listaCuartel;
    private boolean flagAgregar;

    public GestionCuartelNV(CuartelData cuartelData) {        
        initComponents();
        this.cuartelData = cuartelData;
        configurarComboBox();
        modoPrevioABusqueda();
    }

    private void configurarComboBox() {
        jlMensajeCuartel.setForeground(Color.BLACK);
        jlMensajeCuartel.setText("");
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
//        jcbCuarteles.setSelectedIndex(-1);
        jtfNombre.setText("");      // limpia el contenido que queda luego de la ejecución de 'jcbCuartelesActionPerformed' al agregar el primer item 'jcbCuarteles' (lo cual cambia el item seleccionado del mismo y, por tanto, provoca la ejecución del método antes mencionado)
    }

    private void limpiarCamposDistintosDeNombre() {
        jtfDireccion.setText("");
        jtfTelefono.setText("");
        jtfCorreoElec.setText("");
        jtfCoorX.setText("");
        jtfCoorY.setText("");
    }

    private void borrarMensajesMenosEnJCB() {
        jlMensajeCoorX.setText("");
        jlMensajeCoorY.setText("");
        jlMensajeCorreoElec.setText("");
        jlMensajeDemasDatos.setText("");
        jlMensajeDireccion.setText("");
        jlMensajeNombre.setText("");
        jlMensajeTelefono.setText("");
    }

    private void borrarMensajesDeDemasCampos() {
        jlMensajeCoorX.setText("");
        jlMensajeCoorY.setText("");
        jlMensajeCorreoElec.setText("");
        jlMensajeDireccion.setText("");
        jlMensajeTelefono.setText("");
    }

    private void modoPrevioABusqueda() {
        /* 
            Modo "previo a búsqueda": se aplica en la situación inicial de la vista y posterior a una 
        operación llevada a cabo exitosamente. También se aplica cada vez que se cambia el contenido
        de 'jtfNombre'.
         */

        if (jcbCuarteles.getSelectedIndex() != -1) {
            jcbCuarteles.setSelectedIndex(-1);  // cuando se tipea en 'jtfNombre' se ejecuta 'modoPrevioABusqueda()', por lo que, de haber tenido un item seleccionado en 'jcbCuarteles', al cambiar el contenido de 'jtfNombre' es conveniente deseleccionar el item, para evitar confusiones 
        }
        limpiarCamposDistintosDeNombre();
        borrarMensajesMenosEnJCB();

        // inhabilitar campos distintos de 'jtfNombre'
        jtfCoorX.setEnabled(false);
        jtfCoorY.setEnabled(false);
        jtfCorreoElec.setEnabled(false);
        jtfDireccion.setEnabled(false);
        jtfTelefono.setEnabled(false);

        // inhabilitar botones distintos de 'jbBuscar' 
        jbAgregar.setEnabled(false);
        jbModificar.setEnabled(false);
        jbDarDeBaja.setEnabled(false);
        jbLimpiar.setEnabled(false);
        jbGuardar.setEnabled(false);
        jbCancelar.setEnabled(false);

        jcbCuarteles.setEnabled(true);
        jbBuscar.setEnabled(true);

        jtfNombre.setEditable(true);        // necesario para: el caso en el que se culmina la agregación exitosa de un registro (operación que vuelve ineditable a 'jtfNombre') y se regresa al modo "previo a búsqueda"

    }

    private void modoRegistroEncontrado() {
        /* 
            Modo "registro encontrado": se aplica en la situación en la cual se muestran los datos 
        de un registro existente en la BD, indistintamente de si se llegó a dicho registro a través de 
        'jcbCuarteles' o 'jbBuscar'. También se aplica cuando se cancela la modificación de un registro.
         */

        borrarMensajesDeDemasCampos();  // necesario para: el caso en el que en el modo "operación" habían mensajes indicando que faltaba completar uno o más campos y, sin que dicho campos hayan desparecido, se cancela la operación y regresa al modo "registro encontrado"

//        jtfNombre.setEditable(true);    
        // indicaciones para el usuario
        jlMensajeNombre.setForeground(Color.BLACK);
        jlMensajeNombre.setText("Hay un cuartel con este nombre entre los registrados");
        jlMensajeDemasDatos.setForeground(Color.BLUE);
        jlMensajeDemasDatos.setText("<html>Puede modificar el cuartel encontrado haciendo click en '" + jbModificar.getText() + "' o eliminarlo haciendo click en '" + jbDarDeBaja.getText() + "'</html>");   // el html se utiliza para poder escribir más de una línea en el label    

        // se establece el texto de los jtf distintos de 'jtfNombre'
        jtfNombre.setText(cuartel.getNombreCuartel());
        jtfDireccion.setText(cuartel.getDireccion());
        jtfTelefono.setText(cuartel.getTelefono());
        jtfCorreoElec.setText(cuartel.getCorreo());
        jtfCoorX.setText(String.valueOf(cuartel.getCoordenadaX()));
        jtfCoorY.setText(String.valueOf(cuartel.getCoordenadaY()));

        // habilitar y volver ineditables aquellos campos distintos de 'jtfNombre'
        jtfCoorX.setEnabled(true);
        jtfCoorY.setEnabled(true);
        jtfCorreoElec.setEnabled(true);
        jtfDireccion.setEnabled(true);
        jtfTelefono.setEnabled(true);
        jtfCoorX.setEditable(false);
        jtfCoorY.setEditable(false);
        jtfCorreoElec.setEditable(false);
        jtfDireccion.setEditable(false);
        jtfTelefono.setEditable(false);

        // habilitar, necesario para (entre otras cosas): el caso en el que se cancela la modificación de un registro y se regresa al modo "registro encontrado"         
        jbModificar.setEnabled(true);
        jbDarDeBaja.setEnabled(true);
        jbBuscar.setEnabled(true);
        jcbCuarteles.setEnabled(true);

        // inhabilitar
        jbAgregar.setEnabled(false);
        // necesario para: el caso en el que se cancela la modificación de un registro y se regresa al modo "registro encontrado" 
        jbGuardar.setEnabled(false);
        jbCancelar.setEnabled(false);
        jbLimpiar.setEnabled(false);

    }

    private void modoRegistroNoEncontrado() {
        /* 
            Modo "registro no encontrado": situación en la cual se ha clickeado en 'jbBuscar' y 
        el nombre ingresado no se corresponde con el de ningún registro en la BD (ni activo ni inactivo).
        También se aplica cuando se cancela la agregación de un registro.        
         */

        limpiarCamposDistintosDeNombre();
        borrarMensajesDeDemasCampos();      // necesario para: el caso en el que en el modo "operación" habían mensajes indicando que faltaba completar uno o más campos y, sin que dicho campos hayan desparecido, se cancela la operación y regresa al modo "registro no encontrado"

        // indicaciones al usuario
        jlMensajeNombre.setForeground(Color.BLACK);
        jlMensajeNombre.setText("No existe un cuartel registrado con este nombre");
        jlMensajeDemasDatos.setForeground(Color.BLUE);
        jlMensajeDemasDatos.setText("<html>Puede registrar un cuartel con el nombre ingresado haciendo click en '" + jbAgregar.getText() + "' y completando los campos de abajo</html>");   // el html se utiliza para poder escribir más de una línea en el label    

        jtfNombre.setEditable(true);    // necesario para: el caso en el que se cancela la agregación de un registro (operación que vuelve ineditable a 'jtfNombre') y se regresa al modo "registro no encontrado"

        // inhabilitar campos distintos de 'jtfNombre'
        jtfCoorX.setEnabled(false);
        jtfCoorY.setEnabled(false);
        jtfCorreoElec.setEnabled(false);
        jtfDireccion.setEnabled(false);
        jtfTelefono.setEnabled(false);

        // inhabilitar 
        jbModificar.setEnabled(false);
        jbDarDeBaja.setEnabled(false);
        // necesario para: el caso en el que se cancela la agregación de un registro y se regresa al modo "registro no encontrado" 
        jbGuardar.setEnabled(false);
        jbCancelar.setEnabled(false);
        jbLimpiar.setEnabled(false);

        // habilitar, necesario para (entre otras cosas): el caso en el que se cancela la agregación de un registro y se regresa al modo "registro no encontrado" 
        jbAgregar.setEnabled(true);
        jbBuscar.setEnabled(true);
        jcbCuarteles.setEnabled(true);
    }

    private void modoOperacion() {
        /* 
            Modo "operación": se aplica en la situación en la cual se está agregando o modificando 
        registro (no aplica para la baja, dado que esta última solo requiere del click en 'jbDarDeBaja' 
        y de la confirmación o declinación de la solicitud de confirmación posterior).
         */

        // habilitar campos, necesario para la agregación de un registro, pues en el modo "registro no encontrado" estos campos estaban inhabilitados
        jtfCoorX.setEnabled(true);
        jtfCoorY.setEnabled(true);
        jtfCorreoElec.setEnabled(true);
        jtfDireccion.setEnabled(true);
        jtfNombre.setEnabled(true);     // necesario para poder modificar un registro
        jtfTelefono.setEnabled(true);

        // volver editables los capos disntintos de 'jtfNombre', necesario para: 1) La modificación de un registro, pues en el modo "registro encontrado" estos campos eran no editables, 2) La agregación de un registro, en caso de que, antes de la agregación se haya llegado al modo "previo a búsqueda" mediante una operación de modificación o baja (ambas vuelven ineditable estos campos)
        jtfCoorX.setEditable(true);
        jtfCoorY.setEditable(true);
        jtfCorreoElec.setEditable(true);
        jtfDireccion.setEditable(true);
        jtfTelefono.setEditable(true);

        if (flagAgregar) {
            jtfNombre.setEditable(false);
        } else {
            jtfNombre.setEditable(true);    // necesario para poder modificar un registro
        }

        // habilitar 
        jbGuardar.setEnabled(true);
        jbCancelar.setEnabled(true);
        jbLimpiar.setEnabled(true);

        // inhabilitar 
        jbBuscar.setEnabled(false);
        jcbCuarteles.setEnabled(false);
        jbAgregar.setEnabled(false);
        jbModificar.setEnabled(false);
        jbDarDeBaja.setEnabled(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlNombre = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        jbGuardar = new javax.swing.JButton();
        jbSalir = new javax.swing.JButton();
        jlGestionCuarteles = new javax.swing.JLabel();
        jbAgregar = new javax.swing.JButton();
        jbModificar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jbDarDeBaja = new javax.swing.JButton();
        jlMensajeNombre = new javax.swing.JLabel();
        jlCuartel = new javax.swing.JLabel();
        jcbCuarteles = new javax.swing.JComboBox<>();
        jlBuscarConCB = new javax.swing.JLabel();
        jlBuscarConTF = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jbBuscar = new javax.swing.JButton();
        jlMensajeDemasDatos = new javax.swing.JLabel();
        jlMensajeCuartel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jlMensajeCoorY = new javax.swing.JLabel();
        jlCoorX = new javax.swing.JLabel();
        jlDireccion = new javax.swing.JLabel();
        jtfDireccion = new javax.swing.JTextField();
        jlCorreoElec = new javax.swing.JLabel();
        jtfCoorY = new javax.swing.JTextField();
        jlMensajeCorreoElec = new javax.swing.JLabel();
        jtfTelefono = new javax.swing.JTextField();
        jlMensajeDireccion = new javax.swing.JLabel();
        jtfCorreoElec = new javax.swing.JTextField();
        jtfCoorX = new javax.swing.JTextField();
        jlCoorY = new javax.swing.JLabel();
        jlMensajeTelefono = new javax.swing.JLabel();
        jlMensajeCoorX = new javax.swing.JLabel();
        jbLimpiar = new javax.swing.JButton();
        jlTelefono = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(555, 750));

        jlNombre.setText("Nombre:");

        jtfNombre.setColumns(20);
        jtfNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfNombreKeyTyped(evt);
            }
        });

        jbGuardar.setText("Guardar");
        jbGuardar.setEnabled(false);
        jbGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGuardarActionPerformed(evt);
            }
        });

        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        jlGestionCuarteles.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jlGestionCuarteles.setText("Gestión de cuarteles");

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

        jcbCuarteles.setMaximumRowCount(10);
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

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jlCoorX.setText("Coordenada X:");

        jlDireccion.setText("Dirección:");

        jtfDireccion.setEditable(false);
        jtfDireccion.setEnabled(false);

        jlCorreoElec.setText("Correo Electrónico:");

        jtfCoorY.setEditable(false);
        jtfCoorY.setEnabled(false);

        jtfTelefono.setEditable(false);
        jtfTelefono.setEnabled(false);

        jtfCorreoElec.setEditable(false);
        jtfCorreoElec.setEnabled(false);

        jtfCoorX.setEditable(false);
        jtfCoorX.setEnabled(false);

        jlCoorY.setText("Coordenada Y:");

        jbLimpiar.setText("Limpiar campos");
        jbLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimpiarActionPerformed(evt);
            }
        });

        jlTelefono.setText("Teléfono:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlDireccion)
                    .addComponent(jlTelefono)
                    .addComponent(jlCorreoElec)
                    .addComponent(jlCoorX)
                    .addComponent(jlCoorY))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfCoorX, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlMensajeCoorX, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlMensajeCoorY, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfCorreoElec, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlMensajeDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlMensajeTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlMensajeCorreoElec, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jtfCoorY, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addComponent(jbLimpiar)
                        .addGap(22, 22, 22))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlDireccion)
                    .addComponent(jtfDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jlMensajeDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlTelefono)
                    .addComponent(jtfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jlMensajeTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCorreoElec)
                    .addComponent(jtfCorreoElec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jlMensajeCorreoElec, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCoorX)
                    .addComponent(jtfCoorX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jlMensajeCoorX, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCoorY)
                    .addComponent(jtfCoorY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbLimpiar))
                .addGap(3, 3, 3)
                .addComponent(jlMensajeCoorY, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlBuscarConTF)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(jlMensajeDemasDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jbAgregar)
                                                .addGap(18, 18, 18)
                                                .addComponent(jbModificar)
                                                .addGap(18, 18, 18)
                                                .addComponent(jbDarDeBaja))))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jlCuartel)
                                        .addGap(75, 75, 75)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jcbCuarteles, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlMensajeCuartel, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jlNombre)
                                        .addGap(72, 72, 72)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jbBuscar))
                                            .addComponent(jlMensajeNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlGestionCuarteles)
                                    .addComponent(jlBuscarConCB))))
                        .addGap(0, 39, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jbGuardar)
                .addGap(48, 48, 48)
                .addComponent(jbCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jlMensajeNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlMensajeDemasDatos, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 20, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(14, 14, 14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbGuardar)
                    .addComponent(jbCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAgregar)
                    .addComponent(jbModificar)
                    .addComponent(jbDarDeBaja))
                .addGap(11, 11, 11)
                .addComponent(jbSalir)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimpiarActionPerformed
        limpiarCamposDistintosDeNombre();
    }//GEN-LAST:event_jbLimpiarActionPerformed

    private void jbGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGuardarActionPerformed

        // si en la búsqueda no se encontró ningún cuartel
        if (cuartel == null) {
            cuartel = new Cuartel();
        }

        // se limpian posibles mensajes de una operación fallida
        borrarMensajesDeDemasCampos();

        // obtención y validación de entradas
        boolean entradasValidas = true;

        String nombre = jtfNombre.getText();
        if (nombre.isBlank()) {
            entradasValidas = false;
            jlMensajeNombre.setForeground(Color.RED);
            jlMensajeNombre.setText("Debe ingresar un nombre válido");
        } else if (cuartelData.buscarNombreEntreInactivos(nombre)) {
            entradasValidas = false;
            jlMensajeNombre.setForeground(Color.RED);
            jlMensajeNombre.setText("<html>Este nombre ya se encuentra ocupado por un registro eliminado. Por favor, ingrese otro</html>");
        } else if (cuartelData.buscarCuartelPorNombre(nombre) != null) {
            entradasValidas = false;
            jlMensajeNombre.setForeground(Color.RED);
            jlMensajeNombre.setText("<html>Este nombre ya se encuentra ocupado por un registro. Por favor, ingrese otro</html>");
        } else {
            cuartel.setNombreCuartel(nombre);
        }

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

        if (entradasValidas && flagAgregar) {       // si las entradas son válidas y se está agregando un cuartel 
            if (cuartelData.guardarCuartel(cuartel)) {
                JOptionPane.showMessageDialog(this, "Se ha agregado el cuartel a los registros", "Información", JOptionPane.INFORMATION_MESSAGE);
                configurarComboBox();
                modoPrevioABusqueda();
            } else {
                JOptionPane.showMessageDialog(this, "No se ha podido agregar el cuartel a los registros", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (entradasValidas && !flagAgregar) {   // si las entradas son válidas y se está modificando un cuartel 
            if (cuartelData.modificarCuartel(cuartel)) {
                JOptionPane.showMessageDialog(this, "Se ha modificado el cuartel", "Información", JOptionPane.INFORMATION_MESSAGE);
                configurarComboBox();
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

        jlMensajeNombre.setText("");

        jlMensajeDemasDatos.setForeground(Color.BLUE);
        jlMensajeDemasDatos.setText("<html>Modifique los campos que desee. También puede modificar el nombre de arriba.</html>");

        flagAgregar = false;
    }//GEN-LAST:event_jbModificarActionPerformed

    private void jcbCuartelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbCuartelesActionPerformed
//        if (jcbCuarteles.getSelectedIndex() == -1) {
//            limpiarCamposDistintosDeNombre();
//            return;
//        }
//        cuartel = (Cuartel) jcbCuarteles.getSelectedItem();
//        if (cuartel != null) {
//            modoRegistroEncontrado();
//        }

        cuartel = (Cuartel) jcbCuarteles.getSelectedItem();
        if (cuartel != null) {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jcbCuartelesActionPerformed

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed

        // establecer el índice de 'jcbCuarteles' en -1
//        jcbCuarteles.setSelectedIndex(-1);
        String nombreCuartel = jtfNombre.getText();

        if (nombreCuartel.isBlank()) {
            jlMensajeDemasDatos.setText("");  // necesario para: evitar que quede un mensaje ahí en el caso en que se busca algo y luego se vuelve a buscar
            jlMensajeNombre.setForeground(Color.RED);
            jlMensajeNombre.setText("Debe ingresar un nombre válido");
            return;
        }

        if (cuartelData.buscarNombreEntreInactivos(nombreCuartel)) {
            jlMensajeDemasDatos.setText("");
            jlMensajeNombre.setForeground(Color.RED);
            jlMensajeNombre.setText("<html>Este nombre ya se encuentra ocupado por un registro eliminado. Por favor, ingrese otro</html>");
            return;
        }

        cuartel = cuartelData.buscarCuartelPorNombre(nombreCuartel);
        if (cuartel != null) {

            // se limpia 'jlDemasDatos' (potencialmente innecesario)
//            jlMensajeDemasDatos.setText("");         
            modoRegistroEncontrado();

        } else {
            modoRegistroNoEncontrado();
        }
    }//GEN-LAST:event_jbBuscarActionPerformed

    private void jbAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAgregarActionPerformed

        modoOperacion();

        jlMensajeNombre.setText("");

        jlMensajeDemasDatos.setForeground(Color.BLUE);
        jlMensajeDemasDatos.setText("Complete todos los campos de abajo");

        flagAgregar = true;
    }//GEN-LAST:event_jbAgregarActionPerformed

    private void jbDarDeBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDarDeBajaActionPerformed

        String nombreCuartel = cuartel.getNombreCuartel();

        if (JOptionPane.showConfirmDialog(this,
                "¿Está seguro de querer dar de baja al cuartel '" + nombreCuartel + "'?",
                "Advertencia",
                JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION
                && cuartelData.eliminarCuartelPorNombre(nombreCuartel)) {

            JOptionPane.showMessageDialog(this, "Se dió de baja al cuartel '" + nombreCuartel + "'");
            configurarComboBox();
            modoPrevioABusqueda();
        }
    }//GEN-LAST:event_jbDarDeBajaActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        if (flagAgregar) {
            modoRegistroNoEncontrado();
        } else {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jtfNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNombreKeyTyped
        if (!jbGuardar.isEnabled()) {   // si no se está modificando un cuartel (en cuyo caso, se podría modificar el nombre)    
            modoPrevioABusqueda();
        }
    }//GEN-LAST:event_jtfNombreKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
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
