package ssgs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArtifactListFile implements ArtifactList {
    private String filePath;
    private String format = ".txt";
    private List<Artifact> artifacts;

    public ArtifactListFile(String fileName) {
        this.filePath = System.getProperty("user.dir") + File.separator + fileName + format;
        this.artifacts = new ArrayList<>();
        File file = new File(this.filePath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                ErrorHandler.handleException(e, "Errore durante la creazione del file");
            }
        }
    }

    @Override
    public List<Artifact> getAll() {
        loadFromFile();
        return artifacts;
    }

    @Override
    public void add(Artifact artifact) {
        validateArtifact(artifact);
        loadFromFile();
        for (Artifact existingArtifact : artifacts) {
            if (existingArtifact.getId().equals(artifact.getId())) {
                throw new IllegalArgumentException("L'artefatto con lo stesso ID esiste già");
            }
        }
        artifacts.add(artifact);
        saveToFile();
    }

    @Override
    public Artifact getById(String id) {
        loadFromFile();
        for (Artifact artifact : artifacts) {
            if (artifact.getId().equals(id)) {
                return artifact;
            }
        }
        return null;
    }

    @Override
    public void delete(String id) {
        loadFromFile();
        Artifact artifactToRemove = null;
        for (Artifact artifact : artifacts) {
            if (artifact.getId().equals(id)) {
                artifactToRemove = artifact;
                break;
            }
        }
        if (artifactToRemove != null) {
            artifacts.remove(artifactToRemove);
            saveToFile();
        }
    }

    @Override
    public void update(Artifact artifact) {
        validateArtifact(artifact);
        loadFromFile();
        boolean found = false;
        for (int i = 0; i < artifacts.size(); i++) {
            if (artifacts.get(i).getId().equals(artifact.getId())) {
                artifacts.set(i, artifact);
                found = true;
                break;
            }
        }
        if (found) {
            saveToFile();
        } else {
            throw new IllegalArgumentException("L'artefatto con l'ID specificato non esiste");
        }
    }

    public void deleteAllArtifacts() throws IOException {
        artifacts.clear();
        saveToFile();
    }

    private void loadFromFile() {
        artifacts.clear();
        File file = new File(filePath);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        String id = parts[0].trim();
                        String name = parts[1].trim();
                        String origin = parts[2].trim();
                        String creationPeriod = parts[3].trim();
                        artifacts.add(new Artifact(id, name, origin, creationPeriod));
                    }
                }
            } catch (IOException e) {
                ErrorHandler.handleException(e, "Errore durante la lettura del file");
            }
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Artifact artifact : artifacts) {
                writer.write(artifact.getId() + "," + artifact.getName() + "," + artifact.getOrigin() + ","
                        + artifact.getCreationPeriod());
                writer.newLine();
            }
        } catch (IOException e) {
            ErrorHandler.handleException(e, "Errore durante il salvataggio del file");
        }
    }

    private void validateArtifact(Artifact artifact) {
        if (artifact == null ||
                artifact.getId() == null || artifact.getId().isEmpty() ||
                artifact.getName() == null || artifact.getName().isEmpty() ||
                artifact.getOrigin() == null || artifact.getOrigin().isEmpty() ||
                artifact.getCreationPeriod() == null || artifact.getCreationPeriod().isEmpty()) {
            throw new IllegalArgumentException("L'artefatto non può avere campi nulli o vuoti");
        }
    }
}
