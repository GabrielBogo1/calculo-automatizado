package br.com.entregarequisicoes.requisicoescalculos.service;

import br.com.entregarequisicoes.requisicoescalculos.dto.CalculoDTO;
import br.com.entregarequisicoes.requisicoescalculos.dto.EntradaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

@Service
public class CalculaService {

    public CalculoDTO calcular (EntradaDTO entradaDTO){

        if (entradaDTO.getNumeros().size() < 20){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else {
            CalculoDTO calculoDTO = new CalculoDTO();
            calculoDTO.setQtdNumeros(entradaDTO.getNumeros().size());

            calculoDTO.setMedia(this.media(entradaDTO));
            calculoDTO.setMediana(this.mediana(entradaDTO));
            calculoDTO.setDesvioPadrao(this.desvioPadrao(entradaDTO));

            return calculoDTO;
        }
    }

    private double media (EntradaDTO entradaDTO){

        int soma = 0;

        for (int i = 0; i<entradaDTO.getNumeros().size(); i++){
            soma += entradaDTO.getNumeros().get(i);
        }

        return soma / entradaDTO.getNumeros().size();
    }

    private double mediana (EntradaDTO entradaDTO){

        Collections.sort(entradaDTO.getNumeros());

        if (entradaDTO.getNumeros().size() % 2 == 0){
            double posicaoDir = entradaDTO.getNumeros().size() / 2;
            double posicaoEsq = posicaoDir - 1;
            return (posicaoDir + posicaoEsq) / 2;
        }else{
            double result = entradaDTO.getNumeros().size() / 2;
            int posicao = (int) result;

            return entradaDTO.getNumeros().get(posicao);
        }
    }

    private double desvioPadrao (EntradaDTO entradaDTO){

        double soma = 0;

        for (int i = 0; i<entradaDTO.getNumeros().size(); i++){
            double aux = (entradaDTO.getNumeros().get(i) - this.media(entradaDTO));
            double resultado = aux * aux;
            soma += resultado;
        }

        double result = soma/entradaDTO.getNumeros().size();

        return Math.sqrt(result);
    }
}
