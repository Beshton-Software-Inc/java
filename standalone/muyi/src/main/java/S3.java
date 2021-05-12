

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;
public class S3 {
    public static void getBuckets() {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
        Bucket named_bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            System.out.println(b.getName());
        }
    }


    public static void main(String[] args) {
        getBuckets();
    }
}
