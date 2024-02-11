package co.pickcake.testconfig;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class TestDataItem {

     private Long itemId;
     private Map<String, Object> items;

}
