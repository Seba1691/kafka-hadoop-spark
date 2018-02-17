
package api;
/**
 * Class that has the differents filter for the gps.
 * */
public class GpsFilter {

  private Long id;
  
  private Float minLongitude;
  
  private Float maxLongitude;
  
  private Float minLatitude;
  
  private Float maxLatitude;
  
  private Long minTimeStamp;
  
  private Long maxTimeStamp;

  public Long getId() {
    return id;
  }

  public Float getMinLongitude() {
    return minLongitude;
  }

  public Float getMaxLongitude() {
    return maxLongitude;
  }

  public Float getMinLatitude() {
    return minLatitude;
  }

  public Float getMaxLatitude() {
    return maxLatitude;
  }

  public Long getMinTimeStamp() {
    return minTimeStamp;
  }

  public Long getMaxTimeStamp() {
    return maxTimeStamp;
  }
  
  
}
