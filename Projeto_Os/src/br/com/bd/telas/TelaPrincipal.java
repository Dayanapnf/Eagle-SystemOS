/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bd.telas;

import br.com.bd.dao.ModuloConexao;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.JOptionPane;



/**
 *
 * @author Dayana
 */
public class TelaPrincipal extends javax.swing.JFrame {
    Connection conexao_Postgres = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;
    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        initComponents();
        conexao_Postgres = ModuloConexao.conector();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        desktop = new javax.swing.JDesktopPane();
        lbl_logotipo = new javax.swing.JLabel();
        lbl_usuario_menu = new javax.swing.JLabel();
        lbl_data = new javax.swing.JLabel();
        menu = new javax.swing.JMenuBar();
        menu_cad = new javax.swing.JMenu();
        menu_cad_cliente = new javax.swing.JMenuItem();
        menu_cad_Os = new javax.swing.JMenuItem();
        menu_cad_User = new javax.swing.JMenuItem();
        txt_menu_up_login = new javax.swing.JMenuItem();
        txt_menu_tec_cad = new javax.swing.JMenuItem();
        menu_relatorio = new javax.swing.JMenu();
        menu_relatorio_service = new javax.swing.JMenuItem();
        menu_help = new javax.swing.JMenu();
        menu_help_sobre = new javax.swing.JMenuItem();
        menu_options = new javax.swing.JMenu();
        menu_options_exit = new javax.swing.JMenuItem();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/7462103_wing_feather_freedom_eagle_bird_icon.png"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EAGLE - SystemOS");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 491, Short.MAX_VALUE)
        );

        lbl_logotipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/7462103_wing_feather_freedom_eagle_bird_icon (1).png"))); // NOI18N
        lbl_logotipo.setText("jLabel2");

        lbl_usuario_menu.setFont(new java.awt.Font("Leelawadee UI", 1, 18)); // NOI18N
        lbl_usuario_menu.setForeground(new java.awt.Color(255, 102, 0));
        lbl_usuario_menu.setText("Usuário");

        lbl_data.setFont(new java.awt.Font("Leelawadee UI", 1, 14)); // NOI18N
        lbl_data.setForeground(new java.awt.Color(102, 102, 102));
        lbl_data.setText("Data");

        menu_cad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/285655_user_icon.png"))); // NOI18N
        menu_cad.setText("Cadastro");
        menu_cad.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N

        menu_cad_cliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        menu_cad_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/296561_on_touchscreen_submit_touch_point_icon (1).png"))); // NOI18N
        menu_cad_cliente.setText("Cliente");
        menu_cad_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_cad_clienteActionPerformed(evt);
            }
        });
        menu_cad.add(menu_cad_cliente);

        menu_cad_Os.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK));
        menu_cad_Os.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/296561_on_touchscreen_submit_touch_point_icon (1).png"))); // NOI18N
        menu_cad_Os.setText("Ordem de Serviço");
        menu_cad_Os.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_cad_OsActionPerformed(evt);
            }
        });
        menu_cad.add(menu_cad_Os);

        menu_cad_User.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK));
        menu_cad_User.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/296561_on_touchscreen_submit_touch_point_icon (1).png"))); // NOI18N
        menu_cad_User.setText("Usuários");
        menu_cad_User.setEnabled(false);
        menu_cad_User.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_cad_UserActionPerformed(evt);
            }
        });
        menu_cad.add(menu_cad_User);

        txt_menu_up_login.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK));
        txt_menu_up_login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/296561_on_touchscreen_submit_touch_point_icon (1).png"))); // NOI18N
        txt_menu_up_login.setText("Alterar Login");
        txt_menu_up_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_menu_up_loginActionPerformed(evt);
            }
        });
        menu_cad.add(txt_menu_up_login);

        txt_menu_tec_cad.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK));
        txt_menu_tec_cad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/296561_on_touchscreen_submit_touch_point_icon (1).png"))); // NOI18N
        txt_menu_tec_cad.setText("Técnico");
        txt_menu_tec_cad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_menu_tec_cadActionPerformed(evt);
            }
        });
        menu_cad.add(txt_menu_tec_cad);

        menu.add(menu_cad);

        menu_relatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/1320816_analystic_chart_line_report_icon.png"))); // NOI18N
        menu_relatorio.setText("Relatório");
        menu_relatorio.setEnabled(false);
        menu_relatorio.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N

        menu_relatorio_service.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        menu_relatorio_service.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/296561_on_touchscreen_submit_touch_point_icon (1).png"))); // NOI18N
        menu_relatorio_service.setText("Auditoria OS");
        menu_relatorio_service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_relatorio_serviceActionPerformed(evt);
            }
        });
        menu_relatorio.add(menu_relatorio_service);

        menu.add(menu_relatorio);

        menu_help.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/49594_help_male_user_icon.png"))); // NOI18N
        menu_help.setText("Ajuda");
        menu_help.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N

        menu_help_sobre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.ALT_MASK));
        menu_help_sobre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/296561_on_touchscreen_submit_touch_point_icon (1).png"))); // NOI18N
        menu_help_sobre.setText("Sobre");
        menu_help_sobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_help_sobreActionPerformed(evt);
            }
        });
        menu_help.add(menu_help_sobre);

        menu.add(menu_help);

        menu_options.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/2305599_adjust_controls_options_plus_preferences_icon.png"))); // NOI18N
        menu_options.setText("Opções");
        menu_options.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N

        menu_options_exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        menu_options_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/296561_on_touchscreen_submit_touch_point_icon (1).png"))); // NOI18N
        menu_options_exit.setText("Sair");
        menu_options_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_options_exitActionPerformed(evt);
            }
        });
        menu_options.add(menu_options_exit);

        menu.add(menu_options);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_usuario_menu, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lbl_data)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_logotipo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbl_usuario_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_logotipo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_data, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(981, 578));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menu_cad_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cad_clienteActionPerformed
        // abrir o form TelaCliente dentro do desktop pane
        TelaCliente cliente = new TelaCliente();
        desktop.add(cliente);
        cliente.show();
    }//GEN-LAST:event_menu_cad_clienteActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // As linhas abaixo substitui a label lbl_data pela data atual do sistema ao inicializar o form
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        lbl_data.setText(formatador.format(data));
    }//GEN-LAST:event_formWindowActivated

    private void menu_options_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_options_exitActionPerformed
        // exibe uma caixa de dialogo
        int sair  =  JOptionPane.showConfirmDialog(null, " Tem certeza que deseja sair?" , "Atenção", JOptionPane.YES_NO_OPTION);
        if(sair == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_menu_options_exitActionPerformed

    private void menu_help_sobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_help_sobreActionPerformed
        // chamando a tela sobre
        Tela_ajuda_sobre sobre = new Tela_ajuda_sobre();
        sobre.setVisible(true);
        
    }//GEN-LAST:event_menu_help_sobreActionPerformed

    private void menu_cad_UserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cad_UserActionPerformed
        //abrir o form TelaUsuario dentro do desktop pane
        TelaUsuario usuario = new TelaUsuario();
        desktop.add(usuario);
        usuario.show();
    }//GEN-LAST:event_menu_cad_UserActionPerformed

    private void txt_menu_up_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_menu_up_loginActionPerformed
        TelaUpLogin log = new TelaUpLogin();
        desktop.add(log);
        log.show();
    }//GEN-LAST:event_txt_menu_up_loginActionPerformed

    private void menu_cad_OsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cad_OsActionPerformed
        // chamando tela os
        TelaOs os = new TelaOs();
        desktop.add(os);
        os.show();
    }//GEN-LAST:event_menu_cad_OsActionPerformed

    private void menu_relatorio_serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_relatorio_serviceActionPerformed
        TelaAuditoria aud = new TelaAuditoria();
        desktop.add(aud);
        aud.show();
    }//GEN-LAST:event_menu_relatorio_serviceActionPerformed

    private void txt_menu_tec_cadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_menu_tec_cadActionPerformed
       TelaTec tec= new TelaTec();
       desktop.add(tec);
       tec.show();
    }//GEN-LAST:event_txt_menu_tec_cadActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbl_data;
    private javax.swing.JLabel lbl_logotipo;
    public static javax.swing.JLabel lbl_usuario_menu;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenu menu_cad;
    private javax.swing.JMenuItem menu_cad_Os;
    public static javax.swing.JMenuItem menu_cad_User;
    private javax.swing.JMenuItem menu_cad_cliente;
    private javax.swing.JMenu menu_help;
    private javax.swing.JMenuItem menu_help_sobre;
    private javax.swing.JMenu menu_options;
    private javax.swing.JMenuItem menu_options_exit;
    public static javax.swing.JMenu menu_relatorio;
    private javax.swing.JMenuItem menu_relatorio_service;
    private javax.swing.JMenuItem txt_menu_tec_cad;
    private javax.swing.JMenuItem txt_menu_up_login;
    // End of variables declaration//GEN-END:variables
}
