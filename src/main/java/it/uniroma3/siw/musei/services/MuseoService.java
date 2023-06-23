package it.uniroma3.siw.musei.services;

import static java.lang.Math.toIntExact;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.musei.model.Luogo;
import it.uniroma3.siw.musei.model.Museo;
import it.uniroma3.siw.musei.repository.MuseoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MuseoService {

    private final MuseoRepository museoRepository;

    @Transactional
    public Museo salvaMuseo(Museo museo) {
        return this.museoRepository.save(museo);
    }

    @Transactional
    public void cancellaMuseo(Long id) {
        this.museoRepository.deleteById(id);
    }

    public List<Museo> getMusei() {
        return stream(this.museoRepository.findAll().spliterator(), false).collect(toList());
    }

    public List<Museo> getMusei(LocalDate dataDa, LocalDate dataA) {
        return this.museoRepository.findTop6ByDataBetweenOrderByData(dataDa, dataA);
    }

    public Pair<Integer, List<Museo>> getMusei(Luogo luogo, LocalDate dataDa, LocalDate dataA) {
        Integer nMusei = 0;
        List<Museo> musei = null;
        if(luogo == null && dataDa == null && dataA == null) {
            nMusei = this.contaMusei();
            musei = this.getMusei();
        }
        if(luogo == null && dataDa == null && dataA != null) {
            nMusei = this.museoRepository.countByDataBetween(LocalDate.now(), dataA);
            musei = this.museoRepository.findByDataBetween(LocalDate.now(), dataA);
        }
        if(luogo == null && dataDa != null && dataA == null) {
            nMusei = this.museoRepository.countByDataGreaterThanEqual(dataDa);
            musei = this.museoRepository.findByDataGreaterThanEqual(dataDa);
        }
        if(luogo == null && dataDa != null && dataA != null) {
            nMusei = this.museoRepository.countByDataBetween(dataDa, dataA);
            musei = this.museoRepository.findByDataBetween(dataDa, dataA);
        }
        if(luogo != null && dataDa == null && dataA == null) {
            nMusei = this.museoRepository.countByLuogo(luogo);
            musei = this.museoRepository.findByLuogo(luogo);
        }
        if(luogo != null && dataDa == null && dataA != null) {
            nMusei = this.museoRepository.countByLuogoAndDataBetween(luogo, LocalDate.now(), dataA);
            musei = this.museoRepository.findByLuogoAndDataBetween(luogo, LocalDate.now(), dataA);
        }
        if(luogo != null && dataDa != null && dataA == null) {
            nMusei = this.museoRepository.countByLuogoAndDataGreaterThanEqual(luogo, dataDa);
            musei = this.museoRepository.findByLuogoAndDataGreaterThanEqual(luogo, dataDa);
        }
        if(luogo != null && dataDa != null && dataA != null) {
            nMusei = this.museoRepository.countByLuogoAndDataBetween(luogo, dataDa, dataA);
            musei = this.museoRepository.findByLuogoAndDataBetween(luogo, dataDa, dataA);
        }
        return Pair.of(nMusei, musei);
    }

    public Museo getMuseo(Long id) {
        return this.museoRepository.findById(id).get();
    }

    public int contaMusei() {
        return toIntExact(this.museoRepository.count());
    }

    public boolean esisteMuseo(Long id, LocalDate data, Luogo luogo) {
        return this.museoRepository.existsByIdNotAndDataAndLuogo(id, data, luogo);
    }
    
}
