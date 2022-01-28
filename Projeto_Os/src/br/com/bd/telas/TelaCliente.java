
package br.com.bd.telas;
import java.sql.*;
import br.com.bd.dao.ModuloConexao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
// a linha abaixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;
/**
 *
 * @author Dayana
 */
public class TelaCliente extends javax.swing.JInternalFrame {
    Connection conexao_Postgres = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;
    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao_Postgres = ModuloConexao.conector();
    }
    
//metodo para adicionar clientes
    private void adicionar(){
        String sql= "SELECT * FROM CLIENTE WHERE CPF_CLIENTE =?";
        String sql2 = "INSERT INTO CLIENTE(nome_cliente, cpf_cliente, endereco_cliente, telefone_cliente, email_cliente) VALUES (?,?,?,?,?)";
        try {
            pst1 = conexao_Postgres.prepareStatement(sql);
            pst = conexao_Postgres.prepareStatement(sql2);
            pst.setString(1, txt_cli_nome.getText());
            pst.setString(2, txt_cli_cpf.getText());
            pst.setString(3, txt_cli_endereco.getText());
            pst.setString(4, txt_cli_fone.getText());
            pst.setString(5, txt_cli_email.getText());
            
            pst1.setString(1, txt_cli_cpf.getText());
            rs = pst1.executeQuery();
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null," Cliente já cadastrado!");
                //as linhas abaixo "limpam" os campos
                limpar();
                
            }
            else{
                //validando campos obrigatorios
                if ((!txt_cli_nome.getText().isEmpty()) && (!txt_cli_cpf.getText().isEmpty()) &&
                   (!txt_cli_fone.getText().isEmpty()) && (!txt_cli_endereco.getText().isEmpty()) &&
                   (!txt_cli_email.getText().isEmpty())){
                     //atualizando a tabela usuario com os dados do formulario
                    int add = pst.executeUpdate();

                    if(add > 0){
                        JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
                        //as linhas abaixo "limpam" os campos
                        limpar();
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                }
            }
            
        }catch (Exception e) {
             JOptionPane.showMessageDialog(null, e);
        }
    }
    //metodo para buscar cliente  pelo nome com filtro
    private void pesquisar_cliente(){
        String sql = "SELECT nome_cliente as Nome , cpf_cliente as CPF, endereco_cliente as Endereço, telefone_cliente as Telefone, email_cliente as Email FROM CLIENTE WHERE NOME_CLIENTE ILIKE ? OR CPF_CLIENTE LIKE ?";
        try {
            pst = conexao_Postgres.prepareStatement(sql);
            //passando o conteudo da caixa de pesquisa para o ? + o (%)
            pst.setString(1,txtCliPesquisar.getText()+ "%");
            pst.setString(2,txtCliPesquisar.getText()+ "%");
            rs = pst.executeQuery();
            //preenchendo a tabela(de visualizacao do select)  em tempo real com o recurso da biblioteca rs2xml
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    // metodo para setar os campos do formulario com o conteudo da tabela
    public void setar_campos(){
        int setar = tblClientes.getSelectedRow();
        txt_cli_nome.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        txt_cli_cpf.setText(tblClientes.getModel().getValueAt(setar,1).toString());
        txt_cli_endereco.setText(tblClientes.getModel().getValueAt(setar,2).toString());
        txt_cli_fone.setText(tblClientes.getModel().getValueAt(setar,3).toString());
        txt_cli_email.setText(tblClientes.getModel().getValueAt(setar,4).toString());
        
        //desabilitar o botao adicionar
        btnAddCli.setEnabled(false);
    }
      //metodo para alterar dados dos clientes
    private  void alterar(){
        String sql = "SELECT * FROM CLIENTE WHERE cpf_cliente=?";
        String sql2 = "UPDATE CLIENTE SET nome_cliente=?, cpf_cliente=?, endereco_cliente=?, telefone_cliente=?,email_cliente=? WHERE cpf_cliente=?"; 
        try {
            pst1 = conexao_Postgres.prepareStatement(sql);
            pst = conexao_Postgres.prepareStatement(sql2);
            pst.setString(1, txt_cli_nome.getText());
            pst.setString(2, txt_cli_cpf.getText());
            pst.setString(3, txt_cli_endereco.getText());
            pst.setString(4, txt_cli_fone.getText());
            pst.setString(5, txt_cli_email.getText());
            pst.setString(6, txt_cli_cpf.getText());
            
            pst1.setString(1, txt_cli_cpf.getText());
            rs = pst1.executeQuery();
            
            if(rs.next()){
                if(txt_cli_cpf.getText().isEmpty() || txt_cli_nome.getText().isEmpty() || txt_cli_endereco.getText().isEmpty()
                   || txt_cli_fone.getText().isEmpty() || txt_cli_email.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Preecha todos os campos!");
                }
                else{
                    //atualizando a tabela cliente com os dados do formulario
                    int add = pst.executeUpdate();

                    if(add > 0){
                        JOptionPane.showMessageDialog(null, "Dados do usuário alterados com sucesso!");
                        //as linhas abaixo "limpam" os campos
                        limpar();
                        btnAddCli.setEnabled(true);
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
            }
           
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //metodo para remover clientes
    private void remover(){
        //a estrutura abaixo confirma a remoção do cliente
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente?","Atenção",JOptionPane.YES_NO_OPTION);
        
        if(confirma == JOptionPane.YES_OPTION){
            String sql = "DELETE FROM CLIENTE WHERE cpf_cliente=?";
            try {
                pst = conexao_Postgres.prepareStatement(sql);
                pst.setString(1, txt_cli_cpf.getText());
                int removido = pst.executeUpdate();
                
                if(removido > 0){
                    JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
                    //as linhas abaixo "limpam" os campos
                    limpar();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cliente ativo! Não é possivel removê-lo.");
            }
        }
    }
    
    //metodo para limpar os campos do formulario
    private void limpar(){
        txt_cli_nome.setText(null);
        txt_cli_cpf.setText(null);
        txt_cli_endereco.setText(null);
        txt_cli_fone.setText(null);
        txt_cli_email.setText(null);
        txtCliPesquisar.setText(null);
        //a linha abaixo limpa os campos da tabela
        ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_cli_nome = new javax.swing.JTextField();
        txt_cli_cpf = new javax.swing.JTextField();
        txt_cli_endereco = new javax.swing.JTextField();
        txt_cli_email = new javax.swing.JTextField();
        txt_cli_fone = new javax.swing.JTextField();
        txtCliPesquisar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnAddCli = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btn_cli_update = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnCleanCliente = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Clientes");
        setPreferredSize(new java.awt.Dimension(677, 491));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("* Nome:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("* CPF:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("* Endereço:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("* Telefone:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("E-mail:");

        txt_cli_nome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cli_nomeActionPerformed(evt);
            }
        });

        txtCliPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliPesquisarActionPerformed(evt);
            }
        });
        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/search_icon.png"))); // NOI18N

        tblClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nome", "CPF", "Endereço", "Telefone", "Email"
            }
        ));
        tblClientes.setFocusable(false);
        tblClientes.getTableHeader().setReorderingAllowed(false);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        btnAddCli.setBackground(new java.awt.Color(153, 153, 153));
        btnAddCli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/create.png"))); // NOI18N
        btnAddCli.setToolTipText("Adicionar");
        btnAddCli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddCli.setPreferredSize(new java.awt.Dimension(70, 80));
        btnAddCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCliActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(153, 153, 153));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/delete.png"))); // NOI18N
        jButton2.setToolTipText("Deletar");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setPreferredSize(new java.awt.Dimension(70, 70));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btn_cli_update.setBackground(new java.awt.Color(153, 153, 153));
        btn_cli_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/update.png"))); // NOI18N
        btn_cli_update.setToolTipText("Atualizar");
        btn_cli_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cli_update.setPreferredSize(new java.awt.Dimension(70, 70));
        btn_cli_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cli_updateActionPerformed(evt);
            }
        });

        jLabel6.setText("* Campos Obrigatórios");

        btnCleanCliente.setBackground(new java.awt.Color(204, 204, 204));
        btnCleanCliente.setFont(new java.awt.Font("Candara", 1, 11)); // NOI18N
        btnCleanCliente.setForeground(new java.awt.Color(102, 102, 102));
        btnCleanCliente.setText("Clean");
        btnCleanCliente.setToolTipText("Apagar");
        btnCleanCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCleanCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_cli_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addGap(126, 126, 126)
                                .addComponent(jLabel6))
                            .addComponent(jScrollPane1)
                            .addComponent(btnCleanCliente, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_cli_fone, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(66, 66, 66)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_cli_endereco)
                                            .addComponent(txt_cli_email)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txt_cli_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addGap(28, 28, 28)
                                .addComponent(txt_cli_nome)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(27, 27, 27))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddCli, btn_cli_update, jButton2});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_cli_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_cli_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txt_cli_fone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_cli_endereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_cli_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCleanCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_cli_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_cli_cpf, txt_cli_fone});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddCli, btn_cli_update, jButton2});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_cli_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cli_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cli_nomeActionPerformed

    private void btnAddCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCliActionPerformed
        // chama a função para adicionar cliente
        adicionar();
    }//GEN-LAST:event_btnAddCliActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // o evento abaixo é do tipo "enquanto estiver digitando "
        pesquisar_cliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased
    // evento para setar os campos da tabela clicando com mouse:
    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        setar_campos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btn_cli_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cli_updateActionPerformed
        alterar();
    }//GEN-LAST:event_btn_cli_updateActionPerformed

    private void txtCliPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliPesquisarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // chamando o metodo remover
        remover();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnCleanClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanClienteActionPerformed
        // limpando todos os campos
        txt_cli_nome.setText(null);
        txt_cli_cpf.setText(null);
        txt_cli_endereco.setText(null);
        txt_cli_fone.setText(null);
        txt_cli_email.setText(null);
        txtCliPesquisar.setText(null);
        //limpando tabela
        ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
        btnAddCli.setEnabled(true);
    }//GEN-LAST:event_btnCleanClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCli;
    private javax.swing.JButton btnCleanCliente;
    private javax.swing.JButton btn_cli_update;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txt_cli_cpf;
    private javax.swing.JTextField txt_cli_email;
    private javax.swing.JTextField txt_cli_endereco;
    private javax.swing.JTextField txt_cli_fone;
    private javax.swing.JTextField txt_cli_nome;
    // End of variables declaration//GEN-END:variables
}
