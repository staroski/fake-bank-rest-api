package br.com.staroski.fakebank.controller;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.staroski.fakebank.controller.dto.ContaDto;
import br.com.staroski.fakebank.model.Agencia;
import br.com.staroski.fakebank.model.Cliente;
import br.com.staroski.fakebank.model.Conta;
import br.com.staroski.fakebank.repository.AgenciaRepository;
import br.com.staroski.fakebank.repository.ClienteRepository;
import br.com.staroski.fakebank.repository.ContaRepository;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AgenciaRepository agenciaRepository;

    @GetMapping("/listar")
    public List<ContaDto> listar() {
        return contaRepository.findAll().stream().map(conta -> new ContaDto(conta)).toList();
    }

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public ContaDto criar(@RequestBody ContaDto dto) {
        Long numeroAgencia = dto.getNumeroAgencia();
        Agencia agencia = agenciaRepository.findById(numeroAgencia)
                .orElseThrow(() -> new ObjectNotFoundException(numeroAgencia, Agencia.class.getSimpleName()));

        Long codigoCliente = dto.getCodigoCliente();
        Cliente cliente = clienteRepository.findById(codigoCliente)
                .orElseThrow(() -> new ObjectNotFoundException(codigoCliente, Cliente.class.getSimpleName()));

        BigDecimal saldo = dto.getSaldo();

        Conta conta = new Conta();
        conta.setAgencia(agencia);
        conta.setCliente(cliente);
        conta.setSaldo(saldo);
        conta = contaRepository.save(conta);

        Long numeroConta = conta.getNumero();

        agencia.getContas().add(conta);
        agenciaRepository.save(agencia);

        cliente.getContas().add(conta);
        clienteRepository.save(cliente);

        return new ContaDto(codigoCliente, numeroAgencia, numeroConta, saldo);
    }
}
