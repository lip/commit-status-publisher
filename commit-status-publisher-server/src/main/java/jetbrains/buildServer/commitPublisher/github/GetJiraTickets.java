package jetbrains.buildServer.commitPublisher.github;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GetJiraTickets {
  private static final Logger LOG = Logger.getInstance(GetJiraTickets.class.getName());

  private static final String PATTERN = "[- ]\\d+";
  private static final String[] PROJECT = {"od","cozmo","bi"};

  @NotNull
  public static List<String> getListTicketLink(@NotNull String branchName, 
                                               @NotNull String jiraLink) {

    List<String> listTicketLink = new ArrayList<String>();
    for (int i = 0; i < PROJECT.length; i++) {
      Pattern projPattern = Pattern.compile(PROJECT[i] + PATTERN);
      Matcher projMatcher = projPattern.matcher(branchName.toLowerCase());
      while (projMatcher.find()) {
        String ticketLink = jiraLink + projMatcher.group().replaceAll("[- ]","-");
        listTicketLink.add(ticketLink);
      }
    }
    if (listTicketLink.size() == 0) {
      LOG.debug("Pull request title " + branchName + " does not contain any ticket id");
    }
    return listTicketLink;
  }
}
