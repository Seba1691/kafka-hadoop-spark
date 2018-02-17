package api;

import com.sun.jersey.spi.resource.Singleton;
import entities.Gps;
import service.KafkaService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/gps/")
@Singleton
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML})
public class GpsEndpoint {

  private KafkaService kafkaService = new KafkaService();

  /**
   * Method used to add a gps.
   * */
  @POST
  public void addGps(Gps gps) {
    kafkaService.addElement(gps);
  }

}
