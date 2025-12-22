package org.example;

import org.example.password.model.Password;
import org.example.password.service.Service;
import org.example.Repo.FilePasswordRepo;
import java.util.Scanner;

public class App {



    public static void main(String[] args) {

        FilePasswordRepo repo = new FilePasswordRepo();
        Service service = new Service(repo);

        Scanner in = new Scanner(System.in);

        System.out.println("🔐 Password Manager CLI");

        while (true) {
            System.out.println("\nCommands: add | delete | view | exit");
            System.out.print("> ");
            String cmd = in.nextLine();

            try {
                switch (cmd) {

                    case "add" -> {
                        System.out.print("Username: ");
                        String user = in.nextLine();

                        System.out.print("Password: ");
                        String pass = in.nextLine();

                        System.out.print("Notes: ");
                        String notes = in.nextLine();

                        System.out.print("Website: ");
                        String website = in.nextLine();

                        service.savePasswordList(user, pass, notes, website);
                        System.out.println("Added.");
                    }

                    case "view" -> {
                        for (Password p : service.getPasswords()) {

                            System.out.println(
                                    p.getId() + " | " +
                                            p.getUsername() + " | " +
                                            p.getPassword() + " | " +
                                    p.getNotes() + " | " + p.getWebsite()
                            );
                        }
                    }

                    case "delete" -> {
                        System.out.print("ID: ");
                        String id = in.nextLine();
                        service.deletePasswordById(id);
                        System.out.println("Deleted.");
                    }

                    case "exit" -> {
                        service.save();
                        System.out.println("Vault saved. Bye");
                        return;
                    }

                    default -> System.out.println("Unknown command");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
