package net.twitter.dao;

import net.twitter.dto.LikedTweetDto;
import net.twitter.dto.TweetDto;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface LikedTweetDao {
    @SqlUpdate
    @UseClasspathSqlLocator
    @GetGeneratedKeys
    int insert(@BindBean LikedTweetDto likedTweetDto);

    @SqlQuery
    @UseClasspathSqlLocator
    @RegisterBeanMapper(LikedTweetDto.class)
    LikedTweetDto find(@Bind("id") int id);
}
