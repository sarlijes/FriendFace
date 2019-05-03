package projekti.interact;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThumbUpRepository extends JpaRepository<ThumbUp, Long> {

    List<ThumbUp> getThumbUpsByInteractableId(Long id);

}
