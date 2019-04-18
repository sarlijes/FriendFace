package projekti;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Picture extends Interactable {

    private Long contentLength;
    private String name;
    private String contentType;
    private String caption;
    private Boolean isProfilePicture;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    @ManyToOne(cascade = CascadeType.ALL)
    private PictureAlbum pictureAlbum;

}
