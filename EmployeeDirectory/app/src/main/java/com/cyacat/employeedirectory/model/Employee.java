package com.cyacat.employeedirectory.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Conrad Yacat on 7/15/2016.
 */
public class Employee {

    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String TITLE = "title";
    public static final String OFFICE_PHONE = "office_phone";
    public static final String MOBILE_PHONE = "mobile_phone";
    public static final String EMAIL = "email";
    public static final String PHOTO_FILENAME = "photo_filename";

    @DatabaseField(generatedId = true, columnName = ID)
    private int _id;

    @DatabaseField(columnName = FIRST_NAME)
    private String _firstName;

    @DatabaseField(columnName = LAST_NAME)
    private String _lastName;

    @DatabaseField(columnName = TITLE)
    private String _title;

    @DatabaseField(columnName = OFFICE_PHONE)
    private String _officePhone;

    @DatabaseField(columnName = MOBILE_PHONE)
    private String _mobilePhone;

    @DatabaseField(columnName = EMAIL)
    private String _email;

    @DatabaseField(columnName = PHOTO_FILENAME)
    private String _photoFileName;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_firstName() {
        return _firstName;
    }

    public void set_firstName(String _firstName) {
        this._firstName = _firstName;
    }

    public String get_lastName() {
        return _lastName;
    }

    public void set_lastName(String _lastName) {
        this._lastName = _lastName;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_officePhone() {
        return _officePhone;
    }

    public void set_officePhone(String _officePhone) {
        this._officePhone = _officePhone;
    }

    public String get_mobilePhone() {
        return _mobilePhone;
    }

    public void set_mobilePhone(String _mobilePhone) {
        this._mobilePhone = _mobilePhone;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_photoFileName() {
        return _photoFileName;
    }

    public void set_photoFileName(String _photoFileName) {
        this._photoFileName = _photoFileName;
    }
}
