package it.uniroma3.siw.musei.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.musei.model.Prenotazione;
import it.uniroma3.siw.musei.model.Utente;

@Repository
public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {

    public List<Prenotazione> findByAcquirente(Utente acquirente);
    
}
