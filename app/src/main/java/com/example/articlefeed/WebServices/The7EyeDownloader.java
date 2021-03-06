package com.example.articlefeed.WebServices;

import android.os.AsyncTask;
import android.util.Log;

import com.example.articlefeed.Entities.ArticleItem;
import com.example.articlefeed.Utilities.DateParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class The7EyeDownloader  extends AsyncTask<String,Void,ArrayList<ArticleItem>> {

    private ArrayList<ArticleItem> articles;

    public The7EyeDownloader(ArrayList<ArticleItem> articles) {
        this.articles = articles;
    }
    //private ArrayList<ArticleItem> refreshedArticles = new ArrayList<>();


    @Override
    protected ArrayList<ArticleItem> doInBackground(String... strings) {

        //selectors to get articles on webpage.
        String articleSelectorTop = "#main-headlines-wrapper > section > div.cols4-content > article";
        String articleSelectorBot = "#main-headlines-wrapper > section > div.hpitems > div > article";

        String title;
        String body;
        String date;
        String imageURL;
        String linkURL;
        try{
            String url = strings[0];
            Document page = Jsoup.connect(url).get();

            //top articles.

            Elements articleElements = page.select(articleSelectorTop);
            for (Element e : articleElements)
            {
                title = e.select("div.item-body.has-image > header > hgroup > h1 > a").text();
                body = e.select("div.item-body.has-image > header > hgroup > h2 > a").text();

                String rawDate = e.select("div.item-body.has-image > div.under-headline > span.date").text();
                date = "";
                date += rawDate.substring(0,2);
                date += " ";
                date += DateParser.NumberMonthToHebrew(rawDate.substring(3,5));
                date = date.replace("0","");
                Log.d("date",rawDate.substring(3,5));

                imageURL = e.select("div.item-media > a > img").attr("src");
                linkURL = e.select(" div.item-media > a").attr("href");

                articles.add(new ArticleItem(title,body,date,imageURL,linkURL));
            }

            //bottom articles.

            articleElements = page.select(articleSelectorBot);
            for (Element e : articleElements)
            {
                title = e.select("div.item-body.has-image > header > hgroup > h1 > a").text();
                body = e.select("div.item-body.has-image > header > hgroup > h2 > a").text();

                String rawDate = e.select("div.item-body.has-image > div.under-headline > span.date").text();
                date = "";
                date += rawDate.substring(0,2);
                date += " ";
                date += DateParser.NumberMonthToHebrew(rawDate.substring(3,5));
                date = date.replace("0","");
                Log.d("date",rawDate.substring(3,5));

                imageURL = e.select("div.item-media > a > img").attr("src");
                linkURL = e.select(" div.item-media > a").attr("href");

                articles.add(new ArticleItem(title,body,date,imageURL,linkURL));
            }


            return articles;

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return articles;
    }
}
