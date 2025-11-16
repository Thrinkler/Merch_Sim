package app.players;
import app.ItemTemplate;
import app.Items;
import app.Material;
import app.Merch;

import java.util.List;

public class MinerPlayer extends Player {
    private int diamondSkill;
    private int woodSkill;
    private int stoneSkill;
    private int ironSkill;
    private int herbSkill;
    private int watSkill;

    private int coalSkill;
    private int fibSkill;
    private int hidSkill;
    private int wheSkill;
    private int meaSkill;
    private int fisSkill;

    public MinerPlayer(Merch merch) {
        super(merch);
        workTools.addAll(List.of(new ItemTemplate[]{itemList.getItem("Iron Pickaxe"), itemList.getItem("Steel Pickaxe"),
        itemList.getItem("Rune Pickaxe"), itemList.getItem("Stone Pickaxe"),itemList.getItem("Diamond Pickaxe"),}));
        //if(rand.nextBoolean()){
            tool = new Items(itemList.getItem("Iron Pickaxe"),1);
        //}

        evaluateSkills();
    }

    public MinerPlayer(Merch merch, int diamondSkill, int woodSkill, int stoneSkill, int ironSkill, int herbSkill, int watSkill,
                       int coalSkill, int fibSkill, int hidSkill, int wheSkill, int meaSkill, int fisSkill){
        super(merch);
        this.diamondSkill = diamondSkill;
        this.woodSkill = woodSkill;
        this.stoneSkill = stoneSkill;
        this.ironSkill = ironSkill;
        this.herbSkill = herbSkill;
        this.watSkill = watSkill;
        this.coalSkill = coalSkill;
        this.fibSkill = fibSkill;
        this.hidSkill = hidSkill;
        this.wheSkill = wheSkill;
        this.meaSkill = meaSkill;
        this.fisSkill = fisSkill;
    }
    private void evaluateSkills() {
        diamondSkill = rand.nextInt(2)+1;
        woodSkill = rand.nextInt(10)+10;
        stoneSkill = rand.nextInt(20)+10;
        ironSkill = rand.nextInt(10) +5;
        herbSkill = rand.nextInt(20) +20;
        watSkill = rand.nextInt(25) +25;

        coalSkill = rand.nextInt(10) +10;
        fibSkill = rand.nextInt(10)+10;
        hidSkill = rand.nextInt(15)+15;
        wheSkill = rand.nextInt(10)+20;
        meaSkill = rand.nextInt(20)+10;
        fisSkill = rand.nextInt(20)+10;
    }


    public int dayRoutine() {
        super.dayRoutine();
        int minObtained = 0;
        if(tool != null && tool.useItem() ) {
            if(rand.nextDouble() < 0.9){
                minObtained = playerWentMining();
                }
        }
        else {
            lookingForFood = true;
            findNewTool();
            lookingForFood = false;
        }

        return minObtained;
    }




    int playerWentMining() // Nuevos materiales
    {

        int dia = rand.nextInt(diamondSkill);
        int woo = rand.nextInt(woodSkill/2,woodSkill);
        int sto = rand.nextInt(stoneSkill/2, stoneSkill);
        int iron = rand.nextInt(ironSkill/2, ironSkill);
        int her = rand.nextInt(herbSkill/2, herbSkill);
        int wat = rand.nextInt(watSkill/2, watSkill);
        int fib = rand.nextInt(fibSkill/2, fibSkill);
        int hid = rand.nextInt(hidSkill/2, hidSkill);
        int whe = rand.nextInt(wheSkill/2, wheSkill);
        int mea = rand.nextInt(meaSkill/2, meaSkill);
        int fis = rand.nextInt(fisSkill/2, fisSkill);
        int coa = rand.nextInt(coalSkill/2, coalSkill);


        Material diamond = new Material("Diamond");
        Material wood = new Material("Wood");
        Material stone = new Material("Stone");
        Material ironOre = new Material("Iron Ore");
        Material herb = new Material("Herb");
        Material water = new Material("Water");
        Material fiber = new Material("Fiber");
        Material rawhide = new Material("Rawhide");
        Material wheat = new Material("Wheat");
        Material rawMeat = new Material("Raw Meat");
        Material rawFish = new Material("Raw Fish");
        Material coal = new Material("Coal");

        for(int i = 0; i < dia; i++) sellItem(diamond);
        for(int i = 0; i < woo; i++) sellItem(wood);
        for(int i = 0; i < sto; i++) sellItem(stone);
        for(int i = 0; i < iron; i++) sellItem(ironOre);
        for(int i = 0; i < her; i++) sellItem(herb);
        for(int i = 0; i < wat; i++) sellItem(water);
        for(int i = 0; i < fib; i++) sellItem(fiber);
        for(int i = 0; i < hid; i++) sellItem(rawhide);
        for(int i = 0; i < whe; i++) sellItem(wheat);
        for(int i = 0; i < mea; i++) sellItem(rawMeat);
        for(int i = 0; i < fis; i++) sellItem(rawFish);
        for(int i = 0; i < coa; i++) sellItem(coal);

        return dia+woo+sto+iron+her+wat+fib+hid+whe+mea+fis+coa;

    }
}
