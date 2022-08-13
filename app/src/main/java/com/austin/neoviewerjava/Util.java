package com.austin.neoviewerjava;

import com.austin.neoviewerjava.database.Neo;

public class Util {

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
