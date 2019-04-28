package projekti;

import java.io.IOException;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import projekti.UserAccount;
import projekti.UserAccountRepository;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import projekti.interact.Comment;
import projekti.interact.CommentController;
import projekti.interact.CommentRepository;
import projekti.interact.CommentService;
import projekti.interact.ThumbUp;
import projekti.interact.ThumbUpController;
import projekti.interact.ThumbUpRepository;
import projekti.interact.ThumbUpService;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjektiTest {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserAccountController userAccountController;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private MessageController messageController;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ThumbUpController thumbUpController;
    @Autowired
    private ThumbUpRepository thumbUpRepository;
    @Autowired
    private ThumbUpService thumbUpService;
    @Autowired
    private CommentController commentController;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;

    @Test
    public void addingOneUserAccountIncreacesDatabaseSize() {
        int userAccountRepositorySize = userAccountRepository.findAll().size();
        UserAccount u = new UserAccount("lam12", "pw1245", "pw1245", "first", "last", "lam85", null, null, null);
        userAccountRepository.save(u);
        assertTrue(userAccountService.getAllUserAccounts().size() == userAccountRepositorySize + 1);
//        assertTrue(userAccountRepository.findAll().contains(u));
    }

    @Test
    public void canAddUserAndIdMatches() {
        UserAccount u = new UserAccount("heavyman", "pw1245", "pw1245", "first", "last", "heavymancode5655", null, null, null);
        userAccountRepository.save(u);
        Long uId = u.getId();
        Long fetchedId = userAccountService.getUserAccountByUserName("heavyman").getId();
        assertTrue(uId.equals(fetchedId));
    }

    @Test
    public void addingOneMessageIncreacesDatabaseSize() {
        int messageRepositorySize = messageRepository.findAll().size();
        Message m = new Message(null, null, now(), "content 1454", null, null);
        messageRepository.save(m);
        assertTrue(messageRepository.findAll().size() == messageRepositorySize + 1);
    }

    @Test
    public void canAddMessageAndIdMatches() {
        Message m = new Message(null, null, now(), "content 1454", null, null);
        messageRepository.save(m);
        Long id = m.getId();
        Long fetchedId = messageRepository.getOne(id).getId();
        assertTrue(id.equals(fetchedId));
    }

    @Test
    public void addingOneThumbUpIncreacesDatabaseSize() {
        int thumbUpRepositorySize = thumbUpRepository.findAll().size();
        ThumbUp t = new ThumbUp(null, null);
        thumbUpRepository.save(t);
        assertTrue(thumbUpRepository.findAll().size() == thumbUpRepositorySize + 1);
    }

    @Test
    public void canAddThumbUpAndIdMatches() {
        ThumbUp t = new ThumbUp(null, null);
        thumbUpRepository.save(t);
        Long id = t.getId();
        Long fetchedId = thumbUpRepository.getOne(id).getId();
        assertTrue(id.equals(fetchedId));
    }

    @Test
    public void addingCommentIncreacesDatabaseSize() {
        int commentRepositorySize = commentRepository.findAll().size();
        Message m = new Message(null, null, now(), "want some ice cream", null, null);
        messageRepository.save(m);
        Comment c = new Comment(null, m, now(), "comment 114");
        commentRepository.save(c);
        assertTrue(commentRepository.findAll().size() == commentRepositorySize + 1);
    }

    @Test
    public void canAddCommentAndIdMatches() {
        Comment c = new Comment(null, null, now(), "comment 556555");
        commentRepository.save(c);
        Long id = c.getId();
        Long fetchedId = commentRepository.getOne(id).getId();
        assertTrue(id.equals(fetchedId));
    }

    @Test
    public void canAddAllRelevantEntities() {
        UserAccount stargazer = new UserAccount("stargazer", "pw1245", "pw1245", "first", "last", "stargazer86", null, null, null);
        UserAccount friend = new UserAccount("franklampard", "pw1245", "pw1245", "first", "last", "frankie", null, null, null);

        userAccountRepository.save(stargazer);
        userAccountRepository.save(friend);

        Message m = new Message(stargazer, friend, now(), "stars r nice", null, null);
        messageRepository.save(m);

        Comment c = new Comment(stargazer, m, now(), "comment 556555");
        commentRepository.save(c);
        ThumbUp t = new ThumbUp(stargazer, m);
        thumbUpRepository.save(t);

        Long userAccountId = stargazer.getId();
        Long messageId = m.getId();
        Long commentId = c.getId();
        Long thumbUpId = t.getId();

        System.out.println(messageId);
        System.out.println(commentId);
        System.out.println(thumbUpId);

        assertFalse(userAccountRepository.getOne(m.getId()).getId() == null);
        assertFalse(messageRepository.getOne(messageId).getId() == null);
        assertFalse(commentRepository.getOne(commentId).getId() == null);
        assertFalse(thumbUpRepository.getOne(thumbUpId).getId() == null);
//        assertTrue(messageRepository.getOne(messageId).getMessageComments().contains(c));
    }
}
