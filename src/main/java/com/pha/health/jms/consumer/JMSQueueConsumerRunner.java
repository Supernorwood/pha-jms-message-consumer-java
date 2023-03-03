package com.pha.health.jms.consumer;

import java.io.File;
import java.util.Date;

import javax.jms.JMSException;

/**
 * loads a message from the ActiveMQ queue and sends it to a local file

 */
public class JMSQueueConsumerRunner {

    public static void main(String[] args) throws JMSException {

        JMSQueueConsumer jmsQueueProducer = new JMSQueueConsumer("localhost", 61616, "PHA_FORM_A");

        Object messageFromQueue = jmsQueueProducer.getMessage();

        System.out.println("messageFromQueue: " + messageFromQueue);

        Long currentDateAndTime = new Date().getTime();

        // generate a time stamped file
        File outputfile = new File("PHA_FORM_A" + "-validate-and-transform-pha-form-a-data-" + currentDateAndTime + ".dat");

        //write the content to the destintaion file
        JMSQueueConsumerFileHelper.writeToFile(messageFromQueue, outputfile);

        //verify the file operations took place
        //System.out.println("full file" + outputfile.getAbsolutePath());
        //System.out.println("exists: " + outputfile.exists());
    }
}
