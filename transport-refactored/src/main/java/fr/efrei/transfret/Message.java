package fr.efrei.transfret;

/**
 * Contenu d'une notification : un sujet et un corps pour les canaux longs (email),
 * un texte court pour les canaux courts (SMS).
 */
public record Message(String sujet, String corps, String texteCourt) {
}
