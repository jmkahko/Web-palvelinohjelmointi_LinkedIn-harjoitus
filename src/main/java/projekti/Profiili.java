/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author janne
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profiili extends AbstractPersistable<Long>  {
    
    @Lob
    @Basic(fetch = FetchType.LAZY)@Type(type = "org.hibernate.type.BinaryType")
    private byte[] content;
    @OneToMany(mappedBy = "profiili")
    private List<Account> accounts = new ArrayList<>();
}
