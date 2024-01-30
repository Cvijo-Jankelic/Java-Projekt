package org.projekt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.KeyStore;
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


}
