package projekti;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Transactional
    public void addWallMessage(UserAccount sender, UserAccount reciever, LocalDateTime dateTime, String content) {
        Message message = new Message(sender, reciever, dateTime, content, null);
        messageRepository.save(message);
        UserAccount u = userAccountRepository.getUserAccountByUserName("jessi");
//        sender.getSentWallMessages().add(message);
//        u.getRecievedWallMessages().add(message);
    }

    public Page<Message> findMax25messages(Long id) {
        Pageable pageable = PageRequest.of(0, 25, Sort.by("messageTimeStamp").descending());
        Page<Message> recievedMessages = messageRepository.findAllByRecieverId(pageable, id);
        return recievedMessages;
    }

}
