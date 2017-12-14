A simple project to demonstrate my attempt to enable bolt in embedded Neo4j using the OGM library.

To build and run:

```
mvn clean install && java -jar target/neo-ogm-embedded-bolt.jar
```

This will point the database to `target/neo-store`, drop the database, and then populate it with a few nodes. Bolt is configured to run on `17687`.


It can also be pointed at an already running instance of Neo4j using a bolt connection. To build and run this way:

```
mvn -o clean install && java -jar -Dneo-credentials=<username>:<password> -Dneo-store=bolt://localhost:7687 target/neo-ogm-embedded-bolt.jar
```