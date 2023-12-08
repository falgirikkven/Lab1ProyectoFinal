package lab1proyectofinal.vistas;

import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyVetoException;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import lab1proyectofinal.accesoADatos.BomberoData;
import lab1proyectofinal.accesoADatos.BrigadaData;
import lab1proyectofinal.accesoADatos.CuartelData;
import lab1proyectofinal.accesoADatos.SiniestroData;
import lab1proyectofinal.accesoADatos.Utils;

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
    private JLabel jLabAux = Utils.jLabConfigurado();

    private final BomberoData bomberoData;
    private final BrigadaData brigadaData;
    private final CuartelData cuartelData;
    private final SiniestroData siniestroData;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();

        this.bomberoData = new BomberoData();
        this.brigadaData = new BrigadaData();
        this.cuartelData = new CuartelData();
        this.siniestroData = new SiniestroData();

        // Gestion Cuartel 
        this.gestionCuartel = new GestionCuartel(cuartelData);
        DesktopPane.add(this.gestionCuartel);

        // Listados por cuartel
        this.listadosPorCuartel = new ListadosPorCuartel(cuartelData);
        DesktopPane.add(this.listadosPorCuartel);

        // Gestión Brigada 
        this.gestionBrigada = new GestionBrigada(cuartelData, brigadaData);
        DesktopPane.add(this.gestionBrigada);

        // Listados por brigada
        this.listadosPorBrigada = new ListadosPorBrigada(brigadaData);
        DesktopPane.add(this.listadosPorBrigada);

        // Brigadas según situación
        this.brigadasSegunSituacion = new BrigadasSegunSituacion(brigadaData);
        DesktopPane.add(this.brigadasSegunSituacion);

        // Gestión Bomberos
        this.gestionBombero = new GestionBombero(cuartelData, brigadaData, bomberoData);
        DesktopPane.add(this.gestionBombero);
        
        // Listado de bomberos
        this.listadoDeBomberos = new ListadoDeBomberos(bomberoData);
        DesktopPane.add(this.listadoDeBomberos);
    }

    private void focusIFrame(JInternalFrame iFrame) {

        // la primera vez que se ejecute este método focusedFrame será igual a null
        if (focusedFrame != null) {

            // si se está llevando a cabo alguna operación en 'focusedFrame' y se quiere pasar a un
            // JInternalFrame distinto sin antes haber terminado la operación 
            if (focusedFrame instanceof GestionBrigada
                    && !(iFrame instanceof GestionBrigada)
                    && ((GestionBrigada) focusedFrame).isEnOperacion()) {
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
                    && ((GestionBombero) focusedFrame).isEnOperacion()) {
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
        JMenuCuarteles = new javax.swing.JMenu();
        JMenuItemGestionCuarteles = new javax.swing.JMenuItem();
        JMenuItemListadosPorCuartel = new javax.swing.JMenuItem();
        JMenuBrigada = new javax.swing.JMenu();
        JMenuItemGestionBrigadas = new javax.swing.JMenuItem();
        jMenuItemListadosPorBrigada = new javax.swing.JMenuItem();
        jMenuItemBrigadasSegunSituacion = new javax.swing.JMenuItem();
        bomberoMenu = new javax.swing.JMenu();
        gestionBomberoMI = new javax.swing.JMenuItem();
        jMIListadoDeBomberos = new javax.swing.JMenuItem();
        siniestroMenu = new javax.swing.JMenu();
        gestionSiniestroMI = new javax.swing.JMenuItem();
        tratamientoEmergenciaMI = new javax.swing.JMenuItem();

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

        JMenuCuarteles.setText("Cuarteles");
        JMenuCuarteles.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        JMenuItemGestionCuarteles.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        JMenuItemGestionCuarteles.setText("Gestión de cuarteles");
        JMenuItemGestionCuarteles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemGestionCuartelesActionPerformed(evt);
            }
        });
        JMenuCuarteles.add(JMenuItemGestionCuarteles);

        JMenuItemListadosPorCuartel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        JMenuItemListadosPorCuartel.setText("Listados por cuartel");
        JMenuItemListadosPorCuartel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemListadosPorCuartelActionPerformed(evt);
            }
        });
        JMenuCuarteles.add(JMenuItemListadosPorCuartel);

        JMenuBarEntidades.add(JMenuCuarteles);

        JMenuBrigada.setText("Brigada");
        JMenuBrigada.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        JMenuItemGestionBrigadas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        JMenuItemGestionBrigadas.setText("Gestión de brigadas");
        JMenuItemGestionBrigadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemGestionBrigadasActionPerformed(evt);
            }
        });
        JMenuBrigada.add(JMenuItemGestionBrigadas);

        jMenuItemListadosPorBrigada.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jMenuItemListadosPorBrigada.setText("Listados por brigada");
        jMenuItemListadosPorBrigada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemListadosPorBrigadaActionPerformed(evt);
            }
        });
        JMenuBrigada.add(jMenuItemListadosPorBrigada);

        jMenuItemBrigadasSegunSituacion.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jMenuItemBrigadasSegunSituacion.setText("Brigadas según situación");
        jMenuItemBrigadasSegunSituacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBrigadasSegunSituacionActionPerformed(evt);
            }
        });
        JMenuBrigada.add(jMenuItemBrigadasSegunSituacion);

        JMenuBarEntidades.add(JMenuBrigada);

        bomberoMenu.setText("Bombero");
        bomberoMenu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        gestionBomberoMI.setText("Gestión Bombero");
        gestionBomberoMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gestionBomberoMIActionPerformed(evt);
            }
        });
        bomberoMenu.add(gestionBomberoMI);

        jMIListadoDeBomberos.setText("Listado de bomberos");
        jMIListadoDeBomberos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIListadoDeBomberosActionPerformed(evt);
            }
        });
        bomberoMenu.add(jMIListadoDeBomberos);

        JMenuBarEntidades.add(bomberoMenu);

        siniestroMenu.setText("Siniestro");
        siniestroMenu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        gestionSiniestroMI.setText("Gestión Siniestro");
        gestionSiniestroMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gestionSiniestroMIActionPerformed(evt);
            }
        });
        siniestroMenu.add(gestionSiniestroMI);

        tratamientoEmergenciaMI.setText("Tratamiento de Emergencia");
        tratamientoEmergenciaMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tratamientoEmergenciaMIActionPerformed(evt);
            }
        });
        siniestroMenu.add(tratamientoEmergenciaMI);

        JMenuBarEntidades.add(siniestroMenu);

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

    private void JMenuItemGestionCuartelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemGestionCuartelesActionPerformed
        focusIFrame(gestionCuartel);
    }//GEN-LAST:event_JMenuItemGestionCuartelesActionPerformed

    private void gestionBomberoMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gestionBomberoMIActionPerformed
        focusIFrame(gestionBombero);
    }//GEN-LAST:event_gestionBomberoMIActionPerformed

    private void gestionSiniestroMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gestionSiniestroMIActionPerformed
