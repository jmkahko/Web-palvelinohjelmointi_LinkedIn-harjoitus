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
public class Taito extends AbstractPersistable<Long> {
    
    private String taito;
    private Long tykkays;
    
    @ManyToMany(mappedBy = "taidot")
    private List<Account> Accounts = new ArrayList<>();
    
}
