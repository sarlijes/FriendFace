package projekti;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

//    @Transactional
    public void addUserAccount(String userName, String passWord, String firstName, String lastName, String profileCode) {
        userAccountRepository.save(new UserAccount(userName, passWord, firstName, lastName, profileCode, null));
        userAccountRepository.getUserAccountByUserName(userName).setPictureAlbum(new PictureAlbum());
    }

    public Long getIdByProfileCode(String profileCode) {
        return userAccountRepository.getUserAccountByProfileCode(profileCode).getId();
    }

    public UserAccount getUserAccountByUserName(String userName) {
        return userAccountRepository.getUserAccountByUserName(userName);
    }

    public UserAccount getUserAccountByProfileCode(String profileCode) {
        return userAccountRepository.getUserAccountByProfileCode(profileCode);
    }
}
