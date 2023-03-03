package org.example.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.LoggerFactory;

public class JMSQueueConsumer extends JMSQueueProducerConsumer {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(JMSQueueConsumer.class);

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
        return getMessage(DEFAULT_AMOUNT_OF_TIME_TO_WAIT_FOR_MESSAGE_IN_MILLISECONDS);
    }

    /**
     * Loads a message from the target JMS Queue given a timeout period
     *
     * @param timeout How long to wait for a message before giving up
     * @return Next message from the JMS Queue. Returns {@code null} if no
     * message was be loaded
     * @throws JMSException On JMS Queue error
     */
    public Object getMessage(final long timeout) throws JMSException {
        if (logger.isDebugEnabled()) {
            logger.debug("enter :: getMessage()");
            logger.debug("param - connection: {}", this.connectionString);
            logger.debug("param - queue:      {}", targetJMSQueue);
            logger.debug("param - timeout:    {}", timeout);
        }

        Connection connection = null;
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.connectionString);
        connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(this.targetJMSQueue);
        MessageConsumer consumer = session.createConsumer(queue);

        Object objectFromTheQueue = consumer.receive(timeout);

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

        if (logger.isDebugEnabled()) {
            logger.debug("returning: {}", parsedObject);
            logger.debug("exit :: getMessage()");
        }
        return parsedObject;
    }

    /**
     * Clear all message from the target JMS Queue. This method is needed as
     * there does not appear to be a direct way to delete the Queue itself
     *
     * @return number of messages purged/deleted
     * @throws JMSException on JMS Queue error
     */
    public int clearMessages() throws JMSException {
        if (logger.isDebugEnabled()) {
            logger.debug("enter :: clearMessages()");
            logger.debug("param - queue: {}", this.targetJMSQueue);
        }

        int numberOfMessagesRemovedFromQueue = 0;

        while (true) {

            if (logger.isDebugEnabled()) {
                logger.debug("grabbing next message from the queue");
            }

            Object messageLoadedFromQueue = getMessage(1);

            if (messageLoadedFromQueue == null) {

                if (logger.isDebugEnabled()) {
                    logger.debug("last message fetched was null. this means the queue is empty. returning.");
                }

                break;

            } else {

                if (logger.isDebugEnabled()) {
                    logger.debug("loaded message from queue: {}", messageLoadedFromQueue);
                }

                numberOfMessagesRemovedFromQueue++;

                if (logger.isDebugEnabled()) {
                    logger.debug("total messages purged so far: {}", numberOfMessagesRemovedFromQueue);
                }

            }

        }

        if (logger.isDebugEnabled()) {
            logger.debug("returning: {}", numberOfMessagesRemovedFromQueue);
            logger.debug("exit :: clearMessages()");
        }
        return numberOfMessagesRemovedFromQueue;
    }

}
