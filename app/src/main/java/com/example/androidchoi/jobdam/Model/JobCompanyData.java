package com.example.androidchoi.jobdam.Model;

import java.io.Serializable;

/**
 * Created by Choi on 2015-11-05.
 */
public class JobCompanyData implements Serializable {
    private JobCompanyNameData name;
    public JobCompanyNameData getName() {
        return name;
    }
    public JobCompanyData(){ name = new JobCompanyNameData();}
}
