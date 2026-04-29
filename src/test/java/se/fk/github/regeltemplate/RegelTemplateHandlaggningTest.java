package se.fk.github.regeltemplate;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import se.fk.rimfrost.framework.regel.manuell.base.AbstractRegelManuellHandlaggningTest;

@QuarkusTest
@QuarkusTestResource.List(
{
      @QuarkusTestResource(WireMockRegelTemplate.class)
})
public class RegelTemplateHandlaggningTest extends AbstractRegelManuellHandlaggningTest
{
}
