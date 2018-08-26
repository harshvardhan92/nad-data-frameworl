package com.techm.dataparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLToTSV {

	private void createTsvFile(String htmlFilePath, String tsvFilePath) throws IOException {
		StringBuilder bodyHtml = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(new File(htmlFilePath)));
		String line = br.readLine();
		while (line != null) {
			bodyHtml.append(line);
			line = br.readLine();
		}
		try {
			FileWriter writer = new FileWriter(tsvFilePath);

			Document doc = Jsoup.parseBodyFragment(bodyHtml.toString());
			Elements rows = doc.getElementsByTag("tr");

			for (Element row : rows) {
				Elements cells = row.getElementsByTag("td");
				for (Element cell : cells) {
					writer.write(cell.text().concat("\t"));
				}
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.out.println("Provide arguments as");
			System.out.println("1. HTML File Path  2. TSV File Path\n");
			System.exit(1);
		}
		String htmlFilePath = args[0];
		String tsvFilePath = args[1];
		new HTMLToTSV().createTsvFile(htmlFilePath, tsvFilePath);
	}

}
