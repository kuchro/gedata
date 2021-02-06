package org.gedata.producer.repository;

import org.gedata.producer.model.user.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDataRepository extends JpaRepository<UserData,Long> {

    List<UserData> findByEmailAddressOrNickname(String email, String nickname);
}
