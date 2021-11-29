package br.com.staroski.fakebank.controller.dto;

import java.util.Collections;
import java.util.List;

import br.com.staroski.fakebank.model.Agencia;
import br.com.staroski.fakebank.model.Conta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgenciaDto {

    private Long numero;
    private String nome;
    private List<ContaDto> contas;

    public AgenciaDto(Agencia agencia) {
        this.numero = agencia.getNumero();
        this.nome = agencia.getNome();
        List<Conta> contas = agencia.getContas();
        this.contas = contas != null
                ? contas.stream().map(conta -> new ContaDto(conta)).toList()
                : Collections.emptyList();
    }
}
