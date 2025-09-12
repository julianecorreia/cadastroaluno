package br.unipar.devbackend.cadastroaluno;

import br.unipar.devbackend.cadastroaluno.dao.AlunoDAO;
import br.unipar.devbackend.cadastroaluno.dao.EnderecoDAO;
import br.unipar.devbackend.cadastroaluno.model.Aluno;
import br.unipar.devbackend.cadastroaluno.model.Endereco;
import br.unipar.devbackend.cadastroaluno.service.ViaCepService;
import br.unipar.devbackend.cadastroaluno.util.EntityManagerUtil;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MainAtividade {


    public static void main(String[] args) throws JAXBException, IOException {

        //O usuário deve informar um CEP
        Scanner s = new Scanner(System.in);
        System.out.println("Digite o CEP:");
        String cep = s.nextLine(); // caso utilize sem hífen .replace("-", "");

        System.out.println("Você digitou o CEP: " + cep);

        //O programa deve consultar esse CEP no banco de dados,
        // via Hibernate, se já existe um endereço cadastrado com o CEP informado.
        EntityManagerUtil.getEmf(); //criando a fabrica de entities managers

        EnderecoDAO endDAO = new EnderecoDAO(EntityManagerUtil.getEntityManager()); // gerenciador que vai buscar as coisas no banco de dados
        List<Endereco> enderecosList = endDAO.findByCep(cep); // buscando o cep no banco de dados

        if (enderecosList.isEmpty()){
            System.out.println("CEP não existe no banco de dados.");
            //Se o CEP não existir: O sistema deve consultar a API ViaCEP para recuperar os atributos completos do
            // endereço (logradouro, bairro, cidade, estado, etc.). E com os dados retornados pela API criar um
            // novo registro de endereço no banco de dados, armazenando também a data e hora da gravação.

            Endereco novoEndereco = ViaCepService.consultaAPI(cep);
            System.out.println("Endereço retornado pela API ViaCEP:");
            System.out.println(novoEndereco.toString());

            novoEndereco.setDataCadastro(LocalDateTime.now());

            EnderecoDAO endNovoDAO = new EnderecoDAO(EntityManagerUtil.getEntityManager());
            endNovoDAO.inserirEndereco(novoEndereco);
        } else {
            System.out.println("CEP encontrado!");
            //Se o CEP existir no banco de dados:
            // O sistema deve solicitar o cadastro de um novo aluno associado a esse endereço.

            System.out.println("Vamos cadastrar um novo aluno para esse endereço!");
            Aluno aluno = new Aluno(); // criamos um objeto aluno E instanciamos ele
            System.out.println("Digite o nome do aluno:");
            aluno.setNome(s.nextLine());
            System.out.println("Digite o RA do aluno:");
            aluno.setRa(s.nextLine());
            System.out.println("Digite o telefone do aluno:");
            aluno.setTelefone(s.nextLine());
            System.out.println("Digite o email do aluno:");
            aluno.setEmail(s.nextLine());
            System.out.println("Digite a data de nascimento do aluno:");
            aluno.setData_nasc(new java.util.Date(s.nextLine()));

            //numa aplicação real, só salvariamos endereço JUNTO com aluno, então seria assim:
            //aluno.setEndereco(endereco);

            //acima preenchemos os dados do aluno no objeto aluno

            AlunoDAO aluDAO = new AlunoDAO(EntityManagerUtil.getEntityManager()); // gerenciador que vai mandar o aluno pro banco
            aluDAO.inserirAluno(aluno); // inserindo o aluno no banco de dados

            //aqui editamos APENAS endereço:
            for (Endereco endereco : enderecosList) { //foreach - ele percorre TODA a lista
                EnderecoDAO enderecoDAO = new EnderecoDAO(EntityManagerUtil.getEntityManager()); // gerenciador que vai atualizar o endereço

                endereco.setAluno(aluno);
                enderecoDAO.editarEndereco(endereco);
            }

        }

        EntityManagerUtil.closeEntityManagerFactory(); // fechando a fabrica de entity managers
    }
}
