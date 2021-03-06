package br.com.staroski.fakebank.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.staroski.fakebank.dto.TransacaoDto;
import br.com.staroski.fakebank.dto.TransferenciaDto;
import br.com.staroski.fakebank.model.Agencia;
import br.com.staroski.fakebank.model.Conta;
import br.com.staroski.fakebank.model.Transacao;
import br.com.staroski.fakebank.repository.AgenciaRepository;
import br.com.staroski.fakebank.repository.ContaRepository;
import br.com.staroski.fakebank.repository.TransacaoRepository;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private AgenciaRepository agenciaRepository;

    @Autowired
    private ContaRepository contaRepository;

    @PostMapping("/listar")
    public List<TransacaoDto> listar(@RequestBody TransacaoDto dto) {
        Long numeroAgencia = dto.getNumeroAgencia();
        Long numeroConta = dto.getNumeroConta();
        Agencia agencia = agenciaRepository.findById(numeroAgencia)
                .orElseThrow(() -> new ObjectNotFoundException(numeroAgencia, Agencia.class.getSimpleName()));
        Conta conta = contaRepository.findByAgenciaAndNumero(agencia, numeroConta)
                .orElseThrow(() -> new ObjectNotFoundException(numeroConta, Conta.class.getSimpleName()));
        return transacaoRepository.findByConta(conta).stream().map(transacao -> new TransacaoDto(transacao)).toList();
    }

    @PostMapping("/depositar")
    @ResponseStatus(HttpStatus.OK)
    public TransacaoDto depositar(@RequestBody TransacaoDto dto) {
        BigDecimal valor = dto.getValor();
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Imposs??vel depositar valor negativo: " + valor);
        }
        Long numeroAgencia = dto.getNumeroAgencia();
        Long numeroConta = dto.getNumeroConta();
        Agencia agencia = agenciaRepository.findById(numeroAgencia)
                .orElseThrow(() -> new ObjectNotFoundException(numeroAgencia, Agencia.class.getSimpleName()));
        Conta conta = contaRepository.findByAgenciaAndNumero(agencia, numeroConta)
                .orElseThrow(() -> new ObjectNotFoundException(numeroConta, Conta.class.getSimpleName()));
        BigDecimal saldo = conta.getSaldo();
        conta.setSaldo(saldo.add(valor));
        contaRepository.save(conta);

        Transacao transacao = new Transacao();
        transacao.setDataHora(LocalDateTime.now());
        transacao.setAgencia(conta.getAgencia());
        transacao.setConta(conta);
        transacao.setCliente(conta.getCliente());
        transacao.setValor(valor);
        return new TransacaoDto(transacaoRepository.save(transacao));
    }

    @PostMapping("/sacar")
    @ResponseStatus(HttpStatus.OK)
    public TransacaoDto sacar(@RequestBody TransacaoDto dto) {
        BigDecimal valor = dto.getValor();
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Imposs??vel sacar valor negativo: " + valor);
        }
        Long numeroAgencia = dto.getNumeroAgencia();
        Long numeroConta = dto.getNumeroConta();
        Agencia agencia = agenciaRepository.findById(numeroAgencia)
                .orElseThrow(() -> new ObjectNotFoundException(numeroAgencia, Agencia.class.getSimpleName()));
        Conta conta = contaRepository.findByAgenciaAndNumero(agencia, numeroConta)
                .orElseThrow(() -> new ObjectNotFoundException(numeroConta, Conta.class.getSimpleName()));
        BigDecimal saldo = conta.getSaldo();
        if (saldo.compareTo(valor) < 0) {
            throw new IllegalArgumentException("Imposs??vel sacar " + valor + ", saldo dispon??vel: " + saldo);
        }
        conta.setSaldo(saldo.subtract(valor));
        contaRepository.save(conta);

        Transacao transacao = new Transacao();
        transacao.setDataHora(LocalDateTime.now());
        transacao.setAgencia(conta.getAgencia());
        transacao.setConta(conta);
        transacao.setCliente(conta.getCliente());
        transacao.setValor(valor.negate());
        return new TransacaoDto(transacaoRepository.save(transacao));
    }

    @PostMapping("/transferir")
    @ResponseStatus(HttpStatus.OK)
    public List<TransacaoDto> transferir(@RequestBody TransferenciaDto dto) {
        BigDecimal valorOrigem = dto.getValor();
        if (valorOrigem.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Imposs??vel sacar valor negativo: " + valorOrigem);
        }
        Long numeroAgenciaOrigem = dto.getAgenciaOrigem();
        Long numeroContaOrigem = dto.getContaOrigem();
        Agencia agenciaOrigem = agenciaRepository.findById(numeroAgenciaOrigem)
                .orElseThrow(() -> new ObjectNotFoundException(numeroAgenciaOrigem, Agencia.class.getSimpleName()));
        Conta contaOrigem = contaRepository.findByAgenciaAndNumero(agenciaOrigem, numeroContaOrigem)
                .orElseThrow(() -> new ObjectNotFoundException(numeroContaOrigem, Conta.class.getSimpleName()));

        Long numeroAgenciaDestino = dto.getAgenciaDestino();
        Long numeroContaDestino = dto.getContaDestino();
        Agencia agenciaDestino = agenciaRepository.findById(numeroAgenciaDestino)
                .orElseThrow(() -> new ObjectNotFoundException(numeroAgenciaDestino, Agencia.class.getSimpleName()));
        Conta contaDestino = contaRepository.findByAgenciaAndNumero(agenciaDestino, numeroContaDestino)
                .orElseThrow(() -> new ObjectNotFoundException(numeroContaDestino, Conta.class.getSimpleName()));

        if (numeroContaOrigem.compareTo(numeroContaDestino) == 0) {
            throw new IllegalArgumentException("Contas de origem e destino n??o podem ser iguais");
        }

        BigDecimal saldoOrigem = contaOrigem.getSaldo();
        if (saldoOrigem.compareTo(valorOrigem) < 0) {
            throw new IllegalArgumentException(
                    "Imposs??vel transferir " + valorOrigem + ", saldo dispon??vel: " + saldoOrigem);
        }

        BigDecimal saldoDestino = contaDestino.getSaldo();

        contaOrigem.setSaldo(saldoOrigem.subtract(valorOrigem));
        contaDestino.setSaldo(saldoDestino.add(valorOrigem));

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        LocalDateTime dataHora = LocalDateTime.now();

        Transacao transacao1 = new Transacao();
        transacao1.setDataHora(dataHora);
        transacao1.setAgencia(contaOrigem.getAgencia());
        transacao1.setConta(contaOrigem);
        transacao1.setCliente(contaOrigem.getCliente());
        transacao1.setValor(valorOrigem.negate());
        transacao1 = transacaoRepository.save(transacao1);

        Transacao transacao2 = new Transacao();
        transacao2.setDataHora(dataHora);
        transacao2.setAgencia(contaDestino.getAgencia());
        transacao2.setConta(contaDestino);
        transacao2.setCliente(contaDestino.getCliente());
        transacao2.setValor(valorOrigem);
        transacao2 = transacaoRepository.save(transacao2);

        return Arrays.asList(new TransacaoDto(transacao1), new TransacaoDto(transacao2));
    }
}
