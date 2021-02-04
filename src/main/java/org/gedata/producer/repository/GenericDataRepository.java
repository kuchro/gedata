package org.gedata.producer.repository;

import org.gedata.producer.model.producer.GenericData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericDataRepository extends JpaRepository<GenericData,Long> {
}
