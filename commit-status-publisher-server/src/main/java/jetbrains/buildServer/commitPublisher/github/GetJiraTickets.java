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

  private static final String PATTERN = "\\d*";
  private static final String[] PROJECT = {"od","cozmo","bi"};

  @NotNull
  public static String getTicketLink(@NotNull String branchName, 
                                     @NotNull String jiraLink) {

    String name = branchName.toLowerCase().replaceAll("[^\\da-z]", "");
    String ticketLink = null;
    for (int i = 0; i < PROJECT.length; i++) {
      Pattern projPattern = Pattern.compile(PROJECT[i] + PATTERN);
      Matcher projMatcher = projPattern.matcher(name);
      while (projMatcher.find()) {
        ticketLink = projMatcher.group().replaceAll(PROJECT[i], jiraLink + PROJECT[i] + "-");
        break;
      }
    }
    if (ticketLink == null) {
      LOG.debug("Pull request title " + branchName + " does not contain any ticket id");
    }
    return ticketLink;
  }
}
