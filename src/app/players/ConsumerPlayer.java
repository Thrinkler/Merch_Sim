package app.players;

import app.*;

public class ConsumerPlayer extends Player {
    public ConsumerPlayer(Merch merch){
        super(merch);
        itemsThatCanGoToWishlist = itemList.getFinalItems();
        for(int i = 0; i < 10; i++) addItemToWishList();
    }

    @Override
    public int dayRoutine() {
        super.dayRoutine();
        buyProcess();
        if(rand.nextBoolean()) sellItem();
        return 0;
    }
}
