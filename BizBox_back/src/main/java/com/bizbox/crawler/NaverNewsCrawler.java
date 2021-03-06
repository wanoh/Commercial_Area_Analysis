package com.bizbox.crawler;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class NaverNewsCrawler {
	/**
	 * 네이버 부동산 뉴스 크롤링
	 * @return
	 */
	public String getNaverNews() {
		String URL = "https://land.naver.com/news/headline.nhn";
		Document doc = null;
		try {
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String baseUrl = "https://land.naver.com";
		Elements elem = doc.select("#content > div.section_headline > ul > li");

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (Element element : elem) {
			JSONObject temp = new JSONObject();
			String title = element.select("dl > dt:nth-child(2) > a").text();
			String url = baseUrl + element.select("dl > dt:nth-child(2) > a").attr("href");
			temp.put("title", title);
			temp.put("url", url);
			jsonArray.add(temp);
		}
		jsonObject.put("headlines", jsonArray);
		return jsonObject.toString();
	}
}
