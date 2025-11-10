package app;

import app.players.Players;

public class SellOffer {

    private final Items item;
    private double price;
    private final Players player;

    public SellOffer(Items item, double price, Players player) {
        this.player = player;
        this.item = item;
        this.price = price;
    }


    public void lowerPrice(){
        this.price *= 0.95;
    }

    public Items getItem(){
        return item;
    }

    public double getPrice(){
        return price;
    }

    public Players getPlayer(){
        return player;
    }

}
