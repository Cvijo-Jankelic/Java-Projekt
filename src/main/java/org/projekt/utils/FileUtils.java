package org.projekt.utils;

import org.projekt.entity.AppUser;
import org.projekt.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static final String usersFileName = "users/username_passwords.txt";
    private static final String serializeFileName = "users/users_pass.ser";

    public Map<String, String> readUsers() {
        Map<String, String> users = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(usersFileName))) {
            String redak;
            while ((redak = br.readLine()) != null) {
                if(redak.trim().isEmpty()){
                    // Ako je prazan string preskoci sve
                    continue;
                }
                String username = br.readLine();
                String password = br.readLine();
                users.put(username, password);
            }
        } catch (Exception ex) {
            String msg = "Dogodila se greska kod mapiranja korisnika i lozinki";
            logger.error(msg, ex);
            System.out.println(msg);
        }
        return users;
    }

    public void serializeChanges(Object obj, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object deserializeChanges(String fileName){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))){
            return ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            String msg = "Dogodila se greska kod deserijaliziranja podataka";
            logger.error(msg, ex);
            throw new RuntimeException(ex);
        }
    }

    public static void saveUserIntoTextFile(AppUser user){
        File userFile = new File(usersFileName);
        String password = null;

        try{

            password = LoginService.hashPassword(user.getPassword());

        }catch (NoSuchAlgorithmException ex){
            System.out.println("Dogodila se greska kod hashiranja lozinku u tekstulanu datoteku");
            ex.printStackTrace();
            logger.error(ex.getMessage());

        }

        try(PrintWriter pw = new PrintWriter(userFile)) {
            pw.print(user.getUsername());
            pw.print(", ");
            pw.println(password);

        } catch (FileNotFoundException ex) {
            System.out.println("Dogodila se greska tokom zapisivnja korisnika unutar tekstualne datoteke");
            ex.printStackTrace();
            logger.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }


}
