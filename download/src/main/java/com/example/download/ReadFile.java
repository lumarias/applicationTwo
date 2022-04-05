package com.example.download;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ReadFile {
    public String QUEUE_NAME = "newSQS";

    public void read() throws IOException {
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/159205842436/newSQS";

        CSVReader reader = new CSVReader(new FileReader("C:\\ejercicio\\download\\download\\ejemplo.csv"));
        List<String[]> li=reader.readAll();
        System.out.println("Total de filas "+li.size());
        Iterator<String[]> i1= li.iterator();
             while(i1.hasNext()){

                String[] str=i1.next();

                for(int i=0;i<str.length;i++)
                {
                    SendMessageRequest send_msg_request = new SendMessageRequest()
                            .withQueueUrl(queueUrl)
                            .withMessageBody(" "+str[i])
                            .withDelaySeconds(5);
                    sqs.sendMessage(send_msg_request);
                //System.out.print(" "+str[i]);

                }
               }

    }

}
