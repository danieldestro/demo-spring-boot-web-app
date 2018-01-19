package demo.app.web.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.app.core.service.SearchFilter;
import demo.app.web.vo.UserRoleVO;

public interface UserRoleFacade {

    Page<UserRoleVO> list(SearchFilter filter, Pageable page);

    UserRoleVO find(Integer id);

    void delete(Integer id);

    UserRoleVO create(UserRoleVO vo);

    UserRoleVO update(UserRoleVO vo);
}
