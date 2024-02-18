package co.pickcake.reservedomain.searchcake.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultDto<T> {
    private int count;
    private T data;
}
