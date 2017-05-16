package io.karmanov.watermark.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Class for watermark request presentation. Contains ID of the document
 * and basic validation rules.
 */
@Data
@ApiModel(description = "Represent request for watermark the document")
public class WatermarkRequestDTO {

    @NotNull
    @ApiModelProperty(notes = "ID of the document to watermark", required = true)
    private Long documentId;
}
