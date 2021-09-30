package co.com.mintic.tiendagenerica.models;

import java.time.Instant;

import javax.persistence.*;

@Entity
@Table(name = "refreshtoken")
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(nullable = false)
  private Instant expiryDate;

  @Column(nullable = false, unique = true)
  private String token;
  
  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  public RefreshToken() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Instant getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Instant expiryDate) {
    this.expiryDate = expiryDate;
  }

}
