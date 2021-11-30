package br.com.staroski.fakebank.dto;

import java.util.Collections;
import java.util.List;

import br.com.staroski.fakebank.model.Cliente;
import br.com.staroski.fakebank.model.Conta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

    private Long codigo;
    private String cpf;
    private String nome;
    private String login;
    private List<ContaDto> contas;

    public ClienteDto(Cliente cliente) {
        this.codigo = cliente.getCodigo();
        this.cpf = cliente.getCpf();
        this.nome = cliente.getNome();
        this.login = cliente.getLogin();
        List<Conta> contas = cliente.getContas();
        this.contas = contas != null
                ? contas.stream().map(conta -> new ContaDto(conta)).toList()
                : Collections.emptyList();
    }
}
