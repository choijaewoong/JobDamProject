package com.example.androidchoi.jobdam.Model;

import com.begentgroup.xmlparser.SerializedName;

import java.io.Serializable;

/**
 * Created by Choi on 2015-11-05.
 */
public class JobContentData implements Serializable {
    private String title;
    private String location;
    @SerializedName("job-type")
    private String jobType;
    @SerializedName("job-category")
    private String jobCategory;
    @SerializedName("experience-level")
    private String experienceLevel;
    @SerializedName("required-education-level")
    private String educationLevel;
    private String keyword;
    private String salary;

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getJobType() {
        return jobType;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getSalary() {
        return salary;
    }
}
