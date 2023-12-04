/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package lab1proyectofinal.vistas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import lab1proyectofinal.entidades.Cuartel;
import lab1proyectofinal.entidades.Brigada;
import lab1proyectofinal.accesoADatos.CuartelData;
import lab1proyectofinal.accesoADatos.BrigadaData;
import lab1proyectofinal.accesoADatos.Utils;

/**
 *
 * @author nahue
 */
public class GestionBrigada extends javax.swing.JInternalFrame {

    private final CuartelData cuartelData;
    private final BrigadaData brigadaData;
    private Brigada brigada;
    private List<Cuartel> listaCuartel;
    private List<Brigada> listaBrigada;
    // flag para determinar si se está llevando a cabo una operación (agregación, modificación o 
    // baja de brigada)
    private boolean enOperacion = false;
    // flag para determinar si se está agregando o modificando una brigada    
    private boolean enAgregacion;
    // falg para determinar si el usuario ha seleccionado un item de 'jComboBoxBrigadas'
    private boolean usuarioSeleccionoBrigada = true;
    private String nombreRegEncontrado;
    private MouseListener[] mouseListenersJCBEspecialidades;
    private MouseListener[] mouseListenersJCBEspecialidadesAB;
    private MouseListener[] mouseListenersJCBCuarteles;
    private MouseListener[] mouseListenersJCBCuartelesAB;
    private MouseListener[] mouseListenersJRBDisponible;
    private JLabel jLabelAux = new JLabel();

    public GestionBrigada(CuartelData cuartelData, BrigadaData brigadaData) {
        initComponents();
        this.cuartelData = cuartelData;
        this.brigadaData = brigadaData;
        configurarComboBoxEspecialidades();
        configurarComboBoxBrigadas();
        configurarJLabel();
        modoPrevioABusqueda();
    }

    boolean isEnOperacion() {
        return enOperacion;
    }

    void cancelarOperacion() {
        jButtonCancelar.doClick();
    }

    private void configurarJLabel() {
        jLabelAux.setFont(Utils.fuentePlana);
        Dimension dim = new Dimension(300, 80);
        jLabelAux.setPreferredSize(dim);
    }

    private void configurarComboBoxBrigadas() {
        jLabelMensajeBrigada.setForeground(Color.BLACK);
        jLabelMensajeBrigada.setText("");

        usuarioSeleccionoBrigada = false;
        jComboBoxBrigadas.removeAllItems();
        listaBrigada = brigadaData.listarBrigadas();
        if (listaBrigada.isEmpty()) {
            jLabelMensajeBrigada.setForeground(Color.RED);
            jLabelMensajeBrigada.setText("<html>Advertencia: no hay brigadas cargadas en el "
                    + "sistema.</html>");
            return;
        }
        for (Brigada bri : listaBrigada) {
            jComboBoxBrigadas.addItem(bri);
        }
        // limpia el contenido que queda luego de la ejecución de 
        // 'jComboBoxCuartelesActionPerformed' al agregarle el primer item
        jTextFieldNombre.setText("");

        usuarioSeleccionoBrigada = true;
    }

    private void configurarComboBoxEspecialidades() {
        String especialidades[] = Utils.obtenerEspecialidades();
        for (String especialidad : especialidades) {
            jComboBoxEspecialidades.addItem(especialidad);
        }
    }

