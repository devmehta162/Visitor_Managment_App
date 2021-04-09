package com.global.visitormanagment.model;

public class VisitorModel {
    private int id;
    private String name;
    private String phone;
    private String visitor_id;
    private int no_of_person;
    private String purpose;
    private String date;
    private String in_time;
    private String out_time;
    private String file;
    private int active_status;
    private String created_at;
    private String updated_at;
    private int created_by;
    private int updated_by;
    private int school_id;
    private int academic_id;


    public VisitorModel() {
    }

    public VisitorModel(int id, String name, String phone, String visitor_id, int no_of_person, String purpose, String date, String in_time, String out_time, String file, int active_status, String created_at, String updated_at, int created_by, int updated_by, int school_id, int academic_id) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.visitor_id = visitor_id;
        this.no_of_person = no_of_person;
        this.purpose = purpose;
        this.date = date;
        this.in_time = in_time;
        this.out_time = out_time;
        this.file = file;
        this.active_status = active_status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.created_by = created_by;
        this.updated_by = updated_by;
        this.school_id = school_id;
        this.academic_id = academic_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVisitor_id() {
        return visitor_id;
    }

    public void setVisitor_id(String visitor_id) {
        this.visitor_id = visitor_id;
    }

    public int getNo_of_person() {
        return no_of_person;
    }

    public void setNo_of_person(int no_of_person) {
        this.no_of_person = no_of_person;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getActive_status() {
        return active_status;
    }

    public void setActive_status(int active_status) {
        this.active_status = active_status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(int updated_by) {
        this.updated_by = updated_by;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public int getAcademic_id() {
        return academic_id;
    }

    public void setAcademic_id(int academic_id) {
        this.academic_id = academic_id;
    }
}
