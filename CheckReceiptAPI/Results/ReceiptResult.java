package CheckReceiptAPI.Results;

import lombok.Data;

@Data
public class ReceiptResult extends Result{

    /**
     * Внутренняя информация о чеке
     */
    private Receipt receipt;

    public Receipt getReceipt() {

        return  receipt;
    }

    public void setReceipt(Receipt receipt) {

        this.receipt = receipt;
    }
}

