//package com.example.androidchoi.jobdam.Manager;
//
//import com.example.androidchoi.jobdam.Model.MyJobList;
//import com.google.gson.Gson;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
///**
// * Created by Choi on 2015-11-13.
// */
//public class MyJobRequest extends NetworkRequest<MyJobList>{
//
//    String mUserName;
//    private String showMyJobUrl = "http://52.69.235.46:3000/showmyscrap/%s";
//
//    public MyJobRequest(String userName){
//        mUserName = userName;
//    }
//
//    @Override
//    public URL getURL() throws MalformedURLException {
//        String url = String.format(showMyJobUrl, mUserName);
//        return new URL(url);
//    }
//
//    @Override
//    public MyJobList parse(InputStream is) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = br.readLine()) != null) {
//            sb.append(line).append("\n\r");
//        }
//
//        if(sb.toString() != null){
////            Toast.makeText(MyApplication.getContext(),"dddddd",Toast.LENGTH_SHORT).show();
//            Gson gson = new Gson();
//            MyJobList.get(getContext(), gson.fromJson(sb.toString(), MyJobList.class));
//        }else {
////            Toast.makeText(MyApplication.getContext(),"aaaaa",Toast.LENGTH_SHORT).show();
//            MyJobList.get(getContext());
//        }
////        Gson gson = new Gson();
////        MyJobList result = gson.fromJson(sb.toString(), MyJobList.class);
//        return MyJobList.get(getContext());
//    }
//
//    @Override
//    public void setRequestHeader(HttpURLConnection conn) {
//        super.setRequestHeader(conn);
//        conn.setRequestProperty("Accept", "application/json");
//    }
//}
