package projekti;

import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.interact.FriendRequest;
import projekti.interact.FriendRequestService;

@Controller

public class UserAccountController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private PictureAlbumService pictureAlbumService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private FriendRequestService friendRequestService;

    @GetMapping("/profile/{profileCode}")
    public String showProfilePage(Model model, @PathVariable String profileCode) {

// "Kun käyttäjä on kirjautuneena, saa häneen liittyvän käyttäjätunnuksen ns. tietoturvakontekstista."
// TÄMÄ EI TOIMI, antaa null point exception
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//        System.out.println("UserAccountController " + username);
//        createMockRequests();        
        UserAccount u = userAccountService.getUserAccountByProfileCode(profileCode);
        PictureAlbum pA = pictureAlbumService.getPictureAlbumByOwner(u);
        List<FriendRequest> sentFriendRequests = friendRequestService.getSentFriendRequestsByUserAccount(u);
        List<FriendRequest> recievedFriendRequests = friendRequestService.getRecievedFriendRequestsByUserAccount(u);

        model.addAttribute("userAccount", u);
        model.addAttribute("profileCode", profileCode);
        model.addAttribute("pictures", pictureAlbumService.getPictureAlbumByOwner(u).getPictures());
        model.addAttribute("recievedmessages", messageService.findMax25messages(u.getId()));
        model.addAttribute("sentFriendRequests", sentFriendRequests);
        model.addAttribute("recievedFriendRequests", recievedFriendRequests);

        for (Picture pic : pictureAlbumService.getPictureAlbumByOwner(u).getPictures()) {
            Long profilePicId;
            if (pic.getIsProfilePicture() == true) {
                profilePicId = pic.getId();
                model.addAttribute("profilePicId", profilePicId);
            }
        }
        return "profile";
    }

    public void createMockRequests() {
        LocalDateTime dateTime = now();
        friendRequestService.addFriendRequest(userAccountService.getUserAccountById(Long.valueOf(1)), userAccountService.getUserAccountById(Long.valueOf(7)), dateTime);
        friendRequestService.addFriendRequest(userAccountService.getUserAccountById(Long.valueOf(1)), userAccountService.getUserAccountById(Long.valueOf(10)), dateTime);
    }

    @PostMapping("/signup")
    public String create(@RequestParam String userName, @RequestParam String passWord, @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String profileCode) {
        if (userAccountService.getUserAccountByUserName(userName) != null) {
            return "redirect:/signup";
        }
        userAccountService.addUserAccount(userName, passwordEncoder.encode(passWord), firstName, lastName, profileCode);
        pictureAlbumService.addPictureAlbum(userAccountService.getIdByProfileCode(profileCode));
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userName, @RequestParam String passWord) {
        // jos jompikumpi on tyhjä tai käyttäjää ei löydy, uudelleenohjaa:
        if (userName.isEmpty() || passWord.isEmpty() || userAccountService.getUserAccountByUserName(userName) == null) {
            return "redirect:/login";
        }
        // Jos salasana täsmää tietokannassa olevaan:
        UserAccount u = new UserAccount();
        if (userAccountService.getUserAccountByUserName(userName).getPassWord().equals(passWord)) {
            u = userAccountService.getUserAccountByUserName(userName);
        }
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
        return "redirect:/profile/" + userAccountService.getUserAccountByUserName(userName).getProfileCode();
    }

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logOut() {
        return "login";
    }
}
