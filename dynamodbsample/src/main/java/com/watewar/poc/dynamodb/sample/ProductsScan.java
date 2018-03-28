package com.watewar.poc.dynamodb.sample;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import java.util.Iterator;

public class ProductsScan {
    public static void main(String[] args) throws Exception {

        DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build());

        Table table = dynamoDB.getTable("Products");
        ScanSpec scanSpec = new ScanSpec()
                .withProjectionExpression("#ID, Nomenclature , stat.sales")
                .withFilterExpression("#ID between :start_id and :end_id")
                .withNameMap(new NameMap().with("#ID",  "ID"))
                .withValueMap(new ValueMap().withNumber(":start_id", 120)
                        .withNumber(":end_id", 129));

        try {
            ItemCollection<ScanOutcome> items = table.scan(scanSpec);
            Iterator<Item> iter = items.iterator();

            while (iter.hasNext()) {
                Item item = iter.next();
                System.out.println(item.toString());
            }
        } catch (Exception e) {
            System.err.println("Cannot perform a table scan:");
            System.err.println(e.getMessage());
        }
    }
}
