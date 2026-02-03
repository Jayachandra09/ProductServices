CREATE TABLE product_report (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    report_date DATE NOT NULL,

    total_products BIGINT,

    average_price DOUBLE,

    created_at DATETIME
);
