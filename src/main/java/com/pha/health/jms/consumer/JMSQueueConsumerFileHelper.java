package com.pha.health.jms.consumer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JMSQueueConsumerFileHelper {

    /**
     * Writes data to an output file
     *
     * @param data Data to write to file
     * @param outputFile File to write data out to
     * @return boolean value representing file write status
     */
    public static boolean writeToFile(final Object data, final File outputFile) {

        if (data == null) {

            return false;

        } else if (outputFile == null) {

            return false;
        }

        try {

            BufferedWriter bufferedWriter;

            bufferedWriter = new BufferedWriter(new FileWriter(outputFile, true));
            bufferedWriter.write(data.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException ex) {

            return false;
        }

        return true;
    }

    private JMSQueueConsumerFileHelper() {
    }
}
