package com.pha.health.jms.consumer;

import com.pha.health.jms.worker.JMSQueueWorker;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

public class JMSQueueConsumer extends JMSQueueWorker {

    /**
     * Initialize a JMS Queue consumer given a target JMS Queue name
     *
     * @param targetJMSQueue target JMS Queue name
     */
    public JMSQueueConsumer(final String targetServer, final int port, final String targetJMSQueue) {
        super(targetServer, port, targetJMSQueue);
    }

    /**
     * Build the connecting string for connecting to the target JMS server
     */
    @Override
    protected String buildConnectionString() {
        return super.buildConnectionString() + "?jms.prefetchPolicy.all=0";
    }
    /**
     * Default time to wait for a message before giving up. Used when no wait
     * period specified.
     */
    private final Integer DEFAULT_AMOUNT_OF_TIME_TO_WAIT_FOR_MESSAGE_IN_MILLISECONDS = 5 * 1000;

    /**
     * Loads a message from the target JMS Queue
     *
     * @return Next message from JMS Queue. Returns {@code null} if no message
     * can be loaded
     * @throws JMSException On JMS Queue error
     */
    public Object getMessage() throws JMSException {

        Connection connection = null;
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.connectionString);
        connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(this.targetJMSQueue);
        MessageConsumer consumer = session.createConsumer(queue);

        Object objectFromTheQueue = consumer.receive(DEFAULT_AMOUNT_OF_TIME_TO_WAIT_FOR_MESSAGE_IN_MILLISECONDS);

        Object parsedObject;

        if (objectFromTheQueue instanceof ActiveMQTextMessage) {

            parsedObject = ((ActiveMQTextMessage) objectFromTheQueue).getText();

        } else {

            if (objectFromTheQueue != null) {
                ObjectMessage loadedMessageFromQueue = (ObjectMessage) objectFromTheQueue;
                parsedObject = loadedMessageFromQueue.getObject();
            } else {
                parsedObject = null;
            }

        }

        objectFromTheQueue = null;

        consumer.close();
        consumer = null;
        queue = null;
        session.close();
        session = null;

        connection.close();
        connection = null;
        connectionFactory = null;

        return parsedObject;
    }

}
