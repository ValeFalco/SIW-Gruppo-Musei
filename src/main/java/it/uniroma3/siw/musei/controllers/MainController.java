package it.uniroma3.siw.musei.controllers;

import static it.uniroma3.siw.musei.controllers.components.constants.CurrencyConstants.SIMBOLO_MONETA;
import static java.time.LocalDate.now;
import static java.time.format.TextStyle.SHORT;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.musei.controllers.components.UserDetailsComponent;
import it.uniroma3.siw.musei.model.Luogo;
import it.uniroma3.siw.musei.model.Museo;
import it.uniroma3.siw.musei.model.dto.RicercaDTO;
import it.uniroma3.siw.musei.services.LuogoService;
import it.uniroma3.siw.musei.services.MuseoService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private static final LocalDate DATA_DA = now();
    private static final LocalDate DATA_A = now().plusYears(1l);

    private final MuseoService museoService;
    private final LuogoService luogoService;

    private final UserDetailsComponent userDetailsComponent;

    @GetMapping(value = { "/", "/index" })
    public String index(Model model) {
        log.info("Richiesta GET /index");
        model.addAttribute("musei", this.museoService.getMusei(DATA_DA, DATA_A));
        return "index";
    }

    @GetMapping("/profilo")
    public String mostraProfilo(Model model) {
        model.addAttribute("utente", this.userDetailsComponent.getUtenteAutenticato());
        model.addAttribute("valuta", SIMBOLO_MONETA);
        return "profilo";
    }

    @GetMapping("/home")
    public String homeUtente(Model model) {
        log.info("Richiesta GET /home");
        model.addAttribute("numeroMusei", this.museoService.contaMusei());
        model.addAttribute("ricerca", new RicercaDTO());
        model.addAttribute("luoghi", this.luogoService.getLuoghi());
        model.addAttribute("stileData", SHORT);
        model.addAttribute("linguaData", Locale.getDefault());
        model.addAttribute("musei", this.museoService.getMusei());
        return "home";
    }

    @GetMapping("/admin/home")
    public String getAdminHome() {
        log.info("Richiesta GET /admin/home");
        return "admin/home";
    }

    @PostMapping("/ricerca")
    public String homeUtenteFiltrata(@ModelAttribute("ricerca") RicercaDTO ricerca, Model model) {
        Luogo luogo = (ricerca.getLuogoId() != 0l) ? this.luogoService.getLuogo(ricerca.getLuogoId()) : null;
        Pair<Integer, List<Museo>> museiFiltrati = this.museoService.getMusei(luogo, ricerca.getDataDa(), ricerca.getDataA());
        int nMusei = museiFiltrati.getFirst();
        List<Museo> musei = museiFiltrati.getSecond();
        model.addAttribute("numeroMusei", nMusei);
        model.addAttribute("ricerca", ricerca);
        model.addAttribute("luoghi", this.luogoService.getLuoghi());
        model.addAttribute("stileData", SHORT);
        model.addAttribute("linguaData", Locale.getDefault());
        model.addAttribute("musei", musei);
        return "home";
    }

}
