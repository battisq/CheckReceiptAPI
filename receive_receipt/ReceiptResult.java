package com.our_company.comon.libs.receive_receipt;

public class ReceiptResult {

    /**
     * Внутренняя информация о чеке
     */

    boolean isSuccess;
    int resultCode;
    String message;
    Receipt receipt;

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if (message != null && message.compareTo("") != 0 && this.message.compareTo(message) != 0)
            this.message = message;
    }
}

