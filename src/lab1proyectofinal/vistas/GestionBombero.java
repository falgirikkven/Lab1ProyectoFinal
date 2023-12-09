/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package lab1proyectofinal.vistas;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;
import javax.swing.text.JTextComponent;
import lab1proyectofinal.entidades.*;
import lab1proyectofinal.accesoADatos.*;

/**
 *
 * @author nahue
 */
public class GestionBombero extends javax.swing.JInternalFrame {

    private final CuartelData cuartelData;
    private final BrigadaData brigadaData;
    private final BomberoData bomberoData;
    private Brigada brigada;
    private Bombero bombero;
    private List<Cuartel> listaCuartel;
    private List<Brigada> listaBrigada;
    private List<Bombero> listaBombero;
    private boolean enOperacion = false;
    private boolean enAgregacion;
    private boolean enModificacion;
    private boolean programaCambiandoJCBBomberos;
    private Integer dniRegEncontrado;
    private MouseListener[] mouseListenersJCBGruposSanguineos;
    private MouseListener[] mouseListenersJCBGruposSanguineosAB;
    private MouseListener[] mouseListenersJCBBrigadas;
    private MouseListener[] mouseListenersJCBBrigadasAB;
    private MouseListener[] mouseListenersJDCFechaNacimientoJTC;
    private MouseListener[] mouseListenersJDCFechaNacimientoAB;
    private JLabel jLabAux = Utils.jLabConfigurado();

    public GestionBombero(CuartelData cuartelData, BrigadaData brigadaData, BomberoData bomberoData) {
        initComponents();
        this.cuartelData = cuartelData;
        this.brigadaData = brigadaData;
        this.bomberoData = bomberoData;
        configurarJCBGruposSanguineos();
        configurarJCBBomberos();
        configurarJDCFechaNacimiento();
        modoPrevioABusqueda();
    }

    boolean isEnOperacion() {
        return enOperacion;
    }

    void cancelarOperacion() {
        jBCancelar.doClick();
    }

    private void configurarJCBBomberos() {
        jLabMensajeBombero.setText("");

        programaCambiandoJCBBomberos = true;
        jCBBomberos.removeAllItems();
        listaBombero = bomberoData.listarBomberos();
        if (listaBombero.isEmpty()) {
            jLabMensajeBombero.setForeground(Color.BLACK);
            jLabMensajeBombero.setText("<html>Advertencia: no hay bomberos cargados en el "
                    + "sistema.</html>");
            programaCambiandoJCBBomberos = false;
            return;
        }
        for (Bombero bom : listaBombero) {
            jCBBomberos.addItem(bom);
        }
        programaCambiandoJCBBomberos = false;
    }

    private void configurarJCBGruposSanguineos() {
        String gruposSanguineos[] = Utils.obtenerGrupoSanguineo();
        for (String gruposSanguineo : gruposSanguineos) {
            jCBGrupoSanguineo.addItem(gruposSanguineo);
        }
    }

