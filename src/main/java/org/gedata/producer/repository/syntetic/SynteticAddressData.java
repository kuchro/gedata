package org.gedata.producer.repository.syntetic;

import org.gedata.producer.model.synteticdata.FakeAddressData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynteticAddressData extends JpaRepository<FakeAddressData,Long> {
}
