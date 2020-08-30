package com.our_company.comon.libs.receive_receipt;

import com.google.gson.annotations.SerializedName;

public class MetaData {
    String id;
    ID _id;
    String fsId;
    String ofdId;
    String subtype;
    String kktRegId;
    int documentId;
    ReceiveDate receiveDate;
    String protocolVersion;
    int protocolSubversion;

    public static class ID {
        @SerializedName("$oid")
        String oid;

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }
    }

    public static class ReceiveDate {
        @SerializedName("$date")
        long date;

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }
    }

    //region Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ID get_id() {
        return _id;
    }

    public void set_id(ID _id) {
        this._id = _id;
    }

    public String getFsId() {
        return fsId;
    }

    public void setFsId(String fsId) {
        this.fsId = fsId;
    }

    public String getOfdId() {
        return ofdId;
    }

    public void setOfdId(String ofdId) {
        this.ofdId = ofdId;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getKktRegId() {
        return kktRegId;
    }

    public void setKktRegId(String kktRegId) {
        this.kktRegId = kktRegId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public ReceiveDate getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(ReceiveDate receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public int getProtocolSubversion() {
        return protocolSubversion;
    }

    public void setProtocolSubversion(int protocolSubversion) {
        this.protocolSubversion = protocolSubversion;
    }
    //endregion
}
