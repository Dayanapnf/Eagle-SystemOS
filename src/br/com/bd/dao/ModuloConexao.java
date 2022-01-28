package br.com.bd.dao;
import java.sql.*;
/**
 *
 * @author daypn
 */
public class ModuloConexao {
    
    private static Connection conexao_Postgres = null;
    
    
    private static String url = "jdbc:postgresql://localhost:5432/Projeto_Os";
    
    private static final String usuario = "postgres";
    private static final String senha = "day293847"; 
    
    //estabelecendo a conexao com o banco
    public static Connection conector(){
        try {
           conexao_Postgres = DriverManager.getConnection(url,usuario,senha);
           System.out.println("Conexão bem sucedida!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conexao_Postgres;
    }
    
    public static void desconectar(){
        try {
            if(conexao_Postgres != null){
                conexao_Postgres.close();
                System.out.println("Conexão encerrada!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}