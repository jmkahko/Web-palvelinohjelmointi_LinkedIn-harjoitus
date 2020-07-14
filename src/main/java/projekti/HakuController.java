/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
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
public class HakuController {
    
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    YhteydetRepository yhteydetRepository;   
    
    @GetMapping("/haku")
    public String haku(Model model) {

        model.addAttribute("account", accountRepository.findAll());
        return "haku";
    }
    
    @PostMapping("/haku")
    public String haku(Model model, @RequestParam String haku) {
    
        model.addAttribute("account", accountRepository.findByFullnameContaining(haku));

        return "redirect:/haku";
    }
    
    @PostMapping("/profiili/{yhteysId}/yhteyslisaa")
    public String hyvaksyYhteys(@PathVariable Long yhteysId) {
        Yhteydet y = new Yhteydet();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findByUsername(username);
        
        y.setYhteys(yhteysId);
        y.setPyynto(Boolean.TRUE);
        
        account.getYhteydet().add(y);
        
        yhteydetRepository.save(y);
       
        return "redirect:/haku";
    }
    
}
