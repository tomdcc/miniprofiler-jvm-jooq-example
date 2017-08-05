/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jdev.miniprofiler.examples.jooq;

import io.jdev.miniprofiler.DefaultProfilerProvider;
import io.jdev.miniprofiler.ProfilerProvider;
import io.jdev.miniprofiler.ScriptTagWriter;
import io.jdev.miniprofiler.jooq.MiniProfilerExecuteListenerProvider;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

/**
 * Sets up data source, jOOQ interface and MiniProfiler.
 */
public class Startup implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // set up MiniProfiler profiler provider
        ProfilerProvider profilerProvider = new DefaultProfilerProvider();

        // create script tag writer used by JSPs hooked up to correct provider
        sce.getServletContext().setAttribute("scriptTagWriter", new ScriptTagWriter(profilerProvider));

        // create jOOQ database interface
        sce.getServletContext().setAttribute("db", createDSLContext(profilerProvider));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Shut down db interface
        ((DSLContext) sce.getServletContext().getAttribute("db")).close();
    }

    /**
     * Initialize a jOOQ context and wire up to MiniProfiler
     */
    private static DSLContext createDSLContext(ProfilerProvider profilerProvider) {
        return DSL.using(
            new DefaultConfiguration()
                .set(SQLDialect.H2)
                .set(createDataSource())
                // this adds SQL timings to profiling sessions with SQL provided by jOOQ,
                // with parameters inlined
                .set(new MiniProfilerExecuteListenerProvider(profilerProvider))
        );
    }

    /**
     * Initialize a H2 data source and populate with some data
     */
    private static DataSource createDataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setUrl("jdbc:h2:mem:jooq-example;init="
            + "runscript from 'classpath:schema.sql'\\;"
            + "runscript from 'classpath:data.sql'\\;"
        );
        ds.setUser("sa");
        return ds;
    }
}
