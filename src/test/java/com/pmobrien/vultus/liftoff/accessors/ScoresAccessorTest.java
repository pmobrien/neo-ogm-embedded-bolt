package com.pmobrien.vultus.liftoff.accessors;

import com.google.common.base.Strings;
import com.pmobrien.vultus.liftoff.neo.NeoConnector;
import com.pmobrien.vultus.liftoff.neo.pojo.Athlete;
import java.nio.file.Paths;
import junit.framework.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ScoresAccessorTest {
  
  @BeforeClass
  public void beforeClass() {
    if(Strings.isNullOrEmpty(System.getProperty(NeoConnector.NEO_STORE))) {
      System.setProperty(
          NeoConnector.NEO_STORE,
          Paths.get(Paths.get("").toAbsolutePath().toString(), "target", "neo-store").toString()
      );
    }
    
    new ScoresAccessor().addScore(Athletes.PATRICK);
    new ScoresAccessor().addScore(Athletes.AARON);
    new ScoresAccessor().addScore(Athletes.EDWIN);
    new ScoresAccessor().addScore(Athletes.OLD_DUDE);
    new ScoresAccessor().addScore(Athletes.BIG_BERTHA);
  }
  
  @Test
  public void test() {
    // get all
    Assert.assertEquals(5, new ScoresAccessor().getScores(null, null).size());
    
    // only age group filters
    Assert.assertEquals(3, new ScoresAccessor().getScores(Athlete.AgeGroup.GROUP_0_39, null).size());
    Assert.assertEquals(1, new ScoresAccessor().getScores(Athlete.AgeGroup.GROUP_40_54, null).size());
    Assert.assertEquals(1, new ScoresAccessor().getScores(Athlete.AgeGroup.GROUP_55_PLUS, null).size());
    
    // only gender filters
    Assert.assertEquals(4, new ScoresAccessor().getScores(null, Athlete.Gender.MALE).size());
    Assert.assertEquals(1, new ScoresAccessor().getScores(null, Athlete.Gender.FEMALE).size());
    
    // age and gender filters
    Assert.assertEquals(3, new ScoresAccessor().getScores(Athlete.AgeGroup.GROUP_0_39, Athlete.Gender.MALE).size());
    Assert.assertEquals(0, new ScoresAccessor().getScores(Athlete.AgeGroup.GROUP_0_39, Athlete.Gender.FEMALE).size());
    Assert.assertEquals(0, new ScoresAccessor().getScores(Athlete.AgeGroup.GROUP_40_54, Athlete.Gender.MALE).size());
    Assert.assertEquals(1, new ScoresAccessor().getScores(Athlete.AgeGroup.GROUP_40_54, Athlete.Gender.FEMALE).size());
    Assert.assertEquals(1, new ScoresAccessor().getScores(Athlete.AgeGroup.GROUP_55_PLUS, Athlete.Gender.MALE).size());
    Assert.assertEquals(0, new ScoresAccessor().getScores(Athlete.AgeGroup.GROUP_55_PLUS, Athlete.Gender.FEMALE).size());
  }
  
  private static class Athletes {
    
    private static final Athlete PATRICK = new Athlete()
        .setUsername("Patrick O'Brien")
        .setWeight(235L)
        .setGender(Athlete.Gender.MALE)
        .setAgeGroup(Athlete.AgeGroup.GROUP_0_39)
        .setSnatch(225L)
        .setCleanAndJerk(300L)
        .setMetcon(320L);
    
    private static final Athlete AARON = new Athlete()
        .setUsername("Aaron Le Hew")
        .setWeight(220L)
        .setGender(Athlete.Gender.MALE)
        .setAgeGroup(Athlete.AgeGroup.GROUP_0_39)
        .setSnatch(225L)
        .setCleanAndJerk(275L)
        .setMetcon(300L);
    
    private static final Athlete EDWIN = new Athlete()
        .setUsername("Edwin Bonayon")
        .setWeight(205L)
        .setGender(Athlete.Gender.MALE)
        .setAgeGroup(Athlete.AgeGroup.GROUP_0_39)
        .setSnatch(225L)
        .setCleanAndJerk(315L)
        .setMetcon(325L);
    
    private static final Athlete OLD_DUDE = new Athlete()
        .setUsername("Old Dude")
        .setWeight(150L)
        .setGender(Athlete.Gender.MALE)
        .setAgeGroup(Athlete.AgeGroup.GROUP_55_PLUS)
        .setSnatch(125L)
        .setCleanAndJerk(185L)
        .setMetcon(250L);
    
    private static final Athlete BIG_BERTHA = new Athlete()
        .setUsername("Big Bertha")
        .setWeight(280L)
        .setGender(Athlete.Gender.FEMALE)
        .setAgeGroup(Athlete.AgeGroup.GROUP_40_54)
        .setSnatch(275L)
        .setCleanAndJerk(350L)
        .setMetcon(100L);
  }
}
