import java.util.List;

public class UI {

    // printne seznam vsech aut
    public void vytisknoutAuta(Garaz garaz) {
        // Získá seznam aut z objektu garáže
        List<Auto> x = garaz.getAuta();
        // prochazi seznam aut
        for (int i = 0; i < x.size(); i++) {
            // získá auto na aktuálním indexu
            Auto auto = x.get(i);
            // vytiskne pořadí auta- znacku, model na novy radek
            IO.println((i + 1) + (": ") + (auto.getZnacka()) + (" ") + (auto.getModel()) + ("\n"));
        }
    }

    // hlavní menu
    public void ukazMenu() {
        IO.println("----------------------");
        IO.println("1. Přidat auto");
        IO.println("2. Odebrat auto");
        IO.println("3. Zobrazit seznam aut");
        IO.println("4. Vybrat auto a jet na výlet");
        IO.println("5. Vypnout");
        IO.println("----------------------");
    }

    // podmenu pro konkretni auto
    public void detailyAuta(Auto auto) {
        boolean running = true;

        while (running) {
            IO.println("----------------------");
            IO.println("Detaily auta: " + auto.getZnacka() + " " + auto.getModel());
            IO.println("1. Doplnit palivo");
            IO.println("2. Opravit část");
            IO.println("3. Zobrazit detaily auta");
            IO.println("4. Zpět na hlavní menu");
            IO.println("----------------------");

            // vezme a zkontroluje input uzivatele
            int vyber = parseInt(getInput("Zvolte akci: "));

            switch (vyber) {
                case 1 -> {
                    // podiva se na mnozstvi paliva a zkontroluje platnost
                    double amount = parseDouble(getInput("Zadejte množství paliva k doplňení (L): "));
                    if (!Double.isNaN(amount) && amount > 0) {
                        auto.refuel(amount); // metoda pro doplneni paliva
                        IO.println("Doplněno " + amount + " L paliva.");
                    }
                }
                case 2 -> opravitCast(auto); // zavola metodu pro opraveni casti
                case 3 -> zobrazitDetailyAuta(auto); // zavola metodu pro zobrazeni detailu auta
                case 4 -> running = false; // ukonci podmenu a vrati se do hlavniho menu
                default -> IO.println("Neplatná volba.");
            }
        }
    }

    // zobrazi info o aute
    private void zobrazitDetailyAuta(Auto auto) {
        IO.println("----------------------");
        IO.println("Detaily auta: " + auto.getZnacka() + " " + auto.getModel());
        IO.println("Kapacita nádrže: " + auto.getKapacitaPaliva() + " L");
        IO.println("Aktuální množství paliva: " + auto.getAktualniPalivo() + " L");
        IO.println("Spotřeba paliva: " + auto.getSpotreba() + " L/100km");
        IO.println("Stav částí:");
        for (CastAuta part : auto.getCasti()) { // for each cykl pro vsechny soucasti
            IO.println("- " + part.getJmeno() + ": " + (part.funguje() ? "funguje" : "nefunguje"));
        }
        IO.println("----------------------");
    }

    // oprava jedne casti auta
    private void opravitCast(Auto auto) {
        IO.println("----------------------");
        IO.println("Stav částí auta: " + auto.getZnacka() + " " + auto.getModel());
        List<CastAuta> casti = auto.getCasti(); // ziska seznam casti auta

        // vypise cisla casti pro vyber, posune o 1 proto, aby jsme zacinali od 1
        for (int i = 0; i < casti.size(); i++) {
            CastAuta part = casti.get(i);
            IO.println((i + 1) + ": " + part.getJmeno() + " (" + (part.funguje() ? "funguje" : "nefunguje") + ")");
        }
        IO.println("----------------------");

        // nacte cislo casti k oprave a zkontroluje jestli je platne
        int indexOpravy = parseInt(getInput("Zadejte číslo části k opravě: "));

        if (indexOpravy >= 1 && indexOpravy <= casti.size()) {
            CastAuta part = casti.get(indexOpravy - 1); // vezme cast
            if (!part.funguje()) { // pokud nefunguje
                part.oprav(); // zavola metodu na opraveni
                IO.println("Část " + part.getJmeno() + " byla opravena.");
            } else {
                IO.println("Část " + part.getJmeno() + " již funguje.");
            }
        } else {
            IO.println("Neplatné číslo části. Zkuste znovu.");
        }
    }

