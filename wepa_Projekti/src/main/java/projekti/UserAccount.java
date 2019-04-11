package projekti;

import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount extends AbstractPersistable<Long> {

    private String userName;
    private String passWord;
    private String firstName;
    private String lastName;
    private String profileCode;
//    @OneToMany(mappedBy = "reciever")
//    private List<WallMessage> recievedWallMessages = new ArrayList<>();
//    @OneToMany(mappedBy = "sender")
//    private List<WallMessage> sentWallMessages = new ArrayList<>();
//    @OneToOne(mappedBy = "owner")

//    @OneToOne(mappedBy = "pictureAlbum")
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "picturealbum_id", referencedColumnName = "id")
    
//    @OneToOne(mappedBy = "owner")    
    
    @OneToOne(cascade = CascadeType.ALL)
    private PictureAlbum pictureAlbum;
}
