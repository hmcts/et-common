package uk.gov.hmcts.ecm.common.model.bulk.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DynamicFixedListType {

    private transient DynamicValueType value;
    @JsonProperty("list_items")
    private List<DynamicValueType> listItems;

    public DynamicFixedListType(String value) {
        this.value = new DynamicValueType(value, value);
    }

    public DynamicFixedListType() {}

    public static DynamicFixedListType from(List<DynamicValueType> listItems, DynamicFixedListType original) {
        var dynamicFixedListType = new DynamicFixedListType();
        dynamicFixedListType.listItems = listItems;

        var selectedValue = DynamicFixedListType.getSelectedValue(original);
        if (selectedValue.isPresent()) {
            var value = selectedValue.get();
            for (var listItem : listItems) {
                if (listItem.getCode().equals(value.getCode())) {
                    value.setLabel(listItem.getLabel());
                    dynamicFixedListType.value = value;
                    break;
                }
            }
        }

        return dynamicFixedListType;
    }

    public static Optional<DynamicValueType> getSelectedValue(DynamicFixedListType dynamicFixedListType) {
        return dynamicFixedListType != null ? Optional.ofNullable(dynamicFixedListType.getValue()) : Optional.empty();
    }
}
