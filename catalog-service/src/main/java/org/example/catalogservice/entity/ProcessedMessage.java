package org.example.catalogservice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "processed_message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedMessage {
    @Id
    private Long orderId;

    private LocalDateTime processedAt;
}
