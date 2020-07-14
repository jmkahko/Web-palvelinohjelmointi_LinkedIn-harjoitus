/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author janne
 */

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    Account findByAstringof(String astringof);
    Account findByFullnameContaining(String fullname);
    Account findByFullname(String fullname);

    List<Account> findByUsernameAndYhteydet(String username, Yhteydet yhteydet);
    List<Account> findByTaidot(Taito taito);
    List<Profiili> findByAstringofAndProfiili(String astringof, Profiili profiili);
    
}
