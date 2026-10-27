package fr.efrei.transfret;

import java.util.Optional;

public class ServiceClient {

    private ValidateurClient validateur = new ValidateurClient();

    public Client creerClient(String nom, String email, String telephone, String adresse, TypeClient type, String siret) {
        Optional<String> erreur = validateur.valider(nom, email, type, siret);
        if (erreur.isPresent()) {
            System.out.println("ERREUR : " + erreur.get());
            return null;
        }
        Client c = new Client(nom, email, telephone, adresse, type, siret);
        Database.getInstance().sauvegarderClient(c);
        EmailService.envoyer(c.getEmail(), "Bienvenue chez TransFret", "Bonjour " + c.getNom() + ", votre compte " + c.getType().name().toLowerCase() + " a été créé.");
        return c;
    }
}
