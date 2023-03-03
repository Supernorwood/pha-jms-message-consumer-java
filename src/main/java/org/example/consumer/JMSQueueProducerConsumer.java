package org.example.consumer;

import org.slf4j.LoggerFactory;

/**
 * Abstract JMS Queue ProducerConsumer
 *
 */
public abstract class JMSQueueProducerConsumer {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JMSQueueProducerConsumer.class);

    /**
     * JMS Server hostname or IP
     */
    protected final String targetServer;

    /**
     * Port for JMS application on target queue
     */
    protected final Integer port;
    /**
     * Name of the target JMS Queue
     */
    protected final String targetJMSQueue;

    /**
     * Connection string to the target server
     */
    protected final String connectionString;

    /**
     * Initialize a JMSQueueProducerConsumer given a target server, port and JMS
     * Queue name
     *
     * @param targetServer Server hosting the JMS queue application
     * @param port port the JMS Queue application is listening on
     * @param targetJMSQueue target JMS Queue name
     */
    public JMSQueueProducerConsumer(final String targetServer, final int port, final String targetJMSQueue) {
        this.targetServer = targetServer;
        this.port = port;
        this.targetJMSQueue = targetJMSQueue;
        this.connectionString = this.buildConnectionString();

        if (logger.isDebugEnabled()) {
            logger.debug("the created JMSQueueProducerConsumer: {}", this);
        }

    }

    /**
     * Build the connecting string for connecting to the target JMS server
     */
    protected String buildConnectionString() {
        return String.format("tcp://%s:%d", this.targetServer, this.port);
    }

    /**
     * Target server this ProducerConsumer will operate on
     */
    public String getTargetServer() {
        return targetServer;
    }

    /**
     * Listening port for the target server
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Get the name of the target JMS Queue for this ProducerConsumer
     *
     * @return target JMS Queue name
     */
    public String getTargetJMSQueue() {
        return targetJMSQueue;
    }

    /**
     * Get the connection string for connecting to the target JMS server
     *
     * @return created server connection string
     */
    public String getConnectionString() {
        return connectionString;
    }

    @Override
    public String toString() {
        return "JMSQueueProducerConsumer{" + "targetServer=" + targetServer + ", port=" + port + ", targetJMSQueue=" + targetJMSQueue + ", connectionString=" + connectionString + '}';
    }

}
