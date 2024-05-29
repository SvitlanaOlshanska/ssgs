package ssgs;

import java.util.List;

public interface ArtifactList {
    List<Artifact> getAll();

    void add(Artifact artifact);

    Artifact getById(String id);

    void delete(String id);

    void update(Artifact artifact);
}
