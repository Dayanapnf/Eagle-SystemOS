/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bd.telas;
import java.sql.*;
import br.com.bd.dal.ModuloConexao;
import net.proteanit.sql.DbUtils;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import br.com.bd.telas.TelaLogin;
import static br.com.bd.telas.TelaLogin.cpf_user;
/**
 *
 * @author Dayana
 */
public class TelaOs extends javax.swing.JInternalFrame {
    Connection conexao_Postgres = null;
    PreparedStatement pst = null;
    PreparedStatement pst1, pst2 = null;
    ResultSet rs = null;
    
    private String tipo;
    /** Creates new form TelaOs */
    public TelaOs() {
        initComponents();
        conexao_Postgres = ModuloConexao.conector();
        
    }
    
    private void pesquisar_cliente(){
        String sql= "SELECT nome_cliente AS nome , cpf_cliente AS cpf , telefone_cliente AS fone FROM CLIENTE WHERE nome_cliente ILIKE ? OR CPF_CLIENTE LIKE ? ";
        try {
            //pst1 = conexao_Postgres.prepareStatement(sql);
            pst = conexao_Postgres.prepareStatement(sql);
            pst.setString(1, txtCliPesquisar.getText() + "%");
            pst.setString(2, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    private void setar_campos(){
        int setar = tblClientes.getSelectedRow();
        txtCliCpf.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        
            
    }
    //metodo cadastrar os
    private void emitir_os(){
        String sql = "SELECT * FROM TECNICO WHERE cpf_tec=?";
        String sql2 = "INSERT INTO ORDEM_SERVICO(tipo,situacao,equipamento,descricao,valor_os,cpf_cliente,cpf_tec,cpf_user) VALUES (?,?,?,?,?,?,?,?)";
        try {
            pst1 = conexao_Postgres.prepareStatement(sql);
            pst = conexao_Postgres.prepareStatement(sql2);
            
            String valor = txtOsValor.getText();
            valor = valor.replace(",", ".");
            pst.setString(1, tipo);
            pst.setString(2, cboOsSituacao.getSelectedItem().toString());
            pst.setString(3, txtOsEquipamento.getText());
            pst.setString(4, txtOsDescricao.getText());
            pst.setFloat(5, Float.parseFloat(valor));
            pst.setString(6, txtCliCpf.getText());
            pst.setString(7, txtOsTec.getText());
            pst.setString(8, cpf_user);    
            pst1.setString(1, txtOsTec.getText());
            rs = pst1.executeQuery();
            
            if(txtOsEquipamento.getText().isEmpty() || txtOsValor.getText().isEmpty() 
                    || txtCliCpf.getText().isEmpty() || txtOsTec.getText().isEmpty()){
                 JOptionPane.showMessageDialog(null,"Preencha todos os campos!");
            }
            else{
                if(rs.next()){
                    int adicionado = pst.executeUpdate();
                    if(adicionado > 0){
                         JOptionPane.showMessageDialog(null,"OS emetida com sucesso!");
                         
                         txtCliCpf.setText(null);
                         txtOsTec.setText(null);
                         txtOsValor.setText(null);
                         txtOsDescricao.setText(null);
                         txtOsEquipamento.setText(null);
                         
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Funcionário não cadastrado!");
                }
            
            }
        }catch (java.lang.NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Preencha todos os campos! ");
                //System.out.println(e);
        }catch (Exception e2) {
            JOptionPane.showMessageDialog(null,e2);
        }
    }
    
    // metodo para pesquisar uma os
    private void pesquisar_os(){
        // a linha abaixo cria uma entrada do tipo JOPtionPane
        String num_os = JOptionPane.showInputDialog("Número da OS");
        String sql = "SELECT * FROM ORDEM_SERVICO WHERE id_OS =" + num_os;
        try {
            pst= conexao_Postgres.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                txtOs.setText(rs.getString(1));
                txtData.setText(rs.getString(2));
                // setando os radio buttons
                String rbtTipo= rs.getString(3);
                if(rbtTipo.equals("Ordem de Serviço")){
                    rbtOs.setSelected(true);
                    tipo="Ordem de Serviço";
                }else{
                    rbtOrc.setSelected(true);
                    tipo="Orçamento";
                }
                cboOsSituacao.setSelectedItem(rs.getString(4));
                txtOsEquipamento.setText(rs.getString(5));
                txtOsTec.setText(rs.getString(9));
                txtOsValor.setText(rs.getString(7));
                txtOsDescricao.setText(rs.getString(6));
                txtCliCpf.setText(rs.getString(8));
                btnOsAdicionar.setEnabled(false);
                txtCliPesquisar.setEnabled(false);
                tblClientes.setVisible(false);
            }
            else{
                JOptionPane.showMessageDialog(null,"OS não cadastrada!");
            }
        } catch (org.postgresql.util.PSQLException e) {
            JOptionPane.showMessageDialog(null,"OS inválida");
            //System.out.println(e);
        }catch (Exception e2){
            JOptionPane.showMessageDialog(null,e2);
        }   
    }
    
    //metodo para alterar uma os
    private void alterar_os(){
        String sql = "UPDATE ordem_servico SET tipo=?, situacao=?,equipamento=?,descricao=?,valor_os=?,cpf_tec=?, cpf_user=? WHERE id_os=?";
        String sql2 = "SELECT * FROM ordem_servico WHERE id_os=?";
        try {
            
            pst1 = conexao_Postgres.prepareStatement(sql2);
            pst = conexao_Postgres.prepareStatement(sql);
            
            String valor = txtOsValor.getText();
            valor = valor.replace(",", ".");
            
            pst.setString(1, tipo);
            pst.setString(2, cboOsSituacao.getSelectedItem().toString());
            pst.setString(3, txtOsEquipamento.getText());
            pst.setString(4, txtOsDescricao.getText());
            pst.setFloat(5, Float.parseFloat(valor)); 
            pst.setString(6, txtOsTec.getText());
            pst.setString(7, cpf_user); 
            pst.setInt(8, Integer.parseInt(txtOs.getText()));
            
            
            pst1.setInt(1, Integer.parseInt(txtOs.getText()));
            rs = pst1.executeQuery();
            //System.out.println("OLA");
            
            if(txtOsEquipamento.getText().isEmpty() || txtOsValor.getText().isEmpty() 
                    || txtCliCpf.getText().isEmpty() || txtOsTec.getText().isEmpty() ||
                    txtOsDescricao.getText().isEmpty()){
                 JOptionPane.showMessageDialog(null,"Preencha todos os campos!");
            }
            else{
                if(rs.next()){
                    int adicionado = pst.executeUpdate();
                    if(adicionado > 0){
                         JOptionPane.showMessageDialog(null,"OS atualizada com sucesso!");
                        txtOs.setText(null);
                        txtData.setText(null);
                        txtCliCpf.setText(null);
                        txtOsTec.setText(null);
                        txtOsValor.setText(null);
                        txtOsDescricao.setText(null);
                        txtOsEquipamento.setText(null);
                         
                         //habilitar objetos
                        btnOsAdicionar.setEnabled(true);
                        txtCliPesquisar.setEnabled(true);
                        tblClientes.setVisible(true);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"OS não inexistente!");
                }
            }
        }catch(org.postgresql.util.PSQLException e){
            JOptionPane.showMessageDialog(null,e);
        }catch(java.lang.NumberFormatException e1){
            JOptionPane.showMessageDialog(null,"Preencha todos os campos!");
        }catch (Exception e2) {
            JOptionPane.showMessageDialog(null,e2);
            //System.out.println(e2);
        }
    }
    
    private void deletar(){
        int confirmar = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esta OS", "Atenção", JOptionPane.YES_NO_OPTION);
        if(confirmar == JOptionPane.YES_OPTION){
            String sql = "DELETE FROM ORDEM_SERVICO WHERE ID_OS=?";
            String sql2 = "SELECT * FROM ordem_servico WHERE id_os=?";
            String sql3 = "UPDATE ordem_servico SET cpf_user=? WHERE id_os=?";
            
            
            try {
                
                pst1 = conexao_Postgres.prepareStatement(sql2);
                pst1.setInt(1, Integer.parseInt(txtOs.getText()));
                rs = pst1.executeQuery();
                
                pst2 = conexao_Postgres.prepareStatement(sql3);
                pst2.setString(1, cpf_user);
                pst2.setInt(2, Integer.parseInt(txtOs.getText()));
                
                
                pst = conexao_Postgres.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(txtOs.getText()));
                
                if(txtOs.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Informe o N° da OS!");
                }
                else{
                    if(rs.next()){
                        pst2.executeUpdate();
                        int apagado = pst.executeUpdate();
                        
                        if(apagado > 0){    
                            JOptionPane.showMessageDialog(null, "OS excluída com sucesso!");
                            txtOs.setText(null);
                            txtData.setText(null);
                            txtCliCpf.setText(null);
                            txtOsTec.setText(null);
                            txtOsValor.setText(null);
                            txtOsDescricao.setText(null);
                            txtOsEquipamento.setText(null);
                         
                            //habilitar objetos
                            btnOsAdicionar.setEnabled(true);
                            txtCliPesquisar.setEnabled(true);
                            tblClientes.setVisible(true);                    
                        }
                    }
                    
                }
            }catch (java.lang.NumberFormatException e) {
                JOptionPane.showMessageDialog(null,"Informe o N° da OS ! ");
                //System.out.println(e);
            }catch(Exception e2){
                JOptionPane.showMessageDialog(null,e2);
            }
        }
        
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtData = new javax.swing.JTextField();
        txtOs = new javax.swing.JTextField();
        rbtOrc = new javax.swing.JRadioButton();
        rbtOs = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboOsSituacao = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtCliPesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        txtCliCpf = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtOsEquipamento = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtOsDescricao = new javax.swing.JTextPane();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtOsValor = new javax.swing.JTextField();
        btnOsAdicionar = new javax.swing.JButton();
        btnOsPesquisar = new javax.swing.JButton();
        btnOsAlterar = new javax.swing.JButton();
        btnOsExcluir = new javax.swing.JButton();
        txtOsTec = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnClean = new javax.swing.JButton();

        jLabel6.setText("jLabel6");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("OS");
        setPreferredSize(new java.awt.Dimension(677, 491));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
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
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Nº OS");

        jLabel2.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Data");

        txtData.setEditable(false);
        txtData.setEnabled(false);
        txtData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataActionPerformed(evt);
            }
        });

