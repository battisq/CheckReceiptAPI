package com.our_company.comon.libs.receive_receipt;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.our_company.comon.database.converters.DateConverter;
import com.our_company.comon.database.converters.ItemsConverter;
import com.our_company.comon.utilits.ArrayListForDatabase;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Непосредственно сам чек. В разных чеках по разному заполнены параметры.
 */
@Entity(primaryKeys = {"fiscalSign", "fiscalDocumentNumber", "fiscalNumber"})
public class Receipt implements Serializable {

    //region Money
    /**
     * Общая сумма по чеку, в копейках
     */
    private int totalSum;
    /**
     * Сумма, оплаченная наличными, в копейках
     */
    private int cashTotalSum;
    /**
     * Сумма, оплаченная безналичным способом оплаты, в копейках
     */
    private int ecashTotalSum;
    /**
     * Сумма НДС оплаченная по ставке 18%, в копейках
     */
    @SerializedName("nds18")
    private int totalNds18Sum;
    /**
     * Сумма НДС оплаченная по ставке 10%, в копейках
     */
    @SerializedName("nds10")
    private int totalNds10Sum;
    //endregion

    //region Cashbox
    /**
     * Фискальный признак документа, также известный как ФП, ФПД
     */
    private long fiscalSign;
    /**
     * Номер фискального документа
     */
    private int fiscalDocumentNumber;
    /**
     * Код чека
     */
    private int receiptCode;
    /**
     * Номер запроса
     */
    private int requestNumber;
    /**
     * Фискальный номер
     */
    @NonNull
    @SerializedName("fiscalDriveNumber")
    private String fiscalNumber;
    /**
     * Что-то вроде зашифрованной информации о чеке
     */
    private String rawData;
    /**
     * Номер смены
     */
    private int shiftNumber;
    /**
     * Регистрационный номер ККТ
     */
    private String kktRegId;
    //endregion

    //region Store
    /**
     * ИНН продавца
     */
    @SerializedName("userInn")
    private String retailInn;
    /**
     * Адрес точки продажи
     */
    private String retailPlaceAddress;
    /**
     * Наименование продавца/магазина
     */
    @SerializedName("user")
    private String storeName;
    /**
     * Данные кассира, который пробил чек
     */
    @SerializedName("operator")
    private String cashier;
    /**
     * Адрес электронной почты организации, отправившей информацию по чеку в ФНС
     */
    @SerializedName("senderAddress")
    private String senderEmailAddress;
    //endregion

    //region Operation
    /**
     * Тип операции. Полагаю продажа, покупка и т.д.
     */
    private int operationType;
    /**
     * Дата совершения операции
     */
    @TypeConverters({DateConverter.class})
    @SerializedName("dateTime")
    private Date receiptDateTime;

    /**
     * Товары/услуги, участвующие в операции
     */
    @TypeConverters({ItemsConverter.class})
    private List<Item> items;
    //endregion

    //region Other
    @Ignore
    @SerializedName("metadata")
    MetaData metaData;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    /**
     * Тип системы налогообложения
     */
    private int taxationType;

    @Ignore
    // Не понимаю что это
    private List<Object> stornoItems;
    @Ignore
    // Не понимаю что это
    private List<Object> modifiers;
    @Ignore
    // Не понимаю что это
    private List<Object> message;
    @Ignore
    // Не понимаю что это
    private List<Object> properties;
    //endregion

    // region added for convenience

    /**
     * Общая сумма по чеку, в копейках
     */
    private double subunitSum;
    /**
     * Сумма, оплаченная наличными, в копейках
     */
    private double cashSubunitSum;
    /**
     * Сумма, оплаченная безналичным способом оплаты, в копейках
     */
    private double ecashSubunitSum;
    /**
     * Сумма НДС оплаченная по ставке 18%, в копейках
     */
    private double subunitNds18Sum;
    /**
     * Сумма НДС оплаченная по ставке 10%, в копейках
     */
    private double subunitNds10Sum;

    // endregion

    // region Getters and Setters
    public void setSubunitSum(double subunitSum) {

        this.subunitSum = subunitSum;
    }

    public void setSubunitNds10Sum(double subunitNds10Sum) {

        this.subunitNds10Sum = subunitNds10Sum;
    }

