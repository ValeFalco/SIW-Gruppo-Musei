package it.uniroma3.siw.musei.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.musei.model.Biglietto;
import it.uniroma3.siw.musei.model.Museo;

@Repository
public interface BigliettoRepository extends CrudRepository<Biglietto, Long> {

    public boolean existsByNomeAndMuseo(String nome, Museo museo);

}
