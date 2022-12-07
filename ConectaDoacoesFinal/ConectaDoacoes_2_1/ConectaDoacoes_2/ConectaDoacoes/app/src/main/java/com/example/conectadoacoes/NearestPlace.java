package com.example.conectadoacoes;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.TextView;

import com.example.conectadoacoes.R;

public class NearestPlace extends AppCompatActivity {

    private ArrayList<Bundle> collectionPlaces = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_place);

        getSupportActionBar().hide();

        TextView result;
        result = findViewById(R.id.txViewResultado);

        // Receber informações da tela anterior
        Bundle userCoordinates = getIntent().getExtras();

        // Popular a lista
        collectionPlaces = PopulateArrayList();

        // Cálculo de local mais próximo
        float distanceNearestPlace = distanceCalculator(userCoordinates, collectionPlaces.get(0));
        float distanceBetweenPlaces;
        int position = 0;
        for(int i = 0; i < collectionPlaces.size(); i++){
            distanceBetweenPlaces = distanceCalculator(userCoordinates, collectionPlaces.get(i));
            if(distanceBetweenPlaces <= distanceNearestPlace){
                distanceNearestPlace = distanceBetweenPlaces;
                position = i;
            }
        }

        // Exibir result
        String show = "Este é o local mais próximo:\n\n" +
                "Nome: " + collectionPlaces.get(position).getString("equipamento_publico_disponivel") +
                "\nEndereço: " + collectionPlaces.get(position).getString("endereco") +
                "\nBairro: " + collectionPlaces.get(position).getString("bairro");
        result.setText(show);

    }

    /**
     * Método para popular o Array list de bundles.
     *
     * Preencher uma lista com dados tipo bundle, contendo as informações dos locais de arrecadação.
     *
     * @author Rodrigo Leão
     * @return ArrayList<Bundle>
     */
    public ArrayList<Bundle> PopulateArrayList() {
        ArrayList<Bundle> bundleArrayList = new ArrayList<>();
        Bundle firstPlace = new Bundle();
        Bundle secondPlace = new Bundle();
        Bundle thirdPlace = new Bundle();

        firstPlace.putInt("_id", 1);
        firstPlace.putString("equipamento_publico_disponivel", "INSTITUTO SOLIDARE");
        firstPlace.putString("municipal_estadual", "ONG");
        firstPlace.putString("endereco", "Rua Alcântara, 176");
        firstPlace.putString("bairro", "COQUEIRAL");
        firstPlace.putDouble("latitude", -8.08923080582917);
        firstPlace.putDouble("longitude", -34.9656872321342);

        secondPlace.putInt("_id", 2);
        secondPlace.putString("equipamento_publico_disponivel", "Escola Municipal Célia Arraes");
        secondPlace.putString("municipal_estadual", "MUNICIPAL - LIBERADO");
        secondPlace.putString("endereco", "Rua José Noya, nº 131, Várzea.");
        secondPlace.putString("bairro", "VÁRZEA");
        secondPlace.putDouble("latitude", -8.039167);
        secondPlace.putDouble("longitude", -34.958817);

        thirdPlace.putInt("_id", 3);
        thirdPlace.putString("equipamento_publico_disponivel", "Escola Municipal Diná de Oliveira");
        thirdPlace.putString("municipal_estadual", "MUNICIPAL - LIBERADO");
        thirdPlace.putString("endereco", "Rua São Mateus, s/n, Iputinga");
        thirdPlace.putString("bairro", "IPUTINGA");
        thirdPlace.putDouble("latitude", -8.030336);
        thirdPlace.putDouble("longitude", -34.93478);

        bundleArrayList.add(firstPlace);
        bundleArrayList.add(secondPlace);
        bundleArrayList.add(thirdPlace);

        return bundleArrayList;

    }

    /**
     * Método para calcular distância entre coordenadas
     *
     * Nesta versão estamos utilizando a fórmula de Haversine para cálculo de distância
     * Obs1.: O valor informada é a distância em linha reta entre os dois pontos
     * Obs2.: Para versões futuras, calcular utilizando as rotas fornecidas pelo Google Maps
     *
     * @author Rodrigo Leão
     * @param origin    bundle com coordenadas da origem
     * @param destiny   bundle com coordenadas do destino
     * @return Float    distância, em Km, entre pontos.
     */
    public Float distanceCalculator(Bundle origin, Bundle destiny){
        Double distance;

        float latOrigin = toRadians(origin.getDouble("latitude"));
        float longOrigin = toRadians(origin.getDouble("longitude"));
        float latDestiny = toRadians(destiny.getDouble("latitude"));
        float longDestiny = toRadians(destiny.getDouble("longitude"));

        float deltaLat = calculateDelta(latOrigin, latDestiny);
        float deltaLong = calculateDelta(longOrigin, longDestiny);

        // a = [sen²(Δlat/2) + cos(lat1)] x cos(lat2) x sen²(Δlong/2)
        distance = (Math.pow(Math.sin(deltaLat/2), 2) + Math.cos(latOrigin))*
                Math.cos(latDestiny)*Math.pow((Math.sin(deltaLong)/2),2);

        // c = 2 x cot(√a/√(1−a))
        distance = 2*Math.pow(Math.tan(Math.sqrt(distance)/Math.sqrt(1-distance)),-1);

        // d = R x c | R = 6,371 km (raio da terra)
        distance = 6.371*distance;

        return distance.floatValue();
    }

    /**
     * Método para converter de graus para radianos
     *
     * @author Rodrigo Leão
     * @param numDouble número em graus
     * @return Float    valor em radianos
     */
    public float toRadians(double numDouble){
        float pi = 3.14159f;
        return (float)numDouble*(pi/180.0f);
    }

    /**
     * Calcular delta: x1 - x2
     *
     * @author Rodrigo Leão
     * @param x1    valor 1
     * @param x2    valor 2
     * @return delta
     */
    public float calculateDelta(float x1, float x2){
        float delta = x1 - x2;
        if (delta < 0){
            return delta*(-1);
        } else {
            return delta;
        }
    }
}

