/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package lab1proyectofinal.vistas;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.ToolTipManager;
import javax.swing.text.JTextComponent;
import lab1proyectofinal.entidades.*;
import lab1proyectofinal.accesoADatos.*;

/**
 *
 * @author nahue
 */
public class GestionEmergencia extends javax.swing.JInternalFrame {

    private final BrigadaData brigadaData;
    private final SiniestroData siniestroData;
    private Brigada brigada;
    private Siniestro siniestro;
    private List<Brigada> brigadas;
    private List<Siniestro> siniestros;
    private boolean enOperacion = false;
    private boolean enAgregacion;
    private boolean enModificacion;
    private boolean enEstablecerBrigada;
    private boolean enEstablecerResolucion;
    private boolean programaCambiandoJCBEmergencias;
    private MouseListener[] mouseListenersJCBTipo;
    private MouseListener[] mouseListenersJCBTipoAB;
    private MouseListener[] mouseListenersJDCFechaInicioJTC;
    private MouseListener[] mouseListenersJDCFechaInicioAB;
    private MouseListener[] mouseListenersJDCFechaResolucionJTC;
    private MouseListener[] mouseListenersJDCFechaResolucionAB;
    private MouseListener[] mouseListenersJSpHoraInicioAB;
    private MouseListener[] mouseListenersJSpHoraResolucionAB;
    private JLabel jLabAux = Utils.jLabConfigurado();

    public GestionEmergencia(SiniestroData siniestroData, BrigadaData brigadaData) {
        initComponents();
        this.brigadaData = brigadaData;
        this.siniestroData = siniestroData;
        configurarJCBTipo();
        configurarJCBEmergencias();
        configurarJDCFechaInicio();
        configurarJDCFechaResolucion();
        configurarJSp(jSpHoraInicio);
        configurarJSp(jSpHoraResolucion);
        modoPrevioASeleccion();
    }

    boolean isEnOperacion() {
        return enOperacion;
    }

    void cancelarOperacion() {
        jBCancelar.doClick();
    }

    private void configurarJCBEmergencias() {
        jLabMensajeJCBEmergencias.setText("");

        programaCambiandoJCBEmergencias = true;
        jCBEmergencias.removeAllItems();
        siniestros = siniestroData.listarSiniestros();
        if (siniestros.isEmpty()) {
            jLabMensajeJCBEmergencias.setForeground(Color.BLACK);
            jLabMensajeJCBEmergencias.setText("<html>Advertencia: no hay siniestros cargados en el "
                    + "sistema.</html>");
            programaCambiandoJCBEmergencias = false;
            return;
        }
        for (Siniestro sin : siniestros) {
            jCBEmergencias.addItem(sin);
        }
        programaCambiandoJCBEmergencias = false;
    }

    private void configurarJCBTipo() {
        String tipos[] = Utils.obtenerEspecialidades();
        for (String tipo : tipos) {
            jCBTipos.addItem(tipo);
        }
    }

    private void configurarJDCFechaInicio() {
        Component[] comps = jDCFechaInicio.getComponents();
        for (Component c : comps) {
            if (c instanceof JTextComponent) {
                JTextComponent jtc = (JTextComponent) c;
                jtc.setEditable(false);
                if (jtc.getMouseListeners().length > 0) {
                    mouseListenersJDCFechaInicioJTC = jtc.getMouseListeners();
                    for (MouseListener listener : mouseListenersJDCFechaInicioJTC) {
                        if (listener instanceof ToolTipManager) {
                            ((ToolTipManager) listener).setEnabled(false);  // nota: revisar si es necesario
                        } else {
                            jtc.removeMouseListener(listener);
                        }
                    }
                }
            }
        }
    }

    private void configurarJDCFechaResolucion() {
        Component[] comps = jDCFechaResolucion.getComponents();
        for (Component c : comps) {
            if (c instanceof JTextComponent) {
                JTextComponent jtc = (JTextComponent) c;
                jtc.setEditable(false);
                if (jtc.getMouseListeners().length > 0) {
                    mouseListenersJDCFechaResolucionJTC = jtc.getMouseListeners();
                    for (MouseListener listener : mouseListenersJDCFechaResolucionJTC) {
                        if (listener instanceof ToolTipManager) {
                            ((ToolTipManager) listener).setEnabled(false);  // nota: revisar si es necesario
                        } else {
                            jtc.removeMouseListener(listener);
                        }
                    }
                }
            }
        }
    }

    private void configurarJSp(JSpinner jsp) {
        Component[] comps = jsp.getComponents();
        for (Component c : comps) {
            if (c instanceof JTextComponent) {
                JTextComponent jtc = (JTextComponent) c;
                jtc.setEditable(false);
                // nota: lo de abajo podría ser útil
//                if (jtc.getMouseListeners().length > 0) {
//                    mouseListenersJDCFechaResolucionJTC = jtc.getMouseListeners();
//                    for (MouseListener listener : mouseListenersJDCFechaResolucionJTC) {
//                        if (listener instanceof ToolTipManager) {
//                            ((ToolTipManager) listener).setEnabled(false);  // nota: revisar si es necesario
//                        } else {
//                            jtc.removeMouseListener(listener);
//                        }
//                    }
//                }
            }
        }
    }

