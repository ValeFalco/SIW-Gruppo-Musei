package it.uniroma3.siw.musei.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.musei.model.Luogo;
import it.uniroma3.siw.musei.model.Museo;

@Repository
public interface MuseoRepository extends CrudRepository<Museo, Long> {

    public boolean existsByIdNotAndDataAndLuogo(Long id, LocalDate data, Luogo luogo);

    public List<Museo> findTop6ByDataBetweenOrderByData(LocalDate prima, LocalDate dopo);

    public int countByLuogo(Luogo luogo);
    public List<Museo> findByLuogo(Luogo luogo);

    public int countByDataGreaterThanEqual(LocalDate dataDa);
    public List<Museo> findByDataGreaterThanEqual(LocalDate dataDa);

    public int countByDataBetween(LocalDate dataDa, LocalDate dataA);
    public List<Museo> findByDataBetween(LocalDate prima, LocalDate dopo);

    public int countByLuogoAndDataGreaterThanEqual(Luogo luogo, LocalDate dataDa);
    public List<Museo> findByLuogoAndDataGreaterThanEqual(Luogo luogo, LocalDate dataDa);

    public int countByLuogoAndDataBetween(Luogo luogo, LocalDate dataDa, LocalDate dataA);
    public List<Museo> findByLuogoAndDataBetween(Luogo luogo, LocalDate dataDa, LocalDate dataA);
    
}
