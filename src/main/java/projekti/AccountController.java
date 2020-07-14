/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author janne
 */
@Controller
public class AccountController {
    
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @GetMapping("/rekisteroidy")
    public String rekisteroidy() {
        return "rekisteroidy";
    }
    
    @PostMapping("/rekisteroidy")
    public String add(@Valid @RequestParam String username, @RequestParam String password, 
            @RequestParam String fullname, @RequestParam String astringof) {
        
        if (accountRepository.findByUsername(username) != null) {
            return "redirect:/rekisteroidy";
        }
        
        Account a = new Account(username, passwordEncoder.encode(password), fullname, astringof, null, null, null);
        accountRepository.save(a);
        return "redirect:/";
    }
    
}
