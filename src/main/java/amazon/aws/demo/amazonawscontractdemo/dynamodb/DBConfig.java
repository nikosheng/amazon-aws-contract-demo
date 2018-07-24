package amazon.aws.demo.amazonawscontractdemo.dynamodb;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @author: jiasfeng
 * @Date: 7/24/2018
 */
@Configuration
public class DBConfig {
    @Bean
    public DynamoDB getDynamoDB() {
        return DynamoDBClient.getDynamoDBClient();
    }
}
