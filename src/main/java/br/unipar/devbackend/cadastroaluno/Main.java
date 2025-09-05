package br.unipar.devbackend.cadastroaluno;

import br.unipar.devbackend.cadastroaluno.dao.AlunoDAO;
import br.unipar.devbackend.cadastroaluno.model.Aluno;
import br.unipar.devbackend.cadastroaluno.util.EntityManagerUtil;

import java.util.Date;

public class Main {


    public static void main(String[] args) {
        EntityManagerUtil.getEmf(); //abrindo o sistema e a conexão com o banco de dados

        Aluno aluno = new Aluno(); // criamos um objeto aluno E instanciamos ele
        aluno.setNome("Juvenal Amaral"); // juvenal tem um nome
        aluno.setRa("60004869"); //juvenal tem ra
        aluno.setTelefone("(45) 98593-2694"); // juvenal tem telefone
        aluno.setEmail("jujuama@gmail.com"); // juvenal tem email
        aluno.setData_nasc(new Date("01/09/2007")); // juvenal nasceu um dia

        //criamos o objeto DAO (Data Access Object) responsável pelas transações com o banco de dados
        AlunoDAO alunoDAO = new AlunoDAO(EntityManagerUtil.getEntityManager()); //no construtor passamos uma nova EM
        alunoDAO.inserirAluno(aluno); //solicitamos que ele insira o novo aluno no banco de dados

        EntityManagerUtil.closeEntityManagerFactory(); // fechando o sistema e a conexão com o banco de dados
    }

}
