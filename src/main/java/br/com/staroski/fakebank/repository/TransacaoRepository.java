package br.com.staroski.fakebank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.staroski.fakebank.model.Agencia;
import br.com.staroski.fakebank.model.Cliente;
import br.com.staroski.fakebank.model.Conta;
import br.com.staroski.fakebank.model.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByAgencia(Agencia agencia);

    List<Transacao> findByCliente(Cliente cliente);

    List<Transacao> findByConta(Conta conta);

}
