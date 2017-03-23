package com.freemoz.app.dto;

import com.squareup.tape.QueueFile;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BreadCrumbDTOTest extends TestCase {
    public void testSomething() {
        String splat = "/Costumes/By_Type";


        List<String> split = Arrays.asList(splat.split("/"));

        for (int i=split.size() -1; i != 0; i--) {
            System.out.println(String.join("/", split.subList(0, i + 1)) + " " + split.get(i));
        }
    }

    public void testSomethingElse() {
        String t = "https://searchco.de/something/here.php?test".replaceAll("\\W+", " ");
        System.out.println(t);
    }

}
