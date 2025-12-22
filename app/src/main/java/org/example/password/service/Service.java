package org.example.password.service;

import org.example.password.model.Password;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Service {
    private final List<Password> passwords;


    public Service(List<Password> passwords) {
        this.passwords = passwords;
    }

    public void savePasswordList(String note, String username, String password){
        Password p = new Password();
        p.setUsername(username);
        p.setPassword(password);
        p.setNotes(note);
        p.setCreatedAt(LocalDateTime.now());
        p.setId(UUID.randomUUID().toString());
        this.passwords.add(p);
    }

    public Password getPasswordById(String id){
        for (Password p : passwords){
            if(p.getId().equals(id)){
                return p;
            }
        }
        throw new RuntimeException("Password with id " + id + " not found");
    }

    public void DeletePasswordById(String id){
        for (int i = 0; i<this.passwords.size(); i++){
            if(this.passwords.get(i).getId().equals(id)){
                Password p = this.passwords.get(i);
                //p.setDeleted();
                this.passwords.remove(p);
            }
        }

        throw new RuntimeException("Password with id " + id + " not found");
    }

    public List<Password> getPasswords(){
        return List.copyOf(this.passwords);
    }

//    public String displayPasswords(Password p){
//        if(p.isDeleted()){
//            return "";
//        }
//        else{
//            return String.format("Id: %s |Username: %s | Password: %s| Created At: %s ",p.getId(), p.getUsername(), p.getPassword(), p.getCreatedAt());
//        }
//    }

    public void updatePassword(String id, String password){
         Password p = getPasswordById(id);
         p.setPassword(password);

    }

    public void updateNote(String id, String note){
        Password p = getPasswordById(id);
        p.setNotes(note);
    }

}
