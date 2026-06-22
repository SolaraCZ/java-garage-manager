import java.util.ArrayList;
import java.util.List;

public class Garaz {
    private List<Auto> auta; // Seznam pro uložení objektů typu Auto
    private static final int MAX_AUT_V_GARAZI = 5; // maximální kapacita garáže

    // konstruktor třídy Garaz
    public Garaz() {
        // inicializace seznamu aut jako arraylist
        this.auta = new ArrayList<>();
        // zavolání metody pro přednastavení aut
        prednastavitAuta();
    }

    // zavolání metody pro nastavení nějakých aut na začátku
    private void prednastavitAuta() {
        // vytvoření instancí třídy Auto
        auta.add(new Auto("Toyota", "Corolla", 50, 6.5));
        auta.add(new Auto("Ford", "Focus", 55, 7.0));
        auta.add(new Auto("Volkswagen", "Golf", 60, 5.8));
    }

    // Přidá auto do garáže, pokud není dosaženo maximální kapacity - return true, pokud bylo auto přidáno, jinak false.
    public boolean addAuto(Auto auto) {
        if (auta.size() >= MAX_AUT_V_GARAZI) {
            return false;
        }
        // přidání auta
        auta.add(auto);
        return true;
    }

    // odebere auto z garáže
    public void removeAuto(Auto car) {
        // odebrání daného objektu auto
        auta.remove(car);
    }


    // vrátí kopii seznamu všech aut v garáži
    public List<Auto> getAuta() {
        // vrácení nového arraylistu, aby se zabránilo jeho změně
        return new ArrayList<>(auta);
    }
}