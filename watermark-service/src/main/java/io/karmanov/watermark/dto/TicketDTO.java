package io.karmanov.watermark.dto;

import io.karmanov.watermark.domain.TicketStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Request payload to create new document
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Request payload to create new document")
public class TicketDTO {

    private UUID id;

    @ApiModelProperty("ID of the document")
    private Long documentId;

    @ApiModelProperty("Current status code of document (1 - COMPLETE, 2 - IN PROGRESS, 3 - FAILED).")
    private TicketStatus status;

    @ApiModelProperty("Document information. Will be filled only in ticket has status 1(COMPLETE).")
    private DocumentDTO document;

    @ApiModelProperty("Date when ticket was created")
    private DateTime createdDate;

    @ApiModelProperty("Date when ticket was last modified")
    private DateTime modifiedDate;
}
