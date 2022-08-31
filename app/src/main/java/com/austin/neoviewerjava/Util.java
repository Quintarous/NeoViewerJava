package com.austin.neoviewerjava;

import com.austin.neoviewerjava.database.Neo;
import com.austin.neoviewerjava.network.NeoResponse;

import java.util.ArrayList;

public class Util {

    public static ArrayList<Neo> processNeoResponses(ArrayList<NeoResponse> list) {
        final ArrayList<Neo> outputList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            NeoResponse response = list.get(i);
            outputList.add(new Neo(
                    response.id,
                    response.name,
                    response.designation,
                    response.jplUrl,
                    response.hazardous,
                    response.diameterData.kilometers.diameterMin,
                    response.diameterData.kilometers.diameterMax,
                    response.diameterData.meters.diameterMin,
                    response.diameterData.meters.diameterMax,
                    response.diameterData.miles.diameterMin,
                    response.diameterData.miles.diameterMax,
                    response.diameterData.feet.diameterMin,
                    response.diameterData.feet.diameterMax
            ));
        }
        return outputList;
    }

    public static Neo getSampleNeo() {
        return new Neo(
                1,
                "name",
                "designation",
                "jplUrl",
                true,
                1F,
                1F,
                1F,
                1F,
                1F,
                1F,
                1F,
                1F
        );
    }
}