    private void limpiarEntradasDeJPDatos() {
        jCBTipos.setSelectedIndex(-1);
        jTFCoorX.setText("");
        jTFCoorY.setText("");
        jTADetalles.setText("");
        jDCFechaInicio.setDate(null);
        jDCFechaResolucion.setDate(null);
        jSpHoraInicio.setValue(null);   // nota: ver si funciona
        jSpHoraResolucion.setValue(null);   // nota: ver si funciona
        jTFBrigada.setText("");
    }

    private void borrarMensajesMenosEnJCBEmergencias() {
        jLabMensajeJCBTipo.setText("");
        jLabMensajeJPDatos.setText("");
        jTFCoorX.setText("");
        jTFCoorY.setText("");
        jLabMensajeJTADetalles.setText("");
        jLabMensajeFechaHoraInicio.setText("");
        jLabMensajeFechaHoraResolucion.setText("");
        jLabMensajeJTFDesempeño.setText("");
        jLabMensajeJTFBrigada.setText("");
    }

    private void borrarMensajesDeJPDatos() {
        jLabMensajeJCBTipo.setText("");
        jLabMensajeCoordenadas.setText("");
        jLabMensajeJTADetalles.setText("");
        jLabMensajeFechaHoraInicio.setText("");
        jLabMensajeFechaHoraResolucion.setText("");
        jLabMensajeJTFDesempeño.setText("");
        jLabMensajeJTFBrigada.setText("");
    }

    // los métodos de "solo lectura" y "lectura-escritura" han sido (en gran medida) copiados de 
    // internet (pueden haber efectos secundarios que no tenga contemplados)                          
    private void soloLecturaJCBTipos(boolean b) {
        if (b) {
            if (jCBTipos.getMouseListeners().length > 0) {
                mouseListenersJCBTipo = jCBTipos.getMouseListeners();
                for (MouseListener listener : mouseListenersJCBTipo) {
                    jCBTipos.removeMouseListener(listener);
                }
            }
        } else {
            if (jCBTipos.getMouseListeners().length == 0) {
                for (MouseListener listener : mouseListenersJCBTipo) {
                    jCBTipos.addMouseListener(listener);
                }
            }
        }

        Component[] comps = jCBTipos.getComponents();
        for (Component c : comps) {
            if (c instanceof AbstractButton) {
                AbstractButton ab = (AbstractButton) c;
                if (b) {
                    ab.setVisible(false);
                    if (ab.getMouseListeners().length > 0) {
                        mouseListenersJCBTipoAB = ab.getMouseListeners();
                        for (MouseListener listener : mouseListenersJCBTipoAB) {
                            ab.removeMouseListener(listener);
                        }
                    }
                } else {
                    ab.setVisible(true);
                    if (ab.getMouseListeners().length == 0) {
                        for (MouseListener listener : mouseListenersJCBTipoAB) {
                            ab.addMouseListener(listener);
                        }
                    }
                }
            }
        }
    }

    private void soloLecturaBotonJDCFechaInicio(boolean b) {
        Component[] comps = jDCFechaInicio.getComponents();
        for (Component c : comps) {
            if (c instanceof AbstractButton) {
                AbstractButton ab = (AbstractButton) c;
                if (b) {
                    ab.setVisible(false);
                    if (ab.getMouseListeners().length > 0) {
                        mouseListenersJDCFechaInicioAB = ab.getMouseListeners();
                        for (MouseListener listener : mouseListenersJDCFechaInicioAB) {
                            ab.removeMouseListener(listener);
                        }
                    }
                } else {
                    ab.setVisible(true);
                    if (ab.getMouseListeners().length == 0) {
                        for (MouseListener listener : mouseListenersJDCFechaInicioAB) {
                            ab.addMouseListener(listener);
                        }
                    }
                }
            }
        }
    }

    private void soloLecturaBotonJDCFechaResolucion(boolean b) {
        Component[] comps = jDCFechaResolucion.getComponents();
        for (Component c : comps) {
            if (c instanceof AbstractButton) {
                AbstractButton ab = (AbstractButton) c;
                if (b) {
                    ab.setVisible(false);
                    if (ab.getMouseListeners().length > 0) {
                        mouseListenersJDCFechaResolucionAB = ab.getMouseListeners();
                        for (MouseListener listener : mouseListenersJDCFechaResolucionAB) {
                            ab.removeMouseListener(listener);
                        }
                    }
                } else {
                    ab.setVisible(true);
                    if (ab.getMouseListeners().length == 0) {
                        for (MouseListener listener : mouseListenersJDCFechaResolucionAB) {
                            ab.addMouseListener(listener);
                        }
                    }
                }
            }
        }
    }

    // nota: revisar si este método afecta a solo un botón de los presentes en el jspinner
    private void soloLecturaBotonJSpHoraInicio(boolean b) {
        Component[] comps = jSpHoraInicio.getComponents();
        for (Component c : comps) {
            if (c instanceof AbstractButton) {
                AbstractButton ab = (AbstractButton) c;
                if (b) {
                    ab.setVisible(false);
                    if (ab.getMouseListeners().length > 0) {
                        mouseListenersJSpHoraInicioAB = ab.getMouseListeners();
                        for (MouseListener listener : mouseListenersJSpHoraInicioAB) {
                            ab.removeMouseListener(listener);
                        }
                    }
                } else {
                    ab.setVisible(true);
                    if (ab.getMouseListeners().length == 0) {
                        for (MouseListener listener : mouseListenersJSpHoraInicioAB) {
                            ab.addMouseListener(listener);
                        }
                    }
                }
            }
        }
    }

