package demo.app.manager.data;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import demo.app.manager.domain.GlobalConfiguration;

@Repository
public interface GlobalConfigurationRepository extends JpaRepository<GlobalConfiguration, Integer>, JpaSpecificationExecutor<GlobalConfiguration> {

    Page<GlobalConfiguration> findByContext(String context, Pageable pageable);

    Page<GlobalConfiguration> findByNameContaining(String name, Pageable page);

    Page<GlobalConfiguration> findByNameContainingAndContext(String name, String context, Pageable page);

    @Query("select distinct o.context from GlobalConfiguration o order by o.context ASC")
    List<String> listContexts();
}
