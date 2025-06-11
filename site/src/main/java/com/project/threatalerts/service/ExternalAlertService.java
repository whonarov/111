package com.project.threatalerts.service;

import com.project.threatalerts.models.Alert;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ExternalAlertService {

    @Autowired
    private AlertService alertService;

    private static final String ZAXID_URL = "https://zaxid.net/news/";

    @Scheduled(fixedRate = 1500)
    public void fetchExternalAlerts() {
        try {
            Document doc = Jsoup.connect("https://zaxid.net/news/").get();

            Elements newsItems = doc.select("li.default-news-list > a");

            if (newsItems.isEmpty()) {
                System.out.println("Новини зі Zaxid.net не знайдено.");
                return;
            }

            System.out.println("Отримано " + newsItems.size() + " новин зі Zaxid.net");

            for (Element item : newsItems) {
                String title = item.text();
                String link = item.absUrl("href");

                if (title.isEmpty()) continue;

                Alert alert = new Alert();
                alert.setTitle(title);
                alert.setDescription("Джерело: " + link);
                alert.setCity("Львів");

                alertService.createAlert(alert);
                System.out.println("✅ Додано новину: " + title);
            }

        } catch (IOException e) {
            System.err.println(" Помилка при парсингу Zaxid.net: " + e.getMessage());
        }
    }

}
