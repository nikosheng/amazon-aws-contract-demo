package amazon.aws.demo.amazonawscontractdemo.controller;

import amazon.aws.demo.amazonawscontractdemo.constants.Constants;
import amazon.aws.demo.amazonawscontractdemo.model.Contract;
import amazon.aws.demo.amazonawscontractdemo.s3.S3GetObject;
import amazon.aws.demo.amazonawscontractdemo.service.IDynamodbService;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: jiasfeng
 * @Date: 7/7/2018
 */
@Controller
@Slf4j
public class ContractController {
    @Autowired
    private IDynamodbService dynamodbService;

    @RequestMapping("/")
    public String greeting() {
        return "index";
    }

    @RequestMapping("/query/contract")
    @ResponseBody
    public List<Contract> query(@RequestBody Map<String, Object> payload) {
        String contractCode = (String) payload.get("contractCode");
        String upload1 = (String) payload.get("upload1");
        String upload2 = (String) payload.get("upload2");

        List<Contract> contracts = new ArrayList<>();
        if (!StringUtils.isNullOrEmpty(contractCode)) {
            contracts =
                    dynamodbService.query(Constants.TABLE_CONTRACTS, "contract_code", contractCode);
        } else if (!StringUtils.isNullOrEmpty(upload1) && !StringUtils.isNullOrEmpty(upload2)) {
            contracts =
                    dynamodbService.query(Constants.TABLE_CONTRACTS, "upload_date", upload1, upload2);
        }

        return contracts;
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String bucket,
                                               @RequestParam String key) {
        try {
            S3Object object = S3GetObject.download(Constants.REGION, bucket, key);
            if (object != null) {
                MediaType mediaType = MediaType.parseMediaType(object.getObjectMetadata().getContentType());
                // Fallback to the default content type if type could not be determined
                if (mediaType == null) {
                    mediaType = MediaType.APPLICATION_OCTET_STREAM;
                }
                S3ObjectInputStream objectInputStream = object.getObjectContent();

                byte[] bytes = IOUtils.toByteArray(objectInputStream);
                String fileName = URLEncoder.encode(key.substring(key.lastIndexOf("/") + 1), "UTF-8").replaceAll("\\+", "%20");

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(mediaType);
                httpHeaders.setContentLength(bytes.length);
                httpHeaders.setContentDispositionFormData("attachment", fileName);
                return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
