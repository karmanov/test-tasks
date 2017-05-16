package io.karmanov.watermark.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.joda.time.DateTime;

/**
 * Response payload of the document
 */
@Data
@ApiModel(description = "Response payload of the document")
public class DocumentDTO extends DocumentCreateRequestDTO {

    @ApiModelProperty("Document id")
    private Long id;

    @ApiModelProperty("Date when document was created")
    private DateTime createdDate;

    @ApiModelProperty("Date when document was last modified")
    private DateTime modifiedDate;
}
