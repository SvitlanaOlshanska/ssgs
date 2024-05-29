package ssgs;

import java.util.Objects;

public class Artifact {
    private String id;
    private String name;
    private String origin;
    private String creationPeriod;

    public Artifact(String id, String name, String origin, String creationPeriod) {
        this.id = id;
        this.name = name;
        this.origin = origin;
        this.creationPeriod = creationPeriod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCreationPeriod() {
        return creationPeriod;
    }

    public void setCreationPeriod(String creationPeriod) {
        this.creationPeriod = creationPeriod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Artifact artifact = (Artifact) o;
        return Objects.equals(id, artifact.id) && Objects.equals(name, artifact.name)
                && Objects.equals(origin, artifact.origin) && Objects.equals(creationPeriod, artifact.creationPeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, origin, creationPeriod);
    }
}