    private void soloLecturaBotonJSpHoraResolucion(boolean b) {
        Component[] comps = jSpHoraResolucion.getComponents();
        for (Component c : comps) {
            if (c instanceof AbstractButton) {
                AbstractButton ab = (AbstractButton) c;
                if (b) {
                    ab.setVisible(false);
                    if (ab.getMouseListeners().length > 0) {
                        mouseListenersJSpHoraResolucionAB = ab.getMouseListeners();
                        for (MouseListener listener : mouseListenersJSpHoraResolucionAB) {
                            ab.removeMouseListener(listener);
                        }
                    }
                } else {
                    ab.setVisible(true);
                    if (ab.getMouseListeners().length == 0) {
                        for (MouseListener listener : mouseListenersJSpHoraResolucionAB) {
                            ab.addMouseListener(listener);
                        }
                    }
                }
            }
        }
    }

    private void setEnabledJPCompsDatos(boolean b) {
        jTFBrigada.setEnabled(b);
        jCBTipos.setEnabled(b);
        jTFCoorX.setEnabled(b);
        jTFCoorY.setEnabled(b);
        jTADetalles.setEnabled(b);
        jDCFechaInicio.setEnabled(b);
        jSpHoraInicio.setEnabled(b);
        jDCFechaResolucion.setEnabled(b);
        jSpHoraResolucion.setEnabled(b);
        jTFDesempeño.setEnabled(b);
        jTFBrigada.setEnabled(b);
    }

