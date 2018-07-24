package amazon.aws.demo.amazonawscontractdemo.service.impl;

import amazon.aws.demo.amazonawscontractdemo.dynamodb.DynamoDBClient;
import amazon.aws.demo.amazonawscontractdemo.model.Contract;
import amazon.aws.demo.amazonawscontractdemo.service.IDynamodbService;
import com.alibaba.fastjson.JSONObject;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description:
 * @author: jiasfeng
 * @Date: 7/10/2018
 */
@Service
@Slf4j
public class DynamodbServiceImpl implements IDynamodbService {
    @Autowired
    private DynamoDB dynamoDB;

    @Override
    public List<Contract> query(String tableName, String attribute, Object value) {
        Table table = dynamoDB.getTable(tableName);
        List<Contract> contracts = new ArrayList<>();
        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression(String.format("%s = :v_attribute", attribute))
                .withValueMap(new ValueMap().with(":v_attribute", value));
        try {
            log.info("Attempting to read the item...");
            ItemCollection<QueryOutcome> items = table.query(spec);
            Iterator<Item> iterator = items.iterator();
            contracts = convert(iterator);
        }
        catch (Exception e) {
            log.error("Unable to read item: " + attribute);
            log.error(e.getMessage());
        }
        return contracts;
    }

    @Override
    public List<Contract> query(String tableName, String attribute, Object val1, Object val2) {
        Table table = dynamoDB.getTable(tableName);
        List<Contract> contracts = new ArrayList<>();
        ScanSpec spec = new ScanSpec()
                .withFilterExpression("#attr between :v_val1 and :v_val2")
                .withNameMap(new NameMap().with("#attr", attribute))
                .withValueMap(new ValueMap().with(":v_val1", val1).with(":v_val2", val2));
        try {
            log.info("Attempting to read the item...");
            ItemCollection<ScanOutcome> items = table.scan(spec);
            Iterator<Item> iterator = items.iterator();
            contracts = convert(iterator);
        }
        catch (Exception e) {
            log.error("Unable to read item: " + attribute);
            log.error(e.getMessage());
        }
        return contracts;
    }

    private List<Contract> convert(Iterator<Item> iterator) {
        List<Contract> contracts = new ArrayList<>();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            Contract contract = JSONObject.parseObject(item.toJSON(), Contract.class);
            contracts.add(contract);
        }
        return contracts;
    }
}
