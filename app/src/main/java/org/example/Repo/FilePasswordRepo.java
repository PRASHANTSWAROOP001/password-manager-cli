package org.example.Repo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.password.model.Password;


import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;


public class FilePasswordRepo implements PasswordRepository {
private static final Path FILE_PATH = Paths.get("data", "encryptedData.json");
private final ObjectMapper mapper;
public FilePasswordRepo() {
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
    try{
        String json = mapper.writeValueAsString(passwords);
        Files.write(
                FILE_PATH,
                json.getBytes(),
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }
    catch(IOException e){
        throw new RuntimeException("Unable to save passwords", e);
    }
}

    @Override
    public List<Password> getAll() {
        try {
            String json = Files.readString(FILE_PATH);

            if (json.isBlank()) {
                return new ArrayList<>();
            }

            return mapper.readValue(
                    json,
                    new TypeReference<List<Password>>() {}
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to load passwords", e);
        }
    }



}
