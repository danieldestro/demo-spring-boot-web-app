package demo.app.web.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * Base mapper implementation to convert an Entity to/from a VO and vice versa.
 */
@Component
public abstract class BaseMapper<S, D> implements ModelMapper<S, D> {

    private MapperFactory mapperFactory;

    public BaseMapper() {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        configureMappings();
    }

    protected abstract void configureMappings();

    /**
     * Adds a default mapping configuration. If you need to add customized configuration you can implement yourself.
     * 
     * @param entityClass
     * @param voClass
     */
    protected void defaultMappingConfiguration(Class<S> entityClass, Class<D> voClass) {
        // Entity to/from VO
        getMapperFactory().classMap(entityClass, voClass).byDefault().register();
        // getMapperFactory().classMap(entityClass, voClass).use(entityClass,
        // voClass).mapNulls(false).mapNullsInReverse(false).byDefault().register();
    }

    protected MapperFactory getMapperFactory() {
        return mapperFactory;
    }

    @Override
    public D mapTo(S source, Class<D> type) {
        return getMapperFactory().getMapperFacade().map(source, type);
    }

    @Override
    public Page<D> mapTo(Page<S> page, Pageable pageable, Class<D> type) {

        List<S> content = page.getContent();
        List<D> converted = mapList(content, type);

        return new PageImpl<>(converted, pageable, page.getTotalElements());
    }

    @Override
    public S mapFrom(D dest, Class<S> type) {
        return getMapperFactory().getMapperFacade().map(dest, type);
    }

    @Override
    public List<D> mapList(List<S> list, Class<D> type) {
        return getMapperFactory().getMapperFacade().mapAsList(list, type);
    }
}
