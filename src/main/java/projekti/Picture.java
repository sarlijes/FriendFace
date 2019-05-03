package projekti;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import projekti.interact.Comment;
import projekti.interact.ThumbUp;

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

//  Local annotations:
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty
//    Heroku-proof annotation:
//    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] content;
    @ManyToOne(cascade = CascadeType.ALL)
    private PictureAlbum pictureAlbum;
    @OneToMany
    private List<Comment> pictureComments = new ArrayList<>();
    @OneToMany
    private List<ThumbUp> pictureThumbUps = new ArrayList<>();
    private int pictureThumbUpCount;
}
