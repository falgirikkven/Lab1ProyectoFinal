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
    private String nombreRegEncontrado;
    private JLabel jLabelAux = new JLabel();

    ;                     

    public GestionCuartel(CuartelData cuartelData) {
        initComponents();
        this.cuartelData = cuartelData;
        configurarComboBox();
        configurarJLabel();
        modoPrevioABusqueda();
    }

    private void configurarJLabel() {
        jLabelAux.setFont(new Font("Dialog", 0, 14));
        Dimension dim = new Dimension(300, 80);
        jLabelAux.setPreferredSize(dim);
    }

    private void configurarComboBox() {
        jLabelMensajeCuartel.setForeground(Color.BLACK);
        jLabelMensajeCuartel.setText("");
        jComboBoxCuarteles.removeAllItems();
        listaCuartel = cuartelData.listarCuarteles();
        if (listaCuartel.isEmpty()) {
            jLabelMensajeCuartel.setForeground(Color.RED);
            jLabelMensajeCuartel.setText(
                    "<html>Advertencia: no hay cuarteles cargados en el sistema.</html>");
            return;
        }
        for (Cuartel cuar : listaCuartel) {
            jComboBoxCuarteles.addItem(cuar);
        }
        // limpia el contenido que queda luego de la ejecución de 
        // 'jComboBoxCuartelesActionPerformed' al agregarle el primer item 
        jTextFieldNombre.setText("");
    }

    private void limpiarCamposDistintosDeNombre() {
        jTextFieldDireccion.setText("");
        jTextFieldTelefono.setText("");
        jTextFieldCorreoElec.setText("");
        jTextFieldCoorX.setText("");
        jTextFieldCoorY.setText("");
    }

    private void borrarMensajesMenosEnCuarteles() {
        jLabelMensajeCoorX.setText("");
        jLabelMensajeCoorY.setText("");
        jLabelMensajeCorreoElec.setText("");
        jLabelMensajeDemasDatos.setText("");
        jLabelMensajeDireccion.setText("");
        jLabelMensajeNombre.setText("");
        jLabelMensajeTelefono.setText("");
    }

    private void borrarMensajesDeDatos() {
        jLabelMensajeCoorX.setText("");
        jLabelMensajeCoorY.setText("");
        jLabelMensajeCorreoElec.setText("");
        jLabelMensajeDireccion.setText("");
        jLabelMensajeTelefono.setText("");
        jLabelMensajeNombre.setText("");
    }

    private void borrarMensajesDeDemasDatos() {
        jLabelMensajeCoorX.setText("");
        jLabelMensajeCoorY.setText("");
        jLabelMensajeCorreoElec.setText("");
        jLabelMensajeDireccion.setText("");
        jLabelMensajeTelefono.setText("");
    }

    private void modoPrevioABusqueda() {
        /* 
            Modo "previo a búsqueda", se aplica cuando:
        1) La primera vez que se utiliza este JInternalFrame 
        2) Inmediatamente luego de una operación llevada a cabo exitosamente
        3) Se cambia el contenido de 'jTextFieldNombre', sin modificar un registro.
         */

        // si se tiene un item seleccionado en 'jComboBoxCuarteles' al momento de cambiar el
        // contenido de 'jTextFieldNombre' es conveniente deseleccionar el item para evitar 
        // discrepancia entre ambos componentes 
        if (jComboBoxCuarteles.getSelectedIndex() != -1) {
            jComboBoxCuarteles.setSelectedIndex(-1);
        }
        limpiarCamposDistintosDeNombre();
        borrarMensajesMenosEnCuarteles();

        jTextFieldCoorX.setEnabled(false);
        jTextFieldCoorY.setEnabled(false);
        jTextFieldCorreoElec.setEnabled(false);
        jTextFieldDireccion.setEnabled(false);
        jTextFieldTelefono.setEnabled(false);
        jButtonAgregar.setEnabled(false);
        jButtonModificar.setEnabled(false);
        jButtonDarDeBaja.setEnabled(false);
        jButtonLimpiar.setEnabled(false);
        jButtonGuardar.setEnabled(false);
        jButtonCancelar.setEnabled(false);

        jComboBoxCuarteles.setEnabled(true);
        jButtonBuscar.setEnabled(true);

        // necesario para el caso en el que se agrega un registro (operación que vuelve ineditable
        // a 'jTextFieldNombre') y se regresa al modo "previo a búsqueda"
        jTextFieldNombre.setEditable(true);
    }

    private void modoRegistroEncontrado() {
        /* 
            Modo "registro encontrado", se aplica cuando:
        1) Se encuentra un registro mediante 'jComboBoxCuarteles' o 'jButtonBuscar'. 
        2) Se cancela la modificación de un registro.
         */

        if (jComboBoxCuarteles.getSelectedIndex() == -1) {
            jComboBoxCuarteles.setSelectedItem(cuartel);
        }

        // necesario para el caso en el que en el modo "operación" habían mensajes y, sin que 
        // dicho mensajes hayan desparecido, se cancela la operación y se regresa al modo 
        // "registro encontrado"
        borrarMensajesDeDemasDatos();

        // indicaciones para el usuario
        jLabelMensajeNombre.setForeground(Color.BLACK);
        // el html se utiliza para poder escribir más de una línea en el label    
        jLabelMensajeNombre.setText(
                "<html>Hay un cuartel con este nombre entre los registrados.</html>");
        jLabelMensajeDemasDatos.setForeground(Color.BLUE);
        jLabelMensajeDemasDatos.setText(
                "<html>Puede modificar el cuartel encontrado haciendo click en '"
                + jButtonModificar.getText() + "' o darle de baja haciendo click en '"
                + jButtonDarDeBaja.getText() + "'.</html>");

        jTextFieldNombre.setText(cuartel.getNombreCuartel());
        jTextFieldDireccion.setText(cuartel.getDireccion());
        jTextFieldTelefono.setText(cuartel.getTelefono());
        jTextFieldCorreoElec.setText(cuartel.getCorreo());
        jTextFieldCoorX.setText(String.valueOf(cuartel.getCoordenadaX()));
        jTextFieldCoorY.setText(String.valueOf(cuartel.getCoordenadaY()));

        // necesario para compararlo con una hipotética modificación de nombre, en cuyo caso se 
        // debe de asegurar que el nuevo nombre no sea uno ya reservado (y distinto del que tenía 
        // el registro previo a la modificación)
        nombreRegEncontrado = jTextFieldNombre.getText();

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

        // necesario para (al menos) el caso en el que se cancela la modificación de un registro 
        // (operación que inhabilita estos componentes) y se regresa al modo "registro encontrado"         
        jButtonModificar.setEnabled(true);
        jButtonDarDeBaja.setEnabled(true);
        jButtonBuscar.setEnabled(true);
        jComboBoxCuarteles.setEnabled(true);

        jButtonAgregar.setEnabled(false);
        // necesario para (al menos) el caso en el que se cancela la modificación de un registro 
        // (operación que inhabilita estos componentes) y se regresa al modo "registro encontrado"
        jButtonGuardar.setEnabled(false);
        jButtonCancelar.setEnabled(false);
        jButtonLimpiar.setEnabled(false);

    }

    private void modoRegistroNoEncontrado() {
        /* 
            Modo "registro no encontrado", se aplica cuando: 
        1) Se ha clickeado en 'jButtonBuscar' y el nombre ingresado no se corresponde con el de 
        ningún registro en la BD (ni activo ni inactivo).
        2) Se cancela la agregación de un registro.        
         */

        limpiarCamposDistintosDeNombre();
        // necesario para el caso en el que en el modo "operación" habían mensajes y, sin que 
        // dicho mensajes hayan desparecido, se cancela la operación y se regresa al modo 
        // "registro no encontrado"
        borrarMensajesDeDemasDatos();

        // indicaciones al usuario
        jLabelMensajeNombre.setForeground(Color.BLACK);
        jLabelMensajeNombre.setText("<html>No existe un cuartel registrado con este nombre."
                + "</html>");
        jLabelMensajeDemasDatos.setForeground(Color.BLUE);
        jLabelMensajeDemasDatos.setText("<html>Puede registrar un cuartel con el nombre ingresado "
                + "haciendo click en '" + jButtonAgregar.getText() + "' e ingresando los demás "
                + "datos.</html>");

        // necesario para el caso en el que se cancela la agregación de un registro (operación que 
        // vuelve ineditable a 'jTextFieldNombre') y se regresa al modo "registro no encontrado"
        jTextFieldNombre.setEditable(true);

        jTextFieldCoorX.setEnabled(false);
        jTextFieldCoorY.setEnabled(false);
        jTextFieldCorreoElec.setEnabled(false);
        jTextFieldDireccion.setEnabled(false);
        jTextFieldTelefono.setEnabled(false);
        jButtonModificar.setEnabled(false);
        jButtonDarDeBaja.setEnabled(false);
        // necesario para (al menos) el caso en el que se cancela la agregación de un registro y se        
        // regresa al modo "registro no encontrado" 
        jButtonGuardar.setEnabled(false);
        jButtonCancelar.setEnabled(false);
        jButtonLimpiar.setEnabled(false);

        // necesario para (al menos) el caso en el que se cancela la agregación de un registro y se
        // regresa al modo "registro no encontrado" 
        jButtonAgregar.setEnabled(true);
        jButtonBuscar.setEnabled(true);
        jComboBoxCuarteles.setEnabled(true);
    }

    private void modoOperacion() {
        /* 
            Modo "operación", se aplica cuando: 
        1) Se está agregando o modificando un registro (no aplica para la baja, dado que esta 
        última solo requiere del click en 'jbDarDeBaja' y de la confirmación o declinación de 
        la solicitud de confirmación posterior).
         */

        // habilitar campos, necesario para la agregación de un registro, pues en el modo 
        // "registro no encontrado" estos campos estaban inhabilitados
        jTextFieldCoorX.setEnabled(true);
        jTextFieldCoorY.setEnabled(true);
        jTextFieldCorreoElec.setEnabled(true);
        jTextFieldDireccion.setEnabled(true);
        jTextFieldTelefono.setEnabled(true);

        jTextFieldCoorX.setEditable(true);
        jTextFieldCoorY.setEditable(true);
        jTextFieldCorreoElec.setEditable(true);
        jTextFieldDireccion.setEditable(true);
        jTextFieldTelefono.setEditable(true);

        if (enAgregacion) {
            jTextFieldNombre.setEditable(false);
        } else {
            jTextFieldNombre.setEditable(true);
        }

        jButtonGuardar.setEnabled(true);
        jButtonCancelar.setEnabled(true);
        jButtonLimpiar.setEnabled(true);

        jButtonBuscar.setEnabled(false);
        jComboBoxCuarteles.setEnabled(false);
        jButtonAgregar.setEnabled(false);
        jButtonModificar.setEnabled(false);
        jButtonDarDeBaja.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
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
        jLabelMensajeNombre = new javax.swing.JLabel();
        jLabelCuartel = new javax.swing.JLabel();
        jComboBoxCuarteles = new javax.swing.JComboBox<>();
        jLabelBuscarConCB = new javax.swing.JLabel();
        jLabelBuscarConTF = new javax.swing.JLabel();
        jLabelDemasDatos = new javax.swing.JLabel();
        jButtonBuscar = new javax.swing.JButton();
        jLabelMensajeDemasDatos = new javax.swing.JLabel();
        jLabelMensajeCuartel = new javax.swing.JLabel();
        jPanelDemasDatos = new javax.swing.JPanel();
        jLabelMensajeCoorY = new javax.swing.JLabel();
        jLabelCoorX = new javax.swing.JLabel();
        jLabelDireccion = new javax.swing.JLabel();
        jTextFieldDireccion = new javax.swing.JTextField();
        jLabelCorreoElec = new javax.swing.JLabel();
        jTextFieldCoorY = new javax.swing.JTextField();
        jLabelMensajeCorreoElec = new javax.swing.JLabel();
        jTextFieldTelefono = new javax.swing.JTextField();
        jLabelMensajeDireccion = new javax.swing.JLabel();
        jTextFieldCorreoElec = new javax.swing.JTextField();
        jTextFieldCoorX = new javax.swing.JTextField();
        jLabelCoorY = new javax.swing.JLabel();
        jLabelMensajeTelefono = new javax.swing.JLabel();
        jLabelMensajeCoorX = new javax.swing.JLabel();
        jButtonLimpiar = new javax.swing.JButton();
        jLabelTelefono = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1085, 560));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelNombre.setText("Nombre:");
        getContentPane().add(jLabelNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 274, -1, -1));

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

        jLabelMensajeNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelMensajeNombre.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabelMensajeNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 305, 340, 40));

        jLabelCuartel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelCuartel.setText("Cuartel:");
        getContentPane().add(jLabelCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 133, -1, -1));

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

        jLabelMensajeDemasDatos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelMensajeDemasDatos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabelMensajeDemasDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 410, 450, 55));

        jLabelMensajeCuartel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelMensajeCuartel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabelMensajeCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 165, 340, 40));

        jPanelDemasDatos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelDemasDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelMensajeCoorY.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jLabelMensajeCoorY, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 295, 250, 23));

        jLabelCoorX.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelCoorX.setText("Coordenada X:");
        jPanelDemasDatos.add(jLabelCoorX, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 204, -1, -1));

        jLabelDireccion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelDireccion.setText("Dirección:");
        jPanelDemasDatos.add(jLabelDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 24, -1, -1));

        jTextFieldDireccion.setEditable(false);
        jTextFieldDireccion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextFieldDireccion.setEnabled(false);
        jPanelDemasDatos.add(jTextFieldDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 200, -1));

        jLabelCorreoElec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelCorreoElec.setText("Correo Electrónico:");
        jPanelDemasDatos.add(jLabelCorreoElec, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 144, -1, -1));

        jTextFieldCoorY.setEditable(false);
        jTextFieldCoorY.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextFieldCoorY.setEnabled(false);
        jPanelDemasDatos.add(jTextFieldCoorY, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, 75, -1));

        jLabelMensajeCorreoElec.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jLabelMensajeCorreoElec, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 170, 250, 23));

        jTextFieldTelefono.setEditable(false);
        jTextFieldTelefono.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextFieldTelefono.setEnabled(false);
        jPanelDemasDatos.add(jTextFieldTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 200, -1));

        jLabelMensajeDireccion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jLabelMensajeDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 50, 250, 23));

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
        jPanelDemasDatos.add(jLabelCoorY, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 264, -1, -1));

        jLabelMensajeTelefono.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jLabelMensajeTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 110, 250, 23));

        jLabelMensajeCoorX.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jLabelMensajeCoorX, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 230, 250, 23));

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
        jPanelDemasDatos.add(jLabelTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 84, -1, -1));

        getContentPane().add(jPanelDemasDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 440, 330));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpiarActionPerformed
        limpiarCamposDistintosDeNombre();
    }//GEN-LAST:event_jButtonLimpiarActionPerformed

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        // se limpian posibles mensajes de una operación fallida
        borrarMensajesDeDatos();

        boolean entradasValidas = true;

        String nombre = jTextFieldNombre.getText().trim();
        // comprobar 'jTextFieldNombre', en caso de modificación
        if (nombre.isBlank()) {
            entradasValidas = false;
            jLabelMensajeNombre.setForeground(Color.RED);
            jLabelMensajeNombre.setText("<html>El nombre no puede estar compuesto solo por "
                    + "espacios en blanco.</html>");
        } else if (cuartelData.estaNombreEntreInactivos(nombre)) {
            entradasValidas = false;
            jLabelMensajeNombre.setForeground(Color.RED);
            jLabelMensajeNombre.setText("<html>Este nombre se encuentra ocupado por un cuartel "
                    + "dado de baja y no puede ser utilizado por otro.</html>");
        } else {
            Cuartel cuartelBuscado = cuartelData.buscarCuartelPorNombre(nombre);
            if (cuartelBuscado != null) {
                if (cuartelBuscado.getNombreCuartel().equals(nombreRegEncontrado)) {
                    cuartel.setNombreCuartel(nombre);
                } else {
                    entradasValidas = false;
                    jLabelMensajeNombre.setForeground(Color.RED);
                    jLabelMensajeNombre.setText("<html>Este nombre se encuentra ocupado por un "
                            + "cuartel y no puede ser utilizado por otro.</html>");
                }
            }
            cuartel.setNombreCuartel(nombre);
        }

        String direccion = jTextFieldDireccion.getText();
        if (direccion.isBlank()) {
            entradasValidas = false;
            jLabelMensajeDireccion.setForeground(Color.RED);
            jLabelMensajeDireccion.setText("Debe completar este campo.");
        } else {
            cuartel.setDireccion(direccion);
        }

        String telefono = jTextFieldTelefono.getText();
        if (telefono.isBlank()) {
            entradasValidas = false;
            jLabelMensajeTelefono.setForeground(Color.RED);
            jLabelMensajeTelefono.setText("Debe completar este campo.");
        } else if (!Utils.esTelefonoValido(telefono)) {
            entradasValidas = false;
            jLabelMensajeTelefono.setForeground(Color.RED);
            jLabelMensajeTelefono.setText("Debe ingresar un número de teléfono válido.");
        } else {
            cuartel.setTelefono(telefono);
        }

        String correoElec = jTextFieldCorreoElec.getText();
        if (correoElec.isBlank()) {
            entradasValidas = false;
            jLabelMensajeCorreoElec.setForeground(Color.RED);
            jLabelMensajeCorreoElec.setText("Debe completar este campo.");
        } else {
            cuartel.setCorreo(correoElec);
        }

        try {
            int coorX = Integer.parseInt(jTextFieldCoorX.getText());
            cuartel.setCoordenadaX(coorX);
        } catch (NumberFormatException e) {
            jLabelMensajeCoorX.setForeground(Color.RED);
            jLabelMensajeCoorX.setText("Debe ingresar un número entero.");
            entradasValidas = false;
        }

        try {
            int coorY = Integer.parseInt(jTextFieldCoorY.getText());
            cuartel.setCoordenadaY(coorY);
        } catch (NumberFormatException e) {
            jLabelMensajeCoorY.setForeground(Color.RED);
            jLabelMensajeCoorY.setText("Debe ingresar un número entero.");
            entradasValidas = false;
        }

        if (entradasValidas && enAgregacion) {

            if (cuartelData.guardarCuartel(cuartel)) {
                jLabelAux.setText("<html>Se registró el cuartel " + "\"" + cuartel.getNombreCuartel()
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabelAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarComboBox();
                modoPrevioABusqueda();
            } else {
                jLabelAux.setText("<html>No se pudo registrar el cuartel \"" + cuartel.getNombreCuartel()
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabelAux, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } // si las entradas son válidas y se está modificando un cuartel 
        else if (entradasValidas && !enAgregacion) {
            if (cuartelData.modificarCuartel(cuartel)) {
                jLabelAux.setText("<html>Se modificó el cuartel \"" + cuartel.getNombreCuartel()
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabelAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarComboBox();
                modoPrevioABusqueda();
            } else {
                jLabelAux.setText("<html>No se pudo modificar el cuartel \"" + cuartel.getNombreCuartel()
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabelAux, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonGuardarActionPerformed

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        this.hide();
    }//GEN-LAST:event_jButtonSalirActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        enAgregacion = false;
        modoOperacion();

        jLabelMensajeNombre.setText("");
        jLabelMensajeDemasDatos.setForeground(Color.BLUE);
        jLabelMensajeDemasDatos.setText("<html>Modifique los datos que desee. También puede "
                + "modificar el nombre.</html>");
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jComboBoxCuartelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCuartelesActionPerformed
        cuartel = (Cuartel) jComboBoxCuarteles.getSelectedItem();
        // por si se generó un actionEvent a causa de un 'jComboBoxCuarteles' actualizado (lo cual 
        // deja un índice seleccionado = -1 y, por tanto, un elemento null)
        if (cuartel != null) {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jComboBoxCuartelesActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        String nombreCuartel = jTextFieldNombre.getText().trim();

        if (nombreCuartel.isBlank()) {
            // necesario para evitar que quede un mensaje ahí en el caso en que se busca un 
            // nombre, se encuentra algo e inmediatamente se vuelve a buscar otro nombre
            jLabelMensajeDemasDatos.setText("");
            jLabelMensajeNombre.setForeground(Color.RED);
            jLabelMensajeNombre.setText("<html>El nombre no puede estar compuesto solo por "
                    + "espacios en blanco.</html>");
            return;
        }

        if (cuartelData.estaNombreEntreInactivos(nombreCuartel)) {
            jLabelMensajeDemasDatos.setText("");
            jLabelMensajeNombre.setForeground(Color.RED);
            jLabelMensajeNombre.setText("<html>Este nombre ya se encuentra ocupado por un "
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
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        enAgregacion = true;
        modoOperacion();

        jLabelMensajeNombre.setText("");
        jLabelMensajeDemasDatos.setForeground(Color.BLUE);
        jLabelMensajeDemasDatos.setText("<html>Complete los campos de los demás datos del cuartel."
                + "</html>");
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jButtonDarDeBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDarDeBajaActionPerformed
        String nombreCuartel = cuartel.getNombreCuartel();

        jLabelAux.setText("<html>¿Está seguro de querer dar de baja al cuartel '" + nombreCuartel
                + "'?</html>");
        if (JOptionPane.showConfirmDialog(this, jLabelAux, "Advertencia", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            jLabelAux.setText("Se dió de baja al cuartel \"" + nombreCuartel + "\".");
            if (cuartelData.eliminarCuartelPorNombre(nombreCuartel)) {
                JOptionPane.showMessageDialog(this, jLabelAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarComboBox();
                modoPrevioABusqueda();
            } else {
                jLabelAux.setText("<html>No se pudo dar de baja al cuartel \"" + nombreCuartel + "\". "
                        + "Asegurese de que no hay brigadas activas en este cuartel antes de "
                        + "intentar darle de baja.</html>");
                JOptionPane.showMessageDialog(this, jLabelAux, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonDarDeBajaActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        if (enAgregacion) {
            modoRegistroNoEncontrado();
        } else {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jTextFieldNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNombreKeyTyped
        // si no se está modificando un cuartel, volver al modo "previo a búsqueda"
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
    private javax.swing.JLabel jLabelMensajeCoorX;
    private javax.swing.JLabel jLabelMensajeCoorY;
    private javax.swing.JLabel jLabelMensajeCorreoElec;
    private javax.swing.JLabel jLabelMensajeCuartel;
    private javax.swing.JLabel jLabelMensajeDemasDatos;
    private javax.swing.JLabel jLabelMensajeDireccion;
    private javax.swing.JLabel jLabelMensajeNombre;
    private javax.swing.JLabel jLabelMensajeTelefono;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelTelefono;
    private javax.swing.JPanel jPanelDemasDatos;
    private javax.swing.JTextField jTextFieldCoorX;
    private javax.swing.JTextField jTextFieldCoorY;
    private javax.swing.JTextField jTextFieldCorreoElec;
    private javax.swing.JTextField jTextFieldDireccion;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldTelefono;
    // End of variables declaration//GEN-END:variables
}
