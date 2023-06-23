package it.uniroma3.siw.musei.controllers.components.validators;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.musei.model.Luogo;
import it.uniroma3.siw.musei.model.Museo;
import it.uniroma3.siw.musei.services.MuseoService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MuseoValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(MuseoValidator.class);

    private final MuseoService museoService;

    @Override
    public void validate(Object target, Errors errors) {
        log.info("Validazione Museo Iniziata");
        Museo museo = (Museo)target;
        Long id = (museo.getId() != null) ? museo.getId() : 0l;
        LocalDate data = museo.getData();
        Luogo luogo = museo.getLuogo(); 

        log.debug("Validazione Globale");
        if(this.museoService.esisteMuseo(id, data, luogo)) {
            log.debug("Non si può organizzare più musei nella stessa data nello stesso luogo");
            errors.reject("Overlap.museo");
        }

        log.info("Validazione Museo Terminata");
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return Museo.class.equals(clazz);
    }

    
    
}
