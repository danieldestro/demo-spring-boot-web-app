package demo.app.web.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Generic interface for a mapper to convert entities to VOs and vice versa. Remember to provide a new mapper implementation class.
 */
public interface ModelMapper<S, D> {

    D mapTo(S source, Class<D> type);

    Page<D> mapTo(Page<S> page, Pageable pageable, Class<D> type);

    S mapFrom(D dest, Class<S> type);

    List<D> mapList(List<S> list, Class<D> type);
}
