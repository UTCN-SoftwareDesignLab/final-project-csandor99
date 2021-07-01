package com.example.demo.item;

import com.example.demo.item.model.dto.ItemDTO;
import com.example.demo.report.ReportServiceFactory;
import com.example.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<ItemDTO> allItems() {
        return itemService.findAll();
    }

    @GetMapping(SEARCH)
    public List<ItemDTO> filteredItems(@PathVariable String filter){ return itemService.filteredItems(filter);}

    @PostMapping
    public ItemDTO create(@RequestBody ItemDTO item) {
        return itemService.create(item);
    }

    @PatchMapping
    public ItemDTO edit(@RequestBody ItemDTO item) {
        return itemService.edit(item);
    }

    @PatchMapping(SELL + "/{name}")
    public void sell(@PathVariable String name,@RequestBody List<ItemDTO> list){ itemService.sell(name,list);}

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }

    @GetMapping(ENTITY)
    public ItemDTO getBook(@PathVariable Long id) {
        return itemService.get(id);
    }

    @GetMapping(EXPORT_REPORT)
    public ResponseEntity<?> exportReport(@PathVariable ReportType type) {
        HttpHeaders headers = new HttpHeaders();

        ByteArrayOutputStream outputStream = reportServiceFactory.getReportService(type).export();
        ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=OutOfStockItems.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }
}
