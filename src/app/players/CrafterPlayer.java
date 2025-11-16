package app.players;

import app.ItemTemplate;
import app.Items;
import app.Merch;

import java.util.ArrayList;

public class CrafterPlayer extends Player{
    public CrafterPlayer(Merch merch){
        super(merch);
        itemsThatCanGoToWishlist = itemList.getToolItems();
    }
    public boolean buyCraftProcess(ItemTemplate itemToBuy){
        if(buyProcess(itemToBuy)) return true;
        return craftProcess(itemToBuy);
    }

    public boolean craftProcess(ItemTemplate itemToBuy){
        Items canCraft = craftItem(itemToBuy);
        if(canCraft != null){
            return true;
        }
        if(itemToBuy.getCraftNeeded() != null){
            for(ItemTemplate material : itemToBuy.getCraftNeeded()){
                if(!buyCraftProcess(material)) return false;
            }
            canCraft = craftItem(itemToBuy);
        }
        return canCraft != null;
    }

    public int craftProcess(int craftNeeded) {
        int out = 0;
        for(int i = 0; i < craftNeeded; i++){
            if (wishList.isEmpty()) {
                addItemToWishList();
            }
            ItemTemplate itemToBuy = wishList.removeFirst();
                if(craftProcess(itemToBuy)){
                    out++;
                }
        }

        return out;
    }



    public int dayRoutine(){
        super.dayRoutine();
        int randNum = rand.nextInt(5);
        int crafted = craftProcess(randNum);
        sellItem(crafted);

        return randNum;
    }

    public void sellItem(int crafted){
        for(int i = 0; i< crafted; i++){
            if (!inventory.isEmpty()) {
                ArrayList<Items> itemsToSell = new ArrayList<>();
                for (Items item : inventory) {
                    if (item != tool) {
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
