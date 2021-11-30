package br.com.staroski.fakebank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.staroski.fakebank.model.Agencia;
import br.com.staroski.fakebank.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByAgenciaAndNumero(Agencia agencia, Long numero);
}
