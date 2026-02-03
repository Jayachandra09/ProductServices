package dev.jay.productservices.schedulers;

import dev.jay.productservices.models.ProductReport;
import dev.jay.productservices.repositories.ProductReportRepository;
import dev.jay.productservices.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class ProductReportScheduler {

    private final ProductRepository productRepository;
    private final ProductReportRepository productReportRepository;

    /*
     Production cron (daily midnight):
     0 0 0 * * ?

     Testing cron (every 30 seconds):
     0/30 * * * * ?
    */
    // Evict cache when new report generated
    @CacheEvict(value = "reports", allEntries = true)
    @Scheduled(cron = "0 0 0 * * ?") // change to midnight after testing
    public void generateDailyReport() {

        System.out.println("📊 Generating Daily Product Report...");

        // ✅ Fast SQL COUNT(*)
        Long totalProducts = productRepository.count();

        // ✅ Fast SQL AVG(price)
        Double avgPrice = productRepository.findAveragePrice();

        if (avgPrice == null) {
            avgPrice = 0.0;
        }

        ProductReport report = new ProductReport();
        report.setReportDate(new Date());
        report.setTotalProducts(totalProducts);
        report.setAveragePrice(avgPrice);
        report.setCreatedAt(new Date());

        productReportRepository.save(report);

        System.out.println("✅ Report Saved Successfully");
    }
}
