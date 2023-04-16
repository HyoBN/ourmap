package ourmap.demo.entity;

import lombok.Getter;

@Getter
public enum StoreTypes {
    CAFE("카페","#FF9900"),
    RESTAURANT("식당","#FF6600"),
    BAR("술집","#66FFCC"),
    OTHER("기타","#006699");
    
    private final String description;
    private final String color;

    StoreTypes(String description, String color) {
        this.description = description;
        this.color = color;
    }
}
