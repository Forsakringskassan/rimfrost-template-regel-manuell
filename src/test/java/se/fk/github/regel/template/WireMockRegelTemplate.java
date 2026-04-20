package se.fk.github.regel.template;

import com.github.tomakehurst.wiremock.WireMockServer;
import se.fk.rimfrost.framework.regel.manuell.WireMockRegelManuell;

import java.util.HashMap;
import java.util.Map;

public class WireMockRegelTemplate extends WireMockRegelManuell
{
   @Override
   protected Map<String, String> wiremockMapping(WireMockServer server)
   {
      Map<String, String> map = new HashMap<>(super.wiremockMapping(server));
      // TODO: Lägg till regel-specifika bas-url:er som behöver mockas till map
      return map;
   }
}
