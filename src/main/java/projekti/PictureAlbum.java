package projekti;

import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureAlbum extends AbstractPersistable<Long> {

    @OneToOne(mappedBy = "pictureAlbum")
    private UserAccount owner;
    
    @OneToMany(mappedBy = "pictureAlbum")
    private List<Picture> pictures = new ArrayList<>();

}
