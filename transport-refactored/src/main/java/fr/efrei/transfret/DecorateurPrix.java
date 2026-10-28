package fr.efrei.transfret;

/**
 * Décorateur abstrait : enveloppe un autre calcul de prix et modifie son résultat.
 */
public abstract class DecorateurPrix implements CalculPrix {

    protected final CalculPrix calculEnveloppe;

    protected DecorateurPrix(CalculPrix calculEnveloppe) {
        this.calculEnveloppe = calculEnveloppe;
    }
}
