/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.time.LocalDateTime;
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
public class Kommentointi extends AbstractPersistable<Long> {

    private String kommentti;
    private String kommentoija;
    private LocalDateTime messageDate = LocalDateTime.now();
    
    @ManyToMany(mappedBy = "kommentointi")
    private List<Postaus> postaukset = new ArrayList<>();
    
    
}
