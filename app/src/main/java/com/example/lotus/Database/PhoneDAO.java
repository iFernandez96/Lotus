package com.example.lotus.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.lotus.Database.entities.Phone;

import java.util.List;

@Dao
public interface PhoneDAO {
    @Delete
    void delete(Phone phone);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Phone... phone);

    @Query("SELECT * FROM " + LoginDatabase.PHONE_TABLE + " ORDER BY phoneID")
    List<Phone> getAllPhones();

    @Query("DELETE from " + LoginDatabase.PHONE_TABLE + " WHERE phoneID = :phoneID")
    void deletePhone(int phoneID);

    @Query("DELETE from " + LoginDatabase.PHONE_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + LoginDatabase.PHONE_TABLE + " WHERE phoneID = :phoneID")
    Phone getPhoneByID(int phoneID);
    @Query("SELECT * FROM " + LoginDatabase.PHONE_TABLE + " WHERE userID = :userID")
    Phone getPhoneByUserID(int userID);

    // get the model name from the users phone
    @Query("SELECT model_name FROM " + LoginDatabase.PHONE_TABLE + " WHERE userID = :userID")
    String getModelName(int userID);
    // get the brand of the users phone
    @Query("SELECT brand FROM " + LoginDatabase.PHONE_TABLE + " WHERE userID = :userID")
    String getBrand(int userID);
    // get the firmware version of the users phone
    @Query("SELECT firmware_version FROM " + LoginDatabase.PHONE_TABLE + " WHERE userID = :userID")
    String getFirmwareVersion(int userID);
    // get the android version of the users phone
    @Query("SELECT android_version FROM " + LoginDatabase.PHONE_TABLE + " WHERE userID = :userID")
    String getAndroidVersion(int userID);
    // get the security patch of the users phone
    @Query("SELECT security_patch FROM " + LoginDatabase.PHONE_TABLE + " WHERE userID = :userID")
    String getSecurityPatch(int userID);
    // get the build number of the users phone
    @Query("SELECT build_number FROM " + LoginDatabase.PHONE_TABLE + " WHERE userID = :userID")
    String getBuildNumber(int userID);
    // get the kernel version of the users phone
    @Query("SELECT kernel_version FROM " + LoginDatabase.PHONE_TABLE + " WHERE userID = :userID")
    String getKernelVersion(int userID);
    // get the baseband version of the users phone
    @Query("SELECT baseband_version FROM " + LoginDatabase.PHONE_TABLE + " WHERE userID = :userID")
    String getBasebandVersion(int userID);
    // get the cpu of the users phone

    // Update the phone database with model name using userID
    @Query("UPDATE " + LoginDatabase.PHONE_TABLE + " SET model_name = :model_name WHERE userID = :userID")
    void insertModelName(String model_name, int userID);
    // Update the phone database with brand using userID
    @Query("UPDATE " + LoginDatabase.PHONE_TABLE + " SET brand = :brand WHERE userID = :userID")
    void insertBrand(String brand, int userID);
    // Update the phone database with firmware version using userID
    @Query("UPDATE " + LoginDatabase.PHONE_TABLE + " SET firmware_version = :firmware_version WHERE userID = :userID")
    void insertFirmwareVersion(String firmware_version, int userID);
    // Update the phone database with android version using userID
    @Query("UPDATE " + LoginDatabase.PHONE_TABLE + " SET android_version = :android_version WHERE userID = :userID")
    void insertAndroidVersion(String android_version, int userID);
    // Update the phone database with security patch using userID
    @Query("UPDATE " + LoginDatabase.PHONE_TABLE + " SET security_patch = :security_patch WHERE userID = :userID")
    void insertSecurityPatch(String security_patch, int userID);
    // Update the phone database with build number using userID
    @Query("UPDATE " + LoginDatabase.PHONE_TABLE + " SET build_number = :build_number WHERE userID = :userID")
    void insertBuildNumber(String build_number, int userID);
    // Update the phone database with kernel version using userID
    @Query("UPDATE " + LoginDatabase.PHONE_TABLE + " SET kernel_version = :kernel_version WHERE userID = :userID")
    void insertKernelVersion(String kernel_version, int userID);
    // Update the phone database with baseband version using userID
    @Query("UPDATE " + LoginDatabase.PHONE_TABLE + " SET baseband_version = :baseband_version WHERE userID = :userID")
    void insertBasebandVersion(String baseband_version, int userID);
    // Update the phone database with cpu using userID


    @Transaction
    public default void updatePhoneDetails(Phone phone) {
        updatePhoneDetails(phone.getModel_name(), phone.getBrand(), phone.getFirmware_version(),
                phone.getAndroid_version(), phone.getSecurity_patch(), phone.getBuild_number(),
                phone.getKernel_version(), phone.getBaseband_version(), phone.getUserID());
    }

    @Query("UPDATE " + LoginDatabase.PHONE_TABLE +
            " SET model_name = :model_name, brand = :brand, firmware_version = :firmware_version, " +
            "android_version = :android_version, security_patch = :security_patch, " +
            "build_number = :build_number, kernel_version = :kernel_version, " +
            "baseband_version = :baseband_version WHERE userID = :userID")
    void updatePhoneDetails(String model_name, String brand, String firmware_version,
                            String android_version, String security_patch, String build_number,
                            String kernel_version, String baseband_version, int userID);

}
