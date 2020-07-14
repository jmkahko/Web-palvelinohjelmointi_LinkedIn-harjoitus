/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author janne
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Yhteydet extends AbstractPersistable<Long> {
    
    private Long yhteys;
    private Boolean pyynto = false;
    private Boolean hyvaksytty = false;
    
    @ManyToMany(mappedBy = "yhteydet")
    private List<Account> Accounts = new ArrayList<>();
        
    
}
