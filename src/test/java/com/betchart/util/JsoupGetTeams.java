package com.betchart.util;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupGetTeams {
	
	public static void main(String[] args) throws IOException {
		
		//getTeamData();
		getLeagueData();
	}
	
	//list of teams (id, name)
	private static void getLeagueData() throws IOException {
		File input = new File("wsc\\PremierLeagueSummary.html");
		Document document = Jsoup.parse(input, "UTF-8");
		
		//standings-15151-content
		Element tbody = document.getElementById("standings-15151-content");
		Elements rows = tbody.getElementsByTag("tr");
		for (Element row : rows) {
			Element cell = row.getElementsByClass("team").get(0);
			Element link = cell.getElementsByTag("a").get(0);
			System.out.println(row.attr("data-team-id") + " - " + link.text());
		}
	}
	
	private static void getTeamData() throws IOException {
		String path = "wsc\\teams\\";
		Integer teamId = 167;
		String teamName = "Manchester-City";
		File input = new File(path + teamId + "-" + teamName.replace(" ", "-") + ".html");
		Document document = Jsoup.parse(input, "UTF-8");
		Element table = document.getElementById("top-player-stats-summary-grid");
		Element tbody = table.getElementsByTag("tbody").get(0);
		Elements rows = tbody.getElementsByTag("tr");
		for (Element row : rows) {
			Elements cells = row.getElementsByTag("td");
			Element aCell = cells.get(2);
			Element link = aCell.getElementsByTag("a").get(0);
			String[] hrefArr = link.attr("href").split("/");
			System.out.println(hrefArr[2] + " - " + hrefArr[4].replace("-", " "));
		}
	}
}
