package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private final File file;

    public FileHandler(String filePath) {
        this.file = new File(filePath);
    }

    public void writeLine(String line, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public List<String> readAllLines() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return lines;
    }

    public boolean exists() {
        return file.exists();
    }

    public void createFileIfNotExists() {
        if (!exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Arquivo criado: " + file.getPath());
                }
            } catch (IOException e) {
                System.err.println("Erro ao criar o arquivo: " + e.getMessage());
            }
        }
    }
}