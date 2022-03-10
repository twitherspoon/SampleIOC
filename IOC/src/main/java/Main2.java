
import org.apache.commons.lang.SystemUtils;

//import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
//import software.amazon.awssdk.services.s3.waiters.S3Waiter;

public class Main2 {
	
	public static void main(String[] args) {
		
		
		 Region region = Region.US_WEST_2;
	        S3Client s3 = S3Client.builder()
	                .region(region)
	                .build();

	        performOperations(s3) ;
		
		System.out.println();
	}
	
	public static void performOperations(S3Client s3) {
		
       

        // snippet-start:[s3.java2.s3_bucket_ops.list_bucket]
        // List buckets
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3.listBuckets(listBucketsRequest);
        listBucketsResponse.buckets().stream().forEach(x -> System.out.println(x.name()));
        // snippet-end:[s3.java2.s3_bucket_ops.list_bucket]

        
        s3.close();
        // snippet-end:[s3.java2.s3_bucket_ops.delete_bucket] 
    }

}
