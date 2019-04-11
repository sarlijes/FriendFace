package projekti;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @Autowired
    private PictureRepository pictureRepository;

    @PostMapping("/profile/{profileCode}")
    public String add(@RequestParam("file") MultipartFile file, @PathVariable String profileCode, @RequestParam String caption) throws IOException {
//        if (!file.getContentType().equals("image/jpg")) {
//            return "redirect:/profile/" + profileCode;
//        }
        if (pictureRepository.count() <= 9) {
            pictureService.addPicture(file, profileCode, caption, false);
        }
        return "redirect:/profile/" + profileCode;
    }

    @DeleteMapping("/picture/{id}")
    public String delete(@PathVariable Long id) throws IOException {
        pictureRepository.deleteById(id);
//        pictureService.addPicture(file, profileCode);
        return "redirect:/profile/";
    }

    @PostMapping("/picture/{id}")
    public String makeProfilePicture(@PathVariable Long id) throws IOException {
        pictureService.makeProfilePicture(id);
        return "redirect:/profile/";
    }

//    @PostMapping("/profile/{profileCode}")
//    public String add(@RequestParam("file") MultipartFile file, @PathVariable String profileCode) throws IOException {
////        if (!file.getContentType().equals("image/jpg")) {
////            return "redirect:/profile/" + profileCode;
////        }
//        pictureService.addPicture(file, profileCode);
//        return "redirect:/profile/" + profileCode;
//    }
    @GetMapping("/picture/{id}")
    public ResponseEntity<byte[]> viewFile(@PathVariable Long id) {
        Picture pic = pictureRepository.getOne(id);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(pic.getContentType()));
        headers.setContentLength(pic.getContentLength());

        return new ResponseEntity<>(pic.getContent(), headers, HttpStatus.CREATED);
    }
}
