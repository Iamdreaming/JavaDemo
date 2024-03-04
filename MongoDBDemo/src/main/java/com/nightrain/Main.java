package com.nightrain;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Main {
    private static final String MONGO_HOST = "localhost";

    private static final Integer MONGO_PORT = 27017;

    private static final String MONGO_DB = "testdb";

    public static void main(String[] args) {
        try(MongoClient mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT)) {

            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MONGO_DB);
            System.out.println("Connect to database successfully");

            // 获取Collection
            mongoDatabase.getCollection("test");
            System.out.println("create collection");

            // 获取collection
            MongoCollection<Document> collection = mongoDatabase.getCollection("test");

            // 插入document
            Document doc = new Document("name", "MongoDB")
                    .append("type", "database")
                    .append("count", 1)
                    .append("info", new Document("x", 203).append("y", 102));
            collection.insertOne(doc);

            // 统计count
            System.out.println(collection.countDocuments());

            // query - first
            Document myDoc = collection.find().first();
            if (myDoc != null) {
                System.out.println(myDoc.toJson());
            }

            // query - loop all
            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    System.out.println(cursor.next().toJson());
                }
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}