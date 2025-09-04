package br.unipar.devbackend.cadastroaluno.dao;

import br.unipar.devbackend.cadastroaluno.model.Aluno;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AlunoDAO { // Data Access Object

    //metodos de crud
    //create (x) / read (x) / update (x) / delete (x)

    EntityManager em;

    public AlunoDAO(EntityManager em) {
        this.em = em;
    }

    public List<Aluno> findAll() {
        return em.createQuery("SELECT a FROM Aluno a", Aluno.class)
                .getResultList();
    }

    public Aluno findByRA(String ra) { //HQL - Hibernate Query Language
        return em.createQuery("SELECT a FROM Aluno a WHERE a.ra = :ra", Aluno.class)
                .setParameter("ra", ra).getSingleResult();
    }

    public Aluno findById(Long id) {
        return em.find(Aluno.class, id);
    }

    public Aluno inserirAluno(Aluno aluno) {
        try {
            em.getTransaction().begin(); //abrimos a transação com o banco de dados
            em.persist(aluno); // insert - inserindo o aluno no banco de dados
            em.getTransaction().commit(); // confirma que é isso mesmo!
            return aluno; //retorna o aluno com ID (gerado no banco de dados)
        } catch (Exception ex) {
            em.getTransaction().rollback(); //desfazer a operação
            System.out.println("Algo de errado não deu certo: " + ex.getMessage());
            return null;
        } finally {
            if(em.isOpen()) {
                em.close();
                System.out.println("EntityManager fechado com sucesso!");
            }
        }
    }

    public Aluno editarAluno(Aluno aluno) {
        try {
            em.getTransaction().begin(); //abrimos a transação com o banco de dados
            em.merge(aluno); // update - editando o aluno no banco de dados
            em.getTransaction().commit(); // confirma que é isso mesmo!
            return aluno; //retorna o aluno com ID (gerado no banco de dados)
        } catch (Exception ex) {
            em.getTransaction().rollback(); //desfazer a operação
            System.out.println("Algo de errado não deu certo: " + ex.getMessage());
            return null;
        } finally {
            if(em.isOpen()) {
                em.close();
                System.out.println("EntityManager fechado com sucesso!");
            }
        }
    }

    public Boolean deletarAluno(Aluno aluno) {
        try {
            em.getTransaction().begin(); //abrimos a transação com o banco de dados
            em.remove(aluno); // delete - remove o aluno no banco de dados
            em.getTransaction().commit(); // confirma que é isso mesmo!
            return true; //retorna que deu certo a exclusão
        } catch (Exception ex) {
            em.getTransaction().rollback(); //desfazer a operação
            System.out.println("Algo de errado não deu certo: " + ex.getMessage());
            return false;
        } finally {
            if(em.isOpen()) {
                em.close();
                System.out.println("EntityManager fechado com sucesso!");
            }
        }
    }


}
