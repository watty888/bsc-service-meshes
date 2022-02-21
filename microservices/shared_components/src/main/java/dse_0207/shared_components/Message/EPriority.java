package dse_0207.shared_components.Message;

public enum EPriority {
  LOW("low"),
  HIGH("high");

  public final String label;

  EPriority(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
