package fr.efrei.transfret;

import java.util.List;

/**
 * Tout ce qu'il faut pour calculer un prix, que ce soit pour une expédition ou pour un devis.
 */
public record DemandePrix(List<Colis> colis, int distance, ModeTransport mode, boolean urgent, boolean assurance,
                          TypeClient typeClient) {

    public double poidsTotal() {
        double total = 0;
        for (Colis c : colis) {
            total = total + c.getPoids();
        }
        return total;
    }

    // en m3
    public double volumeTotal() {
        double total = 0;
        for (Colis c : colis) {
            total = total + c.getVolume();
        }
        return total;
    }

    public double valeurTotale() {
        double total = 0;
        for (Colis c : colis) {
            total = total + c.getValeurDeclaree();
        }
        return total;
    }

    public boolean contient(TypeMarchandise type) {
        for (Colis c : colis) {
            if (c.getType() == type) {
                return true;
            }
        }
        return false;
    }
}
