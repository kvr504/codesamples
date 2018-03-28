package com.watewar.poc.dynamodb.sample;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

public class ProductsDeleteTable {
    public static void main(String[] args) throws Exception {

        DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build());

        Table table = dynamoDB.getTable("Products");
        try {
            System.out.println("Performing table delete, wait...");
            table.delete();
            table.waitForDelete();
            System.out.print("Table successfully deleted.");
        } catch (Exception e) {
            System.err.println("Cannot perform table delete: ");
            System.err.println(e.getMessage());
        }
    }
}