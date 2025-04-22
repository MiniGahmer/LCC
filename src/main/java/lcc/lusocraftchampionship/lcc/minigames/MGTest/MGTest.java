package lcc.lusocraftchampionship.minigame.MGTest;

import lcc.lusocraftchampionship.minigame.AMinigame;
import lcc.lusocraftchampionship.minigame.IMinigameExplanation;
import lcc.lusocraftchampionship.minigame.MGTest.listener.ListenerHello;
import lcc.lusocraftchampionship.minigame.MGTest.stages.MGTestBegin;
import lcc.lusocraftchampionship.minigame.MGTest.stages.MGTestEnd;

public class MGTest extends AMinigame {
  public MGTest(String data, boolean isTesting) {
    super(data, isTesting);

    addClassListener(ListenerHello.class);
    addState(MGStages.BEGIN, MGTestBegin.class);
    addState(MGStages.END, MGTestEnd.class);
  }

  @Override
  public IMinigameExplanation getMinigameExplanation(IMinigameExplanation minigameExplanation) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getMinigameExplanation'");
  }

  @Override
  public String getWorldName() {
    return "World";
  }
}
