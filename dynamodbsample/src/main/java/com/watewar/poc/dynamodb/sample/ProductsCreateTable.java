package com.watewar.poc.dynamodb.sample;

/**
 * Hello world!
 *
 */

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.Arrays;

public class ProductsCreateTable {
    public static void main(String[] args) throws Exception {

        //AWSStaticCredentialsProvider credentials = new AWSStaticCredentialsProvider(new BasicAWSCredentials("myKeyID", "myAccessKey"));
        //DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard().withCredentials(credentials).withRegion(Regions.US_EAST_1).build());

        DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build());

        String tableName = "Products";
        try {
            System.out.println("Creating the table, wait...");
            Table table = dynamoDB.createTable (tableName,
                    Arrays.asList (
                            new KeySchemaElement("ID", KeyType.HASH), // the partition key
                            // the sort key
                            new KeySchemaElement("Nomenclature", KeyType.RANGE)
                    ),
                    Arrays.asList (
                            new AttributeDefinition("ID", ScalarAttributeType.N),
                            new AttributeDefinition("Nomenclature", ScalarAttributeType.S)
                    ),
                    new ProvisionedThroughput(10L, 10L)
            );
            table.waitForActive();
            System.out.println("Table created successfully.  Status: " +
                    table.getDescription().getTableStatus());

        } catch (Exception e) {
            System.err.println("Cannot create the table: ");
            System.err.println(e.getMessage());
        }
    }
}
