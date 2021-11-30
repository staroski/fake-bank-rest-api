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

import br.com.staroski.fakebank.dto.AgenciaDto;
import br.com.staroski.fakebank.model.Agencia;
import br.com.staroski.fakebank.repository.AgenciaRepository;

@RestController
@RequestMapping("/agencias")
public class AgenciaController {

    @Autowired
    private AgenciaRepository agenciaRepository;

    @GetMapping("/listar")
    public List<AgenciaDto> listar() {
        return agenciaRepository.findAll().stream().map(agencia -> new AgenciaDto(agencia)).toList();
    }

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public AgenciaDto criar(@RequestBody Agencia cliente) {
        return new AgenciaDto(agenciaRepository.save(cliente));
    }
}
