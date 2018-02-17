package entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Gps entity.
 * */
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Gps implements Serializable, Comparable {

  private static final long serialVersionUID = -3345787892147268261L;

  @XmlElement
  private Long id;

  @XmlElement
  private Long timestamp;

  @XmlElement
  private Float longitude;

  @XmlElement
  private Float latitude;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public Float getLongitude() {
    return longitude;
  }

  public void setLongitude(Float longitude) {
    this.longitude = longitude;
  }

  public Float getLatitude() {
    return latitude;
  }

  public void setLatitude(Float latitude) {
    this.latitude = latitude;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
    result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
    result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Gps other = (Gps) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (latitude == null) {
      if (other.latitude != null)
        return false;
    } else if (!latitude.equals(other.latitude))
      return false;
    if (longitude == null) {
      if (other.longitude != null)
        return false;
    } else if (!longitude.equals(other.longitude))
      return false;
    if (timestamp == null) {
      if (other.timestamp != null)
        return false;
    } else if (!timestamp.equals(other.timestamp))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Gps [id=" + id + ", timestamp=" + timestamp + ", longitude=" + longitude
        + ", latitude=" + latitude + "]";
  }

  @Override
  public int compareTo(Object o) {
    return (this.getId().compareTo(((Gps) o).getId()));
  }
}
