package it.uniroma3.siw.musei.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.AUTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Museo {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String nome;

    @NotNull
    @Future
    private LocalDate data;

    @NotNull
    private LocalTime orario;

    @OneToOne
    private Luogo luogo;

    @OneToMany(mappedBy = "museo", cascade = ALL)
    private List<Biglietto> biglietti;

    public Museo() {
        this.biglietti = new LinkedList<>();
    }

    public Museo(String nome, LocalDate data, LocalTime orario) {
        this();
        this.nome = nome;
        this.data = data;
        this.orario = orario;
    }
    
}
