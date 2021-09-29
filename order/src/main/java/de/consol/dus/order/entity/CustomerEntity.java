package de.consol.dus.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class CustomerEntity {

  @Id
  @SequenceGenerator(name = "CustomerIdGenerator", sequenceName = "customer_seq_id", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CustomerIdGenerator")
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "email", nullable = false, unique = true)
  private String email;
}
