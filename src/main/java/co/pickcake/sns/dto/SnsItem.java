package co.pickcake.sns.dto;

import co.pickcake.sns.domain.SNS;
import lombok.Data;

@Data
public class SnsItem {
    private String url;
    public SnsItem(SNS sns) {
        url = sns.getUrl();

    }
}
