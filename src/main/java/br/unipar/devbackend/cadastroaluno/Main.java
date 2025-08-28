package br.unipar.devbackend.cadastroaluno;

import br.unipar.devbackend.cadastroaluno.model.Aluno;
import br.unipar.devbackend.cadastroaluno.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;

import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerUtil.getEmf();

        EntityManager em = EntityManagerUtil.getEmf().createEntityManager();
        List<Aluno> alunos = em.createQuery("SELECT t FROM Aluno t", Aluno.class)
                .getResultList();

        for (Aluno al : alunos) {
            System.out.println("Aluno: " + al.getId() + " - " +
                    al.getNome() + " - RA: " + al.getRa());
        }

        EntityManagerUtil.closeEntityManagerFactory();
    }


//    public static void main(String[] args) {
//        EntityManagerUtil.getEmf();
//
//        Aluno aluno = new Aluno();
//        aluno.setNome("Maria da Silva");
//        aluno.setRa("60007565");
//        aluno.setData_nasc(new Date("03/05/2000"));
//        aluno.setEmail("mariazinha@gmail.com");
//        aluno.setTelefone("(45) 9 9471-3954");
//
//        EntityManager em = EntityManagerUtil.getEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.persist(aluno);
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            em.getTransaction().rollback();
//            System.out.println("Erro: " + ex.getMessage());
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//                System.out.println("EntityManager fechado com sucesso!");
//            }
//        }
//
//        EntityManagerUtil.closeEntityManagerFactory();
//    }
}
