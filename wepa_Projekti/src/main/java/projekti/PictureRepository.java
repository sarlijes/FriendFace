package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    Picture getPictureById(Long id);

//    @Query(
//            value = "SELECT * from INTERACTABLE WHERE DTYPE = 'Picture' AND PICTURE_ALBUM_ID = ID",
//            nativeQuery = true)
//    List<Picture> findPicturesFromInteractablesByPictureAlbumID(@Param("ID") Long ID);

}
