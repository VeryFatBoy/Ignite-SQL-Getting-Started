package org.apache.ignite.gettingstarted;

import java.util.Iterator;
import java.util.List;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.cache.affinity.AffinityKeyMapped;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

/**
 * Do the following prior running this example:
 * <lu>
 *     <li>
 *         Start apache ignite cluster node using {apache_ignite_version}/bin/ignite.sh (bat) file.
 *     </li>
 *     <li>
 *         Create SQL schema and indexes executing {@link JdbcExampleCreateTables} and {@link JdbcExampleCreateIndexes}
 *         sources.
 *     </li>
 * </lu>
 *
 * Run this example after that to see how to execute DML statements from Java side.
 */
public class JavaSample {
    /**
     * Executes the example.
     * @param args
     */
    public static void main(String[] args) {
        Ignition.setClientMode(true);

        try (Ignite ignite = Ignition.start()) {

            System.out.println();
            System.out.println(">>> Java Sample is started.");

            // Getting a reference to City and Person caches created by
            // {@link JdbcExampleCreateTables} and {@link JdbcExampleCreateIndexes}
            IgniteCache cityCache = ignite.cache("SQL_PUBLIC_CITY");

            IgniteCache personCache = ignite.cache("SQL_PUBLIC_PERSON");

            insertData(cityCache, personCache);

            queryData(cityCache);

            updateData(cityCache);

            removeData(personCache);

            queryData(cityCache);
        }
    }

    /**
     * Putting data into the cluster.
     *
     * @param cityCache City cache.
     * @param personCache Person cache.
     */
    private static void insertData(IgniteCache cityCache, IgniteCache personCache) {
        // Make sure the cache is empty.
        cityCache.clear();

        // Inserting entries into City.
        SqlFieldsQuery query = new SqlFieldsQuery("INSERT INTO City (id, name) VALUES (?, ?)");

        cityCache.query(query.setArgs(1, "Forest Hill")).getAll();
        cityCache.query(query.setArgs(2, "Denver")).getAll();
        cityCache.query(query.setArgs(3, "St. Petersburg")).getAll();

        System.out.println(">>> Inserted entries into City:" + cityCache.size(CachePeekMode.PRIMARY));

        // Make sure the cache is empty.
        personCache.clear();

        // Inserting entries into Person.
        query = new SqlFieldsQuery("INSERT INTO Person (id, name, city_id) VALUES (?, ?, ?)");

        personCache.query(query.setArgs(1, "John Doe", 3)).getAll();
        personCache.query(query.setArgs(2, "Jane Roe", 2)).getAll();
        personCache.query(query.setArgs(3, "Mary Major", 1)).getAll();
        personCache.query(query.setArgs(4, "Richard Miles", 2)).getAll();

        System.out.println(">>> Inserted entries into Person:" + personCache.size(CachePeekMode.PRIMARY));
    }

    /**
     * Query data from the cluster.
     *
     * @param cityCache City cache.
     */
    private static void queryData(IgniteCache cityCache) {
        // Querying data from the cluster using a distributed JOIN.
        SqlFieldsQuery query = new SqlFieldsQuery("SELECT p.name, c.name " +
            " FROM Person p, City c WHERE p.city_id = c.id");

        FieldsQueryCursor<List<?>> cursor = cityCache.query(query);

        Iterator<List<?>> iterator = cursor.iterator();

        System.out.println("Query result:");

        while (iterator.hasNext()) {
            List<?> row = iterator.next();

            System.out.println(">>>    " + row.get(0) + ", " + row.get(1));
        }
    }

    /**
     * Updating data in the cluster.
     *
     * @param cityCache City cache.
     */
    private static void updateData(IgniteCache cityCache) {
        // Updating a city entry.
        SqlFieldsQuery query = new SqlFieldsQuery("UPDATE City SET name = 'Foster City' WHERE id = 2");

        cityCache.query(query).getAll();
    }

    /**
     * Remove data from the cluster.
     *
     * @param personCache Person cache.
     */
    private static void removeData(IgniteCache personCache) {
        // Removing a person.
        SqlFieldsQuery query = new SqlFieldsQuery("DELETE FROM Person WHERE name = 'John Doe'");

        personCache.query(query).getAll();
    }
}
