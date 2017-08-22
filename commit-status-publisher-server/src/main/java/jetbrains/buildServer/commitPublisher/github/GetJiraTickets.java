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
  private static final String cozmo = "coz\\d*";
  private static final String bi = "bi\\d*";

  @NotNull
  public static List<String> getListJiraTickets(@NotNull String branch_name, 
                                                @NotNull String jira_link) {

    List<String> list_jira_link = new ArrayList<String>();
    String name = branch_name.toLowerCase().replaceAll("[^\\da-z]", "");

    Pattern od_pattern = Pattern.compile(od);
    Matcher od_matcher = od_pattern.matcher(name);
    while (od_matcher.find()){
      String od_id = od_matcher.group().replaceAll("od", jira_link + "od-");
      list_jira_link.add(od_id);
    }

    Pattern cozmo_pattern = Pattern.compile(cozmo);
    Matcher cozmo_matcher = cozmo_pattern.matcher(name);
    while (cozmo_matcher.find()){
      String coz_id = cozmo_matcher.group().replaceAll("coz", jira_link + "coz-");
      list_jira_link.add(coz_id);
    }

    Pattern bi_pattern = Pattern.compile(bi);
    Matcher bi_matcher = bi_pattern.matcher(name);
    while (bi_matcher.find()) {
      String bi_id = bi_matcher.group().replaceAll("bi", jira_link + "bi-");
      list_jira_link.add(bi_id);
    }
    if (!(list_jira_link.size()>0)) {
      LOG.debug("Pull request title " + branch_name + " does not contain any ticket id");
      return null;
    }
    return list_jira_link;
  }
}
