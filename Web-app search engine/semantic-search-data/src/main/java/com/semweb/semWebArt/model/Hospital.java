package com.semweb.semWebArt.model;

public class Hospital {


    String buildingNumber;
	String emergency;
	String operatorType;
	String osm_TimeStamp;//
	String ref_France_Finess;
	String lat_long;
	String city;
	String medicalSpecialty;
	String alternateName;
    String email;

    String name;
    String postalCode;
	String provider;
	String streetAddress;
	String telephone;


    public Hospital() {}
    public String getBuildingNumber() {
        return buildingNumber;
    }
    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }
    public String getEmergency() {
        return emergency;
    }
    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }
    public String getOperatorType() {
        return operatorType;
    }
    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }
    public String getOsm_TimeStamp() {
        return osm_TimeStamp;
    }
    public void setOsm_TimeStamp(String osm_TimeStamp) {
        this.osm_TimeStamp = osm_TimeStamp;
    }
    public String getRef_France_Finess() {
        return ref_France_Finess;
    }
    public void setRef_France_Finess(String ref_France_Finess) {
        this.ref_France_Finess = ref_France_Finess;
    }

    public String getLat_long() {
        return lat_long;
    }
    public void setLat_long(String lat_long) {
        this.lat_long = lat_long;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getMedicalSpecialty() {
        return medicalSpecialty;
    }
    public void setMedicalSpecialty(String medicalSpecialty) {
        this.medicalSpecialty = medicalSpecialty;
    }
    public String getAlternateName() {
        return alternateName;
    }
    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getProvider() {
        return provider;
    }
    public void setProvider(String provider) {
        this.provider = provider;
    }
    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


//    @Override
//    public String toString() {
//        return "Hospital{" +
//                "name='" + name + '\'' +
//                ", people='" + people + '\'' +
//                ", sex='" + sex + '\'' +
//                ", birth=" + birth +
//                '}';
//    }
}
