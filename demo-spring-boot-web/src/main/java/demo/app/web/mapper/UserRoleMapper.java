package demo.app.web.mapper;

import org.springframework.stereotype.Component;

import demo.app.sso.domain.UserRole;
import demo.app.web.vo.UserRoleVO;

/**
 * Mapper to convert <code>UserRole</code> to/from <code>UserRoleVO</code>.
 */
@Component
public class UserRoleMapper extends BaseMapper<UserRole, UserRoleVO> {

    @Override
    protected void configureMappings() {
        getMapperFactory()
                .classMap(UserRole.class, UserRoleVO.class)
                .byDefault().register();
    }
}