    // maain loop programu
    public void userInput(Garaz garaz) {
        boolean running = true; // ridici promena pro ukonceni cyklu

        while (running) {
            ukazMenu(); // zobrazi hlavni menu

            // vezme a zkontroluje input uzivatele
            int vyber = parseInt(getInput("Zvolte akci: "));

            switch (vyber) {
                case 1 -> {
                    // bere inputy pro nove auto
                    String vyrobce = "";
                    while (vyrobce.isEmpty()) {
                        vyrobce = getInput("Zadejte značku auta: ").trim();
                        if (vyrobce.isEmpty()) {
                            IO.println("Značka auta nesmí být prázdná. Zkuste to znovu.");
                        }
                    }

                    String model = "";
                    while (model.isEmpty()) {
                        model = getInput("Zadejte model auta: ").trim();
                        if (model.isEmpty()) {
                            IO.println("Model auta nesmí být prázdný. Zkuste to znovu.");
                        }
                    }

                    // zkontroluje ze kapacita nadrze je kladne cislo
                    double kapacitaNadrze;
                    while (true) {
                        kapacitaNadrze = parseDouble(getInput("Zadejte kapacitu nádrže (L): "));
                        if (!Double.isNaN(kapacitaNadrze) && kapacitaNadrze > 0) {
                            break;
                        }
                        IO.println("Kapacita nádrže musí být kladné číslo. Zkuste to znovu.");
                    }

                    // zkontroluje ze kapacita je kladne cislo
                    double spotreba;
                    while (true) {
                        spotreba = parseDouble(getInput("Zadejte spotřebu paliva (L/100km): "));
                        if (!Double.isNaN(spotreba) && spotreba > 0) {
                            break;
                        }
                        IO.println("Spotřeba paliva musí být kladné číslo. Zkuste to znovu.");
                    }

                    // vytvori nove auto a prida ho do garaze
                    Auto newAuto = new Auto(vyrobce, model, kapacitaNadrze, spotreba);
                    if (garaz.addAuto(newAuto)) {
                        IO.println("Auto " + vyrobce + " " + model + " bylo přidáno.");
                    } else {
                        IO.println("Garáž je plná. Maximální počet aut je 5.");
                    }
                }

                case 2 -> {
                    // odebrani auta z garaze
                    List<Auto> autaOdebrat = garaz.getAuta();
                    if (!autaOdebrat.isEmpty()) {
                        vytisknoutAuta(garaz); // vypise seznam aut
                        int indexOdstranit = parseInt(getInput("Zadejte číslo auta k odebrání: "));
                        if (indexOdstranit >= 1 && indexOdstranit <= autaOdebrat.size()) {
                            Auto vybraneAuto = autaOdebrat.get(indexOdstranit - 1);
                            garaz.removeAuto(vybraneAuto); // Odstrani auto z garáže
                            IO.println("Auto " + vybraneAuto.getZnacka() + " " + vybraneAuto.getModel() + " bylo odebráno.");
                        } else {
                            IO.println("Neplatné číslo auta.");
                        }
                    } else {
                        IO.println("V garáži žádná auta nejsou.");
                    }
                }

                case 3 -> {
                    // zobrazi list aut a umozni si jedno vybrat pro jeho detaily
                    List<Auto> auta = garaz.getAuta();
                    if (!auta.isEmpty()) {
                        vytisknoutAuta(garaz);
                        int indexDetaily = parseInt(getInput("Zadejte číslo auta pro detaily: "));
                        if (indexDetaily >= 1 && indexDetaily <= auta.size()) {
                            Auto vybraneAuto = auta.get(indexDetaily - 1);
                            detailyAuta(vybraneAuto); // zavola podmenu pro detaily auta
                        } else {
                            IO.println("Neplatné číslo auta.");
                        }
                    } else {
                        IO.println("V garáži žádná auta nejsou.");
                    }
                }

                case 4 -> {
                    // vyjezd na vylet
                    List<Auto> autaProJizdu = garaz.getAuta();
                    if (!autaProJizdu.isEmpty()) {
                        vytisknoutAuta(garaz);
                        int indexRidit = parseInt(getInput("Zadejte číslo auta pro výlet: "));
                        if (indexRidit >= 1 && indexRidit <= autaProJizdu.size()) {
                            Auto vybraneAuto = autaProJizdu.get(indexRidit - 1);
                            double vzdalenost = parseDouble(getInput("Zadejte vzdálenost pro výlet (km): "));

                            if (!Double.isNaN(vzdalenost) && vzdalenost > 0) {
                                if (vybraneAuto.zkontrolovatAuto()) { // zkontroluje auto
                                    String result = vybraneAuto.ridit(vzdalenost); // spusti jizdu
                                    IO.println(result);
                                } else {
                                    IO.println("Auto nemůže vyjet, protože je nějaká část rozbitá.");
                                }
                            } else {
                                IO.println("Vzdálenost musí být kladné číslo.");
                            }
                        } else {
                            IO.println("Neplatné číslo auta.");
                        }
                    } else {
                        IO.println("V garáži žádná auta nejsou.");
                    }
                }

                case 5 -> running = false; // ukonci main loop
                default -> IO.println("Neplatná volba");
            }
        }
    }

    // pomocna metoda pro nacteni vstupu
    public String getInput(String prompt) {
        return IO.readln(prompt);
    }

    // prevede input na desetine cislo, podporujou se tecky i carky
    public double parseDouble(String input) {
        try {
            String normalized = input.trim().replace(',', '.'); // nahradi carky teckou
            return Double.parseDouble(normalized);
        } catch (NumberFormatException e) {
            return Double.NaN; // vraci not a number
        }
    }

    // prevede input na cele cislo a podiva se jestli je kladne
    public int parseInt(String input) {
        try {
            int value = Integer.parseInt(input.trim());
            if (value <= 0) { // kladnost
                throw new NumberFormatException();
            }
            return value;
        } catch (NumberFormatException e) {
            return -1; // vraci -1 pro chybu
        }
    }
}