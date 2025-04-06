package com.jabberpoint.patterns.factory;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the FontColor enum.
 */
class ItemTypeTest
{

    @Test
    public void testGetItemTypeValue() {
        assertTrue(Arrays.asList(ItemType.values()).contains(ItemType.TITLE));
        assertTrue(Arrays.asList(ItemType.values()).contains(ItemType.SUBTITLE));
        assertTrue(Arrays.asList(ItemType.values()).contains(ItemType.TEXT));
        assertTrue(Arrays.asList(ItemType.values()).contains(ItemType.BULLET));
        assertTrue(Arrays.asList(ItemType.values()).contains(ItemType.BITMAP));
        assertTrue(Arrays.asList(ItemType.values()).contains(ItemType.BACKGROUND));
    }

    @Test
    public void testEnumValueOf() {
        assertEquals(ItemType.TITLE, ItemType.valueOf("TITLE"));
        assertEquals(ItemType.SUBTITLE, ItemType.valueOf("SUBTITLE"));
        assertEquals(ItemType.TEXT, ItemType.valueOf("TEXT"));
        assertEquals(ItemType.BULLET, ItemType.valueOf("BULLET"));
        assertEquals(ItemType.BITMAP, ItemType.valueOf("BITMAP"));
        assertEquals(ItemType.BACKGROUND, ItemType.valueOf("BACKGROUND"));
    }
}