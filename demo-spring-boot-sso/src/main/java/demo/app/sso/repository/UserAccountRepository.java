package demo.app.sso.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import demo.app.sso.domain.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer>, JpaSpecificationExecutor<UserAccount> {

    UserAccount findByEmployeeNumber(@Param("employeeNumber") String employeeNumber);

    UserAccount findByEmailAddress(@Param("emailAddress") String emailAddress);

    Page<UserAccount> findByEmailAddressContaining(@Param("emailAddress") String emailAddress, Pageable pageable);

    /**
     * Finds a user by id and lockVersion. If the lock version is different than the one contained in the table, nothing
     * is returned.
     */
    UserAccount findByIdAndLockVersion(@Param("id") Integer id, @Param("lockVersion") Integer lockVersion);
}
