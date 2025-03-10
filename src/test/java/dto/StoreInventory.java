package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class StoreInventory {

    private Integer sold;
    private Integer string;
    private Integer pending;
    private Integer available;

    @JsonProperty("Not Available")
    private Integer notAvailable;
}
