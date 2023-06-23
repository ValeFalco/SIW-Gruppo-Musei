package it.uniroma3.siw.musei.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.musei.model.Prenotazione;
import it.uniroma3.siw.musei.model.Utente;
import it.uniroma3.siw.musei.repository.PrenotazioneRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;

    @Transactional
    public void salvaPrenotazione(Prenotazione prenotazione) {
        this.prenotazioneRepository.save(prenotazione);
    }

    @Transactional
    public void cancellaPrenotazione(Long id) {
        this.prenotazioneRepository.deleteById(id);
    }

    public List<Prenotazione> getPrenotazioni(Utente utente) {
        return this.prenotazioneRepository.findByAcquirente(utente);
    }

    public Prenotazione getPrenotazione(Long id) {
        return this.prenotazioneRepository.findById(id).get();
    }
    
}
