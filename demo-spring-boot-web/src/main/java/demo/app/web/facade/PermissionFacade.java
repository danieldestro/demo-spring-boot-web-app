package demo.app.web.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.app.core.service.SearchFilter;
import demo.app.web.vo.PermissionVO;

public interface PermissionFacade {

    Page<PermissionVO> list(SearchFilter filter, Pageable page);

    PermissionVO find(Integer id);

    void delete(Integer id);

    PermissionVO create(PermissionVO vo);

    PermissionVO update(PermissionVO vo);
}
