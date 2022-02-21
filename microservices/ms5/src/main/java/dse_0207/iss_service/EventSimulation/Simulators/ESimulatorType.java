package dse_0207.iss_service.EventSimulation.Simulators;

public enum ESimulatorType {
    weather("weather"),
    temperature("temperature");

    public final String label;

    ESimulatorType(String label) {
        this.label = label;
    }

    public static ESimulatorType getTypeByName(String type) {
        try {
            return ESimulatorType.valueOf(type);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
