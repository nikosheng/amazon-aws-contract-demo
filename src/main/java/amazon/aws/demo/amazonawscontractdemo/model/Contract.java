package amazon.aws.demo.amazonawscontractdemo.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @author: jiasfeng
 * @Date: 7/8/2018
 */
@Data
public class Contract {
    private String contractCode;
    private Date uploadDate;
    private String bucketName;
    private String objectKey;

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }
}
