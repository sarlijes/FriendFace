package projekti;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        UserAccount userAccount = userAccountRepository.getUserAccountByUserName(username);
        if (userAccount == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        System.out.println("++++++++++++++++++++++++++++++++++++");

        return new org.springframework.security.core.userdetails.User(
                userAccount.getUserName(),
                userAccount.getPassWord(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
