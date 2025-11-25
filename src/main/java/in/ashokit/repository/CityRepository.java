package in.ashokit.repository;

import in.ashokit.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<CityEntity,Integer> {
    List<CityEntity> findByStateStateId(Integer stateId);
}
