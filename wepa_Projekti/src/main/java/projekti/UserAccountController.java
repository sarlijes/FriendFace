package projekti;

import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.util.*;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import projekti.interact.Comment;
import projekti.interact.FriendRequest;
import projekti.interact.FriendRequestService;
import projekti.interact.CommentService;

@Controller

public class UserAccountController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private PictureAlbumService pictureAlbumService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private FriendRequestService friendRequestService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/homepage")
    public String homepage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return "redirect:/profile/" + userAccountService.getUserAccountByUserName(username).getProfileCode();
    }

    @GetMapping("/profile/{profileCode}")
    public String showProfilePage(Model model, @PathVariable String profileCode) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = auth.getName();

//        createMockRequests();        
        UserAccount u = userAccountService.getUserAccountByProfileCode(profileCode);
        PictureAlbum pA = pictureAlbumService.getPictureAlbumByOwner(u);
//        Long pAiD = pA.getId();
        List<FriendRequest> sentFriendRequests = friendRequestService.getSentFriendRequestsByUserAccount(u);
        List<FriendRequest> recievedFriendRequests = friendRequestService.getRecievedFriendRequestsByUserAccount(u);
        List<UserAccount> allUserAccounts = userAccountService.getAllUserAccounts();

        model.addAttribute("logged", userAccountService.getUserAccountByUserName(loggedInUsername));
        model.addAttribute("loggedUserNameUpperCase", userAccountService.getUserAccountByUserName(loggedInUsername).getUserName().toUpperCase());
        model.addAttribute("userAccount", u);
        model.addAttribute("allUserAccounts", allUserAccounts);

        Page<Message> recievedmessages = messageService.findMax25messages(u.getId());

        List<Picture> pictures = pictureAlbumService.getPictureAlbumByOwner(u).getPictures();

        for (Message m : recievedmessages) {
            m.setMessageComments(commentService.getCommentsByInteractableId(m.getId()));
        }
//        List<Comment> max10Comments = new ArrayList<>();
        for (Picture p : pictures) {
//            p.setPictureComments(commentService.getCommentsByInteractableId(p.getId()));
//            Page<Comment> max10pictureComments = commentService.getMax10CommentsByInteractableId(p.getId());
//            System.out.println(max10pictureComments.getTotalElements() + " max10pictureComments total elements");
//            max10pictureComments.stream()
//                    .map(comment -> ploi.add(comment));
//            p.setPictureComments(max10Comments);
            p.setPictureComments(commentService.getCommentsByInteractableId(p.getId()));
        }
//        System.out.println(max10Comments.size());
//        System.out.println("+++++++++++++++++++++++++++");

        model.addAttribute("pictures", pictures);
        model.addAttribute("recievedmessages", recievedmessages);
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

    @PostMapping("/signup")
    public String create(@Valid @ModelAttribute UserAccount userAccount, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        userAccount.setPassWord(passwordEncoder.encode(userAccount.getPassWord()));
        userAccountService.addUserAccount(userAccount);
        pictureAlbumService.addPictureAlbum(userAccountService.getIdByProfileCode(userAccount.getProfileCode()));
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String showSignUpPage(@ModelAttribute UserAccount userAccount) {
        return "signup";
    }

    @PostMapping("/logout")
    public String logOut() {
        return "redirect:/login";
    }
    

    
//    login?logout

    public boolean userNameisValid(String userName) {

        if (userAccountService.getUserAccountByUserName(userName) != null) {
            return false;
        }
        return true;
    }

    public boolean profileCodeisValid(String profileCode) {
        if (userAccountService.getUserAccountByProfileCode(profileCode) != null) {
            return false;
        }
        return true;
    }

}
/*
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
 */
