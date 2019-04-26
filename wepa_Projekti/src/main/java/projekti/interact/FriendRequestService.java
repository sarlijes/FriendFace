package projekti.interact;

import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekti.PictureAlbum;
import projekti.PictureAlbumRepository;
import projekti.UserAccount;
import projekti.UserAccountRepository;

@Service
public class FriendRequestService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;
    
 
    @Transactional
    public void addFriendRequest(FriendRequest friendRequest) {
        friendRequestRepository.save(friendRequest);
    }

    @Transactional
    public void addFriendRequestNativeSQL(FriendRequest friendRequest) {
        Long sourceUserAccountId = friendRequest.getSourceUserAccount().getId();
        Long targetUserAccountId = friendRequest.getTargetUserAccount().getId();
        LocalDateTime dateTime = now();
        Boolean pending = true;
        Boolean accepted = false;
        Long id = new Random().nextLong();

        friendRequestRepository.insertFriendRequest(id,accepted,dateTime,pending, sourceUserAccountId, targetUserAccountId);
    }

    @Transactional
    public void rejectFriendRequest(FriendRequest friendRequest) {
        friendRequest.getSourceUserAccount().getSentFriendRequests().remove(friendRequest);
        friendRequest.getTargetUserAccount().getRecievedFriendRequests().remove(friendRequest);
        friendRequestRepository.deleteById(friendRequest.getId());
    }

    @Transactional
    public void acceptFriendRequest(FriendRequest friendRequest) {
        friendRequest.setAccepted(Boolean.TRUE);
        friendRequest.setPending(Boolean.FALSE);
    }

    public List<FriendRequest> getSentFriendRequestsByUserAccount(UserAccount sourceUserAccount) {
        return friendRequestRepository.findBySourceUserAccount(sourceUserAccount);
    }

    public List<FriendRequest> getRecievedFriendRequestsByUserAccount(UserAccount targetUserAccount) {
        return friendRequestRepository.findByTargetUserAccount(targetUserAccount);
    }

    public FriendRequest getFriendRequestById(Long id) {
        return friendRequestRepository.getOne(id);
    }
}