    private void soloLecturaCompsJPDatosDistintosDeJTFBrigada(boolean b) {
        if (b) {
            soloLecturaJCBTipos(true);
            jTFCoorX.setEditable(false);
            jTFCoorY.setEditable(false);
            jTADetalles.setEditable(false);
            soloLecturaBotonJDCFechaInicio(true);
            soloLecturaBotonJSpHoraInicio(true);
            soloLecturaBotonJDCFechaResolucion(true);
            soloLecturaBotonJSpHoraResolucion(true);
            jTFDesempeño.setEditable(false);
        } else {
            soloLecturaJCBTipos(false);
            jTFCoorX.setEditable(true);
            jTFCoorY.setEditable(true);
            jTADetalles.setEditable(true);
            soloLecturaBotonJDCFechaInicio(false);
            soloLecturaBotonJSpHoraInicio(false);
            soloLecturaBotonJDCFechaResolucion(false);
            soloLecturaBotonJSpHoraResolucion(false);
            jTFDesempeño.setEditable(true);
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

    private void modoPrevioASeleccion() {
        /* 
            Modo "previo a búsqueda", se aplica cuando:
        1) La primera vez que se utiliza este JInternalFrame 
        2) Inmediatamente luego de una operación llevada a cabo exitosamente        
         */

        if (jCBEmergencias.getSelectedIndex() != -1) {
            programaCambiandoJCBEmergencias = true;
            jCBEmergencias.setSelectedIndex(-1);
            programaCambiandoJCBEmergencias = false;
        }
        limpiarEntradasDeJPDatos();
        borrarMensajesMenosEnJCBEmergencias();

        jBAgregar.setEnabled(true);
        jBBrigada.setEnabled(false);
        jBCancelar.setEnabled(false);
        jBEliminar.setEnabled(false);
        jBGuardar.setEnabled(false);
        jBLimpiar.setEnabled(false);
        jBModificar.setEnabled(false);
        jBResolucion.setEnabled(false);
        jCBEmergencias.setEnabled(true);
        setEnabledJPCompsDatos(false);
    }

    private void modoRegistroEncontrado() {
        /* 
            Modo "registro encontrado", se aplica cuando:
        1) Se encuentra un registro mediante "jCBEmergencias". 
        2) Se cancela la modificación de un registro, la asignación de una brigada o la asignación
        de una resolución.
         */

        borrarMensajesDeJPDatos();

        jLabMensajeJPDatos.setForeground(Color.BLUE);
        jLabMensajeJPDatos.setText("<html>Puede modificar la emergencia encontrada haciendo "
                + "click en \"" + jBModificar.getText() + "\", asignarle una brigada haciendo "
                + "click en \"" + jBBrigada + "\", asignarle una resolución haciendo click en "
                + "\"" + jBResolucion + "\" o eliminarla haciendo click en "
                + "\"" + jBEliminar.getText() + "\".</html>");
        jCBTipos.setSelectedItem(siniestro.getTipo());
        jTFCoorX.setText(String.valueOf(siniestro.getCoordenadaX()));
        jTFCoorY.setText(String.valueOf(siniestro.getCoordenadaY()));
        jDCFechaInicio.setDate(localDateToDate(siniestro.getFechaHoraInicio().toLocalDate()));
        jSpHoraInicio.setValue(siniestro.getFechaHoraInicio().toLocalTime()); // nota: verificar si funciona
        jTADetalles.setText(siniestro.getDetalles());
        if (siniestro.getBrigada() != null) {
            jTFBrigada.setText(siniestro.getBrigada().getNombreBrigada());
        } else {
            jTFBrigada.setText("");
            jLabMensajeJTFBrigada.setText("No se ha asignado brigada.");
        }
        if (siniestro.getFechaHoraResolucion() != null) {    // nota: revisar si funciona
            jDCFechaResolucion.setDate(localDateToDate(siniestro.getFechaHoraResolucion().toLocalDate()));
            jSpHoraResolucion.setValue(siniestro.getFechaHoraResolucion().toLocalTime()); // nota: verificar si funciona
        } else {
            jDCFechaResolucion.setDate(null);
            jSpHoraInicio.setValue(null);   // nota: revisar si funciona
        }
        jTFDesempeño.setText(String.valueOf(siniestro.getPuntuacion()));

        jBAgregar.setEnabled(false);
        jBBrigada.setEnabled(true);
        jBCancelar.setEnabled(false);
        jBEliminar.setEnabled(true);
        jBGuardar.setEnabled(false);
        jBLimpiar.setEnabled(false);
        jBModificar.setEnabled(true);
        jBResolucion.setEnabled(true);
        jCBEmergencias.setEnabled(true);
        setEnabledJPCompsDatos(true);

        soloLecturaCompsJPDatosDistintosDeJTFBrigada(true);
    }

    private void modoOperacion() {
        /* 
            Modo "operación", se aplica cuando: 
        1) Se está agregando o modificando un registro (no aplica para la baja, dado que esta 
        última solo requiere del click en "jbDarDeBaja" y de la confirmación o declinación de 
        la solicitud de confirmación posterior).
         */

        borrarMensajesMenosEnJCBEmergencias();
        enOperacion = true;

        jBAgregar.setEnabled(false);
        jBBrigada.setEnabled(false);
        jBCancelar.setEnabled(true);
        jBEliminar.setEnabled(false);
        jBGuardar.setEnabled(true);
        jBLimpiar.setEnabled(true);
        jBModificar.setEnabled(false);
        jBResolucion.setEnabled(false);
        jCBEmergencias.setEnabled(false);
        setEnabledJPCompsDatos(true);

        soloLecturaCompsJPDatosDistintosDeJTFBrigada(false);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBGuardar = new javax.swing.JButton();
        jBSalir = new javax.swing.JButton();
        jLabGestionEmergencias = new javax.swing.JLabel();
        jBCancelar = new javax.swing.JButton();
        jLabEmergencia = new javax.swing.JLabel();
        jCBEmergencias = new javax.swing.JComboBox<>();
        jLabBuscarConCB = new javax.swing.JLabel();
        jLabDatos = new javax.swing.JLabel();
        jLabMensajeJPDatos = new javax.swing.JLabel();
        jLabMensajeJCBEmergencias = new javax.swing.JLabel();
        jPDatos = new javax.swing.JPanel();
        jBLimpiar = new javax.swing.JButton();
        jLabFechaInicio = new javax.swing.JLabel();
        jDCFechaInicio = new com.toedter.calendar.JDateChooser();
        jLabMensajeFechaHoraInicio = new javax.swing.JLabel();
        jLabMensajeJCBTipo = new javax.swing.JLabel();
        jCBTipos = new javax.swing.JComboBox<>();
        jLabTipo = new javax.swing.JLabel();
        jTFCoorX = new javax.swing.JTextField();
        jLabMensajeCoordenadas = new javax.swing.JLabel();
        jLabCoorX = new javax.swing.JLabel();
        jLabCoorY = new javax.swing.JLabel();
        jTFCoorY = new javax.swing.JTextField();
        jLabDetalles = new javax.swing.JLabel();
        jScPDetalles = new javax.swing.JScrollPane();
        jTADetalles = new javax.swing.JTextArea();
        jLabMensajeJTADetalles = new javax.swing.JLabel();
        jLabMensajeFechaHoraResolucion = new javax.swing.JLabel();
        jDCFechaResolucion = new com.toedter.calendar.JDateChooser();
        jLabJDCFechaResolucion = new javax.swing.JLabel();
        jLabMensajeJTFDesempeño = new javax.swing.JLabel();
        jTFDesempeño = new javax.swing.JTextField();
        jLabDesempeño = new javax.swing.JLabel();
        jSpHoraResolucion = new javax.swing.JSpinner();
        jSpHoraInicio = new javax.swing.JSpinner();
        jTFBrigada = new javax.swing.JTextField();
        jLabBrigada = new javax.swing.JLabel();
        jLabMensajeJTFBrigada = new javax.swing.JLabel();
        jBAgregar = new javax.swing.JButton();
        jBModificar = new javax.swing.JButton();
        jBEliminar = new javax.swing.JButton();
        jBBrigada = new javax.swing.JButton();
        jBResolucion = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1300, 750));
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

        jBGuardar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBGuardar.setText("Guardar");
        jBGuardar.setEnabled(false);
        jBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(jBGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 670, -1, -1));

        jBSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBSalir.setText("Salir");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });
        getContentPane().add(jBSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 670, -1, -1));

        jLabGestionEmergencias.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabGestionEmergencias.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabGestionEmergencias.setText("Gestión de emergencias");
        getContentPane().add(jLabGestionEmergencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 1020, -1));

        jBCancelar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBCancelar.setText("Cancelar");
        jBCancelar.setEnabled(false);
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(jBCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 670, -1, -1));

        jLabEmergencia.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabEmergencia.setText("Emergencia:");
        getContentPane().add(jLabEmergencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jCBEmergencias.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jCBEmergencias.setMaximumRowCount(10);
        jCBEmergencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBEmergenciasActionPerformed(evt);
            }
        });
        getContentPane().add(jCBEmergencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 340, -1));

        jLabBuscarConCB.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabBuscarConCB.setText("Puede seleccionar una emergencia de entre las registradas:");
        getContentPane().add(jLabBuscarConCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        jLabDatos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabDatos.setText("Datos de la emergencia:");
        getContentPane().add(jLabDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, -1));

        jLabMensajeJPDatos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabMensajeJPDatos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabMensajeJPDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 1030, 23));

        jLabMensajeJCBEmergencias.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabMensajeJCBEmergencias.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabMensajeJCBEmergencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 340, 23));

        jPDatos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jBLimpiar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBLimpiar.setText("Limpiar campos");
        jBLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimpiarActionPerformed(evt);
            }
        });
        jPDatos.add(jBLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 370, -1, -1));

        jLabFechaInicio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabFechaInicio.setText("Fecha de inicio:");
        jPDatos.add(jLabFechaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        jDCFechaInicio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jDCFechaInicio.setMaxSelectableDate(Date.from(((LocalDate.now()).atStartOfDay(ZoneId.systemDefault())).toInstant()));
        jDCFechaInicio.setMinSelectableDate(Date.from(((LocalDate.now().minusYears(70)).atStartOfDay(ZoneId.systemDefault())).toInstant()));
        jPDatos.add(jDCFechaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, 140, 30));

        jLabMensajeFechaHoraInicio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDatos.add(jLabMensajeFechaHoraInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 320, 320, 46));

        jLabMensajeJCBTipo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDatos.add(jLabMensajeJCBTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 310, 23));

        jCBTipos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDatos.add(jCBTipos, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 320, -1));

        jLabTipo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabTipo.setText("Tipo:");
        jPDatos.add(jLabTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        jTFCoorX.setEditable(false);
        jTFCoorX.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTFCoorX.setEnabled(false);
        jPDatos.add(jTFCoorX, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 75, -1));

        jLabMensajeCoordenadas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDatos.add(jLabMensajeCoordenadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 116, 310, 23));

        jLabCoorX.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabCoorX.setText("Coordenada X:");
        jPDatos.add(jLabCoorX, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabCoorY.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabCoorY.setText("Coordenada Y:");
        jPDatos.add(jLabCoorY, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, -1, -1));

        jTFCoorY.setEditable(false);
        jTFCoorY.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTFCoorY.setEnabled(false);
        jPDatos.add(jTFCoorY, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, 75, -1));

        jLabDetalles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabDetalles.setText("Detalles:");
        jPDatos.add(jLabDetalles, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, -1, -1));

        jTADetalles.setColumns(20);
        jTADetalles.setRows(5);
        jScPDetalles.setViewportView(jTADetalles);

        jPDatos.add(jScPDetalles, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 320, 120));

        jLabMensajeJTADetalles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDatos.add(jLabMensajeJTADetalles, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, 310, 23));

        jLabMensajeFechaHoraResolucion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDatos.add(jLabMensajeFechaHoraResolucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 140, 280, 46));

        jDCFechaResolucion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jDCFechaResolucion.setMaxSelectableDate(Date.from(((LocalDate.now()).atStartOfDay(ZoneId.systemDefault())).toInstant()));
        jDCFechaResolucion.setMinSelectableDate(Date.from(((LocalDate.now().minusYears(70)).atStartOfDay(ZoneId.systemDefault())).toInstant()));
        jPDatos.add(jDCFechaResolucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 110, 140, 30));

        jLabJDCFechaResolucion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabJDCFechaResolucion.setText("Fecha de resolución:");
        jPDatos.add(jLabJDCFechaResolucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, -1, -1));

        jLabMensajeJTFDesempeño.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDatos.add(jLabMensajeJTFDesempeño, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 230, 440, 23));

        jTFDesempeño.setEditable(false);
        jTFDesempeño.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTFDesempeño.setEnabled(false);
        jPDatos.add(jTFDesempeño, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 200, 75, -1));

        jLabDesempeño.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabDesempeño.setText("Desempeño en el tratamiento de la emergencia:");
        jPDatos.add(jLabDesempeño, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, -1, -1));

        jSpHoraResolucion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jSpHoraResolucion.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1702076735463L), null, new java.util.Date(), java.util.Calendar.SECOND));
        jSpHoraResolucion.setEditor(new javax.swing.JSpinner.DateEditor(jSpHoraResolucion, "HH:mm:ss"));
        jPDatos.add(jSpHoraResolucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 110, 100, 30));

        jSpHoraInicio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jSpHoraInicio.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1702076735463L), null, new java.util.Date(), java.util.Calendar.SECOND));
        jSpHoraInicio.setEditor(new javax.swing.JSpinner.DateEditor(jSpHoraInicio, "HH:mm:ss"));
        jPDatos.add(jSpHoraInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 290, 100, 30));

        jTFBrigada.setEditable(false);
        jTFBrigada.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDatos.add(jTFBrigada, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, 250, -1));

        jLabBrigada.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabBrigada.setText("Brigada:");
        jPDatos.add(jLabBrigada, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, -1, -1));

        jLabMensajeJTFBrigada.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDatos.add(jLabMensajeJTFBrigada, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 70, 280, 23));

        getContentPane().add(jPDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 1050, 420));

        jBAgregar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBAgregar.setText("Agregar emergencia");
        jBAgregar.setEnabled(false);
        jBAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(jBAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, -1, -1));

        jBModificar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBModificar.setText("Modificar emergencia");
        jBModificar.setEnabled(false);
        jBModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBModificarActionPerformed(evt);
            }
        });
        getContentPane().add(jBModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 100, -1, -1));

        jBEliminar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBEliminar.setText("Eliminar emergencia");
        jBEliminar.setEnabled(false);
        jBEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(jBEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 100, -1, -1));

        jBBrigada.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBBrigada.setText("Asignar brigada");
        getContentPane().add(jBBrigada, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 150, 150, -1));

        jBResolucion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBResolucion.setText("Asignar resolución");
        getContentPane().add(jBResolucion, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 150, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimpiarActionPerformed
        limpiarEntradasDeJPDatos();
    }//GEN-LAST:event_jBLimpiarActionPerformed

    private void jBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarActionPerformed
        borrarMensajesDeJPDatos();

        boolean entradasValidas = true;

        if (enAgregacion || enModificacion) {
            if (jCBTipos.getSelectedIndex() == -1) {
                entradasValidas = false;
                jLabMensajeJCBTipo.setForeground(Color.RED);
                jLabMensajeJCBTipo.setText("Debe seleccionar un tipo de emergencia.");
            } else {
                siniestro.setTipo((String) jCBTipos.getSelectedItem());
            }

            String strCoordenadaX = jTFCoorX.getText();
            String strCoordenadaY = jTFCoorY.getText();
            try {
                int intCoordernadaX = Integer.parseInt(strCoordenadaX);
                int intCoordernadaY = Integer.parseInt(strCoordenadaY);
                siniestro.setCoordenadaX(intCoordernadaX);
                siniestro.setCoordenadaY(intCoordernadaY);
            } catch (NumberFormatException e) {
                entradasValidas = false;
                if (strCoordenadaX.isBlank() && strCoordenadaY.isBlank()) {
                    jLabMensajeCoordenadas.setForeground(Color.RED);
                    jLabMensajeCoordenadas.setText("<html>Debe completar los campos de coordenadas.</html>");
                } else if (strCoordenadaX.isBlank()) {
                    jLabMensajeCoordenadas.setForeground(Color.RED);
                    jLabMensajeCoordenadas.setText("<html>Debe completar los campo de la coordenada X.</html>");
                } else if (strCoordenadaY.isBlank()) {
                    jLabMensajeCoordenadas.setForeground(Color.RED);
                    jLabMensajeCoordenadas.setText("<html>Debe completar los campo de la coordenada Y.</html>");
                } else {
                    jLabMensajeCoordenadas.setForeground(Color.RED);
                    jLabMensajeCoordenadas.setText("<html>Las coordenadas deben ser números enteros.</html>");
                }
            }

            String detalles = jTADetalles.getText();
            if (detalles.isBlank()) {
                entradasValidas = false;
                jLabMensajeJTADetalles.setForeground(Color.RED);
                jLabMensajeJTADetalles.setText("Debe completar este campo.");
            } else {
                siniestro.setDetalles(detalles);
            }

            // nota: revisar si esto funciona
            LocalDate fechaInicio = jDCFechaInicio.getDate().toInstant().atZone(ZoneId.systemDefault()).
                    toLocalDate();
            LocalTime horaInicio = LocalTime.parse(String.valueOf(jSpHoraInicio.getValue()),
                    DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            if (fechaInicio == null && horaInicio == null) {
                entradasValidas = false;
                jLabMensajeFechaHoraInicio.setForeground(Color.RED);
                jLabMensajeFechaHoraInicio.setText("Debe seleccionar una fecha y una hora.");
            } else if (fechaInicio == null) {
                entradasValidas = false;
                jLabMensajeFechaHoraInicio.setForeground(Color.RED);
                jLabMensajeFechaHoraInicio.setText("Debe seleccionar una fecha.");
            } else if (horaInicio == null) {
                entradasValidas = false;
                jLabMensajeFechaHoraInicio.setForeground(Color.RED);
                jLabMensajeFechaHoraInicio.setText("Debe seleccionar una hora.");
            } else {
                LocalDateTime fechaHoraInicio = LocalDateTime.of(fechaInicio, horaInicio);
                if (fechaHoraInicio.isAfter(LocalDateTime.now())) {
                    entradasValidas = false;
                    jLabMensajeFechaHoraInicio.setForeground(Color.RED);
                    jLabMensajeFechaHoraInicio.setText("<html>La fecha y hora de inicio del incidente no pueden ser posteriores a la fecha y hora actual.</html>");
                } else {
                    siniestro.setFechaHoraInicio(fechaHoraInicio);
                }
            }

            if (entradasValidas && enAgregacion) {
                if (siniestroData.guardarSiniestro(siniestro)) {
                    jLabAux.setText("<html>Se registró la emergencia.</html>");
                    JOptionPane.showMessageDialog(this, jLabAux, "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                    configurarJCBEmergencias();
                    modoPrevioASeleccion();
                    enOperacion = false;
                    enAgregacion = false;
                } else {
                    jLabAux.setText("<html>No se pudo registrar la emergencia.</html>");
                    JOptionPane.showMessageDialog(this, jLabAux, "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (entradasValidas && enModificacion) {
                if (siniestroData.modificarSiniestro(siniestro)) {
                    jLabAux.setText("<html>Se ha modificado la emergencia.</html>");
                    JOptionPane.showMessageDialog(this, jLabAux, "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                    configurarJCBEmergencias();
                    modoPrevioASeleccion();
                    enOperacion = false;
                    enModificacion = false;
                } else {
                    jLabAux.setText("<html>No se pudo modificar la emergencia.</html>");
                    JOptionPane.showMessageDialog(this, jLabAux, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (enEstablecerResolucion) {
            // nota: revisar si esto funciona
            LocalDate fechaResolucion = jDCFechaInicio.getDate().toInstant().atZone(ZoneId.systemDefault()).
                    toLocalDate();
            LocalTime horaResolucion = LocalTime.parse(String.valueOf(jSpHoraInicio.getValue()),
                    DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            if (fechaResolucion == null && horaResolucion == null) {
                entradasValidas = false;
                jLabMensajeFechaHoraResolucion.setForeground(Color.RED);
                jLabMensajeFechaHoraResolucion.setText("Debe seleccionar una fecha y una hora.");
            } else if (fechaResolucion == null) {
                entradasValidas = false;
                jLabMensajeFechaHoraResolucion.setForeground(Color.RED);
                jLabMensajeFechaHoraResolucion.setText("Debe seleccionar una fecha.");
            } else if (horaResolucion == null) {
                entradasValidas = false;
                jLabMensajeFechaHoraResolucion.setForeground(Color.RED);
                jLabMensajeFechaHoraResolucion.setText("Debe seleccionar una hora.");
            } else {
                LocalDateTime fechaHoraResolucion = LocalDateTime.of(fechaResolucion, horaResolucion);
                if (fechaHoraResolucion.isAfter(LocalDateTime.now())) {
                    entradasValidas = false;
                    jLabMensajeFechaHoraResolucion.setForeground(Color.RED);
                    jLabMensajeFechaHoraResolucion.setText("<html>La fecha y hora de resolución de la emergencia no pueden ser posteriores a la fecha y hora actual.</html>");
                } else if (siniestro.getFechaHoraInicio().isAfter(fechaHoraResolucion)) {
                    entradasValidas = false;
                    jLabMensajeFechaHoraResolucion.setForeground(Color.RED);
                    jLabMensajeFechaHoraResolucion.setText("<html>La fecha y hora de resolución de la emergencia  no pueden ser anteriores a la fecha y hora de inicio de ka misma.</html>");
                } else {
                    siniestro.setFechaHoraResolucion(fechaHoraResolucion);
                }
            }

            String strDesempeño = jTFDesempeño.getText().trim();
            try {
                int intDesempeño = Integer.parseInt(strDesempeño);
                if (intDesempeño < 1 || intDesempeño > 10) {
                    entradasValidas = false;
                    jLabMensajeJTFDesempeño.setForeground(Color.RED);
                    jLabMensajeJTFDesempeño.setText("<html>Debe ingresar un número entero entre el 1 y el 10, inclusive.</html>");
                    siniestro.setPuntuacion(intDesempeño);
                }
            } catch (NumberFormatException e) {
                entradasValidas = false;
                if (strDesempeño.isBlank()) {
                    jLabMensajeJTFDesempeño.setForeground(Color.RED);
                    jLabMensajeJTFDesempeño.setText("<html>Debe completar este campo.</html>");
                } else {
                    jLabMensajeJTFDesempeño.setForeground(Color.RED);
                    jLabMensajeJTFDesempeño.setText("<html>Debe ingresar un número entero entre el 1 y el 10, inclusive.</html>");
                }
            }

            if (entradasValidas) {
                if (siniestroData.modificarSiniestro(siniestro)) {
                    jLabAux.setText("<html>Se agregó la resolución a la emergencia.</html>");
                    JOptionPane.showMessageDialog(this, jLabAux, "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                    configurarJCBEmergencias();
                    modoPrevioASeleccion();
                    enOperacion = false;
                    enEstablecerResolucion = false;
                } else {
                    jLabAux.setText("<html>No se pudo agregar la resolución a la emergencia.</html>");
                    JOptionPane.showMessageDialog(this, jLabAux, "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jBGuardarActionPerformed

    private void jBSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalirActionPerformed
        this.hide();
    }//GEN-LAST:event_jBSalirActionPerformed

    private void jBModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModificarActionPerformed
        enModificacion = true;
        modoOperacion();

        jLabMensajeJPDatos.setForeground(Color.BLUE);
        jLabMensajeJPDatos.setText("<html>Modifique los datos que desee. Recuerde que si desea "
                + "cambiar la brigada asignada o algún dato referido a la resolución de la "
                + "emergencia, debe hacer click en los botones \"" + jBBrigada + "\" o "
                + "\"" + jBResolucion + "\", respectivamente.</html>");
    }//GEN-LAST:event_jBModificarActionPerformed

    private void jCBEmergenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBEmergenciasActionPerformed
        siniestro = (Siniestro) jCBEmergencias.getSelectedItem();

        // evita que el programa entre en modo "registro encontrado" cada vez que se genera un 
        // actionEvent en "jCBEmergencias" sin la intervención del usuario y, además, el índice 
        // seleccionado en dicho componente es distinto de -1 (situación que ocurre al agregar el 
        // primer item a "jCBEmergencias" en "configurarJCBEmergencias)
        if (siniestro != null && programaCambiandoJCBEmergencias == false) {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jCBEmergenciasActionPerformed

    private void jBAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarActionPerformed
        enAgregacion = true;
        modoOperacion();

        jLabMensajeJPDatos.setForeground(Color.BLUE);
        jLabMensajeJPDatos.setText("<html>Complete los campos de los datos solicitados de la "
                + "emergencia.</html>");
    }//GEN-LAST:event_jBAgregarActionPerformed

    private void jBEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEliminarActionPerformed
        jLabAux.setText("<html>¿Está seguro de querer eliminar esta emergencia de los registros?</html>");
        if (JOptionPane.showConfirmDialog(this, jLabAux, "Advertencia", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            if (siniestroData.eliminarSiniestro(siniestro.getCodigoSiniestro())) {
                jLabAux.setText("<html>Se eliminó la emergencia de los registros.</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarJCBEmergencias();
                modoPrevioASeleccion();
            } else {
                jLabAux.setText("<html>No se pudo eliminar la emergencia de los registros.</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBEliminarActionPerformed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        enOperacion = false;
        modoRegistroEncontrado();
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // parte de un parche para evitar que el enfoque caiga sobre un componente inhabilitado
        // al cambiar de JInternalFrame 
        this.requestFocusInWindow();
    }//GEN-LAST:event_formInternalFrameActivated

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // parte de un parche para evitar que el enfoque caiga sobre un componente inhabilitado
        // al cambiar de JInternalFrame 
        jCBEmergencias.requestFocusInWindow();
    }//GEN-LAST:event_formFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAgregar;
    private javax.swing.JButton jBBrigada;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBEliminar;
    private javax.swing.JButton jBGuardar;
    private javax.swing.JButton jBLimpiar;
    private javax.swing.JButton jBModificar;
    private javax.swing.JButton jBResolucion;
    private javax.swing.JButton jBSalir;
    private javax.swing.JComboBox<Siniestro> jCBEmergencias;
    private javax.swing.JComboBox<String> jCBTipos;
    private com.toedter.calendar.JDateChooser jDCFechaInicio;
    private com.toedter.calendar.JDateChooser jDCFechaResolucion;
    private javax.swing.JLabel jLabBrigada;
    private javax.swing.JLabel jLabBuscarConCB;
    private javax.swing.JLabel jLabCoorX;
    private javax.swing.JLabel jLabCoorY;
    private javax.swing.JLabel jLabDatos;
    private javax.swing.JLabel jLabDesempeño;
    private javax.swing.JLabel jLabDetalles;
    private javax.swing.JLabel jLabEmergencia;
    private javax.swing.JLabel jLabFechaInicio;
    private javax.swing.JLabel jLabGestionEmergencias;
    private javax.swing.JLabel jLabJDCFechaResolucion;
    private javax.swing.JLabel jLabMensajeCoordenadas;
    private javax.swing.JLabel jLabMensajeFechaHoraInicio;
    private javax.swing.JLabel jLabMensajeFechaHoraResolucion;
    private javax.swing.JLabel jLabMensajeJCBEmergencias;
    private javax.swing.JLabel jLabMensajeJCBTipo;
    private javax.swing.JLabel jLabMensajeJPDatos;
    private javax.swing.JLabel jLabMensajeJTADetalles;
    private javax.swing.JLabel jLabMensajeJTFBrigada;
    private javax.swing.JLabel jLabMensajeJTFDesempeño;
    private javax.swing.JLabel jLabTipo;
    private javax.swing.JPanel jPDatos;
    private javax.swing.JScrollPane jScPDetalles;
    private javax.swing.JSpinner jSpHoraInicio;
    private javax.swing.JSpinner jSpHoraResolucion;
    private javax.swing.JTextArea jTADetalles;
    private javax.swing.JTextField jTFBrigada;
    private javax.swing.JTextField jTFCoorX;
    private javax.swing.JTextField jTFCoorY;
    private javax.swing.JTextField jTFDesempeño;
    // End of variables declaration//GEN-END:variables
}
