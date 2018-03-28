package com.watewar.poc.dynamodb.sample;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class ProductsLoadData {
    public static void main(String[] args) throws Exception {

        DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build());

        Table table = dynamoDB.getTable("Products");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream("productinfo.json");

        JsonParser parser = new JsonFactory()
                .createParser(stream);



        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(parser);

        int ID = actualObj.get("ID").asInt();
        System.out.println("ID is "+ID);
        String Nomenclature1 = actualObj.get("Nomenclature").asText();
        System.out.println("Nomenclature is "+Nomenclature1);

        table.putItem(new Item()
                .withPrimaryKey("ID", ID, "Nomenclature", Nomenclature1)
                .withJSON("Stat", actualObj.get("Stat").toString()));

       /* JsonNode rootNode = new ObjectMapper().readTree(parser);
        Iterator<JsonNode> iter = rootNode.iterator();
        JsonNode currentNode;

        while (iter.hasNext()) {
            currentNode =  iter.next();
            int ID = currentNode.get("ID").asInt();
            String Nomenclature = currentNode.path("Nomenclature").asText();
    System.out.println("ID "+ID+" Nomenclature "+Nomenclature);
            try {
                table.putItem(new Item()
                        .withPrimaryKey("ID", ID, "Nomenclature", Nomenclature)
                        .withJSON("Stat", currentNode.path("Stat").toString()));
                System.out.println("Successful load: " + ID + " " + Nomenclature);
            } catch (Exception e) {
                System.err.println("Cannot add product: " + ID + " " + Nomenclature);
                System.err.println(e.getMessage());
                break;
            }
        }*/
        parser.close();
    }
}