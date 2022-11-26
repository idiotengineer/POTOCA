package My_Project.integration.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Dates {
    @Column(name = "uploaded_time", nullable = false, updatable = false)
    private LocalDateTime uploadedTime;

    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;
}
