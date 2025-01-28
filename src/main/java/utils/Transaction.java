package utils;

import java.util.Objects;

public class Transaction {
    private String sendingDate;
    private double amount;
    private double fees;
    private String receiverName;
    private String senderName;
    private String transactionNumber;

    
    public Transaction(String transactionNumber, double amount, double fees, String receiverName, String senderName, String sendingDate) {
        if (transactionNumber == null || transactionNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction number cannot be null or empty.");
        }
        this.transactionNumber = transactionNumber;
        this.amount = amount;
        this.fees = fees;
        this.receiverName = receiverName != null ? receiverName.trim() : "";
        this.senderName = senderName != null ? senderName.trim() : "";
        this.sendingDate = sendingDate != null ? sendingDate.trim() : "";
    }

    /**
     * Overloaded constructor for minimal transaction details.
     *
     * @param transactionNumber the unique identifier for the transaction.
     */
    public Transaction(String transactionNumber) {
        this(transactionNumber, 0.0, 0.0, "", "","");
    }

    // Getters
    public String getTransactionSendingDate() {
        return sendingDate;
    }
    
    public String getTransactionNumber() {
        return transactionNumber;
    }

    public double getAmount() {
        return amount;
    }

    public double getFees() {
        return fees;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    // Setters (optional)
    public void setTransactionNumber(String transactionNumber) {
        if (transactionNumber == null || transactionNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction number cannot be null or empty.");
        }
        this.transactionNumber = transactionNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName != null ? receiverName.trim() : "";
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName != null ? senderName.trim() : "";
    }

    /**
     * Provides a string representation of the Transaction object.
     *
     * @return a string with transaction details.
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionNumber='" + transactionNumber + '\'' +
                ", amount=" + amount +
                ", fees=" + fees +
                ", receiverName='" + receiverName + '\'' +
                ", senderName='" + senderName + '\'' +
                '}';
    }

    /**
     * Checks if two transactions are equal based on their attributes.
     *
     * @param o the object to compare.
     * @return true if transactions are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 &&
                Double.compare(that.fees, fees) == 0 &&
                transactionNumber.equals(that.transactionNumber) &&
                Objects.equals(receiverName, that.receiverName) &&
                Objects.equals(senderName, that.senderName);
    }

    /**
     * Generates a hash code for the Transaction object.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(transactionNumber, amount, fees, receiverName, senderName);
    }
}
