package ourmap.demo.entity;

import lombok.Getter;

@Getter
public enum StoreTypes {
    CAFE("카페"),
    RESTAURANT("식당"),
    BAR("술집"),
    OTHER("기타");

    private final String description;

    StoreTypes(String description) {
        this.description = description;
    }
}
