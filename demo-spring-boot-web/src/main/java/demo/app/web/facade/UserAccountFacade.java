package demo.app.web.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.app.core.service.SearchFilter;
import demo.app.web.vo.UserAccountVO;

public interface UserAccountFacade {

    Page<UserAccountVO> list(SearchFilter filter, Pageable page);

    String findEmployeeNumber(String email);

    UserAccountVO find(Integer id);

    void delete(Integer id);

    UserAccountVO create(UserAccountVO vo);

    UserAccountVO update(UserAccountVO vo);

}
