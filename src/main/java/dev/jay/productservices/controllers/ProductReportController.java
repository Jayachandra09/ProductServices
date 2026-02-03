package dev.jay.productservices.controllers;

import dev.jay.productservices.models.ProductReport;
import dev.jay.productservices.repositories.ProductReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ProductReportController {

    private final ProductReportRepository productReportRepository;

    @Cacheable(value = "reports", key = "#page + '-' + #size")
    @GetMapping
    public Page<ProductReport> getReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return productReportRepository.findAll(
                PageRequest.of(
                        page,
                        size,
                        Sort.by("reportDate").descending()
                )
        );
    }
}
