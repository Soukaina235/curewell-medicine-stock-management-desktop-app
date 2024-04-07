package com.curewell.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MedicineTest {

    @Test
    public void testMedicineGettersAndConstructor1() {
        Medicine medicine = new Medicine(1, "med1", "description1", Category.Cat1, 85.5, 400);
        assertEquals(1, medicine.getId());
        assertEquals("med1", medicine.getName());
        assertEquals("description1", medicine.getDescription());
        assertEquals(Category.Cat1, medicine.getCategory());
        assertEquals(85.5, medicine.getPrice(), 0.001);
        assertEquals(400, medicine.getQuantity());
    }

    @Test
    public void testMedicineGettersAndConstructor2() {
        Medicine medicine = new Medicine("med1", "description1", Category.Cat2, 85.5, 400);
        assertEquals(0, medicine.getId());
        assertEquals("med1", medicine.getName());
        assertEquals("description1", medicine.getDescription());
        assertEquals(Category.Cat2, medicine.getCategory());
        assertEquals(85.5, medicine.getPrice(), 0.001);
        assertEquals(400, medicine.getQuantity());
    }

    @Test
    public void testSetters() {
        Medicine medicine = new Medicine(1, "med1", "description1", Category.Cat1, 85.5, 400);

        medicine.setName("med1New");
        assertEquals("med1New", medicine.getName());

        medicine.setDescription("description1New");
        assertEquals("description1New", medicine.getDescription());

        medicine.setCategory(Category.Cat3);
        assertEquals(Category.Cat3, medicine.getCategory());

        medicine.setPrice(100);
        assertEquals(100, medicine.getPrice(), 0.001);

        medicine.setQuantity(200);
        assertEquals(200, medicine.getQuantity());
    }

    @Test
    public void testCanValidate() {
        Medicine medicine = new Medicine(1, "med1", "description1", Category.Cat1, 85.5, 400);

        assertTrue(medicine.canValidate(10));
        assertFalse(medicine.canValidate(500));
    }

    @Test
    public void testDecreaseQuantity() {
        Medicine medicine = new Medicine(1, "med1", "description1", Category.Cat1, 85.5, 400);
        medicine.decreaseQuantity(10);

        assertEquals(390, medicine.getQuantity());
    }

    @Test
    public void testIncreaseQuantity() {
        Medicine medicine = new Medicine(1, "med1", "description1", Category.Cat1, 85.5, 400);
        medicine.increaseQuantity(20);

        assertEquals(420, medicine.getQuantity());
    }

    @Test
    public void testEquals() {
        Medicine medicine1 = new Medicine(1, "med1", "description1", Category.Cat1, 85.5, 400);
        Medicine medicine2 = new Medicine(1, "med2", "description2", Category.Cat2, 42.0, 300);
        Medicine medicine3 = new Medicine(2, "med3", "description3", Category.Cat3, 60.75, 200);

        assertEquals(medicine1, medicine2);
        assertNotEquals(medicine1, medicine3);
    }
}