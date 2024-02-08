package co.pickcake.policies.filename.policy;

import org.springframework.stereotype.Component;

@Component
public class FileSpecificGeneratePolicy implements FileNamePolicy {
    @Override
    public String generateDefaultName() {
        return null;
    }

    @Override
    public String generateLogicalName(String brand, String name) {
        return null;
    }

    @Override
    public String generatePhysicalName() {
        return null;
    }

    @Override
    public String extractEXT(String originalName) {
        return null;
    }

    @Override
    public String concatGenExt(String originalName) {
        return null;
    }
}
