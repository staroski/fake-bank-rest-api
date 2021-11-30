package br.com.staroski.fakebank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.staroski.fakebank.dto.ClienteDto;
import br.com.staroski.fakebank.model.Cliente;
import br.com.staroski.fakebank.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/listar")
    public List<ClienteDto> listar() {
        return clienteRepository.findAll().stream().map(cliente -> new ClienteDto(cliente)).toList();
    }

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDto criar(@RequestBody Cliente cliente) {
        return new ClienteDto(clienteRepository.save(cliente));
    }
}
