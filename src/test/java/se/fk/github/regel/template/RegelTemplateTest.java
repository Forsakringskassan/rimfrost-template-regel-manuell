package se.fk.github.regel.template;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import se.fk.rimfrost.framework.regel.test.RegelTest;

@QuarkusTest
@QuarkusTestResource.List(
{
      @QuarkusTestResource(WireMockTestResource.class),
      @QuarkusTestResource(StorageDataTestResource.class)
})
public class RegelTemplateTest extends RegelTest
{
   // TODO
}
