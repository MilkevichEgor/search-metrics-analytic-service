package com.geosearch.entity;

import com.geosearch.constant.SearchType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "search")
public class SearchAnalytic {
  @Id
  @Column(name = "id", nullable = false)
  private UUID id;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  @Enumerated(EnumType.STRING)
  private SearchType searchType;

  @Override
  public final boolean equals(Object o) {
	if (this == o) return true;
	if (o == null) return false;
	Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
	Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
	if (thisEffectiveClass != oEffectiveClass) return false;
	SearchAnalytic that = (SearchAnalytic) o;
	return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
	return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }

  @Override
  public String toString() {
	return getClass().getSimpleName() + "(" +
		"id = " + id + ", " +
		"createdAt = " + createdAt + ", " +
		"updatedAt = " + updatedAt + ", " +
		"searchType = " + searchType + ")";
  }
}
