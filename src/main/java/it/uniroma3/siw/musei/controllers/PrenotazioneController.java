package it.uniroma3.siw.musei.controllers;

import static it.uniroma3.siw.musei.model.Biglietto.aumentaQuantita;
import static it.uniroma3.siw.musei.model.Biglietto.riduciQuantita;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.musei.controllers.components.UserDetailsComponent;
import it.uniroma3.siw.musei.model.Biglietto;
import it.uniroma3.siw.musei.model.Prenotazione;
import it.uniroma3.siw.musei.model.Utente;
import it.uniroma3.siw.musei.services.BigliettoService;
import it.uniroma3.siw.musei.services.PrenotazioneService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PrenotazioneController {

    private static final Logger log = LoggerFactory.getLogger(PrenotazioneController.class);

    private final BigliettoService bigliettoService;
    private final PrenotazioneService prenotazioneService;

    private final UserDetailsComponent userDetailsComponent;

    @GetMapping("/prenotazioni/biglietti/{id}")
    public String effettuaPrenotazione(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        log.info("Richiesta GET /prenotazione/biglietto/" + id);
        Biglietto biglietto = this.bigliettoService.getBiglietto(id);
        riduciQuantita(biglietto);
        Utente utente = this.userDetailsComponent.getUtenteAutenticato();
        Prenotazione prenotazione = new Prenotazione(biglietto, utente);
        this.prenotazioneService.salvaPrenotazione(prenotazione);
        redirectAttributes.addAttribute("prenotazione", true);
        return "redirect:/musei/" + biglietto.getMuseo().getId();
    }

    @GetMapping("/prenotazioni/{id}/delete")
    public String annullaPrenotazione(@PathVariable Long id, Model model) {
        log.info("Richiesta GET /prenotazione/" + id + "/delete");
        Prenotazione prenotazione = this.prenotazioneService.getPrenotazione(id);
        Biglietto biglietto = prenotazione.getBiglietto();
        aumentaQuantita(biglietto);
        this.bigliettoService.salvaBiglietto(biglietto);
        this.prenotazioneService.cancellaPrenotazione(id);
        return "redirect:/profilo";
    }
    
}
