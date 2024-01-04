/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package lab1proyectofinal.vistas;

import java.awt.Color;
import java.awt.Component;
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
    private boolean enModoPrevioABusqueda;
    private boolean enAgregacion;
    private boolean enModificacion;
    private boolean programaCambiandoJCBBrigadas;
    private String nombreRegEncontrado;
    private MouseListener[] mouseListenersJCBEspecialidades;
    private MouseListener[] mouseListenersJCBEspecialidadesAB;
    private MouseListener[] mouseListenersJCBCuarteles;
    private MouseListener[] mouseListenersJCBCuartelesAB;
    private MouseListener[] mouseListenersJRBEnServicio;
    private JLabel jLabAux = Utils.jLabConfigurado();

    public GestionBrigada(CuartelData cuartelData, BrigadaData brigadaData) {
        initComponents();
        this.cuartelData = cuartelData;
        this.brigadaData = brigadaData;
        configurarJCBEspecialidades();
        configurarJCBBrigadas();
        modoPrevioABusqueda();
    }

    public boolean isEnAgregacion() {
        return enAgregacion;
    }

    public boolean isEnModificacion() {
        return enModificacion;
    }

    void cancelarOperacion() {
        jBCancelar.doClick();
    }

    private void configurarJCBBrigadas() {
        jLabMensajeBrigada.setText("");

        programaCambiandoJCBBrigadas = true;
        jCBBrigadas.removeAllItems();
        listaBrigada = brigadaData.listarBrigadas();
        if (listaBrigada.isEmpty()) {
            jLabMensajeBrigada.setForeground(Color.BLUE);
            jLabMensajeBrigada.setText("<html>No hay brigadas cargadas en el "
                    + "sistema.</html>");
            programaCambiandoJCBBrigadas = false;
            return;
        }
        for (Brigada bri : listaBrigada) {
            jCBBrigadas.addItem(bri);
        }
        programaCambiandoJCBBrigadas = false;
    }

    private void configurarJCBEspecialidades() {
        String especialidades[] = Utils.obtenerEspecialidades();
        for (String especialidad : especialidades) {
            jCBEspecialidades.addItem(especialidad);
        }
    }

    private void configurarJCBCuarteles() {
        jLabMensajeCuarteles.setText("");

        jCBCuarteles.removeAllItems();
        listaCuartel = cuartelData.listarCuarteles();
        if (listaCuartel.isEmpty()) {
            jLabMensajeCuarteles.setForeground(Color.BLUE);
            jLabMensajeCuarteles.setText("<html>No hay cuarteles cargados en el "
                    + "sistema.</html>");
            modoInhabilitado();
            jLabAux.setText("<html>No hay cuarteles registrados. No se puede agregar, modificar "
                    + "o dar de baja brigadas hasta que se registre un cuartel.</html>");
            JOptionPane.showMessageDialog(this, jLabAux, "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (jTFNombre.getText().isBlank()) {
            modoPrevioABusqueda();
        }
        for (Cuartel cuar : listaCuartel) {
            jCBCuarteles.addItem(cuar);
        }
        if (nombreRegEncontrado != null) {
            jCBCuarteles.setSelectedItem(brigada.getCuartel());
            return;
        }
        jCBCuarteles.setSelectedIndex(-1);
    }

    private void limpiarEntradasDeJPDemasDatos() {
        jCBEspecialidades.setSelectedIndex(-1);
        BGEnServicio.clearSelection();
        jCBCuarteles.setSelectedIndex(-1);
    }

    private void borrarMensajesMenosEnJCBBrigadas() {
        jLabMensajeNombre.setText("");
        jLabMensajeDemasDatos.setText("");
        jLabMensajeEspecialidades.setText("");
        jLabMensajeEnServicio.setText("");
        jLabMensajeCuarteles.setText("");
    }

    private void borrarMensajesDeDatos() {
        jLabMensajeNombre.setText("");
        jLabMensajeEspecialidades.setText("");
        jLabMensajeEnServicio.setText("");
        jLabMensajeCuarteles.setText("");
    }

    private void borrarMensajesDeDemasDatos() {
        jLabMensajeEspecialidades.setText("");
        jLabMensajeEnServicio.setText("");
        jLabMensajeCuarteles.setText("");
    }

    // los métodos de "solo lectura" y "lectura-escritura" han sido (en gran medida) copiados de 
    // internet (pueden haber efectos secundarios que no tenga contemplados)
    private void soloLecturaJCBEspecialidades(boolean b) {
        if (b) {
            if (jCBEspecialidades.getMouseListeners().length > 0) {
                mouseListenersJCBEspecialidades = jCBEspecialidades.getMouseListeners();
                for (MouseListener listener : mouseListenersJCBEspecialidades) {
                    jCBEspecialidades.removeMouseListener(listener);
                }
            }
        } else {
            if (jCBEspecialidades.getMouseListeners().length == 0) {
                for (MouseListener listener : mouseListenersJCBEspecialidades) {
                    jCBEspecialidades.addMouseListener(listener);
                }
            }
        }

        Component[] comps = jCBEspecialidades.getComponents();
        for (Component c : comps) {
            if (c instanceof AbstractButton) {
                AbstractButton ab = (AbstractButton) c;
                if (b) {
                    ab.setVisible(false);
                    if (ab.getMouseListeners().length > 0) {
                        mouseListenersJCBEspecialidadesAB = ab.getMouseListeners();
                        for (MouseListener listener : mouseListenersJCBEspecialidadesAB) {
                            ab.removeMouseListener(listener);
                        }
                    }
                } else {
                    ab.setVisible(true);
                    if (ab.getMouseListeners().length == 0) {
                        for (MouseListener listener : mouseListenersJCBEspecialidadesAB) {
                            ab.addMouseListener(listener);
                        }
                    }
                }
            }
        }
    }

    private void soloLecturaJCBCuarteles(boolean b) {
        if (b) {
            if (jCBCuarteles.getMouseListeners().length > 0) {
                mouseListenersJCBCuarteles = jCBCuarteles.getMouseListeners();
                for (MouseListener listener : mouseListenersJCBCuarteles) {
                    jCBCuarteles.removeMouseListener(listener);
                }
            }
        } else {
            if (jCBCuarteles.getMouseListeners().length == 0) {
                for (MouseListener listener : mouseListenersJCBCuarteles) {
                    jCBCuarteles.addMouseListener(listener);
                }
            }
        }

        Component[] comps = jCBCuarteles.getComponents();
        for (Component c : comps) {
            if (c instanceof AbstractButton) {
                AbstractButton ab = (AbstractButton) c;
                if (b) {
                    ab.setVisible(false);
                    if (ab.getMouseListeners().length > 0) {
                        mouseListenersJCBCuartelesAB = ab.getMouseListeners();
                        for (MouseListener listener : mouseListenersJCBCuartelesAB) {
                            ab.removeMouseListener(listener);
                        }
                    }
                } else {
                    ab.setVisible(true);
                    if (ab.getMouseListeners().length == 0) {
                        for (MouseListener listener : mouseListenersJCBCuartelesAB) {
                            ab.addMouseListener(listener);
                        }
                    }
                }
            }
        }
    }

    private void soloLecturaJRB(JRadioButton jrb, boolean b) {
        if (b) {
            if (jrb.getMouseListeners().length > 0) {
                mouseListenersJRBEnServicio = jrb.getMouseListeners();
                for (MouseListener listener : mouseListenersJRBEnServicio) {
                    jrb.removeMouseListener(listener);
                }
            }
        } else {
            if (jrb.getMouseListeners().length == 0) {
                for (MouseListener listener : mouseListenersJRBEnServicio) {
                    jrb.addMouseListener(listener);
                }
            }
        }
    }

    private void setEnabledDemasDatos(boolean b) {
        jCBEspecialidades.setEnabled(b);
        jRBEnServicioNo.setEnabled(b);
        jRBEnServicioSi.setEnabled(b);
        jCBCuarteles.setEnabled(b);
    }

    private void soloLecturaDemasDatos(boolean b) {
        if (b) {
            soloLecturaJCBEspecialidades(b);
            soloLecturaJRB(jRBEnServicioSi, b);
            soloLecturaJRB(jRBEnServicioNo, b);
            soloLecturaJCBCuarteles(b);
        } else {
            soloLecturaJCBEspecialidades(b);
            soloLecturaJRB(jRBEnServicioSi, b);
            soloLecturaJRB(jRBEnServicioNo, b);
            soloLecturaJCBCuarteles(b);
        }
    }

    private void modoInhabilitado() {
        /*
            Modo "inhabilitado", se aplica cuando:
        1) No existen cuarteles activos y, por tanto, no se puede agregar, modificar o dar de baja a 
        una brigada, pues no existe ninguna activa.
         */

        enModoPrevioABusqueda = false;
        enAgregacion = false;
        enModificacion = false;

        jBAgregar.setEnabled(false);
        jBModificar.setEnabled(false);
        jBDarDeBaja.setEnabled(false);
        jBLimpiar.setEnabled(false);
        jBGuardar.setEnabled(false);
        jBCancelar.setEnabled(false);
        jBBuscar.setEnabled(false);
        jCBBrigadas.setEnabled(false);
        jTFNombre.setEnabled(false);
        setEnabledDemasDatos(false);

        jBSalir.requestFocusInWindow();
    }

    private void modoPrevioABusqueda() {
        /* 
            Modo "previo a búsqueda", se aplica cuando:
        1) La primera vez que se utiliza este JInternalFrame 
        2) Inmediatamente luego de una operación llevada a cabo exitosamente
        3) Se cambia el contenido de "jTextFieldNombre", sin modificar un registro.
         */

        enModoPrevioABusqueda = true;
        enAgregacion = false;
        enModificacion = false;

        nombreRegEncontrado = null;

        if (jCBBrigadas.getSelectedIndex() != -1) {
            programaCambiandoJCBBrigadas = true;
            jCBBrigadas.setSelectedIndex(-1);
            programaCambiandoJCBBrigadas = false;
        }
        limpiarEntradasDeJPDemasDatos();
        borrarMensajesMenosEnJCBBrigadas();

        jBAgregar.setEnabled(false);
        jBBuscar.setEnabled(true);
        jBCancelar.setEnabled(false);
        jBDarDeBaja.setEnabled(false);
        jBGuardar.setEnabled(false);
        jBLimpiar.setEnabled(false);
        jBModificar.setEnabled(false);
        jCBBrigadas.setEnabled(true);
        jTFNombre.setEnabled(true);
        setEnabledDemasDatos(false);

        jTFNombre.setEditable(true);
    }

    private void modoRegistroEncontrado() {
        /* 
            Modo "registro encontrado", se aplica cuando:
        1) Se encuentra un registro mediante "jComboBoxBrigadas" o "jButtonBuscar". 
        2) Se cancela la modificación de un registro.
         */

        enModoPrevioABusqueda = false;
        enAgregacion = false;
        enModificacion = false;

        if (jCBBrigadas.getSelectedIndex() == -1) {
            programaCambiandoJCBBrigadas = true;
            jCBBrigadas.setSelectedItem(brigada);
            programaCambiandoJCBBrigadas = false;
        }

        borrarMensajesDeDemasDatos();

        jLabMensajeNombre.setForeground(Color.BLUE);
        jLabMensajeNombre.setText("<html>Hay una brigada con este nombre entre las "
                + "registradas.</html>");
        jLabMensajeDemasDatos.setForeground(Color.BLUE);
        jLabMensajeDemasDatos.setText("<html>Puede modificar la brigada encontrada haciendo "
                + "click en \"" + jBModificar.getText() + "\" o darle de baja haciendo click en "
                + "\"" + jBDarDeBaja.getText() + "\".</html>");

        jTFNombre.setText(brigada.getNombreBrigada());
        jCBEspecialidades.setSelectedItem(brigada.getEspecialidad());
        if (brigada.isEnServicio()) {
            jRBEnServicioSi.setSelected(true);
        } else {
            jRBEnServicioNo.setSelected(true);
        }
        for (int i = 0; i < jCBCuarteles.getItemCount(); i++) {
            if (brigada.getCuartel().getNombreCuartel()
                    .equals(jCBCuarteles.getItemAt(i).getNombreCuartel())) {
                jCBCuarteles.setSelectedIndex(i);
            }
        }

        // necesario para compararlo con una hipotética modificación del contenido de 
        // "jTextFieldNombre", en caso de que se proceda a modificar el registro
        nombreRegEncontrado = jTFNombre.getText();

        jBAgregar.setEnabled(false);
        jBBuscar.setEnabled(true);
        jBCancelar.setEnabled(false);
        jBDarDeBaja.setEnabled(true);
        jBGuardar.setEnabled(false);
        jBLimpiar.setEnabled(false);
        jBModificar.setEnabled(true);
        jCBBrigadas.setEnabled(true);
        jTFNombre.setEnabled(true);
        setEnabledDemasDatos(true);

        jTFNombre.setEditable(true);
        soloLecturaDemasDatos(true);
    }

    private void modoRegistroNoEncontrado() {
        /* 
            Modo "registro no encontrado", se aplica cuando: 
        1) Se ha clickeado en "jButtonBuscar" y el nombre ingresado no se corresponde con el de 
        ningún registro en la BD (ni activo ni inactivo).
        2) Se cancela la agregación de un registro.
         */

        enModoPrevioABusqueda = false;
        enAgregacion = false;
        enModificacion = false;

        limpiarEntradasDeJPDemasDatos();
        borrarMensajesDeDemasDatos();

        jLabMensajeNombre.setForeground(Color.BLUE);
        jLabMensajeNombre.setText("<html>No existe una brigada registrada con este "
                + "nombre.</html>");
        jLabMensajeDemasDatos.setForeground(Color.BLUE);
        jLabMensajeDemasDatos.setText("<html>Puede registrar una brigada con el nombre ingresado "
                + "haciendo click en \"" + jBAgregar.getText() + "\" e ingresando los demás "
                + "datos.</html>");

        jBAgregar.setEnabled(true);
        jBBuscar.setEnabled(true);
        jBCancelar.setEnabled(false);
        jBDarDeBaja.setEnabled(false);
        jBGuardar.setEnabled(false);
        jBLimpiar.setEnabled(false);
        jBModificar.setEnabled(false);
        jCBBrigadas.setEnabled(true);
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

        enModoPrevioABusqueda = false;

        borrarMensajesDeDatos();

        jBAgregar.setEnabled(false);
        jBBuscar.setEnabled(false);
        jBCancelar.setEnabled(true);
        jBDarDeBaja.setEnabled(false);
        jBGuardar.setEnabled(true);
        jBLimpiar.setEnabled(true);
        jBModificar.setEnabled(false);
        jCBBrigadas.setEnabled(false);
        jTFNombre.setEnabled(true);
        setEnabledDemasDatos(true);

        if (enAgregacion) {
            jTFNombre.setEditable(false);
        } else if (enModificacion) {
            jTFNombre.setEditable(true);
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

        BGEnServicio = new javax.swing.ButtonGroup();
        jLabNombre = new javax.swing.JLabel();
        jTFNombre = new javax.swing.JTextField();
        jBGuardar = new javax.swing.JButton();
        jBSalir = new javax.swing.JButton();
        jLabGestionBrigadas = new javax.swing.JLabel();
        jBAgregar = new javax.swing.JButton();
        jBModificar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jBDarDeBaja = new javax.swing.JButton();
        jLabMensajeNombre = new javax.swing.JLabel();
        jCBBrigadas = new javax.swing.JComboBox<>();
        jLabBuscarConCB = new javax.swing.JLabel();
        jLabBuscarConTF = new javax.swing.JLabel();
        jLabDemasDatos = new javax.swing.JLabel();
        jBBuscar = new javax.swing.JButton();
        jLabMensajeDemasDatos = new javax.swing.JLabel();
        jLabMensajeBrigada = new javax.swing.JLabel();
        jPDemasDatos = new javax.swing.JPanel();
        jLabEspecialidad = new javax.swing.JLabel();
        jLabCuartel = new javax.swing.JLabel();
        jLabMensajeEnServicio = new javax.swing.JLabel();
        jLabMensajeEspecialidades = new javax.swing.JLabel();
        jBLimpiar = new javax.swing.JButton();
        jLabEnServicio = new javax.swing.JLabel();
        jCBEspecialidades = new javax.swing.JComboBox<>();
        jRBEnServicioSi = new javax.swing.JRadioButton();
        jRBEnServicioNo = new javax.swing.JRadioButton();
        jLabMensajeCuarteles = new javax.swing.JLabel();
        jCBCuarteles = new javax.swing.JComboBox<>();

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

        jLabNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabNombre.setText("Nombre:");
        getContentPane().add(jLabNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        jTFNombre.setColumns(20);
        jTFNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTFNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTFNombreKeyTyped(evt);
            }
        });
        getContentPane().add(jTFNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, 250, -1));

        jBGuardar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBGuardar.setText("Guardar");
        jBGuardar.setEnabled(false);
        jBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(jBGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 460, -1, -1));

        jBSalir.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBSalir.setText("Salir");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });
        getContentPane().add(jBSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 460, -1, -1));

        jLabGestionBrigadas.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabGestionBrigadas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabGestionBrigadas.setText("Gestión de brigadas");
        getContentPane().add(jLabGestionBrigadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1070, -1));

        jBAgregar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBAgregar.setText("Agregar brigada");
        jBAgregar.setEnabled(false);
        jBAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(jBAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, -1));

        jBModificar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBModificar.setText("Modificar brigada");
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
        getContentPane().add(jBCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 460, -1, -1));

        jBDarDeBaja.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBDarDeBaja.setText("Dar de baja brigada");
        jBDarDeBaja.setEnabled(false);
        jBDarDeBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDarDeBajaActionPerformed(evt);
            }
        });
        getContentPane().add(jBDarDeBaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, -1, -1));

        jLabMensajeNombre.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabMensajeNombre.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabMensajeNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, 340, 40));

        jCBBrigadas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jCBBrigadas.setMaximumRowCount(10);
        jCBBrigadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBBrigadasActionPerformed(evt);
            }
        });
        getContentPane().add(jCBBrigadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 380, -1));

        jLabBuscarConCB.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabBuscarConCB.setText("Puede seleccionar una brigada de entre las registradas:");
        getContentPane().add(jLabBuscarConCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jLabBuscarConTF.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabBuscarConTF.setText("Puede ingresar un nombre y ver si está registrado como brigada:");
        getContentPane().add(jLabBuscarConTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabDemasDatos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabDemasDatos.setText("Demás datos de la brigada:");
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

        jLabMensajeBrigada.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabMensajeBrigada.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabMensajeBrigada, new org.netbeans.lib.awtextra.AbsoluteConstraints(152, 165, 340, 40));

        jPDemasDatos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPDemasDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabEspecialidad.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabEspecialidad.setText("Especialidad:");
        jPDemasDatos.add(jLabEspecialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 24, -1, -1));

        jLabCuartel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabCuartel.setText("Cuartel:");
        jPDemasDatos.add(jLabCuartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 172, -1, -1));

        jLabMensajeEnServicio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jLabMensajeEnServicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 125, 340, 23));

        jLabMensajeEspecialidades.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jLabMensajeEspecialidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 55, 340, 23));

        jBLimpiar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jBLimpiar.setText("Limpiar campos");
        jBLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimpiarActionPerformed(evt);
            }
        });
        jPDemasDatos.add(jBLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 260, -1, -1));

        jLabEnServicio.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabEnServicio.setText("En servicio:");
        jPDemasDatos.add(jLabEnServicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 95, -1, -1));

        jCBEspecialidades.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jCBEspecialidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 340, -1));

        BGEnServicio.add(jRBEnServicioSi);
        jRBEnServicioSi.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jRBEnServicioSi.setText("Si");
        jPDemasDatos.add(jRBEnServicioSi, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 95, -1, -1));

        BGEnServicio.add(jRBEnServicioNo);
        jRBEnServicioNo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jRBEnServicioNo.setText("No");
        jPDemasDatos.add(jRBEnServicioNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 95, -1, -1));

        jLabMensajeCuarteles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jLabMensajeCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 202, 340, 40));

        jCBCuarteles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPDemasDatos.add(jCBCuarteles, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 167, 200, -1));

        getContentPane().add(jPDemasDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 500, 310));

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
            } else if (brigadaData.estaNombreEntreInactivos(nombre)) {
                entradasValidas = false;
                jLabMensajeNombre.setForeground(Color.RED);
                jLabMensajeNombre.setText("<html>Este nombre se encuentra ocupado por una brigada "
                        + "dada de baja y no puede ser utilizado por otra.</html>");
            } else {
                Brigada brigadaBuscada = brigadaData.buscarBrigadaPorNombre(nombre);
                if (brigadaBuscada != null) {
                    if (brigadaBuscada.getNombreBrigada().equals(nombreRegEncontrado)) {
                        brigada.setNombreBrigada(nombre);
                    } else {
                        entradasValidas = false;
                        jLabMensajeNombre.setForeground(Color.RED);
                        jLabMensajeNombre.setText("<html>Este nombre se encuentra ocupado por una "
                                + "brigada y no puede ser utilizado por otra.</html>");
                    }
                } else {
                    brigada.setNombreBrigada(nombre);
                }
            }
        } else {
            brigada.setNombreBrigada(nombre);
        }

        if (jCBEspecialidades.getSelectedIndex() > -1) {
            brigada.setEspecialidad((String) jCBEspecialidades.getSelectedItem());
        } else {
            entradasValidas = false;
            jLabMensajeEspecialidades.setForeground(Color.RED);
            jLabMensajeEspecialidades.setText("Debe seleccionar una especialidad.");
        }

        if (jRBEnServicioSi.isSelected()) {
            brigada.setEnServicio(true);
        } else if (jRBEnServicioNo.isSelected()) {
            brigada.setEnServicio(false);
        } else {
            entradasValidas = false;
            jLabMensajeEnServicio.setForeground(Color.RED);
            jLabMensajeEnServicio.setText("Debe indicar si la brigada está en servicio.");
        }

        if (jCBCuarteles.getSelectedIndex() == -1) {
            entradasValidas = false;
            jLabMensajeCuarteles.setForeground(Color.RED);
            jLabMensajeCuarteles.setText("Debe seleccionar un cuartel.");
        } else {
            brigada.setCuartel((Cuartel) jCBCuarteles.getSelectedItem());
        }

        if (entradasValidas && enAgregacion) {
            if (brigadaData.guardarBrigada(brigada)) {
                jLabAux.setText("<html>Se registró la brigada \"" + brigada.getNombreBrigada()
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarJCBBrigadas();
                modoRegistroEncontrado();
            } else {
                jLabAux.setText("<html>No se pudo registrar la brigada \""
                        + brigada.getNombreBrigada() + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (entradasValidas && enModificacion) {
            if (brigadaData.modificarBrigada(brigada)) {
                jLabAux.setText("<html>Se ha modificado la brigada \"" + brigada.getNombreBrigada()
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarJCBBrigadas();
                modoRegistroEncontrado();
            } else {
                jLabAux.setText("<html>No se pudo modificar la brigada \""
                        + brigada.getNombreBrigada() + "\".</html>");
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

    private void jCBBrigadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBBrigadasActionPerformed
        brigada = (Brigada) jCBBrigadas.getSelectedItem();

        // evita que el programa entre en modo "registro encontrado" cada vez que se genera un 
        // actionEvent en "jCBBrigadas" sin la intervención del usuario y, además, el índice 
        // seleccionado en dicho componente es distinto de -1 (situación que ocurre al agregar el 
        // primer item a "jCBBrigadas" en "configurarJCBBrigadas)        
        if (brigada != null && programaCambiandoJCBBrigadas == false) {
            modoRegistroEncontrado();
        }
    }//GEN-LAST:event_jCBBrigadasActionPerformed

    private void jBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarActionPerformed
        String nombreBrigada = jTFNombre.getText().trim();

        if (nombreBrigada.isBlank()) {
            jLabMensajeDemasDatos.setText("");
            jLabMensajeNombre.setForeground(Color.RED);
            jLabMensajeNombre.setText("<html>El nombre no puede estar compuesto solo por "
                    + "espacios en blanco.</html>");
            return;
        }
        if (brigadaData.estaNombreEntreInactivos(nombreBrigada)) {
            jLabMensajeDemasDatos.setText("");
            jLabMensajeNombre.setForeground(Color.RED);
            jLabMensajeNombre.setText("<html>Este nombre ya se encuentra ocupado por una brigada "
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
    }//GEN-LAST:event_jBBuscarActionPerformed

    private void jBAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarActionPerformed
        enAgregacion = true;
        modoOperacion();

        jLabMensajeNombre.setText("");
        jLabMensajeDemasDatos.setForeground(Color.BLUE);
        jLabMensajeDemasDatos.setText("<html>Complete los campos de los demás datos de la "
                + "brigada.</html>");
    }//GEN-LAST:event_jBAgregarActionPerformed

    private void jBDarDeBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDarDeBajaActionPerformed
        String nombreBrigada = brigada.getNombreBrigada();

        jLabAux.setText("<html>¿Está seguro de querer dar de baja a la brigada \""
                + nombreBrigada + "\"?</html>");
        if (JOptionPane.showConfirmDialog(this, jLabAux, "Advertencia", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            if (brigadaData.eliminarBrigadaPorNombre(nombreBrigada)) {
                jLabAux.setText("<html>Se dió de baja a la brigada \"" + nombreBrigada
                        + "\".</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                configurarJCBBrigadas();
                jTFNombre.setText("");
                modoPrevioABusqueda();
            } else {
                jLabAux.setText("<html>No se pudo dar de baja a la brigada \"" + nombreBrigada
                        + "\". Asegurese de que no hay bomberos activos en esta brigada antes de "
                        + "intentar darle de baja.</html>");
                JOptionPane.showMessageDialog(this, jLabAux, "Error",
                        JOptionPane.ERROR_MESSAGE);
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
        if (!enModificacion && !enModoPrevioABusqueda) {
            modoPrevioABusqueda();
        }
    }//GEN-LAST:event_jTFNombreKeyTyped

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        configurarJCBCuarteles();

        // parte de un parche para evitar que el enfoque caiga sobre un componente inhabilitado
        // al cambiar de JInternalFrame 
        this.requestFocusInWindow();
    }//GEN-LAST:event_formInternalFrameActivated

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // parte de un parche para evitar que el enfoque caiga sobre un componente inhabilitado
        // al cambiar de JInternalFrame
        jTFNombre.requestFocusInWindow();
    }//GEN-LAST:event_formFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGEnServicio;
    private javax.swing.JButton jBAgregar;
    private javax.swing.JButton jBBuscar;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBDarDeBaja;
    private javax.swing.JButton jBGuardar;
    private javax.swing.JButton jBLimpiar;
    private javax.swing.JButton jBModificar;
    private javax.swing.JButton jBSalir;
    private javax.swing.JComboBox<Brigada> jCBBrigadas;
    private javax.swing.JComboBox<Cuartel> jCBCuarteles;
    private javax.swing.JComboBox<String> jCBEspecialidades;
    private javax.swing.JLabel jLabBuscarConCB;
    private javax.swing.JLabel jLabBuscarConTF;
    private javax.swing.JLabel jLabCuartel;
    private javax.swing.JLabel jLabDemasDatos;
    private javax.swing.JLabel jLabEnServicio;
    private javax.swing.JLabel jLabEspecialidad;
    private javax.swing.JLabel jLabGestionBrigadas;
    private javax.swing.JLabel jLabMensajeBrigada;
    private javax.swing.JLabel jLabMensajeCuarteles;
    private javax.swing.JLabel jLabMensajeDemasDatos;
    private javax.swing.JLabel jLabMensajeEnServicio;
    private javax.swing.JLabel jLabMensajeEspecialidades;
    private javax.swing.JLabel jLabMensajeNombre;
    private javax.swing.JLabel jLabNombre;
    private javax.swing.JPanel jPDemasDatos;
    private javax.swing.JRadioButton jRBEnServicioNo;
    private javax.swing.JRadioButton jRBEnServicioSi;
    private javax.swing.JTextField jTFNombre;
    // End of variables declaration//GEN-END:variables
}
