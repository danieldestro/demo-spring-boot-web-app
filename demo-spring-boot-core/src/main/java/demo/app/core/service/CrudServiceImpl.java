package demo.app.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import demo.app.core.domain.BaseEntity;

public class CrudServiceImpl<T extends BaseEntity<K>, K extends Serializable> implements CrudService<T, K> {

    @Autowired
    private JpaRepository<T, K> crudRepository;

    @Override
    public List<T> findAll() {
        return crudRepository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return crudRepository.findAll(pageable);
    }

    @Override
    public List<T> findByExample(Example<T> example, Sort sort) {
        return crudRepository.findAll(example, sort);
    }

    @Override
    public T findById(K id) {
        return crudRepository.findOne(id);
    }

    @Override
    @Transactional
    public T create(T entity) {
        return crudRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public T update(T entity) {
        return crudRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        delete(entity.getId());
    }

    @Override
    @Transactional
    public void delete(K id) {
        crudRepository.delete(id);
        crudRepository.flush();
    }
}
