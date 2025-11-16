package app;


import java.util.Random;

public class Items extends ItemTemplate {
    private int quality;
    private int duration;

    public Items(String name, Material[] craft_needed) {
        super(name, craft_needed);
    }

    public Items(ItemTemplate template, int quality) {
        super(template.getName(), template.getCraftNeeded());
        this.quality = quality;
        Random rand = new Random();
        duration = quality+rand.nextInt(20);
    }

    public Items(String name) {
        super(name, null);
    }

    public int getQuality() {
        return quality;
    }

    public boolean useItem(){
        duration--;
        return duration >= 0;
    }
}


