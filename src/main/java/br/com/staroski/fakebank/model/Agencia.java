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
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    @Column(nullable = false)
    private String nome;

    @OneToMany
    @JoinTable(name = "agencia_contas",
               joinColumns = { @JoinColumn(name = "agencia_numero") },
               inverseJoinColumns = { @JoinColumn(name = "contas_numero") })
    private List<Conta> contas;
}
