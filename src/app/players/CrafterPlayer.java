package app.players;

import app.ItemTemplate;
import app.Items;
import app.Merch;

public class CrafterPlayer extends Players{
    public CrafterPlayer(Merch merch){
        super(merch);
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


    @Override
    public void buyProcess() {
        if (wishList.isEmpty()) {
            addItemToWishList();
        }
        ItemTemplate itemToBuy = wishList.getFirst();
        craftProcess(itemToBuy);
        wishList.remove(itemToBuy);
    }
    public void dayRoutine(){
        buyProcess();
        if(rand.nextBoolean()) sellItem();
    }
}
