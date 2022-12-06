package net.twitter.dao;

import net.twitter.dto.FollowerDto;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface FollowerDao {

    @SqlUpdate
    @UseClasspathSqlLocator
    void insert(@BindBean FollowerDto followerDto);

    @SqlQuery
    @UseClasspathSqlLocator
    FollowerDto find(@Bind("followedId") String followedId);

}
