package br.com.staroski.fakebank.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;

    @OneToMany
    @JoinTable(name = "cliente_contas",
               joinColumns = { @JoinColumn(name = "cliente_codigo") },
               inverseJoinColumns = {@JoinColumn(name = "contas_numero") })
    private List<Conta> contas;
}
