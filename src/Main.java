public class Main {
    public static void main(String[] args) {
        // vytvoření nové instance třídy Garaz, která bude spravovat seznam aut
        Garaz garaz = new Garaz();
        // vytvoření nové instance třídy UI pro interakci s uživatelem
        UI ui = new UI();
        // Zavolání metody userInput na UI, která spustí hlavní loop aplikace
        ui.userInput(garaz);
    }
}
