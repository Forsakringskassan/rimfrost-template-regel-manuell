package se.fk.github.regelmanuell;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.spi.Connector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import se.fk.rimfrost.OperativtUppgiftslagerRequestMessage;
import se.fk.rimfrost.OperativtUppgiftslagerResponseMessage;
import se.fk.rimfrost.OperativtUppgiftslagerStatusMessage;
import se.fk.rimfrost.Status;
import se.fk.rimfrost.framework.regel.RegelRequestMessagePayload;
import se.fk.rimfrost.framework.regel.RegelRequestMessagePayloadData;
import se.fk.rimfrost.framework.regel.RegelResponseMessagePayload;
import se.fk.rimfrost.framework.regel.Utfall;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.anyRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@QuarkusTest
@QuarkusTestResource.List(
{
      @QuarkusTestResource(WireMockTestResource.class)
})
public class RegelManuellTest
{
   private static final String oulRequestsChannel = "operativt-uppgiftslager-requests";
   private static final String oulResponsesChannel = "operativt-uppgiftslager-responses";
   private static final String oulStatusNotificationChannel = "operativt-uppgiftslager-status-notification";
   private static final String oulStatusControlChannel = "operativt-uppgiftslager-status-control";
   private static final String regelRequestsChannel = "regel-requests";
   private static final String regelResponsesChannel = "regel-responses";
   private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
         .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
   private static final String kundbehovsflodeEndpoint = "/kundbehovsflode/";
   private static WireMockServer wiremockServer;

   @Inject
   @Connector("smallrye-in-memory")
   InMemoryConnector inMemoryConnector;

   @BeforeAll
   static void setup()
   {
      setupRegelManuellTest();
      setupWiremock();
   }

   static void setupRegelManuellTest()
   {
      Properties props = new Properties();
      try (InputStream in = RegelManuellTest.class.getResourceAsStream("/test.properties"))
      {
         if (in == null)
         {
            throw new RuntimeException("Could not find /test.properties in classpath");
         }
         props.load(in);
      }
      catch (IOException e)
      {
         throw new RuntimeException("Failed to load test.properties", e);
      }
   }

   static void setupWiremock()
   {
      wiremockServer = WireMockTestResource.getWireMockServer();
   }

   public static List<LoggedRequest> waitForWireMockRequest(
         WireMockServer server,
         String urlRegex,
         int minRequests)
   {
      List<LoggedRequest> requests = Collections.emptyList();
      int retries = 20;
      long sleepMs = 250;
      for (int i = 0; i < retries; i++)
      {
         requests = server.findAll(anyRequestedFor(urlMatching(urlRegex)));
         if (requests.size() >= minRequests)
         {
            return requests;
         }
         try
         {
            Thread.sleep(sleepMs);
         }
         catch (InterruptedException e)
         {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for WireMock request", e);
         }
      }
      return requests;
   }

   private void sendRegelRequest(String kundbehovsflodeId) throws Exception
   {
      RegelRequestMessagePayload payload = new RegelRequestMessagePayload();
      RegelRequestMessagePayloadData data = new RegelRequestMessagePayloadData();
      data.setKundbehovsflodeId(kundbehovsflodeId);
      payload.setSpecversion(se.fk.rimfrost.framework.regel.SpecVersion.NUMBER_1_DOT_0);
      payload.setId("99994567-89ab-4cde-9012-3456789abcde");
      payload.setSource("TestSource-001");
      payload.setType(regelRequestsChannel);
      payload.setKogitoprocid("234567");
      payload.setKogitorootprocid("123456");
      payload.setKogitorootprociid("77774567-89ab-4cde-9012-3456789abcde");
      payload.setKogitoparentprociid("88884567-89ab-4cde-9012-3456789abcde");
      payload.setKogitoprocinstanceid("66664567-89ab-4cde-9012-3456789abcde");
      payload.setKogitoprocist("345678");
      payload.setKogitoprocversion("111");
      payload.setKogitoproctype(se.fk.rimfrost.framework.regel.KogitoProcType.BPMN);
      payload.setKogitoprocrefid("56789");
      payload.setData(data);
      inMemoryConnector.source(regelRequestsChannel).send(payload);
   }

   private List<? extends Message<?>> waitForMessages(String channel)
   {
      await().atMost(5, TimeUnit.SECONDS).until(() -> !inMemoryConnector.sink(channel).received().isEmpty());
      return inMemoryConnector.sink(channel).received();
   }

