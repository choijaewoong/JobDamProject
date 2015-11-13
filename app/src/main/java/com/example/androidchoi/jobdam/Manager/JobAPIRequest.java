//package com.example.androidchoi.jobdam.Manager;
//
//import com.begentgroup.xmlparser.XMLParser;
//import com.example.androidchoi.jobdam.Model.JobList;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//
///**
// * Created by Choi on 2015-11-13.
// */
//public class JobAPIRequest extends NetworkRequest<JobList> {
//    @Override
//    public URL getURL() throws MalformedURLException {
//        String url = "http://api.saramin.co.kr/job-search?stock=kospi+kosdaq&sr=directhire&fields=posting-date+expiration-date+keyword-code+count&count=10";
//        return new URL(url);
//    }
//
//    @Override
//    public JobList parse(InputStream is) throws IOException {
//        XMLParser parser = new XMLParser();
//        JobList jobList = parser.fromXml(is, "jobs", JobList.class);
//        return jobList;
//    }
//}
