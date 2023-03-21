/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Models.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Nour
 */
public class LocalStorage {

    private String fileName = "storage.txt";

    public LocalStorage() throws IOException {
        this.initLLocalStorage();
    }

    public File initLLocalStorage() throws IOException {

        File myObj = new File(fileName);
        if (myObj.createNewFile()) {
            System.out.println("storage created: " + myObj.getName());
        } else {
            System.out.println("storage init.");
        }
        return myObj;
    }

    public void writeToStorage(User user) throws IOException {

        FileWriter myWriter = new FileWriter(fileName);
        JSONObject obj = new JSONObject();

        obj.put("id", user.getId());
        obj.put("nom", user.getNom());
        obj.put("prenom", user.getPrenom());
        obj.put("email", user.getEmail());
        obj.put("password", user.getPassword());
        obj.put("telephone", user.getTelephone());
        obj.put("cin", user.getCin());
        obj.put("roles", user.getRoles());
        obj.put("verified", user.getVerified());
        obj.put("image", user.getImage());
        obj.put("verification_code", user.getVerification_code());

        System.out.println(obj.toJSONString());

        myWriter.write(obj.toJSONString());
        myWriter.close();
        System.out.println("Successfully stored connectedUser.");
    }

    public User getStoredUser() throws FileNotFoundException {
        User user = new User();

        File myObj = new File(fileName);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            Object obj = JSONValue.parse(data);
            JSONObject json = (JSONObject) obj;
            user = getUserFromJSON(json);
        }
        myReader.close();
        return user;
    }

    public User getUserFromJSON(JSONObject json) {
        User user = new User();

        long id = (Long) json.get("id");
        String nom = (String) json.get("nom");
        String prenom = (String) json.get("prenom");
        String email = (String) json.get("email");
        String password = (String) json.get("password");
        String telephone = (String) json.get("telephone");
        String cin = (String) json.get("cin");
        String roles = (String) json.get("roles");
        long verified = (Long) json.get("verified");
        String image = (String) json.get("image");
        String verification_code = (String) json.get("verification_code");

        user.setId(Math.toIntExact(id));
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setPassword(password);
        user.setTelephone(telephone);
        user.setCin(cin);
        user.setRoles(roles);
        user.setVerified(Math.toIntExact(verified));
        user.setImage(image);
        user.setVerification_code(verification_code);

        return user;

    }

    public void deleteStorage() {
        File myObj = new File(fileName);
        if (myObj.delete()) {
            System.out.println("Deleted storage: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
}
