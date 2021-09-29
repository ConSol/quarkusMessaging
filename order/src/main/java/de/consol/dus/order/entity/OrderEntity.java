package de.consol.dus.order.entity;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {

  @Id
  @SequenceGenerator(name = "OrderIdGenerator", sequenceName = "customer_seq_id", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderIdGenerator")
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "uuid", nullable = false, unique = true)
  private UUID uuid;

  @Column(name = "description", nullable = false, unique = false)
  private String description;

  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false)
  private CustomerEntity customer;

  @Column(name = "created", nullable = false)
  private Instant created;

  @Column(name = "total_in_cents", nullable = false)
  private int totalInCents;
}
