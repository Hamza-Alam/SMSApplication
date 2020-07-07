package com.example.smsapplication;

public class CategoryModel {

    int id;
    String CategoryName;
    String NetworkNumber;

    public CategoryModel(int id, String categoryName, String networkNumber) {
        this.id = id;
        CategoryName = categoryName;
        NetworkNumber = networkNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getNetworkNumber() {
        return NetworkNumber;
    }

    public void setNetworkNumber(String networkNumber) {
        NetworkNumber = networkNumber;
    }
}
