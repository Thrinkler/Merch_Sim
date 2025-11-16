package app.players;
import app.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Player {
    ArrayList<Items> inventory;
    HashMap<String,Integer> materialList;
    final Random rand = new Random();

    final Merch merch;
    final ItemList itemList = new ItemList();

    ArrayList<ItemTemplate> wishList;

    ArrayList<ItemTemplate> itemsThatCanGoToWishlist;

    boolean isDead = false;

    int day = 0;

    Items tool;
    ArrayList<ItemTemplate> workTools = new ArrayList<>();

    int thirst = 0;
    int hunger = 0;

    int maxHunger;
    int maxThirst;

    boolean lookingForFood = false;

   double money;

    private final int crafting_stat;

    public Player(Merch merch){
        money = rand.nextInt(1000);
        this.merch = merch;

        ItemList itemList = new ItemList();

        crafting_stat = rand.nextInt(10);

        this.inventory = new ArrayList<>();
        this.materialList = new HashMap<>();

        this.wishList = new ArrayList<>();
        this.itemsThatCanGoToWishlist = itemList.getGameItems();

        maxHunger = rand.nextInt(10,20);

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
        if(materialList.containsKey(item.getName()) && materialList.getOrDefault(item.getName(), 0) > 0){
            materialList.put(item.getName(), materialList.getOrDefault(item.getName(), 0) - 1);
        }
        else {
            this.inventory.removeIf(items -> items.getName().equals(item.getName()));
        }
        merch.setNewOffer(new SellOffer(item,calculatedPrice, this));
    }

    private Items searchForFood(){
        for (Items item : inventory) {
            if(itemList.isFood(item)){
                return item;
            }
        }
        for(String material: materialList.keySet()){
            Material mat = new Material(material);
            if(itemList.isFood(mat)){
                return mat;
            }
        }
        return null;
    }

    public void eat(){
        Items food = searchForFood();
        if(food == null) {
            lookingForFood = true;
            ArrayList<ItemTemplate> possibleFood;
            possibleFood = (ArrayList<ItemTemplate>) itemList.getFoodItems().clone();

            ItemTemplate foodWish;
            while(!possibleFood.isEmpty()){
                foodWish = possibleFood.remove(rand.nextInt(possibleFood.size()));
                if(buyProcess(foodWish)) break;
            }
            lookingForFood = false;
        }
        food = searchForFood();
        if(food == null) {return;}

        hunger = 0;
        maxHunger = rand.nextInt(5,10);
        inventory.remove(food);

        if(materialList.getOrDefault(food.getName(), 0) > 0){
            materialList.put(food.getName(), materialList.getOrDefault(food.getName(), 0) - 1);
        }
    }


    public void kill(){
        isDead = true;
    }

    public boolean isNotDead(){
        return !isDead;

    }

    public void essentialDaily(){
        day++;
        hunger ++;
        //thirst ++;
        eat();
        ArrayList<Items> newInv = new ArrayList<>();
        for(Items item : inventory){
            if(item.useItem()){
                newInv.add(item);
            }
        }
        inventory = newInv;
        if(thirst > maxThirst ||  hunger >maxHunger){
            kill();
        }
    }

    public int dayRoutine(){
        essentialDaily();
        return 0;
    }

    void findNewTool(){
        for(Items item: inventory){
            for(ItemTemplate workTool : workTools){
                if(workTool.getName().equals(item.getName())){
                    tool = item;
                    return;
                }
            }
        }
        for(ItemTemplate item: workTools){
            lookingForFood = true;
            buyProcess(item);
            lookingForFood = false;
        }
    }

    public boolean buyProcess(ItemTemplate itemToBuy){
        OffersOfAnItem wishedOffer = merch.findOffersOfAnItem(itemToBuy);
        return buyProcess(wishedOffer);
    }
    boolean buyProcess(OffersOfAnItem wishedOffer){
        SellOffer possibleOffer;
        if((hunger < maxHunger*0.8 || thirst < maxThirst*0.8)&& !lookingForFood){
            possibleOffer = (wishedOffer == null)? null : wishedOffer.getRandSmartOffer(money/rand.nextInt(1,5));
        }
        else{
            possibleOffer = (wishedOffer == null)? null : wishedOffer.getRandSmartOffer(money);
        }

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
        ItemTemplate itemToBuy = wishList.removeFirst();

        for(Items item: inventory){
            if(item.getName().equals(itemToBuy.getName())){return ;}
        }
        if(materialList.getOrDefault(itemToBuy.getName(), 0) > 0){return;}

        if(!buyProcess(itemToBuy)) wishList.add(itemToBuy);
    }



    public double calculateInitialPrice(Items item) {

        double productionCost = getProductionCost(item);
        double myFairValue = (productionCost * (1.0 + ((double) item.getQuality() / 10.0)));
        OffersOfAnItem marketOffers = merch.findOffersOfAnItem(item);
        if (marketOffers == null) {
            return myFairValue* 1.1;
        }

        double[] minmax = marketOffers.getMinMaxPriceOffer();

        if(Double.isNaN(minmax[0])) minmax[0] = myFairValue;
        double lowest = Math.max(minmax[0] * 0.95,myFairValue*1.1);

        double highest = Math.min(minmax[1], marketOffers.maxPrice) * 1.05;
        if(lowest == 0) lowest = 0.1;
        if (lowest >= highest) {
            highest = lowest * 1.1;
        }


        return rand.nextDouble(lowest, highest);
    }
    private double getProductionCost(ItemTemplate item){
        double productionCost = 0;

        if(item.getCraftNeeded() == null || item.getCraftNeeded().length == 0) { // Es un material
            OffersOfAnItem matOffer = merch.findOffersOfAnItem(item);
            double initialPrice = 1;
            return matOffer != null ? matOffer.getAveragePrice() :  initialPrice;
        }

        for(String mat : item.getCraftHashNeeded().keySet()){
            productionCost+= item.getCraftHashNeeded().get(mat) * getProductionCost(itemList.getItem(mat));
        }
        return productionCost;
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

}


