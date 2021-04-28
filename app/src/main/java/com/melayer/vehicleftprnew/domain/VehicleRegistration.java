package com.melayer.vehicleftprnew.domain;

public class VehicleRegistration {
    private Long srNo;
    private String vehicleId;
    private String vehicleNo;
    private String psrName;
    private String cellNumber;
    private String startPlace;
    private String endPlace;
    private String licenceNo;
    private String licenceValidity;
    private String tdmAsmName;
    private String tdmAsm;
    private String distributor;
    private String entryDate;
    private String userId;

    public Long getSrNo() {
        return srNo;
    }

    public void setSrNo(Long srNo) {
        this.srNo = srNo;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getPsrName() {
        return psrName;
    }

    public void setPsrName(String psrName) {
        this.psrName = psrName;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getLicenceValidity() {
        return licenceValidity;
    }

    public void setLicenceValidity(String licenceValidity) {
        this.licenceValidity = licenceValidity;
    }

    public String getTdmAsmName() {
        return tdmAsmName;
    }

    public void setTdmAsmName(String tdmAsmName) {
        this.tdmAsmName = tdmAsmName;
    }

    public String getTdmAsm() {
        return tdmAsm;
    }

    public void setTdmAsm(String tdmAsm) {
        this.tdmAsm = tdmAsm;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "VehicleRegistration{" +
                "srNo=" + srNo +
                ", vehicleId='" + vehicleId + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", psrName='" + psrName + '\'' +
                ", cellNumber=" + cellNumber +
                ", startPlace='" + startPlace + '\'' +
                ", endPlace='" + endPlace + '\'' +
                ", licenceNo='" + licenceNo + '\'' +
                ", licenceValidity='" + licenceValidity + '\'' +
                ", tdmAsmName='" + tdmAsmName + '\'' +
                ", tdmAsm='" + tdmAsm + '\'' +
                ", distributor='" + distributor + '\'' +
                ", entryDate='" + entryDate + '\'' +
                ", userId=" + userId +
                '}';
    }
}
