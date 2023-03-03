package org.example.consumer;

import java.io.File;
import java.util.Date;

import javax.jms.JMSException;

public class Main {

    public static void main(String[] args) throws JMSException {
        System.out.println("I am the JMS Producer !");

        System.out.println("trying t0o write to the queue");

        JMSQueueConsumer jmsQueueProducer = new JMSQueueConsumer("localhost", 61616, "PHA_FORM_A");
        Object someMessage = jmsQueueProducer.getMessage();

        Long rando = new Date().getTime();
        System.out.println("the object message: ");
        System.out.println(someMessage);
        File outputfile = new File("some-file" + rando + ".dat");
        System.out.println("output file: ");
        System.out.println(outputfile.getAbsolutePath());
        FileUtil.writeToFile(someMessage, outputfile, FileUtil.APPEND.DONT_APPEND);

        System.out.println("exists? " + outputfile.exists());
        /*
        
PHA Form A Challange

Part 1:
	- Create an endpoint (POST) to receive sample PHA Form A
	- Unmarshall and parse the JSON doc
	- Extract demographic data (Name, Address, Phone, Email, DOB, DODID, Gender, Service, Rank)
	- Design a new JSON doc called PhaPersonInfo.  Have seperate subsections for personal data, address data,
	  contact data, service data.
	- Return new JSON doc as response (HTTP 200).
	- Add some basic validation. Come up w/ meaningful validation messages for a few required fields:
		- input message itself, required First and Last Names, required DOB, DODID, Email
	- Use standard libs (Spring Boot, Jackson)
	
Part 2:
	- [done but reverted] Modify Part 1. Instead of returning new JSON doc in a response, just return HTTP 200 if message validates and is
	  successfully parsed and transformed.
	- [done] Download and install Active MQ.
	- [done] Add a message producer to send new JSON doc to a queue in AMQ.
        - [done] Call the queue PHA_FORM_A.
        
        
	- [done] Write a message consumer (seperate project and WAR) to retrieve the message from the queue
        - [done] and write it out to a local file.
         */
    }
}

/*
// up next
    
    // add the existing jms code into the rest server ecosystem
    rework and reword the code'15\'
            comment and explain the code [30]
                    push each of the projects to git[15]
                            get each of the projects on the main branch [15]
                                    save the postman files to a repo for testing [optional][15]
    
    things to do next
            
            export everything into a war [0]
                    and run the war in an application server?: [idk]
 */
