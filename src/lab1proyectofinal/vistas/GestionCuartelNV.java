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
    private String nombreRegEncontrado;

    public GestionCuartelNV(CuartelData cuartelData) {
        initComponents();
        this.cuartelData = cuartelData;
        configurarComboBox();
        modoPrevioABusqueda();
    }

    private void configurarComboBox() {
        jlMensajeCuartel.setForeground(Color.BLACK);
        jlMensajeCuartel.setText("");
        jComboBoxCuarteles.removeAllItems();
        listaCuartel = cuartelData.listarCuarteles();
        if (listaCuartel.isEmpty()) {
            jlMensajeCuartel.setForeground(Color.RED);
            jlMensajeCuartel.setText("Advertencia: no hay cuarteles cargados en el sistema.");
            return;
        }
        for (Cuartel cuar : listaCuartel) {
            jComboBoxCuarteles.addItem(cuar);
        }
        jTextFieldNombre.setText("");      // limpia el contenido que queda luego de la ejecución de 'jComboBoxCuartelesActionPerformed' al agregar el primer item en 'jComboBoxCuarteles' (lo cual cambia el item seleccionado del mismo y, por tanto, provoca la ejecución del método antes mencionado)
    }

    private void limpiarCamposDistintosDeNombre() {
        jTextFieldDireccion.setText("");
        jTextFieldTelefono.setText("");
        jTextFieldCorreoElec.setText("");
        jTextFieldCoorX.setText("");
        jTextFieldCoorY.setText("");
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
        de 'jTextFieldNombre'.
         */

        if (jComboBoxCuarteles.getSelectedIndex() != -1) {
            jComboBoxCuarteles.setSelectedIndex(-1);  // cuando se tipea en 'jTextFieldNombre' se ejecuta 'modoPrevioABusqueda()', por lo que, de haber tenido un item seleccionado en 'jComboBoxCuarteles', al cambiar el contenido de 'jTextFieldNombre' es conveniente deseleccionar el item, para evitar confusiones 
        }
        limpiarCamposDistintosDeNombre();
        borrarMensajesMenosEnJCB();

        // inhabilitar campos distintos de 'jTextFieldNombre'
        jTextFieldCoorX.setEnabled(false);
        jTextFieldCoorY.setEnabled(false);
        jTextFieldCorreoElec.setEnabled(false);
        jTextFieldDireccion.setEnabled(false);
        jTextFieldTelefono.setEnabled(false);

        // inhabilitar botones distintos de 'jButtonBuscar' 
        jButtonAgregar.setEnabled(false);
        jButtonModificar.setEnabled(false);
        jButtonDarDeBaja.setEnabled(false);
        jButtonLimpiar.setEnabled(false);
        jButtonGuardar.setEnabled(false);
        jButtonCancelar.setEnabled(false);

        jComboBoxCuarteles.setEnabled(true);
        jButtonBuscar.setEnabled(true);

        jTextFieldNombre.setEditable(true);        // necesario para el caso en el que se culmina la agregación exitosa de un registro (operación que vuelve ineditable a 'jTextFieldNombre') y se regresa al modo "previo a búsqueda"

    }

    private void modoRegistroEncontrado() {
        /* 
            Modo "registro encontrado": se aplica en la situación en la cual se muestran los datos 
        de un registro existente en la BD, indistintamente de si se llegó a dicho registro a través de 
        'jComboBoxCuarteles' o 'jButtonBuscar'. También se aplica cuando se cancela la modificación de un registro.
         */

        borrarMensajesDeDemasCampos();  // necesario para el caso en el que en el modo "operación" habían mensajes indicando que faltaba completar uno o más campos y, sin que dicho campos hayan desparecido, se cancela la operación y regresa al modo "registro encontrado"

        // indicaciones para el usuario
        jlMensajeNombre.setForeground(Color.BLACK);
        jlMensajeNombre.setText("<html>Hay un cuartel con este nombre entre los registrados.</html>");
        jlMensajeDemasDatos.setForeground(Color.BLUE);
        jlMensajeDemasDatos.setText("<html>Puede modificar el cuartel encontrado haciendo click en '" + jButtonModificar.getText() + "' o darle de baja haciendo click en '" + jButtonDarDeBaja.getText() + "'.</html>");   // el html se utiliza para poder escribir más de una línea en el label    

        // se establece el texto de los jtf distintos de 'jTextFieldNombre'
        jTextFieldNombre.setText(cuartel.getNombreCuartel());
        jTextFieldDireccion.setText(cuartel.getDireccion());
        jTextFieldTelefono.setText(cuartel.getTelefono());
        jTextFieldCorreoElec.setText(cuartel.getCorreo());
        jTextFieldCoorX.setText(String.valueOf(cuartel.getCoordenadaX()));
        jTextFieldCoorY.setText(String.valueOf(cuartel.getCoordenadaY()));

        nombreRegEncontrado = jTextFieldNombre.getText();   // necesario para compararlo con una hipotética modificación, en donde el nombre se puede cambiar y, por lo tanto, se debe de asegurar que el nuevo nombre no sea uno ya reservado (y distinto del que tenía el registro previo a la modificación)

        // habilitar y volver ineditables aquellos campos distintos de 'jTextFieldNombre'
        jTextFieldCoorX.setEnabled(true);
        jTextFieldCoorY.setEnabled(true);
        jTextFieldCorreoElec.setEnabled(true);
        jTextFieldDireccion.setEnabled(true);
        jTextFieldTelefono.setEnabled(true);
        jTextFieldCoorX.setEditable(false);
        jTextFieldCoorY.setEditable(false);
        jTextFieldCorreoElec.setEditable(false);
        jTextFieldDireccion.setEditable(false);
        jTextFieldTelefono.setEditable(false);

        // habilitar, necesario para (entre otras cosas) el caso en el que se cancela la modificación de un registro y se regresa al modo "registro encontrado"         
        jButtonModificar.setEnabled(true);
        jButtonDarDeBaja.setEnabled(true);
        jButtonBuscar.setEnabled(true);
        jComboBoxCuarteles.setEnabled(true);

        // inhabilitar
        jButtonAgregar.setEnabled(false);
        // necesario para el caso en el que se cancela la modificación de un registro y se regresa al modo "registro encontrado" 
        jButtonGuardar.setEnabled(false);
        jButtonCancelar.setEnabled(false);
        jButtonLimpiar.setEnabled(false);

    }

    private void modoRegistroNoEncontrado() {
        /* 
            Modo "registro no encontrado": situación en la cual se ha clickeado en 'jButtonBuscar' y 
        el nombre ingresado no se corresponde con el de ningún registro en la BD (ni activo ni inactivo).
        También se aplica cuando se cancela la agregación de un registro.        
         */

        limpiarCamposDistintosDeNombre();
        borrarMensajesDeDemasCampos();      // necesario para el caso en el que en el modo "operación" habían mensajes indicando que faltaba completar uno o más campos y, sin que dicho campos hayan desparecido, se cancela la operación y regresa al modo "registro no encontrado"

        // indicaciones al usuario
        jlMensajeNombre.setForeground(Color.BLACK);
        jlMensajeNombre.setText("<html>No existe un cuartel registrado con este nombre.</html>");
        jlMensajeDemasDatos.setForeground(Color.BLUE);
        jlMensajeDemasDatos.setText("<html>Puede registrar un cuartel con el nombre ingresado haciendo click en '" + jButtonAgregar.getText() + "' e ingresando los demás datos.</html>");   // el html se utiliza para poder escribir más de una línea en el label    

        jTextFieldNombre.setEditable(true);    // necesario para el caso en el que se cancela la agregación de un registro (operación que vuelve ineditable a 'jTextFieldNombre') y se regresa al modo "registro no encontrado"

        // inhabilitar campos distintos de 'jTextFieldNombre'
        jTextFieldCoorX.setEnabled(false);
        jTextFieldCoorY.setEnabled(false);
        jTextFieldCorreoElec.setEnabled(false);
        jTextFieldDireccion.setEnabled(false);
        jTextFieldTelefono.setEnabled(false);

        // inhabilitar 
        jButtonModificar.setEnabled(false);
        jButtonDarDeBaja.setEnabled(false);
        // necesario para el caso en el que se cancela la agregación de un registro y se regresa al modo "registro no encontrado" 
        jButtonGuardar.setEnabled(false);
        jButtonCancelar.setEnabled(false);
        jButtonLimpiar.setEnabled(false);

        // habilitar, necesario para (entre otras cosas): el caso en el que se cancela la agregación de un registro y se regresa al modo "registro no encontrado" 
        jButtonAgregar.setEnabled(true);
        jButtonBuscar.setEnabled(true);
        jComboBoxCuarteles.setEnabled(true);
    }

    private void modoOperacion() {
        /* 
            Modo "operación": se aplica en la situación en la cual se está agregando o modificando 
        registro (no aplica para la baja, dado que esta última solo requiere del click en 'jbDarDeBaja' 
        y de la confirmación o declinación de la solicitud de confirmación posterior).
         */

        // habilitar campos, necesario para la agregación de un registro, pues en el modo "registro no encontrado" estos campos estaban inhabilitados
        jTextFieldCoorX.setEnabled(true);
        jTextFieldCoorY.setEnabled(true);
        jTextFieldCorreoElec.setEnabled(true);
        jTextFieldDireccion.setEnabled(true);
        jTextFieldNombre.setEnabled(true);     // necesario para poder modificar un registro
        jTextFieldTelefono.setEnabled(true);

        // volver editables los capos distintos de 'jTextFieldNombre', necesario para 1) La modificación de un registro, pues en el modo "registro encontrado" estos campos eran no editables, 2) La agregación de un registro en caso de que, antes de la agregación, se haya llegado al modo "previo a búsqueda" mediante una operación de modificación o baja (ambas vuelven ineditable estos campos)
        jTextFieldCoorX.setEditable(true);
        jTextFieldCoorY.setEditable(true);
        jTextFieldCorreoElec.setEditable(true);
        jTextFieldDireccion.setEditable(true);
        jTextFieldTelefono.setEditable(true);

        if (flagAgregar) {
            jTextFieldNombre.setEditable(false);
        } else {
            jTextFieldNombre.setEditable(true);    // necesario para poder modificar un registro
        }

        // habilitar 
        jButtonGuardar.setEnabled(true);
        jButtonCancelar.setEnabled(true);
        jButtonLimpiar.setEnabled(true);

        // inhabilitar 
        jButtonBuscar.setEnabled(false);
        jComboBoxCuarteles.setEnabled(false);
        jButtonAgregar.setEnabled(false);
        jButtonModificar.setEnabled(false);
        jButtonDarDeBaja.setEnabled(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelNombre = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jButtonGuardar = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jLabelGestionCuarteles = new javax.swing.JLabel();
        jButtonAgregar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jButtonDarDeBaja = new javax.swing.JButton();
        jlMensajeNombre = new javax.swing.JLabel();
        jLabelCuartel = new javax.swing.JLabel();
        jComboBoxCuarteles = new javax.swing.JComboBox<>();
        jLabelBuscarConCB = new javax.swing.JLabel();
        jLabelBuscarConTF = new javax.swing.JLabel();
        jLabelDemasDatos = new javax.swing.JLabel();
        jButtonBuscar = new javax.swing.JButton();
        jlMensajeDemasDatos = new javax.swing.JLabel();
        jlMensajeCuartel = new javax.swing.JLabel();
        jPanelDemasDatos = new javax.swing.JPanel();
        jlMensajeCoorY = new javax.swing.JLabel();
        jLabelCoorX = new javax.swing.JLabel();
        jLabelDireccion = new javax.swing.JLabel();
        jTextFieldDireccion = new javax.swing.JTextField();
        jLabelCorreoElec = new javax.swing.JLabel();
        jTextFieldCoorY = new javax.swing.JTextField();
        jlMensajeCorreoElec = new javax.swing.JLabel();
        jTextFieldTelefono = new javax.swing.JTextField();
        jlMensajeDireccion = new javax.swing.JLabel();
        jTextFieldCorreoElec = new javax.swing.JTextField();
        jTextFieldCoorX = new javax.swing.JTextField();
        jLabelCoorY = new javax.swing.JLabel();
        jlMensajeTelefono = new javax.swing.JLabel();
        jlMensajeCoorX = new javax.swing.JLabel();
        jButtonLimpiar = new javax.swing.JButton();
        jLabelTelefono = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1085, 560));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelNombre.setText("Nombre:");
        getContentPane().add(jLabelNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, -1, -1));

        jTextFieldNombre.setColumns(20);
        jTextFieldNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextFieldNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNombreKeyTyped(evt);
            }
        });
        getContentPane().add(jTextFieldNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 200, -1));

        jButtonGuardar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButtonGuardar.setText("Guardar");
        jButtonGuardar.setEnabled(false);
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 470, -1, -1));

        jButtonSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButtonSalir.setText("Salir");
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 470, -1, -1));

        jLabelGestionCuarteles.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelGestionCuarteles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGestionCuarteles.setText("Gestión de cuarteles");
        getContentPane().add(jLabelGestionCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1070, -1));

        jButtonAgregar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButtonAgregar.setText("Agregar cuartel");
        jButtonAgregar.setEnabled(false);
        jButtonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, -1));

        jButtonModificar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButtonModificar.setText("Modificar cuartel");
        jButtonModificar.setEnabled(false);
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, -1, -1));

        jButtonCancelar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.setEnabled(false);
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 470, -1, -1));

        jButtonDarDeBaja.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButtonDarDeBaja.setText("Dar de baja cuartel");
        jButtonDarDeBaja.setEnabled(false);
        jButtonDarDeBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDarDeBajaActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonDarDeBaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, -1, -1));

        jlMensajeNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jlMensajeNombre.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jlMensajeNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 305, 340, 40));

        jLabelCuartel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelCuartel.setText("Cuartel:");
        getContentPane().add(jLabelCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, -1, -1));

        jComboBoxCuarteles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jComboBoxCuarteles.setMaximumRowCount(10);
        jComboBoxCuarteles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCuartelesActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBoxCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 200, -1));

        jLabelBuscarConCB.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelBuscarConCB.setText("Puede seleccionar un cuartel de entre los registrados:");
        getContentPane().add(jLabelBuscarConCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jLabelBuscarConTF.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelBuscarConTF.setText("Puede ingresar un nombre de cuartel y ver si está registrado:");
        getContentPane().add(jLabelBuscarConTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabelDemasDatos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelDemasDatos.setText("Demás datos del cuartel:");
        getContentPane().add(jLabelDemasDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 90, -1, -1));

        jButtonBuscar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 270, -1, -1));

        jlMensajeDemasDatos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jlMensajeDemasDatos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jlMensajeDemasDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 410, 450, 55));

        jlMensajeCuartel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jlMensajeCuartel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jlMensajeCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 165, 340, 40));

        jPanelDemasDatos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelDemasDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlMensajeCoorY.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jlMensajeCoorY, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 295, 250, 23));

        jLabelCoorX.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelCoorX.setText("Coordenada X:");
        jPanelDemasDatos.add(jLabelCoorX, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        jLabelDireccion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelDireccion.setText("Dirección:");
        jPanelDemasDatos.add(jLabelDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jTextFieldDireccion.setEditable(false);
        jTextFieldDireccion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextFieldDireccion.setEnabled(false);
        jPanelDemasDatos.add(jTextFieldDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 200, -1));

        jLabelCorreoElec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelCorreoElec.setText("Correo Electrónico:");
        jPanelDemasDatos.add(jLabelCorreoElec, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jTextFieldCoorY.setEditable(false);
        jTextFieldCoorY.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextFieldCoorY.setEnabled(false);
        jPanelDemasDatos.add(jTextFieldCoorY, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, 75, -1));

        jlMensajeCorreoElec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jlMensajeCorreoElec, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 170, 250, 23));

        jTextFieldTelefono.setEditable(false);
        jTextFieldTelefono.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextFieldTelefono.setEnabled(false);
        jPanelDemasDatos.add(jTextFieldTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 200, -1));

        jlMensajeDireccion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jlMensajeDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 50, 250, 23));

        jTextFieldCorreoElec.setEditable(false);
        jTextFieldCorreoElec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextFieldCorreoElec.setEnabled(false);
        jPanelDemasDatos.add(jTextFieldCorreoElec, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 200, -1));

        jTextFieldCoorX.setEditable(false);
        jTextFieldCoorX.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextFieldCoorX.setEnabled(false);
        jPanelDemasDatos.add(jTextFieldCoorX, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 75, -1));

        jLabelCoorY.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelCoorY.setText("Coordenada Y:");
        jPanelDemasDatos.add(jLabelCoorY, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        jlMensajeTelefono.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jlMensajeTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 110, 250, 23));

        jlMensajeCoorX.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jlMensajeCoorX, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 230, 250, 23));

        jButtonLimpiar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButtonLimpiar.setText("Limpiar campos");
        jButtonLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimpiarActionPerformed(evt);
            }
        });
        jPanelDemasDatos.add(jButtonLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, -1, -1));

        jLabelTelefono.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelTelefono.setText("Teléfono:");
        jPanelDemasDatos.add(jLabelTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        getContentPane().add(jPanelDemasDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 440, 330));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpiarActionPerformed
        limpiarCamposDistintosDeNombre();
    }//GEN-LAST:event_jButtonLimpiarActionPerformed

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed

        // si la operación es una agregación y no una modificación
        if (cuartel == null) {
            cuartel = new Cuartel();
        }

        // se limpian posibles mensajes de una operación fallida
        borrarMensajesDeDemasCampos();

        // obtención y validación de entradas
        boolean entradasValidas = true;

        String nombre = jTextFieldNombre.getText().trim();
        // comprobar 'jTextFieldNombre' en caso de modificación
        if (nombre.isBlank()) {
            entradasValidas = false;
            jlMensajeNombre.setForeground(Color.RED);
            jlMensajeNombre.setText("<html>El nombre no puede estar compuesto solo por espacios en blanco.</html>");
        } else if (cuartelData.estaNombreEntreInactivos(nombre)) {
            entradasValidas = false;
            jlMensajeNombre.setForeground(Color.RED);
            jlMensajeNombre.setText("<html>Este nombre ya se encuentra ocupado por un registro dado de baja. Por favor, ingrese otro.</html>");
        } else {
            Cuartel cuartelBuscado = cuartelData.buscarCuartelPorNombre(nombre);
            if (cuartelBuscado != null) {
                if (cuartelBuscado.getNombreCuartel().equals(nombreRegEncontrado)) {
                    cuartel.setNombreCuartel(nombre);
                } else {
                    entradasValidas = false;
                    jlMensajeNombre.setForeground(Color.RED);
                    jlMensajeNombre.setText("<html>Este nombre se encuentra ocupado por otro registro. Por favor, ingrese otro.</html>");
                }
            }
            cuartel.setNombreCuartel(nombre);
        }

        String direccion = jTextFieldDireccion.getText();
        if (direccion.isBlank()) {
            entradasValidas = false;
            jlMensajeDireccion.setForeground(Color.RED);
            jlMensajeDireccion.setText("Debe completar este campo");
        } else {
            cuartel.setDireccion(direccion);
        }

        String telefono = jTextFieldTelefono.getText();
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

        String correoElec = jTextFieldCorreoElec.getText();
        if (correoElec.isBlank()) {
            entradasValidas = false;
            jlMensajeCorreoElec.setForeground(Color.RED);
            jlMensajeCorreoElec.setText("Debe completar este campo");
        } else {
            cuartel.setCorreo(correoElec);
        }

        try {
            int coorX = Integer.parseInt(jTextFieldCoorX.getText());
            cuartel.setCoordenadaX(coorX);
        } catch (NumberFormatException e) {
            jlMensajeCoorX.setForeground(Color.RED);
            jlMensajeCoorX.setText("Debe ingresar un número entero");
            entradasValidas = false;
        }

        try {
            int coorY = Integer.parseInt(jTextFieldCoorY.getText());
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
    }//GEN-LAST:event_jButtonGuardarActionPerformed

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        this.hide();
    }//GEN-LAST:event_jButtonSalirActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        flagAgregar = false;
        modoOperacion();

        jlMensajeNombre.setText("");

        jlMensajeDemasDatos.setForeground(Color.BLUE);
        jlMensajeDemasDatos.setText("<html>Modifique los datos que desee. También puede modificar el nombre.</html>");
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jComboBoxCuartelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCuartelesActionPerformed
        cuartel = (Cuartel) jComboBoxCuarteles.getSelectedItem();
        if (cuartel != null) {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jComboBoxCuartelesActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        String nombreCuartel = jTextFieldNombre.getText().trim();

        if (nombreCuartel.isBlank()) {
            jlMensajeDemasDatos.setText("");  // necesario para evitar que quede un mensaje ahí en el caso en que se busca algo y luego se vuelve a buscar
            jlMensajeNombre.setForeground(Color.RED);
            jlMensajeNombre.setText("<html>El nombre no puede estar compuesto solo por espacios en blanco.</html>");
            return;
        }

        if (cuartelData.estaNombreEntreInactivos(nombreCuartel)) {
            jlMensajeDemasDatos.setText("");
            jlMensajeNombre.setForeground(Color.RED);
            jlMensajeNombre.setText("<html>Este nombre ya se encuentra ocupado por un registro dado de baja. Por favor, ingrese otro.</html>");
            return;
        }

        cuartel = cuartelData.buscarCuartelPorNombre(nombreCuartel);
        if (cuartel != null) {
            modoRegistroEncontrado();
        } else {
            modoRegistroNoEncontrado();
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        flagAgregar = true;
        modoOperacion();

        jlMensajeNombre.setText("");

        jlMensajeDemasDatos.setForeground(Color.BLUE);
        jlMensajeDemasDatos.setText("<html>Complete los campos de los demás datos del cuartel.</html>");
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jButtonDarDeBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDarDeBajaActionPerformed
        String nombreCuartel = cuartel.getNombreCuartel();

        if (JOptionPane.showConfirmDialog(this,
                "¿Está seguro de querer dar de baja al cuartel '" + nombreCuartel + "'?",
                "Advertencia",
                JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            if (cuartelData.eliminarCuartelPorNombre(nombreCuartel)) {
                JOptionPane.showMessageDialog(this, "Se dió de baja al cuartel '" + nombreCuartel + "'.", "Información", JOptionPane.INFORMATION_MESSAGE);
                configurarComboBox();
                modoPrevioABusqueda();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo dar de baja al cuartel '" + nombreCuartel + "'.\nAsegurese de que no hay brigadas activas en este cuartel antes\nde intentar darle de baja.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonDarDeBajaActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        if (flagAgregar) {
            modoRegistroNoEncontrado();
        } else {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jTextFieldNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNombreKeyTyped
        // si no se está modificando un cuartel (en cuyo caso, se podría modificar el nombre)
        if (!jButtonGuardar.isEnabled()) {
            modoPrevioABusqueda();
        }
    }//GEN-LAST:event_jTextFieldNombreKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregar;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonDarDeBaja;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JButton jButtonLimpiar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JComboBox<Cuartel> jComboBoxCuarteles;
    private javax.swing.JLabel jLabelBuscarConCB;
    private javax.swing.JLabel jLabelBuscarConTF;
    private javax.swing.JLabel jLabelCoorX;
    private javax.swing.JLabel jLabelCoorY;
    private javax.swing.JLabel jLabelCorreoElec;
    private javax.swing.JLabel jLabelCuartel;
    private javax.swing.JLabel jLabelDemasDatos;
    private javax.swing.JLabel jLabelDireccion;
    private javax.swing.JLabel jLabelGestionCuarteles;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelTelefono;
    private javax.swing.JPanel jPanelDemasDatos;
    private javax.swing.JTextField jTextFieldCoorX;
    private javax.swing.JTextField jTextFieldCoorY;
    private javax.swing.JTextField jTextFieldCorreoElec;
    private javax.swing.JTextField jTextFieldDireccion;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldTelefono;
    private javax.swing.JLabel jlMensajeCoorX;
    private javax.swing.JLabel jlMensajeCoorY;
    private javax.swing.JLabel jlMensajeCorreoElec;
    private javax.swing.JLabel jlMensajeCuartel;
    private javax.swing.JLabel jlMensajeDemasDatos;
    private javax.swing.JLabel jlMensajeDireccion;
    private javax.swing.JLabel jlMensajeNombre;
    private javax.swing.JLabel jlMensajeTelefono;
    // End of variables declaration//GEN-END:variables
}
