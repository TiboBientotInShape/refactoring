package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DelaiTransportTest {

    @Test
    void delaiRoute() {
        assertEquals(1, new DelaiRoute().nombreDeJours(465, false));
        assertEquals(2, new DelaiRoute().nombreDeJours(775, false));
        assertEquals(1, new DelaiRoute().nombreDeJours(775, true));
    }

    @Test
    void delaiRail() {
        assertEquals(2, new DelaiRail().nombreDeJours(465, false));
        assertEquals(1, new DelaiRail().nombreDeJours(465, true));
    }

    @Test
    void delaiMer() {
        assertEquals(11, new DelaiMer().nombreDeJours(5850, false));
        assertEquals(4, new DelaiMer().nombreDeJours(760, false));
    }

    @Test
    void delaiAir() {
        assertEquals(2, new DelaiAir().nombreDeJours(5840, false));
        assertEquals(1, new DelaiAir().nombreDeJours(5840, true));
        assertEquals(1, new DelaiAir().nombreDeJours(465, false));
    }

    @Test
    void lEstimateurDonneAuMoinsUnJour() {
        EstimateurDelai estimateur = new EstimateurDelai(Configuration.delaisParMode());
        // 0 km en rail urgent donnerait 0 jour
        assertEquals(1, estimateur.estimer(0, ModeTransport.RAIL, true));
    }
}
