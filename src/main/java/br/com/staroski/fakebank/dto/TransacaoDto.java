package br.com.staroski.fakebank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.staroski.fakebank.model.Transacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDto {

    private Long id;
    private LocalDateTime dataHora;
    private Long numeroAgencia;
    private Long numeroConta;
    private Long codigoCliente;
    private BigDecimal valor;

    public TransacaoDto(Transacao transacao) {
        this.id = transacao.getId();
        this.dataHora = transacao.getDataHora();
        this.numeroAgencia = transacao.getAgencia().getNumero();
        this.numeroConta = transacao.getConta().getNumero();
        this.codigoCliente = transacao.getCliente().getCodigo();
        this.valor = transacao.getValor();
    }
}
