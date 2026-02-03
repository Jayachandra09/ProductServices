package dev.jay.productservices.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Column(name = "last_updated_at")
    private Date lastUpdatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    // ✅ Automatically called before INSERT
    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        this.createdAt = now;
        this.lastUpdatedAt = now;
        this.isDeleted = false;
    }

    // ✅ Automatically called before UPDATE
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdatedAt = new Date();
    }
}
