package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TarifTransportTest {

    private static final double DELTA = 0.001;

    private static DemandePrix demande(int distance, Colis colis) {
        return new DemandePrix(List.of(colis), distance, ModeTransport.ROUTE, false, false, TypeClient.PARTICULIER);
    }

    private static Colis colis(double poids, double longueur, double largeur, double hauteur) {
        return new Colis("test", poids, longueur, largeur, hauteur, TypeMarchandise.STANDARD, 100);
    }

    @Test
    void tarifRoute() {
        // 100 km x 1,20 + 50 kg x 0,10
        assertEquals(125.0, new TarifRoute().prixDeBase(demande(100, colis(50, 10, 10, 10))), DELTA);
    }

    @Test
    void tarifRail() {
        // 465 x 0,80 + 800 x 0,07
        assertEquals(428.0, new TarifRail().prixDeBase(demande(465, colis(800, 10, 10, 10))), DELTA);
    }

    @Test
    void tarifRailMinimum150() {
        assertEquals(150.0, new TarifRail().prixDeBase(demande(100, colis(50, 10, 10, 10))), DELTA);
    }

    @Test
    void tarifMer() {
        // 5850 x 0,50 + 15000 x 0,03
        assertEquals(3375.0, new TarifMer().prixDeBase(demande(5850, colis(15000, 10, 10, 10))), DELTA);
    }

    @Test
    void tarifMerMinimum500() {
        assertEquals(500.0, new TarifMer().prixDeBase(demande(100, colis(50, 10, 10, 10))), DELTA);
    }

    @Test
    void tarifAirAuPoidsReel() {
        // colis dense : 100 kg > 0,001 m3 x 167
        assertEquals(510.0, new TarifAir().prixDeBase(demande(100, colis(100, 10, 10, 10))), DELTA);
    }

    @Test
    void tarifAirAuPoidsVolumetrique() {
        // 0,48 m3 x 167 = 80,16 kg > 50 kg
        assertEquals(420.72, new TarifAir().prixDeBase(demande(100, colis(50, 100, 80, 60))), DELTA);
    }
}
