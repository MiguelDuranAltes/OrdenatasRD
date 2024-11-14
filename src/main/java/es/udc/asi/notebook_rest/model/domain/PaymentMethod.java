package es.udc.asi.notebook_rest.model.domain;

import jakarta.persistence.*;

@Entity
public class PaymentMethod {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_generator")
  @SequenceGenerator(name = "payment_generator", sequenceName = "payment_seq")
  private Long id;

  @Column
  private String creditCardNumber;

  @Column
  private Integer cvv;

  @Column
  private String name;

}
