package com.melayer.vehicleftprnew.domain;

import java.io.File;

/**
 * Created by root on 24/8/16.
 */
public class Module {
    private String vehicleId;
    private String vehicleNo;
    private String unit;
    private String checkPointName;
    private String remarks;
    private Integer checkPointId;
    private String checkPointStatus;
    private String finalInspectionStatus;
    private File image;
    private String entryDate;
    private Integer userId;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Integer checkPointId) {
        this.checkPointId = checkPointId;
    }

    public String getCheckPointStatus() {
        return checkPointStatus;
    }

    public void setCheckPointStatus(String checkPointStatus) {
        this.checkPointStatus = checkPointStatus;
    }

    public String getFinalInspectionStatus() {
        return finalInspectionStatus;
    }

    public void setFinalInspectionStatus(String finalInspectionStatus) {
        this.finalInspectionStatus = finalInspectionStatus;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Module{" +
                "vehicleId='" + vehicleId + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", unit='" + unit + '\'' +
                ", checkPointName='" + checkPointName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", checkPointId=" + checkPointId +
                ", checkPointStatus='" + checkPointStatus + '\'' +
                ", finalInspectionStatus='" + finalInspectionStatus + '\'' +
                ", image='" + image + '\'' +
                ", entryDate='" + entryDate + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
