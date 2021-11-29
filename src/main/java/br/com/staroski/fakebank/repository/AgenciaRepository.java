package br.com.staroski.fakebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.staroski.fakebank.model.Agencia;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Long> {

}
