package uk.gov.hmcts.ecm.common.model.bulk.types;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DynamicFixedListTypeTest {

    @Test
    public void testGetSelectedLabel() {
        var dynamicFixedListType = create(null);
        assertNull(dynamicFixedListType.getSelectedLabel());

        dynamicFixedListType = create(new DynamicValueType("code1", "label1"));
        assertEquals("label1", dynamicFixedListType.getSelectedLabel());
    }

    @Test
    public void testGetSelectedCode() {
        var dynamicFixedListType = create(null);
        assertNull(dynamicFixedListType.getSelectedCode());

        dynamicFixedListType = create(new DynamicValueType("code1", "label1"));
        assertEquals("code1", dynamicFixedListType.getSelectedCode());
    }

    @Test
    public void testIsValidCodeForList() {
        var dynamicFixedListType = create(null);
        assertTrue(dynamicFixedListType.isValidCodeForList("code1"));
        assertTrue(dynamicFixedListType.isValidCodeForList("code2"));
        assertTrue(dynamicFixedListType.isValidCodeForList("code3"));
        assertFalse(dynamicFixedListType.isValidCodeForList("code4"));
        assertFalse(dynamicFixedListType.isValidCodeForList(null));
        assertFalse(dynamicFixedListType.isValidCodeForList(""));
    }

    private DynamicFixedListType create(DynamicValueType selectedValue) {
        var dynamicFixedListType = new DynamicFixedListType();
        dynamicFixedListType.setListItems(List.of(
                new DynamicValueType("code1", "label1"),
                new DynamicValueType("code2", "label2"),
                new DynamicValueType("code3", "label3")));
        dynamicFixedListType.setValue(selectedValue);
        return dynamicFixedListType;
    }
}
