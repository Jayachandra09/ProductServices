package dev.jay.productservices.repositories.projections;

public interface ProductProjection {
//    Instead of creating a new projection for every retrival we can use a single ProductProjection
    Long getId();
    String getTitle();
    String getDescription();
}
