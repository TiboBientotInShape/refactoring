package fr.efrei.transfret;

/**
 * Strategy : délai de livraison d'un mode de transport, en jours (le minimum d'un jour est appliqué par
 * {@link EstimateurDelai}).
 */
public interface DelaiTransport {

    int nombreDeJours(int distance, boolean urgent);
}
