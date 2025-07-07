package com.example.demo.endpoint.rest.controller.storedInt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoredIntController {

  private static final String FILE_PATH =
      "/tmp/stored-int.txt"; // Dans AWS Lambda, /tmp est le seul répertoire accessible en écriture

  @GetMapping("/stored-int")
  public int getStoredInt() {
    try {
      // Vérifier si le fichier existe
      Path path = Paths.get(FILE_PATH);

      if (Files.exists(path)) {
        // Lire le nombre stocké
        String content = new String(Files.readAllBytes(path));
        return Integer.parseInt(content.trim());
      } else {
        // Créer le fichier avec un nombre aléatoire
        int randomNumber = new Random().nextInt();
        Files.write(path, String.valueOf(randomNumber).getBytes());
        return randomNumber;
      }
    } catch (IOException e) {
      throw new RuntimeException("Erreur lors de la manipulation du fichier", e);
    } catch (NumberFormatException e) {
      throw new RuntimeException("Le contenu du fichier n'est pas un nombre valide", e);
    }
  }
}
