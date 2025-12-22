package org.example.password.service;

import org.example.Repo.PasswordRepository;
import org.example.password.model.Password;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Service {
    private final PasswordRepository passwordRepo;
    private final List<Password> passwords;


    public Service(PasswordRepository passwordRepo) {
        this.passwordRepo = passwordRepo;
        this.passwords = new ArrayList<>(passwordRepo.getAll());
    }

    public void savePasswordList(String username, String password, String notes, String website) {
        Password p = new Password();
        p.setUsername(username);
        p.setPassword(password);
        p.setNotes(notes);
        p.setWebsite(website);
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

    public void deletePasswordById(String id){
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


    public void updatePassword(String id, String password){
         Password p = getPasswordById(id);
         p.setPassword(password);

    }

    public void updateNote(String id, String note){
        Password p = getPasswordById(id);
        p.setNotes(note);
    }

    public void save(){
        passwordRepo.saveAll(this.passwords);
    }

}
