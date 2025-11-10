package app;

import java.util.HashMap;

public class ItemTemplate {
    private final String name;
    private final Material[] craft_needed;
    private final HashMap<String, Integer> listofCraft;


    public ItemTemplate(String name, Material[] craft_needed) {
        this.name = name;
        this.craft_needed = craft_needed;
        listofCraft = new HashMap<>();
        if(craft_needed != null)  for (ItemTemplate needed : craft_needed) {
            listofCraft.put(needed.getName(), listofCraft.getOrDefault(needed.getName(), 0) + 1);
        }
    }

    public String getName() {
        return name;
    }

    public Material[] getCraftNeeded() {
        return craft_needed;
    }

    public HashMap<String, Integer> getCraftHashNeeded() {
        return listofCraft;
    }


}
