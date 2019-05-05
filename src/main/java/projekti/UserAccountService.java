package projekti;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public void addUserAccount(UserAccount userAccount) {
        userAccountRepository.save(userAccount);
        userAccount.setPictureAlbum(new PictureAlbum());
    }

    @Cacheable("useraccounts")
    public Long getIdByProfileCode(String profileCode) {
        return userAccountRepository.getUserAccountByProfileCode(profileCode).getId();
    }

    @Cacheable("useraccounts")
    public UserAccount getUserAccountById(Long id) {
        return userAccountRepository.getUserAccountById(id);
    }

    @Cacheable("useraccounts")
    public UserAccount getUserAccountByUserName(String userName) {
        return userAccountRepository.getUserAccountByUserName(userName);
    }

    @Cacheable("useraccounts")
    public UserAccount getUserAccountByProfileCode(String profileCode) {
        return userAccountRepository.getUserAccountByProfileCode(profileCode);
    }

    @Cacheable("useraccounts")
    public List<UserAccount> getAllUserAccounts() {
        return userAccountRepository.findAll();
    }

    public boolean userNameisValid(String userName) {
        return getUserAccountByUserName(userName) == null;
    }

    public boolean profileCodeisValid(String profileCode) {
        return getUserAccountByProfileCode(profileCode) == null;
    }
}
