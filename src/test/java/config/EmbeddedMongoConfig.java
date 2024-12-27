//package africa.semicolon.toDo.data.config;
//
//import com.mongodb.client.MongoClient;
//import de.flapdoodle.embed.mongo.MongodExecutable;
//import de.flapdoodle.embed.mongo.MongodStarter;
//import de.flapdoodle.embed.mongo.config.MongodConfig;
//import de.flapdoodle.embed.mongo.config.runtime.Network;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
//
//@Configuration
//public class EmbeddedMongoConfig extends AbstractMongoClientConfiguration {
//
//    @Override
//    protected String getDatabaseName() {
//        return "todotestdb";
//    }
//
//    @Bean
//    public MongodExecutable embeddedMongoServer() throws Exception {
//        MongodStarter starter = MongodStarter.getDefaultInstance();
//        MongodConfig mongodConfig = MongodConfig.builder()
//                .version(de.flapdoodle.embed.mongo.distribution.Version.Main.PRODUCTION)
//                .net(new Network("localhost", 27017, Network.localhostIsIPv6()))
//                .build();
//        return starter.prepare(mongodConfig);
//    }
//
//    @Bean
//    public MongoClient mongoClient() throws Exception {
//        return super.mongoClient();
//    }
//}
