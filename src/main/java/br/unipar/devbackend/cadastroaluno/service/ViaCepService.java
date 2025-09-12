package br.unipar.devbackend.cadastroaluno.service;

import br.unipar.devbackend.cadastroaluno.model.Endereco;
import jakarta.xml.bind.JAXBException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViaCepService {

    public static Endereco consultaAPI(String cep) throws IOException, JAXBException {
        String url = "https://viacep.com.br/ws/" + cep + "/xml/";

        //estabelecendo a conexão
        URL urlViaCep = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlViaCep.openConnection();
        connection.setRequestMethod("GET"); //metodo que pega o conteudo da api

        //leitura do conteudo
        BufferedReader leitor = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String linha;
        StringBuilder resposta = new StringBuilder();

        while ((linha = leitor.readLine()) != null) { //enquanto tiver
            resposta.append(linha); //incrementa na resposta a linha lida
        }
        leitor.close();

        System.out.println(resposta);

        return Endereco.unmarshalFromString(resposta.toString());
    }

}
