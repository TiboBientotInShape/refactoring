package fr.efrei.transfret;

import java.util.Optional;

public class ServiceClient {

    private final ClientRepository clients;
    private final ValidateurClient validateur;
    private final CanalNotification email;

    public ServiceClient(ClientRepository clients, ValidateurClient validateur, CanalNotification email) {
        this.clients = clients;
        this.validateur = validateur;
        this.email = email;
    }

    public Client creerClient(String nom, String email, String telephone, String adresse, TypeClient type, String siret) {
        Optional<String> erreur = validateur.valider(nom, email, type, siret);
        if (erreur.isPresent()) {
            System.out.println("ERREUR : " + erreur.get());
            return null;
        }
        Client c = new Client(nom, email, telephone, adresse, type, siret);
        clients.sauvegarder(c);
        this.email.envoyer(c, new Message("Bienvenue chez TransFret",
                "Bonjour " + c.getNom() + ", votre compte " + c.getType().name().toLowerCase() + " a été créé.", null));
        return c;
    }
}
