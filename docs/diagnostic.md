# Étape 1 – Diagnostic du code historique

## Rôle et dépendances de chaque classe

| Classe | Ce qu'elle fait | Dépend de |
|---|---|---|
| `ServiceClient` | valide un client **et** l'enregistre **et** envoie l'email de bienvenue | `Client`, `Database` (singleton), `EmailService` (statique) |
| `ServiceExpedition` | valide, affecte un véhicule, calcule prix et délai, gère le cycle de vie, envoie emails et SMS, génère les identifiants | `Database`, `CalculateurPrix`, `AffectationVehicule`, `EmailService`, `SmsService`, `Utils`, `Vehicule` |
| `CalculateurPrix` | tarif de base par mode, suppléments, urgence, remise client, assurance, arrondi | `Colis` |
| `AffectationVehicule` | choisit le premier véhicule compatible | `Flotte` (statique), `Camion`, `CamionFrigorifique`, `Train`, `Bateau`, `Avion` |
| `Flotte` | liste statique et modifiable des véhicules | toutes les classes de véhicules |
| `IVehicule` | interface « fourre-tout » de tous les véhicules | `Colis` |
| `Vehicule` | chargement, capacité, disponibilité, plein | `Colis` |
| `Camion`, `CamionFrigorifique`, `Train`, `Bateau`, `Avion` | véhicules concrets | `Vehicule` |
| `Client`, `Colis` | modèle (type en `String` / `int`) | — |
| `Expedition` | modèle **et** génération de la facture texte | `Client`, `Colis`, `Vehicule`, `Utils` |
| `Database` | singleton, maps statiques publiques, faux SQL | `Client`, `Expedition` |
| `EmailService` | méthode statique, journal statique public | — |
| `SmsService` | journal statique public, clé d'API en dur | — |
| `Utils` | dates, arrondi, email, table des distances (cascade de `if`) | — |
| `Main` | démo | presque tout |

## Défauts → emplacement → principe violé

| Défaut | Emplacement | Principe violé |
|---|---|---|
| `ServiceExpedition` valide, affecte, tarife, calcule le délai, gère le cycle de vie et notifie | `ServiceExpedition` | **S** (responsabilité unique) |
| `Expedition.genererFacture()` : une entité fait de la mise en forme | `Expedition` | **S** |
| `ServiceClient` valide, enregistre et envoie l'email de bienvenue | `ServiceClient` | **S** |
| `Utils` mélange dates, prix, emails, distances | `Utils` | **S** |
| `if/else` sur le mode pour le tarif ; ajouter un mode = modifier la classe | `CalculateurPrix.calculerPrixHT` | **O** (ouvert/fermé) |
| `if/else` sur le type de client pour la remise | `CalculateurPrix.calculerPrixHT` | **O** |
| Suppléments en `if` successifs | `CalculateurPrix.calculerPrixHT` | **O** |
| `switch` sur le mode pour le délai | `ServiceExpedition.calculerDelai` | **O** |
| `if` sur le mode pour démarrer (`rouler`/`naviguer`/`voler`) | `ServiceExpedition.demarrerExpedition` | **O**, **L** |
| Cascade d'`instanceof` | `AffectationVehicule.trouverVehicule` | **O**, **L** |
| `switch` sur un entier pour le libellé du type de colis | `Colis.getLibelleType` | **O** |
| `CamionFrigorifique.charger()` refuse les colis non réfrigérés : il ne remplace pas un `Camion` | `CamionFrigorifique` | **L** (Liskov) |
| `Train.faireLePlein()` lève une exception, masquée par un `catch` vide | `Train`, `ServiceExpedition.demarrerExpedition` | **L** |
| `UnsupportedOperationException` dans `rouler`/`naviguer`/`voler`/`activerRefrigeration` | `Camion`, `Train`, `Bateau`, `Avion` | **I** (ségrégation des interfaces), **L** |
| `IVehicule` impose 13 méthodes à tous les véhicules | `IVehicule` | **I** |
| `Database.getInstance()` singleton, maps statiques publiques | `Database`, `ServiceClient`, `ServiceExpedition`, `LegacyAdapter` | **D** (inversion des dépendances) |
| `EmailService.envoyer()` statique, `new SmsService()` en dur | `ServiceClient`, `ServiceExpedition` | **D** |
| `new CalculateurPrix()`, `new AffectationVehicule()` en dur | `ServiceExpedition` | **D** |
| `Flotte.vehicules`, `ServiceExpedition.compteur`, `Utils.FORMAT_DATE`, `EmailService.journal`, `SmsService.journal` : `static` modifiables (que `LegacyAdapter` doit vider à la main) | `Flotte`, `ServiceExpedition`, `Utils`, `EmailService`, `SmsService` | **D** (état global) |

### Autres défauts (hors SOLID)
- Nombres magiques : `1.2`, `0.10`, `0.8`, `150`, `500`, `167`, `4.5`, `1.15`, `1.30`, `5000`, `0.85`, `70`, `9`, `18`, `1440`, `720`, `3000`, `1.852`, `35000`…
- Codes de type en `String` (`"ROUTE"`, `"PREMIUM"`, `"CREEE"`) et en `int` (`getType() == 3`).
- `Math.round(x * 100.0) / 100.0` répété (alors que `Utils.arrondir` existe).
- Calculs dupliqués : poids / volume / valeur totale (`Expedition`, `CalculateurPrix`, `AffectationVehicule`), détection dangereux / réfrigéré (×3).
- Erreurs signalées par `System.out.println` + `return null`.
- Clé d'API en dur dans `SmsService` ; SQL construit par concaténation (injection).

## Endroits à modifier dans le code historique

### Ajouter un mode `FLUVIAL`
1. `CalculateurPrix.calculerPrixHT` : nouvelle branche tarif + minimum.
2. `ServiceExpedition.calculerDelai` : nouveau `case`.
3. `ServiceExpedition.creerExpedition` : règle « réfrigéré » (`mode.equals("ROUTE") || mode.equals("MER")`) et règle « pas d'urgence ».
4. `ServiceExpedition.demarrerExpedition` : branche `naviguer` pour le fluvial.
5. `AffectationVehicule.trouverVehicule` : nouvelle branche + `instanceof Peniche`.
6. Nouvelle classe `Peniche` qui implémente toutes les méthodes de `IVehicule` (avec des `UnsupportedOperationException`).
7. `Flotte.initialiser` : ajouter la péniche.
8. `Utils.getDistance` : ajouter le trajet Paris → Le Havre.

→ **8 endroits dans 5 fichiers existants** (`CalculateurPrix`, `ServiceExpedition`, `AffectationVehicule`,
`Flotte`, `Utils`) **+ 1 nouvelle classe** (`Peniche`).

### Ajouter un supplément « hors gabarit »
1. `CalculateurPrix.calculerPrixHT` : repérer un colis de plus de 200 cm dans la boucle sur les colis + une ligne `if`
   dans les suppléments.

→ **1 fichier existant modifié** (`Colis` expose déjà ses dimensions), mais c'est une classe qui grossit à
chaque supplément.
