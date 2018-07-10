package amazon.aws.demo.amazonawscontractdemo.service;

import amazon.aws.demo.amazonawscontractdemo.model.Contract;

import java.util.List;

/**
 * @Description:
 * @author: jiasfeng
 * @Date: 7/10/2018
 */
public interface IDynamodbService {
    /**
     * Query
     * @param tableName
     * @param attribute
     * @param value
     * @return
     */
    List<Contract> query(String tableName, String attribute, Object value);

    /**
     * Query an item between {min} and {max}
     * @param tableName
     * @param attribute
     * @param val1
     * @param val2
     * @return
     */
    List<Contract> query(String tableName, String attribute, Object val1, Object val2);
}
