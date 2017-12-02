package com.pmobrien.vultus.liftoff.accessors;

import com.pmobrien.vultus.liftoff.neo.pojo.ScoreNode;
import org.testng.annotations.Test;

public class ScoresAccessorTest {

  
  
  @Test
  public void testAddScore() {
    
  }
  
  private static class Athletes {
    
    private static final ScoreNode PATRICK = new ScoreNode()
        .setUsername("Patrick O'Brien")
        .setWeight(235L)
        .setGender(ScoreNode.Gender.MALE)
        .setAgeGroup(ScoreNode.AgeGroup.GROUP_0_39)
        .setSnatch(225L)
        .setCleanAndJerk(300L)
        .setMetcon(320L);
    
    private static final ScoreNode AARON = new ScoreNode()
        .setUsername("Aaron Le Hew")
        .setWeight(220L)
        .setGender(ScoreNode.Gender.MALE)
        .setAgeGroup(ScoreNode.AgeGroup.GROUP_0_39)
        .setSnatch(225L)
        .setCleanAndJerk(275L)
        .setMetcon(300L);
    
    private static final ScoreNode EDWIN = new ScoreNode()
        .setUsername("Edwin Bonayon")
        .setWeight(205L)
        .setGender(ScoreNode.Gender.MALE)
        .setAgeGroup(ScoreNode.AgeGroup.GROUP_0_39)
        .setSnatch(225L)
        .setCleanAndJerk(315L)
        .setMetcon(325L);
    
    private static final ScoreNode OLD_DUDE = new ScoreNode()
        .setUsername("Old Dude")
        .setWeight(150L)
        .setGender(ScoreNode.Gender.MALE)
        .setAgeGroup(ScoreNode.AgeGroup.GROUP_55_PLUS)
        .setSnatch(125L)
        .setCleanAndJerk(185L)
        .setMetcon(250L);
    
    private static final ScoreNode BIG_BERTHA = new ScoreNode()
        .setUsername("Big Bertha")
        .setWeight(280L)
        .setGender(ScoreNode.Gender.FEMALE)
        .setAgeGroup(ScoreNode.AgeGroup.GROUP_0_39)
        .setSnatch(275L)
        .setCleanAndJerk(350L)
        .setMetcon(100L);
  }
}
