package se.fk.github.regel.template;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Map;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WireMockTestResource implements QuarkusTestResourceLifecycleManager
{

   private static WireMockServer server;

   public static WireMockServer getWireMockServer()
   {
      return server;
   }

   @Override
   public Map<String, String> start()
   {
      server = new WireMockServer(
            options()
                  .dynamicPort()
                  .usingFilesUnderDirectory("src/test/resources"));
      server.start();

      return Map.of(
            "handlaggning.api.base-url", server.baseUrl());
   }

   @Override
   public void stop()
   {
      if (server != null)
      {
         server.stop();
      }
   }
}
