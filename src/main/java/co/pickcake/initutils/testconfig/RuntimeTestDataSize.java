package co.pickcake.initutils.testconfig;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RuntimeTestDataSize {

    private int batch;
    private int size;
    private Long alpha;
    private Long beta;

}
