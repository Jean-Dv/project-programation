package co.edu.uptc.infrastructure;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Factory class to create and manage MongoClient instances.
 */
public class MongoClientFactory {
  public static HashMap<String, MongoClient> clients = new HashMap<>();

  /**
   * Creates a new {@link MongoClient} instance if it does not exist, otherwise it returns the
   * existing one.
   *
   * @param contextName The name of the context.
   * @param url The URL to connect to the MongoDB server.
   * @return The {@link MongoClient} instance.
   */
  public static MongoClient createClient(String contextName, String url) {
    MongoClient mongoClient = MongoClientFactory.getClient(contextName);
    if (mongoClient == null) {
      mongoClient = MongoClientFactory.createAndConnectClient(url);
      MongoClientFactory.registerClient(contextName, mongoClient);
    }
    return mongoClient;
  }

  private static MongoClient getClient(String contextName) {
    return MongoClientFactory.clients.get(contextName);
  }

  private static MongoClient createAndConnectClient(String url) {
    MongoClient mongoClient = MongoClients
        .create(MongoClientSettings.builder().applyConnectionString(new ConnectionString(url))
            .applyToSocketSettings(builder -> builder.connectTimeout(5, TimeUnit.SECONDS)).build());
    mongoClient.startSession();
    return mongoClient;
  }

  private static void registerClient(String contextName, MongoClient mongoClient) {
    MongoClientFactory.clients.put(contextName, mongoClient);

  }
}
