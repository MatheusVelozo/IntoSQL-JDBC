package javaJDBC;

import conexaojdbc.SingleConnection;
import dao.UserPosDAO;
import model.Telefone;
import model.Userposjava;
import org.junit.Test;

import java.util.List;

public class TestBancoJdbc {

    //Criar novo registro.
    @Test
    public void initBanco() {
        UserPosDAO userPosDAO = new UserPosDAO();
        Userposjava userposjava = new Userposjava();

        userposjava.setNome("Hellen");
        userposjava.setEmail("hellen@gmail.com");


        userPosDAO.salvar(userposjava);
    }

    //Listar todos registros.
    @Test
    public void initListar() {
        UserPosDAO dao = new UserPosDAO();
        try {
            List<Userposjava> list = dao.listar();

            for (Userposjava userposjava : list) {
                System.out.println(userposjava);
                System.out.println("===============================");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Buscar por ID.
    @Test
    public void initBuscar() {
       UserPosDAO dao = new UserPosDAO();

        try {
            Userposjava userposjava = dao.buscar(4L);

            System.out.println(userposjava);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Atualizar registro por ID.
    @Test
    public void initAtualizar() {

        try {
            UserPosDAO userPosDAO = new UserPosDAO();

            Userposjava objetoBanco = userPosDAO.buscar(4L);

            objetoBanco.setNome("Atualizado");
            userPosDAO.atualizar(objetoBanco);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Deletar registro.
    @Test
    public void initDeletar() {
        try {
            UserPosDAO userPosDAO = new UserPosDAO();
            userPosDAO.deletar(4L);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testeInsertTelefone() {

        Telefone telefone = new Telefone();
        telefone.setNumero("(19) 992188469");
        telefone.setTipo("casa");
        telefone.setUsuario(5L);

        UserPosDAO userPosDAO = new UserPosDAO();
        userPosDAO.salvarTelefone(telefone);

    }

}
