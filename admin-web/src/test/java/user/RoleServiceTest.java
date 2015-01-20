package user;

import com.qatang.admin.entity.user.User;
import com.qatang.admin.query.user.UserSearchable;
import com.qatang.admin.service.user.UserService;
import com.qatang.admin.web.config.InitConfig;
import com.qatang.admin.web.config.WebConfig;
import com.qatang.admin.web.config.WebInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qatang
 * @since 2014-12-30 17:26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InitConfig.class})
public class RoleServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void findAllTest() {
        UserSearchable userSearchable = new UserSearchable();

        Page<User> page = userService.findAll(userSearchable);
        System.out.println(page.getContent().size());
        System.out.println(page.getSize());
    }
}
