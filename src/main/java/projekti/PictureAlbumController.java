package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PictureAlbumController {

    @Autowired
    private PictureAlbumService pictureAlbumService;

    public String createPictureAlbum(Long id) {
        pictureAlbumService.addPictureAlbum(id);
        return "redirect:/";
    }
}
