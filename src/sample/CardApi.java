package sample;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 46990527d on 02/12/16.
 */
public class CardApi {

    private static String url="https://api.magicthegathering.io/v1/cards?page=10&pageSize=20";

    public static ArrayList<Card> getCards() {

        ArrayList<Card> cards = new ArrayList<>();

        try {

            JSONObject jsonO = new JSONObject(HttpUtils.get(url));
            JSONArray jsonCards = jsonO.getJSONArray("cards");

            for (int i = 0; i < jsonCards.length(); ++i) {

                JSONObject objeto = jsonCards.getJSONObject(i);

                Card Carta = new Card();
                Carta.setName(objeto.getString("name"));
                Carta.setType(objeto.getString("type"));
                Carta.setRarity(objeto.getString("rarity"));
                if (objeto.has("text")){
                    Carta.setText(objeto.getString("text"));
                }

                System.out.println("colors => "+objeto.has("colors"));
                if (objeto.has("colors")) {
                    JSONArray arr = objeto.getJSONArray("colors");
                    Carta.setColors(arr.join(", "));
                } else {
                    Carta.setColors(null);
                }

                if (objeto.has("imageUrl")){
                    Carta.setImageUrl(objeto.getString("imageUrl"));
                }else{
                    Carta.setImageUrl(null);
                }

                cards.add(Carta);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cards;

    }

    public static ArrayList<Card> getCards(String filter) {

        ArrayList<Card> cards = new ArrayList<>();

        try {

            JSONObject jsonO = new JSONObject(HttpUtils.get(url+"?"+filter));
            JSONArray jsonCards = jsonO.getJSONArray("cards");

            for (int i = 0; i < jsonCards.length(); ++i) {

                JSONObject objeto = jsonCards.getJSONObject(i);

                Card Carta = new Card();
                Carta.setName(objeto.getString("name"));
                Carta.setType(objeto.getString("type"));
                Carta.setRarity(objeto.getString("rarity"));
                if (objeto.has("text")){
                    Carta.setText(objeto.getString("text"));
                }

                System.out.println("colors => "+objeto.has("colors"));
                if (objeto.has("colors")) {
                    JSONArray arr = objeto.getJSONArray("colors");
                    Carta.setColors(arr.join(", "));
                } else {
                    Carta.setColors(null);
                }

                if (objeto.has("imageUrl")){
                    Carta.setImageUrl(objeto.getString("imageUrl"));
                }else{
                    Carta.setImageUrl(null);
                }

                cards.add(Carta);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cards;

    }

}


