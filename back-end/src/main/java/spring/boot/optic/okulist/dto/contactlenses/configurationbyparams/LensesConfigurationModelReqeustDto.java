package spring.boot.optic.okulist.dto.contactlenses.configurationbyparams;

import lombok.Data;

@Data
public class LensesConfigurationModelReqeustDto {
    private String configurationName;
    private Long manufacturerId;
    private Long colorId;
    private Long cylinderId;
    private Long degreeId;
    private Long diopterId;
    private Long materialId;
    private Long sphereId;
}
