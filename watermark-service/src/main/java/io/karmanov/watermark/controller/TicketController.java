package io.karmanov.watermark.controller;


import io.karmanov.watermark.dto.TicketDTO;
import io.karmanov.watermark.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Provides operations to track the ticket status
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/watermark-service")
@Api(description = "Provides operations track the ticket status")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping(value = "/tickets/{ticket_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Finds ticket by provided id")
    public TicketDTO getTicket(@PathVariable("ticket_id") UUID ticketId) {
        return ticketService.getById(ticketId);
    }
}
