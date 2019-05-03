package projekti.interact;

import projekti.*;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class FriendRequest extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn(name = "sourceUserAccount_id")
    private UserAccount sourceUserAccount;

    @ManyToOne
    @JoinColumn(name = "targetUserAccount_id")
    private UserAccount targetUserAccount;

    private LocalDateTime friendRequestTimeStamp;
    private Boolean accepted;
    private Boolean pending;

}
