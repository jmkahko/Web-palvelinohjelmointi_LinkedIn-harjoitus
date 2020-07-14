/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.io.IOException;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author janne
 */
@Controller
public class ProfiiliController {
    
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    ProfiiliRepository profiiliRepository;
    
    @Autowired
    TaitoRepository taitoRepository;
    
    @Autowired
    YhteydetRepository yhteydetRepository;

    
    @GetMapping("/profiili")
    public String profiilisivu(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Pageable pageable_taitotop = PageRequest.of(0, 3, Sort.by("tykkays").descending());

        model.addAttribute("profiili", accountRepository.findByUsername(username));
        model.addAttribute("taito_top", taitoRepository.findAll(pageable_taitotop));
        model.addAttribute("taito", taitoRepository.findAll());
        model.addAttribute("odottavat", yhteydetRepository.findByPyynto(TRUE));
        model.addAttribute("hyvaksytyt", yhteydetRepository.findByHyvaksytty(TRUE));
        return "profiili";
    }
    
   
    @PostMapping("/profiili/kuva")
    public String lisaaKuva(@RequestParam("file") MultipartFile file) throws IOException {
        Profiili fo = new Profiili();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);

        fo.setContent(file.getBytes());
        
        if (file.getContentType().equals("image/gif")) {
            profiiliRepository.save(fo);
        }
        
        account.setProfiili(fo);
        
        accountRepository.save(account);

        return "redirect:/profiili";
    }
    
    @PostMapping("/profiili/lisaataito")
    public String lisaaTaito(@RequestParam String taitolisays) {
        Taito taito = new Taito();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);
        
        taito.setTykkays(0L);
        taito.setTaito(taitolisays);
        
        account.getTaidot().add(taito);

        taitoRepository.save(taito);
        accountRepository.save(account);
        
        return "redirect:/profiili";
    }
    
    @PostMapping("/profiili/{taitotykkaysId}/tykkaataitoa")
    public String tykkaaTaidosta(@PathVariable Long taitotykkaysId) {
        Taito t = taitoRepository.getOne(taitotykkaysId);

        t.setTykkays(t.getTykkays()+1L);
        taitoRepository.save(t);
       
        return "redirect:/profiili";
    }
    
    @PostMapping("/profiili/{yhteysId}/yhteyshyvaksy")
    public String hyvaksyYhteys(@PathVariable Long yhteysId) {
        Yhteydet y = yhteydetRepository.getOne(yhteysId);
        
        y.setHyvaksytty(Boolean.TRUE);
        y.setPyynto(FALSE);
        
        yhteydetRepository.save(y);
       
        return "redirect:/profiili";
    }
    
    @PostMapping("/profiili/{yhteysId}/yhteyshylkaa")
    public String hylkaaYhteys(@PathVariable Long yhteysId) {
        Yhteydet y = yhteydetRepository.getOne(yhteysId);
        
        y.setPyynto(Boolean.FALSE);
        
        yhteydetRepository.save(y);
       
        return "redirect:/profiili";
    }
    
    @PostMapping("/profiili/{yhteysId}/yhteyskatkaise")
    public String katkaiseYhteys(@PathVariable Long yhteysId) {
        Yhteydet y = yhteydetRepository.getOne(yhteysId);
        
        y.setPyynto(Boolean.FALSE);
        y.setHyvaksytty(Boolean.FALSE);
        
        yhteydetRepository.save(y);
       
        return "redirect:/profiili";
    }
    
    @GetMapping("/profiili/kayttajat/{astringof}")
    public String yksittainenProfiili(Model model, @PathVariable String astringof) {
        Pageable pageable = PageRequest.of(0, 99, Sort.by("tykkays").descending());
        Pageable pageable_taitotop = PageRequest.of(0, 3, Sort.by("tykkays").descending());
        
        Account account = accountRepository.findByAstringof(astringof);
        
        //Kuva haku ei toimi Herokussa, paikallisesti toimii
        //model.addAttribute("kuva", account.getProfiili().getId());
        
        model.addAttribute("profiili", accountRepository.findByUsername(account.getUsername()));
        model.addAttribute("taito_top", taitoRepository.findAll(pageable_taitotop));
        model.addAttribute("taito", taitoRepository.findAll(pageable));
        
        return "profiili_kayttajat";
    }
    
    // Nämä eivät toimi oikein. Kun profiilin id:n kovakoodaa, niin kuva näkyy oikein, mutta profiilin id:n haku ei toimi. Herokuussa ei toimi ollenkaan
    //@GetMapping(path = "/profiili/kayttajat/{astringof}", produces = "image/gif")
    //@ResponseBody
    //public byte[] get(Model model, @PathVariable String astringof) {
        //Account account = accountRepository.findByAstringof(astringof);
        
    //    return profiiliRepository.getOne(10L).getContent();
    //}
}

