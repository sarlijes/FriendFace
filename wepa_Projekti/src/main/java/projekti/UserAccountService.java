package projekti;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public void addUserAccount(UserAccount userAccount) {
        userAccountRepository.save(userAccount);
        userAccount.setPictureAlbum(new PictureAlbum());
    }

    public Long getIdByProfileCode(String profileCode) {
        return userAccountRepository.getUserAccountByProfileCode(profileCode).getId();
    }

    public UserAccount getUserAccountById(Long id) {
        return userAccountRepository.getUserAccountById(id);
    }

    public UserAccount getUserAccountByUserName(String userName) {
        return userAccountRepository.getUserAccountByUserName(userName);
    }

    public UserAccount getUserAccountByProfileCode(String profileCode) {
        return userAccountRepository.getUserAccountByProfileCode(profileCode);
    }

    public List<UserAccount> getAllUserAccounts() {
        return userAccountRepository.findAll();
    }
}
