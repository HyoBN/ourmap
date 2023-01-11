package ourmap.demo.entity;

import lombok.Getter;

@Getter
public enum MessageTypes {

    FRIENDREQUEST("친구신청"),
    FRIENDREJECT("친구거절"),
    FRIENDACCEPT("친구수락");
    private final String description;

    MessageTypes(String description) {
        this.description=description;
    }

}
