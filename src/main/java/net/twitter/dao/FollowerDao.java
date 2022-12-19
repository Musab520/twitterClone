package net.twitter.dao;

import net.twitter.dto.FollowerDto;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface FollowerDao {

    @SqlUpdate
    @UseClasspathSqlLocator
    @GetGeneratedKeys
    int insert(@BindBean FollowerDto followerDto);

    @SqlQuery
    @UseClasspathSqlLocator
    @RegisterBeanMapper(FollowerDto.class)
    FollowerDto find(@Bind("id") String id);

}
