package amazon.aws.demo.amazonawscontractdemo.dynamodb;

import amazon.aws.demo.amazonawscontractdemo.constants.Constants;
import amazon.aws.demo.amazonawscontractdemo.model.Contract;
import com.alibaba.fastjson.JSONObject;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: jiasfeng
 * @Date: 7/6/2018
 */
public class ContractsDBQuery {

    public static List<Contract> query(String tableName, String attribute, Object value) {
        DynamoDB dynamoDB = DynamoDBClient.getDynamoDBClient();
        Table table = dynamoDB.getTable(tableName);
        List<Contract> contracts = new ArrayList<>();
        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression(String.format("%s = :v_attribute", attribute))
                .withValueMap(new ValueMap().with(":v_attribute", value));
        try {
            System.out.println("Attempting to read the item...");
            ItemCollection<QueryOutcome> items = table.query(spec);
            Iterator<Item> iterator = items.iterator();
            contracts = convert(iterator);
        }
        catch (Exception e) {
            System.err.println("Unable to read item: " + attribute);
            System.err.println(e.getMessage());
        }
        return contracts;
    }

    private static List<Contract> convert(Iterator<Item> iterator) {
        List<Contract> contracts = new ArrayList<>();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            Contract contract = JSONObject.parseObject(item.toJSON(), Contract.class);
            contracts.add(contract);
        }
        return contracts;
    }
}
