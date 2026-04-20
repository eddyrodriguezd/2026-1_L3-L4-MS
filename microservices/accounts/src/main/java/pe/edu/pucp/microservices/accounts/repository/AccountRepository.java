package pe.edu.pucp.microservices.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.pucp.microservices.accounts.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