        txtOs.setEditable(false);
        txtOs.setEnabled(false);

        buttonGroup1.add(rbtOrc);
        rbtOrc.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        rbtOrc.setForeground(new java.awt.Color(102, 102, 102));
        rbtOrc.setText("Orçamento");
        rbtOrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrcActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOs);
        rbtOs.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        rbtOs.setForeground(new java.awt.Color(102, 102, 102));
        rbtOs.setText("Ordem de Serviço");
        rbtOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtOrc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rbtOs))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtData)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtOs, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOrc)
                    .addComponent(rbtOs))
                .addGap(30, 30, 30))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtData, txtOs});

        jLabel3.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Situação");

        cboOsSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Na bancada", "Entrega OK", "Orçamento Reprovado", "Aguardando Aprovação", "Aguardando peças", "Abandonado pelo cliente", "Retornou" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(102, 102, 102))); // NOI18N

        txtCliPesquisar.setMinimumSize(new java.awt.Dimension(20, 20));
        txtCliPesquisar.setPreferredSize(new java.awt.Dimension(20, 20));
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

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/search_icon.png"))); // NOI18N

        tblClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return(false);
            }
        };
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "CPF", "Nome", "Telefone"
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

        txtCliCpf.setEnabled(false);

        jLabel11.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("CPF:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCliCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(txtCliPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jLabel4))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCliCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCliPesquisar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jLabel7.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("*Equipamento:");

        jLabel8.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("*Descrição:");

        jScrollPane2.setViewportView(txtOsDescricao);

        jLabel9.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("*CPF técnico:");

        jLabel10.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Valor:");

        txtOsValor.setText("0");

        btnOsAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/create.png"))); // NOI18N
        btnOsAdicionar.setToolTipText("Adicionar");
        btnOsAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAdicionarActionPerformed(evt);
            }
        });

        btnOsPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/read.png"))); // NOI18N
        btnOsPesquisar.setToolTipText("Consultar");
        btnOsPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsPesquisarActionPerformed(evt);
            }
        });

        btnOsAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/update.png"))); // NOI18N
        btnOsAlterar.setToolTipText("Atualizar");
        btnOsAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAlterarActionPerformed(evt);
            }
        });

        btnOsExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bd/icones/delete.png"))); // NOI18N
        btnOsExcluir.setToolTipText("Delete");
        btnOsExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsExcluir.setPreferredSize(new java.awt.Dimension(70, 80));
        btnOsExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsExcluirActionPerformed(evt);
            }
        });

        txtOsTec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOsTecActionPerformed(evt);
            }
        });

        jLabel12.setText("* Campos Obrigatórios");

        btnClean.setFont(new java.awt.Font("Candara", 0, 11)); // NOI18N
        btnClean.setForeground(new java.awt.Color(102, 102, 102));
        btnClean.setText("Clean");
        btnClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(297, 297, 297)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(btnClean))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnOsAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnOsPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnOsAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnOsExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9))
                                        .addGap(24, 24, 24)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtOsTec, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cboOsSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel10))
                                        .addGap(12, 12, 12)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtOsEquipamento)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnOsAdicionar, btnOsAlterar, btnOsExcluir, btnOsPesquisar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboOsSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtOsTec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtOsEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClean)
                        .addGap(43, 43, 43)
                        .addComponent(jLabel12)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnOsAlterar)
                            .addComponent(btnOsExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnOsPesquisar)
                            .addComponent(btnOsAdicionar))
                        .addContainerGap(33, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnOsAdicionar, btnOsAlterar, btnOsExcluir, btnOsPesquisar});

        setBounds(0, 0, 677, 491);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOsExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsExcluirActionPerformed
        // deletar os
        deletar();
    }//GEN-LAST:event_btnOsExcluirActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // chamando o metodo chamar cliente
        pesquisar_cliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void txtOsTecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOsTecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOsTecActionPerformed

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        setar_campos();        
    }//GEN-LAST:event_tblClientesMouseClicked

    private void rbtOrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrcActionPerformed
        // atribuindo um texto a variavel tipo se selecionADO
        tipo = "Orçamento";
    }//GEN-LAST:event_rbtOrcActionPerformed

    private void rbtOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsActionPerformed
        // 
        tipo = "Ordem de Serviço";
    }//GEN-LAST:event_rbtOsActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // ao abrir o forme marcar o fomr orcamento
        rbtOrc.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOsAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAdicionarActionPerformed
        emitir_os();
    }//GEN-LAST:event_btnOsAdicionarActionPerformed

    private void btnOsPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsPesquisarActionPerformed
        // chamando o metodo pesquisr_os
        pesquisar_os();
    }//GEN-LAST:event_btnOsPesquisarActionPerformed

    private void txtDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataActionPerformed

    private void btnOsAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAlterarActionPerformed
        // chamando metodo para alterar OS
        alterar_os();
    }//GEN-LAST:event_btnOsAlterarActionPerformed

    private void btnCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanActionPerformed
        // TODO add your handling code here:
        txtOs.setText(null);
        txtData.setText(null);
        txtCliCpf.setText(null);
        txtOsTec.setText(null);
        txtOsValor.setText(null);
        txtOsDescricao.setText(null);
        txtOsEquipamento.setText(null);
                         
                         //habilitar objetos
        btnOsAdicionar.setEnabled(true);
        txtCliPesquisar.setEnabled(true);
        tblClientes.setVisible(true);
    }//GEN-LAST:event_btnCleanActionPerformed

    private void txtCliPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliPesquisarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClean;
    private javax.swing.JButton btnOsAdicionar;
    private javax.swing.JButton btnOsAlterar;
    private javax.swing.JButton btnOsExcluir;
    private javax.swing.JButton btnOsPesquisar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOsSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rbtOrc;
    private javax.swing.JRadioButton rbtOs;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliCpf;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtOs;
    private javax.swing.JTextPane txtOsDescricao;
    private javax.swing.JTextField txtOsEquipamento;
    private javax.swing.JTextField txtOsTec;
    private javax.swing.JTextField txtOsValor;
    // End of variables declaration//GEN-END:variables

}
