package it.uniroma3.siw.musei.model.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RicercaDTO {

    private Long luogoId;
    private LocalDate dataDa;
    private LocalDate dataA;
    
}
