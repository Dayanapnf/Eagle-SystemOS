/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bd.telas;
import java.sql.*;
import br.com.bd.dal.ModuloConexao;
import javax.swing.JOptionPane;
/**
 *
 * @author Dayana
 */
public class TelaUsuario extends javax.swing.JInternalFrame {
    Connection conexao_Postgres = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;
    
    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
        initComponents();
        conexao_Postgres = ModuloConexao.conector();
    }
    
    //metodo para consultar usuarios
    private void consultar(){
        if(!txt_cpf_func.getText().isEmpty()){
            String sql = "SELECT * FROM USUARIOS WHERE cpf_user=?";
            try {
                pst = conexao_Postgres.prepareStatement(sql);
                pst.setString(1, txt_cpf_func.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    txt_func_nome.setText(rs.getString(1));
                    txt_func_fone.setText(rs.getString(3));
                    //a linha abaixo refere-se ao ComboBox
                    combo_box_perfil.setSelectedItem(rs.getString(6));
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                    //as linhas abaixo "limpam" os campos
                    txt_func_nome.setText(null);
                    txt_func_fone.setText(null);
                    txt_cpf_func.setText(null);
                    txt_func_login.setText(null);
                    txt_func_senha.setText(null);
                    combo_box_perfil.setSelectedItem(null);
                }
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Utilize o campo CPF para realizar a consulta!");
            txt_func_nome.setText(null);
            txt_func_fone.setText(null);
            txt_func_login.setText(null);
            txt_func_senha.setText(null);
            combo_box_perfil.setSelectedItem(null);
        }

    }
    
    //mnetodo para adicionar usuarios
    private void adicionar(){
        String sql = "SELECT * FROM USUARIOS WHERE cpf_user=?";
        String sql2 = "INSERT INTO USUARIOS(cpf_user, nome_user, telefone, login, senha, perfil) VALUES (?,?,?,?,?,?)";
        try {
            
            pst1 = conexao_Postgres.prepareStatement(sql);
            pst = conexao_Postgres.prepareStatement(sql2);
            pst.setString(1, txt_cpf_func.getText());
            pst.setString(2, txt_func_nome.getText());
            pst.setString(3, txt_func_fone.getText());
            pst.setString(4, txt_func_login.getText());
            pst.setString(5, txt_func_senha.getText());
            //jogar o valor do ComboBox para o BD
            pst.setString(6, combo_box_perfil.getSelectedItem().toString());
            
            pst1.setString(1, txt_cpf_func.getText());
            rs = pst1.executeQuery();
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null," Funcionário já cadastrado!");
                //as linhas abaixo "limpam" os campos
                txt_func_nome.setText(null);
                txt_cpf_func.setText(null);
                txt_func_fone.setText(null);
                txt_func_senha.setText(null);
                txt_func_login.setText(null);
                combo_box_perfil.setSelectedItem(null);
                
            }
            else{
                //validando campos obrigatorios
                if ((!txt_cpf_func.getText().isEmpty()) && (!txt_func_nome.getText().isEmpty()) &&
                   (!txt_func_fone.getText().isEmpty()) && (!txt_func_login.getText().isEmpty()) &&
                   (!txt_func_senha.getText().isEmpty()) && (!combo_box_perfil.getSelectedItem().toString().isEmpty())){
                     //atualizando a tabela usuario com os dados do formulario
                    int add = pst.executeUpdate();

                    if(add > 0){
                        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                        //as linhas abaixo "limpam" os campos
                        txt_func_nome.setText(null);
                        txt_func_fone.setText(null);
                        txt_cpf_func.setText(null);
                        txt_func_login.setText(null);
                        txt_func_senha.setText(null);
                        combo_box_perfil.setSelectedItem(null);
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                }
            }
            
        }catch (Exception e) {
             JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //metodo para alterar dados dos usuarios
    private  void alterar(){
        String sql = "SELECT * FROM USUARIOS WHERE cpf_user=?";
        String sql2 = "UPDATE USUARIOS SET nome_user=?,cpf_user=?, telefone=?, perfil=? WHERE cpf_user=?"; 
        try {
            pst1 = conexao_Postgres.prepareStatement(sql);
            pst = conexao_Postgres.prepareStatement(sql2);
            pst.setString(1, txt_func_nome.getText());
            pst.setString(2, txt_cpf_func.getText());
            pst.setString(3, txt_func_fone.getText());
            pst.setString(4, combo_box_perfil.getSelectedItem().toString());
            pst.setString(5, txt_cpf_func.getText());
            
            pst1.setString(1, txt_cpf_func.getText());
            rs = pst1.executeQuery();
            
            if(rs.next()){
                if(txt_cpf_func.getText().isEmpty() || txt_func_nome.getText().isEmpty() || txt_func_fone.getText().isEmpty() || combo_box_perfil.getSelectedItem().toString().isEmpty() ){
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                }else{
                   //atualizando a tabela usuario com os dados do formulario
                    int add = pst.executeUpdate();

                    if(add > 0){
                        JOptionPane.showMessageDialog(null, "Dados do usuário alterados com sucesso!");
                        //as linhas abaixo "limpam" os campos
                        txt_func_nome.setText(null);
                        txt_func_fone.setText(null);
                        txt_cpf_func.setText(null);
                        txt_func_login.setText(null);
                        txt_func_senha.setText(null);
                        combo_box_perfil.setSelectedItem(null);
                    } 
                }
            }else{
                JOptionPane.showMessageDialog(null, "Usuário não encontrado!");
            }  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //metodo para remover usuarios
    private void remover(){
        //a estrutura abaixo confirma a remoção do usuario
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuário?","Atenção",JOptionPane.YES_NO_OPTION);
        
        if(confirma == JOptionPane.YES_OPTION){
                String sql = "DELETE FROM USUARIOS WHERE cpf_user=?";
            try {
                pst = conexao_Postgres.prepareStatement(sql);
                pst.setString(1, txt_cpf_func.getText());
                int removido = pst.executeUpdate();
                
                if(removido > 0){
                    JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
                    //as linhas abaixo "limpam" os campos
                    txt_func_nome.setText(null);
                    txt_func_fone.setText(null);
                    txt_cpf_func.setText(null);
                    txt_func_login.setText(null);
                    txt_func_senha.setText(null);
                    combo_box_perfil.setSelectedItem(null);
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

        lbl_func_nome = new javax.swing.JLabel();
        lbl_func_cpf = new javax.swing.JLabel();
        lbl_func_login = new javax.swing.JLabel();
        lbl_func_senha = new javax.swing.JLabel();
        lbl_func_perfil = new javax.swing.JLabel();
        lbl_func_fone = new javax.swing.JLabel();
        txt_func_nome = new javax.swing.JTextField();
        txt_func_login = new javax.swing.JTextField();
        combo_box_perfil = new javax.swing.JComboBox<>();
        btn_func_create = new javax.swing.JButton();
        btn_func_update = new javax.swing.JButton();
        btn_func_read = new javax.swing.JButton();
        btn_func_delete = new javax.swing.JButton();
        txt_func_fone = new javax.swing.JTextField();
        txt_cpf_func = new javax.swing.JTextField();
        txt_func_senha = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setPreferredSize(new java.awt.Dimension(677, 491));

        lbl_func_nome.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        lbl_func_nome.setForeground(new java.awt.Color(102, 102, 102));
        lbl_func_nome.setText("* Nome:");

        lbl_func_cpf.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        lbl_func_cpf.setForeground(new java.awt.Color(102, 102, 102));
        lbl_func_cpf.setText("* CPF:");

        lbl_func_login.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        lbl_func_login.setForeground(new java.awt.Color(102, 102, 102));
        lbl_func_login.setText("* Login:");

        lbl_func_senha.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        lbl_func_senha.setForeground(new java.awt.Color(102, 102, 102));
        lbl_func_senha.setText("* Senha:");

        lbl_func_perfil.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        lbl_func_perfil.setForeground(new java.awt.Color(102, 102, 102));
        lbl_func_perfil.setText("* Perfil:");

        lbl_func_fone.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        lbl_func_fone.setForeground(new java.awt.Color(102, 102, 102));
        lbl_func_fone.setText("* Telefone:");

        txt_func_nome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_func_nomeActionPerformed(evt);
            }
        });

        combo_box_perfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "admin", "user" }));
        combo_box_perfil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        combo_box_perfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_box_perfilActionPerformed(evt);
            }
        });

        btn_func_create.setBackground(new java.awt.Color(153, 153, 153));
        btn_func_create.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/create.png"))); // NOI18N
        btn_func_create.setToolTipText("Adicionar");
        btn_func_create.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_func_create.setPreferredSize(new java.awt.Dimension(70, 80));
        btn_func_create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_func_createActionPerformed(evt);
            }
        });

        btn_func_update.setBackground(new java.awt.Color(153, 153, 153));
        btn_func_update.setForeground(new java.awt.Color(255, 102, 0));
        btn_func_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/update.png"))); // NOI18N
        btn_func_update.setToolTipText("Atualizar");
        btn_func_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_func_update.setPreferredSize(new java.awt.Dimension(70, 80));
        btn_func_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_func_updateActionPerformed(evt);
            }
        });

        btn_func_read.setBackground(new java.awt.Color(153, 153, 153));
        btn_func_read.setForeground(new java.awt.Color(255, 102, 0));
        btn_func_read.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/read.png"))); // NOI18N
        btn_func_read.setToolTipText("Consultar");
        btn_func_read.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_func_read.setPreferredSize(new java.awt.Dimension(70, 80));
        btn_func_read.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_func_readActionPerformed(evt);
            }
        });

        btn_func_delete.setBackground(new java.awt.Color(153, 153, 153));
        btn_func_delete.setForeground(new java.awt.Color(255, 102, 0));
        btn_func_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/delete.png"))); // NOI18N
        btn_func_delete.setToolTipText("Deletar");
        btn_func_delete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_func_delete.setPreferredSize(new java.awt.Dimension(70, 80));
        btn_func_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_func_deleteActionPerformed(evt);
            }
        });

        txt_func_fone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_func_foneActionPerformed(evt);
            }
        });

        jLabel1.setText("* Campos Obrigatórios");

        jButton1.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 102, 102));
        jButton1.setText("Clean");
        jButton1.setToolTipText("Apagar");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_func_nome)
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1)
                                .addComponent(txt_func_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton1)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(0, 135, Short.MAX_VALUE)
                                            .addComponent(btn_func_create, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btn_func_read, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btn_func_update, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btn_func_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txt_cpf_func, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txt_func_login, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(combo_box_perfil, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(58, 58, 58)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(lbl_func_fone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbl_func_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txt_func_fone)
                                                .addComponent(txt_func_senha)))))
                                .addGap(38, 38, 38))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_func_cpf)
                            .addComponent(lbl_func_login)
                            .addComponent(lbl_func_perfil))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btn_func_create, btn_func_delete, btn_func_read, btn_func_update});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_func_nome)
                    .addComponent(txt_func_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_func_cpf)
                    .addComponent(lbl_func_fone, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_func_fone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cpf_func, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_func_login)
                    .addComponent(txt_func_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_func_senha)
                    .addComponent(txt_func_senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_func_perfil)
                    .addComponent(combo_box_perfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_func_update, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_func_delete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_func_read, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_func_create, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_func_create, btn_func_delete, btn_func_read, btn_func_update});

        setBounds(0, 0, 677, 491);
    }// </editor-fold>//GEN-END:initComponents

    private void combo_box_perfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_box_perfilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_box_perfilActionPerformed

    private void txt_func_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_func_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_func_nomeActionPerformed

    private void btn_func_createActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_func_createActionPerformed
        // chamando a função adicionar
        adicionar();
    }//GEN-LAST:event_btn_func_createActionPerformed

    private void btn_func_readActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_func_readActionPerformed
        //chamar o metodo consultar
        consultar();
    }//GEN-LAST:event_btn_func_readActionPerformed

    private void txt_func_foneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_func_foneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_func_foneActionPerformed

    private void btn_func_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_func_updateActionPerformed
        // chamar o metodo alterar
        alterar();
    }//GEN-LAST:event_btn_func_updateActionPerformed

    private void btn_func_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_func_deleteActionPerformed
        // chamar o metodo remover
        remover();
    }//GEN-LAST:event_btn_func_deleteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        txt_func_nome.setText(null);
        txt_func_fone.setText(null);
        txt_cpf_func.setText(null);
        txt_func_login.setText(null);
        txt_func_senha.setText(null);
        combo_box_perfil.setSelectedItem(null);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_func_create;
    private javax.swing.JButton btn_func_delete;
    private javax.swing.JButton btn_func_read;
    private javax.swing.JButton btn_func_update;
    private javax.swing.JComboBox<String> combo_box_perfil;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbl_func_cpf;
    private javax.swing.JLabel lbl_func_fone;
    private javax.swing.JLabel lbl_func_login;
    private javax.swing.JLabel lbl_func_nome;
    private javax.swing.JLabel lbl_func_perfil;
    private javax.swing.JLabel lbl_func_senha;
    private javax.swing.JTextField txt_cpf_func;
    private javax.swing.JTextField txt_func_fone;
    private javax.swing.JTextField txt_func_login;
    private javax.swing.JTextField txt_func_nome;
    private javax.swing.JTextField txt_func_senha;
    // End of variables declaration//GEN-END:variables
}
