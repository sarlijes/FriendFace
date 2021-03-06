package projekti;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import projekti.*;
import projekti.interact.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class FluentleniumTest extends org.fluentlenium.adapter.junit.FluentTest {

    @LocalServerPort
    private Integer port;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserAccountService userAccountService;

    @Test
    public void canCreateUserAndLogIn() {
        int userAccountRepositorySize = userAccountRepository.findAll().size();
        goTo("http://localhost:" + port + "/signup");
        find("#username").fill().with("user123");
        find("#password").fill().with("1111111111");
        find("#confirmPassWord").fill().with("1111111111");
        find("#firstName").fill().with("Kaaleppi");
        find("#lastName").fill().with("1111111111");
        find("#profileCode").fill().with("1111111111");
        find("form").first().submit();
        assertTrue(userAccountService.getAllUserAccounts().size() == userAccountRepositorySize + 1);

        assertTrue(pageSource().contains("Please log in"));
        assertTrue(pageSource().contains("usernameinput"));
        assertTrue(pageSource().contains("passwordinput"));
        find("#usernameinput").fill().with("user123");
        find("#passwordinput").fill().with("1111111111");
        find("form").first().submit();
        assertTrue(pageSource().contains("Kaaleppi"));
    }

    @Test
    public void canCreateUser() {
        int userAccountRepositorySize = userAccountRepository.findAll().size();
        goTo("http://localhost:" + port + "/signup");
        find("#username").fill().with("12345");
        find("#password").fill().with("12345");
        find("#confirmPassWord").fill().with("12345");
        find("#firstName").fill().with("12345");
        find("#lastName").fill().with("12345");
        find("#profileCode").fill().with("12345");
        find("form").first().submit();
        assertTrue(userAccountService.getAllUserAccounts().size() == userAccountRepositorySize + 1);
        assertTrue(pageSource().contains("Please log in"));
    }
}
