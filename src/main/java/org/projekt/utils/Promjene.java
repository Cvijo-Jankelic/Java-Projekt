package org.projekt.utils;

import org.projekt.Enum.Role;
import org.projekt.entity.Campaign;
import org.projekt.services.Session;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Promjene implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String staraVrijednost;
    private final String novaVrijednost;
    private final LocalDateTime datumPromjene;
    Role role = Session.getCurrentUser().getRole();
    private static final String campaignDat = "serializedDat/campaignSerialized.ser";
    private final String adsDat = "serializedDat/adsSerialized.ser";
    private final String companyDat = "serializedDat/companySerialized.ser";
    private final String userDat = "serializedDat/userSerialized.ser";

    public Promjene(String staraVrijednost, String novaVrijednost, LocalDateTime datumPromjene, Role role) {
        this.staraVrijednost = staraVrijednost;
        this.novaVrijednost = novaVrijednost;
        this.datumPromjene = datumPromjene;
        this.role = role;
    }

    public String getStaraVrijednost() {
        return staraVrijednost;
    }

    public String getNovaVrijednost() {
        return novaVrijednost;
    }

    public LocalDateTime getDatumPromjene() {
        return datumPromjene;
    }

    public static <T> void serializeObjects(T objectOld, T objectNew, String fileName){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(campaignDat))) {
            oos.writeObject(objectOld);
            oos.writeObject(objectNew);
        } catch (IOException e) {
            String msg = "Pogreska kod serijaliziranja podataka";
            e.printStackTrace();
        }
        System.out.println("Serijalizacija uspjesna!");
    }
    public static <T> void serializeObject(T objectOld, String fileName){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(campaignDat))) {
            oos.writeObject(objectOld);
        } catch (IOException e) {
            String msg = "Pogreska kod serijaliziranja podataka";
            e.printStackTrace();
        }
        System.out.println("Serijalizacija uspjesna!");
    }
    public static List<Campaign> deserijalizirajKampanje(){
        List<Campaign> campaignList = new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(campaignDat))){
            Campaign oldCamapign = (Campaign) ois.readObject();
            Campaign newCampaign = (Campaign) ois.readObject();

            campaignList.add(oldCamapign);
            campaignList.add(newCampaign);
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("Došlo je do greške prilikom deserijalizacije: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return campaignList;
    }
    public static void serializePromjena(Promjene promjena, String fileName) {
        List<Promjene> promjene = deserijaliziraj(fileName);
        promjene.add(promjena);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(promjene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<Promjene> deserijaliziraj(String fileName) {
        List<Promjene> promjene = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            promjene = (List<Promjene>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Datoteka nije pronađena. Kreira se nova datoteka.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return promjene;
    }

    @Override
    public String toString() {
        return "Promjene{" +
                "staraVrijednost='" + staraVrijednost + '\'' +
                ", novaVrijednost='" + novaVrijednost + '\'' +
                ", datumPromjene=" + datumPromjene +
                ", role=" + role +
                ", adsDat='" + adsDat + '\'' +
                ", companyDat='" + companyDat + '\'' +
                ", userDat='" + userDat + '\'' +
                '}';
    }
}
