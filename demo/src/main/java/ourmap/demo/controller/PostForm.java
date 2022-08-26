package ourmap.demo.controller;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import ourmap.demo.entity.StoreTypes;
import ourmap.demo.entity.Tip;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PostForm {

    @NotNull
    private String storeName;
    private StoreTypes storeType;
    private String tip;
}
