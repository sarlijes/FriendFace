package projekti.interact;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public void addFriendRequest(UserAccount sourceUser, UserAccount targetUser, LocalDateTime dateTime) {
        FriendRequest f = new FriendRequest(sourceUser, targetUser, dateTime, false, true);
        sourceUser.getSentFriendRequests().add(f);
        targetUser.getRecievedFriendRequests().add(f);
        friendRequestRepository.save(f);
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

