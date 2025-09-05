package br.unipar.devbackend.cadastroaluno.dao;

import br.unipar.devbackend.cadastroaluno.model.Endereco;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EnderecoDAO {

    EntityManager em;

    public EnderecoDAO(EntityManager em) {
        this.em = em;
    }

    public List<Endereco> findAll() {
        return em.createQuery("SELECT a FROM Endereco a", Endereco.class)
                .getResultList();
    }

    public Endereco findByCep(String cep) {
        return em.createQuery("SELECT e FROM Endereco e WHERE e.cep = :cep", Endereco.class)
                .setParameter("cep", cep).getSingleResult();
    }

    public Endereco findById(Long id) {
        return em.find(Endereco.class, id);
    }

    public Endereco inserirEndereco(Endereco endereco) {
        try {
            em.getTransaction().begin(); //abrimos a transação com o banco de dados
            em.persist(endereco); // insert - inserindo o endereco no banco de dados
            em.getTransaction().commit(); // confirma que é isso mesmo!
            return endereco; //retorna o endereco com ID (gerado no banco de dados)
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

    public Endereco editarEndereco(Endereco endereco) {
        try {
            em.getTransaction().begin(); //abrimos a transação com o banco de dados
            em.merge(endereco); // update - editando o endereco no banco de dados
            em.getTransaction().commit(); // confirma que é isso mesmo!
            return endereco; //retorna o endereco com ID (gerado no banco de dados)
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

    public Boolean deletarEndereco(Endereco endereco) {
        try {
            em.getTransaction().begin(); //abrimos a transação com o banco de dados
            em.remove(endereco); // delete - remove o endereco no banco de dados
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
