package net.twitter.provider;

import com.zaxxer.hikari.HikariDataSource;
import net.twitter.infra.Configuration;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.LoggerFactory;

public class JdbiProvider {
    private Jdbi jdbi = null;
    private static JdbiProvider instance = instance();
    private HikariDataSource dataSource;

    private JdbiProvider() {

    }

    public static JdbiProvider instance() {
        if (instance == null) {
            instance = new JdbiProvider();
        }
        return instance;
    }

    public void init() {
        if (jdbi != null) {
            jdbi = null;
        }
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (Exception ex) {
                LoggerFactory.getLogger(this.getClass()).error("Hikari data source is null");
                } finally {
                dataSource = null;
            }
        }
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(Configuration.instance().getDbUrl());
        dataSource.setUsername(Configuration.instance().getDbUser());
        dataSource.setPassword(Configuration.instance().getDbPassword());
        dataSource.setMinimumIdle(Configuration.instance().getDbMinPoolSize());
        dataSource.setMaximumPoolSize(Configuration.instance().getDbMaxPoolSize());
        dataSource.setIdleTimeout(Configuration.instance().getDbConnectionIdleTimeout());

        jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    public Jdbi jdbi() {
        return jdbi;
    }
}
