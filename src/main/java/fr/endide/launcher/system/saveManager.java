package fr.endide.launcher.system;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javafx.concurrent.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class saveManager {
    static fileManager fileManager = new fileManager();
    static Gson gson = createGsonInstance();
    public static boolean setupIsFinish;
    public static String email;
    public static String password;
    public static boolean startupPassword;
    public static List<userProfil.minecraftUserItem> minecraftItems = new ArrayList<>();
    public static List<userProfil.minecraftServer> serverList = new ArrayList<>();
    public static List<userProfil.minecraftVersions> versionsList = new ArrayList<>();
    public static Gson createGsonInstance(){
        return  new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }
    public static void getConfigData(){
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(fileManager.createGameDir() + File.separator + "launcherConfig.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        userProfil users = gson.fromJson(reader, userProfil.class);
        setupIsFinish = users.setupIsFinish;
        email = users.email;
        password = users.password;
        startupPassword = users.startupPassword;
        for(int index = 0; index < users.minecraftUserItems.size(); index++){
            minecraftItems.add(users.minecraftUserItems.get(index));
        }
        for(int index = 0; index < users.minecraftServersItems.size(); index++){
            serverList.add(users.minecraftServersItems.get(index));
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //MinecraftVersions



    //Server
    public static void saveServer(String name, String ram, String api, String version, String path, boolean created){
        userProfil.minecraftServer serverItem = new userProfil.minecraftServer(name, ram, api, version, path, created);
        serverList.add(serverItem);
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileManager.createGameDir() + File.separator + "launcherConfig.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(email);
        userProfil profile = new userProfil(setupIsFinish, email, password, startupPassword, minecraftItems, serverList, versionsList);
        gson.toJson(profile, writer);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Minecraft Versions

    //User
    public static void saveUser(String mcEmail, String username, String token, String uuid, boolean isPremium){
        userProfil.minecraftUserItem userItem = new userProfil.minecraftUserItem(mcEmail, username, token , uuid, isPremium);
        minecraftItems.add(userItem);
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileManager.createGameDir() + File.separator + "launcherConfig.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        userProfil profile = new userProfil(setupIsFinish, email, password, startupPassword, minecraftItems, serverList, versionsList);
        gson.toJson(profile, writer);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<userProfil.minecraftUserItem> getAllAccount(){
        return minecraftItems;
    }


    public void getOneAccount(String username){

    }
    public void deleteAccount(String username){

    }
    public static void changeSettings(boolean startupPasswordEtat){
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileManager.createGameDir() + File.separator + "launcherConfig.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        startupPassword = startupPasswordEtat;
        userProfil profile = new userProfil(true, email, password, startupPassword, minecraftItems, serverList, versionsList);
        gson.toJson(profile, writer);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Setup
    public static void finishSetup(String email, String password){
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileManager.createGameDir() + File.separator + "launcherConfig.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        startupPassword = true;
        userProfil profile = new userProfil(true, email, password, startupPassword, minecraftItems, serverList, versionsList);
        gson.toJson(profile, writer);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


