package projekti;

import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import projekti.interact.FriendRequest;
import projekti.interact.FriendRequestService;

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
    private FriendRequestService friendRequestService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/index")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
//        showProfilePage(model, username);
        return "redirect:/profile/" + userAccountService.getUserAccountByUserName(username).getProfileCode();
    }

    
//    @RequestMapping(value = "/userAccountNamesAutocomplete")
//    @ResponseBody
//    public List<UserAccount> getBooks() {
//        List<UserAccount> all = userAccountRepository.findAll();
//        List<String> suggestions = new ArrayList<>();
//
////        for (UserAccount u : all) {
////            suggestions.add(u.toString());
////            System.out.println(u.toString());
////        }
//        return all;
//    }

//    @RequestMapping(value = "/userAccountNamesAutocomplete")
////        @PostMapping(path="/books", consumes="application/json", produces="application/json")
//    @ResponseBody
//    public List<String> userAccountNamesAutocomplete(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
//        List<String> suggestions = new ArrayList<>();
//        suggestions.add("pentti");
//        suggestions.add("penakontti");
//        suggestions.add("Antti Miettinen");
//        suggestions.add(("Nenantti Mietti"));
//        return suggestions;
//    }
//	public ModelAndView searchPlants(@RequestParam(value="searchTerm", required=false, defaultValue="") String searchTerm) {
////		log.debug("entering search plants");
//		ModelAndView modelAndView = new ModelAndView();
//		List<PlantDTO> plants = new ArrayList<PlantDTO>(); 
//		try {
//			plants = specimenService.fetchPlants(searchTerm);
//			modelAndView.setViewName("plantResults");
////			if (plants.size() == 0 ) {
////				log.warn("Received 0 results for search string: " + searchTerm);
////			}
//		} catch (Exception e) {
////			log.error("Error happened in searchPlants endpoint", e);
//			e.printStackTrace();
//			modelAndView.setViewName("error");
//		}
//		modelAndView.addObject("plants", plants);
////		log.debug("exiting search Plants");
//		return modelAndView;
//	}
    @GetMapping("/profile/{profileCode}")
    public String showProfilePage(Model model, @PathVariable String profileCode) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = auth.getName();

//        createMockRequests();        
        UserAccount u = userAccountService.getUserAccountByProfileCode(profileCode);

        PictureAlbum pA = pictureAlbumService.getPictureAlbumByOwner(u);
        List<FriendRequest> sentFriendRequests = friendRequestService.getSentFriendRequestsByUserAccount(u);
        List<FriendRequest> recievedFriendRequests = friendRequestService.getRecievedFriendRequestsByUserAccount(u);

        model.addAttribute("logged", userAccountService.getUserAccountByUserName(loggedInUsername));
        model.addAttribute("loggedUserNameUpperCase", userAccountService.getUserAccountByUserName(loggedInUsername).getUserName().toUpperCase());

        model.addAttribute("userAccount", u);
//        model.addAttribute("profileCode", u.getProfileCode()); 
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

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup";
    }

    @GetMapping("/logout")
    public String logOut() {
        return "login";
    }
}
