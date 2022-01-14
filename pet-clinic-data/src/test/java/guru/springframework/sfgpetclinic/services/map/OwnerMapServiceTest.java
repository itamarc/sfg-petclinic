package guru.springframework.sfgpetclinic.services.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.sfgpetclinic.model.Owner;

public class OwnerMapServiceTest {
    OwnerServiceMap ownerServiceMap;
    final Long ownerId = 1L;
    final String firstName = "Fiona";
    final String lastName = "Smith";

    @BeforeEach
    void setUp() throws Exception {
        ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());
        ownerServiceMap.save(Owner.builder().id(ownerId).firstName(firstName).lastName(lastName).build());
    }

    @Test
    public void findAll() {
        // Act
        Set<Owner> owners = ownerServiceMap.findAll();
        // Assert
        assertEquals(1, owners.size());
    }

    @Test
    public void findById() {
        // Act
        Owner owner = ownerServiceMap.findById(ownerId);
        // Assert
        assertEquals(firstName, owner.getFirstName());
    }

    @Test
    public void save() {
        // Act
        Owner owner = ownerServiceMap.save(Owner.builder().id(ownerId).firstName(firstName).build());
        // Assert
        assertEquals(firstName, owner.getFirstName());
    }

    @Test
    public void saveNoId() {
        Owner savedOwner = ownerServiceMap.save(Owner.builder().firstName(firstName).build());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    public void delete() {
        // Act
        ownerServiceMap.delete(Owner.builder().id(ownerId).firstName(firstName).build());
        // Assert
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    public void deleteById() {
        // Act
        ownerServiceMap.deleteById(ownerId);
        // Assert
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    public void findByLastName() {
        // Act
        Owner owner = ownerServiceMap.findByLastName(lastName);
        // Assert
        assertEquals(firstName, owner.getFirstName());
        assertEquals(ownerId, owner.getId());
    }

    @Test
    public void findByLastNameNotFound() {
        // Act
        Owner owner = ownerServiceMap.findByLastName("ThisLastNameDoesNotExist");
        // Assert
        assertNull(owner);
    }
}
