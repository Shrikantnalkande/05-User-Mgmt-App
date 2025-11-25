package in.ashokit.repository;

import in.ashokit.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    public boolean existsByEmail(String email);

    public UserEntity findByEmailAndPwd(String email,String pwd);

    public UserEntity findByEmail(String email);
}
