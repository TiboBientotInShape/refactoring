package fr.efrei.transfret;

import java.util.Map;

/**
 * Assemble la chaîne de décorateurs du prix : quels suppléments, et dans quel ordre.
 * <p>
 * Chaque supplément vérifie lui-même s'il concerne l'expédition, la chaîne est donc la même pour tout le monde.
 * L'ordre compte : la remise entreprise dépend du prix après suppléments et urgence,
 * et l'assurance s'ajoute après la remise.
 */
public class AssemblagePrix {

    private final Map<ModeTransport, TarifTransport> tarifs;
    private final Map<TypeClient, PolitiqueRemise> remises;

    public AssemblagePrix(Map<ModeTransport, TarifTransport> tarifs, Map<TypeClient, PolitiqueRemise> remises) {
        this.tarifs = tarifs;
        this.remises = remises;
    }

    public CalculPrix assembler() {
        CalculPrix calcul = new TarifDeBase(tarifs);
        calcul = new SupplementFragile(calcul);
        calcul = new SupplementDangereux(calcul);
        calcul = new SupplementRefrigere(calcul);
        calcul = new SupplementUrgence(calcul);
        calcul = new Remise(calcul, remises);
        calcul = new Assurance(calcul);
        return calcul;
    }
}
