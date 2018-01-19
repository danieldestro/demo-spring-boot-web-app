package demo.app.sso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import demo.app.sso.domain.EntityAudit;

@Repository
public interface EntityAuditRepository extends JpaRepository<EntityAudit, Integer>, JpaSpecificationExecutor<EntityAudit> {

}
