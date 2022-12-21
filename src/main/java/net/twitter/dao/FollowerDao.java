package net.twitter.dao;

import net.twitter.dto.FollowerDto;
import net.twitter.dto.UserDto;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface FollowerDao {

    @SqlUpdate
    @UseClasspathSqlLocator
    @GetGeneratedKeys
    int insert(@BindBean FollowerDto followerDto);

    @SqlQuery
    @UseClasspathSqlLocator
    @RegisterBeanMapper(FollowerDto.class)
    FollowerDto find(@Bind("id") String id);

    @SqlQuery
    @UseClasspathSqlLocator
    @RegisterBeanMapper(UserDto.class)
    List<UserDto> listUserFollowers(String userId);

    @SqlUpdate
    @UseClasspathSqlLocator
    void delete(String userId, String followingId);
}
