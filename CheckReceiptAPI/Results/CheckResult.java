package CheckReceiptAPI.Results;

public class CheckResult extends Result {

    private boolean receiptExists;

    public boolean getReceiptExists() {

        return receiptExists;
    }

    public void setReceiptExists(boolean receiptExists) {

        this.receiptExists = receiptExists;
    }

    public CheckResult() {

    }
}
