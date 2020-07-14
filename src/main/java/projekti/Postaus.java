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
public class Postaus extends AbstractPersistable<Long> {
    
    private String postaaja;
    private LocalDateTime messageDate = LocalDateTime.now();
    private String message;
    private Long tykkays;

    @ManyToMany
    private List<Kommentointi> kommentointi = new ArrayList<>();

    
}

