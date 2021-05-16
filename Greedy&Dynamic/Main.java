import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.stream.JsonReader;


import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    /**
     * Propogated {@link IOException} here
     * {@link #readFile} and {@link #writeOutput} methods should be called here
     * A {@link Scheduler} instance must be instantiated here
     */
    public static void main(String[] args) throws IOException {
        String file = args[0];              //get file name as an argument
        Assignment[] assignments = readFile(file);
        Arrays.sort(assignments);           //sort assingment array for binary searcch
        Scheduler sc = new Scheduler(assignments);
        writeOutput("solution_dynamic.json",sc.scheduleDynamic());      //write dynamic solution
        writeOutput("solution_greedy.json",sc.scheduleGreedy());        //write greedy solution

    }

    /**
     * @param filename json filename to read
     * @return Returns a list of {@link Assignment}s obtained by reading the given json file
     * @throws FileNotFoundException If the given file does not exist
     */
    private static Assignment[] readFile(String filename) throws FileNotFoundException {
        /* File reading operations here */

        Gson gson = new Gson();          //new Gson object to read json file
        JsonReader jr = new JsonReader(new FileReader(filename));
        Assignment[] duties = gson.fromJson(jr, Assignment[].class);    //append duties array
        return duties;
    }

    /**
     * @param filename  json filename to write
     * @param arrayList a list of {@link Assignment}s to write into the file
     * @throws IOException If something goes wrong with file creation
     */
    private static void writeOutput(String filename, ArrayList<Assignment> arrayList) throws IOException {
        /*writing output operations are applied here*/

        Writer writer = new FileWriter(filename);       //new Writer Object
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();   //I used pretty printing for good looking
        gson.toJson(arrayList,writer);
        writer.close();

    }
}
