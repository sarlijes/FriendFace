package projekti;

import static java.time.LocalDateTime.now;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import projekti.interact.Comment;
import projekti.interact.CommentRepository;
import projekti.interact.ThumbUp;
import projekti.interact.ThumbUpRepository;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjektiTest {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ThumbUpRepository thumbUpRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void addingOneUserAccountIncreacesDatabaseSize() {
        int userAccountRepositorySize = userAccountRepository.findAll().size();
        UserAccount userAccount = new UserAccount("lam12", "pw1245", "pw1245", "first", "last", "lam85", null, null, null);
        userAccountRepository.save(userAccount);
        assertTrue(userAccountService.getAllUserAccounts().size() == userAccountRepositorySize + 1);
    }

    @Test
    public void canAddUserAndIdMatches() {
        UserAccount userAccount = new UserAccount("heavyman", "pw1245", "pw1245", "first", "last", "heavymancode5655", null, null, null);
        userAccountRepository.save(userAccount);
        Long userAccountId = userAccount.getId();
        Long fetchedId = userAccountService.getUserAccountByUserName("heavyman").getId();
        assertTrue(userAccountId.equals(fetchedId));
    }

    @Test
    public void addingOneMessageIncreacesDatabaseSize() {
        int messageRepositorySize = messageRepository.findAll().size();
        Message message = new Message(null, null, now(), "content 1454", null, null, 0);
        messageRepository.save(message);
        assertTrue(messageRepository.findAll().size() == messageRepositorySize + 1);
    }

    @Test
    public void canAddMessageAndIdMatches() {
        Message message = new Message(null, null, now(), "content 1454", null, null, 0);
        messageRepository.save(message);
        Long id = message.getId();
        Long fetchedId = messageRepository.getOne(id).getId();
        assertTrue(id.equals(fetchedId));
    }

    @Test
    public void addingOneThumbUpIncreacesDatabaseSize() {
        int thumbUpRepositorySize = thumbUpRepository.findAll().size();
        ThumbUp thumbUp = new ThumbUp(null, null);
        thumbUpRepository.save(thumbUp);
        assertTrue(thumbUpRepository.findAll().size() == thumbUpRepositorySize + 1);
    }

    @Test
    public void canAddThumbUpAndIdMatches() {
        ThumbUp thumbUp = new ThumbUp(null, null);
        thumbUpRepository.save(thumbUp);
        Long id = thumbUp.getId();
        Long fetchedId = thumbUpRepository.getOne(id).getId();
        assertTrue(id.equals(fetchedId));
    }

    @Test
    public void canAddCommentAndIdMatches() {
        Comment comment = new Comment(null, null, now(), "comment 556555");
        commentRepository.save(comment);
        Long id = comment.getId();
        Long fetchedId = commentRepository.getOne(id).getId();
        assertTrue(id.equals(fetchedId));
    }

    @Test
    public void canAddAllRelevantEntities() {
        UserAccount stargazer = new UserAccount("stargazer", "pw1245", "pw1245", "first", "last", "stargazer86", null, null, null);
        UserAccount friend = new UserAccount("franklampard", "pw1245", "pw1245", "first", "last", "frankie", null, null, null);

        userAccountRepository.save(stargazer);
        userAccountRepository.save(friend);

        Message message = new Message(stargazer, friend, now(), "stars r nice", null, null, 0);
        messageRepository.save(message);

        Comment comment = new Comment(stargazer, message, now(), "comment 556555");
        commentRepository.save(comment);
        ThumbUp thumbUp = new ThumbUp(stargazer, message);
        thumbUpRepository.save(thumbUp);

        Long userAccountId = stargazer.getId();
        Long messageId = message.getId();
        Long commentId = comment.getId();
        Long thumbUpId = thumbUp.getId();

        System.out.println(messageId);
        System.out.println(commentId);
        System.out.println(thumbUpId);

        assertFalse(userAccountRepository.getOne(message.getId()).getId() == null);
        assertFalse(messageRepository.getOne(messageId).getId() == null);
        assertFalse(commentRepository.getOne(commentId).getId() == null);
        assertFalse(thumbUpRepository.getOne(thumbUpId).getId() == null);
    }
}
