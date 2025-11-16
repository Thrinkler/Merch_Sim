package app;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemList {

    // Listas Requeridas (para no romper tu código)
    private final ArrayList<ItemTemplate> gameItems;      // TODOS los ítems
    private final ArrayList<ItemTemplate> materialItems;  // Solo materiales (consumidos en crafteo)
    private final ArrayList<ItemTemplate> finalItems;     // Ítems que no son materiales (productos)

    // Nuevas Listas (para definir roles)
    public final ArrayList<ItemTemplate> foodItems;        // Solo comida (para Chefs y Consumidores)
    public final ArrayList<ItemTemplate> toolItems;        // Herramientas (para Mineros, etc.)
    public final ArrayList<ItemTemplate> equipmentItems;   // Armas y armaduras (para Crafters)
    public final ArrayList<ItemTemplate> artisanItems;     // Lujo y arte (para Crafters Artistas)

    // Lista de Materias Primas (para Mineros)
    public final ArrayList<ItemTemplate> rawMaterials;

    public ItemList() {
        // Inicializa todas las listas
        gameItems = new ArrayList<>();
        materialItems = new ArrayList<>();
        finalItems = new ArrayList<>();
        foodItems = new ArrayList<>();
        toolItems = new ArrayList<>();
        equipmentItems = new ArrayList<>();
        artisanItems = new ArrayList<>();
        rawMaterials = new ArrayList<>();

        // --- 1. MATERIAS PRIMAS (Producidas por Mineros) ---
        // Estas son la base de la economía. No tienen receta.
        Material water = new Material("Water");
        Material wood = new Material("Wood");
        Material stone = new Material("Stone");
        Material ironOre = new Material("Iron Ore");
        Material coal = new Material("Coal");
        Material herb = new Material("Herb");
        Material fiber = new Material("Fiber"); // Antes de 'Herb', ahora materia prima
        Material rawhide = new Material("Rawhide"); // Para hacer cuero
        Material wheat = new Material("Wheat"); // Para hacer harina
        Material rawMeat = new Material("Raw Meat");
        Material rawFish = new Material("Raw Fish");
        Material diamond = new Material("Diamond");

        // Añadir a las listas de "Materias Primas" y "Materiales"
        rawMaterials.addAll(Arrays.asList(water, wood, stone, ironOre, coal, herb, fiber, rawhide, wheat, rawMeat, rawFish, diamond));
        materialItems.addAll(rawMaterials);

        // --- 2. MATERIALES REFINADOS (Producidos por Crafters/Chefs) ---
        // Estos se craftean, pero se usan para craftear otras cosas.
        Material flour = new Material("Flour", new Material[]{wheat, stone});
        Material dough = new Material("Dough", new Material[]{flour, water});
        Material leather = new Material("Leather", new Material[]{rawhide, water});
        Material leatherStrip = new Material("Leather Strip", new Material[]{leather});
        Material thread = new Material("Thread", new Material[]{fiber});
        Material cloth = new Material("Cloth", new Material[]{thread});
        Material ironIngot = new Material("Iron Ingot", new Material[]{ironOre, coal});
        Material steelIngot = new Material("Steel Ingot", new Material[]{ironIngot, coal});
        Material glass = new Material("Glass", new Material[]{stone, coal});
        Material bottle = new Material("Bottle", new Material[]{glass});
        Material potionBase = new Material("Potion Base", new Material[]{water, herb});
        //Material paper = new Material("Paper", new Material[]{wood, water});
        //Material ink = new Material("Ink", new Material[]{coal, water});
        Material gemDust = new Material("Gem Dust", new Material[]{stone, diamond});
        Material rune = new Material("Rune", new Material[]{gemDust, herb});
        //Material oil = new Material("Oil", new Material[]{herb, water}); // Para lámparas
        //Material pigment = new Material("Pigment", new Material[]{herb, stone}); // Para arte
        //Material dyedCloth = new Material("Dyed Cloth", new Material[]{cloth, pigment}); // Para arte
        //Material steelReinforced = new Material("Reinforced Steel", new Material[]{steelIngot, leather});
        //Material scroll = new Material("Scroll", new Material[]{paper, ink});

        // Añadir a la lista de "Materiales"
        materialItems.addAll(Arrays.asList(flour, dough, leather, leatherStrip, thread, cloth, ironIngot, steelIngot,
                glass, bottle, potionBase, gemDust, rune ));//, , paper, ink, oil, pigment, dyedCloth, steelReinforced, scroll));

        // --- 3. COMIDA (Producida por Chefs) ---
        // Estos son "finalItems" pero también van a "foodItems"
        ItemTemplate bread = new ItemTemplate("Bread", new Material[]{dough, coal});
        ItemTemplate cookedMeat = new ItemTemplate("Cooked Meat", new Material[]{rawMeat, coal});
        ItemTemplate cookedFish = new ItemTemplate("Cooked Fish", new Material[]{rawFish, coal});
        ItemTemplate herbSoup = new ItemTemplate("Herb Soup", new Material[]{water, herb});
        ItemTemplate potionOfHealing = new ItemTemplate("Potion of Healing", new Material[]{potionBase, herb, bottle});
        ItemTemplate potionOfStrength = new ItemTemplate("Potion of Strength", new Material[]{potionBase, rune, bottle});

        // Añadir a las listas de "Comida" y "Finales"
        foodItems.addAll(Arrays.asList(bread, cookedMeat, cookedFish, herbSoup, potionOfHealing, potionOfStrength));
        finalItems.addAll(foodItems);

        // --- 4. PRODUCTOS FINALES (Producidos por Crafters) ---

        // 4a. Herramientas
        ItemTemplate ironPickaxe = new ItemTemplate("Iron Pickaxe", new Material[]{ironIngot, wood, leatherStrip});
        ItemTemplate steelPickaxe = new ItemTemplate("Steel Pickaxe", new Material[]{steelIngot, wood, leather});
        ItemTemplate stonePickaxe = new ItemTemplate("Stone Pickaxe", new Material[]{stone, stone, wood});
        ItemTemplate runePickaxe = new ItemTemplate("Rune Pickaxe", new Material[]{steelIngot, rune, wood});
        ItemTemplate diamondPickaxe = new ItemTemplate("Diamond Pickaxe", new Material[]{diamond, diamond, steelIngot, wood});

        //ItemTemplate map = new ItemTemplate("Map", new Material[]{paper, ink});
        //ItemTemplate backpack = new ItemTemplate("Backpack", new Material[]{cloth, leatherStrip, thread});
        //ItemTemplate oilLamp = new ItemTemplate("Oil Lamp", new Material[]{oil, bottle, thread});

        ItemTemplate stoneMortar = new ItemTemplate("Stone Mortar", new Material[]{stone, stone});
        ItemTemplate rollingPin = new ItemTemplate("Rolling Pin", new Material[]{wood, wood});
        ItemTemplate ironSkillet = new ItemTemplate("Iron Skillet", new Material[]{ironIngot, wood});
        ItemTemplate knife = new ItemTemplate("Knife", new Material[]{ironIngot, wood, leatherStrip});
        ItemTemplate apron = new ItemTemplate("Apron", new Material[]{cloth, leatherStrip, thread});
        toolItems.addAll(Arrays.asList(ironPickaxe, steelPickaxe,  stoneMortar, rollingPin,
                ironSkillet,knife, apron, stonePickaxe, diamondPickaxe, runePickaxe));
        finalItems.addAll(toolItems);

        /*
        // 4b. Equipamiento (Armas y Armaduras)
        ItemTemplate ironSword = new ItemTemplate("Iron Sword", new Material[]{ironIngot, leatherStrip, wood});
        ItemTemplate steelSword = new ItemTemplate("Steel Sword", new Material[]{steelReinforced, leatherStrip, wood});
        ItemTemplate leatherArmor = new ItemTemplate("Leather Armor", new Material[]{leather, leather, thread});
        ItemTemplate clothRobe = new ItemTemplate("Cloth Robe", new Material[]{cloth, thread, thread});
        ItemTemplate runeHelmet = new ItemTemplate("Rune Helmet", new Material[]{steelIngot, rune, leather});

        equipmentItems.addAll(Arrays.asList(ironSword, steelSword, leatherArmor, clothRobe, runeHelmet));
        finalItems.addAll(equipmentItems);

        // 4c. Artesanías y Lujo
        ItemTemplate magicRing = new ItemTemplate("Magic Ring", new Material[]{ironIngot, rune});
        ItemTemplate jeweledNecklace = new ItemTemplate("Jeweled Necklace", new Material[]{ironIngot, thread, gemDust});
        ItemTemplate diamondRing = new ItemTemplate("Diamond Ring", new Material[]{ironIngot, diamond});
        ItemTemplate diamondCrown = new ItemTemplate("Diamond Crown", new Material[]{steelIngot, diamond, gemDust});

        ItemTemplate scrollOfKnowledge = new ItemTemplate("Scroll of Knowledge", new Material[]{scroll, rune, pigment});
        ItemTemplate painting = new ItemTemplate("Painting", new Material[]{wood, dyedCloth});
        ItemTemplate enchantedTorch = new ItemTemplate("Enchanted Torch", new Material[]{oil, rune, wood});

        artisanItems.addAll(Arrays.asList(magicRing, jeweledNecklace, diamondRing, diamondCrown, scroll, scrollOfKnowledge, painting, enchantedTorch));
        finalItems.addAll(artisanItems);
        */
        gameItems.addAll(materialItems);
        gameItems.addAll(finalItems);
    }


    public ArrayList<ItemTemplate> getGameItems() {
        return gameItems;
    }


    public ArrayList<ItemTemplate> getMaterialItems() {
        return materialItems;
    }

    public ArrayList<ItemTemplate> getFoodItems() {
        return foodItems;
    }


    public ArrayList<ItemTemplate> getFinalItems() {
        return finalItems;
    }


    public boolean isMaterial(Items item) {
        for (ItemTemplate template : materialItems) {
            if (template.getName().equals(item.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isFood(Items item) {
        for (ItemTemplate template : foodItems) {
            if (template.getName().equals(item.getName())) {
                return true;
            }
        }
        return false;
    }


    public ItemTemplate getItem(String name) {
        for (ItemTemplate item : gameItems) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<ItemTemplate> getToolItems() {
        return toolItems;
    }

}