//        focusIFrame(gestionSiniestro);
    }//GEN-LAST:event_gestionSiniestroMIActionPerformed

    private void tratamientoEmergenciaMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tratamientoEmergenciaMIActionPerformed
//        focusIFrame(tratamientoEmergencia);
    }//GEN-LAST:event_tratamientoEmergenciaMIActionPerformed

    private void JMenuItemListadosPorCuartelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemListadosPorCuartelActionPerformed
        focusIFrame(listadosPorCuartel);
    }//GEN-LAST:event_JMenuItemListadosPorCuartelActionPerformed

    private void JMenuItemGestionBrigadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemGestionBrigadasActionPerformed
        focusIFrame(gestionBrigada);
    }//GEN-LAST:event_JMenuItemGestionBrigadasActionPerformed

    private void jMenuItemBrigadasSegunSituacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBrigadasSegunSituacionActionPerformed
        focusIFrame(brigadasSegunSituacion);
    }//GEN-LAST:event_jMenuItemBrigadasSegunSituacionActionPerformed

    private void jMenuItemListadosPorBrigadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemListadosPorBrigadaActionPerformed
        focusIFrame(listadosPorBrigada);
    }//GEN-LAST:event_jMenuItemListadosPorBrigadaActionPerformed

    private void jMIListadoDeBomberosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIListadoDeBomberosActionPerformed
        focusIFrame(listadoDeBomberos);
    }//GEN-LAST:event_jMIListadoDeBomberosActionPerformed

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
    private javax.swing.JMenuBar JMenuBarEntidades;
    private javax.swing.JMenu JMenuBrigada;
    private javax.swing.JMenu JMenuCuarteles;
    private javax.swing.JMenuItem JMenuItemGestionBrigadas;
    private javax.swing.JMenuItem JMenuItemGestionCuarteles;
    private javax.swing.JMenuItem JMenuItemListadosPorCuartel;
    private javax.swing.JMenu bomberoMenu;
    private javax.swing.JMenuItem gestionBomberoMI;
    private javax.swing.JMenuItem gestionSiniestroMI;
    private javax.swing.JMenuItem jMIListadoDeBomberos;
    private javax.swing.JMenuItem jMenuItemBrigadasSegunSituacion;
    private javax.swing.JMenuItem jMenuItemListadosPorBrigada;
    private javax.swing.JMenu siniestroMenu;
    private javax.swing.JMenuItem tratamientoEmergenciaMI;
    // End of variables declaration//GEN-END:variables
}
