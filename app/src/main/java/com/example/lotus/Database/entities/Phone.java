package com.example.lotus.Database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.lotus.Database.LoginDatabase;

import java.util.Objects;

@Entity(tableName = LoginDatabase.PHONE_TABLE,
    foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userID",
        onDelete = ForeignKey.CASCADE))
public class Phone {
    @PrimaryKey(autoGenerate = true)
    private int phoneID;
    private int userID;
    private String model_name;
    private String brand;

    private String firmware_version;
    private String android_version;
    private String security_patch;
    private String build_number;
    private String kernel_version;
    private String baseband_version;


    public int getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(int phoneID) {
        this.phoneID = phoneID;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }



    public String getFirmware_version() {
        return firmware_version;
    }

    public void setFirmware_version(String firmware_version) {
        this.firmware_version = firmware_version;
    }

    public String getAndroid_version() {
        return android_version;
    }

    public void setAndroid_version(String android_version) {
        this.android_version = android_version;
    }

    public String getSecurity_patch() {
        return security_patch;
    }

    public void setSecurity_patch(String security_patch) {
        this.security_patch = security_patch;
    }

    public String getBuild_number() {
        return build_number;
    }

    public void setBuild_number(String build_number) {
        this.build_number = build_number;
    }

    public String getKernel_version() {
        return kernel_version;
    }

    public void setKernel_version(String kernel_version) {
        this.kernel_version = kernel_version;
    }

    public String getBaseband_version() {
        return baseband_version;
    }

    public void setBaseband_version(String baseband_version) {
        this.baseband_version = baseband_version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return phoneID == phone.phoneID && userID == phone.userID && Objects.equals(model_name, phone.model_name) && Objects.equals(brand, phone.brand) && Objects.equals(firmware_version, phone.firmware_version) && Objects.equals(android_version, phone.android_version) && Objects.equals(security_patch, phone.security_patch) && Objects.equals(build_number, phone.build_number) && Objects.equals(kernel_version, phone.kernel_version) && Objects.equals(baseband_version, phone.baseband_version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneID, userID, model_name, brand, firmware_version, android_version, security_patch, build_number, kernel_version, baseband_version);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}

