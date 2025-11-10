package app.players;
import app.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public abstract class Players{
    ArrayList<Items> inventory;
    HashMap<String,Integer> materialList;
    final Random rand = new Random();

    final Merch merch;
    final ItemList itemList = new ItemList();

    ArrayList<ItemTemplate> wishList;

    ArrayList<ItemTemplate> itemsThatCanGoToWishlist;


    double money;

    private final int crafting_stat;

    public Players(Merch merch){
        money = rand.nextInt(1000);
        this.merch = merch;

        ItemList itemList = new ItemList();

        crafting_stat = rand.nextInt(10);
        this.inventory = new ArrayList<>();
        
        this.materialList = new HashMap<>();
        
        this.wishList = new ArrayList<>();
        this.itemsThatCanGoToWishlist = itemList.getGameItems();
    }

    public void addItemToWishList(){
        wishList.add(itemsThatCanGoToWishlist.get(rand.nextInt(itemsThatCanGoToWishlist.size())));
    }

    public void addToWishList(ItemTemplate item){
        this.wishList.add(item);
    }


    public void sellItem(){
        if (!inventory.isEmpty()) {
            Set<String> mats = materialList.keySet();
            ArrayList<String> matArr = new ArrayList<>(mats);
            if (!mats.isEmpty() && rand.nextBoolean()) {
                String matToSell = matArr.get(rand.nextInt(matArr.size()));
                if (materialList.get(matToSell) > 0) {
                    materialList.put(matToSell, materialList.get(matToSell) - 1);
                    Material mat_to_sell = new Material(matToSell);
                    sellItem(mat_to_sell);
                }
            } else {
                sellItem(inventory.removeFirst());
            }
        }
    }

    private int craftingOutcome(){

        int stat = rand.nextInt(10 - crafting_stat);

        return 10 - stat;
    }

    public Items craftItem(ItemTemplate template) {
        if (template.getCraftNeeded() == null) return null; //si es materia prima, no lo podemos construir
        HashMap<String, Integer> listOfCraft = template.getCraftHashNeeded();

        for (ItemTemplate matNeeded : template.getCraftNeeded()) {
            if (listOfCraft.getOrDefault(matNeeded.getName(), 0) > this.materialList.getOrDefault(matNeeded.getName(), 0))
            { return null;}
        }

        for (ItemTemplate materialsNeeded : template.getCraftNeeded()) {
            this.materialList.put(materialsNeeded.getName(), this.materialList.getOrDefault(materialsNeeded.getName(), 0) - 1);

        }

        Items newItem = new Items(template, craftingOutcome());
        if(itemList.isMaterial(newItem)){
            this.materialList.put(newItem.getName(), this.materialList.getOrDefault(newItem.getName(), 0) + 1);
        }
        else{
            inventory.add(newItem);
        }

        return newItem;
    }


    public void buyItem(OffersOfAnItem offers, SellOffer offer){
        if(offer == null) return;

        offers.sellOffer(offer);

        money -= offer.getPrice();
        Items boughtItem = offer.getItem();
        if (boughtItem instanceof Material mat) {
            this.materialList.put(mat.getName(), this.materialList.getOrDefault(mat.getName(), 0) + 1);

        } else {
            this.inventory.add(boughtItem);
        }
    }


    public void sellItem(Items item){
        if (item == null) return;
        double calculatedPrice = calculateInitialPrice(item);
        //System.out.println("I want to sell a "+ item.getName() + " for " + calculatedPrice);
        merch.setNewOffer(new SellOffer(item,calculatedPrice, this));
    }



    public abstract void dayRoutine();
    public boolean buyProcess(ItemTemplate itemToBuy){
        OffersOfAnItem wishedOffer = merch.findOffersOfAnItem(itemToBuy);
        return buyProcess(wishedOffer);
    }
    boolean buyProcess(OffersOfAnItem wishedOffer){
        SellOffer possibleOffer = (wishedOffer == null)? null : wishedOffer.getRandSmartOffer(money/rand.nextInt(1,10));
        if(possibleOffer != null){
            buyItem(wishedOffer, possibleOffer);
            return true;
        }
        return false;
    }

    public void buyProcess() {

        if (rand.nextBoolean() || wishList.isEmpty()) {
            addItemToWishList();
        }
        ItemTemplate itemToBuy = wishList.getFirst();
        wishList.remove(itemToBuy);
        if(!buyProcess(itemToBuy)) wishList.add(itemToBuy);
    }



    public double calculateInitialPrice(Items item) {

        double productionCost = getProductionCost(item);
        double myFairValue = (productionCost * (1.0 + ((double) item.getQuality() / 10.0)));
        OffersOfAnItem marketOffers = merch.findOffersOfAnItem(item);
        if (marketOffers == null) {
            return myFairValue* 1.2;
        }

        double[] minmax = marketOffers.getMinMaxPriceOffer();

        if(Double.isNaN(minmax[0])) minmax[0] = myFairValue;
        double lowest = Math.max(minmax[0] * 0.95,myFairValue*1.1);

        double highest = Math.min(minmax[1], marketOffers.maxPrice) * 1.05;
        if (lowest >= highest) {
            highest = lowest * 1.1;
        }


        return rand.nextDouble(lowest, highest);
    }
    private double getProductionCost(ItemTemplate item){
        double productionCost = 0;

        if(item.getCraftNeeded() == null || item.getCraftNeeded().length == 0) { // Es un material
            OffersOfAnItem matOffer = merch.findOffersOfAnItem(item);
            return matOffer != null ? matOffer.getAveragePrice() :  10;
        }

        for(String mat : item.getCraftHashNeeded().keySet()){
            productionCost+= item.getCraftHashNeeded().get(mat) * getProductionCost(itemList.getItem(mat));
        }
        return productionCost;
    }

    public ArrayList<Items> getInventory() {
        return inventory;
    }

    public double getMoney() {
        return money;
    }

    public double getPatrimony() {
        double patrimony = money;
        for (Items item : inventory) {
            patrimony += (merch.findOffersOfAnItem(item) != null) ? merch.findOffersOfAnItem(item).getAveragePrice(): 0;
        }

        for(String mats : materialList.keySet()){
            patrimony += (merch.findOffersOfAnItem(mats) != null) ?
                    merch.findOffersOfAnItem(mats).getAveragePrice()* materialList.get(mats):
            calculateInitialPrice(new Items(itemList.getItem(mats),10));
        }
        return patrimony;
    }


    public void addMoney(double money) {
        this.money += money;
    }

    public HashMap<String, Integer> getMaterialList() {
        return materialList;
    }


    public ArrayList<ItemTemplate> getWishList(){
        return wishList;
    }
}


