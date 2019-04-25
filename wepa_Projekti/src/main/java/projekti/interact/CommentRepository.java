package projekti.interact;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.UserAccount;

public interface CommentRepository extends JpaRepository<Comment, Long> {

//    Page<Message> findAllByRecieverId(Pageable pageable, Long id);
//    PictureAlbum getPictureAlbumByOwner(UserAccount owner);
    List<Comment> getCommentsByInteractableId(Long id);
}
