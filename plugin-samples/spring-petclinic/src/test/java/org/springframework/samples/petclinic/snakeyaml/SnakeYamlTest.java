package org.springframework.samples.petclinic.snakeyaml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mocktail.repository.ObjectFileOperations;
import org.springframework.samples.petclinic.Specialty;
import org.springframework.samples.petclinic.Vet;
import org.yaml.snakeyaml.Yaml;

public class SnakeYamlTest {
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    private List<Vet> vets;
    private Vet vet1;

    @Before
    public void setup() {
        vets = new ArrayList<Vet>();
        vet1 = new Vet();
        vet1.setFirstName("firstname1");
        vet1.setLastName("lastName1");
        vet1.setId(1);
        Specialty specialty = new Specialty();
        specialty.setId(2);
        specialty.setName("radiology");
        vet1.addSpecialty(specialty);
        vets.add(vet1);
        
        Vet vet2 = new Vet();
        vet2.setFirstName("firstname2");
        vet2.setLastName("lastName2");
        vet2.setId(1);
        Specialty specialty2 = new Specialty();
        specialty2.setId(2);
        specialty2.setName("radiology2");
        vet2.addSpecialty(specialty2);
        vets.add(vet2);
    }

    @Test
    public void test() {
        Yaml yaml = new Yaml();
        String vetsYaml = yaml.dump(vets);
        System.out.println(vets);
        System.out.println(vetsYaml);
        
        List<Vet> vets3 = (List<Vet>)yaml.load(vetsYaml );
        System.out.println(vets3);
        
        
    }
    
    @Test
    public void test2() throws Exception {
        Yaml yaml = new Yaml();
        System.out.println(yaml.dump(vet1));
        
    }
    
    @Test
    public void testSerializableObject() throws Exception {
        File parentFolder = tempFolder.getRoot();
        ObjectFileOperations objectFileOperations = new ObjectFileOperations();
        objectFileOperations.saveObjectInFile(vets, "vets", parentFolder);
        List<Vet> vets2 = (List<Vet>)objectFileOperations.getObjectFromFile("vets", parentFolder);
        System.out.println(vets2);
    }
}
