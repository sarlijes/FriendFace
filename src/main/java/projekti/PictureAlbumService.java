package projekti;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PictureAlbumService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PictureAlbumRepository pictureAlbumRepository;

    @Transactional
    public void addPictureAlbum(Long id) {
        pictureAlbumRepository.save(new PictureAlbum(userAccountRepository.getOne(id), new ArrayList<>()));
    }

    public PictureAlbum getPictureAlbumByOwner(UserAccount owner) {
        return pictureAlbumRepository.getPictureAlbumByOwner(owner);
    }

}
