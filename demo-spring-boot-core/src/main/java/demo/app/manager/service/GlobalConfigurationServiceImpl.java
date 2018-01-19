package demo.app.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.app.core.service.CrudServiceImpl;
import demo.app.manager.data.GlobalConfigurationRepository;
import demo.app.manager.domain.GlobalConfiguration;

@Service
public class GlobalConfigurationServiceImpl extends CrudServiceImpl<GlobalConfiguration, Integer> implements GlobalConfigurationService {

    @Autowired
    private GlobalConfigurationRepository repository;

    @Override
    public Page<GlobalConfiguration> findByName(String name, Pageable page) {
        return repository.findByNameContaining(name, page);
    }

    @Override
    public Page<GlobalConfiguration> findByContext(String context, Pageable page) {
        return repository.findByContext(context, page);
    }

    @Override
    public Page<GlobalConfiguration> findByNameAndContext(String name, String context, Pageable page) {
        return repository.findByNameContainingAndContext(name, context, page);
    }

    @Override
    public List<String> listContexts() {
        return repository.listContexts();
    }
}
