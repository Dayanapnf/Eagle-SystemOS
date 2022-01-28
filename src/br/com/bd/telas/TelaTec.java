/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bd.telas;

import br.com.bd.dao.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Dayana
 */
public class TelaTec extends javax.swing.JInternalFrame {
    Connection conexao_Postgres = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;
    /**
     * txt_nome_tecCreates new form TelaTec
     */
    public TelaTec() {
        initComponents();
        conexao_Postgres = ModuloConexao.conector();
    }
    private void consultar_tec(){
        if(!txt_cpf_tec.getText().isEmpty()){
            String sql = "SELECT * FROM TECNICO WHERE cpf_tec=?";
            try {
                pst = conexao_Postgres.prepareStatement(sql);
                pst.setString(1, txt_cpf_tec.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    txt_nome_tec.setText(rs.getString(2));
                    txt_tec_tel.setText(rs.getString(3));
                    
                }else {
                    JOptionPane.showMessageDialog(null, "Técnico não cadastrado!");
                    //as linhas abaixo "limpam" os campos
                    txt_nome_tec.setText(null);
                    txt_tec_tel.setText(null);
                    txt_cpf_tec.setText(null);
                    
                }
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Utilize o campo CPF para realizar a consulta!");
            txt_nome_tec.setText(null);
            txt_tec_tel.setText(null);
            txt_cpf_tec.setText(null);
                    
        }

    }
    private void adicionar(){
        String sql = "SELECT * FROM TECNICO WHERE cpf_tec=?";
        String sql2 = "INSERT INTO TECNICO(cpf_tec, nome_tec, telefone) VALUES (?,?,?)";
        try {
            
            pst1 = conexao_Postgres.prepareStatement(sql);
            pst = conexao_Postgres.prepareStatement(sql2);
            pst.setString(1, txt_cpf_tec.getText());
            pst.setString(2, txt_nome_tec.getText());
            pst.setString(3, txt_tec_tel.getText());
            
            
            pst1.setString(1, txt_cpf_tec.getText());
            rs = pst1.executeQuery();
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null," Técnico já cadastrado!");
                //as linhas abaixo "limpam" os campos
                txt_nome_tec.setText(null);
                txt_tec_tel.setText(null);
                txt_cpf_tec.setText(null);
                
            }
            else{
                //validando campos obrigatorios
                if ((!txt_tec_tel.getText().isEmpty()) && (!txt_cpf_tec.getText().isEmpty()) &&
                   (!txt_nome_tec.getText().isEmpty())){
                     //atualizando a tabela usuario com os dados do formulario
                    int add = pst.executeUpdate();

                    if(add > 0){
                        JOptionPane.showMessageDialog(null, "Técnico cadastrado com sucesso!");
                        //as linhas abaixo "limpam" os campos
                        txt_nome_tec.setText(null);
                        txt_tec_tel.setText(null);
                        txt_cpf_tec.setText(null);
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                }
            }
            
        }catch (Exception e) {
             JOptionPane.showMessageDialog(null, e);
        }
    }
    private  void alterar(){
        String sql = "SELECT * FROM TECNICO WHERE cpf_tec=?";
        String sql2 = "UPDATE TECNICO SET nome_tec=?,cpf_tec=?, telefone=? WHERE cpf_tec=?"; 
        try {
            pst1 = conexao_Postgres.prepareStatement(sql);
            pst = conexao_Postgres.prepareStatement(sql2);
            pst.setString(1, txt_nome_tec.getText());
            pst.setString(2, txt_cpf_tec.getText());
            pst.setString(3, txt_tec_tel.getText());
            pst.setString(4, txt_cpf_tec.getText());
            
            pst1.setString(1, txt_cpf_tec.getText());
            rs = pst1.executeQuery();
            
            if(rs.next()){
                if(txt_cpf_tec.getText().isEmpty() || txt_nome_tec.getText().isEmpty() || txt_tec_tel.getText().isEmpty() ){
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                }else{
                   //atualizando a tabela usuario com os dados do formulario
                    int add = pst.executeUpdate();

                    if(add > 0){
                        JOptionPane.showMessageDialog(null, "Dados do Técnico alterados com sucesso!");
                        //as linhas abaixo "limpam" os campos
                        txt_nome_tec.setText(null);
                        txt_tec_tel.setText(null);
                        txt_cpf_tec.setText(null);
                    } 
                }
            }else{
                JOptionPane.showMessageDialog(null, "Técnico não encontrado!");
            }  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
        private void remover(){
        //a estrutura abaixo confirma a remoção do usuario
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este técnico?","Atenção",JOptionPane.YES_NO_OPTION);
        
        if(confirma == JOptionPane.YES_OPTION){
                String sql = "DELETE FROM TECNICO WHERE cpf_tec=?";
            try {
                pst = conexao_Postgres.prepareStatement(sql);
                pst.setString(1, txt_cpf_tec.getText());
                int removido = pst.executeUpdate();
                
                if(removido > 0){
                    JOptionPane.showMessageDialog(null, "Técnico removido com sucesso!");
                    //as linhas abaixo "limpam" os campos
                    txt_nome_tec.setText(null);
                    txt_tec_tel.setText(null);
                    txt_cpf_tec.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
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

        jLabel1 = new javax.swing.JLabel();
        txt_nome_tec = new javax.swing.JTextField();
        txt_cpf_tec = new javax.swing.JTextField();
        txt_tec_tel = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_add_tec = new javax.swing.JButton();
        btn_buscar_tec = new javax.swing.JButton();
        btn_up_tec = new javax.swing.JButton();
        btn_dell_tec = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Técnico");
        setToolTipText("");
        setPreferredSize(new java.awt.Dimension(677, 491));

        jLabel1.setText("* Campos Obrigatórios");

        txt_cpf_tec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cpf_tecActionPerformed(evt);
            }
        });

        txt_tec_tel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tec_telActionPerformed(evt);
            }
        });

