package demo.app.manager.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.app.core.service.CrudService;
import demo.app.manager.domain.GlobalConfiguration;

public interface GlobalConfigurationService extends CrudService<GlobalConfiguration, Integer> {

    Page<GlobalConfiguration> findByContext(String context, Pageable page);

    Page<GlobalConfiguration> findByName(String name, Pageable page);

    Page<GlobalConfiguration> findByNameAndContext(String name, String context, Pageable page);

    List<String> listContexts();
}