    private void configurarComboBoxCuarteles() {
        jLabelMensajeCuarteles.setForeground(Color.BLACK);
        jLabelMensajeCuarteles.setText("");

        jComboBoxCuarteles.removeAllItems();
        listaCuartel = cuartelData.listarCuarteles();
        if (listaCuartel.isEmpty()) {
            jLabelMensajeCuarteles.setForeground(Color.RED);
            jLabelMensajeCuarteles.setText("<html>Advertencia: no hay cuarteles cargados en el "
                    + "sistema.</html>");
            modoInhabilitado();
            jLabelAux.setText("<html>No hay cuarteles registrados. No se puede agregar, modificar "
                    + "o dar de baja brigadas hasta que se registre un cuartel.</html>");
            JOptionPane.showMessageDialog(this, jLabelAux, "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (Cuartel cuar : listaCuartel) {
            jComboBoxCuarteles.addItem(cuar);
        }
        if (nombreRegEncontrado != null) {
            jComboBoxCuarteles.setSelectedItem(brigada.getCuartel());
            return;
        }
        jComboBoxCuarteles.setSelectedIndex(-1);
    }

    private void limpiarEntradasDistintasDeNombre() {
        jComboBoxEspecialidades.setSelectedIndex(-1);
        buttonGroupDisponible.clearSelection();
        jComboBoxCuarteles.setSelectedIndex(-1);
    }

    private void borrarMensajesMenosEnBrigadas() {
        jLabelMensajeNombre.setText("");
        jLabelMensajeDemasDatos.setText("");
        jLabelMensajeEspecialidades.setText("");
        jLabelMensajeDisponible.setText("");
        jLabelMensajeCuarteles.setText("");
    }

    private void borrarMensajesDeDatos() {
        jLabelMensajeNombre.setText("");
        jLabelMensajeEspecialidades.setText("");
        jLabelMensajeDisponible.setText("");
        jLabelMensajeCuarteles.setText("");
    }

    private void borrarMensajesDeDemasDatos() {
        jLabelMensajeEspecialidades.setText("");
        jLabelMensajeDisponible.setText("");
        jLabelMensajeCuarteles.setText("");
    }

    // los métodos de 'solo lectura' y 'lectura-escritura' han sido (en gran medida) copiados de 
    // internet (puden haber efectos secundarios que no tenga contemplados)                          
    private void comboBoxEspecialidadesEnSoloLectura() {

        if (jComboBoxEspecialidades.getMouseListeners().length > 0) {
            mouseListenersJCBEspecialidades = jComboBoxEspecialidades.getMouseListeners();
        }
        for (MouseListener listener : mouseListenersJCBEspecialidades) {
            jComboBoxEspecialidades.removeMouseListener(listener);
        }

        Component[] comps = jComboBoxEspecialidades.getComponents();
        for (Component c : comps) {
            if (c instanceof AbstractButton) {
                AbstractButton ab = (AbstractButton) c;
                ab.setEnabled(false);
                // (comentario 1)
                if (ab.getMouseListeners().length > 0) {
                    mouseListenersJCBEspecialidadesAB = ab.getMouseListeners();
                }
                for (MouseListener listener : mouseListenersJCBEspecialidadesAB) {
                    ab.removeMouseListener(listener);
                }
            }
        }
    }

    private void comboBoxEspecialidadesEnLecturaEscritura() {

        if (jComboBoxEspecialidades.getMouseListeners().length > 0) {
            return;
        }
        for (MouseListener listener : mouseListenersJCBEspecialidades) {
            jComboBoxEspecialidades.addMouseListener(listener);
        }

        Component[] comps = jComboBoxEspecialidades.getComponents();
        for (Component c : comps) {
            if (c instanceof AbstractButton) {
                AbstractButton ab = (AbstractButton) c;
                ab.setEnabled(true);

                for (MouseListener listener : mouseListenersJCBEspecialidadesAB) {
                    ab.addMouseListener(listener);
                }
            }
        }
    }

    private void comboBoxCuartelesEnSoloLectura() {

        // (comentario 1) 
        if (jComboBoxCuarteles.getMouseListeners().length > 0) {
            mouseListenersJCBCuarteles = jComboBoxCuarteles.getMouseListeners();
        }
        for (MouseListener listener : mouseListenersJCBCuarteles) {
            jComboBoxCuarteles.removeMouseListener(listener);
        }

        Component[] comps = jComboBoxCuarteles.getComponents();
        for (Component c : comps) {
            if (c instanceof AbstractButton) {
                AbstractButton ab = (AbstractButton) c;
                ab.setEnabled(false);
                // (comentario 1)
                if (ab.getMouseListeners().length > 0) {
                    mouseListenersJCBCuartelesAB = ab.getMouseListeners();
                }
                for (MouseListener listener : mouseListenersJCBCuartelesAB) {
                    ab.removeMouseListener(listener);
                }
            }
        }
    }

    private void comboBoxCuartelesEnLecturaEscritura() {

        if (jComboBoxCuarteles.getMouseListeners().length > 0) {
            return;
        }
        for (MouseListener listener : mouseListenersJCBCuarteles) {
            jComboBoxCuarteles.addMouseListener(listener);
        }

        Component[] comps = jComboBoxCuarteles.getComponents();
        for (Component c : comps) {
            if (c instanceof AbstractButton) {
                AbstractButton ab = (AbstractButton) c;
                ab.setEnabled(true);

                for (MouseListener listener : mouseListenersJCBCuartelesAB) {
                    ab.addMouseListener(listener);
                }
            }
        }
    }

    private void radioButtonSiEnSoloLectura(JRadioButton jrb) {
        if (jrb.getMouseListeners().length > 0) {
            mouseListenersJRBDisponible = jrb.getMouseListeners();
        }
        for (MouseListener listener : mouseListenersJRBDisponible) {
            jrb.removeMouseListener(listener);
        }
    }

    private void radioButtonSiEnLecturaEscritura(JRadioButton jrb) {
        if (jrb.getMouseListeners().length > 0) {
            return;
        }
        for (MouseListener listener : mouseListenersJRBDisponible) {
            jrb.addMouseListener(listener);
        }
    }

    private void componentesEnModoSoloLectura() {
        comboBoxEspecialidadesEnSoloLectura();
        radioButtonSiEnSoloLectura(jRadioButtonDisponibleSi);
        radioButtonSiEnSoloLectura(jRadioButtonDisponibleNo);
        comboBoxCuartelesEnSoloLectura();
    }

    private void componentesEnModoLecturaEscritura() {
        comboBoxEspecialidadesEnLecturaEscritura();
        radioButtonSiEnLecturaEscritura(jRadioButtonDisponibleSi);
        radioButtonSiEnLecturaEscritura(jRadioButtonDisponibleNo);
        comboBoxCuartelesEnLecturaEscritura();
    }

    private void modoInhabilitado() {
        /*
            Modo "inhabilitado", se aplica cuando:
        1) No existen cuarteles activos y, por tanto, no se puede agregar, modificar o dar de baja a 
        una brigada, pues no existe ninguna activa.
         */

        // inhabilitar todos los componentes de tipo 'JTextField', 'JComboBox', 'JRadioButton' o 
        // 'JButton' excepto 'jButtonSalir'
        jComboBoxBrigadas.setEnabled(false);
        jTextFieldNombre.setEnabled(false);
        jComboBoxEspecialidades.setEnabled(false);
        jRadioButtonDisponibleSi.setEnabled(false);
        jRadioButtonDisponibleNo.setEnabled(false);
        jComboBoxCuarteles.setEnabled(false);
        jButtonAgregar.setEnabled(false);
        jButtonModificar.setEnabled(false);
        jButtonDarDeBaja.setEnabled(false);
        jButtonLimpiar.setEnabled(false);
        jButtonGuardar.setEnabled(false);
        jButtonCancelar.setEnabled(false);
        jButtonBuscar.setEnabled(false);
    }

    private void modoPrevioABusqueda() {
        /* 
            Modo "previo a búsqueda", se aplica cuando:
        1) La primera vez que se utiliza este JInternalFrame 
        2) Inmediatamente luego de una operación llevada a cabo exitosamente
        3) Se cambia el contenido de 'jTextFieldNombre', sin modificar un registro.
         */

        // uso posterior en 'jButtonGuardarActionPerformed'
        nombreRegEncontrado = null;

        // si se tiene un item seleccionado en 'jComboBoxBrigadas' al momento de cambiar el
        // contenido de 'jTextFieldNombre' es conveniente deseleccionar el item para evitar 
        // discrepancia entre ambos componentes 
        if (jComboBoxBrigadas.getSelectedIndex() != -1) {
            usuarioSeleccionoBrigada = false;
            jComboBoxBrigadas.setSelectedIndex(-1);
            usuarioSeleccionoBrigada = true;
        }
        limpiarEntradasDistintasDeNombre();
        borrarMensajesMenosEnBrigadas();

        jComboBoxEspecialidades.setEnabled(false);
        jRadioButtonDisponibleSi.setEnabled(false);
        jRadioButtonDisponibleNo.setEnabled(false);
        jComboBoxCuarteles.setEnabled(false);
        jButtonAgregar.setEnabled(false);
        jButtonModificar.setEnabled(false);
        jButtonDarDeBaja.setEnabled(false);
        jButtonLimpiar.setEnabled(false);
        jButtonGuardar.setEnabled(false);
        jButtonCancelar.setEnabled(false);

        jComboBoxBrigadas.setEnabled(true);
        jButtonBuscar.setEnabled(true);

        // necesario para el caso en el que se agrega un registro (operación que vuelve ineditable
        // a 'jTextFieldNombre') y se regresa al modo "previo a búsqueda"
        jTextFieldNombre.setEditable(true);
    }

    private void modoRegistroEncontrado() {
        /* 
            Modo "registro encontrado", se aplica cuando:
        1) Se encuentra un registro mediante 'jComboBoxBrigadas' o 'jButtonBuscar'. 
        2) Se cancela la modificación de un registro.
         */

        if (jComboBoxBrigadas.getSelectedIndex() == -1) {

            jComboBoxBrigadas.setSelectedItem(brigada);
        }

        // necesario para el caso en el que en el modo "operación" habían mensajes y, sin que 
        // dicho mensajes hayan desparecido, se cancela la operación y se regresa al modo 
        // "registro encontrado"
        borrarMensajesDeDemasDatos();

        // indicaciones para el usuario
        jLabelMensajeNombre.setForeground(Color.BLACK);
        // el html se utiliza para poder escribir más de una línea en el label    
        jLabelMensajeNombre.setText("<html>Hay una brigada con este nombre entre las registradas."
                + "</html>");
        jLabelMensajeDemasDatos.setForeground(Color.BLUE);
        jLabelMensajeDemasDatos.setText("<html>Puede modificar la brigada encontrada haciendo "
                + "click en '" + jButtonModificar.getText() + "' o darle de baja haciendo click en "
                + "'" + jButtonDarDeBaja.getText() + "'.</html>");

        jTextFieldNombre.setText(brigada.getNombreBrigada());
        switch (brigada.getEspecialidad()) {
            case "Incendio en viviendas e industrias":
                jComboBoxEspecialidades.setSelectedIndex(0);
                break;
            case "Salvamento en derrumbes":
                jComboBoxEspecialidades.setSelectedIndex(1);
                break;
            case "Rescates en ámbito montaña":
                jComboBoxEspecialidades.setSelectedIndex(2);
                break;
            case "Rescates de personas en accidentes de tráfico":
                jComboBoxEspecialidades.setSelectedIndex(3);
                break;
            case "Socorrer inundaciones":
                jComboBoxEspecialidades.setSelectedIndex(4);
                break;
            case "Operativos de prevención":
                jComboBoxEspecialidades.setSelectedIndex(5);
                break;
        }
        if (brigada.isDisponible()) {
            jRadioButtonDisponibleSi.setSelected(true);
        } else {
            jRadioButtonDisponibleNo.setSelected(true);
        }
        for (int i = 0; i < jComboBoxCuarteles.getItemCount(); i++) {
            if (brigada.getCuartel().getNombreCuartel()
                    .equals(jComboBoxCuarteles.getItemAt(i).getNombreCuartel())) {
                jComboBoxCuarteles.setSelectedIndex(i);
            }
        }

        // necesario para compararlo con una hipotética modificación de nombre, en cuyo caso se 
        // debe de asegurar que el nuevo nombre no sea uno ya reservado (y distinto del que tenía 
        // el registro previo a la modificación)
        nombreRegEncontrado = jTextFieldNombre.getText();

        jComboBoxEspecialidades.setEnabled(true);
        jComboBoxCuarteles.setEnabled(true);
        jRadioButtonDisponibleSi.setEnabled(true);
        jRadioButtonDisponibleNo.setEnabled(true);

        // necesario para (al menos) el caso en el que se cancela la modificación de un registro 
        // (operación que inhabilita estos componentes) y se regresa al modo "registro encontrado"         
        jButtonModificar.setEnabled(true);
        jButtonDarDeBaja.setEnabled(true);
        jButtonBuscar.setEnabled(true);
        jComboBoxBrigadas.setEnabled(true);

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

        limpiarEntradasDistintasDeNombre();
        // necesario para el caso en el que en el modo "operación" habían mensajes y, sin que 
        // dicho mensajes hayan desparecido, se cancela la operación y se regresa al modo 
        // "registro no encontrado"
        borrarMensajesDeDemasDatos();

        // indicaciones al usuario
        jLabelMensajeNombre.setForeground(Color.BLACK);
        jLabelMensajeNombre.setText("<html>No existe una brigada registrada con este nombre."
                + "</html>");
        jLabelMensajeDemasDatos.setForeground(Color.BLUE);
        jLabelMensajeDemasDatos.setText("<html>Puede registrar una brigada con el nombre ingresado "
                + "haciendo click en '" + jButtonAgregar.getText() + "' e ingresando los demás "
                + "datos.</html>");

        // necesario para el caso en el que se cancela la agregación de un registro (operación que 
        // vuelve ineditable a 'jTextFieldNombre') y se regresa al modo "registro no encontrado"
        jTextFieldNombre.setEditable(true);

        jComboBoxEspecialidades.setEnabled(false);
        jRadioButtonDisponibleSi.setEnabled(false);
        jRadioButtonDisponibleNo.setEnabled(false);
        jComboBoxCuarteles.setEnabled(false);
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
        jComboBoxBrigadas.setEnabled(true);

    }

    private void modoOperacion() {
        /* 
            Modo "operación", se aplica cuando: 
        1) Se está agregando o modificando un registro (no aplica para la baja, dado que esta 
        última solo requiere del click en 'jbDarDeBaja' y de la confirmación o declinación de 
        la solicitud de confirmación posterior).
         */

        enOperacion = true;

        // habilitar campos, necesario para la agregación de un registro, pues en el modo 
        // "registro no encontrado" estos campos estaban inhabilitados
        if (enAgregacion) {
            jComboBoxEspecialidades.setEnabled(true);
            jRadioButtonDisponibleSi.setEnabled(true);
            jRadioButtonDisponibleNo.setEnabled(true);
            jComboBoxCuarteles.setEnabled(true);
        }

        // permitir que el usuario pueda elegir un item en 'jComboBoxEspecialidades' y 
        // 'jComboBoxCuarteles' o activar a 'jRadioButtonDisponibleSi' o 'jRadioButtonDisponibleNo'        
        componentesEnModoLecturaEscritura();

        if (enAgregacion) {
            jTextFieldNombre.setEditable(false);
        } else {
            jTextFieldNombre.setEditable(true);
        }

        jButtonGuardar.setEnabled(true);
        jButtonCancelar.setEnabled(true);
        jButtonLimpiar.setEnabled(true);

        jButtonBuscar.setEnabled(false);
        jComboBoxBrigadas.setEnabled(false);
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

        buttonGroupDisponible = new javax.swing.ButtonGroup();
        jLabelNombre = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jButtonGuardar = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jLabelGestionBrigadas = new javax.swing.JLabel();
        jButtonAgregar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jButtonDarDeBaja = new javax.swing.JButton();
        jLabelMensajeNombre = new javax.swing.JLabel();
        jLabelBrigada = new javax.swing.JLabel();
        jComboBoxBrigadas = new javax.swing.JComboBox<>();
        jLabelBuscarConCB = new javax.swing.JLabel();
        jLabelBuscarConTF = new javax.swing.JLabel();
        jLabelDemasDatos = new javax.swing.JLabel();
        jButtonBuscar = new javax.swing.JButton();
        jLabelMensajeDemasDatos = new javax.swing.JLabel();
        jLabelMensajeBrigada = new javax.swing.JLabel();
        jPanelDemasDatos = new javax.swing.JPanel();
        jLabelEspecialidad = new javax.swing.JLabel();
        jLabelCuartel = new javax.swing.JLabel();
        jLabelMensajeDisponible = new javax.swing.JLabel();
        jLabelMensajeEspecialidades = new javax.swing.JLabel();
        jButtonLimpiar = new javax.swing.JButton();
        jLabelDisponible = new javax.swing.JLabel();
        jComboBoxEspecialidades = new javax.swing.JComboBox<>();
        jRadioButtonDisponibleSi = new javax.swing.JRadioButton();
        jRadioButtonDisponibleNo = new javax.swing.JRadioButton();
        jLabelMensajeCuarteles = new javax.swing.JLabel();
        jComboBoxCuarteles = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(1134, 547));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
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
        getContentPane().add(jButtonGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 460, -1, -1));

        jButtonSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButtonSalir.setText("Salir");
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 460, -1, -1));

        jLabelGestionBrigadas.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelGestionBrigadas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGestionBrigadas.setText("Gestión de brigadas");
        getContentPane().add(jLabelGestionBrigadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1070, -1));

        jButtonAgregar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButtonAgregar.setText("Agregar brigada");
        jButtonAgregar.setEnabled(false);
        jButtonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, -1));

        jButtonModificar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButtonModificar.setText("Modificar brigada");
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
        getContentPane().add(jButtonCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 460, -1, -1));

        jButtonDarDeBaja.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButtonDarDeBaja.setText("Dar de baja brigada");
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

        jLabelBrigada.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelBrigada.setText("Brigada:");
        getContentPane().add(jLabelBrigada, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 133, -1, -1));

        jComboBoxBrigadas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jComboBoxBrigadas.setMaximumRowCount(10);
        jComboBoxBrigadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxBrigadasActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBoxBrigadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 200, -1));

        jLabelBuscarConCB.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelBuscarConCB.setText("Puede seleccionar una brigada de entre las registradas:");
        getContentPane().add(jLabelBuscarConCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jLabelBuscarConTF.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelBuscarConTF.setText("Puede ingresar un nombre de brigada y ver si está registrado:");
        getContentPane().add(jLabelBuscarConTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabelDemasDatos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelDemasDatos.setText("Demás datos de la brigada:");
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

        jLabelMensajeBrigada.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelMensajeBrigada.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabelMensajeBrigada, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 165, 340, 40));

        jPanelDemasDatos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelDemasDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelEspecialidad.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelEspecialidad.setText("Especialidad:");
        jPanelDemasDatos.add(jLabelEspecialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 24, -1, -1));

        jLabelCuartel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelCuartel.setText("Cuartel:");
        jPanelDemasDatos.add(jLabelCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 172, -1, -1));

        jLabelMensajeDisponible.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jLabelMensajeDisponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 125, 340, 23));

        jLabelMensajeEspecialidades.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jLabelMensajeEspecialidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 55, 340, 23));

        jButtonLimpiar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jButtonLimpiar.setText("Limpiar campos");
        jButtonLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimpiarActionPerformed(evt);
            }
        });
        jPanelDemasDatos.add(jButtonLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 260, -1, -1));

        jLabelDisponible.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabelDisponible.setText("Disponible:");
        jPanelDemasDatos.add(jLabelDisponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 95, -1, -1));

        jComboBoxEspecialidades.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jComboBoxEspecialidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 340, -1));

        buttonGroupDisponible.add(jRadioButtonDisponibleSi);
        jRadioButtonDisponibleSi.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jRadioButtonDisponibleSi.setText("Si");
        jPanelDemasDatos.add(jRadioButtonDisponibleSi, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 95, -1, -1));

        buttonGroupDisponible.add(jRadioButtonDisponibleNo);
        jRadioButtonDisponibleNo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jRadioButtonDisponibleNo.setText("No");
        jPanelDemasDatos.add(jRadioButtonDisponibleNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 95, -1, -1));

        jLabelMensajeCuarteles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jLabelMensajeCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 202, 340, 40));

        jComboBoxCuarteles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanelDemasDatos.add(jComboBoxCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 167, 200, -1));

        getContentPane().add(jPanelDemasDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 500, 310));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpiarActionPerformed
        limpiarEntradasDistintasDeNombre();
    }//GEN-LAST:event_jButtonLimpiarActionPerformed

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        // se limpian posibles mensajes de una operación fallida
        borrarMensajesDeDatos();

        boolean entradasValidas = true;

        String nombre = jTextFieldNombre.getText().trim();
        // comprobar 'jTextFieldNombre' en caso de modificación
        if (nombre.isBlank()) {
            entradasValidas = false;
            jLabelMensajeNombre.setForeground(Color.RED);
            jLabelMensajeNombre.setText("<html>El nombre no puede estar compuesto solo por "
                    + "espacios en blanco.</html>");
        } else if (brigadaData.estaNombreEntreInactivos(nombre)) {
            entradasValidas = false;
            jLabelMensajeNombre.setForeground(Color.RED);
            jLabelMensajeNombre.setText("<html>Este nombre se encuentra ocupado por una brigada "
                    + "dada de baja y no puede ser utilizado por otra.</html>");
        } else {
            Brigada brigadaBuscada = brigadaData.buscarBrigadaPorNombre(nombre);
            if (brigadaBuscada != null) {
                if (brigadaBuscada.getNombreBrigada().equals(nombreRegEncontrado)) {
                    brigada.setNombreBrigada(nombre);
                } else {
                    entradasValidas = false;
                    jLabelMensajeNombre.setForeground(Color.RED);
                    jLabelMensajeNombre.setText("<html>Este nombre se encuentra ocupado por una "
                            + "brigada y no puede ser utilizado por otra.</html>");
                }
            }
            brigada.setNombreBrigada(nombre);
        }

        if (jComboBoxEspecialidades.getSelectedIndex() > -1) {
            brigada.setEspecialidad((String) jComboBoxEspecialidades.getSelectedItem());
        } else {
            entradasValidas = false;
            jLabelMensajeEspecialidades.setForeground(Color.RED);
            jLabelMensajeEspecialidades.setText("Debe seleccionar una especialidad.");
        }

        if (jRadioButtonDisponibleSi.isSelected()) {
            brigada.setDisponible(true);
        } else if (jRadioButtonDisponibleNo.isSelected()) {
            brigada.setDisponible(false);
        } else {
            entradasValidas = false;
            jLabelMensajeDisponible.setForeground(Color.RED);
            jLabelMensajeDisponible.setText("Debe determinar la disponibilidad.");
        }

        if (jComboBoxCuarteles.getSelectedIndex() > -1) {
            brigada.setCuartel((Cuartel) jComboBoxCuarteles.getSelectedItem());
        } else {
            entradasValidas = false;
            jLabelMensajeCuarteles.setForeground(Color.RED);
            jLabelMensajeCuarteles.setText("Debe seleccionar un cuartel.");
        }

        if (entradasValidas && enAgregacion) {
            if (brigadaData.guardarBrigada(brigada)) {
                jLabelAux.setText("<html>Se registró la brigada \"" + brigada.getNombreBrigada()
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabelAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarComboBoxBrigadas();
                componentesEnModoSoloLectura();
                modoPrevioABusqueda();
                enOperacion = false;
            } else {
                jLabelAux.setText("<html>No se pudo registrar la brigada \""
                        + brigada.getNombreBrigada() + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabelAux, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } // si las entradas son válidas y se está modificando una brigada 
        else if (entradasValidas && !enAgregacion) {
            if (brigadaData.modificarBrigada(brigada)) {
                jLabelAux.setText("<html>Se ha modificado la brigada \"" + brigada.getNombreBrigada()
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabelAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarComboBoxBrigadas();
                componentesEnModoSoloLectura();
                modoPrevioABusqueda();
                enOperacion = false;
            } else {
                jLabelAux.setText("<html>No se pudo modificar la brigada \""
                        + brigada.getNombreBrigada() + "\".</html>");
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

    private void jComboBoxBrigadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxBrigadasActionPerformed
        brigada = (Brigada) jComboBoxBrigadas.getSelectedItem();
        // por si se generó un actionEvent a causa de un 'jComboBoxBrigadas' actualizado (lo cual 
        // deja un índice seleccionado = -1 y, por tanto, un elemento null)        
        if (brigada != null && usuarioSeleccionoBrigada) {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jComboBoxBrigadasActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        String nombreBrigada = jTextFieldNombre.getText().trim();

        if (nombreBrigada.isBlank()) {
            // necesario para evitar que quede un mensaje ahí en el caso en que se busca un 
            // nombre, se encuentra algo e inmediatamente se vuelve a buscar otro nombre
            jLabelMensajeDemasDatos.setText("");
            jLabelMensajeNombre.setForeground(Color.RED);
            jLabelMensajeNombre.setText("<html>El nombre no puede estar compuesto solo por "
                    + "espacios en blanco.</html>");
            return;
        }

        if (brigadaData.estaNombreEntreInactivos(nombreBrigada)) {
            jLabelMensajeDemasDatos.setText("");
            jLabelMensajeNombre.setForeground(Color.RED);
            jLabelMensajeNombre.setText("<html>Este nombre ya se encuentra ocupado por una brigada "
                    + "dada de baja. Por favor, ingrese otro.</html>");
            return;
        }

        brigada = brigadaData.buscarBrigadaPorNombre(nombreBrigada);
        if (brigada != null) {
            modoRegistroEncontrado();
        } else {
            brigada = new Brigada();
            modoRegistroNoEncontrado();
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        enAgregacion = true;
        modoOperacion();

        jLabelMensajeNombre.setText("");
        jLabelMensajeDemasDatos.setForeground(Color.BLUE);
        jLabelMensajeDemasDatos.setText("<html>Complete los campos de los demás datos de la "
                + "brigada.</html>");
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jButtonDarDeBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDarDeBajaActionPerformed
        String nombreBrigada = brigada.getNombreBrigada();

        jLabelAux.setText("<html>¿Está seguro de querer dar de baja a la brigada \""
                + nombreBrigada + "\"?</html>");
        if (JOptionPane.showConfirmDialog(this, jLabelAux, "Advertencia", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            if (brigadaData.eliminarBrigadaPorNombre(nombreBrigada)) {
                jLabelAux.setText("<html>Se dió de baja a la brigada \"" + nombreBrigada
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabelAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarComboBoxBrigadas();
                modoPrevioABusqueda();
            } else {
                jLabelAux.setText("<html>No se pudo dar de baja a la brigada \"" + nombreBrigada
                        + "\". Asegurese de que no hay bomberos activos en esta brigada antes de "
                        + "intentar darle de baja.</html>");
                JOptionPane.showMessageDialog(this, jLabelAux, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonDarDeBajaActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        enOperacion = false;
        componentesEnModoSoloLectura();
        if (enAgregacion) {
            modoRegistroNoEncontrado();
        } else {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jTextFieldNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNombreKeyTyped
        // si no se está modificando una brigada, volver al modo "previo a búsqueda"
        if (!jButtonGuardar.isEnabled()) {
            modoPrevioABusqueda();
        }
    }//GEN-LAST:event_jTextFieldNombreKeyTyped

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        configurarComboBoxCuarteles();
        // parte de un parche para evitar que el enfoque caiga sobre un componente inhabilitado
        // al cambiar de JInternalFrame (comentario 2)
        this.requestFocusInWindow();
    }//GEN-LAST:event_formInternalFrameActivated

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // (comentario 2)
        jTextFieldNombre.requestFocusInWindow();
    }//GEN-LAST:event_formFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupDisponible;
    private javax.swing.JButton jButtonAgregar;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonDarDeBaja;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JButton jButtonLimpiar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JComboBox<Brigada> jComboBoxBrigadas;
    private javax.swing.JComboBox<Cuartel> jComboBoxCuarteles;
    private javax.swing.JComboBox<String> jComboBoxEspecialidades;
    private javax.swing.JLabel jLabelBrigada;
    private javax.swing.JLabel jLabelBuscarConCB;
    private javax.swing.JLabel jLabelBuscarConTF;
    private javax.swing.JLabel jLabelCuartel;
    private javax.swing.JLabel jLabelDemasDatos;
    private javax.swing.JLabel jLabelDisponible;
    private javax.swing.JLabel jLabelEspecialidad;
    private javax.swing.JLabel jLabelGestionBrigadas;
    private javax.swing.JLabel jLabelMensajeBrigada;
    private javax.swing.JLabel jLabelMensajeCuarteles;
    private javax.swing.JLabel jLabelMensajeDemasDatos;
    private javax.swing.JLabel jLabelMensajeDisponible;
    private javax.swing.JLabel jLabelMensajeEspecialidades;
    private javax.swing.JLabel jLabelMensajeNombre;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JPanel jPanelDemasDatos;
    private javax.swing.JRadioButton jRadioButtonDisponibleNo;
    private javax.swing.JRadioButton jRadioButtonDisponibleSi;
    private javax.swing.JTextField jTextFieldNombre;
    // End of variables declaration//GEN-END:variables
}
