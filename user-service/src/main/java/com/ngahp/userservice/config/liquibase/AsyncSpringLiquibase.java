package com.ngahp.userservice.config.liquibase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executor;

import org.springframework.boot.autoconfigure.liquibase.DataSourceClosingSpringLiquibase;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.util.StopWatch;

import liquibase.exception.LiquibaseException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsyncSpringLiquibase extends DataSourceClosingSpringLiquibase {
    public static final String DISABLED_MESSAGE = "Liquibase is disabled";
    public static final String STARTING_ASYNC_MESSAGE = "Starting Liquibase asynchronously, your database might not be ready at startup!";
    public static final String STARTING_SYNC_MESSAGE = "Starting Liquibase synchronously";
    public static final String STARTED_MESSAGE = "Liquibase has updated your database in {} ms";
    public static final String EXCEPTION_MESSAGE = "Liquibase could not start correctly, your database is NOT ready: {}";
    public static final long SLOWNESS_THRESHOLD = 5L;
    public static final String SLOWNESS_MESSAGE = "Warning, Liquibase took more than {} seconds to start up!";
    private final Executor executor;
    private final Environment env;

    public AsyncSpringLiquibase(Executor executor, Environment env) {
        this.executor = executor;
        this.env = env;
    }

    public void afterPropertiesSet() throws LiquibaseException {
        if (!this.env.acceptsProfiles(Profiles.of(new String[]{"no-liquibase"}))) {
            if (this.env.acceptsProfiles(Profiles.of(new String[]{"dev|heroku"}))) {
                try {
                    Connection connection = this.getDataSource().getConnection();

                    try {
                        this.executor.execute(() -> {
                            try {
                                log.warn("Starting Liquibase asynchronously, your database might not be ready at startup!");
                                this.initDb();
                            } catch (LiquibaseException var2) {
                                log.error("Liquibase could not start correctly, your database is NOT ready: {}", var2.getMessage(), var2);
                            }

                        });
                    } catch (Throwable var5) {
                        if (connection != null) {
                            try {
                                connection.close();
                            } catch (Throwable var4) {
                                var5.addSuppressed(var4);
                            }
                        }

                        throw var5;
                    }

                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException var6) {
                    log.error("Liquibase could not start correctly, your database is NOT ready: {}", var6.getMessage(), var6);
                }
            } else {
                log.debug("Starting Liquibase synchronously");
                this.initDb();
            }
        } else {
            log.debug("Liquibase is disabled");
        }

    }

    protected void initDb() throws LiquibaseException {
        StopWatch watch = new StopWatch();
        watch.start();
        super.afterPropertiesSet();
        watch.stop();
        log.debug("Liquibase has updated your database in {} ms", watch.getTotalTimeMillis());
        if (watch.getTotalTimeMillis() > 5000L) {
            log.warn("Warning, Liquibase took more than {} seconds to start up!", 5L);
        }
    }
}

