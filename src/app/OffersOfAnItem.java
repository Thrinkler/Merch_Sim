package app;

import java.util.ArrayList;
import java.util.Random;

public class OffersOfAnItem{
    String name;
    ArrayList<SellOffer> offers;
    public double maxPrice;

    private double combinedPrice;
    private SellOffer actualMaxPrice;
    private SellOffer actualMinPrice;

    OffersOfAnItem(SellOffer offer){
        this.name = offer.getItem().getName();
        offers = new ArrayList<>();
        offers.add(offer);
        maxPrice = offer.getPrice();
        combinedPrice = offer.getPrice();
        actualMaxPrice = offer;
        actualMinPrice = offer;
    }
    public String getName(){
        return this.name;
    }
    public ArrayList<SellOffer> getOffers(){
        return this.offers;
    }

    public void sellOffer(SellOffer offer){
        offer.getPlayer().addMoney(offer.getPrice());
        this.offers.remove(offer);

        if(this.offers.isEmpty()) maxPrice = offer.getPrice();

        if(actualMaxPrice.equals(offer)){
            actualMaxPrice = offers.isEmpty() ? actualMaxPrice : offers.getFirst();
            for (SellOffer sellOffer : offers) {
                if(sellOffer.getPrice() >= actualMaxPrice.getPrice()){
                    actualMaxPrice = sellOffer;
                }
            }
        }
        if(actualMinPrice.equals(offer)){
            actualMinPrice = offers.isEmpty() ? actualMinPrice: offers.getFirst();
            for (SellOffer sellOffer : offers) {
                if(sellOffer.getPrice() <= actualMinPrice.getPrice()){
                    actualMinPrice = sellOffer;
                }
            }
        }

        combinedPrice -= offer.getPrice();
    }

    public void updateOffers(){ //Después del ciclo, si nadie vendió
        //combinedPrice *=0.95;
        combinedPrice = 0;
        ArrayList<SellOffer> newOffers = new ArrayList<>();
        for(SellOffer offer: this.offers){
            offer.lowerPrice();
            if(offer.getPlayer().isNotDead()) {
                combinedPrice += offer.getPrice();
                newOffers.add(offer);
            }
        }
        this.offers = newOffers;
    }
    public void addOffer(SellOffer offer){
        this.offers.add(offer);
        combinedPrice += offer.getPrice();
        if(actualMaxPrice.getPrice()<offer.getPrice()){
            actualMaxPrice = offer;
        }
        if(actualMinPrice.getPrice()>offer.getPrice()){
            actualMinPrice = offer;
        }
    }

    public SellOffer getRandSmartOffer(double maxPrice){
        Random rand = new Random();
        for (SellOffer offer : this.offers) {
            if (offer.getPrice() < maxPrice && rand.nextDouble() <= 0.8) {
                return offer;
            }
        }

        return null;
    }


    public double getAveragePrice(){
        double total = combinedPrice;
        return this.offers.isEmpty() ? maxPrice : total / this.offers.size();
    }


    public double[] getMinMaxPriceOffer(){
        if(this.offers.isEmpty()){
            return new double[]{maxPrice,maxPrice};
        }

        return new double[]{actualMinPrice.getPrice(),actualMaxPrice.getPrice()};
    }

}
