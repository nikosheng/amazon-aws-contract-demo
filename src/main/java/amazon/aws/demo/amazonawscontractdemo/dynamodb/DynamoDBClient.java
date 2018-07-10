package amazon.aws.demo.amazonawscontractdemo.dynamodb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

/**
 * A singleton to create the DynamoDB client
 *
 * @author: jiasfeng
 * @Date: 7/6/2018
 */
public class DynamoDBClient {
    private DynamoDBClient(){}

    private static class DynamnoDBClientHolder {
        private final static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://dynamodb.cn-north-1.amazonaws.com.cn", "cn-north-1"))
                .build();

        private final static DynamoDB dynamoDB = new DynamoDB(client);
    }

    public static DynamoDB getDynamoDBClient() {
        return DynamnoDBClientHolder.dynamoDB;
    }
}
