package projekti;

import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class UserAccountController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private PictureAlbumService pictureAlbumService;

    @Autowired
    private PictureService pictureService;

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

    @GetMapping("/profile/{profileCode}")
    public String showProfilePage(Model model, @PathVariable String profileCode) {

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//        System.out.println("UserAccountController " + username);
        UserAccount u = userAccountService.getUserAccountByProfileCode(profileCode);
        model.addAttribute("userAccount", userAccountService.getUserAccountByProfileCode(profileCode));
        model.addAttribute("profileCode", profileCode);
        model.addAttribute("pictures", pictureAlbumService.getPictureAlbumByOwner(u).getPictures());

        for (Picture pic : pictureAlbumService.getPictureAlbumByOwner(u).getPictures()) {
            Long profilePicId;
            if (pic.getIsProfilePicture() == true) {
                profilePicId = pic.getId();
                model.addAttribute("profilePicId", profilePicId);
            }
        }

        return "profile";
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

// "Kun käyttäjä on kirjautuneena, saa häneen liittyvän käyttäjätunnuksen ns. tietoturvakontekstista."
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
        return "redirect:/profile/" + userAccountService.getUserAccountByUserName(userName).getProfileCode();
    }
}
