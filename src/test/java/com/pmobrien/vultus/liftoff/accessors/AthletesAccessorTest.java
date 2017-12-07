package com.pmobrien.vultus.liftoff.accessors;

import com.google.common.base.Strings;
import com.pmobrien.vultus.liftoff.neo.NeoConnector;
import com.pmobrien.vultus.liftoff.neo.pojo.Athlete;
import java.nio.file.Paths;
import junit.framework.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AthletesAccessorTest {
  
  @BeforeClass
  public void beforeClass() {
    if(Strings.isNullOrEmpty(System.getProperty(NeoConnector.NEO_STORE))) {
      System.setProperty(
          NeoConnector.NEO_STORE,
          Paths.get(Paths.get("").toAbsolutePath().toString(), "target", "neo-store").toString()
      );
    }
    
    new AthletesAccessor().addScore(Athletes.PATRICK);
    new AthletesAccessor().addScore(Athletes.AARON);
    new AthletesAccessor().addScore(Athletes.EDWIN);
    new AthletesAccessor().addScore(Athletes.OLD_DUDE);
    new AthletesAccessor().addScore(Athletes.BIG_BERTHA);
    new AthletesAccessor().addScore(Athletes.EDWIN_SCALED);
    new AthletesAccessor().addScore(Athletes.AARON_SCALED);
    new AthletesAccessor().addScore(Athletes.BIG_BERTHA_SCALED);
  }
  
  @Test
  public void test() {
    // get all
    Assert.assertEquals(8, new AthletesAccessor().getScores(null, null).size());
    
    // only age group filters
    Assert.assertEquals(5, new AthletesAccessor().getScores(Athlete.AgeGroup.GROUP_0_39, null).size());
    Assert.assertEquals(2, new AthletesAccessor().getScores(Athlete.AgeGroup.GROUP_40_54, null).size());
    Assert.assertEquals(1, new AthletesAccessor().getScores(Athlete.AgeGroup.GROUP_55_PLUS, null).size());
    
    // only gender filters
    Assert.assertEquals(6, new AthletesAccessor().getScores(null, Athlete.Gender.MALE).size());
    Assert.assertEquals(2, new AthletesAccessor().getScores(null, Athlete.Gender.FEMALE).size());
    
    // age and gender filters
    Assert.assertEquals(5, new AthletesAccessor().getScores(Athlete.AgeGroup.GROUP_0_39, Athlete.Gender.MALE).size());
    Assert.assertEquals(0, new AthletesAccessor().getScores(Athlete.AgeGroup.GROUP_0_39, Athlete.Gender.FEMALE).size());
    Assert.assertEquals(0, new AthletesAccessor().getScores(Athlete.AgeGroup.GROUP_40_54, Athlete.Gender.MALE).size());
    Assert.assertEquals(2, new AthletesAccessor().getScores(Athlete.AgeGroup.GROUP_40_54, Athlete.Gender.FEMALE).size());
    Assert.assertEquals(1, new AthletesAccessor().getScores(Athlete.AgeGroup.GROUP_55_PLUS, Athlete.Gender.MALE).size());
    Assert.assertEquals(0, new AthletesAccessor().getScores(Athlete.AgeGroup.GROUP_55_PLUS, Athlete.Gender.FEMALE).size());
  }
  
  private static class Athletes {
    
    private static final Athlete PATRICK = new Athlete()
        .setUsername("Patrick O'Brien")
        .setWeight(235L)
        .setGender(Athlete.Gender.MALE)
        .setAgeGroup(Athlete.AgeGroup.GROUP_0_39)
        .setSnatch(225L)
        .setCleanAndJerk(300L)
        .setMetcon(320L)
        .setRx(true);
    
    private static final Athlete AARON = new Athlete()
        .setUsername("Aaron Le Hew")
        .setWeight(220L)
        .setGender(Athlete.Gender.MALE)
        .setAgeGroup(Athlete.AgeGroup.GROUP_0_39)
        .setSnatch(225L)
        .setCleanAndJerk(275L)
        .setMetcon(300L)
        .setRx(true);
    
    private static final Athlete EDWIN = new Athlete()
        .setUsername("Edwin Bonayon")
        .setWeight(205L)
        .setGender(Athlete.Gender.MALE)
        .setAgeGroup(Athlete.AgeGroup.GROUP_0_39)
        .setSnatch(225L)
        .setCleanAndJerk(315L)
        .setMetcon(325L)
        .setRx(true);
    
    private static final Athlete OLD_DUDE = new Athlete()
        .setUsername("Old Dude")
        .setWeight(150L)
        .setGender(Athlete.Gender.MALE)
        .setAgeGroup(Athlete.AgeGroup.GROUP_55_PLUS)
        .setSnatch(125L)
        .setCleanAndJerk(185L)
        .setMetcon(250L)
        .setRx(true);
    
    private static final Athlete BIG_BERTHA = new Athlete()
        .setUsername("Big Bertha")
        .setWeight(280L)
        .setGender(Athlete.Gender.FEMALE)
        .setAgeGroup(Athlete.AgeGroup.GROUP_40_54)
        .setSnatch(275L)
        .setCleanAndJerk(350L)
        .setMetcon(100L)
        .setRx(true);
    
    private static final Athlete EDWIN_SCALED = new Athlete()
        .setUsername("Edwin Scaled")
        .setWeight(205L)
        .setGender(Athlete.Gender.MALE)
        .setAgeGroup(Athlete.AgeGroup.GROUP_0_39)
        .setSnatch(225L)
        .setCleanAndJerk(315L)
        .setMetcon(325L)
        .setRx(false);
    
    private static final Athlete AARON_SCALED = new Athlete()
        .setUsername("Aaron Scaled")
        .setWeight(220L)
        .setGender(Athlete.Gender.MALE)
        .setAgeGroup(Athlete.AgeGroup.GROUP_0_39)
        .setSnatch(225L)
        .setCleanAndJerk(275L)
        .setMetcon(300L)
        .setRx(false);
    
    private static final Athlete BIG_BERTHA_SCALED = new Athlete()
        .setUsername("Bertha Scaled")
        .setWeight(280L)
        .setGender(Athlete.Gender.FEMALE)
        .setAgeGroup(Athlete.AgeGroup.GROUP_40_54)
        .setSnatch(275L)
        .setCleanAndJerk(350L)
        .setMetcon(100L)
        .setRx(false);
  }
}
