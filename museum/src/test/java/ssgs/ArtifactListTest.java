package ssgs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class ArtifactListTest {

    private ArtifactListFile artifactList;

    @BeforeEach
    public void setUp() {
        artifactList = new ArtifactListFile("ArchivioTest");
    }

    @AfterEach
    public void tearDown() {
        try {
            artifactList.deleteAllArtifacts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddAndGetAll() {
        Artifact artifact1 = new Artifact("1", "Quadro", "Europa", "Medioevo");
        Artifact artifact2 = new Artifact("2", "Vaso", "Africa", "1400");
        Artifact artifact3 = new Artifact("3", "Anello", "Asia", "1600");

        artifactList.add(artifact1);
        artifactList.add(artifact2);
        artifactList.add(artifact3);

        List<Artifact> artifacts = artifactList.getAll();
        assertEquals(3, artifacts.size());
        assertTrue(artifacts.contains(artifact1));
        assertTrue(artifacts.contains(artifact2));
        assertTrue(artifacts.contains(artifact3));
    }

    @Test
    public void testGetById() {
        Artifact artifact1 = new Artifact("1", "Quadro", "Europa", "Medioevo");
        Artifact artifact2 = new Artifact("2", "Vaso", "Africa", "1400");

        artifactList.add(artifact1);
        artifactList.add(artifact2);

        assertEquals(artifact1, artifactList.getById("1"));
        assertEquals(artifact2, artifactList.getById("2"));
        assertNull(artifactList.getById("3"));
    }

    @Test
    public void testUpdate() {
        Artifact artifact1 = new Artifact("1", "Quadro", "Europa", "Medioevo");
        Artifact artifact2 = new Artifact("1", "Vaso", "Africa", "1400");

        artifactList.add(artifact1);
        artifactList.update(artifact2);

        assertEquals(artifact2, artifactList.getById("1"));
    }

    @Test
    public void testDelete() {
        Artifact artifact1 = new Artifact("1", "Quadro", "Europa", "Medioevo");

        artifactList.add(artifact1);
        artifactList.delete("1");

        assertEquals(0, artifactList.getAll().size());
        assertNull(artifactList.getById("1"));
    }

    @Test
    public void testAddNullArtifact() {
        assertThrows(IllegalArgumentException.class, () -> {
            artifactList.add(null);
        });
    }

    @Test
    public void testAddArtifactWithEmptyId() {
        Artifact artifact = new Artifact("", "Quadro", "Europa", "Medioevo");
        assertThrows(IllegalArgumentException.class, () -> {
            artifactList.add(artifact);
        });
    }

    @Test
    public void testAddArtifactWithNullName() {
        Artifact artifact = new Artifact("1", null, "Europa", "Medioevo");
        assertThrows(IllegalArgumentException.class, () -> {
            artifactList.add(artifact);
        });
    }

    @Test
    public void testAddArtifactWithEmptyName() {
        Artifact artifact = new Artifact("1", "", "Europa", "Medioevo");
        assertThrows(IllegalArgumentException.class, () -> {
            artifactList.add(artifact);
        });
    }

    @Test
    public void testAddArtifactWithNullOrigin() {
        Artifact artifact = new Artifact("1", "Quadro", null, "Medioevo");
        assertThrows(IllegalArgumentException.class, () -> {
            artifactList.add(artifact);
        });
    }

    @Test
    public void testAddArtifactWithEmptyOrigin() {
        Artifact artifact = new Artifact("1", "Quadro", "", "Medioevo");
        assertThrows(IllegalArgumentException.class, () -> {
            artifactList.add(artifact);
        });
    }

    @Test
    public void testAddArtifactWithNullCreationPeriod() {
        Artifact artifact = new Artifact("1", "Quadro", "Europa", null);
        assertThrows(IllegalArgumentException.class, () -> {
            artifactList.add(artifact);
        });
    }

    @Test
    public void testAddArtifactWithEmptyCreationPeriod() {
        Artifact artifact = new Artifact("1", "Quadro", "Europa", "");
        assertThrows(IllegalArgumentException.class, () -> {
            artifactList.add(artifact);
        });
    }

    @Test
    public void testAddDuplicateArtifact() {
        // Aggiunge un artefatto
        Artifact artifact = new Artifact("1", "Quadro", "Europa", "Medioevo");
        artifactList.add(artifact);

        // Tenta di aggiungere lo stesso artefatto
        assertThrows(IllegalArgumentException.class, () -> artifactList.add(artifact));

        // Verifica che l'aggiunta dell'artefatto duplicato non sia avvenuta
        List<Artifact> artifacts = artifactList.getAll();
        assertEquals(1, artifacts.size());
        assertEquals(artifact, artifacts.get(0));
    }

    @Test
    public void testUpdateNonExistingArtifact() {
        Artifact artifact = new Artifact("1", "Quadro", "Europa", "Medioevo");

        assertThrows(IllegalArgumentException.class, () -> artifactList.update(artifact));
    }

    @Test
    public void testDeleteNonExistingArtifact() {
        artifactList.delete("1");

        assertEquals(0, artifactList.getAll().size());
    }
}