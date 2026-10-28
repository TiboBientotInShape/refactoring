package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecorateurPrixTest {

    private static final double DELTA = 0.001;

    // un "faux" tarif de base qui vaut toujours 100 €, pour tester chaque décorateur seul
    private static final CalculPrix CENT_EUROS = demande -> 100.0;

    private static DemandePrix demande(TypeMarchandise type, boolean urgent, boolean assurance, double valeur) {
        Colis colis = new Colis("test", 10, 50, 40, 30, type, valeur);
        return new DemandePrix(List.of(colis), 100, ModeTransport.ROUTE, urgent, assurance, TypeClient.PARTICULIER);
    }

    @Test
    void supplementFragile() {
        assertEquals(115.0, new SupplementFragile(CENT_EUROS).calculer(demande(TypeMarchandise.FRAGILE, false, false, 0)), DELTA);
        assertEquals(100.0, new SupplementFragile(CENT_EUROS).calculer(demande(TypeMarchandise.STANDARD, false, false, 0)), DELTA);
    }

    @Test
    void supplementDangereux() {
        assertEquals(130.0, new SupplementDangereux(CENT_EUROS).calculer(demande(TypeMarchandise.DANGEREUX, false, false, 0)), DELTA);
        assertEquals(100.0, new SupplementDangereux(CENT_EUROS).calculer(demande(TypeMarchandise.FRAGILE, false, false, 0)), DELTA);
    }

    @Test
    void supplementRefrigere() {
        assertEquals(120.0, new SupplementRefrigere(CENT_EUROS).calculer(demande(TypeMarchandise.REFRIGERE, false, false, 0)), DELTA);
        assertEquals(100.0, new SupplementRefrigere(CENT_EUROS).calculer(demande(TypeMarchandise.STANDARD, false, false, 0)), DELTA);
    }

    @Test
    void supplementUrgence() {
        assertEquals(150.0, new SupplementUrgence(CENT_EUROS).calculer(demande(TypeMarchandise.STANDARD, true, false, 0)), DELTA);
        assertEquals(100.0, new SupplementUrgence(CENT_EUROS).calculer(demande(TypeMarchandise.STANDARD, false, false, 0)), DELTA);
    }

    @Test
    void remiseDelegueALaPolitiqueDuClient() {
        Remise remise = new Remise(CENT_EUROS, Configuration.remisesParTypeClient());
        Colis colis = new Colis("test", 10, 50, 40, 30, TypeMarchandise.STANDARD, 0);
        DemandePrix premium = new DemandePrix(List.of(colis), 100, ModeTransport.ROUTE, false, false, TypeClient.PREMIUM);
        assertEquals(80.0, remise.calculer(premium), DELTA);
    }

    @Test
    void assuranceDe2PourcentDeLaValeur() {
        assertEquals(120.0, new Assurance(CENT_EUROS).calculer(demande(TypeMarchandise.STANDARD, false, true, 1000)), DELTA);
    }

    @Test
    void assuranceMinimum15Euros() {
        assertEquals(115.0, new Assurance(CENT_EUROS).calculer(demande(TypeMarchandise.STANDARD, false, true, 100)), DELTA);
    }

    @Test
    void pasDAssuranceSiNonDemandee() {
        assertEquals(100.0, new Assurance(CENT_EUROS).calculer(demande(TypeMarchandise.STANDARD, false, false, 1000)), DELTA);
    }

    @Test
    void lesSupplementsSEnchainent() {
        CalculPrix chaine = new SupplementDangereux(new SupplementFragile(CENT_EUROS));
        Colis fragile = new Colis("a", 10, 50, 40, 30, TypeMarchandise.FRAGILE, 0);
        Colis dangereux = new Colis("b", 10, 50, 40, 30, TypeMarchandise.DANGEREUX, 0);
        DemandePrix demande = new DemandePrix(List.of(fragile, dangereux), 100, ModeTransport.ROUTE, false, false,
                TypeClient.PARTICULIER);
        assertEquals(149.5, chaine.calculer(demande), DELTA);
    }

    @Test
    void laRemiseEntrepriseSeCalculeApresLUrgence() {
        // 5000 kg de solvant Paris -> Marseille en urgent : 1430 x 1,3 x 1,5 = 2788,50, donc remise de 10 %
        AssemblagePrix assemblage = new AssemblagePrix(Configuration.tarifsParMode(), Configuration.remisesParTypeClient());
        CalculateurPrix calculateur = new CalculateurPrix(assemblage.assembler());
        Colis futs = new Colis("Fûts de solvant", 5000, 240, 120, 150, TypeMarchandise.DANGEREUX, 20000);
        DemandePrix demande = new DemandePrix(List.of(futs), 775, ModeTransport.ROUTE, true, false, TypeClient.ENTREPRISE);
        assertEquals(2509.65, calculateur.calculerPrixHT(demande), DELTA);
    }
}
