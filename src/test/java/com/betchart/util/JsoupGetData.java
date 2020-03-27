package com.betchart.util;

import java.io.File;
import java.io.IOException;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupGetData {

	public static void main(String[] args) throws IOException, InterruptedException {
		try {
			File input = new File("wsc\\PremierLeagueSummary.html");
			Document document = Jsoup.parse(input, "UTF-8");
			
			Elements tableElems = document.getElementsByTag("table");
			for (Element el : tableElems) {
				if (el.className().equals("soccer")) {
					Elements rows = el.getElementsByTag("tr");
					for (Element row : rows) {
						//System.out.println(row.className());
						if (row.className().equals("event_round")) {
							System.out.println(row.getElementsByTag("td").get(0).text());
						} else if (row.classNames().contains("stage-finished")){
							Elements cells = row.getElementsByTag("td");
							System.out.println(row.id());
							System.out.println(cells.get(2).text() + " | " + cells.get(3).text() + " | " + cells.get(4).text());
						}
					}
					break;
				}
			}

		} catch (IOException e) {
			// TODO: handle exception
		}
	}
}
