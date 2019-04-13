package projekti;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PictureService {

    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private PictureAlbumRepository pictureAlbumRepository;
    @Autowired
    private PictureAlbumService pictureAlbumService;

    public void addPicture(MultipartFile file, String profileCode, String caption, Boolean isProfilePicture) throws IOException {

        String UserName = userAccountRepository.getUserAccountByProfileCode(profileCode).getUserName();
// hae resurssit repoista:
        UserAccount u = userAccountRepository.getUserAccountByUserName(UserName);
        PictureAlbum pictureAlbum = pictureAlbumRepository.getPictureAlbumByOwner(u);
        List<Picture> pictures = pictureAlbum.getPictures();
// luo kuva ja aseta sisältö:
        Picture picture = new Picture();
        picture.setContent(file.getBytes());
        picture.setContentLength(file.getSize());
        picture.setContentType(file.getContentType());
        picture.setName(file.getOriginalFilename());
        picture.setCaption(caption);
        picture.setIsProfilePicture(Boolean.FALSE);
        picture.setPictureAlbum(u.getPictureAlbum());
//lisää kuva listalle:
        pictures.add(picture);
// Tallenna kaikki, myös repoihin:
        pictureAlbum.setPictures(pictures);
        u.setPictureAlbum(pictureAlbum);
        pictureAlbumRepository.save(pictureAlbum);
        userAccountRepository.save(u);
        pictureRepository.save(picture);
    }

    public void makeProfilePicture(Long id) {
        ////////////////KORVAA///
        /////////////////////////
        UserAccount owner = userAccountRepository.getUserAccountByUserName("jessi");
        PictureAlbum pictureAlbum = pictureAlbumRepository.getPictureAlbumByOwner(owner);
        List<Picture> pictures = pictureAlbum.getPictures();
        System.out.println(owner.getProfileCode());
        System.out.println(id);
        for (Picture pic : pictures) {
            if (pic.getId().equals(id)) {
                pic.setIsProfilePicture(Boolean.TRUE);
            } else {
                pic.setIsProfilePicture(Boolean.FALSE);
            }
        }
        pictureAlbum.setPictures(pictures);
        owner.setPictureAlbum(pictureAlbum);
        pictureAlbumRepository.save(pictureAlbum);
        userAccountRepository.save(owner);
    }
}
