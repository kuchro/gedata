package org.gedata.producer.repository;

import org.gedata.producer.model.producer.GenericData;
import org.gedata.producer.model.user.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenericDataRepository extends JpaRepository<GenericData,Long> {

    List<GenericData> findGenericDataByUserData(UserData userData);
}
