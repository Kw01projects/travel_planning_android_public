package com.example.travel_plan.entities;

import java.util.Date;

public class BaseEntity {
    protected Date createdAt;
    protected Date updatedAt;
    public static final String CREATED_AT_FIELD = "createdAt";
    public static final String UPDATED_AT_FIELD = "updatedAt";

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
