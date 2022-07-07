package com.simar.peminjaman.Model;

import org.apache.logging.log4j.message.Message;
import org.springframework.validation.annotation.Validated;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Peminjaman {
    String archiveId;
    String loginName;

    @NotEmpty(message = "Address")
    String address;
    @NotEmpty(message = "Service Type")
    String serviceType;
    @NotEmpty(message = "Creator Phone")
    String creatorPhone;
    @NotEmpty(message = "Reason")
    String reason;
    public String getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(String archiveId) {
        this.archiveId = archiveId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCreatorPhone() {
        return creatorPhone;
    }

    public void setCreatorPhone(String creatorPhone) {
        this.creatorPhone = creatorPhone;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
