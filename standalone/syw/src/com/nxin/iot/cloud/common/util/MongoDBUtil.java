package com.nxin.iot.cloud.common.util;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import com.nxin.iot.cloud.common.constant.MongoConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bson.Document;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class MongoDBUtil {

    private static final String PLEASE_SEND_IP = “No ID or Port number.”;
    private static final String PWD_UN_ERR = “The user and password is not match.;
    private static final String PLEASE_INSTANCE_MONGOCLIENT = “PLEASE INSTANCE MONGOCLIENT";
    private static final String PLEASE_SEND_MONGO_REPOSITORY = “Please clarify the mongodb to be deleted.”;
    private static final String DELETE_MONGO_REPOSITORY_EXCEPTION = “Delete mongodb with something wrong.”;
    private static final String DELETE_MONGO_REPOSITORY_SUCCESS = “DELETE BATCH MONGO REPOSITORY SUCCESS";
    private static final String NOT_DELETE_MONGO_REPOSITORY = “NOT DELETE MONGO REPOSITORY";
    private static final String DELETE_MONGO_REPOSITORY = "DELETE MONGO REPOSITORY SUCCESS：";
    private static final String CREATE_MONGO_COLLECTION_NOTE = “CREATE MONGO COLLECTION NOTE";
    private static final String NO_THIS_MONGO_DATABASE = “NO THIS MONGO DATABASE";
    private static final String CREATE_MONGO_COLLECTION_SUCCESS = “CREATE MONGO COLLECTION SUCCESS :”;
    private static final String CREATE_MONGO_COLLECTION_EXCEPTION = “CREATE MONGO COLLECTION EXCEPTION";
    private static final String NOT_CREATE_MONGO_COLLECTION = “NOT CREATE MONGO COLLECTION";
    private static final String CREATE_MONGO_COLLECTION_SUCH = “CREATE MONGO COLLECTION SUCH：";
    private static final String NO_FOUND_MONGO_COLLECTION = “NO FOUND MONGO COLLECTION";
    private static final String INSERT_DOCUMEN_EXCEPTION = “INSERT DOCUMEN EXCEPTION";
    private static final String INSERT_DOCUMEN_SUCCESSS = “INSERT DOCUMEN SUCCESSS";
    private static final String COLLECTION_IS_NULL = “documentMongoCollection is null.”;


    private static final Logger logger = Logger.getLogger(MongoDBUtil.class);

    private MongoDBUtil(){

    }

    private static class SingleHolder{
        private static MongoDBUtil mongoDBUtil = new MongoDBUtil();
    }

    public static MongoDBUtil instance(){

        return SingleHolder.mongoDBUtil;
    }

    /**
     * Get mongoDB connection
     * @param host
     * @param port
     * @return
     */
    public MongoClient getMongoConnect(String host,Integer port){

        if(StringUtils.isBlank(host) || null == port){
            logger.error(PLEASE_SEND_IP);
            return null;
        }

        return new MongoClient(host, port);
    }

    /**
     * Get mongoDB connection
     * @param serverAddress
     * @param credentials
     * @return
     */
    @SuppressWarnings("deprecation")
    public MongoClient getMongoConnect(ServerAddress serverAddress,List<MongoCredential> credentials){

        if(null == serverAddress){
            logger.error(PLEASE_SEND_IP);
            return null;
        }

        if(null == credentials || credentials.size() == 0) {
            logger.error(PWD_UN_ERR);
            return null;
        }

        return new MongoClient(serverAddress, credentials);
    }


    /**
     * Bulk delete mongoDB
     * @param mongoClient
     * @param dbNames
     * @return
     */
    public String bulkDropDataBase(MongoClient mongoClient,String...dbNames){

        if(null == mongoClient) return PLEASE_INSTANCE_MONGOCLIENT;

        if(null==dbNames || dbNames.length==0){
            return PLEASE_SEND_MONGO_REPOSITORY;
        }
        try {
            Arrays.asList(dbNames).forEach(dbName -> mongoClient.dropDatabase(dbName));
            logger.info(DELETE_MONGO_REPOSITORY_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(DELETE_MONGO_REPOSITORY_EXCEPTION);
        }
        return dbNames == null ? NOT_DELETE_MONGO_REPOSITORY:DELETE_MONGO_REPOSITORY + String.join(",",dbNames);
    }


    /**
     * create database collection
     * @param mongoClient
     * @param dbName
     * @param collections
     * @return
     */
    public String createCollections(MongoClient mongoClient,String dbName,String...collections){

        if(null == mongoClient) return PLEASE_INSTANCE_MONGOCLIENT;

        if(null==collections || collections.length==0){
            return CREATE_MONGO_COLLECTION_NOTE;
        }

        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
        if(null == mongoDatabase) return NO_THIS_MONGO_DATABASE;

        try {
            Arrays.asList(collections).forEach(collection ->  mongoDatabase.createCollection(collection));
            logger.info(CREATE_MONGO_COLLECTION_SUCCESS);
            return collections == null ? NOT_CREATE_MONGO_COLLECTION:CREATE_MONGO_COLLECTION_SUCH + String.join(",",collections);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(CREATE_MONGO_COLLECTION_EXCEPTION);
        }

        return null;
    }

    /**
     * get MongoCollection
     * @param mongoClient
     * @param dbName
     * @param collection
     * @return
     */
    public MongoCollection<Document> getMongoCollection(MongoClient mongoClient,String dbName,String collection){

        if(null == mongoClient) return null;

        if(StringUtils.isBlank(dbName)) return null;

        if(StringUtils.isBlank(collection)) return null;

        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);

        MongoCollection<Document> collectionDocuments = mongoDatabase.getCollection(collection);

        if(null == collectionDocuments) return null;

        return collectionDocuments;
    }


    /**
     * insert documentation
     * @param mongoCollection
     * @param params
     */
    public void insertDoucument(final MongoCollection<Document> mongoCollection, final Map<String,Object> params){


        if(null == mongoCollection) return;


        try {
            Document document = new Document();
            params.keySet().stream().forEach(field -> document.append(field, params.get(field)));

            List<Document> documents = Lists.newArrayList();
            documents.add(document);
            mongoCollection.insertMany(documents);
            logger.info(INSERT_DOCUMEN_SUCCESSS);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(INSERT_DOCUMEN_EXCEPTION);
        }
    }

    /**
     * update document
     * @param mongoCollection
     * @param conditionParams
     * @param updateParams
     */
    public void updateDocument(final MongoCollection<Document> mongoCollection,final Map<String,Object> conditionParams,
                               final Map<String,Object> updateParams
    ){

        if(null == mongoCollection) return;

        if (null == conditionParams) return;

        if (null == updateParams) return;


        Document conditonDocument = new Document();
        conditionParams.keySet().stream().filter(p -> null != p).forEach(o -> {
            conditonDocument.append(o,conditionParams.get(o));
        });


        Document updateDocument = new Document();
        updateParams.keySet().stream().filter(p -> null != p).forEach(o -> {
            updateDocument.append(o,updateParams.get(o));
        });
        mongoCollection.updateMany(conditonDocument,new Document("$set",updateDocument));
    }

    /**
     *delete docuemnt
     * @param mongoCollection
     * @param multiple
     * @param conditionParams
     * @return
     */
    public long deleteDocument(final MongoCollection<Document> mongoCollection,final boolean multiple,
                               final Map<String,Object> conditionParams){

        if(null == mongoCollection) return 0;

        if(null == conditionParams) return 0;

        Document document = new Document();

        conditionParams.keySet().stream().filter(p -> null != p).forEach(o -> {
            document.append(o,conditionParams.get(o));
        });

        if(multiple) {
            return mongoCollection.deleteMany(document).getDeletedCount();
        }

        return mongoCollection.deleteOne(document).getDeletedCount();
    }

    /**
     * query
     * @param mongoCollection
     * @param conditionParams
     * @param limit
     * @param skip
     * @param sortParams
     */
    public MongoCursor<Document> queryDocument(final MongoCollection<Document> mongoCollection, final Map<String,Object> conditionParams,
                                               final Integer limit, final Integer skip, final Map<String,Integer> sortParams,
                                               final Map<String,Integer> gtLtOrOtherParams,final String compareField
    ){

        if(null == mongoCollection) return null;
        /**
         * 1. FindIterable<Document>
         * 2. MongoCursor<Document>
         * 3. Document collection
         * */

        Document document = new Document();
        if(null != conditionParams && !conditionParams.isEmpty()) {

            conditionParams.forEach((k,v)  -> {
                if(StringUtils.isNotBlank(k)) document.append(k,v);
            });
        }

        Document compareDocument = null;
        if(null != gtLtOrOtherParams && !gtLtOrOtherParams.isEmpty()) {

            Document gtOrLtDoc = new Document();
            gtLtOrOtherParams.forEach((k,v) -> {
                if(StringUtils.isNotBlank(k)) gtOrLtDoc.append(k,v);
            });
            compareDocument = new Document(compareField,gtOrLtDoc);
        }else {
            compareDocument = new Document();
        }

        FindIterable<Document> findIterable = mongoCollection.find(Filters.and(document,compareDocument));

        Document sortDocument = new Document();
        if(null != sortParams && !sortParams.isEmpty()) {
            sortParams.forEach((k,v) -> {
                if(StringUtils.isNotBlank(k)) sortDocument.append(k,v);
            });
            findIterable.sort(sortDocument);
        }

        if(null != limit) findIterable = findIterable.limit(limit);

        if(null != skip) findIterable = findIterable.skip(skip);

        return findIterable.iterator();

    }

    /**
     * query
     * @param mongoCollection
     * @return
     */
    public MongoCursor<Document> queryDocument(final MongoCollection<Document> mongoCollection
    ){

        if(null == mongoCollection) return null;
        FindIterable<Document> findIterable = mongoCollection.find();

        return findIterable.iterator();

    }

    public MongoCursor<Document> queryDocument(final MongoCollection<Document> mongoCollection, final Map<String,Object> conditionParams){

        if(null == mongoCollection) return null;
        Document document = new Document();
        if(null != conditionParams && !conditionParams.isEmpty()) {

            conditionParams.forEach((k,v)  -> {
                if(StringUtils.isNotBlank(k)) document.append(k,v);
            });
        }

        return mongoCollection.find(Filters.and(document)).iterator();
    }

    public MongoCursor<Document> queryDocument(final MongoCollection<Document> mongoCollection, final Map<String,Object> conditionParams,
                                               final Integer limit, final Integer skip){

        if(null == mongoCollection) return null;

        Document document = new Document();
        if(null != conditionParams && !conditionParams.isEmpty()) {

            conditionParams.forEach((k,v)  -> {
                if(StringUtils.isNotBlank(k)) document.append(k,v);
            });
        }

        FindIterable<Document> findIterable = mongoCollection.find(Filters.and(document));

        if(null != limit) findIterable = findIterable.limit(limit);

        if(null != skip) findIterable = findIterable.skip(skip);
        return findIterable.iterator();
    }

    public MongoCursor<Document> queryDocument(final MongoCollection<Document> mongoCollection,final Integer limit, final Integer skip){

        if(null == mongoCollection) return null;

        FindIterable<Document> findIterable = mongoCollection.find();

        if(null != limit) findIterable = findIterable.limit(limit);

        if(null != skip) findIterable = findIterable.skip(skip);
        return findIterable.iterator();
    }

    public MongoCursor<Document> queryDocument(final MongoCollection<Document> mongoCollection, final Map<String,Integer> sortParams,boolean sort){

        if(null == mongoCollection) return null;

        FindIterable<Document> findIterable = mongoCollection.find();

        Document sortDocument = new Document();
        if(null != sortParams && !sortParams.isEmpty()) {
            sortParams.forEach((k,v) -> {
                if(StringUtils.isNotBlank(k)) sortDocument.append(k,v);
            });
            findIterable.sort(sortDocument);
        }

        return findIterable.iterator();
    }

    public MongoCursor<Document> queryDocument(final MongoCollection<Document> mongoCollection, final Map<String,Object> conditionParams,
                                               final Integer limit, final Integer skip,final Map<String,Integer> sortParams){

        if(null == mongoCollection) return null;

        Document document = new Document();
        if(null != conditionParams && !conditionParams.isEmpty()) {

            conditionParams.forEach((k,v)  -> {
                if(StringUtils.isNotBlank(k)) document.append(k,v);
            });
        }

        FindIterable<Document> findIterable = mongoCollection.find(Filters.and(document));

        Document sortDocument = new Document();
        if(null != sortParams && !sortParams.isEmpty()) {
            sortParams.forEach((k,v) -> {
                if(StringUtils.isNotBlank(k)) sortDocument.append(k,v);
            });
            findIterable.sort(sortDocument);
        }

        if(null != limit) findIterable = findIterable.limit(limit);

        if(null != skip) findIterable = findIterable.skip(skip);
        return findIterable.iterator();
    }

    public MongoCursor<Document> queryDocument(final MongoCollection<Document> mongoCollection,
                                               final Integer limit, final Integer skip,final Map<String,Integer> sortParams){

        if(null == mongoCollection) return null;

        FindIterable<Document> findIterable = mongoCollection.find();

        Document sortDocument = new Document();
        if(null != sortParams && !sortParams.isEmpty()) {
            sortParams.forEach((k,v) -> {
                if(StringUtils.isNotBlank(k)) sortDocument.append(k,v);
            });
            findIterable.sort(sortDocument);
        }

        if(null != limit) findIterable = findIterable.limit(limit);

        if(null != skip) findIterable = findIterable.skip(skip);
        return findIterable.iterator();
    }

    public MongoCursor<Document> queryDocument(final MongoCollection<Document> mongoCollection,
                                               final Map<String,Integer> gtLtOrOtherParams,final String compareField
    ){

        if(null == mongoCollection) return null;
        Document compareDocument = null;
        if(null != gtLtOrOtherParams && !gtLtOrOtherParams.isEmpty()) {

            Document gtOrLtDoc = new Document();
            gtLtOrOtherParams.forEach((k,v) -> {
                if(StringUtils.isNotBlank(k)) gtOrLtDoc.append(k,v);
            });
            compareDocument = new Document(compareField,gtOrLtDoc);
        }else {
            compareDocument = new Document();
        }

        return mongoCollection.find(Filters.and(compareDocument)).iterator();

    }

    public MongoCursor<Document> queryDocument(final MongoCollection<Document> mongoCollection,final Integer limit,
                                               final Integer skip,final Map<String,Integer> gtLtOrOtherParams,final String compareField
    ){

        if(null == mongoCollection) return null;
        Document compareDocument = null;
        if(null != gtLtOrOtherParams && !gtLtOrOtherParams.isEmpty()) {

            Document gtOrLtDoc = new Document();
            gtLtOrOtherParams.forEach((k,v) -> {
                if(StringUtils.isNotBlank(k)) gtOrLtDoc.append(k,v);
            });
            compareDocument = new Document(compareField,gtOrLtDoc);
        }else {
            compareDocument = new Document();
        }

        FindIterable<Document> findIterable = mongoCollection.find(compareDocument);

        if(null != limit) findIterable = findIterable.limit(limit);

        if(null != skip) findIterable = findIterable.skip(skip);

        return findIterable.iterator();

    }

    public MongoCursor<Document> queryDocument(final MongoCollection<Document> mongoCollection,
                                               final Integer limit, final Integer skip, final Map<String,Integer> sortParams,
                                               final Map<String,Integer> gtLtOrOtherParams,final String compareField
    ){

        if(null == mongoCollection) return null;
        Document compareDocument = null;
        if(null != gtLtOrOtherParams && !gtLtOrOtherParams.isEmpty()) {

            Document gtOrLtDoc = new Document();
            gtLtOrOtherParams.forEach((k,v) -> {
                if(StringUtils.isNotBlank(k)) gtOrLtDoc.append(k,v);
            });
            compareDocument = new Document(compareField,gtOrLtDoc);
        }else {
            compareDocument = new Document();
        }

        FindIterable<Document> findIterable = mongoCollection.find(compareDocument);

        Document sortDocument = new Document();
        if(null != sortParams && !sortParams.isEmpty()) {
            sortParams.forEach((k,v) -> {
                if(StringUtils.isNotBlank(k)) sortDocument.append(k,v);
            });
            findIterable.sort(sortDocument);
        }

        if(null != limit) findIterable = findIterable.limit(limit);

        if(null != skip) findIterable = findIterable.skip(skip);

        return findIterable.iterator();

    }

    public MongoCursor<Document> queryDocument(final MongoCollection<Document> mongoCollection, final Map<String,Object> conditionParams,
                                               final Map<String,Integer> gtLtOrOtherParams,final String compareField
    ){

        if(null == mongoCollection) return null;

        Document document = new Document();
        if(null != conditionParams && !conditionParams.isEmpty()) {

            conditionParams.forEach((k,v)  -> {
                if(StringUtils.isNotBlank(k)) document.append(k,v);
            });
        }

        Document compareDocument = null;
        if(null != gtLtOrOtherParams && !gtLtOrOtherParams.isEmpty()) {

            Document gtOrLtDoc = new Document();
            gtLtOrOtherParams.forEach((k,v) -> {
                if(StringUtils.isNotBlank(k)) gtOrLtDoc.append(k,v);
            });
            compareDocument = new Document(compareField,gtOrLtDoc);
        }else {
            compareDocument = new Document();
        }

        FindIterable<Document> findIterable = mongoCollection.find(Filters.and(document,compareDocument));

        return findIterable.iterator();

    }

    public MongoCursor<Document> queryDocument(final MongoCollection<Document> mongoCollection, final Map<String,Object> conditionParams,
                                               final Integer limit, final Integer skip,final Map<String,Integer> gtLtOrOtherParams,final String compareField
    ){

        if(null == mongoCollection) return null;
        Document document = new Document();
        if(null != conditionParams && !conditionParams.isEmpty()) {

            conditionParams.forEach((k,v)  -> {
                if(StringUtils.isNotBlank(k)) document.append(k,v);
            });
        }

        Document compareDocument = null;
        if(null != gtLtOrOtherParams && !gtLtOrOtherParams.isEmpty()) {

            Document gtOrLtDoc = new Document();
            gtLtOrOtherParams.forEach((k,v) -> {
                if(StringUtils.isNotBlank(k)) gtOrLtDoc.append(k,v);
            });
            compareDocument = new Document(compareField,gtOrLtDoc);
        }else {
            compareDocument = new Document();
        }

        FindIterable<Document> findIterable = mongoCollection.find(Filters.and(document,compareDocument));

        if(null != limit) findIterable = findIterable.limit(limit);

        if(null != skip) findIterable = findIterable.skip(skip);

        return findIterable.iterator();

    }

    /**
     * count Table
     * @param documentMongoCollection
     * @param conditionParams
     * @param gtLtOrOtherParams
     * @param compareField
     * @return
     */
    public Long countTable(final MongoCollection<Document> documentMongoCollection,final Map<String,Object> conditionParams,
                           final Map<String,Integer> gtLtOrOtherParams,final String compareField){


        if(null == documentMongoCollection) {
            logger.error(COLLECTION_IS_NULL);
            return 0L;
        }

        Document document = new Document();
        if(null != conditionParams && !conditionParams.isEmpty()) {

            conditionParams.forEach((k,v)  -> {
                if(StringUtils.isNotBlank(k)) document.append(k,v);
            });
        }

        Document compareDocument = null;
        if(null != gtLtOrOtherParams && !gtLtOrOtherParams.isEmpty()) {

            Document gtOrLtDoc = new Document();
            gtLtOrOtherParams.forEach((k,v) -> {
                if(StringUtils.isNotBlank(k)) gtOrLtDoc.append(k,v);
            });
            compareDocument = new Document(compareField,gtOrLtDoc);
        }else {
            compareDocument = new Document();
        }

        return documentMongoCollection.count(Filters.and(document,compareDocument));
    }

    /**
     * count Table
     * @param documentMongoCollection
     * @param conditionParams
     * @return
     */
    public Long countTable(final MongoCollection<Document> documentMongoCollection,final Map<String,Object> conditionParams){


        if(null == documentMongoCollection) {
            logger.error(COLLECTION_IS_NULL);
            return 0L;
        }

        Document document = new Document();
        if(null != conditionParams && !conditionParams.isEmpty()) {

            conditionParams.forEach((k,v)  -> {
                if(StringUtils.isNotBlank(k)) document.append(k,v);
            });
        }

        return documentMongoCollection.count(Filters.and(document));
    }

    /**
     * count Table
     * @param documentMongoCollection
     * @param gtLtOrOtherParams
     * @param compareField
     * @return
     */
    public Long countTable(final MongoCollection<Document> documentMongoCollection,
                           final Map<String,Integer> gtLtOrOtherParams,final String compareField){


        if(null == documentMongoCollection) {
            logger.error(COLLECTION_IS_NULL);
            return 0L;
        }


        Document compareDocument = null;
        if(null != gtLtOrOtherParams && !gtLtOrOtherParams.isEmpty()) {

            Document gtOrLtDoc = new Document();
            gtLtOrOtherParams.forEach((k,v) -> {
                if(StringUtils.isNotBlank(k)) gtOrLtDoc.append(k,v);
            });
            compareDocument = new Document(compareField,gtOrLtDoc);
        }else {
            compareDocument = new Document();
        }

        return documentMongoCollection.count(Filters.and(compareDocument));
    }
}
