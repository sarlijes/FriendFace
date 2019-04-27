package projekti.interact;

import java.io.IOException;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.PictureAlbumService;
import projekti.UserAccount;
import projekti.UserAccountRepository;
import projekti.UserAccountService;

@Controller
public class FriendRequestController {

    @Autowired
    private FriendRequestService friendRequestService;
    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @PostMapping("/friendrequest/reject/{id}")
    public String reject(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = auth.getName();
        String authProfileCode = userAccountService.getUserAccountByUserName(loggedInUsername).getProfileCode();
        friendRequestService.rejectFriendRequest(friendRequestService.getFriendRequestById(id));
        return "redirect:/profile/" + authProfileCode;
    }

    @PostMapping("/friendrequest/approve/{id}")
    public String approve(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = auth.getName();
        String authProfileCode = userAccountService.getUserAccountByUserName(loggedInUsername).getProfileCode();
        friendRequestService.acceptFriendRequest(friendRequestService.getFriendRequestById(id));
        return "redirect:/profile/" + authProfileCode;
    }

    @Transactional
    @PostMapping("/profile/{profileCode}/sendFriendRequest")
    public String sendFriendRequest(@PathVariable String profileCode, @RequestParam Long chosenId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = auth.getName();
        UserAccount sourceUserAccount = userAccountService.getUserAccountByUserName(loggedInUsername);
        UserAccount targetUserAccout = userAccountService.getUserAccountById(chosenId);
        FriendRequest f = new FriendRequest(sourceUserAccount, targetUserAccout, now(), false, true);
        friendRequestService.addFriendRequest(f);
        return "redirect:/profile/" + profileCode;
    }
}

// https://stackoverflow.com/questions/49051830/how-to-extract-the-selected-value-from-a-datalist-with-thymeleaf-and-spring
