package app;

import java.util.ArrayList;

public class Merch{
    ArrayList<OffersOfAnItem> itemOffers;

    public Merch(ArrayList<OffersOfAnItem> itemOffers){
        this.itemOffers = itemOffers;
    }
    public Merch(SellOffer sellOffer){
        this.itemOffers = new ArrayList<>();
        this.itemOffers.add(new OffersOfAnItem(sellOffer));
    }
    public Merch(){
        this.itemOffers = new ArrayList<>();
    }

    public ArrayList<OffersOfAnItem> getItemOffers(){
        return this.itemOffers;
    }

    public void setNewOffer(SellOffer sellOffer){
        for(OffersOfAnItem offer: this.itemOffers){
            if(offer.getName() != null && offer.getName().equals(sellOffer.getItem().getName())){
                offer.addOffer(sellOffer);
                return;
            }
        }
        OffersOfAnItem offer = new OffersOfAnItem(sellOffer);
        this.itemOffers.add(offer);
    }

    public OffersOfAnItem findOffersOfAnItem(ItemTemplate item){
        for(OffersOfAnItem offersOfAnItem : this.itemOffers){
            if(offersOfAnItem.name.equals(item.getName())){
                return offersOfAnItem;
            }
        }
        return null;
    }

    public OffersOfAnItem findOffersOfAnItem(String item){
        for(OffersOfAnItem offersOfAnItem : this.itemOffers){
            if(offersOfAnItem.name.equals(item)){
                return offersOfAnItem;
            }
        }
        return null;
    }

    public void updateOffers(){
        for(OffersOfAnItem offer: this.itemOffers){
            offer.updateOffers();
        }
    }
}


