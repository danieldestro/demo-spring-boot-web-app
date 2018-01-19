package demo.app.demo.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import demo.app.demo.domain.Demo;

@Repository
public interface DemoRepository extends JpaRepository<Demo, Integer>, JpaSpecificationExecutor<Demo> {

    Page<Demo> findByNameContaining(String name, Pageable pageable);
}
