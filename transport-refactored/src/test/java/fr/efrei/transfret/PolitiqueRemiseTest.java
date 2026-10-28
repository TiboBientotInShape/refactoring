package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PolitiqueRemiseTest {

    private static final double DELTA = 0.001;

    @Test
    void aucuneRemisePourUnParticulier() {
        assertEquals(1000.0, new AucuneRemise().appliquer(1000), DELTA);
    }

    @Test
    void remiseEntrepriseDe10Pourcent() {
        assertEquals(900.0, new RemiseEntreprise().appliquer(1000), DELTA);
    }

    @Test
    void remiseEntrepriseDe15PourcentAuDelaDe5000() {
        assertEquals(5100.0, new RemiseEntreprise().appliquer(6000), DELTA);
    }

    @Test
    void remiseEntrepriseA5000PileResteA10Pourcent() {
        assertEquals(4500.0, new RemiseEntreprise().appliquer(5000), DELTA);
    }

    @Test
    void remisePremiumDe20Pourcent() {
        assertEquals(800.0, new RemisePremium().appliquer(1000), DELTA);
    }
}
