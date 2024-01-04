package lab1proyectofinal.vistas;

import java.beans.PropertyVetoException;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import lab1proyectofinal.accesoADatos.BomberoData;
import lab1proyectofinal.accesoADatos.BrigadaData;
import lab1proyectofinal.accesoADatos.CuartelData;
import lab1proyectofinal.accesoADatos.EmergenciaData;
import lab1proyectofinal.accesoADatos.Utils;
import lab1proyectofinal.entidades.Brigada;

/**
 *
 * @author Grupo-3
 */
public class MainFrame extends javax.swing.JFrame {

    private JInternalFrame focusedFrame = null;
    private final GestionCuartel gestionCuartel;
    private final ListadosPorCuartel listadosPorCuartel;
    private final GestionBrigada gestionBrigada;
    private final ListadosPorBrigada listadosPorBrigada;
    private final BrigadasSegunSituacion brigadasSegunSituacion;
    private final GestionBombero gestionBombero;
    private final ListadoDeBomberos listadoDeBomberos;
    private final GestionEmergencia gestionEmergencia;
    private final ListadoDeEmergencias listadoDeEmergencias;
    private JLabel jLabAux = Utils.jLabConfigurado();

    private final BomberoData bomberoData;
    private final BrigadaData brigadaData;
    private final CuartelData cuartelData;
    private final EmergenciaData emergenciaData;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();

        bomberoData = new BomberoData();
        brigadaData = new BrigadaData();
        cuartelData = new CuartelData();
        emergenciaData = new EmergenciaData();

        // Gestion Cuartel 
        gestionCuartel = new GestionCuartel(cuartelData);
        DesktopPane.add(gestionCuartel);

        // Listados por cuartel
        listadosPorCuartel = new ListadosPorCuartel(cuartelData);
        DesktopPane.add(listadosPorCuartel);

        // Gestión Brigada 
        gestionBrigada = new GestionBrigada(cuartelData, brigadaData);
        DesktopPane.add(gestionBrigada);

        // Listados por brigada
        listadosPorBrigada = new ListadosPorBrigada(brigadaData);
        DesktopPane.add(listadosPorBrigada);

        // Brigadas según situación
        brigadasSegunSituacion = new BrigadasSegunSituacion(brigadaData);
        DesktopPane.add(brigadasSegunSituacion);

        // Gestión Bomberos
        gestionBombero = new GestionBombero(brigadaData, bomberoData);
        DesktopPane.add(gestionBombero);

        // Listado de bomberos
        listadoDeBomberos = new ListadoDeBomberos(bomberoData);
        DesktopPane.add(listadoDeBomberos);

        // Gestión Emergencias & tratamiento de emergencia
        gestionEmergencia = new GestionEmergencia(emergenciaData);
        DesktopPane.add(gestionEmergencia);
        DesktopPane.add(gestionEmergencia.getTratamientoDeEmergencia());

