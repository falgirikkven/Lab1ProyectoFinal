package lab1proyectofinal.vistas;

import java.beans.PropertyVetoException;
import javax.swing.JInternalFrame;
import lab1proyectofinal.accesoADatos.BrigadaData;
import lab1proyectofinal.accesoADatos.CuartelData;

/**
 *
 * @author Grupo-3
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * SUJETO A CAMBIOS
     */
    private JInternalFrame focusedFrame = null;
    private final FormularioCuartel formularioCuartel;
    private final GestionCuartel gestionCuartel;
    private final FormularioBrigada formularioBrigada;
    private final GestionBrigada gestionBrigada;

    private final CuartelData cuartelData;
    private final BrigadaData brigadaData;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();

        this.cuartelData = new CuartelData();
        this.brigadaData = new BrigadaData();

        // Formulario Cuartel
        this.formularioCuartel = new FormularioCuartel(cuartelData);
        DesktopPane.add(this.formularioCuartel);

        // Gestion Cuartel
        this.gestionCuartel = new GestionCuartel(cuartelData);
        DesktopPane.add(this.gestionCuartel);

        // Formulario Brigada
        this.formularioBrigada = new FormularioBrigada(cuartelData, brigadaData);
        DesktopPane.add(this.formularioBrigada);

        // Gestion Brigada
        this.gestionBrigada = new GestionBrigada(cuartelData, brigadaData);
        DesktopPane.add(this.gestionBrigada);
    }

    private void focusIFrame(JInternalFrame iFrame) {

        if (focusedFrame != null) {
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DesktopPane = new javax.swing.JDesktopPane();
        MenuBar = new javax.swing.JMenuBar();
        cuartelMenu = new javax.swing.JMenu();
        formularioCuartelMI = new javax.swing.JMenuItem();
        gestionCuartelMI = new javax.swing.JMenuItem();
        brigadaMenu = new javax.swing.JMenu();
        formularioBrigadaMI = new javax.swing.JMenuItem();
        gestionBrigadaMI = new javax.swing.JMenuItem();
        bomberoMenu = new javax.swing.JMenu();
        formularioBomberoMI = new javax.swing.JMenuItem();
        gestionBomberoMI = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestión Bomberos");

        javax.swing.GroupLayout DesktopPaneLayout = new javax.swing.GroupLayout(DesktopPane);
        DesktopPane.setLayout(DesktopPaneLayout);
        DesktopPaneLayout.setHorizontalGroup(
            DesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        DesktopPaneLayout.setVerticalGroup(
            DesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 457, Short.MAX_VALUE)
        );

        cuartelMenu.setText("Cuartel");

        formularioCuartelMI.setText("Formulario Cuartel");
        formularioCuartelMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formularioCuartelMIActionPerformed(evt);
            }
        });
        cuartelMenu.add(formularioCuartelMI);

        gestionCuartelMI.setText("Gestión Cuartel");
        gestionCuartelMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gestionCuartelMIActionPerformed(evt);
            }
        });
        cuartelMenu.add(gestionCuartelMI);

        MenuBar.add(cuartelMenu);

        brigadaMenu.setText("Brigada");

        formularioBrigadaMI.setText("Formulario Brigada");
        formularioBrigadaMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formularioBrigadaMIActionPerformed(evt);
            }
        });
        brigadaMenu.add(formularioBrigadaMI);

        gestionBrigadaMI.setText("Gestión Brigada");
        gestionBrigadaMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gestionBrigadaMIActionPerformed(evt);
            }
        });
        brigadaMenu.add(gestionBrigadaMI);

        MenuBar.add(brigadaMenu);

        bomberoMenu.setText("Bombero");

        formularioBomberoMI.setText("Formulario Bombero");
        bomberoMenu.add(formularioBomberoMI);

        gestionBomberoMI.setText("Gestión Bombero");
        bomberoMenu.add(gestionBomberoMI);

        MenuBar.add(bomberoMenu);

        jMenu1.setText("Siniestro");
        MenuBar.add(jMenu1);

        setJMenuBar(MenuBar);

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

    private void formularioCuartelMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formularioCuartelMIActionPerformed
        focusIFrame(formularioCuartel);
    }//GEN-LAST:event_formularioCuartelMIActionPerformed

    private void gestionCuartelMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gestionCuartelMIActionPerformed
        focusIFrame(gestionCuartel);
    }//GEN-LAST:event_gestionCuartelMIActionPerformed

    private void formularioBrigadaMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formularioBrigadaMIActionPerformed
        focusIFrame(formularioBrigada);
    }//GEN-LAST:event_formularioBrigadaMIActionPerformed

    private void gestionBrigadaMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gestionBrigadaMIActionPerformed
        focusIFrame(gestionBrigada);
    }//GEN-LAST:event_gestionBrigadaMIActionPerformed

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
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenu bomberoMenu;
    private javax.swing.JMenu brigadaMenu;
    private javax.swing.JMenu cuartelMenu;
    private javax.swing.JMenuItem formularioBomberoMI;
    private javax.swing.JMenuItem formularioBrigadaMI;
    private javax.swing.JMenuItem formularioCuartelMI;
    private javax.swing.JMenuItem gestionBomberoMI;
    private javax.swing.JMenuItem gestionBrigadaMI;
    private javax.swing.JMenuItem gestionCuartelMI;
    private javax.swing.JMenu jMenu1;
    // End of variables declaration//GEN-END:variables
}