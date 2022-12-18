package net.twitter.dao;

import net.twitter.dto.TweetDto;
import net.twitter.dto.UserDto;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TweetDao {
    @SqlUpdate
    @UseClasspathSqlLocator
    @GetGeneratedKeys
    int insert(@BindBean TweetDto tweetDto);

    @SqlQuery
    @UseClasspathSqlLocator
    @RegisterBeanMapper(TweetDto.class)
    TweetDto find(@Bind("id") int id);

}
