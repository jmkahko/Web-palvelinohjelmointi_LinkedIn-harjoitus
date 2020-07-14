/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author janne
 */
@Controller
public class PostausController {
    
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    PostausRepository postausRepository;
    
    @Autowired
    KommentointiRepository kommentointiRepository;
    
    
    @GetMapping("/etusivu")
    public String list(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Pageable pageable = PageRequest.of(0, 25, Sort.by("messageDate").descending());
        model.addAttribute("etusivu", accountRepository.findByUsername(username));
        model.addAttribute("postaukset", postausRepository.findAll(pageable));
        return "etusivu";
    }
    
    @GetMapping("/kommentoitykkays/{id}")
    public String komentoitavaksiHaku(Model model, @PathVariable Long id) {
        Pageable pageable_kommentti = PageRequest.of(0, 10, Sort.by("messageDate").descending());
        model.addAttribute("postaus", postausRepository.getOne(id));
        model.addAttribute("kommentointi", kommentointiRepository.findAll(pageable_kommentti));
        return "kommentoitykkays";
    }
    
    @PostMapping("/kommentoitykkays/{id}/tykkaa")
    public String tykkaa(@PathVariable Long id) {
        Postaus p = postausRepository.getOne(id);
        
        p.setTykkays(p.getTykkays()+1L);
        postausRepository.save(p);

        return "redirect:/etusivu";
    }
    
    @PostMapping("/kommentoitykkays/{id}/tykkaakommentissa")
    public String tykkaaKommentissa(@PathVariable Long id) {
        Postaus p = postausRepository.getOne(id);
        
        p.setTykkays(p.getTykkays()+1L);
        postausRepository.save(p);

        return "redirect:/kommentoitykkays/" + id;
    }
    
    @PostMapping("kommentoitykkays/{postausId}/kommentointi")
    public String kommentoi(@PathVariable Long postausId, @RequestParam String kommentoi) {
        Kommentointi k = new Kommentointi();
        Postaus p = postausRepository.getOne(postausId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);
        
        k.setKommentti(kommentoi);
        k.setKommentoija(account.getFullname());
        
        p.getKommentointi().add(k);

        kommentointiRepository.save(k);
        postausRepository.save(p);
        
        return "redirect:/kommentoitykkays/" + postausId;
    }
    

    @PostMapping("/etusivu")
    public String luo(@RequestParam String message) {
        Postaus postaus = new Postaus();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);
        
        postaus.setMessage(message);
        postaus.setTykkays(0L);
        List<Kommentointi> kommentointi = null;
        postaus.setKommentointi(kommentointi);
        postaus.setPostaaja(account.getFullname());
        
        postausRepository.save(postaus);
        return "redirect:/etusivu";
    } 
}


