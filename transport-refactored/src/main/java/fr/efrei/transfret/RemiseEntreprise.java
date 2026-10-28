package fr.efrei.transfret;

public class RemiseEntreprise implements PolitiqueRemise {

    private static final double SEUIL_GROS_MONTANT = 5000;
    private static final double COEF_GROS_MONTANT = 0.85;
    private static final double COEF_NORMAL = 0.90;

    @Override
    public double appliquer(double prix) {
        if (prix > SEUIL_GROS_MONTANT) {
            return prix * COEF_GROS_MONTANT;
        }
        return prix * COEF_NORMAL;
    }
}
