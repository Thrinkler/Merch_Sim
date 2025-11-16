package app.players;

import app.ItemTemplate;
import app.Items;
import app.Merch;

import java.util.ArrayList;
import java.util.List;

public class ChefPlayer extends CrafterPlayer{

    public ChefPlayer(Merch merch){
        super(merch);
        itemsThatCanGoToWishlist = itemList.getFoodItems();
        workTools = new ArrayList<>();
        workTools.addAll(List.of(new ItemTemplate[]{itemList.getItem("Knife"), itemList.getItem("Stone Mortar"),
                itemList.getItem("Rolling Pin"), itemList.getItem("Iron Skillet"),itemList.getItem("Apron"),}));
        //if(rand.nextBoolean()){
            tool = new Items(itemList.getItem("Knife"),150);
        //}

    }
    @Override
    public int dayRoutine() {
        essentialDaily();

        if(((tool != null && tool.useItem())|| rand.nextDouble() < 0.1 )) {
            int randNum = rand.nextInt(10,20);
            int crafted = craftProcess(randNum);
            //System.out.println("Crafted " + crafted);

            sellItem(crafted);

        }
        if((tool == null || !tool.useItem())) {
            findNewTool();
        }
        if(rand.nextDouble() < 0.5) {
            buyProcess();
        }

        day++;
        return 0;
    }

    @Override
    public void sellItem(int crafted){
        for(int i = 0; i < crafted; i++){
            if (!inventory.isEmpty()) {
                ArrayList<Items> itemsToSell = new ArrayList<>();
                for (Items item : inventory) {
                    if (itemList.isFood(item) && item != tool) {
                        itemsToSell.add(item);
                    }
                }
                if (!itemsToSell.isEmpty()) {

                    Items itemToSell = itemsToSell.get(rand.nextInt(itemsToSell.size()));
                    sellItem(itemToSell);
                }
            }
        }

    }
}
