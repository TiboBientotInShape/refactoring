package fr.efrei.transfret;

/**
 * Véhicule équipé pour garder les marchandises au froid.
 */
public interface Refrigerable {

    double TEMPERATURE_CONSERVATION = 4;

    void activerRefrigeration(double temperature);
}
