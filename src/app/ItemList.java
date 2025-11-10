package app;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemList{
    private final ArrayList<ItemTemplate> gameItems;
    private final ArrayList<ItemTemplate> materialItems;
    private final ArrayList<ItemTemplate> finalItems;

    public ItemList() {
        gameItems = new ArrayList<>();
        materialItems = new ArrayList<>();
        finalItems = new ArrayList<>();

        Material wood = new Material("Wood");
        Material stone = new Material("Stone");
        Material ironOre = new Material("Iron Ore");
        Material herb = new Material("Herb");
        Material diamond = new Material("Diamond");
        Material water = new Material("Water");



        Material coal = new Material("Coal", new Material[]{wood});
        Material ironIngot = new Material("Iron Ingot", new Material[]{ironOre, coal});
        Material steelIngot = new Material("Steel Ingot", new Material[]{ironIngot, coal});
        Material fiber = new Material("Fiber", new Material[]{herb});
        Material cloth = new Material("Cloth", new Material[]{fiber, water});
        Material thread = new Material("Thread", new Material[]{fiber});
        Material leather = new Material("Leather", new Material[]{herb, water});
        Material leatherStrip = new Material("Leather Strip", new Material[]{leather});
        Material flour = new Material("Flour", new Material[]{herb, stone});
        Material dough = new Material("Dough", new Material[]{flour, water});
        Material bread = new Material("Bread", new Material[]{dough, coal});
        Material potionBase = new Material("Potion Base", new Material[]{water, herb});
        Material potion = new Material("Potion", new Material[]{potionBase, herb});

        Material glass = new Material("Glass", new Material[]{stone, coal});

        Material bottle = new Material("Bottle", new Material[]{glass});
        Material gemDust = new Material("Gem Dust", new Material[]{stone, herb});
        Material rune = new Material("Rune", new Material[]{gemDust, cloth});
        Material oil = new Material("Oil", new Material[]{herb, water});
        Material resin = new Material("Resin", new Material[]{wood, coal});
        Material steelReinforced = new Material("Reinforced Steel", new Material[]{steelIngot, resin});
        Material ink = new Material("Ink", new Material[]{herb, water, coal});
        Material paper = new Material("Paper", new Material[]{cloth, water});
        Material scroll = new Material("Scroll", new Material[]{paper, ink});
        Material enchantedPaper = new Material("Enchanted Paper", new Material[]{scroll, rune});

        gameItems.addAll(Arrays.asList(
                wood, stone, ironOre, herb, water, diamond,
                coal, ironIngot, steelIngot, fiber, cloth, thread,
                leather, leatherStrip, flour, dough, bread, potionBase,
                potion, glass, bottle, gemDust, rune, oil, resin, steelReinforced,
                ink, paper, scroll, enchantedPaper
        ));
        materialItems.addAll(Arrays.asList(
                wood, stone, ironOre, herb, water,
                coal, ironIngot, steelIngot, fiber, cloth, thread,
                leather, leatherStrip, flour, dough, bread, potionBase,
                potion, glass, bottle, gemDust, rune, oil, resin, steelReinforced,
                ink, paper, scroll, enchantedPaper
        ));

        gameItems.add(new ItemTemplate("Iron Pickaxe", new Material[]{ironIngot, ironIngot, wood, leatherStrip}));
        gameItems.add(new ItemTemplate("Steel Pickaxe", new Material[]{steelIngot, steelIngot, wood, resin}));
        gameItems.add(new ItemTemplate("Iron Sword", new Material[]{ironIngot, leatherStrip, wood}));
        gameItems.add(new ItemTemplate("Steel Sword", new Material[]{steelReinforced, leatherStrip, wood}));
        gameItems.add(new ItemTemplate("Rune Sword", new Material[]{steelReinforced, rune, leatherStrip}));

        gameItems.add(new ItemTemplate("Leather Armor", new Material[]{leather, leather, leatherStrip}));
        gameItems.add(new ItemTemplate("Cloth Robe", new Material[]{cloth, thread, thread}));
        gameItems.add(new ItemTemplate("Steel Chestplate", new Material[]{steelReinforced, leatherStrip, resin}));
        gameItems.add(new ItemTemplate("Rune Helmet", new Material[]{steelIngot, rune, resin}));

        gameItems.add(new ItemTemplate("Magic Ring", new Material[]{rune, gemDust}));
        gameItems.add(new ItemTemplate("Scroll of Knowledge", new Material[]{enchantedPaper, ink}));
        gameItems.add(new ItemTemplate("Potion of Healing", new Material[]{potion, herb, bottle}));
        gameItems.add(new ItemTemplate("Potion of Strength", new Material[]{potion, resin, bottle}));
        gameItems.add(new ItemTemplate("Oil Lamp", new Material[]{oil, bottle, coal}));
        gameItems.add(new ItemTemplate("Enchanted Torch", new Material[]{oil, rune, wood}));

        gameItems.add(new ItemTemplate("Steel Nails", new Material[]{steelIngot}));
        gameItems.add(new ItemTemplate("Backpack", new Material[]{cloth, leatherStrip, thread}));
        gameItems.add(new ItemTemplate("Cooked Bread", new Material[]{bread, coal}));
        gameItems.add(new ItemTemplate("Map", new Material[]{paper, ink}));


        Material refinedGem = new Material("Refined Gem", new Material[]{diamond, stone, ironOre, wood});
        Material manaCore = new Material("Mana Core", new Material[]{refinedGem, herb, water});
        Material enchantedGem = new Material("Enchanted Gem", new Material[]{refinedGem, manaCore});
        Material magicThread = new Material("Magic Thread", new Material[]{thread, enchantedGem});
        Material gemHandle = new Material("Gem Handle", new Material[]{wood, refinedGem});
        Material crystalLens = new Material("Crystal Lens", new Material[]{refinedGem, glass});
        Material lightCrystal = new Material("Light Crystal", new Material[]{refinedGem, water});


        gameItems.addAll(Arrays.asList(refinedGem, manaCore,enchantedGem,gemHandle,
                                        lightCrystal, magicThread, crystalLens));
        materialItems.addAll(Arrays.asList(refinedGem, manaCore,enchantedGem,gemHandle,
                                        lightCrystal, magicThread, crystalLens));

        gameItems.add(new ItemTemplate("Diamond Ring", new Material[]{diamond, ironOre}));
        gameItems.add(new ItemTemplate("Jeweled Necklace", new Material[]{refinedGem, thread, ironOre}));
        gameItems.add(new ItemTemplate("Magic Ring", new Material[]{enchantedGem, steelIngot}));
        gameItems.add(new ItemTemplate("Crystal Wand", new Material[]{gemHandle, manaCore, enchantedGem}));
        gameItems.add(new ItemTemplate("Enchanted Sword", new Material[]{ironOre, refinedGem, enchantedGem}));
        gameItems.add(new ItemTemplate("Diamond Crown", new Material[]{refinedGem, ironIngot, cloth}));
        gameItems.add(new ItemTemplate("Light Amulet", new Material[]{lightCrystal, manaCore, thread}));
        gameItems.add(new ItemTemplate("Gem Lamp", new Material[]{refinedGem, oil, ironOre}));
        gameItems.add(new ItemTemplate("Energy Focus", new Material[]{manaCore, crystalLens, gemDust}));
        gameItems.add(new ItemTemplate("Teleport Rune", new Material[]{enchantedGem, rune, paper}));
        gameItems.add(new ItemTemplate("Diamond Pickaxe", new Material[]{diamond, diamond, diamond, wood, wood}));
        gameItems.add(new ItemTemplate("Mana Mirror", new Material[]{crystalLens, enchantedGem, steelIngot}));

        for(ItemTemplate item : gameItems){
            if(!materialItems.contains(item)){
                finalItems.add(item);
            }
        }
    }

    public ArrayList<ItemTemplate> getGameItems() {
        return new ArrayList<>(gameItems);
    }
    public ArrayList<ItemTemplate> getFinalItems() {
        return finalItems;
    }

    public boolean isMaterial(Items item){
        for (ItemTemplate template : materialItems) {
            if (template.getName().equals(item.getName())){
                return true;
            }
        }
        return false;
    }
    public ItemTemplate getItem(int index){
        return gameItems.get(index);
    }
    public ItemTemplate getItem(String name){
        for(ItemTemplate item : gameItems){
            if(item.getName().equals(name)){
                return item;
            }
        }
        return null;
    }
}