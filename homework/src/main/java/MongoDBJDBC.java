import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;
//C:\Program Files\MongoDB\Server\4.4\data\
public class MongoDBJDBC{

    public static void main( String args[] ){
        try{
            // connect to mongodb client      default: mongodb://hostOne:27017
            MongoClient mongoClient = MongoClients.create();

            // connect to database called mydb, create new if not exist;
            MongoDatabase database = mongoClient.getDatabase("mydb");

            MongoCollection<Document> collection = database.getCollection("test");

//            {
//                "name" : "MongoDB",
//                    "type" : "database",
//                    "count" : 1,
//                    "versions": [ "v3.2", "v3.0", "v2.6" ],
//                "info" : { x : 203, y : 102 }
//            }
            Document doc = new Document("name", "MongoDB")
                    .append("type", "database")
                    .append("count", 1)
                    .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                    .append("info", new Document("x", 203).append("y", 102));
            collection.insertOne(doc);

            System.out.println(collection.countDocuments());

//            Document myDoc = collection.find().first();
//            System.out.println(myDoc.toJson());


        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}