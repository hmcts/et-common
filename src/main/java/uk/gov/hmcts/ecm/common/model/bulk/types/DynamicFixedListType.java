package uk.gov.hmcts.ecm.common.model.bulk.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DynamicFixedListType {

    private transient DynamicValueType value;
    @JsonProperty("list_items")
    private List<DynamicValueType> listItems;

    public static DynamicFixedListType of(DynamicValueType value) {
        Objects.requireNonNull(value);
        var dynamicFixedListType = new DynamicFixedListType();
        dynamicFixedListType.value = value;
        return dynamicFixedListType;
    }

    public static DynamicFixedListType from(List<DynamicValueType> listItems) {
        var dynamicFixedListType = new DynamicFixedListType();
        dynamicFixedListType.listItems = listItems;
        return dynamicFixedListType;
    }

    public static DynamicFixedListType from(List<DynamicValueType> listItems, DynamicFixedListType original) {
        var dynamicFixedListType = DynamicFixedListType.from(listItems);

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

    public static Optional<String> getSelectedLabel(DynamicFixedListType dynamicFixedListType) {
        if (dynamicFixedListType != null && dynamicFixedListType.getValue() != null) {
            return Optional.of(dynamicFixedListType.getSelectedLabel());
        } else {
            return Optional.empty();
        }
    }

    public DynamicFixedListType(String value) {
        this.value = new DynamicValueType(value, value);
    }

    public DynamicFixedListType() {
    }

    public String getSelectedLabel() {
        return value != null ? value.getLabel() : null;
    }

    public String getSelectedCode() {
        return value != null ? value.getCode() : null;
    }

    public boolean isValidCodeForList(String code) {
        if (CollectionUtils.isNotEmpty(listItems) && StringUtils.isNotBlank(code)) {
            for (var dynamicValueType : listItems) {
                if (dynamicValueType.getCode().equals(code)) {
                    return true;
                }
            }
        }

        return false;
    }
}
