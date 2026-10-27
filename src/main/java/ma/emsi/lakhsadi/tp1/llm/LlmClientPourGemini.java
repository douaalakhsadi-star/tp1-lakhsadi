package ma.emsi.lakhsadi.tp1.llm;

import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;

/**
 * Gère l'interface avec l'API de Gemini.
 * Lance une requête à chaque nouvelle question.
 */
@Dependent
public class LlmClientPourGemini implements Serializable {

    // Clé pour l'API du LLM
    private final String key;

    // Client REST, conservé pour pouvoir le fermer
    private Client clientRest;

    // Endpoint du serveur REST
    private final WebTarget target;

    public LlmClientPourGemini() {
        // Récupère la clé depuis la variable d'environnement Windows.
        this.key = System.getenv("GEMINI_KEY");

        // Client REST pour envoyer les requêtes.
        this.clientRest = ClientBuilder.newClient();

        // Remplacer MODELE_DU_COURS par le modèle indiqué dans le support.
        this.target = clientRest.target(
                "https://generativelanguage.googleapis.com/v1beta/models/"
                        + "gemini-3.5-flash-lite"
                        + ":generateContent"
        ).queryParam("key", this.key);
    }

    /**
     * Envoie une requête à l'API de Gemini.
     *
     * @param requestEntity le corps de la requête en JSON.
     * @return la réponse REST de l'API.
     */
    public Response envoyerRequete(Entity requestEntity) {
        Invocation.Builder request =
                target.request(MediaType.APPLICATION_JSON_TYPE);

        return request.post(requestEntity);
    }

    public void closeClient() {
        this.clientRest.close();
    }
}