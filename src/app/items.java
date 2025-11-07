package app;

class ItemTemplate{
    private String name;
    private int rarity;
    private int craft_needed;
    

    public ItemTemplate(String name, int rarity, int craft_needed) {
        this.rarity = rarity;
        this.craft_needed = craft_needed;
    }

    public String getName(){
        return name;
    }

    public int getRarity(){
        return rarity;
    }
    public int getCraftNeeded(){
        return craft_needed;
    }
    

}// Hacer un Composite para objetos que estén creandose, vendiendose y comprandose, así como objetos que ya estén creados u objetos plantilla? 

class Material extends ItemTemplate{

    public Material(int rarity){
        super("Material",rarity, Integer.MAX_VALUE);

    }
}   

class Items extends ItemTemplate{

    private int age;
    private int quality;

    public Items(String name,int rarity, int craft_needed){
        super(name, rarity, craft_needed);
        this.age = 0;
    }
    public Items(ItemTemplate template,int quality){
        super(template.getName(), template.getRarity(),template.getCraftNeeded());
        this.age = 0;
        this.quality = quality;
    }
    
}