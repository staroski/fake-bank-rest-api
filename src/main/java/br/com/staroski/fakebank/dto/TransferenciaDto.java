package br.com.staroski.fakebank.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaDto {

    private Long agenciaOrigem;
    private Long contaOrigem;
    private BigDecimal valor;
    private Long agenciaDestino;
    private Long contaDestino;
}
