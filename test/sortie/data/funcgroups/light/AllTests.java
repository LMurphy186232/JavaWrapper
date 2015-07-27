package sortie.data.funcgroups.light;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(GapLightTest.class);
    suite.addTestSuite(LightFilterTest.class);
    suite.addTestSuite(SailLightTest.class);
    suite.addTestSuite(TestAverageLight.class);
    suite.addTestSuite(TestBasalAreaLight.class);
    suite.addTestSuite(TestConstantGLI.class);
    suite.addTestSuite(TestGLIBase.class);
    suite.addTestSuite(TestGLILight.class);
    suite.addTestSuite(TestGLIMap.class);
    suite.addTestSuite(TestGLIPoints.class);
    suite.addTestSuite(TestQuadratGLILight.class);
    suite.addTestSuite(TestStormLight.class);
    //$JUnit-END$
    return suite;
  }

}
