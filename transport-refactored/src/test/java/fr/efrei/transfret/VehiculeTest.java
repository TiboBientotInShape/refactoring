package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VehiculeTest {

    private static List<Colis> colis(TypeMarchandise type) {
        return List.of(new Colis("test", 100, 50, 40, 30, type, 100));
    }

    @Test
    void unCamionStandardRefuseLeDangereuxSansCertificationAdr() {
        Camion camion = new Camion("AB-123-CD", 19000, false);
        assertTrue(camion.peutPrendreEnCharge(colis(TypeMarchandise.STANDARD), ModeTransport.ROUTE));
        assertFalse(camion.peutPrendreEnCharge(colis(TypeMarchandise.DANGEREUX), ModeTransport.ROUTE));
        assertFalse(camion.peutPrendreEnCharge(colis(TypeMarchandise.REFRIGERE), ModeTransport.ROUTE));
    }

    @Test
    void unCamionAdrAccepteLeDangereux() {
        Camion camion = new Camion("EF-456-GH", 12000, true);
        assertTrue(camion.peutPrendreEnCharge(colis(TypeMarchandise.DANGEREUX), ModeTransport.ROUTE));
    }

    @Test
    void leCamionFrigorifiqueNePrendQueDuRefrigere() {
        CamionFrigorifique frigo = new CamionFrigorifique("IJ-789-KL", 8000);
        assertTrue(frigo.peutPrendreEnCharge(colis(TypeMarchandise.REFRIGERE), ModeTransport.ROUTE));
        assertFalse(frigo.peutPrendreEnCharge(colis(TypeMarchandise.STANDARD), ModeTransport.ROUTE));
    }

    @Test
    void leCamionFrigorifiqueSeChargeCommeUnCamion() {
        // Liskov : utilisé comme un Camion, il ne lève plus d'exception au chargement
        Camion camion = new CamionFrigorifique("IJ-789-KL", 8000);
        camion.charger(colis(TypeMarchandise.STANDARD).get(0));
        assertEquals(100, camion.getChargeActuelle(), 0.001);
    }

    @Test
    void unBateauSansConteneursFrigorifiquesRefuseLeRefrigere() {
        assertFalse(new Bateau("NAV-01", 1000000, false).peutPrendreEnCharge(colis(TypeMarchandise.REFRIGERE), ModeTransport.MER));
        assertTrue(new Bateau("NAV-02", 1000000, true).peutPrendreEnCharge(colis(TypeMarchandise.REFRIGERE), ModeTransport.MER));
    }

    @Test
    void unVehiculeRefuseUnAutreModeQueLeSien() {
        assertFalse(new Train("TR-0001", 600000).peutPrendreEnCharge(colis(TypeMarchandise.STANDARD), ModeTransport.ROUTE));
        assertTrue(new Train("TR-0001", 600000).peutPrendreEnCharge(colis(TypeMarchandise.STANDARD), ModeTransport.RAIL));
        assertFalse(new Avion("F-GTXA", 100000).peutPrendreEnCharge(colis(TypeMarchandise.STANDARD), ModeTransport.MER));
    }
}
