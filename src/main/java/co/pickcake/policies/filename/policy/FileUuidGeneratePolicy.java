package co.pickcake.policies.filename.policy;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Primary
public class FileUuidGeneratePolicy implements FileNamePolicy{


    @Override
    public String generateDefaultName() {
        return "hail_cake";
    }

    @Override
    public String generateLogicalName(String brand, String name) {
        return brand + "_" + name;
    }

    @Override
    public String generatePhysicalName() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String extractEXT(String originalName) {
        int pos = originalName.lastIndexOf(".");
        return originalName.substring(pos + 1);
    }

    @Override
    public String concatGenExt(String originalName) {
        String uuid = generatePhysicalName();
        String ext = extractEXT(originalName);
        return uuid + "." + ext;
    }


}
