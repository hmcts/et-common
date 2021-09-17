package uk.gov.hmcts.ecm.common.model.bulk.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
