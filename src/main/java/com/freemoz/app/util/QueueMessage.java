package com.freemoz.app.util;

public class QueueMessage {
    private int queueLocation;
    private long timeout;
    private String message;

    public QueueMessage(String message) {
        this.message = message;
    }

    /**
     * Used internally. Do not change this value.
     * @return
     */
    public int getQueueLocation() {
        return queueLocation;
    }

    /**
     * Used internally. Do not change this value;
     * @param queueLocation
     */
    public void setQueueLocation(int queueLocation) {
        this.queueLocation = queueLocation;
    }

    /**
     * Used internally. No not change this value.
     * @return
     */
    public long getTimeout() {
        return timeout;
    }

    /**
     * Used internally. Do not change this value.
     * @param timeout
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public QueueMessage clone() {
        QueueMessage clone = new QueueMessage(this.getMessage());
        clone.setTimeout(this.getTimeout());
        return clone;
    }

    public boolean areSame(QueueMessage queueMessage) {
        if(queueMessage == null) {
            return false;
        }

        if(queueMessage.getMessage().equals(this.getMessage()) &&
                queueMessage.getTimeout() == this.getTimeout()) {
            return true;
        }

        return false;
    }
}