package demo.app.web.mapper;

import org.springframework.stereotype.Component;

import demo.app.sso.domain.UserAccount;
import demo.app.web.vo.UserAccountVO;

/**
 * Mapper to convert <code>UserAccount</code> to/from <code>UserAccountVO</code>.
 */
@Component
public class UserAccountMapper extends BaseMapper<UserAccount, UserAccountVO> {

    @Override
    protected void configureMappings() {
        getMapperFactory()
                .classMap(UserAccount.class, UserAccountVO.class)
                .byDefault().register();
    }
}
