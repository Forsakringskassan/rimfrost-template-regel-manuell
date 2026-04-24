package se.fk.github.regel.template;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import se.fk.rimfrost.framework.regel.manuell.base.AbstractRegelManuellOulTest;

@QuarkusTest
@QuarkusTestResource.List(
{
      @QuarkusTestResource(WireMockRegelTemplate.class)
})
public class RegelTemplateOulTest extends AbstractRegelManuellOulTest
{
}
