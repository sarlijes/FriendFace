package projekti.interact;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.Interactable;
import projekti.UserAccount;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ThumbUp extends AbstractPersistable<Long>{

    @ManyToOne
    private UserAccount giver;
    @ManyToOne
    private Interactable interactable;

}
