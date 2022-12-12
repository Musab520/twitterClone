package net.twitter.dao;

import com.auth0.json.mgmt.users.User;
import net.twitter.dto.UserDto;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserDao {
    @SqlUpdate
    @UseClasspathSqlLocator
    void insert(@BindBean UserDto userDto);

    @SqlUpdate
    @UseClasspathSqlLocator
    void update(@Bind("id") String id, @BindBean("user") UserDto userDto);

    @SqlQuery
    @UseClasspathSqlLocator
    @RegisterBeanMapper(UserDto.class)
    UserDto find(@Bind("id") String userId);
}
