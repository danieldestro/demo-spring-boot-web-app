package demo.app.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import demo.app.core.domain.BaseEntity;

public interface CrudService<T extends BaseEntity<K>, K extends Serializable> {

    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    List<T> findByExample(Example<T> example, Sort sort);

    T findById(K id);

    T create(T entity);

    T update(T entity);

    void delete(T entity);

    void delete(K id);
}
