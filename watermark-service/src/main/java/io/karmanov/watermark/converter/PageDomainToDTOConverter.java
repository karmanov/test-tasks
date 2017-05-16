package io.karmanov.watermark.converter;

import io.karmanov.watermark.domain.Ticket;
import io.karmanov.watermark.dto.PageDTO;
import io.karmanov.watermark.dto.TicketDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * Converter for {@link Page} entity to {@link PageDTO} transformation
 */
@Component
public class PageDomainToDTOConverter<T> implements Converter<Page, PageDTO<T>> {

    @Override
    public PageDTO<T> convert(Page page) {
        if (page == null) {
            throw new IllegalArgumentException("Could not convert null to PageDTO");
        }
        PageDTO<T> pageDTO = new PageDTO<>();
        BeanUtils.copyProperties(page, pageDTO);
        return pageDTO;
    }
}
