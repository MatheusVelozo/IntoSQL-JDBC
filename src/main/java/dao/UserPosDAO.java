package dao;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserPosDAO {
//CRIA A CONEXÃO PARA APLICAR O CRUD COM O OBJETO USERPOSJAVA.
    private Connection connection;

    public UserPosDAO() {
        connection = SingleConnection.getConnection();
    }

    //INSERIR UM NOVO DADO.
    public void salvar(Userposjava userposjava) {

        try {
            String sql = "insert into userposjava (nome, email) values (?,?)";
            PreparedStatement insert = connection.prepareStatement(sql);
            insert.setString(1, userposjava.getNome());
            insert.setString(2, userposjava.getEmail());
            insert.execute();
            connection.commit(); //Salvar no banco de dados.

        } catch (Exception e) {
            try {
                connection.rollback(); //Reverte a operação.
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    //INSERIR TELEFONE NA TABELA RELACIONADA AO USUÁRIO.
    public void salvarTelefone(Telefone telefone) {

        try {

            String sql = "insert into telefoneuser (numero, tipo, usuariopessoa) values (?, ?, ?);";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, telefone.getNumero());
            statement.setString(2, telefone.getTipo());
            statement.setLong(3, telefone.getUsuario());
            statement.execute();
            connection.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    //FUNÇÃO DE BUSCAR TODOS DADOS DA TABELA.
    public List<Userposjava> listar () throws Exception{
        List<Userposjava> list = new ArrayList<Userposjava>();

        String sql = "select * from userposjava";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) {
            Userposjava userposjava = new Userposjava();
            userposjava.setId(resultado.getLong("id"));
            userposjava.setNome(resultado.getString("nome"));
            userposjava.setEmail(resultado.getString("email"));

            list.add(userposjava);
        }
        return list;
        }

    //FUNÇÃO DE BUSCAR APENAS UM DADO PELO ID.
    public Userposjava buscar (long id) throws Exception{
        Userposjava retorno = new Userposjava();

        String sql = "select * from userposjava where id = " + id;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) { //Retorna apenas um ou nenhum.
            retorno.setId(resultado.getLong("id"));
            retorno.setNome(resultado.getString("nome"));
            retorno.setEmail(resultado.getString("email"));

        }
        return retorno;
    }

    //RETORNA OS DADOS DE TELEFONE, EMAIL, E NOME DAS TABELAS RELACIONADAS.
    public List<BeanUserFone> listaUserFone (long idUser) {

        List<BeanUserFone> beanUserFones = new ArrayList<>();

        String sql = " select nome, numero, email from telefoneuser as fone ";
        sql += " inner join userposjava as userp ";
        sql += " on fone.usuariopessoa = userp.id ";
        sql += "  where userp.id = " + idUser;

    try {
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            BeanUserFone userFone = new BeanUserFone();
            userFone.setEmail(resultSet.getString("email"));
            userFone.setNome(resultSet.getString("nome"));
            userFone.setNumero(resultSet.getString("numero"));

            beanUserFones.add(userFone);

        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return beanUserFones;
}

    //ATUALIZAR REGISTRO.
    public void atualizar (Userposjava userposjava) {

        try {
            String sql = "update userposjava set nome = ? where id = " + userposjava.getId();

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userposjava.getNome());

            statement.execute();
            connection.commit();

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    //DELETAR REGISTRO.
    public void deletar(long id) {
        try {
        String sql = "delete from userposjava where id = " + id;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    }

