package projekti.interact;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projekti.UserAccount;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    List<FriendRequest> findBySourceUserAccount(UserAccount sourceUserAccount);

    List<FriendRequest> findByTargetUserAccount(UserAccount targetUserAccount);

    @Modifying
    @Query(value = "INSERT INTO FRIEND_REQUEST (ID, ACCEPTED, FRIEND_REQUEST_TIME_STAMP, PENDING, SOURCE_USER_ACCOUNT_ID, TARGET_USER_ACCOUNT_ID) values (:id, :accepted, :dateTime, :pending, :sourceUserAccountId, :targetUserAccountId)",
            nativeQuery = true)
    void insertFriendRequest(@Param("id") Long id, @Param("accepted") Boolean accepted, @Param("dateTime") LocalDateTime dateTime,
            @Param("pending") Boolean pending,
            @Param("sourceUserAccountId") Long sourceUserAccountId,
            @Param("targetUserAccountId") Long targetUserAccountId);

//    INSERT INTO FRIEND_REQUEST(ID, ACCEPTED, FRIEND_REQUEST_TIME_STAMP, PENDING, SOURCE_USER_ID, TARGET_USER_ID )
//VALUES (1, false, '2008-08-08', true, 1, 7);
}
/*
    @Modifying
    @Query(value = "insert into Users (name, age, email, status) 
            values (:name, :age, :email, :status)",
            nativeQuery = true)
    void insertUser(@Param("name") String name, @Param("age") Integer age,
            @Param("status") Integer status, @Param("email") String email);
 */
