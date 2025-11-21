import java.util.*;

class Weapon {
    String name;
    int damage;

    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }
    @Override
    public String toString(){
        return this.name;
    }
}

public class test {

    static Scanner scanner = new Scanner(System.in);

    enum Action {
        SAVE,
        REMOVEANIMAL,
        WEAPON,
        FRUIT,
        ANIMAL
    }

    enum Category {
        POME,
        TROPICAL,
        BERRY
    }

    enum Animal {
        CAT,
        DOG,
        BIRD,
        FROG,
        HORSE
    }

    enum Fruit {
        APPLE(Category.POME),
        PEAR(Category.POME),
        QUINCE(Category.POME),

        BANANA(Category.TROPICAL),
        MANGO(Category.TROPICAL),
        PINEAPPLE(Category.TROPICAL),

        CHERRY(Category.BERRY),
        STRAWBERRY(Category.BERRY),
        BLUEBERRY(Category.BERRY);

        public final Category category;

        Fruit(Category category) {
            this.category = category;
        }
    }


    public static Action chooseAction(boolean hasSave, boolean hasLoad, boolean hasWeapon, boolean hasFruit, boolean hasAnimal) {
        int menuOption = 1;
        List<Action> choices = new ArrayList<>();

        System.out.println("Wähle:");

        if (hasSave) {
            System.out.println(menuOption + ".: Save");
            choices.add(Action.SAVE);
            menuOption++;
        }
        if (hasLoad) {
            System.out.println(menuOption + ".: Tier Entfernen");
            choices.add(Action.REMOVEANIMAL);
            menuOption++;
        }
        if (hasWeapon) {
            System.out.println(menuOption + ".: Weapon");
            choices.add(Action.WEAPON);
            menuOption++;
        }
        if (hasFruit) {
            System.out.println(menuOption + ".: Fruit");
            choices.add(Action.FRUIT);
            menuOption++;
        }
        if (hasAnimal) {
            System.out.println(menuOption + ".: Animal");
            choices.add(Action.ANIMAL);
            menuOption++;
        }

        int selection = Integer.parseInt(scanner.next()) - 1;
        return choices.get(selection);
    }


    public static Weapon exerciseDemo(List<Weapon> inventory) {
        int menuOption = 1;
        List<Weapon> inventoryChoice = new ArrayList<>();

        System.out.println("Wähle: ");

        for (Weapon w : inventory) {
            System.out.println(menuOption + ".: " + w + ": " + w.damage + " Schaden");
            inventoryChoice.add(w);
            menuOption++;
        }

        int selection = Integer.parseInt(scanner.next()) - 1;
        return inventoryChoice.get(selection);
    }

    public static Category chooseFruitCategory() {
        int menuOption = 1;
        List<Category> choices = new ArrayList<>();

        System.out.println("Wähle:");

        for (Category c : Category.values()) {
            System.out.println(menuOption + ".: " + c);
            choices.add(c);
            menuOption++;
        }
        int selection = Integer.parseInt(scanner.next());
        return choices.get(selection - 1);
    }

    public static Fruit chooseFruit(Category c) {
        int menuOption = 1;
        List<Fruit> choices = new ArrayList<>();

        System.out.println("Wähle:");

        for (Fruit fruit : Fruit.values()) {
            if (fruit.category == c) {
                System.out.println(menuOption + ".: " + fruit);
                choices.add(fruit);
                menuOption++;
            }
        }

        int selection = Integer.parseInt(scanner.next());
        return choices.get(selection - 1);
    }

    public static Animal chooseAnimalFromList(List<Animal> animals) {
        int menuOption = 1;
        List<Animal> choices = new ArrayList<>();

        System.out.println("Wähle ein Tier: ");

        for (Animal a : animals) {
            System.out.println(menuOption + ".: " + a);
            choices.add(a);
            menuOption++;
        }

        int selection = Integer.parseInt(scanner.next());
        return choices.get(selection - 1);
    }

    public static Optional<Animal> chooseAnimalWithBack(List<Animal> animals){

        int menuOption = 1;
        List<Animal> choices = new ArrayList<>();

        System.out.println("Wähle ein Tier: ");

        for (Animal a : animals) {
            System.out.println(menuOption + ".: " + a);
            choices.add(a);
            menuOption++;
        }

        System.out.println(menuOption + ".: Zurück");

        int selection = Integer.parseInt(scanner.next());

        if (menuOption == selection){
            return Optional.empty();
        }
        return Optional.of(choices.get(selection -1));
    }

    public static void chooseAndRemoveAnimal(List<Animal> animals){
        if (animals.isEmpty()){
            return;
        }
        int menuOption = 1;
        List<Animal> choices = new ArrayList<>();

        System.out.println("Wähle ein Tier: ");

        for (Animal a : animals) {
            System.out.println(menuOption + ".: " + a);
            choices.add(a);
            menuOption++;
        }

        int selection = Integer.parseInt(scanner.next());

        System.out.println("Entferne " + animals.get(selection-1) + "." );
        animals.remove(animals.get(selection-1));
    }


    public static void main(String[] args) {
        List<Weapon> weapons = new ArrayList<>();
        weapons.add(new Weapon("Dolch", 5));
        weapons.add(new Weapon("Speer", 10));
        weapons.add(new Weapon("Großschwert", 25));
        List<Animal> animals = new ArrayList<>();
        animals.add(Animal.BIRD);
        animals.add(Animal.DOG);
        animals.add(Animal.FROG);
        animals.add(Animal.CAT);
        animals.add(Animal.HORSE);

        while (true) {
            Action menuChoice = chooseAction(true, true, true, true, true);
            if (menuChoice == Action.SAVE){
                System.out.println("ToDo");
            }
            else if (menuChoice == Action.REMOVEANIMAL){
                chooseAndRemoveAnimal(animals);
                System.out.println("Verbliebene Tiere: " + animals);
            }
            else if (menuChoice == Action.WEAPON){
                System.out.println(exerciseDemo(weapons));
            }
            else if (menuChoice == Action.FRUIT){
                System.out.println(chooseFruit(chooseFruitCategory()));
            }
            else if (menuChoice == Action.ANIMAL){
                Optional<Animal> choice = chooseAnimalWithBack(animals);
                if (choice.isPresent()){
                    System.out.println(choice.get());
                }
                else {
                    System.out.println("Zurück gewählt.");
                }
            }

            System.out.println("Continue? j/n: ");
            String anotherLoop = scanner.next();
            if (anotherLoop.equals("n")){
                break;
            }
        }
    }
}


