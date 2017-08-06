MiniProfiler jOOQ integration example app
=========================================

The app is a very minimal servlet based app with an embedded H2 database that demonstrates how to execute queries using [jOOQ](https://www.jooq.org/) and display the executed SQL using the [MiniProfiler](https://github.com/tomdcc/miniprofiler-jvm).

The app is running at https://miniprofiler-jvm-jooq-example.herokuapp.com/.

To run the app locally, run `./gradlew appRun` and visit http://localhost:8080/.

About the MiniProfiler / jOOQ integration
-----------------------------------------

The integration captures SQL statements issued by jOOQ. This has the following benefits over using JDBC `DataSource` wrappers:
 
- jOOQ often formats the SQL better than the built-in SQL formatter.
- jOOQ inlines variables in the SQL statements correctly, making copying / pasting easier.
- The integration is much simpler than JDBC `DataSource` wrapping.
- Intercepting `DataSource` instances can be hard to do in certain frameworks.

Using the jOOQ integration
--------------------------

To use the jOOQ integration, configure your jOOQ `DSLContext` using the [`MiniProfilerExecuteListenerProvider`](https://github.com/tomdcc/miniprofiler-jvm/blob/master/miniprofiler-jooq/src/main/java/io/jdev/miniprofiler/jooq/MiniProfilerExecuteListenerProvider.java):

```java
DSLContext ctx = DSL.using(
    new DefaultConfiguration()
        .set(SQLDialect.POSTGRES)
        .set(dataSource)
        .set(new MiniProfilerExecuteListenerProvider(profilerProvider))
);
```

In this example app, this is configured in the [Startup](https://github.com/tomdcc/miniprofiler-jvm-jooq-example/blob/master/src/main/java/io/jdev/miniprofiler/examples/jooq/Startup.java) class.