   public void sendPostRegelManuell(String kundbehovsflodeId)
   {
      // TODO: Change "/regel/manuell/" to match path in RegelController
      given().when().post("/regel/manuell/{kundbehovsflodeId}/done", kundbehovsflodeId).then().statusCode(204);
   }

   @ParameterizedTest
   @CsvSource(
   {
         "5367f6b8-cc4a-11f0-8de9-199901011234"
   })
   void TestRegelManuell(String kundbehovsflodeId) throws Exception
   {
      System.out.printf("Starting TestRegelManuell. %S%n", kundbehovsflodeId);

      // Send regel request to start workflow
      sendRegelRequest(kundbehovsflodeId);

      //
      // Verify GET kundbehovsflöde requested
      //
      List<LoggedRequest> kundbehovsflodeRequests = waitForWireMockRequest(wiremockServer,
            kundbehovsflodeEndpoint + kundbehovsflodeId, 1);
      var getRequests = kundbehovsflodeRequests.stream().filter(p -> p.getMethod().equals(RequestMethod.GET)).toList();
      assertFalse(getRequests.isEmpty());

      //
      // Verify oul message produced
      //
      var messages = waitForMessages(oulRequestsChannel);
      assertEquals(1, messages.size());

      var message = messages.getFirst().getPayload();
      assertInstanceOf(OperativtUppgiftslagerRequestMessage.class, message);

      var oulRequestMessage = (OperativtUppgiftslagerRequestMessage) message;
      assertEquals(kundbehovsflodeId, oulRequestMessage.getKundbehovsflodeId());
      assertEquals("TestUppgiftBeskrivning", oulRequestMessage.getBeskrivning());
      assertEquals("TestUppgiftNamn", oulRequestMessage.getRegel());
      assertEquals("C", oulRequestMessage.getVerksamhetslogik());
      assertEquals("ANSVARIG_HANDLAGGARE", oulRequestMessage.getRoll());
      // TODO: Change "/regel/manuell/" to mtch path in RegelController
      assertTrue(oulRequestMessage.getUrl().contains("/regel/manuell"));

      // Clear previous requests
      wiremockServer.resetRequests();

      //
      // Send mocked OUL response
      //
      OperativtUppgiftslagerResponseMessage oulResponseMessage = new OperativtUppgiftslagerResponseMessage();
      oulResponseMessage.setKundbehovsflodeId(kundbehovsflodeId);
      oulResponseMessage.setUppgiftId("11e53b18-e9ac-4707-825b-a1cb80689c29");
      inMemoryConnector.source(oulResponsesChannel).send(oulResponseMessage);

      //
      // mock status update from OUL
      //
      OperativtUppgiftslagerStatusMessage oulStatusMessage = new OperativtUppgiftslagerStatusMessage();
      oulStatusMessage.setStatus(Status.NY);
      oulStatusMessage.setUppgiftId(oulResponseMessage.getUppgiftId());
      oulStatusMessage.setKundbehovsflodeId(kundbehovsflodeId);
      oulStatusMessage.setUtforarId("383cc515-4c55-479b-a96b-244734ef1336");
      inMemoryConnector.source(oulStatusNotificationChannel).send(oulStatusMessage);

      //
      // mock POST operation from portal FE
      //
      sendPostRegelManuell(kundbehovsflodeId);

      //
      // verify kafka status message sent to oul
      //
      messages = waitForMessages(oulStatusControlChannel);
      assertEquals(1, messages.size());

      message = messages.getFirst().getPayload();
      assertInstanceOf(OperativtUppgiftslagerStatusMessage.class, message);

      oulStatusMessage = (OperativtUppgiftslagerStatusMessage) message;
      assertEquals(oulResponseMessage.getUppgiftId(), oulStatusMessage.getUppgiftId());
      assertEquals(Status.AVSLUTAD, oulStatusMessage.getStatus());

      //
      // verify kafka manuell response message sent to VAH
      //
      messages = waitForMessages(regelResponsesChannel);
      assertEquals(1, messages.size());

      message = messages.getFirst().getPayload();
      assertInstanceOf(RegelResponseMessagePayload.class, message);

      var rtfManuellResponseMessagePayload = (RegelResponseMessagePayload) message;
      assertEquals(kundbehovsflodeId, rtfManuellResponseMessagePayload.getData().getKundbehovsflodeId());
      assertEquals(Utfall.JA, rtfManuellResponseMessagePayload.getData().getUtfall());
   }
}
