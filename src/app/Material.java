package app;

public class Material extends Items {


    public Material(String name) {
        super(name);
    }
    public Material(String name, Material[] items) {
        super(name,items);
    }
    public Material(ItemTemplate itemTemplate) {
        super(itemTemplate.getName());
    }
}
