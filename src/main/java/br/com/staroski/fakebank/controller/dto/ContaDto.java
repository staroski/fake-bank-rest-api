package br.com.staroski.fakebank.controller.dto;

import java.math.BigDecimal;

import br.com.staroski.fakebank.model.Conta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaDto {

    private Long codigoCliente;
    private Long numeroAgencia;
    private Long numeroConta;
    private BigDecimal saldo;

    public ContaDto(Conta conta) {
        this.codigoCliente = conta.getCliente().getCodigo();
        this.numeroAgencia = conta.getAgencia().getNumero();
        this.numeroConta = conta.getNumero();
        this.saldo = conta.getSaldo();
    }
}
