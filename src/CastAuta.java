public class CastAuta {
    // pro ulozeni nazvu casti
    private String jmeno;
    // jestli cast funguje
    private boolean funguje;

    // konstruktor pro novou cast auta
    public CastAuta(String jmeno, boolean funguje) {
        this.jmeno = jmeno;
        this.funguje = funguje;
    }

    // metoda pro opraveni casti
    public void oprav() {
        // nastaví na funkční
        this.funguje = true;
    }

    // metoda vracejici true pokud cast funguje
    public boolean funguje() {
        return funguje;
    }

    //nastavi stav casti funguje/nefunguje
    public void setFunguje(boolean funguje) {
        this.funguje = funguje;
    }

    //vrati jmeno casti
    public String getJmeno() {
        return jmeno;
    }
}