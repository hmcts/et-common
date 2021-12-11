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

    @Test
    public void testFromWithNoOriginal() {
        var listItems = List.of(DynamicValueType.create("code1", "Code 1"),
                DynamicValueType.create("code2", "Code 2"),
                DynamicValueType.create("code3", "Code 3"));

        var dynamicFixedListType = DynamicFixedListType.from(listItems, null);

        verifyListItems(dynamicFixedListType);
        assertNull(dynamicFixedListType.getValue());
    }

    @Test
    public void testFromWithOriginalNoSelectedValue() {
        var listItems = List.of(DynamicValueType.create("code1", "Code 1"),
                DynamicValueType.create("code2", "Code 2"),
                DynamicValueType.create("code3", "Code 3"));
        var original = new DynamicFixedListType();

        var dynamicFixedListType = DynamicFixedListType.from(listItems, original);

        verifyListItems(dynamicFixedListType);
        assertNull(dynamicFixedListType.getValue());
    }

    @Test
    public void testFromWithOriginalWithDifferentSelectedValue() {
        var listItems = List.of(DynamicValueType.create("code1", "Code 1"),
                DynamicValueType.create("code2", "Code 2"),
                DynamicValueType.create("code3", "Code 3"));
        var original = DynamicFixedListType.of(new DynamicValueType("code4", "Code 4"));

        var dynamicFixedListType = DynamicFixedListType.from(listItems, original);

        verifyListItems(dynamicFixedListType);
        assertNull(dynamicFixedListType.getValue());
    }

    @Test
    public void testFromWithOriginalWithSelectedValue() {
        var listItems = List.of(DynamicValueType.create("code1", "Code 1"),
                DynamicValueType.create("code2", "Code 2"),
                DynamicValueType.create("code3", "Code 3"));
        var original = DynamicFixedListType.of(new DynamicValueType("code2", "Code 2"));

        var dynamicFixedListType = DynamicFixedListType.from(listItems, original);

        verifyListItems(dynamicFixedListType);
        assertEquals("code2", dynamicFixedListType.getValue().getCode());
        assertEquals("Code 2", dynamicFixedListType.getValue().getLabel());
    }

    @Test
    public void testFromWithOriginalWithSelectedValueWrongLabel() {
        var listItems = List.of(DynamicValueType.create("code1", "Code 1"),
                DynamicValueType.create("code2", "Code 2"),
                DynamicValueType.create("code3", "Code 3"));
        var original = DynamicFixedListType.of(new DynamicValueType("code2", "Code X"));

        var dynamicFixedListType = DynamicFixedListType.from(listItems, original);

        verifyListItems(dynamicFixedListType);
        assertEquals("code2", dynamicFixedListType.getValue().getCode());
        assertEquals("Code 2", dynamicFixedListType.getValue().getLabel());
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

    private void verifyListItems(DynamicFixedListType dynamicFixedListType) {
        assertEquals(3, dynamicFixedListType.getListItems().size());
        assertEquals("code1", dynamicFixedListType.getListItems().get(0).getCode());
        assertEquals("Code 1", dynamicFixedListType.getListItems().get(0).getLabel());
        assertEquals("code2", dynamicFixedListType.getListItems().get(1).getCode());
        assertEquals("Code 2", dynamicFixedListType.getListItems().get(1).getLabel());
        assertEquals("code3", dynamicFixedListType.getListItems().get(2).getCode());
        assertEquals("Code 3", dynamicFixedListType.getListItems().get(2).getLabel());
    }
}
