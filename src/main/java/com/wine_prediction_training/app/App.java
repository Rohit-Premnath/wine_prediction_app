package com.wine_prediction_training.app;

// /**
//  * Hello world!
//  */
// public class App {
//     public static void main(String[] args) {
//         System.out.println("Hello World!");
//     }
// }
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    public static final Logger logger = LoggerFactory.getLogger(App.class);

    private static final String ACCESS_KEY_ID = "ASIAQMMMF6R6KO6XNV2F";
    private static final String SECRET_KEY = "qp3rpOS+779RYsnGeajI8bxcAb7/vwgxXW+vsVXG";

    private static final String MASTER_URI = "local[*]";

    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
                .appName("Wine Quality Prediction App").master(MASTER_URI)
                .config("spark.executor.memory", "3g")
                .config("spark.driver.memory", "12g")
                .config("spark.jars.packages", "org.apache.hadoop:hadoop-aws:3.2.2")
                .getOrCreate();
        

        spark.sparkContext().hadoopConfiguration().set("fs.s3a.aws.credentials.provider",
                "com.amazonaws.auth.InstanceProfileCredentialsProvider,com.amazonaws.auth.DefaultAWSCredentialsProviderChain");
        spark.sparkContext().hadoopConfiguration().set("fs.s3a.access.key", ACCESS_KEY_ID);
        spark.sparkContext().hadoopConfiguration().set("fs.s3a.secret.key", SECRET_KEY);

        LogisticRegressionV2 parser = new LogisticRegressionV2();
        parser.predict(spark);

        spark.stop();
    }
}
