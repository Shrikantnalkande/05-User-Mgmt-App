package in.ashokit.repository;

import in.ashokit.dto.StateDto;
import in.ashokit.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StateRepository extends JpaRepository<StateEntity,Integer> {
    List<StateEntity> findByCountryCountryId(Integer countryId);
}
