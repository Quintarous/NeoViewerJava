package com.austin.neoviewerjava;

import com.austin.neoviewerjava.database.FeedNeo;
import com.austin.neoviewerjava.database.Neo;
import com.austin.neoviewerjava.network.FeedNeoResponse;
import com.austin.neoviewerjava.network.NeoResponse;

import java.util.ArrayList;
import java.util.List;

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


    /**
     * Takes a day string and a list of [FeedNeoResponse] and outputs a list of [FeedNeo] objects
     * which can be inserted into the database.
     * @param day the date string this list of feed neo's are associated with
     * @param list a list of FeedNeoResponse objects that will be converted to FeedNeo objects
     * @return a list of FeedNeo
     */
    public static List<FeedNeo> processFeedData(String day, List<FeedNeoResponse> list) {
        ArrayList<FeedNeo> outputList = new ArrayList<FeedNeo>();
        for (int i = 0; i < list.size(); i++) {
            FeedNeoResponse response = list.get(i);
            outputList.add(new FeedNeo(
                    response.id,
                    response.name,
                    response.jplUrl,
                    response.hazardous,
                    response.diameterData.kilometers.diameterMin,
                    response.diameterData.kilometers.diameterMax,
                    response.diameterData.meters.diameterMin,
                    response.diameterData.meters.diameterMax,
                    response.diameterData.miles.diameterMin,
                    response.diameterData.miles.diameterMax,
                    response.diameterData.feet.diameterMin,
                    response.diameterData.feet.diameterMax,
                    day)
            );
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
