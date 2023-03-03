package com.pha.health.jms.consumer;

import java.io.File;
import java.util.Date;

import javax.jms.JMSException;

public class JMSQueueConsumerRunner {

    public static void main(String[] args) throws JMSException {

        JMSQueueConsumer jmsQueueProducer = new JMSQueueConsumer("localhost", 61616, "PHA_FORM_A");

        Object messageFromQueue = jmsQueueProducer.getMessage();

        System.out.println("messageFromQueue: "+messageFromQueue);

        Long currentDateAndTime = new Date().getTime();

        File outputfile = new File("PHA_FORM_A" + "-validate-and-transform-pha-form-a-data-" + currentDateAndTime + ".dat");

        JMSQueueConsumerFileHelper.writeToFile(messageFromQueue, outputfile);

        System.out.println("full file" +outputfile.getAbsolutePath());
        System.out.println("exists: " +outputfile.exists());

    }
}
