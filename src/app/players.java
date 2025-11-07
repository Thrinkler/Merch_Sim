package app;
import java.util.ArrayList;
import java.util.Random;

class Players{
    private ArrayList<Items> inventory;
    private ArrayList<Material> craftingMaterial;
    private ArrayList<ItemTemplate> wishList;

    private int money;

    private int crafting_stat;

    public Players(){
        Random r= new Random();
        money = r.nextInt(1000);

        crafting_stat = r.nextInt(10);
        this.inventory = new ArrayList<>();
        this.craftingMaterial = new ArrayList<>();
        this.wishList = new ArrayList<>();
        
    }

    private int craftingOutcome(){
        Random r= new Random();
        int stat = r.nextInt(10 - crafting_stat);

        return 10 - stat;
    }

    public Items craftItem(ItemTemplate template){
        if(craftingMaterial.size() >= template.getCraftNeeded()){
            Items newItem = new Items(template, craftingOutcome());
            inventory.add(newItem);
            for (int i = 0; i < template.getCraftNeeded(); i++) {
                craftingMaterial.removeFirst();
            }
            return newItem;
        }
        return null;
    }



}
