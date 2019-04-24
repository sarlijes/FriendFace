package projekti;

import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.interact.FriendRequest;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount extends AbstractPersistable<Long> {

//    @NotEmpty
//        @Size(min = 2, max = 50)
    private String userName;
    private String passWord;
    private String firstName;
    private String lastName;
    private String profileCode;
    @OneToOne(cascade = CascadeType.ALL)
    private PictureAlbum pictureAlbum;

    @OneToMany(mappedBy = "sourceUserAccount")
    private List<FriendRequest> sentFriendRequests = new ArrayList<FriendRequest>();
    @OneToMany(mappedBy = "targetUserAccount")
    private List<FriendRequest> recievedFriendRequests = new ArrayList<FriendRequest>();
}
