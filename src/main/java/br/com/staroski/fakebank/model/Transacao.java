package br.com.staroski.fakebank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "numeroAgencia")
    private Agencia agencia;

    @ManyToOne
    @JoinColumn(name = "numeroConta")
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "codigoCliente")
    private Cliente cliente;
}
