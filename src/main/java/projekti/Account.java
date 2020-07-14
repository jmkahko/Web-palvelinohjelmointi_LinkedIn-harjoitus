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
import javax.persistence.ManyToOne;
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
public class Account extends AbstractPersistable<Long> {

    private String username;
    private String password;
    private String fullname;
    private String astringof;
    @ManyToOne
    private Profiili profiili;
    @ManyToMany
    private List<Taito> taidot = new ArrayList<>();
    @ManyToMany
    private List<Yhteydet> yhteydet = new ArrayList<>();

}
