package org.example.Repo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.crypto.CryptoService;
import org.example.password.model.Password;


import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;


public class FilePasswordRepo implements PasswordRepository {
private static final Path FILE_PATH = Paths.get("data", "encryptedData.json");
private final ObjectMapper mapper;
private final CryptoService crypto;
private final String masterPassword;

public FilePasswordRepo(String masterPassword) {
	this.masterPassword = masterPassword;
    this.crypto = new CryptoService();
    mapper = new ObjectMapper();
	mapper.registerModule(new JavaTimeModule());
    initFile();
}

private void initFile(){
    try{
        if(!Files.exists(FILE_PATH)){
            Files.createDirectories(FILE_PATH.getParent());
            Files.write(FILE_PATH, "[]".getBytes());
        }

    }
    catch(IOException e){
        throw new RuntimeException("Unable to initialize file", e);
    }
}

    @Override
    public void saveAll(List<Password> passwords) {
        try {
            String json = mapper.writeValueAsString(passwords);
            byte[] encrypted = crypto.encrypt(json, masterPassword);
            Files.write(FILE_PATH, encrypted, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save vault");
        }
    }

    @Override

    public List<Password> getAll() {
        try {
            byte[] encrypted = Files.readAllBytes(FILE_PATH);
            if (encrypted.length == 0) return new ArrayList<>();

            String json = crypto.decrypt(encrypted, masterPassword);
            return mapper.readValue(json, new TypeReference<List<Password>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to unlock vault");
        }
    }



}
