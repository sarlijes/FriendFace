package projekti;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Message extends Interactable {

    @ManyToOne
    private UserAccount sender;
    @ManyToOne
    private UserAccount reciever;
    private LocalDateTime messageTimeStamp;
    private String content;

//     private List <ThumbUp> messageThumbUps  = new ArrayList<>();
//     private List <Comments> messageComments  = new ArrayList<>();

}
