package co.pickcake.policies.policy.policy;

import org.springframework.stereotype.Component;

@Component
public interface FileNamePolicy {

    String generateDefaultName();
    String generateLogicalName(String brand, String name);
    String generatePhysicalName();
    String extractEXT(String originalName);

    String concatGenExt(String originalName);


}
