package vincentlow.twittur.account.credential.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

  private static final long serialVersionUID = 3845696067742481837L;

  @Id
  @GeneratedValue(generator = "uuid-sequence")
  @GenericGenerator(name = "uuid-sequence", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  protected String id;

  @Column(name = "created_by")
  protected String createdBy;

  @Column(name = "created_date")
  protected LocalDateTime createdDate;

  @Column(name = "updated_by")
  protected String updatedBy;

  @Column(name = "updated_date")
  protected LocalDateTime updatedDate;

  @Column(name = "mark_for_delete")
  protected boolean markForDelete;

  @Override
  public String toString() {

    return "BaseEntity{" +
        "id='" + id + '\'' +
        ", createdBy='" + createdBy + '\'' +
        ", createdDate=" + createdDate +
        ", updatedBy='" + updatedBy + '\'' +
        ", updatedDate=" + updatedDate +
        ", markForDelete=" + markForDelete +
        '}';
  }
}
