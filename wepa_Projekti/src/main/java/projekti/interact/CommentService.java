package projekti.interact;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekti.Message;
import projekti.MessageRepository;
import projekti.UserAccount;
import projekti.UserAccountRepository;
import projekti.UserAccountService;
@Service
public class CommentService {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private CommentController commentController;
    
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void addComment(Comment comment) {
        
        commentRepository.save(comment);
//        UserAccount u = userAccountRepository.getUserAccountByUserName("jessi");
//        sender.getSentWallMessages().add(message);
//        u.getRecievedWallMessages().add(message);
    }

//    public Page<Message> findMax25messages(Long id) {
//        Pageable pageable = PageRequest.of(0, 25, Sort.by("messageTimeStamp").descending());
//        Page<Message> recievedMessages = messageRepository.findAllByRecieverId(pageable, id);
//        return recievedMessages;
//    
//}
}