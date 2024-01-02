package Utilities.General;


import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.nio.file.Path;
import java.util.*;


public class GetInputs {
    String sessionCookie = "53616c7465645f5f931f38c2c38ea4617325ca47539e3ffec1ecc2e75e0cfd5d576eb5b719595552fd0b5b3c6ae386fbee55459b0356ea96c13bc49209a6426d";
    String URLTemplate = "https://adventofcode.com/2023/day/1/input";
    int day = 0;
    private final OkHttpClient _httpClient = new OkHttpClient();

    String file = "";
    Scanner stdin;

    public GetInputs(int year,int day) throws IOException {
        URLTemplate = "https://adventofcode.com/20" + year + "/day/" + day + "/input";
        String y = convertYearToPath(year);
        this.day = day;
        file = "C:\\Users\\nkh15448\\IdeaProjects\\AdventOfCode\\src\\inputs\\" + y + "\\input" + +day + ".txt";
        if(!new File(file).exists()) {
            FileWriter fle = new FileWriter(new File(file));
        }
        stdin = new Scanner(new File(file));
    }
    HttpUrl getRemotePuzzleInputUrl() {
        return HttpUrl.get(URI.create(URLTemplate));
    }
    public String fetchInput() throws IOException {
        var request = new Request.Builder()
                .url(getRemotePuzzleInputUrl())
                .header("Cookie", "session=" + sessionCookie)
                .get()
                .build();
        try{

        var response = _httpClient.newCall(request).execute();
            if (response.code() != 200) {
                throw new IOException("Request was not successful. Status code = " + response.code());
            }
            var body = response.body();
            if (body == null) {
                throw new IOException("Request body was empty");
            }
            return body.string();
        }catch(Exception e){
            System.out.println("Failure");
            return null;
        }

    }

    public boolean hasLines() throws FileNotFoundException {
        return stdin.hasNextLine();
    }
    public String nextLine(){
        return stdin.nextLine();
    }
    public int nextInt(){
        return stdin.nextInt();
    }
    public String next(){
        return stdin.next();
    }
    public long nextLong(){
        return stdin.nextLong();
    }
    public double nextDouble(){
        return stdin.nextDouble();
    }



    public String wholeFile(){
        String result = "";
        while(stdin.hasNextLine()){

            result += stdin.nextLine();
            if(!(stdin.hasNextLine())){
                result += "\n";
            }
        }
        return result;
    }
    public ArrayList<String> filetoArrayList(){
        ArrayList<String> result = new ArrayList<String>();
        while(stdin.hasNextLine()){
            result.add(stdin.nextLine());
        }
        return result;
    }
    public String[] filetoArray(){
        ArrayList<String> result = new ArrayList<String>();
        while(stdin.hasNextLine()){
            result.add(stdin.nextLine());
        }
        String[] retrn = new String[result.size()];
        for(int i = 0; i < result.size(); i++){
            retrn[i] = result.get(i);
        }
        return retrn;
    }

    public char[][] getAsCharArray(){
        ArrayList<String> lines = this.filetoArrayList();
        char[][] res = new char[lines.size()][lines.stream().map(x->x.length()).mapToInt(Integer::intValue).max().getAsInt()];
        for(int i = 0; i < lines.size(); i++){
            for(int j = 0 ; j < lines.get(i).length(); j++){
                res[i][j] = lines.get(i).charAt(j);
            }
        }
        return res;
    }
    public ArrayList<ArrayList<Character>> getAsCharArrayList(){
        ArrayList<String> lines = this.filetoArrayList();
        ArrayList<ArrayList<Character>> res = new ArrayList<>();
        for(int i = 0 ; i  <lines.size(); i++){
            ArrayList<Character> temp = new ArrayList<>();
            for(int j = 0 ; j < lines.get(i).length(); j++){
                temp.add(lines.get(i).charAt(j));
            }
            res.add(temp);
        }
        return res;
    }

    public ArrayList<ArrayList<Character>> fetchAsCharArrayList() throws IOException {
        ArrayList<String> lines = (ArrayList<String>) Arrays.asList( this.fetchInput().split("\n"));
        ArrayList<ArrayList<Character>> res = new ArrayList<>();
        for(int i = 0 ; i  <lines.size(); i++){
            ArrayList<Character> temp = new ArrayList<>();
            for(int j = 0 ; j < lines.get(i).length(); j++){
                temp.add(lines.get(i).charAt(j));
            }
            res.add(temp);
        }
        return res;
    }



    public String convertYearToPath(int y){
        HashMap<Integer,String> map = new HashMap<Integer,String>(Map.of(0,"zero",1, "one", 2, "two", 3, "three",  4, "four", 5, "five", 6, "six", 7, "seven", 8, "eight", 9, "nine"));
       String tens = map.get(Integer.parseInt(String.valueOf(y).substring(0,1)));
       String ones = map.get(Integer.parseInt(String.valueOf(y).substring(1,2)));
       return tens + ones;

    }

}
