import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Auto {
    private String znacka;           // značka auta
    private String model;            // model auta
    private double kapacitaPaliva;   // kapacita nádrže v litrech
    private double aktualniPalivo;   // Aktuální množství paliva v litrech
    private double spotreba;         // spotřeba paliva v litrech na 100 km
    private List<CastAuta> casti;    // seznam částí auta

    // konstruktor pro vytvoření nové instance auta
    public Auto(String znacka, String model, double kapacitaPaliva, double spotreba) {
        this.znacka = znacka;
        this.model = model;
        this.kapacitaPaliva = kapacitaPaliva;
        this.spotreba = spotreba;
        // pri vytvoreni je nadr plna
        this.aktualniPalivo = kapacitaPaliva;
        // inicializace seznamu casti
        this.casti = new ArrayList<>();

        // pridani casti
        pridatCast(new CastAuta("Motor", true));
        pridatCast(new CastAuta("Kola", true));
        pridatCast(new CastAuta("Brzdy", true));
        pridatCast(new CastAuta("Palivová nádrž", true));
        pridatCast(new CastAuta("Elektronika", true));
    }

    // simulace jizdy
    public String ridit(double distance) {
        // vypocet pro dojezd paliva
        double potrebaPaliva = (distance / 100) * spotreba;

        // je v nadrzi paliva dostatek?
        if (aktualniPalivo >= potrebaPaliva) {
            // odecteni pouziteho paliva
            aktualniPalivo -= potrebaPaliva;
            // simulace rozbiti po ceste
            simulujRozbiti(distance);
            return "Jeli jste " + distance + " km.";
        } else {
            return "Nedostatek paliva pro cestu.";
        }
    }

    // metoda pro simulaci rozbiti casti auta s pravdepodobnosti zavislou na nahodnosti a delce vyletu
    private void simulujRozbiti(double vzdalenost) {
        Random random = new Random();
        // zakladni pravdepodobnost rozbiti
        double pravdepodobnostRozbiti = 0.001;

        // for each cast auta
        for (CastAuta part : casti) {
            // generovani nahodneho cisla a porovnani s pravdepodobnosti rozbiti
            if (random.nextDouble() < Math.min(pravdepodobnostRozbiti * vzdalenost, 0.2)) { // maximalne 20 procent sance ze se kazda soucastka rozbije
                // nastaveno casti jako rozbita
                part.setFunguje(false);
            }
        }
    }

    // metoda pro doplneni paliva
    public void refuel(double amount) {
        // je zadane mnozstvi kladne?
        if (amount <= 0) {
            // kontrola jestli dojde k preplneni nadrze, zbytek proste asi vytece na zem ig
            System.out.println("Množství paliva k doplnění musí být kladné.");
        } else if (aktualniPalivo + amount > kapacitaPaliva) {
            // nastavení paliva na maximalni moznou kapacitu
            aktualniPalivo = kapacitaPaliva;
        } else {
            // normalni doplneni paliva
            aktualniPalivo += amount;
        }
    }

    // metoda pro kontrolu casti auta
    public boolean zkontrolovatAuto() {
        // for each casti
        for (CastAuta part : casti) {
            // pokud je nejaka rozbita, vraci false
            if (!part.funguje()) {
                return false;
            }
        }
        // pokud jsou vsechny funkcni, true
        return true;
    }

    // metoda pro pridani nove casti
    public void pridatCast(CastAuta part) {
        casti.add(part);
    }

    // gettery

    public String getZnacka() {
        return znacka;
    }

    public String getModel() {
        return model;
    }

    public double getKapacitaPaliva() {
        return kapacitaPaliva;
    }

    public double getAktualniPalivo() {
        return aktualniPalivo;
    }

    public double getSpotreba() {
        return spotreba;
    }

    public List<CastAuta> getCasti() {
        return casti;
    }
}