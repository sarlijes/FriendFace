package projekti;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @Autowired
    private PictureAlbumService pictureAlbumService;

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/profile/{profileCode}")
    public String add(@RequestParam("file") MultipartFile file, @PathVariable String profileCode) throws IOException {
//        if (!file.getContentType().equals("image/jpg")) {
//            return "redirect:/profile/" + profileCode;
//        }
        pictureService.addPicture(file, profileCode);
        return "redirect:/profile/" + profileCode;
    }
}
