package com.betchart.scheduler;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

//@Component
public class GetGameData {

    private static final String CSV_FILE_PATH = "classpath:csv/E0.csv";

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${base.url}")
    private String baseUrl;

    private boolean isSync = false;

    //@Scheduled(fixedDelay = 1000)
    public void execute() throws IOException {

        if (isSync) {
            return;
        }

        final Resource fileResource = resourceLoader.getResource(CSV_FILE_PATH);
        String path = fileResource.getFile().getAbsolutePath();

        Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        boolean first = true;
        for (CSVRecord csvRecord : csvParser) {
            if (first) {
                first = false;
                continue;
            }

            // Accessing values by the names assigned to each column
            String date = csvRecord.get(1);
            String time = csvRecord.get(2);
            String homeTeam = csvRecord.get(3);
            String awayTeam = csvRecord.get(4);

            String homeGoalsFT = csvRecord.get(5);
            String awayGoalsFT = csvRecord.get(6);

            String homeGoalsHT = csvRecord.get(8);
            String awayGoalsHT = csvRecord.get(9);

            String tip1 = csvRecord.get(24);
            String tipX = csvRecord.get(25);
            String tip2 = csvRecord.get(26);


            //System.out.println("Record No - " + csvRecord.getRecordNumber());
            //System.out.println("---------------");
            System.out.println(date + "|" + time + "|" + homeTeam + "|" + awayTeam +
                    "|" + homeGoalsFT + ":" + awayGoalsFT +
                    " (" + homeGoalsHT + ":" + awayGoalsHT + ")" +
                    " - |" + tip1 + "|" + tipX + "|" + tip2 + "|");
            //System.out.println("---------------\n\n");
        }

        isSync = true;

        /*for (Game g : games) {
            //TODO: DAO.save
        }*/

    }
}
