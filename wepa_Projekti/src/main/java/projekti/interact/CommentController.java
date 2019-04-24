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

@Controller
public class CommentController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/profile/{profileCode}/comment/{interactableId}")
    public String addComment(@PathVariable String profileCode, @PathVariable String interactableId, @RequestParam String content) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = auth.getName();

        LocalDateTime dateTime = now();
//        UserAccount writer = userAccountService.getUserAccountByProfileCode(profileCode);
        UserAccount writer = userAccountService.getUserAccountByUserName(loggedInUsername);
        Interactable interactable = new Interactable();
        Comment comment = new Comment(writer, null, dateTime, content);
        commentService.addComment(comment);
        return "redirect:/profile/" + profileCode;
    }
}
