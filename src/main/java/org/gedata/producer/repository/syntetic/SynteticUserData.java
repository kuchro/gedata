package org.gedata.producer.repository.syntetic;


import org.gedata.producer.model.synteticdata.FakeUserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynteticUserData extends JpaRepository<FakeUserData,Long> {
}
