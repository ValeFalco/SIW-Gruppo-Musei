package it.uniroma3.siw.musei.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.musei.model.Utente;

@Repository
public interface UtenteRepository extends CrudRepository<Utente, Long> {
    
}
