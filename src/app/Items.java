package app;


public class Items extends ItemTemplate {
    private int quality;

    public Items(String name, Material[] craft_needed) {
        super(name, craft_needed);
    }

    public Items(ItemTemplate template, int quality) {
        super(template.getName(), template.getCraftNeeded());
        this.quality = quality;
    }

    public Items(String name) {
        super(name, null);
    }

    public int getQuality() {
        return quality;
    }
}


