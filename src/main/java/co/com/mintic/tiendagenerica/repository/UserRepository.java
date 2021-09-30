package co.com.mintic.tiendagenerica.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.com.mintic.tiendagenerica.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	@Transactional
	Boolean deleteByCedula(long cedula);
	
	@Modifying
	@Transactional
	@Query("update User u set u.name = ?1,u.password = ?2 where u.email = ?3")
	void setUserInfoById(String name, String password, String email);
}
