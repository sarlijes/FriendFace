package projekti.interact;

import java.io.IOException;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.PictureAlbumService;
import projekti.UserAccount;
import projekti.UserAccountService;

@Controller
public class FriendRequestController {

    @Autowired
    private FriendRequestService friendRequestService;
    @Autowired
    private UserAccountService userAccountService;

    public String createPictureAlbum(Long sourceUserAccountId, Long targetUserAccountId) {

        UserAccount sourceUser = userAccountService.getUserAccountById(sourceUserAccountId);
        UserAccount targetUser = userAccountService.getUserAccountById(targetUserAccountId);
        LocalDateTime dateTime = now();
        friendRequestService.addFriendRequest(sourceUser, targetUser, dateTime);
        return "redirect:/";
    }

    @PostMapping("/friendrequest/reject/{id}")
    public String reject(@PathVariable Long id) {
        friendRequestService.rejectFriendRequest(friendRequestService.getFriendRequestById(id));
        return "redirect:/profile/" + "jessi545";
    }

    @PostMapping("/friendrequest/approve/{id}")
    public String approve(@PathVariable Long id) {
        friendRequestService.acceptFriendRequest(friendRequestService.getFriendRequestById(id));
        return "redirect:/profile/" + "jessi545";
    }

}
