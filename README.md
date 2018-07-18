To build and run:

```
mvn clean install && java -jar target/neo-ogm-embedded-bolt.jar
```

This branch creates a base node (`storage:/base`) and then attempts to write multiple children nodes with the same name directly under the base node.

The following clause in the query _should_ prevent the database from creating duplicate siblings, however we are seeing that when multiple threads write at the same time, we do in fact end up with duplicates. Clause here:
```
WHERE NOT (parent)-[:PARENT_OF]->(:StorageResource { name: {childName} })
```

After the project runs, it will sit idle until killed (`ctrl + c`) and can be connected to by a Neo4j browser client by setting the Bolt URI to `localhost:17688`.