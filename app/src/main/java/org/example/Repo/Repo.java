package org.example.Repo;

import org.example.password.model.Password;

import java.util.List;

public interface Repo {
    void saveAll(List<Password> passwords);
    List<Password> getAll();
}
