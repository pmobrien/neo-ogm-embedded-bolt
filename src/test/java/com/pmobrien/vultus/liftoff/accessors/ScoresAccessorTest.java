package com.pmobrien.vultus.liftoff.accessors;

import com.pmobrien.vultus.liftoff.neo.pojo.Athlete;
import org.testng.annotations.Test;

public class ScoresAccessorTest {
  
  @Test
  public void testAddScore() {
    
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
        .setAgeGroup(Athlete.AgeGroup.GROUP_0_39)
        .setSnatch(275L)
        .setCleanAndJerk(350L)
        .setMetcon(100L);
  }
}
