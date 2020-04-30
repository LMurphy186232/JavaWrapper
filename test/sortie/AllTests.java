package sortie;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTest(sortie.data.funcgroups.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.analysis.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.disperse.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.disturbance.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.epiphyticestablishment.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.establishment.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.growth.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.light.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.management.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.mortality.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.nci.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.output.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.planting.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.seedpredation.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.snagdynamics.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.statechange.AllTests.suite());
    suite.addTest(sortie.data.funcgroups.substrate.AllTests.suite());
    suite.addTest(sortie.data.simpletypes.AllTests.suite());
    suite.addTest(sortie.datavisualizer.AllTests.suite());
    suite.addTest(sortie.gui.AllTests.suite());
    suite.addTest(sortie.gui.behaviorsetup.AllTests.suite());
    suite.addTest(sortie.gui.harvepplant.AllTests.suite());
    suite.addTest(sortie.gui.modelflowsetup.AllTests.suite());
    suite.addTestSuite(sortie.SynthesisTest.class);
    //$JUnit-END$
    return suite;
  }

}
