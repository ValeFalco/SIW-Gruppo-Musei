package it.uniroma3.siw.musei.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.musei.model.Biglietto;
import it.uniroma3.siw.musei.model.Museo;
import it.uniroma3.siw.musei.repository.BigliettoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BigliettoService {

    private final BigliettoRepository bigliettoRepository;

    @Transactional
    public Biglietto salvaBiglietto(Biglietto biglietto) {
        return this.bigliettoRepository.save(biglietto);
    }

    public Biglietto getBiglietto(Long id) {
        return this.bigliettoRepository.findById(id).get();
    }

    public boolean esisteBiglietto(String nome, Museo museo) {
        return this.bigliettoRepository.existsByNomeAndMuseo(nome, museo);
    }
    
}
