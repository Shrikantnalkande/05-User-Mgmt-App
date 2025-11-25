package in.ashokit.repository;

import in.ashokit.dto.CountryDto;
import in.ashokit.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity,Integer> {
}
