package app;

import app.players.Player;

public class SellOffer {

    private final Items item;
    private double price;
    private final Player player;

    public SellOffer(Items item, double price, Player player) {
        this.player = player;
        this.item = item;
        this.price = price;
    }


    public void lowerPrice(){
        this.price *= 0.95;
        //if(player.isDead()) this.price = 0;
    }

    public Items getItem(){
        return item;
    }

    public double getPrice(){
        return price;
    }

    public Player getPlayer(){
        return player;
    }

}
