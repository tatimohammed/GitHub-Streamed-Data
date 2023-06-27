package com.example.githubproject.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.streaming.DataStreamWriter;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.Trigger;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    @Value("${spark.app.name}")
    private String appName;

    @Value("${spark.master}")
    private String masterUri;

    @Bean
    public SparkSession sparkSession(){
        return SparkSession.builder()
                .appName("KafkaStreamingApp")
                .master(masterUri)
                .getOrCreate();
    }

    @Bean
    public void consume() throws Exception{

        // Define the schema for the message format
        StructType schema = DataTypes.createStructType(new StructField[] {
                DataTypes.createStructField("name", DataTypes.StringType, true),
                DataTypes.createStructField("created_at", DataTypes.StringType, true),
                DataTypes.createStructField("stargazers_count", DataTypes.StringType, true),
                DataTypes.createStructField("watchers_count", DataTypes.StringType, true),
                DataTypes.createStructField("language", DataTypes.StringType, true),
                DataTypes.createStructField("forks", DataTypes.StringType, true)
        });

        // Read data from Kafka and decode it
        Dataset<Row> df = sparkSession().readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:29092")
                .option("subscribe", "github_stream1")
                .load();

        // Decode the value column using the defined schema
        Dataset<Row> decoded_df = df.select(
                functions.from_json(functions.col("value").cast("string"), schema).alias("data")
        );

        // Select the desired columns from the decoded data
        Dataset<Row> formatted_df = decoded_df.select(
                functions.col("data.name"),
                functions.col("data.created_at"),
                functions.col("data.stargazers_count"),
                functions.col("data.watchers_count"),
                functions.col("data.language"),
                functions.col("data.forks")
        ).withColumn("created_at", functions.substring(functions.col("created_at"), 1, 10));

        // Start the streaming query and specify the custom save function
        DataStreamWriter<Row> writer = formatted_df.writeStream()
                .format("csv")
                .option("path", "/home/tweetyx/csv_files")
                .option("checkpointLocation", "/home/tweetyx/stream/files")
                .option("append", "true")
                .option("header", "true");

        StreamingQuery query = writer.trigger(Trigger.ProcessingTime("10 seconds")).start();

    }
}
