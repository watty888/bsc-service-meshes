package dse_0207.shared_components.Message;

import java.util.HashMap;
import java.util.Map;

public class JSONWrapper {
  public Map<String, String> values = new HashMap<>();

  public JSONWrapper() { }

  public JSONWrapper(Map<String, String> values) {
    this.values = values;
  }
}
