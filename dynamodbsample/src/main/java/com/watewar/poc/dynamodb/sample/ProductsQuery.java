package com.watewar.poc.dynamodb.sample;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;

import java.util.HashMap;
import java.util.Iterator;

public class ProductsQuery {
    public static void main(String[] args) throws Exception {

        DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build());

        Table table = dynamoDB.getTable("Products");
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#ID", "ID");
        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":xxx", 122);
        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("#ID = :xxx")
                .withNameMap(new NameMap().with("#ID", "ID"))
                .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;
        try {
            System.out.println("Product with the ID 122");
            items = table.query(querySpec);
            iterator = items.iterator();

            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getNumber("ID") + ": "
                        + item.getString("Nomenclature"));
            }
        } catch (Exception e) {
            System.err.println("Cannot find products with the ID number 122");
            System.err.println(e.getMessage());
        }
    }
}
