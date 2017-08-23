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
  private static final String od = "od\\d*";
  private static final String cozmo = "cozmo\\d*";
  private static final String bi = "bi\\d*";

  @NotNull
  public static List<String> getListJiraTickets(@NotNull String branchName, 
                                                @NotNull String jiraLink) {

    List<String> listJiraLink = new ArrayList<String>();
    String name = branchName.toLowerCase().replaceAll("[^\\da-z]", "");

    Pattern odPattern = Pattern.compile(od);
    Matcher odMatcher = odPattern.matcher(name);
    while (odMatcher.find()) {
      String odId = odMatcher.group().replaceAll("od", jiraLink + "od-");
      listJiraLink.add(odId);
    }

    Pattern cozmoPattern = Pattern.compile(cozmo);
    Matcher cozmoMatcher = cozmoPattern.matcher(name);
    while (cozmoMatcher.find()) {
      String cozmoId = cozmoMatcher.group().replaceAll("cozmo", jiraLink + "cozmo-");
      listJiraLink.add(cozmoId);
    }

    Pattern biPattern = Pattern.compile(bi);
    Matcher biMatcher = biPattern.matcher(name);
    while (biMatcher.find()) {
      String biId = biMatcher.group().replaceAll("bi", jiraLink + "bi-");
      listJiraLink.add(biId);
    }
    if (!(listJiraLink.size()>0)) {
      LOG.debug("Pull request title " + branchName + " does not contain any ticket id");
      return null;
    }
    return listJiraLink;
  }
}
