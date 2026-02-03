package dev.jay.productservices.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductReport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_date")
    @Temporal(TemporalType.DATE)
    private Date reportDate;

    @Column(name = "total_products")
    private Long totalProducts;

    @Column(name = "average_price")
    private Double averagePrice;

    @Column(name = "created_at")
    private Date createdAt;
}
