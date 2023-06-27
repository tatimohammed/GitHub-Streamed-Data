package com.example.githubproject.controller;

import com.example.githubproject.auth.GitHubEventsAPIClient;
import com.example.githubproject.database.GithubData;
import com.example.githubproject.service.GitHubDataServiceImpl;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;

import static org.apache.spark.sql.functions.*;

@CrossOrigin("*")
@RestController
public class Streamer {

    private GitHubDataServiceImpl gitService;

    private SparkSession sparkSession;

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public Streamer(GitHubDataServiceImpl gitService, KafkaTemplate<String, String> kafkaTemplate, SparkSession sparkSession){
        this.gitService = gitService;
        this.kafkaTemplate = kafkaTemplate;
        this.sparkSession = sparkSession;
    }

    @GetMapping("/stream_data")
    public Map<String, Object> stream_data() throws InterruptedException {
        stream();
        producer();
        Map<String, Object> result = consume();
        return result;
    }

    public void stream() throws InterruptedException {

        GitHubEventsAPIClient api = new GitHubEventsAPIClient();
        List<GithubData> data = api.repos(2);
        for (GithubData gitD: data) {
            this.gitService.addData(gitD);
        }

    }
    public void producer() {

        List<GithubData> repos = this.gitService.getRepos();
        List<String> docs = new ArrayList<>();
        for (GithubData repo : repos) {
            String datas = repo.getJsondata().toString().substring(1, repo.getJsondata().toString().length() - 1);
            datas = datas.replaceAll("=", "\":\"");
            datas = datas.replaceAll(", ", "\", \"");
            datas = datas.replaceAll("\\{", "{\"");
            datas = datas.replaceAll("\"\\{", "{");
            String[] data = datas.split("}\",");
            int count = 0;
            for (String s : data) {
                if (count == data.length - 1) {
                    docs.add(s.replace("}", "\"}"));
                } else {
                    docs.add(s + "\"}");
                }
                count++;
            }
        }

        for (String doc : docs) {
            this.kafkaTemplate.send("github_stream1", doc);
        }
    }

    public Map<String, Object> consume(){
        String directoryPath = "/home/tweetyx/csv_files/"; // Replace with your directory path

        String latestFile = getLatestCreatedCSVFile(directoryPath);
        System.out.println("===============================>>>"+latestFile);
        Dataset<Row> df = this.sparkSession.read()
                .format("csv")
                .option("header", "true")
                .load(latestFile);
        df = df.dropDuplicates().na().drop();

        Map<String, Object> result = new HashMap<>();
        Map<String, Long> languages = new HashMap<>();

        long repos = df.count();
        result.put("repos", repos);

        Dataset<Row> languagesDF = df.groupBy(col("language"))
                .agg(count("*").alias("count"))
                .orderBy(col("count").desc());

        languagesDF.collectAsList().forEach(row -> {
            String language = row.getString(0);
            long count = row.getLong(1);
            languages.put(language, count);
        });
        result.put("languages", languages);

        // Extract the month from the 'created_at' column
        df = df.withColumn("month", split(df.col("created_at"), "-").getItem(1));

        // Group the records by the month column and count the number of records in each group
        Dataset<Row> df_result = df.groupBy("month").count().orderBy("month");

        // Convert the DataFrame to a List<Row>
        List<Row> rows = df_result.collectAsList();

        // Create a Map<String, Long> to store the results
        Map<String, Long> months = new HashMap<>();

        // Iterate over the rows and populate the resultMap
        for (Row row : rows) {
            String month = row.getString(0);
            Long count = row.getLong(1);
            months.put(month, count);
        }
        result.put("months", months);

        // Select the 'language' column
        Dataset<Row> languageColumn = df.select("language");

        // Filter out null values and get the distinct languages
        Dataset<Row> uniqueLanguages = languageColumn.filter(languageColumn.col("language").isNotNull()).distinct();

        // Count the number of unique languages
        long uniqueLanguagesCount = uniqueLanguages.count();
        result.put("total_lang", uniqueLanguagesCount);

        return result;
    }

    public static String getLatestCreatedCSVFile(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        Arrays.sort(files, Comparator.comparingLong(File::lastModified));

        File lastFile = files[files.length - 1];
        return lastFile.getAbsolutePath();
    }

}
