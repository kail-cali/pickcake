package co.pickcake.initutils.testconfig;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class RuntimeDataItem {

     private Long itemId;
     private Map<String, Object> items;

}
