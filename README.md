To build and run:

```
mvn clean install && java -jar target/neo-ogm-embedded-bolt.jar
```

The project will build out a few `StorageResource` nodes that look like this:
```
(base:/storage)-[PARENT_OF]->(top)-->(folder)-->(file.txt)
```

The goal is to produce a query that can be passed to the `Session.queryForObject` method that will return a single `StorageResource` object with parent nodes correctly referenced all the way up.

After the project runs, it will sit idle until killed (`ctrl + c`) and can be connected to by a Neo4j browser client by setting the Bolt URI to `localhost:17688`.