    private void configurarJCBBrigadas() {
        jLabMensajeBrigada.setText("");

        jCBBrigadas.removeAllItems();
        listaBrigada = brigadaData.listarBrigadas();
        if (listaBrigada.isEmpty()) {
            jLabMensajeBrigada.setForeground(Color.BLACK);
            jLabMensajeBrigada.setText("<html>Advertencia: no hay brigadas cargadas en el "
                    + "sistema.</html>");
            modoInhabilitado();
            jLabAux.setText("<html>No hay brigadas registradas. No se puede agregar, modificar "
                    + "o dar de baja bomberos hasta que se registre una brigada.</html>");
            JOptionPane.showMessageDialog(this, jLabAux, "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (jTFDNI.getText().isBlank()) {
            modoPrevioABusqueda();
        }
        for (Brigada bri : listaBrigada) {
            jCBBrigadas.addItem(bri);
        }
        if (dniRegEncontrado != null) {
            jCBBrigadas.setSelectedItem(bombero.getBrigada());
            return;
        }
        jCBBrigadas.setSelectedIndex(-1);
    }

    private void configurarJDCFechaNacimiento() {
        Component[] comps = jDCFechaNacimiento.getComponents();
        for (Component c : comps) {
            if (c instanceof JTextComponent) {
                JTextComponent jtc = (JTextComponent) c;
                jtc.setEditable(false);
                if (jtc.getMouseListeners().length > 0) {
                    mouseListenersJDCFechaNacimientoJTC = jtc.getMouseListeners();
                    for (MouseListener listener : mouseListenersJDCFechaNacimientoJTC) {
                        if (listener instanceof ToolTipManager) {
                            ((ToolTipManager) listener).setEnabled(false);
                        } else {
                            jtc.removeMouseListener(listener);
                        }
                    }
                }
            }
        }
    }

    private void limpiarEntradasDeJPDemasDatos() {
        jTFNombreCompleto.setText("");
        jCBGrupoSanguineo.setSelectedIndex(-1);
        jDCFechaNacimiento.setDate(null);
        jTFCelular.setText("");
        jCBBrigadas.setSelectedIndex(-1);
    }

    private void borrarMensajesMenosEnJCBBomberos() {
        jLabMensajeDNI.setText("");
        jLabMensajeDemasDatos.setText("");
        jLabMensajeNomCompleto.setText("");
        jLabMensajeGrupoSanguineo.setText("");
        jLabMensajeFechaNacimiento.setText("");
        jLabMensajeCelular.setText("");
        jLabMensajeBrigada.setText("");
    }

    private void borrarMensajesDeDatos() {
        jLabMensajeDNI.setText("");
        jLabMensajeNomCompleto.setText("");
        jLabMensajeGrupoSanguineo.setText("");
        jLabMensajeFechaNacimiento.setText("");
        jLabMensajeCelular.setText("");
        jLabMensajeBrigada.setText("");
    }

    private void borrarMensajesDeDemasDatos() {
        jLabMensajeNomCompleto.setText("");
        jLabMensajeGrupoSanguineo.setText("");
        jLabMensajeFechaNacimiento.setText("");
        jLabMensajeCelular.setText("");
        jLabMensajeBrigada.setText("");
    }

    // los métodos de "solo lectura" y "lectura-escritura" han sido (en gran medida) copiados de 
    // internet (pueden haber efectos secundarios que no tenga contemplados)                          
    private void soloLecturaJCBGruposSanguineos(boolean b) {

        if (b) {
            if (jCBGrupoSanguineo.getMouseListeners().length > 0) {
                mouseListenersJCBGruposSanguineos = jCBGrupoSanguineo.getMouseListeners();
                for (MouseListener listener : mouseListenersJCBGruposSanguineos) {
                    jCBGrupoSanguineo.removeMouseListener(listener);
                }
            }
        } else {
            if (jCBGrupoSanguineo.getMouseListeners().length == 0) {
                for (MouseListener listener : mouseListenersJCBGruposSanguineos) {
                    jCBGrupoSanguineo.addMouseListener(listener);
                }
            }
        }

        Component[] comps = jCBGrupoSanguineo.getComponents();
        for (Component c : comps) {
            if (c instanceof AbstractButton) {
                AbstractButton ab = (AbstractButton) c;
                if (b) {
                    ab.setVisible(false);
                    if (ab.getMouseListeners().length > 0) {
                        mouseListenersJCBGruposSanguineosAB = ab.getMouseListeners();
                        for (MouseListener listener : mouseListenersJCBGruposSanguineosAB) {
                            ab.removeMouseListener(listener);
                        }
                    }
                } else {
                    ab.setVisible(true);
                    if (ab.getMouseListeners().length == 0) {
                        for (MouseListener listener : mouseListenersJCBGruposSanguineosAB) {
                            ab.addMouseListener(listener);
                        }
                    }
                }
            }
        }
    }

    private void soloLecturaJCBBrigadas(boolean b) {
        if (b) {
            if (jCBBrigadas.getMouseListeners().length > 0) {
                mouseListenersJCBBrigadas = jCBBrigadas.getMouseListeners();
                for (MouseListener listener : mouseListenersJCBBrigadas) {
                    jCBBrigadas.removeMouseListener(listener);
                }
            }
        } else {
            if (jCBBrigadas.getMouseListeners().length == 0) {
                for (MouseListener listener : mouseListenersJCBBrigadas) {
                    jCBBrigadas.addMouseListener(listener);
                }
            }
        }

        Component[] comps = jCBBrigadas.getComponents();
        for (Component c : comps) {
            if (c instanceof AbstractButton) {
                AbstractButton ab = (AbstractButton) c;
                if (b) {
                    ab.setVisible(false);
                    if (ab.getMouseListeners().length > 0) {
                        mouseListenersJCBBrigadasAB = ab.getMouseListeners();
                        for (MouseListener listener : mouseListenersJCBBrigadasAB) {
                            ab.removeMouseListener(listener);
                        }
                    }
                } else {
                    ab.setVisible(true);
                    if (ab.getMouseListeners().length == 0) {
                        for (MouseListener listener : mouseListenersJCBBrigadasAB) {
                            ab.addMouseListener(listener);
                        }
                    }
                }
            }
        }
    }

    private void soloLecturaJDCBotonFechaNacimiento(boolean b) {
        Component[] comps = jDCFechaNacimiento.getComponents();
        for (Component c : comps) {
            if (c instanceof AbstractButton) {
                AbstractButton ab = (AbstractButton) c;
                if (b) {
                    ab.setVisible(false);
                    if (ab.getMouseListeners().length > 0) {
                        mouseListenersJDCFechaNacimientoAB = ab.getMouseListeners();
                        for (MouseListener listener : mouseListenersJDCFechaNacimientoAB) {
                            ab.removeMouseListener(listener);
                        }
                    }
                } else {
                    ab.setVisible(true);
                    if (ab.getMouseListeners().length == 0) {
                        for (MouseListener listener : mouseListenersJDCFechaNacimientoAB) {
                            ab.addMouseListener(listener);
                        }
                    }
                }
            }
        }
    }

    private void setEnabledDemasDatos(boolean b) {
        jCBBrigadas.setEnabled(b);
        jCBGrupoSanguineo.setEnabled(b);
        jDCFechaNacimiento.setEnabled(b);
        jTFCelular.setEnabled(b);
        jTFNombreCompleto.setEnabled(b);
    }

    private void soloLecturaDemasDatos(boolean b) {
        if (b) {
            jTFNombreCompleto.setEditable(false);
            soloLecturaJCBGruposSanguineos(true);
            soloLecturaJDCBotonFechaNacimiento(true);
            jTFCelular.setEditable(false);
            soloLecturaJCBBrigadas(true);
        } else {
            jTFNombreCompleto.setEditable(true);
            soloLecturaJCBGruposSanguineos(false);
            soloLecturaJDCBotonFechaNacimiento(false);
            jTFCelular.setEditable(true);
            soloLecturaJCBBrigadas(false);
        }
    }

//    private Calendar localDateToCalendar(LocalDate ldate) {
//        ZonedDateTime zonedDateTime = ldate.atStartOfDay(ZoneId.systemDefault());
//        Instant instant = zonedDateTime.toInstant();
//        Date date = Date.from(instant);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        return calendar;
//    }
    private Date localDateToDate(LocalDate ldate) {
        ZonedDateTime zonedDateTime = ldate.atStartOfDay(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    private void modoInhabilitado() {
        /*
            Modo "inhabilitado", se aplica cuando:
        1) No existen brigadas activas y, por tanto, no se puede agregar, modificar o dar de baja a 
        un bombero, pues no existe ninguno activo.
         */

        jBAgregar.setEnabled(false);
        jBBuscar.setEnabled(false);
        jBCancelar.setEnabled(false);
        jBDarDeBaja.setEnabled(false);
        jBGuardar.setEnabled(false);
        jBLimpiar.setEnabled(false);
        jBModificar.setEnabled(false);
        jCBBomberos.setEnabled(false);
        jTFDNI.setEnabled(false);
        setEnabledDemasDatos(false);

        jBSalir.requestFocusInWindow();
    }

    private void modoPrevioABusqueda() {
        /* 
            Modo "previo a búsqueda", se aplica cuando:
        1) La primera vez que se utiliza este JInternalFrame 
        2) Inmediatamente luego de una operación llevada a cabo exitosamente
        3) Se cambia el contenido de "jTextFieldDNI", sin modificar un registro.
         */

        dniRegEncontrado = null;

        if (jCBBomberos.getSelectedIndex() != -1) {
            programaCambiandoJCBBomberos = true;
            jCBBomberos.setSelectedIndex(-1);
            programaCambiandoJCBBomberos = false;
        }
        limpiarEntradasDeJPDemasDatos();
        borrarMensajesMenosEnJCBBomberos();

        jBAgregar.setEnabled(false);
        jBBuscar.setEnabled(true);
        jBCancelar.setEnabled(false);
        jBDarDeBaja.setEnabled(false);
        jBGuardar.setEnabled(false);
        jBLimpiar.setEnabled(false);
        jBModificar.setEnabled(false);
        jCBBomberos.setEnabled(true);
        jTFDNI.setEnabled(true);
        setEnabledDemasDatos(false);

        jTFDNI.setEditable(true);
    }

    private void modoRegistroEncontrado() {
        /* 
            Modo "registro encontrado", se aplica cuando:
        1) Se encuentra un registro mediante "jCBBomberos" o "jBBuscar". 
        2) Se cancela la modificación de un registro.
         */

        if (jCBBomberos.getSelectedIndex() == -1) {
            programaCambiandoJCBBomberos = true;
            jCBBomberos.setSelectedItem(bombero);
            programaCambiandoJCBBomberos = false;
        }

        borrarMensajesDeDemasDatos();

        jLabMensajeDNI.setForeground(Color.BLACK);
        jLabMensajeDNI.setText("<html>Hay un bombero con este DNI entre los "
                + "registrados.</html>");
        jLabMensajeDemasDatos.setForeground(Color.BLUE);
        jLabMensajeDemasDatos.setText("<html>Puede modificar al bombero encontrado haciendo "
                + "click en \"" + jBModificar.getText() + "\" o darle de baja haciendo click en "
                + "\"" + jBDarDeBaja.getText() + "\".</html>");

        jTFDNI.setText(String.valueOf(bombero.getDni()));
        jTFNombreCompleto.setText(bombero.getNombreCompleto());
        jCBGrupoSanguineo.setSelectedItem(bombero.getGrupoSanguineo());
        jDCFechaNacimiento.setDate(localDateToDate(bombero.getFechaNacimiento()));      // nota: verificar si funciona
        jTFCelular.setText(bombero.getCelular());
        for (int i = 0; i < jCBBrigadas.getItemCount(); i++) {
            if (bombero.getBrigada().getNombreBrigada()
                    .equals(jCBBrigadas.getItemAt(i).getNombreBrigada())) {
                jCBBrigadas.setSelectedIndex(i);
            }
        }

        // necesario para compararlo con una hipotética modificación del contenido de 
        // "jTFDNI", en caso de que se proceda a modificar el registro
        dniRegEncontrado = Integer.parseInt(jTFDNI.getText());

        jBAgregar.setEnabled(false);
        jBBuscar.setEnabled(true);
        jBCancelar.setEnabled(false);
        jBDarDeBaja.setEnabled(true);
        jBGuardar.setEnabled(false);
        jBLimpiar.setEnabled(false);
        jBModificar.setEnabled(true);
        jCBBomberos.setEnabled(true);
        jTFDNI.setEnabled(true);
        setEnabledDemasDatos(true);

        jTFDNI.setEditable(true);
        soloLecturaDemasDatos(true);
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

        jLabMensajeDNI.setForeground(Color.BLACK);
        jLabMensajeDNI.setText("<html>No existe un bombero registrado con este "
                + "DNI.</html>");
        jLabMensajeDemasDatos.setForeground(Color.BLUE);
        jLabMensajeDemasDatos.setText("<html>Puede registrar un bombero con el DNI ingresado "
                + "haciendo click en \"" + jBAgregar.getText() + "\" e ingresando los demás "
                + "datos.</html>");

        jBAgregar.setEnabled(true);
        jBBuscar.setEnabled(true);
        jBCancelar.setEnabled(false);
        jBDarDeBaja.setEnabled(false);
        jBGuardar.setEnabled(false);
        jBLimpiar.setEnabled(false);
        jBModificar.setEnabled(false);
        jCBBomberos.setEnabled(true);
        jTFDNI.setEnabled(true);
        setEnabledDemasDatos(false);

        jTFDNI.setEditable(true);
    }

    private void modoOperacion() {
        /* 
            Modo "operación", se aplica cuando: 
        1) Se está agregando o modificando un registro (no aplica para la baja, dado que esta 
        última solo requiere del click en "jbDarDeBaja" y de la confirmación o declinación de 
        la solicitud de confirmación posterior).
         */

        borrarMensajesDeDatos();

        enOperacion = true;

        jBAgregar.setEnabled(false);
        jBBuscar.setEnabled(false);
        jBCancelar.setEnabled(true);
        jBDarDeBaja.setEnabled(false);
        jBGuardar.setEnabled(true);
        jBLimpiar.setEnabled(true);
        jBModificar.setEnabled(false);
        jCBBomberos.setEnabled(false);
        jTFDNI.setEnabled(true);
        setEnabledDemasDatos(true);

        if (enAgregacion) {
            jTFDNI.setEditable(false);
        } else if (enModificacion) {
            jTFDNI.setEditable(true);
        }
        soloLecturaDemasDatos(false);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabDNI = new javax.swing.JLabel();
        jTFDNI = new javax.swing.JTextField();
        jBGuardar = new javax.swing.JButton();
        jBSalir = new javax.swing.JButton();
        jLabGestionBomberos = new javax.swing.JLabel();
        jBAgregar = new javax.swing.JButton();
        jBModificar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jBDarDeBaja = new javax.swing.JButton();
        jLabMensajeDNI = new javax.swing.JLabel();
        jLabBombero = new javax.swing.JLabel();
        jCBBomberos = new javax.swing.JComboBox<>();
        jLabBuscarConCB = new javax.swing.JLabel();
        jLabBuscarConTF = new javax.swing.JLabel();
        jLabDemasDatos = new javax.swing.JLabel();
        jBBuscar = new javax.swing.JButton();
        jLabMensajeDemasDatos = new javax.swing.JLabel();
        jLabMensajeBombero = new javax.swing.JLabel();
        jPDemasDatos = new javax.swing.JPanel();
        jLabBrigada = new javax.swing.JLabel();
        jBLimpiar = new javax.swing.JButton();
        jLabMensajeBrigada = new javax.swing.JLabel();
        jCBBrigadas = new javax.swing.JComboBox<>();
        jLabNombreCompleto = new javax.swing.JLabel();
        jTFNombreCompleto = new javax.swing.JTextField();
        jLabMensajeNomCompleto = new javax.swing.JLabel();
        jLabCelular = new javax.swing.JLabel();
        jTFCelular = new javax.swing.JTextField();
        jLabMensajeCelular = new javax.swing.JLabel();
        jLabFechaNacimiento = new javax.swing.JLabel();
        jDCFechaNacimiento = new com.toedter.calendar.JDateChooser();
        jLabMensajeFechaNacimiento = new javax.swing.JLabel();
        jLabMensajeGrupoSanguineo = new javax.swing.JLabel();
        jCBGrupoSanguineo = new javax.swing.JComboBox<>();
        jLabGrupoSanguineo = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1134, 706));
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

        jLabDNI.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabDNI.setText("DNI:");
        getContentPane().add(jLabDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, -1, -1));

        jTFDNI.setColumns(20);
        jTFDNI.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTFDNI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTFDNIKeyTyped(evt);
            }
        });
        getContentPane().add(jTFDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 200, -1));

        jBGuardar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBGuardar.setText("Guardar");
        jBGuardar.setEnabled(false);
        jBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(jBGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 620, -1, -1));

        jBSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBSalir.setText("Salir");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });
        getContentPane().add(jBSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 620, -1, -1));

        jLabGestionBomberos.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabGestionBomberos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabGestionBomberos.setText("Gestión de bomberos");
        getContentPane().add(jLabGestionBomberos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1070, -1));

        jBAgregar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBAgregar.setText("Agregar bombero");
        jBAgregar.setEnabled(false);
        jBAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(jBAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, -1));

        jBModificar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBModificar.setText("Modificar bombero");
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
        getContentPane().add(jBCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 620, -1, -1));

        jBDarDeBaja.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBDarDeBaja.setText("Dar de baja bombero");
        jBDarDeBaja.setEnabled(false);
        jBDarDeBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDarDeBajaActionPerformed(evt);
            }
        });
        getContentPane().add(jBDarDeBaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, -1, -1));

        jLabMensajeDNI.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabMensajeDNI.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabMensajeDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 305, 340, 40));

        jLabBombero.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabBombero.setText("Bombero:");
        getContentPane().add(jLabBombero, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 133, -1, -1));

        jCBBomberos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jCBBomberos.setMaximumRowCount(10);
        jCBBomberos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBBomberosActionPerformed(evt);
            }
        });
        getContentPane().add(jCBBomberos, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 340, -1));

        jLabBuscarConCB.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabBuscarConCB.setText("Puede seleccionar un bombero de entre los registradas:");
        getContentPane().add(jLabBuscarConCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jLabBuscarConTF.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabBuscarConTF.setText("Puede ingresar un DNI y ver si está registrado como bombero:");
        getContentPane().add(jLabBuscarConTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabDemasDatos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabDemasDatos.setText("Demás datos del bombero:");
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

        jLabMensajeBombero.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabMensajeBombero.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabMensajeBombero, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 165, 340, 40));

        jPDemasDatos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPDemasDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabBrigada.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabBrigada.setText("Brigada:");
        jPDemasDatos.add(jLabBrigada, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        jBLimpiar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBLimpiar.setText("Limpiar campos");
        jBLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimpiarActionPerformed(evt);
            }
        });
        jPDemasDatos.add(jBLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 420, -1, -1));

        jLabMensajeBrigada.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jLabMensajeBrigada, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 360, 320, 40));

        jCBBrigadas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jCBBrigadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, 240, -1));

        jLabNombreCompleto.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabNombreCompleto.setText("Nombre Completo:");
        jPDemasDatos.add(jLabNombreCompleto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jTFNombreCompleto.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jTFNombreCompleto, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 270, -1));

        jLabMensajeNomCompleto.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jLabMensajeNomCompleto, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, 320, 23));

        jLabCelular.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabCelular.setText("Celular:");
        jPDemasDatos.add(jLabCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 90, -1));

        jTFCelular.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jTFCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 140, -1));

        jLabMensajeCelular.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jLabMensajeCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 280, 320, 23));

        jLabFechaNacimiento.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabFechaNacimiento.setText("Fecha de nacimiento:");
        jPDemasDatos.add(jLabFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 140, -1));

        jDCFechaNacimiento.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jDCFechaNacimiento.setMaxSelectableDate(Date.from(((LocalDate.now()).atStartOfDay(ZoneId.systemDefault())).toInstant()));
        jDCFechaNacimiento.setMinSelectableDate(Date.from(((LocalDate.now().minusYears(70)).atStartOfDay(ZoneId.systemDefault())).toInstant()));
        jPDemasDatos.add(jDCFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 140, 30));

        jLabMensajeFechaNacimiento.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jLabMensajeFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 320, 23));

        jLabMensajeGrupoSanguineo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jLabMensajeGrupoSanguineo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 320, 23));

        jCBGrupoSanguineo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jCBGrupoSanguineo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 70, -1));

        jLabGrupoSanguineo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabGrupoSanguineo.setText("Grupo sanguíneo:");
        jPDemasDatos.add(jLabGrupoSanguineo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        getContentPane().add(jPDemasDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 560, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimpiarActionPerformed
        limpiarEntradasDeJPDemasDatos();
    }//GEN-LAST:event_jBLimpiarActionPerformed

    private void jBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarActionPerformed
        borrarMensajesDeDatos();

        boolean entradasValidas = true;

        String strDNI = jTFDNI.getText().trim();
        try {
            int intDNI = Integer.parseInt(strDNI);
            if (bomberoData.estaDNIentreInactivos(intDNI)) {
                entradasValidas = false;
                jLabMensajeDNI.setForeground(Color.RED);
                jLabMensajeDNI.setText("<html>Este DNI se encuentra ocupado por un bombero "
                        + "dado de baja y no puede ser utilizado por otro.</html>");
            } else if (intDNI < 10000000 || intDNI > 99999999) {
                entradasValidas = false;
                jLabMensajeDNI.setForeground(Color.RED);
                jLabMensajeDNI.setText("<html>Debe ingresar un DNI válido.</html>");
            } else {
                Bombero bomberoBuscado = bomberoData.buscarBomberoPorDNI(intDNI);
                if (bomberoBuscado != null) {
                    if (bomberoBuscado.getDni() == dniRegEncontrado) {      // nota: ver si esto funciona
                        bombero.setDni(intDNI);
                    } else {
                        entradasValidas = false;
                        jLabMensajeDNI.setForeground(Color.RED);
                        jLabMensajeDNI.setText("<html>Este DNI se encuentra ocupado por un "
                                + "bombero y no puede ser utilizado por otro.</html>");
                    }
                }
                bombero.setDni(intDNI);
            }
        } catch (NumberFormatException e) {
            entradasValidas = false;
            if (strDNI.isBlank()) {
                jLabMensajeDNI.setForeground(Color.RED);
                jLabMensajeDNI.setText("<html>Debe completar este campo.</html>");
            } else {
                jLabMensajeDNI.setForeground(Color.RED);
                jLabMensajeDNI.setText("<html>Debe ingresar un DNI válido.</html>");
            }
        }

        String nombreCompleto = jTFNombreCompleto.getText();
        if (nombreCompleto.isBlank()) {
            entradasValidas = false;
            jLabMensajeNomCompleto.setForeground(Color.RED);
            jLabMensajeNomCompleto.setText("Debe completar este campo.");
        } else {
            bombero.setNombreCompleto(nombreCompleto);
        }

        if (jCBGrupoSanguineo.getSelectedIndex() == -1) {
            entradasValidas = false;
            jLabMensajeGrupoSanguineo.setForeground(Color.RED);
            jLabMensajeGrupoSanguineo.setText("Debe seleccionar un grupo sanguíneo.");
        } else {
            bombero.setGrupoSanguineo((String) jCBGrupoSanguineo.getSelectedItem());
        }

        Date fechaNacimiento = jDCFechaNacimiento.getDate();
        if (fechaNacimiento == null) {
            entradasValidas = false;
            jLabMensajeFechaNacimiento.setForeground(Color.RED);
            jLabMensajeFechaNacimiento.setText("Debe seleccionar una fecha.");
        } else {
            LocalDate fechaNac = fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).
                    toLocalDate();
            if (fechaNac.isAfter(LocalDate.now().minusYears(18))) {
                entradasValidas = false;
                jLabMensajeFechaNacimiento.setForeground(Color.RED);
                jLabMensajeFechaNacimiento.setText("No puede haber bomberos menores de edad.");
            } else {
                bombero.setFechaNacimiento(fechaNac);
            }
        }

        String celular = jTFCelular.getText();
        if (celular.isBlank()) {
            entradasValidas = false;
            jLabMensajeCelular.setForeground(Color.RED);
            jLabMensajeCelular.setText("Debe completar este campo.");
        } else {
            bombero.setCelular(celular);
        }

        if (jCBBrigadas.getSelectedIndex() == -1) {
            entradasValidas = false;
            jLabMensajeBrigada.setForeground(Color.RED);
            jLabMensajeBrigada.setText("Debe seleccionar un cuartel.");
        } else {
            bombero.setBrigada((Brigada) jCBBrigadas.getSelectedItem());
        }

        if (entradasValidas && enAgregacion) {
            if (bomberoData.guardarBombero(bombero)) {
                jLabAux.setText("<html>Se registró al bombero con DNI: \"" + bombero.getDni()
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarJCBBomberos();
                modoPrevioABusqueda();
                jTFDNI.setText("");
                enOperacion = false;
                enAgregacion = false;
            } else {
                jLabAux.setText("<html>No se pudo registrar al bombero con DNI: \""
                        + bombero.getDni() + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (entradasValidas && enModificacion) {
            if (bomberoData.modificarBombero(bombero)) {
                jLabAux.setText("<html>Se ha modificado al bombero con DNI: \"" + bombero.getDni()
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarJCBBomberos();
                modoPrevioABusqueda();
                jTFDNI.setText("");
                enOperacion = false;
                enModificacion = false;
            } else {
                jLabAux.setText("<html>No se pudo modificar al bombero con DNI: \""
                        + bombero.getDni() + "\".</html>");
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

        jLabMensajeDNI.setText("");
        jLabMensajeDemasDatos.setForeground(Color.BLUE);
        jLabMensajeDemasDatos.setText("<html>Modifique los datos que desee. También puede "
                + "modificar el DNI.</html>");
    }//GEN-LAST:event_jBModificarActionPerformed

    private void jCBBomberosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBBomberosActionPerformed
        bombero = (Bombero) jCBBomberos.getSelectedItem();

        // evita que el programa entre en modo "registro encontrado" cada vez que se genera un 
        // actionEvent en "jCBBomberos" sin la intervención del usuario y, además, el índice 
        // seleccionado en dicho componente es distinto de -1 (situación que ocurre al agregar el 
        // primer item a "jCBBomberos" en "configurarJCBBomberos)
        if (bombero != null && programaCambiandoJCBBomberos == false) {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jCBBomberosActionPerformed

    private void jBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarActionPerformed
        String strDNI = jTFDNI.getText().trim();
        int intDNI;

        try {
            intDNI = Integer.parseInt(strDNI);
            if (intDNI < 10000000 || intDNI > 99999999) {
                jLabMensajeDNI.setForeground(Color.RED);
                jLabMensajeDNI.setText("<html>Debe ingresar un DNI válido.</html>");
                return;
            }
        } catch (NumberFormatException e) {
            if (strDNI.isBlank()) {
                jLabMensajeDNI.setForeground(Color.RED);
                jLabMensajeDNI.setText("<html>Debe ingresar el DNI que desea buscar.</html>");
            } else {
                jLabMensajeDNI.setForeground(Color.RED);
                jLabMensajeDNI.setText("<html>Debe ingresar un DNI válido.</html>");
            }
            return;
        }

        if (bomberoData.estaDNIentreInactivos(intDNI)) {
            jLabMensajeDemasDatos.setText("");
            jLabMensajeDNI.setForeground(Color.RED);
            jLabMensajeDNI.setText("<html>Este DNI ya se encuentra ocupado por un bombero "
                    + "dado de baja. Por favor, ingrese otro.</html>");
            return;
        }

        bombero = bomberoData.buscarBomberoPorDNI(intDNI);
        if (bombero != null) {
            modoRegistroEncontrado();
        } else {
            bombero = new Bombero();
            modoRegistroNoEncontrado();
        }
    }//GEN-LAST:event_jBBuscarActionPerformed

    private void jBAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarActionPerformed
        enAgregacion = true;
        modoOperacion();

        jLabMensajeDNI.setText("");
        jLabMensajeDemasDatos.setForeground(Color.BLUE);
        jLabMensajeDemasDatos.setText("<html>Complete los campos de los demás datos del "
                + "bombero.</html>");
    }//GEN-LAST:event_jBAgregarActionPerformed

    private void jBDarDeBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDarDeBajaActionPerformed
        int dni = bombero.getDni();

        jLabAux.setText("<html>¿Está seguro de querer dar de baja al bombero con DNI: \""
                + dni + "\"?</html>");
        if (JOptionPane.showConfirmDialog(this, jLabAux, "Advertencia", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            if (bomberoData.eliminarBomberoPorDNI(dni)) {
                jLabAux.setText("<html>Se dió de baja al bombero con DNI: \"" + dni
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarJCBBomberos();
                jTFDNI.setText("");
                modoPrevioABusqueda();
            } else {
                jLabAux.setText("<html>No se pudo dar de baja al bombero con DNI: \"" + dni
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBDarDeBajaActionPerformed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        enOperacion = false;
        if (enAgregacion) {
            modoRegistroNoEncontrado();
        } else if (enModificacion) {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jTFDNIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFDNIKeyTyped
        if (!enModificacion) {
            modoPrevioABusqueda();
        }
    }//GEN-LAST:event_jTFDNIKeyTyped

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        configurarJCBBrigadas();

        // parte de un parche para evitar que el enfoque caiga sobre un componente inhabilitado
        // al cambiar de JInternalFrame 
        this.requestFocusInWindow();
    }//GEN-LAST:event_formInternalFrameActivated

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // parte de un parche para evitar que el enfoque caiga sobre un componente inhabilitado
        // al cambiar de JInternalFrame 
        jTFDNI.requestFocusInWindow();
    }//GEN-LAST:event_formFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAgregar;
    private javax.swing.JButton jBBuscar;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBDarDeBaja;
    private javax.swing.JButton jBGuardar;
    private javax.swing.JButton jBLimpiar;
    private javax.swing.JButton jBModificar;
    private javax.swing.JButton jBSalir;
    private javax.swing.JComboBox<Bombero> jCBBomberos;
    private javax.swing.JComboBox<Brigada> jCBBrigadas;
    private javax.swing.JComboBox<String> jCBGrupoSanguineo;
    private com.toedter.calendar.JDateChooser jDCFechaNacimiento;
    private javax.swing.JLabel jLabBombero;
    private javax.swing.JLabel jLabBrigada;
    private javax.swing.JLabel jLabBuscarConCB;
    private javax.swing.JLabel jLabBuscarConTF;
    private javax.swing.JLabel jLabCelular;
    private javax.swing.JLabel jLabDNI;
    private javax.swing.JLabel jLabDemasDatos;
    private javax.swing.JLabel jLabFechaNacimiento;
    private javax.swing.JLabel jLabGestionBomberos;
    private javax.swing.JLabel jLabGrupoSanguineo;
    private javax.swing.JLabel jLabMensajeBombero;
    private javax.swing.JLabel jLabMensajeBrigada;
    private javax.swing.JLabel jLabMensajeCelular;
    private javax.swing.JLabel jLabMensajeDNI;
    private javax.swing.JLabel jLabMensajeDemasDatos;
    private javax.swing.JLabel jLabMensajeFechaNacimiento;
    private javax.swing.JLabel jLabMensajeGrupoSanguineo;
    private javax.swing.JLabel jLabMensajeNomCompleto;
    private javax.swing.JLabel jLabNombreCompleto;
    private javax.swing.JPanel jPDemasDatos;
    private javax.swing.JTextField jTFCelular;
    private javax.swing.JTextField jTFDNI;
    private javax.swing.JTextField jTFNombreCompleto;
    // End of variables declaration//GEN-END:variables
}
