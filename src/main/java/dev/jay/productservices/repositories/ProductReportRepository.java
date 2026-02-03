package dev.jay.productservices.repositories;

import dev.jay.productservices.models.ProductReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductReportRepository extends JpaRepository<ProductReport, Long> {
}
