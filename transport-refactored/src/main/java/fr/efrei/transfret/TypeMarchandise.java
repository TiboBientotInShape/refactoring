package fr.efrei.transfret;

public enum TypeMarchandise {
    STANDARD("Standard", "les colis standard sont interdits"),
    FRAGILE("Fragile", "les colis fragiles sont interdits"),
    DANGEREUX("Dangereux", "les matières dangereuses sont interdites"),
    REFRIGERE("Réfrigéré", "le transport réfrigéré est impossible");

    private final String libelle;
    private final String messageInterdiction;

    TypeMarchandise(String libelle, String messageInterdiction) {
        this.libelle = libelle;
        this.messageInterdiction = messageInterdiction;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getMessageInterdiction() {
        return messageInterdiction;
    }
}
