package net.twitter.dao;

import net.twitter.dto.TweetDto;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TweetDao {
    @SqlUpdate
    @UseClasspathSqlLocator
    void insert(@BindBean TweetDto tweetDto);

    @SqlUpdate
    @UseClasspathSqlLocator
    TweetDto find(@Bind("id") int id);

}
