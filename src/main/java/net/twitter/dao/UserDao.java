package net.twitter.dao;

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
    void update(@BindBean("user") UserDto userDto);

    @SqlQuery
    @UseClasspathSqlLocator
    UserDto find(@Bind("id") String userId);
}
