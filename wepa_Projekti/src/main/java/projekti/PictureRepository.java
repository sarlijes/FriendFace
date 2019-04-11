package projekti;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    Picture getPictureById(Long id);
}
