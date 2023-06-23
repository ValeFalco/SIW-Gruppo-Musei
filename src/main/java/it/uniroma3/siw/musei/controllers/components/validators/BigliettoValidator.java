package it.uniroma3.siw.musei.controllers.components.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.musei.model.Biglietto;
import it.uniroma3.siw.musei.services.BigliettoService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BigliettoValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(BigliettoValidator.class);

    private final BigliettoService bigliettoService;

    @Override
    public void validate(Object target, Errors errors) {
        log.info("Validazione Biglietto Iniziata");
        Biglietto biglietto = (Biglietto)target;
        String nome = biglietto.getNome().trim();

        log.debug("Validazione Globale");
        if(this.bigliettoService.esisteBiglietto(nome, biglietto.getMuseo())) {
            log.debug("Questo museo ha gia un biglietto con questo nome");
            errors.reject("Unique.biglietto");
        }

        log.info("Validazione Biglietto Terminata");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Biglietto.class.equals(clazz);
    }

    


    
}
