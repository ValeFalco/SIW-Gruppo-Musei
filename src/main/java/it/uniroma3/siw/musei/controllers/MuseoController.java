package it.uniroma3.siw.musei.controllers;

import static it.uniroma3.siw.musei.controllers.components.constants.CurrencyConstants.SIMBOLO_MONETA;
import static java.time.format.TextStyle.SHORT;

import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.musei.controllers.components.validators.BigliettoValidator;
import it.uniroma3.siw.musei.controllers.components.validators.MuseoValidator;
import it.uniroma3.siw.musei.model.Biglietto;
import it.uniroma3.siw.musei.model.Museo;
import it.uniroma3.siw.musei.model.enumeration.TipoBiglietto;
import it.uniroma3.siw.musei.services.LuogoService;
import it.uniroma3.siw.musei.services.MuseoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MuseoController {

    private static final Logger log = LoggerFactory.getLogger(MuseoController.class);

    private final MuseoService museoService;
    private final LuogoService luogoService;
    private final MuseoValidator museoValidator;
    private final BigliettoValidator bigliettoValidator;

    @GetMapping("/musei/{id}")
    public String mostraMuseo(
            @RequestParam(required = false, value = "prenotazione") boolean prenotazione,
            @PathVariable Long id,
            Model model) {
        log.info("Richiesta GET /musei/" + id);
        Museo museo = this.museoService.getMuseo(id);
        String giorno = museo.getData().getDayOfWeek().getDisplayName(SHORT, Locale.getDefault());
        model.addAttribute("prenotazioneEffettuata", prenotazione);
        model.addAttribute("giorno", giorno);
        model.addAttribute("museo", museo);
        model.addAttribute("valuta", SIMBOLO_MONETA);
        return "museo";
    }

    @GetMapping("/admin/musei")
    public String getMusei(Model model) {
        log.info("Richiesta GET /admin/musei");
        model.addAttribute("musei", this.museoService.getMusei());
        return "admin/musei";
    }

    @GetMapping("/admin/musei/new")
    public String newMuseo(Model model) {
        log.info("Richiesta GET /admin/musei/new");
        model.addAttribute("museo", new Museo());
        model.addAttribute("luoghi", this.luogoService.getLuoghi());
        return "admin/museoForm";
    }

    @GetMapping("/admin/musei/{id}/modify")
    public String modificaMuseo(@PathVariable Long id, Model model) {
        log.info("Richiesta GET /admin/musei/" + id + "/modify");
        model.addAttribute("museo", this.museoService.getMuseo(id));
        model.addAttribute("luoghi", this.luogoService.getLuoghi());
        return "admin/museoForm";
    }

    @GetMapping("/admin/musei/{id}/delete")
    public String cancellaMuseo(@PathVariable Long id, Model model) {
        log.info("Richiesta GET /admin/musei/" + id + "/delete");
        this.museoService.cancellaMuseo(id);
        return "redirect:/admin/musei";
    }

    @GetMapping("/admin/musei/{id}/biglietti/new")
    public String creaBigliettiMuseo(@PathVariable Long id, Model model) {
        log.info("Richiesta GET /admin/musei/" + id + "/biglietti/new");
        model.addAttribute("museo", this.museoService.getMuseo(id));
        model.addAttribute("biglietto", new Biglietto());
        model.addAttribute("tipi", TipoBiglietto.values());
        return "admin/bigliettoForm";
    }

    @PostMapping("/admin/musei/new")
    public String saveMuseo(@Valid @ModelAttribute Museo museo, BindingResult bindingResult, Model model) {
        log.info("Richiesta POST /admin/musei/new");
        this.museoValidator.validate(museo, bindingResult);

        if (!bindingResult.hasErrors()) {
            log.info("Parametri inseriti Corretti");
            this.museoService.salvaMuseo(museo);
            return "redirect:/admin/musei";
        }
        log.warn("Parametri inseriti non Validi");
        model.addAttribute("luoghi", this.luogoService.getLuoghi());
        return "admin/museoForm";
    }

    @PostMapping("/admin/musei/{id}/biglietti")
    public String salvaBigliettiMuseo(
            @Valid @ModelAttribute Biglietto biglietto,
            BindingResult bindingResult,
            @PathVariable("id") Long idMuseo,
            Model model) {
        log.info("Richiesta POST /admin/museo/" + idMuseo + "/biglietti");
        Museo museo = this.museoService.getMuseo(idMuseo);
        museo.getBiglietti().add(biglietto);
        biglietto.setMuseo(museo);
        this.bigliettoValidator.validate(biglietto, bindingResult);

        if (!bindingResult.hasErrors()) {
            this.museoService.salvaMuseo(museo);
            return "redirect:/musei/" + idMuseo;
        }
        model.addAttribute("museo", museo);
        model.addAttribute("tipi", TipoBiglietto.values());
        return "admin/bigliettoForm";
    }

}
