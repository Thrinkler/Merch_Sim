package app;

import app.players.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DataLogger {
    private final PrintWriter pricesWriter;
    private final PrintWriter summaryWriter;
    private final ArrayList<ItemTemplate> allGameItems;

    public DataLogger(String pricesFilePath, String summaryFilePath, ItemList itemList) throws IOException {
        this.allGameItems = itemList.getGameItems();

        pricesWriter = new PrintWriter(new FileWriter(pricesFilePath));

        String priceHeader = "Day," + allGameItems.stream()
                .map(ItemTemplate::getName)
                .collect(Collectors.joining(","));
        pricesWriter.println(priceHeader);

        summaryWriter = new PrintWriter(new FileWriter(summaryFilePath));

        summaryWriter.println("Day,TotalMoney,AvgMoney,AvgPatrimony,RichestPatrimony,PoorestPatrimony");
    }

    public void logDailyPrices(int day, Merch merch) {
        StringBuilder sb = new StringBuilder();
        sb.append(day);

        for (ItemTemplate item : allGameItems) {
            sb.append(",");
            OffersOfAnItem offers = merch.findOffersOfAnItem(item.getName());

            double avgPrice = offers == null? 0: offers.getAveragePrice();

            sb.append(String.format("%.4f", avgPrice));
        }
        pricesWriter.println(sb);
    }

    public void logDailySummary(int day, double totalMoney, ArrayList<Player> players) {

        double totalPatrimony = 0;
        for (Player p : players) {
            totalPatrimony += p.getPatrimony();
        }

        double avgMoney = totalMoney / players.size();
        double avgPatrimony = totalPatrimony / players.size();



        summaryWriter.println(String.format("%d,%.4f,%.4f,%.4f",
                day,
                totalMoney,
                avgMoney,
                avgPatrimony
        ));
    }

    public void close() {
        pricesWriter.close();
        summaryWriter.close();
        System.out.println("DataLogger cerró los archivos.");
    }
}