    public void setSubunitNds18Sum(double subunitNds18Sum) {

        this.subunitNds18Sum = subunitNds18Sum;
    }

    public void setCashSubunitSum(double cashSubunitSum) {

        this.cashSubunitSum = cashSubunitSum;
    }

    public void setECashSubunitSum(double ecashSubunitSum) {

        this.ecashSubunitSum = ecashSubunitSum;
    }

    public int getTotalSum() {

        return totalSum;
    }

    public int getTotalNds10Sum() {

        return totalNds10Sum;
    }

    public int getTotalNds18Sum() {

        return totalNds18Sum;
    }

    public int getCashTotalSum() {

        return cashTotalSum;
    }

    public int getECashTotalSum() {

        return ecashTotalSum;
    }

    public List<Item> getItems() {

        return items;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    public void setCashTotalSum(int cashTotalSum) {
        this.cashTotalSum = cashTotalSum;
    }

    public int getEcashTotalSum() {
        return ecashTotalSum;
    }

    public void setEcashTotalSum(int ecashTotalSum) {
        this.ecashTotalSum = ecashTotalSum;
    }

    public void setTotalNds18Sum(int totalNds18Sum) {
        this.totalNds18Sum = totalNds18Sum;
    }

    public void setTotalNds10Sum(int totalNds10Sum) {
        this.totalNds10Sum = totalNds10Sum;
    }

    public long getFiscalSign() {
        return fiscalSign;
    }

    public void setFiscalSign(long fiscalSign) {
        this.fiscalSign = fiscalSign;
    }

    public int getFiscalDocumentNumber() {
        return fiscalDocumentNumber;
    }

    public void setFiscalDocumentNumber(int fiscalDocumentNumber) {
        this.fiscalDocumentNumber = fiscalDocumentNumber;
    }

    public int getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptCode(int receiptCode) {
        this.receiptCode = receiptCode;
    }

    public int getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getFiscalNumber() {
        return fiscalNumber;
    }

    public void setFiscalNumber(String fiscalNumber) {
        this.fiscalNumber = fiscalNumber;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public int getShiftNumber() {
        return shiftNumber;
    }

    public void setShiftNumber(int shiftNumber) {
        this.shiftNumber = shiftNumber;
    }

    public String getKktRegId() {
        return kktRegId;
    }

    public void setKktRegId(String kktRegId) {
        this.kktRegId = kktRegId;
    }

    public String getRetailInn() {
        return retailInn;
    }

    public void setRetailInn(String retailInn) {
        this.retailInn = retailInn;
    }

    public String getRetailPlaceAddress() {
        return retailPlaceAddress;
    }

    public void setRetailPlaceAddress(String retailPlaceAddress) {
        this.retailPlaceAddress = retailPlaceAddress;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public String getSenderEmailAddress() {
        return senderEmailAddress;
    }

    public void setSenderEmailAddress(String senderEmailAddress) {
        this.senderEmailAddress = senderEmailAddress;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public Date getReceiptDateTime() {
        return receiptDateTime;
    }

    public void setReceiptDateTime(Date receiptDateTime) {
        this.receiptDateTime = receiptDateTime;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getTaxationType() {
        return taxationType;
    }

    public void setTaxationType(int taxationType) {
        this.taxationType = taxationType;
    }

    public List<Object> getStornoItems() {
        return stornoItems;
    }

    public void setStornoItems(List<Object> stornoItems) {
        this.stornoItems = stornoItems;
    }

    public List<Object> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<Object> modifiers) {
        this.modifiers = modifiers;
    }

    public List<Object> getMessage() {
        return message;
    }

    public void setMessage(List<Object> message) {
        this.message = message;
    }

    public List<Object> getProperties() {
        return properties;
    }

    public void setProperties(List<Object> properties) {
        this.properties = properties;
    }

    public double getSubunitSum() {
        return subunitSum;
    }

    public double getCashSubunitSum() {
        return cashSubunitSum;
    }

    public double getEcashSubunitSum() {
        return ecashSubunitSum;
    }

    public void setEcashSubunitSum(double ecashSubunitSum) {
        this.ecashSubunitSum = ecashSubunitSum;
    }

    public double getSubunitNds18Sum() {
        return subunitNds18Sum;
    }

    public double getSubunitNds10Sum() {
        return subunitNds10Sum;
    }
    // endregion
}