        jLabel2.setText("* Nome:");

        jLabel3.setText("* CPF:");

        jLabel4.setText("*  Telefone: ");

        btn_add_tec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/create.png"))); // NOI18N
        btn_add_tec.setToolTipText("Adicionar");
        btn_add_tec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_add_tec.setPreferredSize(new java.awt.Dimension(70, 80));
        btn_add_tec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_tecActionPerformed(evt);
            }
        });

        btn_buscar_tec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/read.png"))); // NOI18N
        btn_buscar_tec.setToolTipText("Consultar");
        btn_buscar_tec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_buscar_tec.setPreferredSize(new java.awt.Dimension(70, 80));
        btn_buscar_tec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_tecActionPerformed(evt);
            }
        });

        btn_up_tec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/update.png"))); // NOI18N
        btn_up_tec.setToolTipText("Atualizar");
        btn_up_tec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_up_tec.setPreferredSize(new java.awt.Dimension(70, 80));
        btn_up_tec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_up_tecActionPerformed(evt);
            }
        });

        btn_dell_tec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/delete.png"))); // NOI18N
        btn_dell_tec.setToolTipText("Deletar");
        btn_dell_tec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_dell_tec.setPreferredSize(new java.awt.Dimension(70, 80));
        btn_dell_tec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dell_tecActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(102, 102, 102));
        jButton5.setText("Clean");
        jButton5.setToolTipText("Apagar");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(38, 38, 38))
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_add_tec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_buscar_tec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_up_tec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_dell_tec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_nome_tec, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cpf_tec, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_tec_tel, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_nome_tec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cpf_tec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tec_tel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_add_tec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_buscar_tec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_up_tec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_dell_tec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(109, 109, 109))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jButton5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_tec_telActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tec_telActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tec_telActionPerformed

    private void txt_cpf_tecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cpf_tecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cpf_tecActionPerformed

    private void btn_buscar_tecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_tecActionPerformed
        consultar_tec();
    }//GEN-LAST:event_btn_buscar_tecActionPerformed

    private void btn_add_tecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_tecActionPerformed
        adicionar();
    }//GEN-LAST:event_btn_add_tecActionPerformed

    private void btn_up_tecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_up_tecActionPerformed
        alterar();
    }//GEN-LAST:event_btn_up_tecActionPerformed

    private void btn_dell_tecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dell_tecActionPerformed
        remover();
    }//GEN-LAST:event_btn_dell_tecActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        txt_nome_tec.setText(null);
        txt_tec_tel.setText(null);
        txt_cpf_tec.setText(null);
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_tec;
    private javax.swing.JButton btn_buscar_tec;
    private javax.swing.JButton btn_dell_tec;
    private javax.swing.JButton btn_up_tec;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txt_cpf_tec;
    private javax.swing.JTextField txt_nome_tec;
    private javax.swing.JTextField txt_tec_tel;
    // End of variables declaration//GEN-END:variables
}
