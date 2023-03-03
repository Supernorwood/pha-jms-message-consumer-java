package com.pha.health.jms.consumer;

import java.io.File;
import java.util.Date;

import javax.jms.JMSException;

public class JMSQueueConsumerRunner {

    public static void main(String[] args) throws JMSException {

        System.out.println("I am the JMS Consumer!");

        System.out.println("trying to read from the target Queue");

        JMSQueueConsumer jmsQueueProducer = new JMSQueueConsumer("localhost", 61616, "PHA_FORM_A");

        Object someMessage = jmsQueueProducer.getMessage();

        Long rando = new Date().getTime();
        System.out.println("the object message: ");
        System.out.println(someMessage);

        File outputfile = new File("some-file" + rando + ".dat");
        System.out.println("output file: ");

        System.out.println(outputfile.getAbsolutePath());

        JMSQueueConsumerFileHelper.writeToFile(someMessage, outputfile);

    }
}
