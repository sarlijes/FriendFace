package projekti.interact;

import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.MessageService;
import projekti.UserAccount;
import projekti.UserAccountService;
import projekti.Interactable;
import projekti.Message;
import projekti.MessageRepository;
import projekti.PictureService;
import projekti.Picture;
import projekti.PictureRepository;

@Controller
public class ThumbUpController {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private ThumbUpService thumbUpService;

    @PostMapping("/profile/{profileCode}/picture/{id}/addThumbUp")
    public String addPictureThumbUp(@PathVariable String profileCode, @PathVariable Long id) {
        Picture picture = pictureService.getOne(id);
        int thumbUpCount = picture.getPictureThumbUpCount();
        if (thumbUpCount == 0) {
            thumbUpCount++;
            picture.setPictureThumbUpCount(thumbUpCount);
            pictureRepository.save(picture);
        } else {
            thumbUpCount--;
            picture.setPictureThumbUpCount(thumbUpCount);
            pictureRepository.save(picture);
        }
        return "redirect:/profile/" + profileCode;
    }

    @PostMapping("/profile/{profileCode}/message/{id}/addThumbUp")
    public String addMessageThumbUp(@PathVariable String profileCode, @PathVariable Long id) {
        Message message = messageRepository.getOne(id);
        int thumbUpCount = message.getMessageThumbUpCount();
        if (thumbUpCount == 0) {
            thumbUpCount++;
            message.setMessageThumbUpCount(thumbUpCount);
            messageRepository.save(message);
        } else {
            thumbUpCount--;
            message.setMessageThumbUpCount(thumbUpCount);
            messageRepository.save(message);
        }
        return "redirect:/profile/" + profileCode;
    }

}
