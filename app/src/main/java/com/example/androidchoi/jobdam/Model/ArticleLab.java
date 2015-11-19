package com.example.androidchoi.jobdam.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Choi on 2015-11-18.
 */
public class ArticleLab {
//    private static ArticleLab sArticleLab;
//    private Context mContext;

    @SerializedName("items")
    private ArrayList<Articles> mArticleList;
    public ArrayList<Articles> getArticleList() { return mArticleList; }

//    private ArticleLab(Context context){
//        mContext = context;
//        mArticleList = new ArrayList<Articles>();
////        Article article = new Article();
////        article.setContent("국민은행 합격했어요...!!!\n 잡담 덕분이에요..ㅜㅜ\n 감사해요!!!\n 잡담 짱짱!!!!!!!!!!!!\n국민은행 합격했어요...!!!\n" +
////                " 잡담 덕분이에요..ㅜㅜ\n" +
////                " 감사해요!!!\n" +
////                " 잡담 짱짱!!!!!!!!!!!!\n국민은행 합격했어요...!!!\n" +
////                " 잡담 덕분이에요..ㅜㅜ\n" +
////                " 감사해요!!!\n" +
////                " 잡담 짱짱!!!!!!!!!!!!\n국민은행 합격했어요...!!!\n" +
////                " 잡담 덕분이에요..ㅜㅜ\n" +
////                " 감사해요!!!\n" +
////                " 잡담 짱짱!!!!!!!!!!!!\n국민은행 합격했어요...!!!\n" +
////                " 잡담 덕분이에요..ㅜㅜ\n" +
////                " 감사해요!!!\n" +
////                " 잡담 짱짱!!!!!!!!!!!!\n국민은행 합격했어요...!!!\n" +
////                " 잡담 덕분이에요..ㅜㅜ\n" +
////                " 감사해요!!!\n" +
////                " 잡담 짱짱!!!!!!!!!!!!\n국민은행 합격했어요...!!!\n" +
////                " 잡담 덕분이에요..ㅜㅜ\n" +
////                " 감사해요!!!\n" +
////                " 잡담 짱짱!!!!!!!!!!!!\n국민은행 합격했어요...!!!\n" +
////                " 잡담 덕분이에요..ㅜㅜ\n" +
////                " 감사해요!!!\n" +
////                " 잡담 짱짱!!!!!!!!!!!!\n국민은행 합격했어요...!!!\n" +
////                " 잡담 덕분이에요..ㅜㅜ\n" +
////                " 감사해요!!!\n" +
////                " 잡담 짱짱!!!!!!!!!!!!\n국민은행 합격했어요...!!!\n" +
////                " 잡담 덕분이에요..ㅜㅜ\n" +
////                " 감사해요!!!\n" +
////                " 잡담 짱짱!!!!!!!!!!!!\n");
////        Articles articles = new Articles();
////        articles.setArticle(article);
////        for(int i= 0; i< 10; i++) {
////            mArticleList.add(articles);
////        }
//    }
//    private ArticleLab(Context context, ArrayList<Articles> articleList){
//        mContext = context;
//        mArticleList = articleList;
//    }
//    public static ArticleLab get(Context context){
//        if(sArticleLab == null){
//            sArticleLab = new ArticleLab(context.getApplicationContext());
//        }
//        return sArticleLab;
//    }
//
//    public static ArticleLab get(Context context, ArticleLab articleLab){
//        if(sArticleLab == null){
//            sArticleLab = new ArticleLab(context.getApplicationContext(), articleLab.getArticleList());
//        }
//        return sArticleLab;
//    }

    public void modifyArticleData(Article article){
        Articles articles = new Articles();
        articles.setArticle(article);
        for(int i = 0; i<mArticleList.size(); i++){
            if(mArticleList.get(i).getArticle().getId().equals(article.getId())){
                mArticleList.set(i, articles);
                return;
            }
        }
        mArticleList.add(0,articles);
    }
}
