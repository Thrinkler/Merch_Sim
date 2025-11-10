package app;

import java.util.ArrayList;
import java.util.Random;

public class OffersOfAnItem{
    String name;
    ArrayList<SellOffer> offers;
    public double maxPrice;

    OffersOfAnItem(SellOffer offer){
        this.name = offer.getItem().getName();
        offers = new ArrayList<>();
        offers.add(offer);
        maxPrice = offer.getPrice();
    }
    public String getName(){
        return this.name;
    }
    public ArrayList<SellOffer> getOffers(){
        return this.offers;
    }

    public void sellOffer(SellOffer offer){
        if(maxPrice < offer.getPrice()){ maxPrice = offer.getPrice();}
        offer.getPlayer().addMoney(offer.getPrice());
        this.offers.remove(offer);
    }

    public void updateOffers(){ //Después del ciclo, si nadie vendió
        for(SellOffer offer: this.offers){
            offer.lowerPrice();
        }
    }
    public void addOffer(SellOffer offer){
        this.offers.add(offer);
    }

    public SellOffer getRandSmartOffer(double maxPrice){
        Random rand = new Random();
        ArrayList<SellOffer> validOffers = new ArrayList<>();
        for (SellOffer offer : this.offers) {
            if (offer.getPrice() < maxPrice) {
                validOffers.add(offer);
            }
        }
        if (validOffers.isEmpty()) {
            return null;
        }

        return validOffers.get(rand.nextInt(validOffers.size()));
    }


    public double getAveragePrice(){
        double total = 0;
        for(SellOffer offer: this.offers){
            total += offer.getPrice();
        }
        return this.offers.isEmpty() ? maxPrice: total / this.offers.size();
    }

    public double[] getMinMaxPriceOffer(){
        if(this.offers.isEmpty()){
            return new double[]{maxPrice,maxPrice};
        }
        SellOffer min = this.offers.getFirst();
        SellOffer max = this.offers.getFirst();
        for(SellOffer offer: this.offers){
            if (offer.getPrice() > max.getPrice()){
                max = offer;
            }
            if (offer.getPrice() < min.getPrice()){
                min = offer;
            }
        }
        return new double[]{min.getPrice(),max.getPrice()};
    }


}