        // Listado de emergencias
        listadoDeEmergencias = new ListadoDeEmergencias(emergenciaData);
        DesktopPane.add(listadoDeEmergencias);
    }

    public GestionEmergencia getGestionEmergencia() {
        return gestionEmergencia;
    }

    void focusIFrame(JInternalFrame iFrame) {
        if (focusedFrame != null) {
            // si se está llevando a cabo alguna operación en 'focusedFrame' y se quiere pasar a un
            // JInternalFrame distinto sin antes haber terminado la operación 
            if (focusedFrame instanceof GestionBrigada
                    && !(iFrame instanceof GestionBrigada)
                    && (((GestionBrigada) focusedFrame).isEnAgregacion()
                    || ((GestionBrigada) focusedFrame).isEnModificacion())) {
                jLabAux.setText("""
                    <html>Si elige salir de 'Gestión de brigadas' con una 
                    operación en curso la operación se cancelará: 
                    ¿desea salir de 'Gestión de brigadas'?</hmtl>""");
                if (JOptionPane.showInternalConfirmDialog(focusedFrame,
                        jLabAux,
                        "Advertencias",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION) {
                    return;
                } else {
                    // visualmente se ve mejor que se esconda el internalFrame antes del click 
                    // programado en 'jButtonCancelar'                    
                    focusedFrame.hide();
                    ((GestionBrigada) focusedFrame).cancelarOperacion();
                }
            }

            if (focusedFrame instanceof GestionBombero
                    && !(iFrame instanceof GestionBombero)
                    && (((GestionBombero) focusedFrame).isEnAgregacion()
                    || ((GestionBombero) focusedFrame).isEnModificacion())) {
                jLabAux.setText("""
                    <html>Si elige salir de 'Gestión de bomberos' con una 
                    operación en curso la operación se cancelará: 
                    ¿desea salir de 'Gestión de bomberos'?</hmtl>""");
                if (JOptionPane.showInternalConfirmDialog(focusedFrame,
                        jLabAux,
                        "Advertencias",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION) {
                    return;
                } else {
                    focusedFrame.hide();
                    ((GestionBombero) focusedFrame).cancelarOperacion();
                }
            }

            if (focusedFrame instanceof GestionEmergencia
                    && !(iFrame instanceof GestionEmergencia)
                    && (((GestionEmergencia) focusedFrame).isEnAgregacion()
                    || ((GestionEmergencia) focusedFrame).isEnModificacion())) {
                jLabAux.setText("""
                    <html>Si elige salir de 'Gestión de emergencias' con una 
                    operación en curso la operación se cancelará: 
                    ¿desea salir de 'Gestión de emergencias'?</hmtl>""");
                if (JOptionPane.showInternalConfirmDialog(focusedFrame,
                        jLabAux,
                        "Advertencias",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION) {
                    return;
                } else {
                    focusedFrame.hide();
                    ((GestionEmergencia) focusedFrame).cancelarOperacion();
                }
            }
            focusedFrame.hide();
        }

        focusedFrame = iFrame;
        focusedFrame.setVisible(true);
        focusedFrame.setLocation(0, 0);
        focusedFrame.moveToFront();
        try {
            focusedFrame.setSelected(true);
        } catch (PropertyVetoException e) {
            System.out.println("[Main Frame Error]");
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DesktopPane = new javax.swing.JDesktopPane();
        JMenuBarEntidades = new javax.swing.JMenuBar();
        JMCuarteles = new javax.swing.JMenu();
        JMIGestionCuarteles = new javax.swing.JMenuItem();
        JMIListadosPorCuartel = new javax.swing.JMenuItem();
        JMBrigadas = new javax.swing.JMenu();
        JMIGestionBrigadas = new javax.swing.JMenuItem();
        jMIListadosPorBrigada = new javax.swing.JMenuItem();
        jMIBrigadasSegunSituacion = new javax.swing.JMenuItem();
        jMBomberos = new javax.swing.JMenu();
        jMIGestionBomberos = new javax.swing.JMenuItem();
        jMIListadoBomberos = new javax.swing.JMenuItem();
        jMEmergencias = new javax.swing.JMenu();
        jMIGestionEmergencias = new javax.swing.JMenuItem();
        jMIListadoEmergencias = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestión Bomberos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setPreferredSize(new java.awt.Dimension(800, 850));
        setSize(new java.awt.Dimension(800, 850));

        javax.swing.GroupLayout DesktopPaneLayout = new javax.swing.GroupLayout(DesktopPane);
        DesktopPane.setLayout(DesktopPaneLayout);
        DesktopPaneLayout.setHorizontalGroup(
            DesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        DesktopPaneLayout.setVerticalGroup(
            DesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 898, Short.MAX_VALUE)
        );

        JMCuarteles.setText("Cuarteles");
        JMCuarteles.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        JMIGestionCuarteles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        JMIGestionCuarteles.setText("Gestión de cuarteles");
        JMIGestionCuarteles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMIGestionCuartelesActionPerformed(evt);
            }
        });
        JMCuarteles.add(JMIGestionCuarteles);

        JMIListadosPorCuartel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        JMIListadosPorCuartel.setText("Listados por cuartel");
        JMIListadosPorCuartel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMIListadosPorCuartelActionPerformed(evt);
            }
        });
        JMCuarteles.add(JMIListadosPorCuartel);

        JMenuBarEntidades.add(JMCuarteles);

        JMBrigadas.setText("Brigadas");
        JMBrigadas.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        JMIGestionBrigadas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        JMIGestionBrigadas.setText("Gestión de brigadas");
        JMIGestionBrigadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMIGestionBrigadasActionPerformed(evt);
            }
        });
        JMBrigadas.add(JMIGestionBrigadas);

        jMIListadosPorBrigada.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jMIListadosPorBrigada.setText("Listados por brigada");
        jMIListadosPorBrigada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIListadosPorBrigadaActionPerformed(evt);
            }
        });
        JMBrigadas.add(jMIListadosPorBrigada);

        jMIBrigadasSegunSituacion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jMIBrigadasSegunSituacion.setText("Brigadas según situación");
        jMIBrigadasSegunSituacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIBrigadasSegunSituacionActionPerformed(evt);
            }
        });
        JMBrigadas.add(jMIBrigadasSegunSituacion);

        JMenuBarEntidades.add(JMBrigadas);

        jMBomberos.setText("Bomberos");
        jMBomberos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jMIGestionBomberos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jMIGestionBomberos.setText("Gestión de bomberos");
        jMIGestionBomberos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIGestionBomberosActionPerformed(evt);
            }
        });
        jMBomberos.add(jMIGestionBomberos);

        jMIListadoBomberos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jMIListadoBomberos.setText("Listado de bomberos");
        jMIListadoBomberos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIListadoBomberosActionPerformed(evt);
            }
        });
        jMBomberos.add(jMIListadoBomberos);

        JMenuBarEntidades.add(jMBomberos);

        jMEmergencias.setText("Emergencias");
        jMEmergencias.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jMIGestionEmergencias.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jMIGestionEmergencias.setText("Gestión de emergencias");
        jMIGestionEmergencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIGestionEmergenciasActionPerformed(evt);
            }
        });
        jMEmergencias.add(jMIGestionEmergencias);

        jMIListadoEmergencias.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jMIListadoEmergencias.setText("Listados de emergencias");
        jMIListadoEmergencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIListadoEmergenciasActionPerformed(evt);
            }
        });
        jMEmergencias.add(jMIListadoEmergencias);

        JMenuBarEntidades.add(jMEmergencias);

        setJMenuBar(JMenuBarEntidades);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DesktopPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DesktopPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JMIGestionCuartelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMIGestionCuartelesActionPerformed
        focusIFrame(gestionCuartel);
    }//GEN-LAST:event_JMIGestionCuartelesActionPerformed

    private void jMIGestionBomberosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIGestionBomberosActionPerformed
        focusIFrame(gestionBombero);
    }//GEN-LAST:event_jMIGestionBomberosActionPerformed

    private void jMIGestionEmergenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIGestionEmergenciasActionPerformed
        focusIFrame(gestionEmergencia);
    }//GEN-LAST:event_jMIGestionEmergenciasActionPerformed

    private void JMIListadosPorCuartelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMIListadosPorCuartelActionPerformed
        focusIFrame(listadosPorCuartel);
    }//GEN-LAST:event_JMIListadosPorCuartelActionPerformed

    private void JMIGestionBrigadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMIGestionBrigadasActionPerformed
        focusIFrame(gestionBrigada);
    }//GEN-LAST:event_JMIGestionBrigadasActionPerformed

    private void jMIBrigadasSegunSituacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIBrigadasSegunSituacionActionPerformed
        focusIFrame(brigadasSegunSituacion);
    }//GEN-LAST:event_jMIBrigadasSegunSituacionActionPerformed

    private void jMIListadosPorBrigadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIListadosPorBrigadaActionPerformed
        focusIFrame(listadosPorBrigada);
    }//GEN-LAST:event_jMIListadosPorBrigadaActionPerformed

    private void jMIListadoBomberosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIListadoBomberosActionPerformed
        focusIFrame(listadoDeBomberos);
    }//GEN-LAST:event_jMIListadoBomberosActionPerformed

    private void jMIListadoEmergenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIListadoEmergenciasActionPerformed
        focusIFrame(listadoDeEmergencias);
    }//GEN-LAST:event_jMIListadoEmergenciasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void ejecutar(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane DesktopPane;
    private javax.swing.JMenu JMBrigadas;
    private javax.swing.JMenu JMCuarteles;
    private javax.swing.JMenuItem JMIGestionBrigadas;
    private javax.swing.JMenuItem JMIGestionCuarteles;
    private javax.swing.JMenuItem JMIListadosPorCuartel;
    private javax.swing.JMenuBar JMenuBarEntidades;
    private javax.swing.JMenu jMBomberos;
    private javax.swing.JMenu jMEmergencias;
    private javax.swing.JMenuItem jMIBrigadasSegunSituacion;
    private javax.swing.JMenuItem jMIGestionBomberos;
    private javax.swing.JMenuItem jMIGestionEmergencias;
    private javax.swing.JMenuItem jMIListadoBomberos;
    private javax.swing.JMenuItem jMIListadoEmergencias;
    private javax.swing.JMenuItem jMIListadosPorBrigada;
    // End of variables declaration//GEN-END:variables
}
