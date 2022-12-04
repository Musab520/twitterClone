package net.twitter.dao;

import net.twitter.dto.UserDto;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserDao {
    @SqlUpdate
    @UseClasspathSqlLocator
    void insert(@BindBean UserDto userDto);

    @SqlUpdate
    @UseClasspathSqlLocator
    void update(@BindBean UserDto userDto);

    @SqlUpdate
    @UseClasspathSqlLocator
    UserDto find(@BindBean UserDto userDto);
}
